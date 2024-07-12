package ism.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ism.enums.Statut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="rvs")
public class Rv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @Enumerated(EnumType.STRING)
    private Statut statut;
    @ManyToOne
    private Medecin medecin;
    @ManyToOne
    private Patient patient;

    public Rv(Date date, Medecin medecin, Patient patient) {
        this.date = date;
        this.statut = Statut.ENCOURS;
        this.medecin = medecin;
        this.patient = patient;
    }

    public String toString() {
        return  "---------------------------------------------------------------------------------------------------------\n"+
                " RV [ ID = " + id + " | DATE = " + new SimpleDateFormat("dd-MM-yyyy").format(date) + " | STATUT = " + statut + 
                " | PATIENT = " + patient.getNomComplet() + " | MÉDECIN = " + medecin.getNomComplet() + " ]\n"+
                "---------------------------------------------------------------------------------------------------------";
    }
      
}
