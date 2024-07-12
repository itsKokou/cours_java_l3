package ism.services;

import java.util.ArrayList;

public interface Service<T> {
    int  add(T data);
    ArrayList<T> getAll();
    T show(int id);
}
