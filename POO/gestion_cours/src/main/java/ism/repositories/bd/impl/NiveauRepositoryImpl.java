package ism.repositories.bd.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import ism.entities.Niveau;
import ism.repositories.bd.NiveauRepository;
import ism.core.DataBase;

public class NiveauRepositoryImpl implements NiveauRepository{
    private DataBase dataBase;
    private final String SQL_INSERT = "INSERT INTO `niveaux` (`id`, `libelle`) VALUES (NULL,?)";
    private final String SQL_SELECT_ALL = "SELECT * FROM `niveaux` where isArchived = ?";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM `niveaux` WHERE isArchived = ? and id = ?";



    public NiveauRepositoryImpl(DataBase dataBase){
        this.dataBase = dataBase;
    }

    @Override
    public int insert(Niveau data) {
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
            System.out.println("Erreur d'insertion de niveau ");
        }
        return lastID;
    }

    @Override
    public ArrayList<Niveau> findAll() {
        ArrayList<Niveau> niveaux = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_ALL);
            dataBase.getPs().setBoolean(1, false);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Niveau niveau = new Niveau(rs.getInt("id"),rs.getString("libelle"),rs.getBoolean("isArchived"));
                niveaux.add(niveau);
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement des donnees niveaux");
            e.printStackTrace();
        }
        return niveaux;
    }

    @Override
    public Niveau findById(int id) {
        Niveau niveau = null;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_ID);
            dataBase.getPs().setBoolean(1, false);
            dataBase.getPs().setInt(2, id);
            ResultSet rs = dataBase.executeSelect();
            if (rs.next()) {
                niveau = new Niveau(rs.getInt("id"),rs.getString("libelle"),rs.getBoolean("isArchived"));
            }
            // dataBase.getPs().close();
            // dataBase.closeConnexion();
            // rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement de la donnee niveau");
            e.printStackTrace();
        }
        return niveau;
    }
    
}
