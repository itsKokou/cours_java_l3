package ism.repositories.bd.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import ism.entities.Module;
import ism.repositories.bd.ModuleRepository;
import ism.core.DataBase;

public class ModuleRepositoryImpl implements ModuleRepository{
    private DataBase dataBase;
    private final String SQL_INSERT = "INSERT INTO `modules` (`id`, `libelle`) VALUES (NULL,?)";
    private final String SQL_SELECT_ALL = "SELECT * FROM `modules` where isArchived = ?";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM `modules` WHERE isArchived = ? and id = ?";
    private final String SQL_UPDATE = "UPDATE `modules` SET libelle = ?, isArchived=? WHERE id = ?";


    public ModuleRepositoryImpl(DataBase dataBase){
        this.dataBase = dataBase;
    }

    @Override
    public int insert(Module data) {
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
            System.out.println("Erreur d'insertion de module ");
        }
        return lastID;
    }

    @Override
    public int update(Module data) {
        int nbreLigne = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_UPDATE);
            dataBase.getPs().setString(1, data.getLibelle().toUpperCase()); 
            dataBase.getPs().setBoolean(2, data.getIsArchived());
            dataBase.getPs().setInt(3, data.getId());
            nbreLigne = dataBase.executeUpdate();
            dataBase.closeConnexion();
            dataBase.getPs().close();
        } catch(Exception e){ 
            System.out.println("Erreur d'update de Module ");
        }
        return nbreLigne;
    }

    @Override
    public ArrayList<Module> findAll() {
        ArrayList<Module> modules = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_ALL);
            dataBase.getPs().setBoolean(1, false);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Module module = new Module(rs.getInt("id"),rs.getString("libelle"),rs.getBoolean("isArchived"));
                modules.add(module);
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement des donnees modules");
            e.printStackTrace();
        }
        return modules;
    }

    @Override
    public Module findById(int id) {
        Module module = null;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_ID);
            dataBase.getPs().setBoolean(1, false);
            dataBase.getPs().setInt(2, id);
            ResultSet rs = dataBase.executeSelect();
            if (rs.next()) {
                module = new Module(rs.getInt("id"),rs.getString("libelle"),rs.getBoolean("isArchived"));
            }
            // dataBase.getPs().close();
            // dataBase.closeConnexion();
            // rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement de la donnee module");
            e.printStackTrace();
        }
        return module;
    }
    
}
