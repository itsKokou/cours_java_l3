package ism.repositories.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseImpl implements DataBase{
    private Connection conn=null;
    protected  String driver;
    protected String url;
    protected String user;
    protected String password;
    private PreparedStatement ps=null;

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
                conn = null;
            } catch (SQLException e) {
                System.out.println("Erreur de fermeture de la bd");
            }
       }
    }

    @Override
    public ResultSet executeSelect() {
        ResultSet rs=null;
        try {
            rs=ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erreur d'execution de la requête Select");
        }
        return rs;
    }

    public int executeUpdate(){
        int nbreLigne=0;
        try {
            nbreLigne = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur d'execution de la requête update");
        }
        return nbreLigne;         
    }

    @Override
    public void initPrepareStatement(String sql) {
        try {
            if (sql.toLowerCase().startsWith("insert")) {
                ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            } else {
                ps = conn.prepareStatement(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public PreparedStatement getPs() {
        return ps;
    }
}
