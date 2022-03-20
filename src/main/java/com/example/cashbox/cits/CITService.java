package com.example.cashbox.cits;

import com.example.cashbox.centers.Center;
import com.example.cashbox.centers.CenterNotFoundException;
import com.example.cashbox.centers.CenterRepository;
import com.example.cashbox.trucks.Truck;
import com.example.cashbox.trucks.TruckNotFoundException;
import com.example.cashbox.trucks.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CITService {

    @Autowired
    private CITRepository citRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private TruckRepository truckRepository;

    public void setCitRepository(CITRepository citRepository) {
        this.citRepository = citRepository;
    }

    public void setCenterRepository(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    public void setTruckRepository(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public CIT getById(int id) {
        Optional<CIT> result = citRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new CITNotFoundException(id);
    }

    public List<CIT> getByYMD(int year, int month, int date) {
        LocalDateTime dateTimeFrom = LocalDateTime.of(year, month, date, 0, 0, 0);
        LocalDateTime dateTimeTo  = LocalDateTime.of(year, month, date + 1, 0, 0, 0);
        return citRepository.findByLastUpdatedBetween(dateTimeFrom, dateTimeTo);
    }

    public CIT create(int senderId, int receiverId, BigDecimal amountTHB, BigDecimal amountUSD) {
        Optional<Center> resultSender = centerRepository.findById(senderId);
        Optional<Center> resultReceiver = centerRepository.findById(receiverId);
        if (resultSender.isEmpty()) {
            throw new CenterNotFoundException(senderId);
        }
        if (resultReceiver.isEmpty()) {
            throw new CenterNotFoundException(receiverId);
        }
        CIT cit = new CIT();
        //Should find a better way to generate ID
        cit.setId(citRepository.findAll().size() + 1);
        cit.setSender(resultSender.get());
        cit.setReceiver(resultReceiver.get());
        cit.setAmountTHB(amountTHB);
        cit.setAmountUSD(amountUSD);
        cit.setStatus(CITStatus.GENERATED);
        LocalDateTime now = LocalDateTime.now();
        cit.setLastUpdated(now);
        cit.setCreatedAt(now);
        citRepository.save(cit);

        //Update balance of sender
        Center sender = cit.getSender();
        sender.setBalanceTHB(sender.getBalanceTHB().subtract(amountTHB));
        sender.setBalanceUSD(sender.getBalanceUSD().subtract(amountUSD));
        centerRepository.save(sender);


        return cit;
    }

    public void receive(int id, int truckId) {
        Optional<CIT> resultCit = citRepository.findById(id);
        if (resultCit.isEmpty()) {
            throw new CITNotFoundException(id);
        }
        Optional<Truck> resultTruck = truckRepository.findById(truckId);
        if (resultTruck.isEmpty()) {
            throw new TruckNotFoundException(truckId);
        }
        CIT cit = resultCit.get();
        cit.setDeliveryTruck(resultTruck.get());
        cit.setStatus(CITStatus.RECEIVED);
        LocalDateTime now = LocalDateTime.now();
        cit.setLastUpdated(now);
        cit.setReceivedAt(now);
        citRepository.save(cit);
    }

    public void deliver(int id) {
        Optional<CIT> resultCit = citRepository.findById(id);
        if (resultCit.isEmpty()) {
            throw new CITNotFoundException(id);
        }
        CIT cit = resultCit.get();
        cit.setStatus(CITStatus.DELIVERED);
        LocalDateTime now = LocalDateTime.now();
        cit.setLastUpdated(now);
        cit.setDeliveredAt(now);
        citRepository.save(cit);
    }

    public void confirm(int id, BigDecimal amountTHB, BigDecimal amountUSD) {
        Optional<CIT> resultCit = citRepository.findById(id);
        if (resultCit.isEmpty()) {
            throw new CITNotFoundException(id);
        }
        CIT cit = resultCit.get();

        //Update balance of receiver
        Center receiver = cit.getReceiver();
        receiver.setBalanceTHB(receiver.getBalanceTHB().add(amountTHB));
        receiver.setBalanceUSD(receiver.getBalanceUSD().add(amountUSD));
        centerRepository.save(receiver);
        //Update CIT status
        cit.setAmountTHBConfirmed(amountTHB);
        cit.setAmountUSDConfirmed(amountUSD);
        cit.setStatus(CITStatus.CONFIRMED);
        LocalDateTime now = LocalDateTime.now();
        cit.setLastUpdated(now);
        cit.setConfirmedAt(now);
        citRepository.save(cit);
    }
}
