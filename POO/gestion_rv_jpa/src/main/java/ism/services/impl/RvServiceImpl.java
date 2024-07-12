package ism.services.impl;

import java.util.Date;
import java.util.List;

import ism.entities.Medecin;
import ism.entities.Rv;
import ism.enums.Statut;
import ism.repositories.RvRepository;
import ism.services.RvService;

public class RvServiceImpl implements RvService{

    private RvRepository rvRepository;

    public RvServiceImpl(RvRepository rvRepository){
        this.rvRepository = rvRepository;
    }

    @Override
    public void save(Rv data) {
        rvRepository.save(data);
    }

    @Override
    public List<Rv> getAll() {
        return rvRepository.findAll();
    }

    @Override
    public Rv show(Long id) {
        return rvRepository.findById(id);
    }

    @Override
    public List<Rv> getByStatut(Statut statut) {
        return rvRepository.findByStatut(statut);
    }

    @Override
    public List<Rv> getByStatutAndDate(Statut statut, Date date) {
        return rvRepository.findByStatutAndDate(statut, date);
    }

    @Override
    public List<Rv> getByStatutAndDateAndMed(Statut statut, Date date, Medecin medecin) {
        return rvRepository.findByStatutAndDateAndMed(statut, date, medecin);
    }
    
}
