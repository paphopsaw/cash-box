package com.example.cashbox.trucks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class TruckService {

    @Autowired
    private TruckRepository truckRepository;

    public void setTruckRepository(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public Truck getById(int id) {
        Optional<Truck> result = truckRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new TruckNotFoundException(id);
    }

    public List<Truck> getAll() {
        return truckRepository.findAll();
    }

    public void updateLocation(int id, TruckUpdateLocationRequest truckUpdateLocationRequest) {
        Optional<Truck> result = truckRepository.findById(id);
        if (result.isPresent()) {
            Truck truck = result.get();
            truck.setLatitude(truckUpdateLocationRequest.getLatitude());
            truck.setLongitude(truckUpdateLocationRequest.getLongitude());
            truck.setLastUpdated(parseDateTime(truckUpdateLocationRequest.getDateTimeString()));
            truckRepository.save(truck);
        } else {
            throw new TruckNotFoundException(id);
        }
    }

    private LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
