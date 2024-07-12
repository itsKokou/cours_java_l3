package ism.repositories.bd;

import java.sql.Date;
import java.util.ArrayList;

import ism.entites.Medecin;
import ism.entites.Rv;

public interface RvRepository extends Repository<Rv>{
    int update (Rv data);
    ArrayList<Rv>findByStatut(String statut);
    ArrayList<Rv>findByStatutAndDate(String statut, Date date);
    ArrayList<Rv>findByStatutAndDateAndMed(String statut, Date date,Medecin medecin);

}
