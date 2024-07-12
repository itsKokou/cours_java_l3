package ism.repositories;

import java.util.Date;
import java.time.LocalTime;
import java.util.List;

import ism.entities.ClasseEnseignee;
import ism.entities.Professeur;
import ism.entities.Module;

public interface ProfesseurRepository extends Repository<Professeur, Long> {
    ClasseEnseignee findClasseEnseignee(Long idProf, Long idC);
    List<Module> findModulesEnseignesByClasse(Long idC);
    List<Professeur> findProfDisponibles(Module module,Date date, LocalTime heureD, LocalTime heureF);
}
