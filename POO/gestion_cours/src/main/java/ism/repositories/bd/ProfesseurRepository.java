package ism.repositories.bd;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import ism.entities.ClasseEnseignee;
import ism.entities.Professeur;
import ism.entities.Module;

public interface ProfesseurRepository extends Repository<Professeur> {
    int update (Professeur data);
    ClasseEnseignee findClasseEnseignee(int idProf, int idC);
    int removeClasseEnseignee(ClasseEnseignee classeEnseignee);
    int removeModuleEnseigne(ClasseEnseignee classeEnseignee, Module module);
    int insertClasseEnseignee(int idProf, ClasseEnseignee classe );
    int insertModuleEnseignee(int idClasseEnseignee, Module module);
    ArrayList<Module> findModulesEnseignesByClasse(int idC);
    ArrayList<Professeur> findProfDisponibles(Module module,Date date, Time heureD, Time heureF);
}
