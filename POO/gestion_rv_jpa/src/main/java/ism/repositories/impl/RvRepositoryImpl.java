package ism.repositories.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import ism.entities.Medecin;
import ism.entities.Rv;
import ism.enums.Statut;
import ism.repositories.RvRepository;

public class RvRepositoryImpl extends RepositoryImpl<Rv> implements RvRepository {
    private String SQL_SELECT_ALL = "select r from Rv r";
    private String SQL_SELECT_BY_STATUT = "select r from Rv r where r.statut like :statut";
    private String SQL_SELECT_By_STATUT_DATE = "select r from Rv r where r.statut like :statut and r.date like :date";
    private String SQL_SELECT_By_STATUT_DATE_MEDECIN = "select r from Rv r where r.statut like :statut and r.date like :date and r.medecin_id =:id";

    public RvRepositoryImpl(EntityManager em) {
        super(em);
        type = Rv.class;
    }

    @Override
    public List<Rv> findAll() {
        return em.createQuery(SQL_SELECT_ALL,type).getResultList();
    }

    @Override
    public List<Rv> findByStatut(Statut statut) {
        return em.createQuery(SQL_SELECT_BY_STATUT,type)
        .setParameter("statut", statut)
        .getResultList();
    }

    @Override
    public List<Rv> findByStatutAndDate(Statut statut, Date date) {
        return em.createQuery(SQL_SELECT_By_STATUT_DATE,type)
        .setParameter("statut", statut)
        .setParameter("date", date)
        .getResultList();
    }

    @Override
    public List<Rv> findByStatutAndDateAndMed(Statut statut, Date date, Medecin medecin) {
        return em.createQuery(SQL_SELECT_By_STATUT_DATE_MEDECIN,type)
        .setParameter("statut", statut)
        .setParameter("date", date)
        .setParameter("id", medecin.getId())
        .getResultList();
    }
    
}
