package ism.services;

import java.util.Date;
import java.util.List;

import ism.entities.Medecin;
import ism.entities.Rv;
import ism.enums.Statut;

public interface RvService extends Service<Rv>{
    // Long update(Rv data);
    List<Rv> getByStatut(Statut statut);
    List<Rv> getByStatutAndDate(Statut statut, Date date);
    List<Rv> getByStatutAndDateAndMed(Statut statut, Date date, Medecin medecin);
}
