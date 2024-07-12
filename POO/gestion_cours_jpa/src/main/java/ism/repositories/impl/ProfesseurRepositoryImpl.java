package ism.repositories.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import ism.entities.ClasseEnseignee;
import ism.entities.Module;
import ism.entities.Professeur;
import ism.repositories.ProfesseurRepository;

public class ProfesseurRepositoryImpl extends RepositoryImpl<Professeur> implements ProfesseurRepository {

    public ProfesseurRepositoryImpl(EntityManager em) {
        super(em);
        this.type = Professeur.class;
        this.select_all = "SELECT_ALL_PROF";
        this.select_by_id="SELECT_PROF_BY_ID";
    }

    @Override
    public void save(Professeur professeur){
        super.save(professeur);
        // for (ClasseEnseignee ce : professeur.getClassesEnseignees()) {
        //     ce.setProfesseur(professeur);
        //     em.getTransaction().begin();
        //     em.persist(ce);
        //     em.getTransaction().commit();
        // }
    }

    @Override
    public ClasseEnseignee findClasseEnseignee(Long idProf, Long idC) {
        try {
            return em.createNamedQuery("SQL_FIND_CE", ClasseEnseignee.class)
            .setParameter("idProf", idProf)
            .setParameter("idC", idC)
            .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Module> findModulesEnseignesByClasse(Long idC) {
        List<Module> modules = new ArrayList<>();
        List<ClasseEnseignee> classes = em.createNamedQuery("SQL_FIND_CE_BY_CLASSE", ClasseEnseignee.class)
                                        .setParameter("idC", idC)
                                        .getResultList();
        for (ClasseEnseignee classeEnseignee : classes) {
            for (Module mod : classeEnseignee.getModules()) {
                if (!modules.contains(mod)) {
                    modules.add(mod);
                }
            }
        }

        return modules;
    }

    @Override
    public List<Professeur> findProfDisponibles(Module module, Date date, LocalTime heureD, LocalTime heureF) {
        List<Professeur> professeurs = new ArrayList<>();
        List<Professeur> professeursDisponibles = new ArrayList<>();

        //les professeurs du module
        for (Professeur professeur : findAll()) {
            for (ClasseEnseignee classeEnseignee : professeur.getClassesEnseignees()) {
                if(classeEnseignee.getModules().contains(module)){
                    professeurs.add(professeur);
                    break;
                }
            }
        }

        for (Professeur professeur : professeurs) {
            if (disponible(professeur.getCours(), date, heureD, heureF)==1) {
                professeursDisponibles.add(professeur);
            }
        }
        return professeurs;
    }

    
}
