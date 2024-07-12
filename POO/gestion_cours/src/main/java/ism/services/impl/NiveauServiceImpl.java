package ism.services.impl;

import java.util.ArrayList;

import ism.entities.Niveau;
import ism.repositories.bd.NiveauRepository;
import ism.services.NiveauService;

public class NiveauServiceImpl implements NiveauService{

    private NiveauRepository niveauRepository;

    public NiveauServiceImpl(NiveauRepository niveauRepository) {
        this.niveauRepository = niveauRepository;
    }

    @Override
    public int add(Niveau data) {
        return niveauRepository.insert(data);
    }

    @Override
    public ArrayList<Niveau> getAll() {
        return niveauRepository.findAll();
    }

    @Override
    public Niveau show(int id) {
        return niveauRepository.findById(id);
    }
    
}
