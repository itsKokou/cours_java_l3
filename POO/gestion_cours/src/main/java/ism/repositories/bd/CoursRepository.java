package ism.repositories.bd;

import java.util.ArrayList;

import ism.entities.Cours;

public interface CoursRepository extends Repository<Cours> {
    ArrayList<Cours> findByClasse(int id);
    ArrayList<Cours> findByProfesseur(int id);
}
