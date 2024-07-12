package ism.repositories;

import java.util.List;

public interface Repository<T,ID> {
    List<T> findAll();
    T findById(ID id);
    void save(T data);
}
