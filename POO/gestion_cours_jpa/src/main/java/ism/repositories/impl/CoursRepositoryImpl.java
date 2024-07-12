package ism.repositories.impl;


import javax.persistence.EntityManager;

import ism.entities.Cours;
import ism.repositories.CoursRepository;


public class  CoursRepositoryImpl extends RepositoryImpl<Cours> implements CoursRepository {

    public CoursRepositoryImpl(EntityManager em) {
        super(em);
        this.type=Cours.class;
        this.select_all = "SELECT_ALL_COURS";
        this.select_by_id="SELECT_COURS_BY_ID";
    }

    @Override
    public Cours findById(Long id) {
        try {
            return em.createNamedQuery(select_by_id, Cours.class)
            .setParameter("id", id)
            .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    

    // private ArrayList<Classe> loadClasses(int id){
    //     ArrayList<Classe> classes = new ArrayList<>();
    //     try {
    //         dataBase.openConnection();
    //         dataBase.initPrepareStatement(SQL_RECUP_CLASSES);
    //         dataBase.getPs().setInt(1, id);
    //         ResultSet rs = dataBase.executeSelect();
    //         while (rs.next()) {
    //             Classe classe = new Classe(rs.getInt("cl.id"), rs.getString("cl.libelle"), rs.getInt("cl.effectif"), rs.getBoolean("cl.isArchived"));
    //             classes.add(classe);
    //         }
    //         // dataBase.getPs().close();
    //         // dataBase.closeConnexion();
    //         // rs.close();
    //     } catch (Exception e) {
    //         System.out.println("Erreur de chargement des donnees cours_classes");
    //     }
    //     return classes;
    // }

    // private void commitClasses(int idCours, ArrayList<Classe> classes){
    //     try {
    //         dataBase.openConnection();
    //         for (Classe classe : classes) {
    //             dataBase.initPrepareStatement(SQL_INSERT_CLASSES);
    //             dataBase.getPs().setInt(1, idCours);
    //             dataBase.getPs().setInt(2, classe.getId());
    //             dataBase.executeUpdate();  
    //         } 
    //         dataBase.getPs().close();
    //         dataBase.closeConnexion();
    //     } catch (Exception e) {
    //         System.out.println("Erreur d'insertion de cours_classes ");
    //     }
    // }
}
