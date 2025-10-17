package com.hms.repository;

import com.hms.model.BiomedicalWaste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiomedicalWasteRepository extends JpaRepository<BiomedicalWaste, Long> {
}
