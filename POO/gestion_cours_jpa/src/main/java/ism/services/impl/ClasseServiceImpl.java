package ism.services.impl;


import java.util.Date;
import java.time.LocalTime;
import java.util.List;

import ism.entities.Classe;
import ism.entities.Module;
import ism.entities.Professeur;
import ism.repositories.ClasseRepository;
import ism.services.ClasseService;

public class ClasseServiceImpl implements ClasseService{
    private ClasseRepository classeRepository;

    public ClasseServiceImpl(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    @Override
    public void save(Classe data) {
        this.classeRepository.save(data);
    }

    @Override
    public List<Classe> getAll() {
        return this.classeRepository.findAll();
    }

    @Override
    public Classe show(Long id) {
        return this.classeRepository.findById(id);
    }

    @Override
    public List<Classe> findClasseDisponibles(Module module, Professeur prof, Date date, LocalTime heureD,
            LocalTime heureF) {
        return this.classeRepository.findClasseDisponibles(module, prof, date, heureD, heureF);
    }

    

}
