package ism.repositories.impl;

import javax.persistence.EntityManager;

import ism.entities.Direction;
import ism.repositories.DirectionRepository;

public class DirectionRepositoryImpl extends RepositoryImpl<Direction> implements DirectionRepository {

    public DirectionRepositoryImpl(EntityManager em) {
        super(em);
        this.type = Direction.class;
        this.select_all="SELECT_ALL_DIRECTION";
    }
    
}
