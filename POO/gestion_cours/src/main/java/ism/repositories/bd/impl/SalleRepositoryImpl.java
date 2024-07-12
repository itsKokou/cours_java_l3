package ism.repositories.bd.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import ism.core.DataBase;
import ism.entities.Cours;
import ism.entities.Salle;
import ism.repositories.bd.SalleRepository;

public class SalleRepositoryImpl implements SalleRepository {
    private DataBase dataBase;

    private final String SQL_INSERT = "INSERT INTO `salles` (`id`, `libelle`,`nbrePlace`) VALUES (NULL,?,?)";
    private final String SQL_SELECT_ALL = "SELECT * FROM `salles` where isArchived = ?";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM `salles` WHERE isArchived = ? and id = ?";
    private final String SQL_UPDATE = "UPDATE `salles` SET libelle = ?, nbrePlace=? ,isArchived=? WHERE id = ?";
    private final String SQL_RECUP_COURS = "SELECT * FROM `cours` WHERE salleID=?";

    public SalleRepositoryImpl(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public int insert(Salle data) {
        int lastID = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_INSERT);
            dataBase.getPs().setString(1, data.getLibelle());
            dataBase.getPs().setInt(2, data.getNbrePlace());
            dataBase.executeUpdate();
            ResultSet rs = dataBase.getPs().getGeneratedKeys();
            if (rs.next()) {
                lastID = rs.getInt(1);
            }
            rs.close();
            dataBase.getPs().close();
            dataBase.closeConnexion();
        } catch (Exception e) {
            System.out.println("Erreur d'insertion de salle ");
        }
        return lastID;
    }

    @Override
    public int update(Salle data) {
        int nbreLigne = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_UPDATE);
            dataBase.getPs().setString(1, data.getLibelle()); 
            dataBase.getPs().setInt(2, data.getNbrePlace()); 
            dataBase.getPs().setBoolean(3, data.getIsArchived());
            dataBase.getPs().setInt(4, data.getId());
            nbreLigne = dataBase.executeUpdate();
            dataBase.closeConnexion();
            dataBase.getPs().close();
        } catch(Exception e){ 
            System.out.println("Erreur d'update de salle ");
        }
        return nbreLigne;
    }

    @Override
    public ArrayList<Salle> findAll() {
        ArrayList<Salle> salles = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_ALL);
            dataBase.getPs().setBoolean(1, false);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Salle salle = new Salle(
                    rs.getInt("id"), 
                    rs.getString("libelle"), 
                    rs.getInt("nbrePlace"), 
                    rs.getBoolean("isArchived"),
                    loadCours(rs.getInt("id"))
                    );
                salles.add(salle);
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement des donnees salles");
            e.printStackTrace();
        }
        return salles;
    }

    @Override
    public Salle findById(int id) {
        Salle salle = null;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_ID);
            dataBase.getPs().setBoolean(1, false);
            dataBase.getPs().setInt(2, id);
            ResultSet rs = dataBase.executeSelect();
            if (rs.next()) {
                salle = new Salle(
                    rs.getInt("id"),
                    rs.getString("libelle"),
                    rs.getInt("nbrePlace"),
                    rs.getBoolean("isArchived"),
                    loadCours(rs.getInt("id"))
                    );
            }
            // dataBase.getPs().close();
            // dataBase.closeConnexion();
            // rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement de la donnee  salle");
            e.printStackTrace();
        }
        return salle;
    }

    public ArrayList<Cours> loadCours(int idSalle) {
        ArrayList<Cours> courss = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_RECUP_COURS);
            dataBase.getPs().setInt(1, idSalle);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Cours cours = new Cours(
                    rs.getInt("id"), 
                    rs.getDate("date"),
                    rs.getTime("heureD"),
                    rs.getTime("heureF")
                    );
                courss.add(cours);
            }
            // dataBase.getPs().close();
            // dataBase.closeConnexion();
            // rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement des donnees cours de salle");
            e.printStackTrace();
        }
        return courss;
    }

    @Override
    public ArrayList<Salle> findSalleDisponibles(int nbrePlace, Date date, Time heureD, Time heureF){
        ArrayList<Salle> salles = new ArrayList<>();
        ArrayList<Salle> sallesDisponibles = new ArrayList<>();

        for (Salle salle : findAll()) {
            if (salle.getNbrePlace()>=nbrePlace) {
                salles.add(salle);
            }
        }

        for (Salle salle : salles) {
            int isDisponible = 1;
            for (Cours cours : salle.getCours()) {
                if (cours.getDate().equals(date)) {
                    if(cours.getHeureD().before(heureF) && heureF.before(cours.getHeureF())){
                        isDisponible=0;
                    }else{
                        if (cours.getHeureD().before(heureD) && heureD.before(cours.getHeureF())) {
                            isDisponible=0;
                        } else {
                            if (heureD.before(cours.getHeureD()) && cours.getHeureD().before(cours.getHeureF()) && cours.getHeureF().before(heureF)) {
                                isDisponible=0;
                            } else {
                                if (cours.getHeureD().before(heureD) && heureD.before(heureF) && heureF.before(cours.getHeureF())) {
                                    isDisponible=0;
                                }else{
                                    if (cours.getHeureD().equals(heureD) && heureF.equals(cours.getHeureF())) {
                                        isDisponible=0;
                                    }
                                }
                            }
                        }
                    } 
                }
            }
            if (isDisponible==1) {
                sallesDisponibles.add(salle);
            }
        }
        return sallesDisponibles;
    }
}
