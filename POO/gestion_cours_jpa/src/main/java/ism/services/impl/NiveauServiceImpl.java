package ism.services.impl;


import java.util.List;

import ism.entities.Niveau;
import ism.repositories.NiveauRepository;
import ism.services.NiveauService;

public class NiveauServiceImpl implements NiveauService{

    private NiveauRepository niveauRepository;

    public NiveauServiceImpl(NiveauRepository niveauRepository) {
        this.niveauRepository = niveauRepository;
    }

    @Override
    public void save(Niveau data) {
        this.niveauRepository.save(data);
    }

    @Override
    public List<Niveau> getAll() {
        return this.niveauRepository.findAll();
    }

    @Override
    public Niveau show(Long id) {
        return this.niveauRepository.findById(id);
    }

    
    
}
