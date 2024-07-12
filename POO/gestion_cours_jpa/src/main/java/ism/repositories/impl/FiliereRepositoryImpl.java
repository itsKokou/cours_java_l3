package ism.repositories.impl;


import javax.persistence.EntityManager;

import ism.entities.Filiere;
import ism.repositories.FiliereRepository;

public class FiliereRepositoryImpl extends RepositoryImpl<Filiere> implements FiliereRepository{

    public FiliereRepositoryImpl(EntityManager em){
        super(em);
        this.type=Filiere.class;
        this.select_all = "SELECT_ALL_F";
        this.select_by_id="SELECT_F_BY_ID";
    }
    
}
