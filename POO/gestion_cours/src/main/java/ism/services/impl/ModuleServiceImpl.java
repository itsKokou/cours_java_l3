package ism.services.impl;

import java.util.ArrayList;

import ism.entities.Module;
import ism.repositories.bd.ModuleRepository;
import ism.services.ModuleService;

public class ModuleServiceImpl implements ModuleService{

    private ModuleRepository moduleRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public int add(Module data) {
        return moduleRepository.insert(data);
    }

    @Override
    public ArrayList<Module> getAll() {
        return moduleRepository.findAll();
    }

    @Override
    public Module show(int id) {
        return moduleRepository.findById(id);
    }

    @Override
    public int update(Module data) {
        return moduleRepository.update(data);
    }
    
}
