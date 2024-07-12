package ism.repositories;


import java.util.Date;
import java.time.LocalTime;
import java.util.List;

import ism.entities.Salle;

public interface SalleRepository extends Repository<Salle, Long> {
    List<Salle> findSalleDisponibles(Long nbrePlace, Date date, LocalTime heureD, LocalTime heureF);
}
