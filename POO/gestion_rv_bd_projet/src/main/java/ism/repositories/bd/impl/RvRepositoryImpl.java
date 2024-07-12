package ism.repositories.bd.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

import ism.entites.Medecin;
import ism.entites.Rv;
import ism.repositories.bd.MedecinRepository;
import ism.repositories.bd.PatientRepository;
import ism.repositories.bd.RvRepository;
import ism.repositories.core.DataBase;

public class RvRepositoryImpl implements RvRepository{
    private DataBase dataBase;
    private final String SQL_INSERT = "INSERT INTO `rv` (`id`, `date`, `statut`, `medecinID`, `patientID`) VALUES (NULL,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE `rv` SET `statut` =? WHERE `id`=?";
    private final String SQL_SELECT_ALL = "SELECT r.*,m.*,p.* FROM rv as r, personne as m, personne as p WHERE r.medecinID= m.id and r.patientID=p.id;";
    private final String SQL_SELECT_BY_STATUT = "SELECT r.*,m.*,p.* FROM rv as r, personne as m, personne as p WHERE r.medecinID= m.id and r.patientID=p.id and r.statut=?;";
    private final String SQL_SELECT_BY_STATUT_DATE = "SELECT r.*,m.*,p.* FROM rv as r, personne as m, personne as p WHERE r.medecinID= m.id and r.patientID=p.id and r.statut=? and r.date=?;";
    private final String SQL_SELECT_BY_STATUT_DATE_MED = "SELECT r.*,m.*,p.* FROM rv as r, personne as m, personne as p WHERE r.medecinID= m.id and r.patientID=p.id and r.statut=? and r.date=? and m.id=?;";
    private final String SQL_SELECT_BY_ID = "SELECT r.*,m.*,p.* FROM rv as r, personne as m, personne as p WHERE r.medecinID= m.id and r.patientID=p.id and r.id=?;";

    private PatientRepository patientRepository ;//= new PatientRepositoryImpl(dataBase);
    private MedecinRepository medecinRepository;

    public RvRepositoryImpl(DataBase dataBase){
        this.dataBase = dataBase;
        patientRepository = new PatientRepositoryImpl(dataBase);
        medecinRepository = new MedecinRepositoryImpl(dataBase);
    }

