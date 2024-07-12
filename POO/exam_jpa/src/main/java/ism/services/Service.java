package ism.services;

import java.util.List;

public interface Service <T,ID> {
    void  save(T data);
    List<T> getAll();
    T show(ID id);
}