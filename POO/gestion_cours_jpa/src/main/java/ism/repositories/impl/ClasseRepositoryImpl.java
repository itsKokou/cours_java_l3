package ism.repositories.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import ism.entities.Classe;
import ism.entities.ClasseEnseignee;
import ism.entities.Cours;
import ism.entities.Module;
import ism.entities.Professeur;
import ism.repositories.ClasseRepository;

public class ClasseRepositoryImpl extends RepositoryImpl<Classe> implements ClasseRepository {

    public ClasseRepositoryImpl(EntityManager em) {
        super(em);
        this.type = Classe.class;
        this.select_all="SELECT_ALL_CLASSE";
        this.select_by_id="SELECT_CLASSE_BY_ID";
    }


    @Override
    public List<Classe> findClasseDisponibles(Module module, Professeur prof, Date date, LocalTime heureD,LocalTime heureF) {
        List<Classe> classes = new ArrayList<>();
        List<Classe> classesDisponibles = new ArrayList<>();
        
        // Les classe du module et du prof
        for (ClasseEnseignee classeEnseignee : prof.getClassesEnseignees()) {
            if (classeEnseignee.getModules().contains(module)) {
                classes.add(findById(classeEnseignee.getClasse().getId()));
            }
        }

        //disponibilité
        for (Classe classe : classes) {
            int isDisponible = 1;
            for (Cours cours : classe.getCours()) {
                if (cours.getModule().equals(module)) {
                    isDisponible =0;
                }else{
                    if (cours.getDate().equals(date)) {
                        if(cours.getHeureD().isBefore(heureF) && heureF.isBefore(cours.getHeureF())){
                            isDisponible=0;
                        }else{
                            if (cours.getHeureD().isBefore(heureD) && heureD.isBefore(cours.getHeureF())) {
                                isDisponible=0;
                            } else {
                                if (heureD.isBefore(cours.getHeureD()) && cours.getHeureD().isBefore(cours.getHeureF()) && cours.getHeureF().isBefore(heureF)) {
                                    isDisponible=0;
                                } else {
                                    if (cours.getHeureD().isBefore(heureD) && heureD.isBefore(heureF) && heureF.isBefore(cours.getHeureF())) {
                                        isDisponible=0;
                                    }else{
                                        if (cours.getHeureD().equals(heureD) && heureF.equals(cours.getHeureF())) {
                                            isDisponible=0;
                                        }
                                    }
                                }
                            }
                        } 
                    }
                }
            }
            if (isDisponible==1) {
                classesDisponibles.add(classe);
            }
        }
        return classesDisponibles;
    }

}
