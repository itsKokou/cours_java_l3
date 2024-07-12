package ism.services.impl;


import java.util.Date;
import java.time.LocalTime;
import java.util.List;

import ism.entities.Professeur;
import ism.repositories.ProfesseurRepository;
import ism.entities.ClasseEnseignee;
import ism.entities.Module;
import ism.services.ProfesseurService;

public class ProfesseurServiceImpl implements ProfesseurService{
    private ProfesseurRepository professeurRepository;

    public ProfesseurServiceImpl(ProfesseurRepository professeurRepository) {
        this.professeurRepository = professeurRepository;
    }

    @Override
    public void save(Professeur data) {
        this.professeurRepository.save(data);
    }

    @Override
    public List<Professeur> getAll() {
        return this.professeurRepository.findAll();
    }

    @Override
    public Professeur show(Long id) {
        return this.professeurRepository.findById(id);
    }

    @Override
    public List<Module> findModulesEnseignesByClasse(Long idC) {
       return professeurRepository.findModulesEnseignesByClasse(idC);
    }

    @Override
    public List<Professeur> findProfDisponibles(Module module, Date date, LocalTime heureD,LocalTime heureF) {
        return professeurRepository.findProfDisponibles(module, date, heureD, heureF);
    }

    @Override
    public ClasseEnseignee findClasseEnseignee(Long idProf, Long idC) {
        return professeurRepository.findClasseEnseignee(idProf, idC);
    }

    
    
}
