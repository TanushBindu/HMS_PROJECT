package com.hms.service;

import com.hms.model.Module;
import com.hms.repository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> findAll() {
        return moduleRepository.findAll();
    }
}
