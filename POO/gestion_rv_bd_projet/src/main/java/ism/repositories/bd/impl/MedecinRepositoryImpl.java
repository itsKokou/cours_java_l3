package ism.repositories.bd.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ism.entites.Medecin;
import ism.repositories.bd.MedecinRepository;
import ism.repositories.core.DataBase;

public class MedecinRepositoryImpl implements MedecinRepository{
    private DataBase dataBase;
    private final String SQL_INSERT = "INSERT INTO `personne` (`id`, `nomComplet`, `type`, `specialite`) VALUES (NULL,?,?,?)";
    private final String SQL_SELECT_ALL = "SELECT * FROM `personne` WHERE type = ?";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM `personne` WHERE type = ? and id =? ";

    public MedecinRepositoryImpl(DataBase dataBase){
        this.dataBase = dataBase;
    }

    @Override
    public int insert(Medecin data) {
        int lastID = 0;
        try {
            
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_INSERT);
            dataBase.getPs().setString(1, data.getNomComplet());
            dataBase.getPs().setString(2, data.getType());
            dataBase.getPs().setString(3, data.getSpecialite());
            dataBase.executeUpdate();
            ResultSet rs = dataBase.getPs().getGeneratedKeys();
            if (rs.next()) {
                lastID = rs.getInt(1);
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
            
        } catch(Exception e){ 
            System.out.println("Erreur d'insertion medecin ");
        }
        return lastID;
    }

    @Override
    public ArrayList<Medecin> findAll() {
        ArrayList<Medecin> medecins = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_ALL);
            dataBase.getPs().setString(1, "Medecin");
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Medecin medecin = new Medecin(rs.getInt("id"), rs.getString("nomComplet"), rs.getString("type"), rs.getString("specialite"));
                medecins.add(medecin);
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement des donnees medecin");
            e.printStackTrace();
        }
        return medecins;
    }

    @Override
    public Medecin findById(int id) {
        Medecin medecin = null;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_ID);
            dataBase.getPs().setString(1, "Medecin");
            dataBase.getPs().setInt(2, id);
            ResultSet rs = dataBase.executeSelect();
            if (rs.next()) {
                medecin = new Medecin(rs.getInt("id"), rs.getString("nomComplet"), rs.getString("type"), rs.getString("specialite"));
            }
            dataBase.getPs().close();
            //dataBase.closeConnexion();
            rs.close();
        } catch (Exception e) {
            System.out.println("Erreur de chargement de la donnee medecin ");
        }
        return medecin;
    }
    
}
