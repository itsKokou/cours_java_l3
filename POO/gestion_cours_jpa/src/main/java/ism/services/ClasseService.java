package ism.services;

import java.util.Date;
import java.time.LocalTime;
import java.util.List;

import ism.entities.Module;
import ism.entities.Classe;
import ism.entities.Professeur;

public interface ClasseService extends Service <Classe,Long>{
    List<Classe> findClasseDisponibles(Module module,Professeur prof, Date date, LocalTime heureD, LocalTime heureF);
}
