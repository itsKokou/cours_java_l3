package rv.ism;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import rv.ism.entities.Medecin;
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
        scanner.close();
    }
}