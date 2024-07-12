package ism.services.impl;

import java.util.ArrayList;

import ism.entities.Filiere;
import ism.repositories.bd.FiliereRepository;
import ism.services.FiliereService;

public class FiliereServiceImpl implements FiliereService{

    private FiliereRepository filiereRepository;

    public FiliereServiceImpl(FiliereRepository filiereRepository) {
        this.filiereRepository = filiereRepository;
    }

    @Override
    public int add(Filiere data) {
        return filiereRepository.insert(data);
    }

    @Override
    public ArrayList<Filiere> getAll() {
        return filiereRepository.findAll();
    }

    @Override
    public Filiere show(int id) {
        return filiereRepository.findById(id);
    }

    
}
