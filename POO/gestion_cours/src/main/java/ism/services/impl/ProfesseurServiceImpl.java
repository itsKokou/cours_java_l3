package ism.services.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import ism.entities.ClasseEnseignee;
import ism.entities.Professeur;
import ism.entities.Module;
import ism.repositories.bd.ProfesseurRepository;
import ism.services.ProfesseurService;

public class ProfesseurServiceImpl implements ProfesseurService{
    private ProfesseurRepository professeurRepository;

    public ProfesseurServiceImpl(ProfesseurRepository professeurRepository) {
        this.professeurRepository = professeurRepository;
    }

    @Override
    public int add(Professeur data) {
        return professeurRepository.insert(data);
    }

    @Override
    public ArrayList<Professeur> getAll() {
        return professeurRepository.findAll();
    }

    @Override
    public Professeur show(int id) {
        return professeurRepository.findById(id);
    }

    @Override
    public int update(Professeur data) {
       return professeurRepository.update(data);
    }

    @Override
    public ClasseEnseignee findClasseEnseignee(int idProf, int idC) {
        return professeurRepository.findClasseEnseignee(idProf, idC);
    }

    @Override
    public int removeClasseEnseignee(ClasseEnseignee classeEnseignee) {
        return professeurRepository.removeClasseEnseignee(classeEnseignee);
    }

    @Override
    public int removeModuleEnseigne(ClasseEnseignee classeEnseignee, Module module) {
       return professeurRepository.removeModuleEnseigne(classeEnseignee, module);
    }

    @Override
    public int addClasseEnseignee(int idProf, ClasseEnseignee classe) {
        return professeurRepository.insertClasseEnseignee(idProf, classe);
    }

    @Override
    public int addModuleEnseignee(int idClasseEnseignee, Module module) {
        return professeurRepository.insertModuleEnseignee(idClasseEnseignee, module);
    }

    @Override
    public ArrayList<Module> findModulesEnseignesByClasse(int idC) {
        return professeurRepository.findModulesEnseignesByClasse(idC);
    }

    @Override
    public ArrayList<Professeur> findProfDisponibles(Module module, Date date, Time heureD, Time heureF) {
        return professeurRepository.findProfDisponibles(module, date, heureD, heureF);
    }
    
}
