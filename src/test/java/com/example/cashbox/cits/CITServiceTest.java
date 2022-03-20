package com.example.cashbox.cits;

import com.example.cashbox.centers.Center;
import com.example.cashbox.centers.CenterRepository;
import com.example.cashbox.centers.CenterService;
import com.example.cashbox.trucks.Truck;
import com.example.cashbox.trucks.TruckRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CITServiceTest {

    @Mock
    private CITRepository citRepository;

    @Mock
    private CenterRepository centerRepository;

    @Mock
    private TruckRepository truckRepository;

    private CIT mockUpCITData() {
        Center center1 = mockUpCenterData(1);
        Center center2 = mockUpCenterData(2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        CIT cit = new CIT();
        cit.setId(1);
        cit.setSender(center1);
        cit.setReceiver(center2);
        cit.setLastUpdated(LocalDateTime.parse("2022-03-20 18:12:30", formatter));
        return cit;
    }

    private Center mockUpCenterData(int id) {
        Center center = new Center();
        center.setId(id);
        center.setBalanceTHB(new BigDecimal("1000000"));
        center.setBalanceUSD(new BigDecimal("1000000"));
        return center;
    }

    private Truck mockUpTruckData() {
        Truck truck = new Truck();
        truck.setId(5);
        return truck;
    }

    private List<CIT> mockUpCITListData() {
        Center center1 = new Center();
        center1.setId(1);
        center1.setBalanceTHB(new BigDecimal("1000000"));
        center1.setBalanceUSD(new BigDecimal("1000000"));
        Center center2 = new Center();
        center2.setId(2);
        center2.setBalanceTHB(new BigDecimal("1000000"));
        center2.setBalanceUSD(new BigDecimal("1000000"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        CIT cit1 = new CIT();
        cit1.setId(1);
        cit1.setSender(center1);
        cit1.setReceiver(center2);
        cit1.setLastUpdated(LocalDateTime.parse("2022-03-20 18:12:30", formatter));

        CIT cit2 = new CIT();
        cit2.setId(2);
        cit2.setSender(center1);
        cit2.setReceiver(center2);
        cit2.setLastUpdated(LocalDateTime.parse("2022-03-20 23:59:59", formatter));

        List<CIT> citList = new ArrayList<>();
        citList.add(cit1);
        citList.add(cit2);
        return citList;
    }

    @Test
    void getById() {
        //Arrange
        when(citRepository.findById(1)).thenReturn(Optional.of(mockUpCITData()));
        //Act
        CITService citService = new CITService();
        citService.setCitRepository(citRepository);
        citService.setTruckRepository(truckRepository);
        citService.setCenterRepository(centerRepository);
        CIT result = citService.getById(1);
        //Assert
        assertEquals(18, result.getLastUpdated().getHour());
        assertEquals(2, result.getReceiver().getId());
    }

    @Test
    void getByDMY() {
        //Arrange
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime from = LocalDateTime.parse("2022-03-20 00:00:00", formatter);
        LocalDateTime to = LocalDateTime.parse("2022-03-21 00:00:00", formatter);
        when(citRepository.findByLastUpdatedBetween(from, to)).thenReturn(mockUpCITListData());
        //Act
        CITService citService = new CITService();
        citService.setCitRepository(citRepository);
        citService.setTruckRepository(truckRepository);
        citService.setCenterRepository(centerRepository);
        List<CIT> result = citService.getByYMD(2022, 3, 20);
        //Assert
        assertEquals(2, result.size());
        assertEquals(59, result.get(1).getLastUpdated().getMinute());
    }

    @Test
    void create() {
        //Arrange
        when(centerRepository.findById(1)).thenReturn(Optional.of(mockUpCenterData(1)));
        when(centerRepository.findById(2)).thenReturn(Optional.of(mockUpCenterData(2)));
        //Act
        CITService citService = new CITService();
        citService.setCitRepository(citRepository);
        citService.setTruckRepository(truckRepository);
        citService.setCenterRepository(centerRepository);
        CIT result = citService.create(1, 2, new BigDecimal("1000"), new BigDecimal("200"));
        Center sender = centerRepository.getById(1);
        //Assert
        assertEquals(new BigDecimal("1000"), result.getAmountTHB());
        assertEquals(new BigDecimal("200"), result.getAmountUSD());
        assertEquals(1, result.getSender().getId());
        assertEquals(2, result.getReceiver().getId());
        assertEquals(CITStatus.GENERATED, result.getStatus());
        assertEquals(new BigDecimal("999000"), sender.getBalanceTHB());
        assertEquals(new BigDecimal("999800"), sender.getBalanceUSD());
    }

    @Test
    void receive() {
        //Arrange
        when(truckRepository.findById(5)).thenReturn(Optional.of(mockUpTruckData()));
        when(citRepository.findById(1)).thenReturn(Optional.of(mockUpCITData()));
        //Act
        CITService citService = new CITService();
        citService.setCitRepository(citRepository);
        citService.setTruckRepository(truckRepository);
        citService.setCenterRepository(centerRepository);
        citService.receive(1,5);
        CIT result = citService.getById(1);
        //Assert
        assertEquals(5, result.getDeliveryTruck().getId());
        assertEquals(CITStatus.RECEIVED, result.getStatus());

    }

    @Test
    void deliver() {
        //Arrange
        when(citRepository.findById(1)).thenReturn(Optional.of(mockUpCITData()));
        //Act
        CITService citService = new CITService();
        citService.setCitRepository(citRepository);
        citService.setTruckRepository(truckRepository);
        citService.setCenterRepository(centerRepository);
        citService.deliver(1);
        CIT result = citService.getById(1);
        //Assert
        assertEquals(CITStatus.DELIVERED, result.getStatus());
    }

    @Test
    void confirm() {
        //Arrange
        when(citRepository.findById(1)).thenReturn(Optional.of(mockUpCITData()));
        //Act
        CITService citService = new CITService();
        citService.setCitRepository(citRepository);
        citService.setTruckRepository(truckRepository);
        citService.setCenterRepository(centerRepository);
        citService.confirm(1, new BigDecimal("99"), new BigDecimal("88"));
        CIT result = citRepository.getById(1);
        //Assert
        assertEquals(CITStatus.CONFIRMED, result.getStatus());
        assertEquals(new BigDecimal("99"), result.getAmountTHBConfirmed());
        assertEquals(new BigDecimal("88"), result.getAmountUSDConfirmed());
        assertEquals(new BigDecimal("1000099"), result.getReceiver().getBalanceTHB());
        assertEquals(new BigDecimal("1000088"), result.getReceiver().getBalanceUSD());
    }
}