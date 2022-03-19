package com.example.cashbox.trucks;

import com.example.cashbox.centers.Center;
import com.example.cashbox.centers.CenterService;
import com.example.cashbox.centers.Currency;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TruckServiceTest {

    @Mock
    private TruckRepository truckRepository;

    private Truck mockUpTruckData() {
        Truck truck1 = new Truck(
                1,
                13.73811900540664,
                100.49090925318582
        );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2022-03-18 23:30:10", formatter);
        truck1.setLastUpdated(dateTime);
        return truck1;
    }

    private List<Truck> mockUpTruckListData() {
        Truck truck1 = new Truck(
                1,
                13.73811900540664,
                100.49090925318582
        );
        Truck truck2 = new Truck(
                2,
                13.745664098170202,
                100.48543726905037
        );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2022-03-18 23:30:10", formatter);
        truck1.setLastUpdated(dateTime);
        truck2.setLastUpdated(dateTime);

        List<Truck> truckList = new ArrayList<>();
        truckList.add(truck1);
        truckList.add(truck2);
        return truckList;
    }

    @Test
    void getById() {
        //Arrange
        when(truckRepository.findById(1)).thenReturn(Optional.of(mockUpTruckData()));
        //Act
        TruckService truckService = new TruckService();
        truckService.setTruckRepository(truckRepository);
        Truck result = truckService.getById(1);
        //Assert
        assertEquals(13.73811900540664, result.getLatitude());
        assertEquals(100.49090925318582, result.getLongitude());
        assertEquals(2022, result.getLastUpdated().getYear());
        assertEquals(23, result.getLastUpdated().getHour());
    }

    @Test
    void getAll() {
        //Arrange
        when(truckRepository.findAll()).thenReturn(mockUpTruckListData());
        //Act
        TruckService truckService = new TruckService();
        truckService.setTruckRepository(truckRepository);
        List<Truck> result = truckService.getAll();
        //Assert
        assertEquals(13.73811900540664, result.get(0).getLatitude());
        assertEquals(100.49090925318582, result.get(0).getLongitude());
        assertEquals(2022, result.get(0).getLastUpdated().getYear());
        assertEquals(23, result.get(0).getLastUpdated().getHour());
        assertEquals(13.745664098170202, result.get(1).getLatitude());
        assertEquals(100.48543726905037, result.get(1).getLongitude());
        assertEquals(2022, result.get(1).getLastUpdated().getYear());
        assertEquals(23, result.get(1).getLastUpdated().getHour());
        assertEquals(2, result.size());
    }

    @Test
    void updateLocation() {
        //Arrange
        when(truckRepository.findById(1)).thenReturn(Optional.of(mockUpTruckData()));
        //Act
        TruckService truckService  = new TruckService();
        truckService.setTruckRepository(truckRepository);
        TruckUpdateLocationRequest truckUpdateLocationRequest = new TruckUpdateLocationRequest(
                10.0,
                110.0,
                "2022-03-20 01:39:05"
        );
        truckService.updateLocation(1, truckUpdateLocationRequest);
        Truck result = truckService.getById(1);
        //Assert
        assertEquals(10.0, result.getLatitude());
        assertEquals(110.0, result.getLongitude());
        assertEquals(20, result.getLastUpdated().getDayOfMonth());
        assertEquals(39, result.getLastUpdated().getMinute());
        assertEquals(5, result.getLastUpdated().getSecond());
    }
}