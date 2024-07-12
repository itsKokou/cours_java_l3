package ism.repositories.bd.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ism.core.DataBase;
import ism.entities.Classe;
import ism.entities.Cours;
import ism.entities.Module;
import ism.entities.Professeur;
import ism.repositories.bd.CoursRepository;
import ism.repositories.bd.SalleRepository;

public class  CoursRepositoryImpl implements CoursRepository {
    private DataBase dataBase;
    private SalleRepository salleRepository;
    
    private final String SQL_INSERT = "INSERT INTO `cours`(`id`, `date`, `heureD`, `heureF`, `codeCours`, `moduleID`, `professeurID`, `salleID`) VALUES (NULL,?,?,?,?,?,?,?)";
    private final String SQL_INSERT_CLASSES = "INSERT INTO `cours_classes` (`id`, `coursID`,`classeID`) VALUES (NULL,?,?)";
    private final String SQL_SELECT_ALL = "SELECT c.*,m.*,p.* FROM `cours` c,`modules` m,`professeurs` p WHERE c.moduleID = m.id and c.professeurID = p.id";
    private final String SQL_SELECT_BY_ID = "SELECT c.*,m.*,p.* FROM `cours` c,`modules` m,`professeurs` p WHERE c.moduleID = m.id and c.professeurID = p.id and c.id = ?";
    private final String SQL_SELECT_BY_PROF = "SELECT c.*,m.*,p.* FROM `cours` c,`modules` m,`professeurs` p WHERE c.moduleID = m.id and c.professeurID = p.id and c.professeurID = ?";
    private final String SQL_SELECT_BY_CLASSE = "SELECT c.*,m.*,p.* FROM `cours` c, `cours_classes` cc,`modules` m,`professeurs` p WHERE c.moduleID = m.id and c.professeurID = p.id and cc.coursID=c.id and cc.classeID=?";
    private final String SQL_RECUP_CLASSES = "SELECT cl.* FROM `cours_classes` c, `classes` cl WHERE c.classeID=cl.id and c.coursID= ?";

    public CoursRepositoryImpl(DataBase dataBase) {
        this.dataBase = dataBase;
        salleRepository = new SalleRepositoryImpl(dataBase);
    }

