package rv.ism.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;

import rv.ism.entities.Patient;
import rv.ism.repositories.BaseRepositoryImpl;
import rv.ism.repositories.PatientRepository;

public class PatientRepositoryImpl extends BaseRepositoryImpl<Patient,Long>  implements PatientRepository{
    private final String SQL_SELECT_ALL="select m from Patient m where m.nomComplet like :nom";
    public PatientRepositoryImpl(EntityManager em) {
        super(em);
        type=Patient.class;
    }

    @Override
    public List<Patient> findAll() {
        return em.createQuery(SQL_SELECT_ALL,type)
        .setParameter("nom","BBW")
        .getResultList();
    }
    
}
