package ism.services.impl;

import java.sql.Date;
import java.util.ArrayList;

import ism.entites.Medecin;
import ism.entites.Rv;
import ism.repositories.bd.RvRepository;
import ism.services.RvService;

public class RvServiceImpl implements RvService{
    private RvRepository rvRepository;

    public RvServiceImpl(RvRepository rvRepository){
        this.rvRepository = rvRepository;
    }

    @Override
    public int add(Rv data) {
        return rvRepository.insert(data);
    }

    @Override
    public ArrayList<Rv> getAll() {
        return rvRepository.findAll();
    }

    @Override
    public Rv show(int id) {
        return rvRepository.findById(id);
    }

    @Override
    public int update(Rv data) {
       return rvRepository.update(data);
    }

    @Override
    public ArrayList<Rv> getByStatut(String statut) {
        return rvRepository.findByStatut(statut);
    }

    @Override
    public ArrayList<Rv> getByStatutAndDate(String statut, Date date) {
        return rvRepository.findByStatutAndDate(statut, date);
    }

    @Override
    public ArrayList<Rv> getByStatutAndDateAndMed(String statut, Date date, Medecin medecin) {
        return rvRepository.findByStatutAndDateAndMed(statut, date, medecin);
    }
    
}
