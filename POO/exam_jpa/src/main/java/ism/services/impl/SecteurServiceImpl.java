package ism.services.impl;

import java.util.List;

import ism.entities.Secteur;
import ism.repositories.SecteurRepository;
import ism.services.SecteurService;

public class SecteurServiceImpl implements SecteurService{
    private SecteurRepository secteurRepository;
    
    public SecteurServiceImpl(SecteurRepository secteurRepository) {
        this.secteurRepository = secteurRepository;
    }

    @Override
    public void save(Secteur data) {
        this.secteurRepository.save(data);
    }

    @Override
    public List<Secteur> getAll() {
        return this.secteurRepository.findAll();
    }

    @Override
    public Secteur show(Long id) {
        return this.secteurRepository.findById(id);
    }
    
}
