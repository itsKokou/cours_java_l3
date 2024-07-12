package ism.services.impl;

import java.util.List;

import ism.entities.Cours;
import ism.repositories.CoursRepository;
import ism.services.CoursService;

public class CoursServiceImpl implements CoursService{
    private CoursRepository coursRepository;

    public CoursServiceImpl(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }

    @Override
    public void save(Cours data) {
        this.coursRepository.save(data);
    }

    @Override
    public List<Cours> getAll() {
        return this.coursRepository.findAll();
    }

    @Override
    public Cours show(Long id) {
        return this.coursRepository.findById(id);
    }

}
