package com.example.cashbox.centers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CenterServiceTest {

    @Mock
    private CenterRepository centerRepository;

    private Center mockUpCenterData() {
        return new Center(
                1,
                "Main center",
                "273 Samsen Rd, Wat Sam Phraya, Phra Nakhon, Bangkok 10200",
                13.768747,
                100.500471,
                new BigDecimal("1_000_000_000.00"),
                new BigDecimal( "20_000_000.00"));
    }

    @Test
    void getById() {
        //Arrange
        when(centerRepository.findById(1)).thenReturn(Optional.of(mockUpCenterData()));
        //Act
        CenterService centerService = new CenterService();
        centerService.setCenterRepository(centerRepository);
        Center result = centerService.getById(1);
        //Assert
        assertEquals("Main center", result.getName());
        assertEquals(13.768747, result.getLatitude());
        assertEquals(new BigDecimal("1_000_000_000.00"), result.getBalanceTHB());
    }

    @Test
    void setBalance() {
        //Arrange
        when(centerRepository.findById(1)).thenReturn(Optional.of(mockUpCenterData()));
        //Act
        CenterService centerService = new CenterService();
        centerService.setCenterRepository(centerRepository);
        centerService.setBalance(1,Currency.THB, new BigDecimal("500_000_000.00"));
        centerService.setBalance(1,Currency.USD, new BigDecimal("4_000_000.00"));
        Center result = centerService.getById(1);
        //Assert
        assertEquals(new BigDecimal("500_000_000.00"), result.getBalanceTHB());
        assertEquals(new BigDecimal("4_000_000.00"), result.getBalanceUSD());

    }
}