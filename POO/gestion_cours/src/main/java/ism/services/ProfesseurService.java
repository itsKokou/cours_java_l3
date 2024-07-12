package ism.services;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import ism.entities.ClasseEnseignee;
import ism.entities.Professeur;
import ism.entities.Module;

public interface ProfesseurService extends Service <Professeur>{
    int update (Professeur data);
    ClasseEnseignee findClasseEnseignee(int idProf, int idC);
    int removeClasseEnseignee(ClasseEnseignee classeEnseignee);
    int removeModuleEnseigne(ClasseEnseignee classeEnseignee, Module module);
    int addClasseEnseignee(int idProf, ClasseEnseignee classe );
    int addModuleEnseignee(int idClasseEnseignee, Module module);
    ArrayList<Module> findModulesEnseignesByClasse(int idC);
    ArrayList<Professeur> findProfDisponibles(Module module,Date date, Time heureD, Time heureF);
}
