package rv.ism.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;

import rv.ism.entities.Medecin;
import rv.ism.repositories.BaseRepositoryImpl;
import rv.ism.repositories.MedecinRepository;

public class MedecinRepositoryImpl extends BaseRepositoryImpl<Medecin,Long>  implements MedecinRepository{
    
    private final String SQL_SELECT_ALL="select m from Medecin m ";
    public MedecinRepositoryImpl(EntityManager em) {
        super(em);
        type=Medecin.class;
        //TODO Auto-generated constructor stub
    }
    @Override
    public List<Medecin> findAll() {
      return em.createQuery(SQL_SELECT_ALL,type)
                .getResultList();
             
    }
    
}
