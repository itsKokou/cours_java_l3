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
import ism.repositories.bd.CoursRepository;
import ism.repositories.bd.ProfesseurRepository;

public class ProfesseurRepositoryImpl implements ProfesseurRepository {
    private DataBase dataBase;
    private CoursRepository coursRepository;

    private final String SQL_INSERT = "INSERT INTO `professeurs`(`id`, `nomComplet`, `portable`) VALUES (NULL,?,?)";
    private final String SQL_INSERT_CLASSE = "INSERT INTO `classes_enseignees`(`id`, `professeurID`, `classeID`) VALUES (NULL,?,?)";
    private final String SQL_INSERT_MODULE = "INSERT INTO `modules_enseignes`(`id`, `moduleID`, `classeEnseigneeID`) VALUES (NULL,?,?)";
    private final String SQL_SELECT_ALL = "SELECT * FROM `professeurs` where isArchived = ?";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM `professeurs` WHERE isArchived = ? and id = ?";
    private final String SQL_RECUP_CLASSES = "SELECT ce.*,cl.*, p.*,n.*,f.* FROM `classes_enseignees` ce,`classes` cl, `professeurs` p,`filieres` f,`niveaux` n WHERE ce.classeID=cl.id and cl.niveauID=n.id and cl.filiereID=f.id and ce.professeurID=p.id and ce.professeurID=? ORDER BY cl.id";
    private final String SQL_RECUP_CLASSE_BY_ID = "SELECT ce.*,cl.*, p.*,n.*,f.* FROM `classes_enseignees` ce,`classes` cl, `professeurs` p,`filieres` f,`niveaux` n WHERE ce.classeID=cl.id and cl.niveauID=n.id and cl.filiereID=f.id and ce.professeurID=p.id and ce.professeurID=? and ce.classeID=? ORDER BY cl.id";
    private final String SQL_RECUP_MODULES_BY_CLASSE = "SELECT id FROM `classes_enseignees` WHERE classeID=?";
    private final String SQL_RECUP_MODULES = "SELECT DISTINCT m.* FROM `modules_enseignes` me, `modules` m WHERE me.moduleID=m.id and m.isArchived=0 and me.classeEnseigneeID =? ORDER BY m.id";
    private final String SQL_UPDATE = "UPDATE `professeurs` SET `nomComplet`=?,`portable`=?,`isArchived`=? WHERE id=?";
    private final String SQL_DELETE_CLASSE = "DELETE FROM `classes_enseignees` WHERE id=?";
    private final String SQL_DELETE_MODULE = "DELETE FROM `modules_enseignes` WHERE moduleID=? and classeEnseigneeID=?";

    public ProfesseurRepositoryImpl(DataBase dataBase) {
        this.dataBase = dataBase;
        this.coursRepository = new CoursRepositoryImpl(dataBase);
    }

