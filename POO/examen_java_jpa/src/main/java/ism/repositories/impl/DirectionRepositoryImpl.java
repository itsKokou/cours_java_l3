package ism.repositories.impl;


import javax.persistence.EntityManager;

import ism.entities.Direction;
import ism.repositories.DirectionRepository;

public class DirectionRepositoryImpl extends RepositoryImpl<Direction> implements DirectionRepository {

    public DirectionRepositoryImpl(EntityManager em) {
        super(em);
        super.selectAll = "ALL_DIRECTION";
        super.type = Direction.class;
    }
    
}
