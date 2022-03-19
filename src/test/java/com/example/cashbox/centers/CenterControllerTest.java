package com.example.cashbox.centers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CenterControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private CenterRepository centerRepository;

    @Test
    void getCenter() {
        //Arrange
        Center center1 = new Center(
                1,
                "Main center",
                "273 Samsen Rd, Wat Sam Phraya, Phra Nakhon, Bangkok 10200",
                13.768747,
                100.500471,
                1_000_000_000.00,
                20_000_000.00);
        when(centerRepository.findById(1)).thenReturn(Optional.of(center1));
        //Act
        CenterResponse response1 = testRestTemplate.getForObject("/api/center-management/center/1", CenterResponse.class);
        CenterResponse response2 = testRestTemplate.getForObject("/api/center-management/center/2", CenterResponse.class);
        //Assert
        assertEquals("Main center", response1.getName());
        assertEquals("Center ID: 2 not found.", response2.getMessage());
    }
}