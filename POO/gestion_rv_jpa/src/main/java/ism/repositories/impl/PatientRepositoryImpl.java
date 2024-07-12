package ism.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;

import ism.entities.Patient;
import ism.repositories.PatientRepository;

public class PatientRepositoryImpl extends RepositoryImpl<Patient> implements PatientRepository {
    private String SQL_SELECT_ALL = "select p from Patient p";

    public PatientRepositoryImpl(EntityManager em) {
        super(em);
        type=Patient.class;
    }

    @Override
    public List<Patient> findAll() {
        return em.createQuery(SQL_SELECT_ALL,type).getResultList();
    }
    
}
