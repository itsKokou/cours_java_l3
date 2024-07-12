package ism.repositories.bd;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import ism.entities.Salle;

public interface SalleRepository extends Repository<Salle> {
    int update (Salle data);
    ArrayList<Salle> findSalleDisponibles(int nbrePlace, Date date, Time heureD, Time heureF);
}
