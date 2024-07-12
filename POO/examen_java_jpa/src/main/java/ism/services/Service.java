package ism.services;

import java.util.List;

public interface Service<T,ID>  {
    List<T> getAll();
    T show(ID id);
    void save(T data);
}
