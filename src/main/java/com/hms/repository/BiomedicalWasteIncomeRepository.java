package com.hms.repository;

import com.hms.model.BiomedicalWasteIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BiomedicalWasteIncomeRepository extends JpaRepository<BiomedicalWasteIncome, Long> {
    List<BiomedicalWasteIncome> findByDateBetween(LocalDate start, LocalDate end);
}
