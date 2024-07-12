package ism.repositories.impl;



import java.util.ArrayList;
import java.util.Date;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;

import ism.entities.Salle;
import ism.repositories.SalleRepository;

public class SalleRepositoryImpl extends RepositoryImpl<Salle> implements SalleRepository {

    public SalleRepositoryImpl(EntityManager em) {
        super(em);
        this.type = Salle.class;
        this.select_all = "SELECT_ALL_SALLE";
        this.select_by_id="SELECT_SALLE_BY_ID";
    }

    // @Override
    // public int insert(Salle data) {
    //     int lastID = 0;
    //     try {
    //         dataBase.openConnection();
    //         dataBase.initPrepareStatement(SQL_INSERT);
    //         dataBase.getPs().setString(1, data.getLibelle());
    //         dataBase.getPs().setInt(2, data.getNbrePlace());
    //         dataBase.executeUpdate();
    //         ResultSet rs = dataBase.getPs().getGeneratedKeys();
    //         if (rs.next()) {
    //             lastID = rs.getInt(1);
    //         }
    //         rs.close();
    //         dataBase.getPs().close();
    //         dataBase.closeConnexion();
    //     } catch (Exception e) {
    //         System.out.println("Erreur d'insertion de salle ");
    //     }
    //     return lastID;
    // }

    // @Override
    // public int update(Salle data) {
    //     int nbreLigne = 0;
    //     try {
    //         dataBase.openConnection();
    //         dataBase.initPrepareStatement(SQL_UPDATE);
    //         dataBase.getPs().setString(1, data.getLibelle()); 
    //         dataBase.getPs().setInt(2, data.getNbrePlace()); 
    //         dataBase.getPs().setBoolean(3, data.getIsArchived());
    //         dataBase.getPs().setInt(4, data.getId());
    //         nbreLigne = dataBase.executeUpdate();
    //         dataBase.closeConnexion();
    //         dataBase.getPs().close();
    //     } catch(Exception e){ 
    //         System.out.println("Erreur d'update de salle ");
    //     }
    //     return nbreLigne;
    // }

    // @Override
    // public ArrayList<Salle> findAll() {
    //     ArrayList<Salle> salles = new ArrayList<>();
    //     try {
    //         dataBase.openConnection();
    //         dataBase.initPrepareStatement(SQL_SELECT_ALL);
    //         dataBase.getPs().setBoolean(1, false);
    //         ResultSet rs = dataBase.executeSelect();
    //         while (rs.next()) {
    //             Salle salle = new Salle(
    //                 rs.getInt("id"), 
    //                 rs.getString("libelle"), 
    //                 rs.getInt("nbrePlace"), 
    //                 rs.getBoolean("isArchived"),
    //                 loadCours(rs.getInt("id"))
    //                 );
    //             salles.add(salle);
    //         }
    //         dataBase.getPs().close();
    //         dataBase.closeConnexion();
    //         rs.close();
    //     } catch (SQLException e) {
    //         System.out.println("Erreur de chargement des donnees salles");
    //         e.printStackTrace();
    //     }
    //     return salles;
    // }

    // @Override
    // public Salle findById(int id) {
    //     Salle salle = null;
    //     try {
    //         dataBase.openConnection();
    //         dataBase.initPrepareStatement(SQL_SELECT_BY_ID);
    //         dataBase.getPs().setBoolean(1, false);
    //         dataBase.getPs().setInt(2, id);
    //         ResultSet rs = dataBase.executeSelect();
    //         if (rs.next()) {
    //             salle = new Salle(
    //                 rs.getInt("id"),
    //                 rs.getString("libelle"),
    //                 rs.getInt("nbrePlace"),
    //                 rs.getBoolean("isArchived"),
    //                 loadCours(rs.getInt("id"))
    //                 );
    //         }
    //         // dataBase.getPs().close();
    //         // dataBase.closeConnexion();
    //         // rs.close();
    //     } catch (SQLException e) {
    //         System.out.println("Erreur de chargement de la donnee  salle");
    //         e.printStackTrace();
    //     }
    //     return salle;
    // }

    // public ArrayList<Cours> loadCours(int idSalle) {
    //     ArrayList<Cours> courss = new ArrayList<>();
    //     try {
    //         dataBase.openConnection();
    //         dataBase.initPrepareStatement(SQL_RECUP_COURS);
    //         dataBase.getPs().setInt(1, idSalle);
    //         ResultSet rs = dataBase.executeSelect();
    //         while (rs.next()) {
    //             Cours cours = new Cours(
    //                 rs.getInt("id"), 
    //                 rs.getDate("date"),
    //                 rs.getTime("heureD"),
    //                 rs.getTime("heureF")
    //                 );
    //             courss.add(cours);
    //         }
    //         // dataBase.getPs().close();
    //         // dataBase.closeConnexion();
    //         // rs.close();
    //     } catch (SQLException e) {
    //         System.out.println("Erreur de chargement des donnees cours de salle");
    //         e.printStackTrace();
    //     }
    //     return courss;
    // }



    @Override
    public List<Salle> findSalleDisponibles(Long nbrePlace, Date date, LocalTime heureD, LocalTime heureF) {
        List<Salle> salles = new ArrayList<>();
        List<Salle> sallesDisponibles = new ArrayList<>();

        for (Salle salle : findAll()) {
            if (salle.getNbrePlace()>=nbrePlace) {
                salles.add(salle);
            }
        }

        for (Salle salle : salles) {
            if (disponible(salle.getCours(), date, heureD, heureF)==1) {
                sallesDisponibles.add(salle);
            }
        }
        return sallesDisponibles;
    }
}
