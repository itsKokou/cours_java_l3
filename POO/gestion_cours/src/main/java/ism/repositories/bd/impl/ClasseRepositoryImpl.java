package ism.repositories.bd.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import ism.core.DataBase;
import ism.entities.Classe;
import ism.entities.ClasseEnseignee;
import ism.entities.Cours;
import ism.entities.Filiere;
import ism.entities.Module;
import ism.entities.Niveau;
import ism.entities.Professeur;
import ism.repositories.bd.ClasseRepository;
import ism.repositories.bd.CoursRepository;

public class ClasseRepositoryImpl implements ClasseRepository {
    private DataBase dataBase;
    private CoursRepository coursRepository;
    
    private final String SQL_INSERT = "INSERT INTO `classes`(`id`, `libelle`, `effectif`, `niveauID`, `filiereID`) VALUES (NULL,?,?,?,?)";
    private final String SQL_INSERT_MODULE = "INSERT INTO `classes_modules`(`id`, `classeID`, `moduleID`) VALUES (NULL,?,?)";
    private final String SQL_SELECT_ALL = "SELECT cl.*,n.*,f.* FROM `classes` cl, `niveaux` n, `filieres` f WHERE cl.niveauID=n.id and cl.filiereID=f.id and cl.isArchived =? ORDER BY cl.id";
    private final String SQL_SELECT_BY_ID = "SELECT cl.*,n.*,f.* FROM `classes` cl, `niveaux` n, `filieres` f WHERE cl.niveauID=n.id and cl.filiereID=f.id and cl.isArchived =? and cl.id = ?";
    // private final String SQL_SELECT_BY_PROF_MOD = "SELECT cl.* FROM `classes` cl, `classes_enseignees` ce, `classes_modules` cm WHERE ce.classeID=cl.id and ce.professeurID=? and cm.classeID=cl.id and cm.moduleID=? ORDER BY cl.id;";
    private final String SQL_SELECT_BY_MODULE = "SELECT cl.*,n.*,f.* FROM `classes` cl, `niveaux` n, `filieres` f,`classes_modules` cm  WHERE cl.niveauID=n.id and cl.filiereID=f.id and cm.classeID=cl.id and cl.isArchived =? and cm.moduleID=? ORDER BY cl.id";
    private final String SQL_RECUP_MODULES = "SELECT m.* FROM `classes_modules` cm, `modules` m WHERE cm.moduleID=m.id and m.isArchived=0 and cm.classeID= ? ORDER BY m.id";
    private final String SQL_DELETE_MODULE = "DELETE FROM `classes_modules` WHERE classeID=? and moduleID= ?";
    private final String SQL_UPDATE = "UPDATE `classes` SET `libelle`=?,`effectif`=?,`isArchived`=? WHERE id=?";

    public ClasseRepositoryImpl(DataBase dataBase) {
        this.dataBase = dataBase;
        coursRepository = new CoursRepositoryImpl(dataBase);
    }

