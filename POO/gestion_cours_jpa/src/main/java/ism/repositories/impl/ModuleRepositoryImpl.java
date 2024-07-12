package ism.repositories.impl;

import javax.persistence.EntityManager;

import ism.entities.Module;
import ism.repositories.ModuleRepository;

public class ModuleRepositoryImpl extends RepositoryImpl<Module> implements ModuleRepository{
    
    public ModuleRepositoryImpl(EntityManager em){
        super(em);
        this.type = Module.class;
        this.select_all = "SELECT_ALL_M";
        this.select_by_id="SELECT_M_BY_ID";
    }

   
}
