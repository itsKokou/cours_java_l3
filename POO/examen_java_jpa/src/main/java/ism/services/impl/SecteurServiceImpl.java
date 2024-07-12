package ism.services.impl;

import java.util.List;

import ism.entities.Secteur;
import ism.repositories.SecteurRepository;
import ism.services.SecteurService;

public class SecteurServiceImpl implements SecteurService {

    private SecteurRepository secteurRepository;

    public SecteurServiceImpl(SecteurRepository secteurRepository) {
        this.secteurRepository = secteurRepository;
    }

    @Override
    public List<Secteur> getAll() {
        return secteurRepository.findAll();
    }

    @Override
    public Secteur show(Long id) {
        return secteurRepository.findById(id);
    }

    @Override
    public void save(Secteur data) {
        secteurRepository.save(data);
    }
    
}
