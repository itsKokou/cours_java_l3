package ism.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import ism.repositories.Repository;

public class RepositoryImpl<T> implements Repository<T,Long> {
    protected EntityManager em;
    protected String select_all;
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
    public List<T> findAll() {
        return em.createNamedQuery(select_all,type)
        .getResultList();
    }

    @Override
    public T findById(Long id) {
        try {
            return em.find(type, id);
        } catch (Exception e) {
            return null;
        }
    }

    
    
}
