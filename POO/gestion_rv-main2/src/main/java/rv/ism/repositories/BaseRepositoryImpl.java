package rv.ism.repositories;

import java.util.List;

import javax.persistence.EntityManager;

public abstract class BaseRepositoryImpl<T,Long>  implements BaseRepository<T,Long>{
    protected EntityManager em;
    protected Class<T> type;
    public BaseRepositoryImpl(EntityManager em) {
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
        System.out.println(id);
        return em.find(type,id);
    }
    
}
