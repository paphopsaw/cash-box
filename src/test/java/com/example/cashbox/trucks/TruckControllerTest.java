package com.example.cashbox.trucks;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TruckControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
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
    void getTruck() {
        //Arrange
        when(truckRepository.findById(1)).thenReturn(Optional.of(mockUpTruckData()));
        //Act
        TruckResponse response1 = testRestTemplate.getForObject("/api/truck-management/truck/1", TruckResponse.class);
        TruckResponse response2 = testRestTemplate.getForObject("/api/truck-management/truck/5", TruckResponse.class);
        //Assert
        assertEquals(13.73811900540664, response1.getLatitude());
        assertEquals("Truck ID: 5 not found.", response2.getMessage());
    }

    @Test
    void getTrucks() {
        //Arrange
        when(truckRepository.findAll()).thenReturn(mockUpTruckListData());
        //Act
        TruckListResponse response1 = testRestTemplate.getForObject("/api/truck-management/trucks", TruckListResponse.class);
        //Assert
        assertEquals(13.73811900540664, response1.getTruckList().get(0).getLatitude());
        assertEquals(13.745664098170202, response1.getTruckList().get(1).getLatitude());
        assertEquals(2, response1.getTruckList().size());
    }

    @Test
    void updateLocation() {
        //Arrange
        when(truckRepository.findById(1)).thenReturn(Optional.of(mockUpTruckData()));
        String jsonString = "{\n" +
                "    \"latitude\": 40.00,\n" +
                "    \"longitude\": 100.00,\n" +
                "    \"dateTimeString\": \"2022-03-20 01:18:11\"\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
        //Act
        TruckResponse response1 = testRestTemplate.postForObject("/api/truck-management/truck/1/updateLocation", entity, TruckResponse.class);
        TruckResponse response2 = testRestTemplate.postForObject("/api/truck-management/truck/5/updateLocation", entity, TruckResponse.class);
        TruckResponse response3 = testRestTemplate.getForObject("/api/truck-management/truck/1", TruckResponse.class);
        //Assert
        assertEquals("Location updated.", response1.getMessage());
        assertEquals("Truck ID: 5 not found.", response2.getMessage());
        assertEquals(40.0, response3.getLatitude());
        assertEquals(100.00, response3.getLongitude());
        assertEquals(11, response3.getLastUpdate().getSecond());

    }
}