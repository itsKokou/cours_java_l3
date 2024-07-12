package ism;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ism.entities.Direction;
import ism.entities.Secteur;
import ism.repositories.DirectionRepository;
import ism.repositories.SecteurRepository;
import ism.repositories.impl.DirectionRepositoryImpl;
import ism.repositories.impl.SecteurRepositoryImpl;
import ism.services.DirectionService;
import ism.services.SecteurService;
import ism.services.impl.DirectionServiceImpl;
import ism.services.impl.SecteurServiceImpl;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static SecteurService secteurService;
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EXAMEN");
        EntityManager em = emf.createEntityManager();
        
        SecteurRepository secteurRepository = new SecteurRepositoryImpl(em);
        secteurService = new SecteurServiceImpl(secteurRepository);

        DirectionRepository directionRepository =  new DirectionRepositoryImpl(em);
        DirectionService directionService = new DirectionServiceImpl(directionRepository);

        int choix;
        do {
            clearScreen();
            System.out.println("1............Ajouter Direction et ses secteurs");
            System.out.println("2............Lister Secteur");
            System.out.println("3............Lister Direction");
            System.out.println("4............Quitter");
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1:
                    clearScreen();
                    System.out.println("Veuillez entre le nom de la direction");
                    String name = sc.nextLine();
                    Direction direction =  new Direction();
                    direction.setName(name);
                    List<Secteur>secteursSansDirection = getSecteurSansDirection(secteurService.getAll());
                    List<Secteur>secteursSaisis = saisiSecteur(secteursSansDirection, direction);
                    direction.setSecteurs(secteursSaisis);
                    directionService.save(direction);
                    System.out.println("\nDirection enrégistrée avec succès");
                    sc.nextLine();
                    break;
            
                case 2:
                    clearScreen();
                    secteurService.getAll().forEach(System.out::println);
                    sc.nextLine();
                    break;
            
                case 3:
                    clearScreen();
                    directionService.getAll().forEach(System.out::println);
                    sc.nextLine();
                    break;
            
                default:
                    break;
            }
        } while (choix!=4);
    }

    private static void clearScreen(){
        System.out.println("\033c");
    }

    private static List<Secteur> getSecteurSansDirection(List<Secteur>secteurs){

        List<Secteur>secteurSansDirection = new ArrayList<>();

        for (Secteur secteur : secteurs) {
            if(secteur.getDirection()==null){
                secteurSansDirection.add(secteur);
            }
        }
        return secteurSansDirection;
    }

    private static List<Secteur> saisiSecteur(List<Secteur>secteursSansDirection, Direction direction){
        List<Secteur>secteursSaisis = new ArrayList<>();
        Long id;
        do {
            clearScreen();
            System.out.println("\n============LISTE DES SECTEURS===============");
            //Affiche les secteurs sans direction
            for (Secteur secteur : secteursSansDirection) {
                if(!secteursSaisis.contains(secteur)){
                    System.out.println(secteur);
                }
            }
            System.out.println("\nEntrez l'id du secteur à affecter:[-1 ==>Nouveau secteur, 0 ==>Enregistrer]");
            id= sc.nextLong();
            sc.nextLine();
            if (id!=0) {
                //Il ne veut pas enregistrer 
                Secteur secteur = new Secteur();
                if (id != -1) {
                    //Il a entré l'id d'un secteur 
                    secteur = secteurService.show(id);

                    if (secteur == null || !secteursSansDirection.contains(secteur) || secteursSaisis.contains(secteur)) {
                        System.out.println("Ce secteur n'existe pas");
                        sc.nextLine();
                    }else{
                        secteur.setDirection(direction);
                        secteursSaisis.add(secteur);
                        System.out.println("\n   Secteur ajoutée avec succès");
                        sc.nextLine();
                    }
                    
                }else{
                    //Il veut créer un nouveau secteur 
                    System.out.println("\nEntrez le nom du nouveau secteur :");
                    String name = sc.nextLine();
                    System.out.println("\nEntrez le code du nouveau secteur :");
                    String code = sc.nextLine();
                    secteur.setName(name);
                    secteur.setCodeUO(code);
                    secteur.setDirection(direction);
                    secteursSaisis.add(secteur);
                    System.out.println("\n   Secteur ajoutée avec succès");
                    sc.nextLine();
                }
            }else{
                //Il enregistre
                if (secteursSaisis.isEmpty()) {
                    System.out.println("\nVeuillez saisir au moins un secteur");
                    sc.nextLine();
                } else {
                    System.out.println("\nLes secteurs sont bien enregistrés");
                    sc.nextLine();
                }
            }

        } while (id!=0 || secteursSaisis.isEmpty());
        return secteursSaisis;
    }
}