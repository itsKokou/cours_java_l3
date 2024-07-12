package ism.services.impl;

import java.util.List;

import ism.entities.Direction;
import ism.repositories.DirectionRepository;
import ism.services.DirectionService;

public class DirectionServiceImpl implements DirectionService{
    private DirectionRepository directionRepository;

    public DirectionServiceImpl(DirectionRepository directionRepository) {
        this.directionRepository = directionRepository;
    }

    @Override
    public void save(Direction data) {
        this.directionRepository.save(data);
    }

    @Override
    public List<Direction> getAll() {
        return this.directionRepository.findAll();
    }

    @Override
    public Direction show(Long id) {
        return this.directionRepository.findById(id);
    }


}
