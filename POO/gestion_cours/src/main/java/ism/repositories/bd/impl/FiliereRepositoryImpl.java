package ism.repositories.bd.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import ism.entities.Filiere;
import ism.repositories.bd.FiliereRepository;
import ism.core.DataBase;

public class FiliereRepositoryImpl implements FiliereRepository{
    private DataBase dataBase;
    private final String SQL_INSERT = "INSERT INTO `filieres` (`id`, `libelle`) VALUES (NULL,?)";
    private final String SQL_SELECT_ALL = "SELECT * FROM `filieres` where isArchived = ?";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM `filieres` WHERE isArchived = ? and id = ?";

    public FiliereRepositoryImpl(DataBase dataBase){
        this.dataBase = dataBase;
    }

    @Override
    public int insert(Filiere data) {
        int lastID = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_INSERT);
            dataBase.getPs().setString(1, data.getLibelle().toUpperCase());
            dataBase.executeUpdate();
            ResultSet rs = dataBase.getPs().getGeneratedKeys();
            if (rs.next()) {
                lastID = rs.getInt(1);
            }
            rs.close();
            dataBase.getPs().close();
            dataBase.closeConnexion();
        } catch (Exception e) {
            System.out.println("Erreur d'insertion de filiere ");
        }
        return lastID;
    }


    @Override
    public ArrayList<Filiere> findAll() {
        ArrayList<Filiere> filieres = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_ALL);
            dataBase.getPs().setBoolean(1, false);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Filiere filiere = new Filiere(rs.getInt("id"),rs.getString("libelle"),rs.getBoolean("isArchived"));
                filieres.add(filiere);
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement des donnees filieres");
            e.printStackTrace();
        }
        return filieres;
    }

    @Override
    public Filiere findById(int id) {
        Filiere filiere = null;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_ID);
            dataBase.getPs().setBoolean(1, false);
            dataBase.getPs().setInt(2, id);
            ResultSet rs = dataBase.executeSelect();
            if (rs.next()) {
                filiere = new Filiere(rs.getInt("id"),rs.getString("libelle"),rs.getBoolean("isArchived"));
            }
            // dataBase.getPs().close();
            // dataBase.closeConnexion();
            // rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement de la donnee filiere");
            e.printStackTrace();
        }
        return filiere;
    }
    
}
