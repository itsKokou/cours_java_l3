package ism.repositories;

import java.util.Date;
import java.util.List;

import ism.entities.Medecin;
import ism.entities.Rv;
import ism.enums.Statut;

public interface RvRepository extends Repository<Rv,Long> {
    List<Rv>findByStatut(Statut statut);
    List<Rv>findByStatutAndDate(Statut statut, Date date);
    List<Rv>findByStatutAndDateAndMed(Statut statut, Date date,Medecin medecin);
}
