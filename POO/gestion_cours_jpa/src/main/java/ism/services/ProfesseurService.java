package ism.services;


import java.util.Date;
import java.time.LocalTime;
import java.util.List;

import ism.entities.Professeur;
import ism.entities.ClasseEnseignee;
import ism.entities.Module;

public interface ProfesseurService extends Service<Professeur,Long>{
    ClasseEnseignee findClasseEnseignee(Long idProf, Long idC);
    List<Module> findModulesEnseignesByClasse(Long idC);
    List<Professeur> findProfDisponibles(Module module, Date date, LocalTime heureD, LocalTime heureF);
}
