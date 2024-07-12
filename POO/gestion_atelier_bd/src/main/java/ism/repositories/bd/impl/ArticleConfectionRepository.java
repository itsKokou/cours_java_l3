package ism.repositories.bd.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ism.entities.ArticleConfection;
import ism.repositories.core.ITables;

public class ArticleConfectionRepository implements ITables<ArticleConfection> {

    @Override
    public int insert(ArticleConfection data) {
        int nbreLigne = 0;
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gestion_atelier_bd", "root", ""
            );
           
            String sql = "INSERT INTO `article_de_confection` (`libelle`,`prix`,`qte`) VALUES(?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, data.getLibelle());
            statement.setDouble(1, data.getPrix());
            statement.setDouble(1, data.getQte());
            nbreLigne = statement.executeUpdate();
            conn.close();
            statement.close();
        } catch(Exception e){ 
            System.out.println("Erreur de chargement du driver ");
        }
        return nbreLigne;
    }

    @Override
    public int update(ArticleConfection data) {
        int nbreLigne = 0;
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gestion_atelier_bd", "root", ""
            );
           
            String sql = "UPDATE `article_de_confection` SET `libelle` =?, `prix`=?, `qte`=? WHERE `id`=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, data.getLibelle());
            statement.setDouble(1, data.getPrix());
            statement.setDouble(1, data.getQte());
            statement.setInt(2, data.getId());
            nbreLigne = statement.executeUpdate();
            conn.close();
            statement.close();
        } catch(Exception e){ 
            System.out.println("Erreur de chargement du driver ");
        }
        return nbreLigne;
    }

    @Override
    public ArrayList<ArticleConfection> findAll() {
        ArrayList<ArticleConfection> articles = new ArrayList<>();

        try {
            //étape 1: charger la classe de driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //étape 2: créer l'objet de connexion
            Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/gestion_atelier_bd", "root", "");
            //étape 3: créer l'objet statement pour executer les requêtes non préparées
            Statement statement = conn.createStatement();
            //étape 4: exécuter la requête
            ResultSet rs = statement.executeQuery("SELECT * FROM article_de_confection ORDER BY id");
            //étape 5: parcourir le fichier (tableau) Resulset pour recuperer et convertir les elements
            while(rs.next()){
                //Categorie cat = new Categorie(rs.getInt(1), rs.getString(2));
                ArticleConfection art = new ArticleConfection(rs.getInt("id"), rs.getString("libelle"),rs.getDouble("prix"),rs.getDouble("qte"));
                articles.add(art);
            }   
            //étape 5: fermez l'objet de connexion
            conn.close();
            statement.close();
            rs.close();
        } catch(Exception e){ 
            System.out.println("Erreur de chargement du driver ");
        }
        return articles;
    }

    @Override
    public ArticleConfection findById(int id) {
        ArticleConfection data = null;
        try {
            //étape 1: charger la classe de driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //étape 2: créer l'objet de connexion
            Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/gestion_atelier_bd", "root", "");
            //étape 3: créer l'objet preparedStatement pour executer les requêtes préparées
            String sql = "SELECT * FROM article_de_confection WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            //étape 4: exécuter la requête
            ResultSet rs = statement.executeQuery();
            //étape 5: parcourir le fichier (tableau) Resulset pour recuperer et convertir les elements
            if(rs.next()){
                //Categorie cat = new Categorie(rs.getInt(1), rs.getString(2));
                data = new ArticleConfection(rs.getInt("id"), rs.getString("libelle"),rs.getDouble("prix"),rs.getDouble("qte"));
            }   
            //étape 5: fermez l'objet de connexion
            conn.close();
            statement.close();
            rs.close();
        } catch(Exception e){ 
            System.out.println("Erreur de chargement du driver ");
        }
        return data;
    }

    @Override
    public int delete(int id) {
        int nbreLigne = 0;
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gestion_atelier_bd", "root", ""
            );
           
            String sql = "DELETE FROM `article_de_confection` WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            nbreLigne = statement.executeUpdate();
            conn.close();
            statement.close();
        } catch(Exception e){ 
            System.out.println("Erreur de chargement du driver ");
        }
        return nbreLigne;
    }

    @Override
    public int indexOf(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'indexOf'");
    }
    
}
