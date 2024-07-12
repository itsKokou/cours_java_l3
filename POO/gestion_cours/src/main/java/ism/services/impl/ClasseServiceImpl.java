package ism.services.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import ism.entities.Module;
import ism.entities.Classe;
import ism.entities.Professeur;
import ism.repositories.bd.ClasseRepository;
import ism.services.ClasseService;

public class ClasseServiceImpl implements ClasseService{
    private ClasseRepository classeRepository;

    public ClasseServiceImpl(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    @Override
    public int add(Classe data) {
        return classeRepository.insert(data);
    }

    @Override
    public ArrayList<Classe> getAll() {
        return classeRepository.findAll();
    }

    @Override
    public Classe show(int id) {
        return classeRepository.findById(id);
    }

    @Override
    public int update(Classe data) {
        return classeRepository.update(data);
    }

    @Override
    public int removeModule(int idClasse, int idModule) {
        return classeRepository.removeModule(idClasse, idModule);
    }

    @Override
    public int addModule(int idClasse, int idModule) {
        return classeRepository.addModule(idClasse, idModule);
    }

    @Override
    public ArrayList<Classe> findClassesByModule(int idModule) {
        return classeRepository.findClassesByModule(idModule);
    }

    @Override
    public ArrayList<Classe> findClasseDisponibles(Module module, Professeur prof, Date date, Time heureD,
            Time heureF) {
        return classeRepository.findClasseDisponibles(module, prof, date, heureD, heureF);
    }

}
