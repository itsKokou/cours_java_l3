package ism.repositories.impl;

import javax.persistence.EntityManager;

import ism.entities.Secteur;
import ism.repositories.SecteurRepository;

public class SecteurRepositoryImpl  extends RepositoryImpl<Secteur> implements SecteurRepository{

    public SecteurRepositoryImpl(EntityManager em) {
        super(em);
        this.type = Secteur.class;
        this.select_all="SELECT_ALL_SECTEUR";
    }
    
}
