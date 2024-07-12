package ism.services.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import ism.entities.Salle;
import ism.repositories.bd.SalleRepository;
import ism.services.SalleService;

public class SalleServiceImpl implements SalleService{
    private SalleRepository salleRepository;
    
    public SalleServiceImpl(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    @Override
    public int add(Salle data) {
        return salleRepository.insert(data);
    }

    @Override
    public ArrayList<Salle> getAll() {
        return salleRepository.findAll();
    }

    @Override
    public Salle show(int id) {
        return salleRepository.findById(id);
    }

    @Override
    public int update(Salle data) {
        return salleRepository.update(data);
    }

    @Override
    public ArrayList<Salle> findSalleDisponibles(int nbrePlace, Date date, Time heureD, Time heureF) {
        return salleRepository.findSalleDisponibles(nbrePlace, date, heureD, heureF);
    }
    
}