    @Override
    public int insert(Rv data) {
        int lastID = 0;
        try {
            int medecinID = data.getMedecin().getId();
            int patientID = data.getPatient().getId();
            if (medecinID==0) {
                medecinID = this.medecinRepository.insert(data.getMedecin());
            }
            if(patientID==0){
                patientID = this.patientRepository.insert(data.getPatient());
            }
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_INSERT);
            dataBase.getPs().setDate(1, data.getDate()); 
            dataBase.getPs().setString(2, data.getStatut());; 
            dataBase.getPs().setInt(3, medecinID);
            dataBase.getPs().setInt(4, patientID);
            dataBase.executeUpdate();
            ResultSet rs = dataBase.getPs().getGeneratedKeys();
            if (rs.next()) {
                lastID = rs.getInt(1);
            }
            dataBase.getPs().close();
            dataBase.closeConnexion();
            rs.close();
            dataBase.closeConnexion();
            
        } catch(Exception e){ 
            System.out.println("Erreur d'insertion rv ");
        }
        return lastID;
    }

    @Override
    public ArrayList<Rv> findAll() {
        ArrayList<Rv> rvs = new ArrayList<>();

        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_ALL);
            ResultSet rs = dataBase.executeSelect();
            while(rs.next()){
                Rv rv = new Rv(rs.getInt("r.id"),rs.getDate("r.date"),rs.getString("r.statut"),
                patientRepository.findById(rs.getInt("p.id")),medecinRepository.findById(rs.getInt("m.id")));
                rvs.add(rv);
            }
            dataBase.getPs().close();  
            dataBase.closeConnexion();
            rs.close();

        } catch(Exception e){ 
            System.out.println("Erreur de chargement des donnees Rv");
        }
        return rvs;
    }

    @Override
    public Rv findById(int id) {
        Rv rv = null;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_ID);
            dataBase.getPs().setInt(1, id);
            ResultSet rs = dataBase.executeSelect();
            if(rs.next()){
                rv = new Rv(rs.getInt("r.id"),rs.getDate("r.date"),rs.getString("r.statut"),
                patientRepository.findById(rs.getInt("p.id")),medecinRepository.findById(rs.getInt("m.id")));
            }
            dataBase.getPs().close();  
            dataBase.closeConnexion();
            rs.close();

        } catch(Exception e){ 
            System.out.println("Erreur de chargement de la donnee Rv");
        }
        return rv;
    }

    @Override
    public int update(Rv data) {
        int nbreLigne = 0;
        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_UPDATE);
            dataBase.getPs().setString(1, data.getStatut());; 
            dataBase.getPs().setInt(2, data.getId());
            nbreLigne = dataBase.executeUpdate();
            dataBase.closeConnexion();
            dataBase.getPs().close();
        } catch(Exception e){ 
            System.out.println("Erreur d'update de rv ");
        }
        return nbreLigne;
    }

    @Override
    public ArrayList<Rv> findByStatut(String statut) {
        ArrayList<Rv> rvs = new ArrayList<>();

        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_STATUT);
            dataBase.getPs().setString(1, statut);
            ResultSet rs = dataBase.executeSelect();
            while(rs.next()){
                Rv rv = new Rv(rs.getInt("r.id"),rs.getDate("r.date"),rs.getString("r.statut"),
                patientRepository.findById(rs.getInt("p.id")),medecinRepository.findById(rs.getInt("m.id")));
                rvs.add(rv);
            }
            dataBase.getPs().close();  
            dataBase.closeConnexion();
            rs.close();

        } catch(Exception e){ 
            System.out.println("Erreur de chargement des donnees rv");
        }
        return rvs;
    }

    @Override
    public ArrayList<Rv> findByStatutAndDate(String statut, Date date) {
        ArrayList<Rv> rvs = new ArrayList<>();

        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_STATUT_DATE);
            dataBase.getPs().setString(1, statut);
            dataBase.getPs().setDate(2, date);
            ResultSet rs = dataBase.executeSelect();
            while(rs.next()){
                Rv rv = new Rv(rs.getInt("r.id"),rs.getDate("r.date"),rs.getString("r.statut"),
                patientRepository.findById(rs.getInt("p.id")),medecinRepository.findById(rs.getInt("m.id")));
                rvs.add(rv);
            }
            dataBase.getPs().close();  
            dataBase.closeConnexion();
            rs.close();

        } catch(Exception e){ 
            System.out.println("Erreur de chargement des donnees rv");
        }
        return rvs;
    }

    @Override
    public ArrayList<Rv> findByStatutAndDateAndMed(String statut, Date date, Medecin medecin) {
        ArrayList<Rv> rvs = new ArrayList<>();

        try {
            dataBase.openConnection();
            dataBase.initPrepareStatement(SQL_SELECT_BY_STATUT_DATE_MED);
            dataBase.getPs().setString(1, statut);
            dataBase.getPs().setDate(2, date);
            dataBase.getPs().setInt(3, medecin.getId());
            ResultSet rs = dataBase.executeSelect();
            while(rs.next()){
                Rv rv = new Rv(rs.getInt("r.id"),rs.getDate("r.date"),rs.getString("r.statut"),
                patientRepository.findById(rs.getInt("p.id")),medecinRepository.findById(rs.getInt("m.id")));
                rvs.add(rv);
            }
            dataBase.getPs().close();  
            dataBase.closeConnexion();
            rs.close();

        } catch(Exception e){ 
            System.out.println("Erreur de chargement des donnees rv");
        }
        return rvs;
    }

    
    
}
