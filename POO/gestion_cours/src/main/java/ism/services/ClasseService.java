package ism.services;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import ism.entities.Module;
import ism.entities.Classe;
import ism.entities.Professeur;

public interface ClasseService extends Service <Classe>{
    int update (Classe data);
    int removeModule (int idClasse, int idModule);
    int addModule (int idClasse, int idModule);
    ArrayList<Classe> findClassesByModule(int idModule);
    ArrayList<Classe> findClasseDisponibles(Module module,Professeur prof,Date date, Time heureD, Time heureF);

}
