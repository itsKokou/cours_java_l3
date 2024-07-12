package ism.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;

import ism.entities.Medecin;
import ism.repositories.MedecinRepository;

public class MedecinRepositoryImpl extends RepositoryImpl<Medecin> implements MedecinRepository{
    private String SQL_SELECT_ALL = "select m from Medecin m";

    public MedecinRepositoryImpl(EntityManager em) {
        super(em);
        type=Medecin.class;
    }

    @Override
    public List<Medecin> findAll() {
        return em.createQuery(SQL_SELECT_ALL,type).getResultList();
    }
    
}
