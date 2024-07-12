package ism.repositories.impl;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import ism.entities.Cours;
import ism.repositories.Repository;

public class RepositoryImpl<T> implements Repository<T,Long> {
    protected EntityManager em;
    protected String select_all;
    protected String select_by_id;
    protected Class<T> type;

    public RepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }


    @Override
    public T findById(Long id) {
        try {
            return em.createNamedQuery(select_by_id, type)
            .setParameter("isArchived", false)
            .setParameter("id", id)
            .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<T> findAll() {
        return em.createNamedQuery(select_all,type)
        .setParameter("isArchived", false)
        .getResultList();
    }

    public int disponible(List<Cours> courss, Date date, LocalTime heureD,LocalTime heureF){
        int isDisponible =1;
        for (Cours cours : courss) {
            if (cours.getDate().equals(date)) {
                if(cours.getHeureD().isBefore(heureF) && heureF.isBefore(cours.getHeureF())){
                    isDisponible=0;
                }else{
                    if (cours.getHeureD().isBefore(heureD) && heureD.isBefore(cours.getHeureF())) {
                        isDisponible=0;
                    } else {
                        if (heureD.isBefore(cours.getHeureD()) && cours.getHeureD().isBefore(cours.getHeureF()) && cours.getHeureF().isBefore(heureF)) {
                            isDisponible=0;
                        } else {
                            if (cours.getHeureD().isBefore(heureD) && heureD.isBefore(heureF) && heureF.isBefore(cours.getHeureF())) {
                                isDisponible=0;
                            }else{
                                if (cours.getHeureD().equals(heureD) && heureF.equals(cours.getHeureF())) {
                                    isDisponible=0;
                                }
                            }
                        }
                    }
                } 
            }
        }
        return isDisponible;    
    }
    
    
}
