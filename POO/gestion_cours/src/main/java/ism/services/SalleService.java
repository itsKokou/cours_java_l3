package ism.services;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import ism.entities.Salle;

public interface SalleService extends Service <Salle> {
    int update (Salle data);
    ArrayList<Salle> findSalleDisponibles(int nbrePlace, Date date, Time heureD, Time heureF);
}
