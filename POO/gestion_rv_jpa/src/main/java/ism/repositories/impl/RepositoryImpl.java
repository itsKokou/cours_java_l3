package ism.repositories.impl;

import javax.persistence.EntityManager;

import ism.repositories.Repository;

public abstract class RepositoryImpl<T> implements Repository<T,Long> {
    protected EntityManager em;
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
    public void remove(Long id) {
        em.getTransaction().begin();
        em.remove(findById(id));
        em.getTransaction().commit();
    }

    @Override
    public T findById(Long id) {
        return em.find(type, id);
    }
    
}