    @Override
    public int insert(Cours data) {
        int lastID = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_INSERT);
            dataBase.getPs().setDate(1, data.getDate());
            dataBase.getPs().setTime(2, data.getHeureD());
            dataBase.getPs().setTime(3, data.getHeureF());
            dataBase.getPs().setString(4, data.getCodeCours());
            dataBase.getPs().setInt(5, data.getModule().getId());
            dataBase.getPs().setInt(6, data.getProfesseur().getId());
            if (data.getSalle()!=null) {
                dataBase.getPs().setInt(7, data.getSalle().getId());
            }else{
                dataBase.getPs().setNull(7, 7);
            }
            dataBase.executeUpdate();
            ResultSet rs = dataBase.getPs().getGeneratedKeys();
            if (rs.next()) {
                lastID = rs.getInt(1);
            }
            rs.close();
            dataBase.getPs().close();
            dataBase.closeConnexion();
            commitClasses(lastID, data.getClasses());
        } catch (Exception e) {
            System.out.println("Erreur d'insertion de Cours ");
        }
        return lastID;
    }

    @Override
    public ArrayList<Cours> findAll() {
        ArrayList<Cours> courss = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_ALL);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Cours cours = new Cours(
                    rs.getInt("c.id"),
                    rs.getDate("c.date"),
                    rs.getTime("c.heureD"),
                    rs.getTime("c.heureF"),
                    rs.getString("c.codeCours"),
                    new Module(rs.getInt("m.id"),rs.getString("m.libelle"), rs.getBoolean("m.isArchived")),
                    new Professeur(rs.getInt("p.id"), rs.getString("p.nomComplet"), rs.getString("p.portable"), rs.getBoolean("p.isArchived")),
                    salleRepository.findById(rs.getInt("c.salleID")),
                    loadClasses(rs.getInt("id"))
                );
                courss.add(cours);
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement des donnees courss");
            e.printStackTrace();
        }
        return courss;
    }

    @Override
    public Cours findById(int id) {
        Cours cours = null;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_ID);
            dataBase.getPs().setInt(1,id);
            ResultSet rs = dataBase.executeSelect();
            if (rs.next()) {
                cours = new Cours(
                    rs.getInt("c.id"),
                    rs.getDate("c.date"),
                    rs.getTime("c.heureD"),
                    rs.getTime("c.heureF"),
                    rs.getString("c.codeCours"),
                    new Module(rs.getInt("m.id"),rs.getString("m.libelle"), rs.getBoolean("m.isArchived")),
                    new Professeur(rs.getInt("p.id"), rs.getString("p.nomComplet"), rs.getString("p.portable"), rs.getBoolean("p.isArchived")),
                    salleRepository.findById(rs.getInt("c.salleID")),
                    loadClasses(rs.getInt("id"))
                );
            }
            // dataBase.getPs().close();
            // dataBase.closeConnexion();
            // rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement de la donnee cours");
            e.printStackTrace();
        }
        return cours;
    }

    @Override
    public ArrayList<Cours> findByClasse(int id) {
        ArrayList<Cours> courss = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_CLASSE);
            dataBase.getPs().setInt(1, id);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Cours cours = new Cours(
                    rs.getInt("c.id"),
                    rs.getDate("c.date"),
                    rs.getTime("c.heureD"),
                    rs.getTime("c.heureF"),
                    rs.getString("c.codeCours"),
                    new Module(rs.getInt("m.id"),rs.getString("m.libelle"), rs.getBoolean("m.isArchived")),
                    new Professeur(rs.getInt("p.id"), rs.getString("p.nomComplet"), rs.getString("p.portable"), rs.getBoolean("p.isArchived")),
                    salleRepository.findById(rs.getInt("c.salleID")),
                    loadClasses(rs.getInt("id"))
                );
                courss.add(cours);
            }
            // dataBase.getPs().close();
            // dataBase.closeConnexion();
            // rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement des donnees courss");
            e.printStackTrace();
        }
        return courss;
    }

    @Override
    public ArrayList<Cours> findByProfesseur(int id) {
        ArrayList<Cours> courss = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_PROF);
            dataBase.getPs().setInt(1,id);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Cours cours = new Cours(
                    rs.getInt("c.id"),
                    rs.getDate("c.date"),
                    rs.getTime("c.heureD"),
                    rs.getTime("c.heureF"),
                    rs.getString("c.codeCours"),
                    new Module(rs.getInt("m.id"),rs.getString("m.libelle"), rs.getBoolean("m.isArchived")),
                    new Professeur(rs.getInt("p.id"), rs.getString("p.nomComplet"), rs.getString("p.portable"), rs.getBoolean("p.isArchived")),
                    salleRepository.findById(rs.getInt("c.salleID")),
                    loadClasses(rs.getInt("id"))
                );
                courss.add(cours);
            }
            // dataBase.getPs().close();
            // dataBase.closeConnexion();
            // rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement des donnees courss");
            e.printStackTrace();
        }
        return courss;
    }


    private ArrayList<Classe> loadClasses(int id){
        ArrayList<Classe> classes = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_RECUP_CLASSES);
            dataBase.getPs().setInt(1, id);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Classe classe = new Classe(rs.getInt("cl.id"), rs.getString("cl.libelle"), rs.getInt("cl.effectif"), rs.getBoolean("cl.isArchived"));
                classes.add(classe);
            }
            // dataBase.getPs().close();
            // dataBase.closeConnexion();
            // rs.close();
        } catch (Exception e) {
            System.out.println("Erreur de chargement des donnees cours_classes");
        }
        return classes;
    }

    private void commitClasses(int idCours, ArrayList<Classe> classes){
        try {
            dataBase.openConnection();
            for (Classe classe : classes) {
                dataBase.initPrepareStatement(SQL_INSERT_CLASSES);
                dataBase.getPs().setInt(1, idCours);
                dataBase.getPs().setInt(2, classe.getId());
                dataBase.executeUpdate();  
            } 
            dataBase.getPs().close();
            dataBase.closeConnexion();
        } catch (Exception e) {
            System.out.println("Erreur d'insertion de cours_classes ");
        }
    }

    

    
}
