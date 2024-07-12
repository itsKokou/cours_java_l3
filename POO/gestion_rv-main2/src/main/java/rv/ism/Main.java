package rv.ism;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import rv.ism.entities.Bbw;
import rv.ism.entities.Medecin;
import rv.ism.entities.Personne;
import rv.ism.enums.Specialite;
import rv.ism.repositories.MedecinRepository;
import rv.ism.repositories.PatientRepository;
import rv.ism.repositories.impl.MedecinRepositoryImpl;
import rv.ism.repositories.impl.PatientRepositoryImpl;

public class Main {
    public static void main(String[] args) {
       EntityManagerFactory emf = Persistence
              .createEntityManagerFactory("RVISM");
        EntityManager em=emf.createEntityManager();
          //em.getTransaction().begin();
            // Personne p1= em.find(Personne.class, 1L);
           // bbw.setImat("000002");
              //  em.persist(bbw);
            // Bbw bbw=new Bbw("000003","X4");
               //bbw.getPersonnes().add(p1);
               //em.persist(bbw);
            //em.getTransaction().commit();

           Bbw bbw=em.find(Bbw.class, 2L);
           System.out.println(bbw.getImat()+" "+bbw.getModel());
              bbw.getPersonnes().stream().forEach(c->{
                  System.out.println(c.getNomComplet());
                 });
          

          /*  List<Personne> personnes=em.createQuery("select p from Personne p where p.nomComplet like :nom",Personne.class)
                                    .setParameter("nom","bb%")
                                    .getResultList();*/ 

            /*   List<Personne> personnes=em.createNamedQuery("findLikeNomComplet",Personne.class)
                   .setParameter("nom","bb%")
                  .getResultList();
                 
            personnes.stream().forEach(c->{
                  System.out.println(c.getNomComplet());
           }); */



        PatientRepository patientRepository=new PatientRepositoryImpl(em);
        MedecinRepository medecinRepository=new MedecinRepositoryImpl(em);
        Scanner scanner =new Scanner(System.in);
        int choix;
        do {
                    System.out.println("1-Ajouter Medecin");
                    System.out.println("2- Lister Medecin");
                    System.out.println("3-Ajouter Patient");
                    System.out.println("4-Lister Patient");
                    System.out.println("5- Rechercher Medecin Par Id");
                    System.out.println("6- Quitter");
                    choix=scanner.nextInt();
                     scanner.nextLine();
                    switch (choix) {
                        case 1:
                             System.out.println("Entrer le nom et prenom");
                             String nomComplet=scanner.nextLine(); 
                             Medecin medecin= new Medecin(nomComplet,Specialite.GENERALISTE);
                             medecinRepository.save(medecin);
                             System.out.println(medecin);
                        break;
                         case 2:
                             medecinRepository.findAll().forEach(System.out::println);
                           break;
                           case 5:
                            System.out.println("Entrer l'ID");
                            Long id=scanner.nextLong();
                            medecin =medecinRepository.findById(id);
                           // System.out.println(medecin);
                            medecin.getRvs().forEach(System.out::println);
                            break;
                        default:
                            break;
                    }
        } while (choix!=6);
    }
}