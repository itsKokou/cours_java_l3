package ism.entites;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Rv {

    private int id;
    private Date date;
    private String statut;

    //Attributs relationnels
    private Patient patient;
    private Medecin medecin;

    public Rv() {
    } 

    public Rv(Date date, Patient patient, Medecin medecin) {
        this.date = date;
        this.statut = "En cours";
        this.patient = patient;
        this.medecin = medecin;
    }

    public Rv(int id, Date date, String statut, Patient patient, Medecin medecin) {
        this.id = id;
        this.date = date;
        this.statut = statut;
        this.patient = patient;
        this.medecin = medecin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    @Override
    public String toString() {
        return  "---------------------------------------------------------------------------------------------------------\n"+
                " RV [ ID = " + id + " | DATE = " + new SimpleDateFormat("dd-MM-yyyy").format(date) + " | STATUT = " + statut + 
                " | PATIENT = " + patient.getNomComplet() + " | MÉDECIN = " + medecin.getNomComplet() + " ]\n"+
                "---------------------------------------------------------------------------------------------------------";
    }
    
    
}
