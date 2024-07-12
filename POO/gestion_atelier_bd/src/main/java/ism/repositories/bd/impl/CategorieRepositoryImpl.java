package ism.repositories.bd.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import ism.entities.Categorie;
import ism.repositories.bd.CategorieRepository;
import ism.repositories.core.Database;

public class CategorieRepositoryImpl implements CategorieRepository {
    private Database database;
    private final String SQL_INSERT = "INSERT INTO `categorie` (`libelle`) VALUES(?)";
    private final String SQL_UPDATE = "UPDATE `categorie` SET `libelle` =? WHERE `id`=?";
    private final String SQL_SELECT_ALL = "SELECT * FROM categorie ORDER BY id";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM categorie WHERE id=?";
    private final String SQL_DELETE = "DELETE FROM `categorie` WHERE id=?";

    public CategorieRepositoryImpl (Database database){
        this.database = database;
    }

    @Override
    public int insert(Categorie data) {
        int nbreLigne = 0;
        try {
            
           database.openConnection();
           database.getPs().setString(1, data.getLibelle());
           nbreLigne = database.executeUpdate(SQL_INSERT);
           database.closeConnexion();
            
        } catch(Exception e){ 
            System.out.println("Erreur d'insertion' ");
        }
        return nbreLigne;
    }

    @Override
    public int update(Categorie data) {
        int nbreLigne = 0;
        try {
            
           database.openConnection();
           database.getPs().setString(1, data.getLibelle());
           database.getPs().setInt(2, data.getId());
           nbreLigne = database.executeUpdate(SQL_UPDATE);
           database.closeConnexion();
            
        } catch(Exception e){ 
            System.out.println("Erreur de mise à jour ");
        }
        return nbreLigne;
    }

    @Override
    public ArrayList<Categorie> findAll() {
        ArrayList<Categorie> categories = new ArrayList<>();

        try {
            database.openConnection();
            ResultSet rs = database.executeSelect(SQL_SELECT_ALL);
            while(rs.next()){
                //Categorie cat = new Categorie(rs.getInt(1), rs.getString(2));
                Categorie cat = new Categorie(rs.getInt("id"), rs.getString("libelle"));
                categories.add(cat);
            }
            database.getPs().close();  
            database.closeConnexion();
            rs.close();

        } catch(Exception e){ 
            System.out.println("Erreur de chargement des donnees ");
        }
        return categories;
    }

    @Override
    public Categorie findById(int id) {
        Categorie cat = null;
        try {
            database.openConnection();
            database.getPs().setInt(1, id);
            ResultSet rs = database.executeSelect(SQL_SELECT_BY_ID);
            if(rs.next()){
                cat = new Categorie(rs.getInt("id"), rs.getString("libelle"));
            }   
            database.closeConnexion();
            rs.close();
        } catch(Exception e){ 
            System.out.println("Erreur de chargement de la donnée ");
        }
        return cat;
    }

    @Override
    public int delete(int id) {
        int nbreLigne = 0;
        try {
            
           database.openConnection();
           database.getPs().setInt(1, id);
           nbreLigne = database.executeUpdate(SQL_DELETE);
           database.closeConnexion();
            
        } catch(Exception e){ 
            System.out.println("Erreur de suppression ");
        }
        return nbreLigne;
    }

    @Override
    public int indexOf(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'indexOf'");
    }
    
}
