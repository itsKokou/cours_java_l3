package ism.repositories.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlImpl implements Database {
    private Connection conn=null;
    private final String driver="com.mysql.cj.jdbc.Driver";
    private final String url="jdbc:mysql://localhost:3306/gestion_atelier_bd";
    private final String user="root";
    private final String password="";
    private PreparedStatement ps;

    @Override
    public PreparedStatement getPs() {
        return ps;
    }

    @Override
    public void openConnection() {
        if(conn==null){
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, user, password); 
            } catch (Exception e) {
                System.out.println("Erreur de connexion à la bd");
            }
        }
    }

    @Override
    public void closeConnexion() {
       if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Erreur de fermeture de la bd");
            }
       }
    }

    @Override
    public ResultSet executeSelect(String sql) {
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            //ps.close();
        } catch (SQLException e) {
            System.out.println("Erreur d'execution de la requête select");
        }
        return rs;      
    }

    public int executeUpdate(String sql){
        int nbreLigne=0;
        try {
            ps = conn.prepareStatement(sql);
            nbreLigne = ps.executeUpdate();
            //ps.close();
        } catch (SQLException e) {
            System.out.println("Erreur d'execution de la requête update");
        }
        return nbreLigne;         
    }

}