    @Override
    public int insert(Professeur data) {
        int lastID = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_INSERT);
            dataBase.getPs().setString(1, data.getNomComplet());
            dataBase.getPs().setString(2, data.getPortable());
            dataBase.executeUpdate();
            ResultSet rs = dataBase.getPs().getGeneratedKeys();
            if (rs.next()) {
                lastID = rs.getInt(1);
            }
            rs.close();
            dataBase.getPs().close();
            dataBase.closeConnexion();
            // inserer classes_enseignees (modules_enseignes)
            for (ClasseEnseignee classe : data.getClassesEnseignees()) {
                this.insertClasseEnseignee(lastID, classe);
            }
        } catch (Exception e) {
            System.out.println("Erreur d'insertion de prof ");
        }
        return lastID;
    }

    @Override
    public int insertClasseEnseignee(int idProf, ClasseEnseignee classe) {
        int lastID = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_INSERT_CLASSE);
            dataBase.getPs().setInt(1, idProf);
            dataBase.getPs().setInt(2, classe.getClasse().getId());
            dataBase.executeUpdate();
            ResultSet rs = dataBase.getPs().getGeneratedKeys();
            if (rs.next()) {
                lastID = rs.getInt(1);
            }
            rs.close();
            dataBase.getPs().close();
            dataBase.closeConnexion();
            //inserer les modules de la classes 
            for (Module module : classe.getModules()) {
                insertModuleEnseignee(lastID, module);
            }
        } catch (Exception e) {
            System.out.println("Erreur d'insertion de classe_enseignee ");
        }
        return lastID;
    }

    @Override
    public int insertModuleEnseignee(int idClasseEnseignee, Module module) {
        int lastID = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_INSERT_MODULE);
            dataBase.getPs().setInt(1, module.getId());
            dataBase.getPs().setInt(2, idClasseEnseignee);
            dataBase.executeUpdate();
            ResultSet rs = dataBase.getPs().getGeneratedKeys();
            if (rs.next()) {
                lastID = rs.getInt(1);
            }
            rs.close();
            dataBase.getPs().close();
            dataBase.closeConnexion();
        } catch (Exception e) {
            System.out.println("Erreur d'insertion de module_enseignee ");
        }
        return lastID;
    }

    @Override
    public int update(Professeur data) {
        int nbreLigne = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_UPDATE);
            dataBase.getPs().setString(1, data.getNomComplet());
            dataBase.getPs().setString(2, data.getPortable());
            dataBase.getPs().setBoolean(3, data.getIsArchived());
            dataBase.getPs().setInt(4, data.getId());
            nbreLigne = dataBase.executeUpdate();
            dataBase.closeConnexion();
            dataBase.getPs().close();
        } catch(Exception e){ 
            System.out.println("Erreur d'update de prof ");
        }
        return nbreLigne;
    }

    @Override
    public ArrayList<Professeur> findAll() {
        ArrayList<Professeur> professeurs = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_ALL);
            dataBase.getPs().setBoolean(1, false);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Professeur prof = new Professeur(
                   rs.getInt("id"),
                   rs.getString("nomComplet"),
                   rs.getString("portable"),
                   rs.getBoolean("isArchived"),
                   loadClassesEnseignees(rs.getInt("id")),
                   coursRepository.findByProfesseur(rs.getInt("id"))
                );
                professeurs.add(prof);
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement des donnees professeurs");
            e.printStackTrace();
        }
        return professeurs;
    }

    @Override
    public Professeur findById(int id) {
        Professeur professeur = null;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_ID);
            dataBase.getPs().setBoolean(1, false);
            dataBase.getPs().setInt(2, id);
            ResultSet rs = dataBase.executeSelect();
            if (rs.next()) {
                professeur = new Professeur(
                   rs.getInt("id"),
                   rs.getString("nomComplet"),
                   rs.getString("portable"),
                   rs.getBoolean("isArchived"),
                   loadClassesEnseignees(rs.getInt("id")),
                   coursRepository.findByProfesseur(rs.getInt("id"))
                );
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur de chargement de la donnee professeur");
            e.printStackTrace();
        }
        return professeur;
    }

    private ArrayList<ClasseEnseignee> loadClassesEnseignees(int idProf){
        ArrayList<ClasseEnseignee> classes = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_RECUP_CLASSES);
            dataBase.getPs().setInt(1, idProf);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                ClasseEnseignee classeEnseignee = new ClasseEnseignee(
                    rs.getInt("ce.id"),
                    new Professeur(idProf, rs.getString("p.nomComplet"), rs.getString("p.portable"), rs.getBoolean("p.isArchived")),
                    new Classe(rs.getInt("cl.id"), rs.getString("cl.libelle"),rs.getInt("cl.effectif"),rs.getBoolean("cl.isArchived"),
                            new Niveau(rs.getInt("n.id"),rs.getString("n.libelle"),rs.getBoolean("n.isArchived")),
                            new Filiere(rs.getInt("f.id"),rs.getString("f.libelle"),rs.getBoolean("f.isArchived"))
                    ),
                    loadModulesEnseignes(rs.getInt("ce.id"))
                );
                classes.add(classeEnseignee);
            }
            // dataBase.getPs().close();
            // dataBase.closeConnexion();
            // rs.close();
        } catch (Exception e) {
            System.out.println("Erreur de chargement des donnees classes_enseignees");
        }
        return classes;
    }

    
    private ArrayList<Module> loadModulesEnseignes(int idClasseEnseignee){
        ArrayList<Module> modules = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_RECUP_MODULES);
            dataBase.getPs().setInt(1, idClasseEnseignee);
            ResultSet rs = dataBase.executeSelect();
            while (rs.next()) {
                Module module = new Module(rs.getInt("m.id"), rs.getString("m.libelle"), rs.getBoolean("m.isArchived"));;
                modules.add(module);
            }
            // dataBase.getPs().close();
            // dataBase.closeConnexion();
            // rs.close();
        } catch (Exception e) {
            System.out.println("Erreur de chargement des donnees modules_enseignes");
        }

        return modules;
    }

    @Override
    public ClasseEnseignee findClasseEnseignee(int idProf, int idC) {
        ClasseEnseignee classeEnseignee = null;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_RECUP_CLASSE_BY_ID);
            dataBase.getPs().setInt(1, idProf);
            dataBase.getPs().setInt(2, idC);
            ResultSet rs = dataBase.executeSelect();
            if (rs.next()) {
                classeEnseignee = new ClasseEnseignee(
                    rs.getInt("ce.id"),
                    new Professeur(idProf, rs.getString("p.nomComplet"), rs.getString("p.portable"), rs.getBoolean("p.isArchived")),
                    new Classe(rs.getInt("cl.id"), rs.getString("cl.libelle"),rs.getInt("cl.effectif"),rs.getBoolean("cl.isArchived"),
                            new Niveau(rs.getInt("n.id"),rs.getString("n.libelle"),rs.getBoolean("n.isArchived")),
                            new Filiere(rs.getInt("f.id"),rs.getString("f.libelle"),rs.getBoolean("f.isArchived"))
                    ),
                    loadModulesEnseignes(rs.getInt("ce.id"))
                );
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (Exception e) {
            System.out.println("Erreur de chargement des donnees classes_enseignees");
        }
        return classeEnseignee;
    }

    @Override
    public ArrayList<Module> findModulesEnseignesByClasse(int idC) {
        ArrayList<Module> modulesEnseignes = new ArrayList<>();
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_RECUP_MODULES_BY_CLASSE);
            dataBase.getPs().setInt(1, idC);
            ResultSet rs = dataBase.executeSelect();
            //System.out.println("Okayy");
            while (rs.next()) {
                modulesEnseignes.addAll(loadModulesEnseignes(rs.getInt("id")));
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
        } catch (Exception e) {
            System.out.println("Erreur de chargement des donnees classes_enseignees");
        }
        return modulesEnseignes;
    }

    

    @Override
    public int removeClasseEnseignee(ClasseEnseignee classeEnseignee) {
        int nbreLigne = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_DELETE_CLASSE);
            dataBase.getPs().setInt(1, classeEnseignee.getId());
            nbreLigne = dataBase.executeUpdate();
            dataBase.getPs().close();
            dataBase.closeConnexion();
            //supprimer les modules enseignes dans cette classe
            for (Module module : classeEnseignee.getModules()) {
                this.removeModuleEnseigne(classeEnseignee, module);
            }
        } catch(Exception e){ 
            System.out.println("Erreur de supression de classe_enseignee ");
        }
        return nbreLigne;
    }

    @Override
    public int removeModuleEnseigne(ClasseEnseignee classeEnseignee, Module module) {
        int nbreLigne = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_DELETE_MODULE);
            dataBase.getPs().setInt(1, module.getId());
            dataBase.getPs().setInt(2, classeEnseignee.getId());
            nbreLigne = dataBase.executeUpdate();
            dataBase.getPs().close();
            dataBase.closeConnexion();
        } catch(Exception e){ 
            System.out.println("Erreur de supression de module_enseigne ");
        }
        return nbreLigne;
    }


    public ArrayList<Professeur> findByModule(Module module) {
        ArrayList<Professeur> professeurs = findAll();
        ArrayList<Professeur> professeursModules = new ArrayList<>();
        
        for (Professeur professeur : professeurs) {
            for (ClasseEnseignee classeEnseignee : professeur.getClassesEnseignees()) {
                if(classeEnseignee.getModules().contains(module)){
                    professeursModules.add(professeur);
                    break;
                }
            }
        }
        return professeursModules;
    }

    @Override
    public ArrayList<Professeur> findProfDisponibles(Module module,Date date, Time heureD, Time heureF){
        ArrayList<Professeur> professeurs = findByModule(module);
        ArrayList<Professeur> professeursDisponibles = new ArrayList<>();
        for (Professeur professeur : professeurs) {
            int isDisponible = 1;
            for (Cours cours : professeur.getCours()) {
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
            if (isDisponible==1) {
                professeursDisponibles.add(professeur);
            }
        }
        return professeursDisponibles;
    }

    
}
