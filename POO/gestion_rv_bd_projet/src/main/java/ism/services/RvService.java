package ism.services;

import java.sql.Date;
import java.util.ArrayList;

import ism.entites.Medecin;
import ism.entites.Rv;

public interface RvService extends Service<Rv>{
    int update(Rv data);
    ArrayList<Rv> getByStatut(String statut);
    ArrayList<Rv> getByStatutAndDate(String statut, Date date);
    ArrayList<Rv> getByStatutAndDateAndMed(String statut, Date date, Medecin medecin);
}
