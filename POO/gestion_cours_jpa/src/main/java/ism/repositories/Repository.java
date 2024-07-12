package ism.repositories;

import java.util.List;

public interface Repository<T, ID> {
    void save (T data) ;//insert+update
    List<T>findAll ();
    T findById (ID id);   
} 