    @Override
    public int insert(Classe data) {
        int lastID = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_INSERT);
            dataBase.getPs().setString(1, data.getLibelle().toUpperCase());
            dataBase.getPs().setInt(2, data.getEffectif());
            dataBase.getPs().setInt(3, data.getNiveau().getId());
            dataBase.getPs().setInt(4, data.getFiliere().getId());
            dataBase.executeUpdate();
            ResultSet rs = dataBase.getPs().getGeneratedKeys();
            if (rs.next()) {
                lastID = rs.getInt(1);
            }
            rs.close();
            dataBase.getPs().close();
            dataBase.closeConnexion();
        } catch (Exception e) {
            System.out.println("Erreur d'insertion de classe ");
        }
        return lastID;
    }

    @Override
    public int update(Classe data) {
        int nbreLigne = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_UPDATE);
            dataBase.getPs().setString(1, data.getLibelle()); 
            dataBase.getPs().setInt(2, data.getEffectif()); 
            dataBase.getPs().setBoolean(3, data.getIsArchived());
            dataBase.getPs().setInt(4, data.getId());
            nbreLigne = dataBase.executeUpdate();
            dataBase.getPs().close();
            dataBase.closeConnexion();
        } catch(Exception e){ 
            System.out.println("Erreur d'update de classe ");
        }
        return nbreLigne;
    }

    @Override
    public int addModule(int idClasse, int idModule){
        int nbreLigne = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_INSERT_MODULE);
            dataBase.getPs().setInt(1, idClasse);
            dataBase.getPs().setInt(2, idModule);
            nbreLigne = dataBase.executeUpdate();
            dataBase.getPs().close();
            dataBase.closeConnexion();
        } catch (Exception e) {
            System.out.println("Erreur d'insertion de classes_modules ");
        }
        return nbreLigne;
    }

    @Override
    public int removeModule(int idClasse, int idModule) {
        int nbreLigne = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_DELETE_MODULE);
            dataBase.getPs().setInt(1, idClasse);
            dataBase.getPs().setInt(2, idModule);
            nbreLigne = dataBase.executeUpdate();
            dataBase.getPs().close();
            dataBase.closeConnexion();
        } catch(Exception e){ 
            System.out.println("Erreur de supression de classes_modules ");
        }
        return nbreLigne;
    }

    @Override
    public ArrayList<Classe> findAll() {
       ArrayList<Classe> classes = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_ALL);
            dataBase.getPs().setBoolean(1, false);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Classe classe = new Classe(
                    rs.getInt("cl.id"),
                    rs.getString("cl.libelle"),
                    rs.getInt("cl.effectif"),
                    rs.getBoolean("cl.isArchived"),
                    new Niveau(rs.getInt("n.id"),rs.getString("n.libelle"),rs.getBoolean("n.isArchived")),
                    new Filiere(rs.getInt("f.id"),rs.getString("f.libelle"),rs.getBoolean("f.isArchived")),
                    loadModules(rs.getInt("cl.id")),
                    coursRepository.findByClasse(rs.getInt("cl.id"))
                );
                classes.add(classe);
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement des donnees classes");
            e.printStackTrace();
        }
        return classes;
    }

    @Override
    public Classe findById(int id) {
        Classe classe =null;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_ID);
            dataBase.getPs().setBoolean(1, false);
            dataBase.getPs().setInt(2,id);
            ResultSet rs = dataBase.executeSelect();
            if (rs.next()) {
                classe = new Classe(
                    rs.getInt("cl.id"),
                    rs.getString("cl.libelle"),
                    rs.getInt("cl.effectif"),
                    rs.getBoolean("cl.isArchived"),
                    new Niveau(rs.getInt("n.id"),rs.getString("n.libelle"),rs.getBoolean("n.isArchived")),
                    new Filiere(rs.getInt("f.id"),rs.getString("f.libelle"),rs.getBoolean("f.isArchived")),
                    loadModules(rs.getInt("cl.id")),
                    coursRepository.findByClasse(rs.getInt("cl.id"))
                );
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement de la donnee classe");
            e.printStackTrace();
        }
        return classe;
    }
    
    private ArrayList<Module> loadModules(int id){
        ArrayList<Module> modules = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_RECUP_MODULES);
            dataBase.getPs().setInt(1, id);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Module module = new Module(rs.getInt("m.id"), rs.getString("m.libelle"), rs.getBoolean("m.isArchived"));
                modules.add(module);
            }
            // dataBase.getPs().close();
            // dataBase.closeConnexion();
            // rs.close();
        } catch (Exception e) {
            System.out.println("Erreur de chargement des donnees classes_modules");
        }
        return modules;
    }

    @Override
    public ArrayList<Classe> findClassesByModule(int idModule) {
        ArrayList<Classe> classes = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_MODULE);
            dataBase.getPs().setBoolean(1, false);
            dataBase.getPs().setInt(2, idModule);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Classe classe = new Classe(
                    rs.getInt("cl.id"),
                    rs.getString("cl.libelle"),
                    rs.getInt("cl.effectif"),
                    rs.getBoolean("cl.isArchived"),
                    new Niveau(rs.getInt("n.id"),rs.getString("n.libelle"),rs.getBoolean("n.isArchived")),
                    new Filiere(rs.getInt("f.id"),rs.getString("f.libelle"),rs.getBoolean("f.isArchived")),
                    loadModules(rs.getInt("cl.id")),
                    coursRepository.findByClasse(rs.getInt("cl.id"))
                );
                classes.add(classe);
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement des donnees classes");
            e.printStackTrace();
        }
        return classes;
    }



    @Override
    public ArrayList<Classe> findClasseDisponibles(Module module, Professeur prof, Date date, Time heureD,Time heureF) {
        ArrayList<Classe> classes = new ArrayList<>();
        ArrayList<Classe> classesDisponibles = new ArrayList<>();
        int isDisponible;

        // Las classe du module et prof
        for (ClasseEnseignee classeEnseignee : prof.getClassesEnseignees()) {
            if (classeEnseignee.getModules().contains(module)) {
                classes.add(findById(classeEnseignee.getClasse().getId()));
            }
        }

        //disponibilité
        for (Classe classe : classes) {
            isDisponible = 1;
            for (Cours cours : classe.getCours()) {
                if (cours.getModule().equals(module)) {
                    isDisponible =0;
                }else{
                    if (cours.getDate().equals(date)) {
                        if(cours.getHeureD().before(heureF) && heureF.before(cours.getHeureF())){
                            isDisponible=0;
                        }else{
                            if (cours.getHeureD().before(heureD) && heureD.before(cours.getHeureF())) {
                                isDisponible=0;
                            } else {
                                if (heureD.before(cours.getHeureD()) && cours.getHeureD().before(cours.getHeureF()) && cours.getHeureF().before(heureF)) {
                                    isDisponible=0;
                                } else {
                                    if (cours.getHeureD().before(heureD) && heureD.before(heureF) && heureF.before(cours.getHeureF())) {
                                        isDisponible=0;
                                    }else{
                                        if (cours.getHeureD().equals(heureD) && heureF.equals(cours.getHeureF())) {
                                            isDisponible=0;
                                        }
                                    }
                                }
                            }
                        } 
                    }
                }
            }
            if (isDisponible==1) {
                classesDisponibles.add(classe);
            }
        }

        return classesDisponibles;
    }

}
