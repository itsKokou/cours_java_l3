package ism.services.impl;

import java.util.List;

import ism.entities.Personne;
import ism.repositories.Repository;
import ism.services.PersonneService;

public class PersonneServiceImpl<T extends Personne> implements PersonneService<T>{
    private Repository<T,Long> repository;

    public PersonneServiceImpl(Repository<T,Long> repository){
        this.repository = repository;
    }

    @Override
    public void save(T data) {
        repository.save(data);
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public T show(Long id) {
       return repository.findById(id);
    }
    
}
