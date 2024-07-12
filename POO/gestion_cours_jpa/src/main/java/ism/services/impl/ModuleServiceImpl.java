package ism.services.impl;


import java.util.List;

import ism.entities.Module;
import ism.repositories.ModuleRepository;
import ism.services.ModuleService;

public class ModuleServiceImpl implements ModuleService{

    private ModuleRepository moduleRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public void save(Module data) {
        this.moduleRepository.save(data);
    }

    @Override
    public List<Module> getAll() {
        return this.moduleRepository.findAll();
    }

    @Override
    public Module show(Long id) {
        return this.moduleRepository.findById(id);
    }

    
    
}
