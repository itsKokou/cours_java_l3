package ism.services.impl;

import java.util.ArrayList;

import ism.entities.Cours;
import ism.repositories.bd.CoursRepository;
import ism.services.CoursService;

public class CoursServiceImpl implements CoursService{
    private CoursRepository coursRepository;

    public CoursServiceImpl(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }

    @Override
    public int add(Cours data) {
        return coursRepository.insert(data);
    }

    @Override
    public ArrayList<Cours> getAll() {
        return coursRepository.findAll();
    }

    @Override
    public Cours show(int id) {
        return coursRepository.findById(id);
    }

}
