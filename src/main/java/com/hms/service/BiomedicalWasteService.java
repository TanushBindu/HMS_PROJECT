package com.hms.service;

import com.hms.model.BiomedicalWaste;
import com.hms.repository.BiomedicalWasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BiomedicalWasteService {

    private final BiomedicalWasteRepository repository;

    public BiomedicalWasteService(BiomedicalWasteRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private BiomedicalWasteRepository biomedicalWasteRepository;

    public List<BiomedicalWaste> getAllWaste() {
        return biomedicalWasteRepository.findAll();
    }

    public BiomedicalWaste getById(Long id) {
        return biomedicalWasteRepository.findById(id).orElse(null);
    }

    public void saveWaste(BiomedicalWaste waste) {
        biomedicalWasteRepository.save(waste);
    }

    public void deleteWaste(Long id) {
        biomedicalWasteRepository.deleteById(id);
    }
}
