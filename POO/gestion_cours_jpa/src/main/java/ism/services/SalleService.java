package ism.services;

import java.util.Date;
import java.time.LocalTime;
import java.util.List;

import ism.entities.Salle;

public interface SalleService extends Service <Salle, Long> {
    List<Salle> findSalleDisponibles(Long nbrePlace, Date date, LocalTime heureD, LocalTime heureF);
}
