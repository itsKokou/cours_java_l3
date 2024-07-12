package ism.repositories;

import java.util.List;

public interface Repository<T,ID> {
    void save(T entity);// insert ou update
    void remove(ID id);
    List<T> findAll();
    T findById(ID id);
}
