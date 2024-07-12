package ism.services.impl;

import java.util.Date;
import java.time.LocalTime;
import java.util.List;

import ism.entities.Salle;
import ism.repositories.SalleRepository;
import ism.services.SalleService;

public class SalleServiceImpl implements SalleService{
    private SalleRepository salleRepository;
    
    public SalleServiceImpl(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }


    @Override
    public void save(Salle data) {
        this.salleRepository.save(data);
    }

    @Override
    public Salle show(Long id) {
        return this.salleRepository.findById(id);
    }

    @Override
    public List<Salle> getAll() {
        return this.salleRepository.findAll();
    }

    @Override
    public List<Salle> findSalleDisponibles(Long nbrePlace, Date date, LocalTime heureD, LocalTime heureF) {
        return salleRepository.findSalleDisponibles(nbrePlace, date, heureD, heureF);
    }
    
}
