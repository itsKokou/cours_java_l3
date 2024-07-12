package ism.repositories.impl;


import javax.persistence.EntityManager;

import ism.entities.Niveau;
import ism.repositories.NiveauRepository;

public class NiveauRepositoryImpl extends RepositoryImpl<Niveau> implements NiveauRepository{

    public NiveauRepositoryImpl(EntityManager em){
        super(em);
        this.type = Niveau.class;
        this.select_all = "SELECT_ALL_N";
        this.select_by_id="SELECT_N_BY_ID";
    }

}
