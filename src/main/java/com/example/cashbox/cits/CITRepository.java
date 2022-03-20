package com.example.cashbox.cits;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CITRepository extends JpaRepository<CIT, Integer> {
    List<CIT> findByLastUpdatedBetween(LocalDateTime from,LocalDateTime to);
}
