package ism.services;

import java.util.List;

public interface Service<T> {
    void  save(T data);
    List<T> getAll();
    T show(Long id);
}
