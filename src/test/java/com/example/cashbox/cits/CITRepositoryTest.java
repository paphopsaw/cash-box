package com.example.cashbox.cits;

import com.example.cashbox.centers.Center;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CITRepositoryTest {

    @Autowired
    private CITRepository citRepository;

    @PostConstruct
    void initializeData() {
        Center center1 = new Center();
        center1.setId(1);
        Center center2 = new Center();
        center2.setId(2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        CIT cit1 = new CIT();
        cit1.setId(1);
        cit1.setSender(center1);
        cit1.setReceiver(center2);
        cit1.setLastUpdated(LocalDateTime.parse("2022-03-20 18:12:30", formatter));
        citRepository.save(cit1);

        CIT cit2 = new CIT();
        cit2.setId(2);
        cit2.setSender(center1);
        cit2.setReceiver(center2);
        cit2.setLastUpdated(LocalDateTime.parse("2022-03-20 23:59:59", formatter));
        citRepository.save(cit2);

        CIT cit3 = new CIT();
        cit3.setId(3);
        cit3.setSender(center1);
        cit3.setReceiver(center2);
        cit3.setLastUpdated(LocalDateTime.parse("2022-03-20 00:00:00", formatter));
        citRepository.save(cit3);

        CIT cit4 = new CIT();
        cit4.setId(4);
        cit4.setSender(center1);
        cit4.setReceiver(center2);
        cit4.setLastUpdated(LocalDateTime.parse("2022-03-19 18:12:13", formatter));
        citRepository.save(cit4);
    }

    @Test
    void findByLastUpdatedBetween() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<CIT> result1 = citRepository.findByLastUpdatedBetween(
                LocalDateTime.parse("2022-03-20 00:00:00", formatter),
                LocalDateTime.parse("2022-03-21 00:00:00", formatter)
        );
        List<CIT> result2 = citRepository.findByLastUpdatedBetween(
                LocalDateTime.parse("2022-03-19 00:00:00", formatter),
                LocalDateTime.parse("2022-03-20 00:00:00", formatter)
        );
        List<CIT> result3 = citRepository.findByLastUpdatedBetween(
                LocalDateTime.parse("2022-03-19 00:00:00", formatter),
                LocalDateTime.parse("2022-03-19 19:00:00", formatter)
        );
        assertEquals(3, result1.size());
        assertEquals(2, result2.size());
        assertEquals(1, result3.size());
        assertEquals(13, result3.get(0).getLastUpdated().getSecond());
    }
}