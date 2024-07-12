package ism.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import ism.repositories.Repository;

public class RepositoryImpl<T> implements Repository<T,Long> {
    protected EntityManager em;
    protected Class<T> type;
    protected String selectAll;

    public RepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<T> findAll() {
        return em.createNamedQuery(selectAll, type).getResultList();
    }

    @Override
    public T findById(Long id) {
        try {
            return em.find(type, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void save(T data) {
        em.getTransaction().begin();
            em.persist(data);
        em.getTransaction().commit();
    }
    
}
