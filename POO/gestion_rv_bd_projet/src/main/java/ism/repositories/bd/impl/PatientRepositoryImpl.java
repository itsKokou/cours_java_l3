package ism.repositories.bd.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import ism.entites.Patient;
import ism.repositories.bd.PatientRepository;
import ism.repositories.core.DataBase;

public class PatientRepositoryImpl implements PatientRepository{
    private DataBase dataBase;
    private final String SQL_INSERT = "INSERT INTO `personne` (`id`, `nomComplet`, `type`, `antecedents`) VALUES (NULL,?,?,?)";
    private final String SQL_SELECT_ALL = "SELECT * FROM `personne` WHERE type =?";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM `personne` WHERE type =? and id =? ";

    public PatientRepositoryImpl(DataBase dataBase){
        this.dataBase = dataBase;
    }

    @Override
    public int insert(Patient data) {
        int lastID = 0;
        try {
            
           dataBase.openConnection();
           dataBase.initPrepareStatement(SQL_INSERT);
           dataBase.getPs().setString(1, data.getNomComplet());
           dataBase.getPs().setString(2, data.getType());
           dataBase.getPs().setString(3, String.join(",", data.getAntecedents()));
           dataBase.executeUpdate();
            ResultSet rs = dataBase.getPs().getGeneratedKeys();
            if (rs.next()) {
                lastID = rs.getInt(1);
            }
            dataBase.closeConnexion();
            dataBase.getPs().close();
        } catch(Exception e){ 
            System.out.println("Erreur d'insertion patient ");
        }
        return lastID;
    }

    @Override
    public ArrayList<Patient> findAll() {
        ArrayList<Patient> patients = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_ALL);
            dataBase.getPs().setString(1, "Patient");
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                ArrayList<String> antecedents = new ArrayList<>(Arrays.asList(rs.getString("antecedents").split(","))) ;
                Patient patient = new Patient(rs.getInt("id"), rs.getString("nomComplet"), rs.getString("type"), antecedents);
                patients.add(patient);
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement des donnees patient");
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public Patient findById(int id) {
        Patient patient = null;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_ID);
            dataBase.getPs().setString(1, "Patient");
            dataBase.getPs().setInt(2, id);
            ResultSet rs = dataBase.executeSelect();
            if (rs.next()) {
                ArrayList<String> antecedents = new ArrayList<>(Arrays.asList(rs.getString("antecedents").split(","))) ;
                patient = new Patient(rs.getInt("id"), rs.getString("nomComplet"), rs.getString("type"), antecedents);
            }
            dataBase.getPs().close();
            //dataBase.closeConnexion();
            rs.close();
        } catch (Exception e) {
            System.out.println("Erreur de chargement de la donnee patient  ");
        }
        return patient;
    }
    
}
