package ism.services.impl;

import java.util.List;

import ism.entities.Filiere;
import ism.repositories.FiliereRepository;
import ism.services.FiliereService;

public class FiliereServiceImpl implements FiliereService{

    private FiliereRepository filiereRepository;

    public FiliereServiceImpl(FiliereRepository filiereRepository) {
        this.filiereRepository = filiereRepository;
    }

    @Override
    public void save(Filiere data) {
        this.filiereRepository.save(data);
    }

    @Override
    public List<Filiere> getAll() {
        return this.filiereRepository.findAll();
    }

    @Override
    public Filiere show(Long id) {
        return this.filiereRepository.findById(id);
    }

}
