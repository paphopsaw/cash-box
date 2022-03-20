package com.example.cashbox.cits;

import com.example.cashbox.MessageResponse;
import com.example.cashbox.centers.Center;
import com.example.cashbox.centers.CenterRepository;
import com.example.cashbox.trucks.Truck;
import com.example.cashbox.trucks.TruckRepository;
import com.example.cashbox.trucks.TruckResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CITControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private CITRepository citRepository;

    @MockBean
    private CenterRepository centerRepository;

    @MockBean
    private TruckRepository truckRepository;

    private CIT mockUpCITData(int id) {
        Center center1 = mockUpCenterData(1);
        Center center2 = mockUpCenterData(2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        CIT cit = new CIT();
        cit.setId(id);
        cit.setSender(center1);
        cit.setReceiver(center2);
        cit.setAmountTHB(new BigDecimal("100"));
        cit.setAmountUSD(new BigDecimal("10"));
        cit.setStatus(CITStatus.GENERATED);
        cit.setLastUpdated(LocalDateTime.parse("2022-03-20 18:12:30", formatter));
        return cit;
    }

    private Center mockUpCenterData(int id) {
        Center center = new Center();
        center.setId(id);
        center.setAddress("Address " + id);
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        CIT cit1 = mockUpCITData(1);
        cit1.setLastUpdated(LocalDateTime.parse("2022-03-20 18:12:30", formatter));

        CIT cit2 = mockUpCITData(2);
        cit2.setLastUpdated(LocalDateTime.parse("2022-03-20 23:59:59", formatter));

        List<CIT> citList = new ArrayList<>();
        citList.add(cit1);
        citList.add(cit2);
        return citList;
    }

    @Test
    void getCIT() {
        //Arrange
        when(citRepository.findById(1)).thenReturn(Optional.of(mockUpCITData(1)));
        //Act
        CITResponse response1 = testRestTemplate.getForObject("/api/delivery-management/cit/1", CITResponse.class);
        MessageResponse response2 = testRestTemplate.getForObject("/api/delivery-management/cit/5", MessageResponse.class);
        //Assert
        assertEquals(12, response1.getLastUpdated().getMinute());
        assertEquals("CIT ID: 5 not found.", response2.getMessage());
    }

    @Test
    void getCITLabel() {
        //Arrange
        when(citRepository.findById(1)).thenReturn(Optional.of(mockUpCITData(1)));
        //Act
        CITLabelResponse response1 = testRestTemplate.getForObject("/api/delivery-management/cit/1/label", CITLabelResponse.class);
        MessageResponse response2 = testRestTemplate.getForObject("/api/delivery-management/cit/5/label", MessageResponse.class);
        //Assert
        assertEquals("Address 1", response1.getSenderAddress());
        assertEquals("Address 2", response1.getReceiverAddress());
        assertEquals("CIT ID: 5 not found.", response2.getMessage());
    }

    @Test
    void getCITsByDate() {
        //Arrange
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime from = LocalDateTime.parse("2022-03-20 00:00:00", formatter);
        LocalDateTime to = LocalDateTime.parse("2022-03-21 00:00:00", formatter);
        when(citRepository.findByLastUpdatedBetween(from, to)).thenReturn(mockUpCITListData());
        //Act
        CITListResponse response1 = testRestTemplate.getForObject("/api/delivery-management/cits/2022/03/20", CITListResponse.class);
        //Assert
        assertEquals(2, response1.getCitList().size());
    }

    @Test
    void createCIT() {
        //Arrange
        when(centerRepository.findById(1)).thenReturn(Optional.of(mockUpCenterData(1)));
        when(centerRepository.findById(2)).thenReturn(Optional.of(mockUpCenterData(2)));
        String jsonString = "{\n" +
                "    \"senderId\": 1,\n" +
                "    \"receiverId\": 2,\n" +
                "    \"amountTHB\": 100,\n" +
                "    \"amountUSD\": 200\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
        //Act
        MessageResponse response = testRestTemplate.postForObject("/api/delivery-management/cit", entity, MessageResponse.class);
        //Assert
        assertEquals("New CIT created ID: 1", response.getMessage());

    }

    @Test
    void receive() {
        //Arrange
        when(citRepository.findById(1)).thenReturn(Optional.of(mockUpCITData(1)));
        when(truckRepository.findById(5)).thenReturn(Optional.of(mockUpTruckData()));
        String jsonString = "{\n" +
                "    \"truckId\": 5\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
        //Act
        MessageResponse response1 = testRestTemplate.postForObject("/api/delivery-management/cit/1/receive", entity, MessageResponse.class);
        MessageResponse response2 = testRestTemplate.postForObject("/api/delivery-management/cit/5/receive", entity, MessageResponse.class);
        CITResponse response3 = testRestTemplate.getForObject("/api/delivery-management/cit/1", CITResponse.class);
        //Assert
        assertEquals("CIT updated to RECEIVED.", response1.getMessage());
        assertEquals("CIT ID: 5 not found.", response2.getMessage());
        assertEquals("received", response3.getStatus());
    }

    @Test
    void deliver() {
        //Arrange
        when(citRepository.findById(1)).thenReturn(Optional.of(mockUpCITData(1)));
        when(truckRepository.findById(5)).thenReturn(Optional.of(mockUpTruckData()));
        String jsonString = "";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
        //Act
        MessageResponse response1 = testRestTemplate.postForObject("/api/delivery-management/cit/1/deliver", entity, MessageResponse.class);
        MessageResponse response2 = testRestTemplate.postForObject("/api/delivery-management/cit/5/deliver", entity, MessageResponse.class);
        CITResponse response3 = testRestTemplate.getForObject("/api/delivery-management/cit/1", CITResponse.class);
        //Assert
        assertEquals("CIT updated to DELIVERED.", response1.getMessage());
        assertEquals("CIT ID: 5 not found.", response2.getMessage());
        assertEquals("delivered", response3.getStatus());
    }

    @Test
    void confirm() {
        //Arrange
        when(citRepository.findById(1)).thenReturn(Optional.of(mockUpCITData(1)));
        when(truckRepository.findById(5)).thenReturn(Optional.of(mockUpTruckData()));
        String jsonString = "{\n" +
                "    \"amountTHB\": 99,\n" +
                "    \"amountUSD\": 199\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
        //Act
        MessageResponse response1 = testRestTemplate.postForObject("/api/delivery-management/cit/1/confirm", entity, MessageResponse.class);
        MessageResponse response2 = testRestTemplate.postForObject("/api/delivery-management/cit/5/confirm", entity, MessageResponse.class);
        CITResponse response3 = testRestTemplate.getForObject("/api/delivery-management/cit/1", CITResponse.class);
        //Assert
        assertEquals("CIT updated to CONFIRMED.", response1.getMessage());
        assertEquals("CIT ID: 5 not found.", response2.getMessage());
        assertEquals("confirmed", response3.getStatus());
    }
}