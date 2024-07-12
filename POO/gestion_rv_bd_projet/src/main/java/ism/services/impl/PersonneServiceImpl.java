package ism.services.impl;

import java.util.ArrayList;

import ism.entites.Personne;
import ism.repositories.bd.Repository;
import ism.services.PersonneService;

public class PersonneServiceImpl<T extends Personne> implements PersonneService<T>{
    private Repository<T> repository;

    public PersonneServiceImpl(Repository<T> repository){
        this.repository = repository;
    }

    @Override
    public int add(T data) {
        return repository.insert(data);
    }

    @Override
    public ArrayList<T> getAll() {
        return repository.findAll();
    }

    @Override
    public T show(int id) {
        return repository.findById(id);
    }
    
}
