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
    private static final int taille = 90;
    private static SecteurService secteurService;

    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EXAM");
        EntityManager em = emf.createEntityManager();
        SecteurRepository secteurRepository = new SecteurRepositoryImpl(em);
        secteurService = new SecteurServiceImpl(secteurRepository);
        DirectionRepository directionRepository = new DirectionRepositoryImpl(em);
        DirectionService directionService = new DirectionServiceImpl(directionRepository);

        int choix;
        String name;

        do {
            title("MENU GENERAL", "+");
            System.out.println("1......Ajouter Direction ainsi que ses secteurs");
            System.out.println("2......Lister Secteurs et sa direction");
            System.out.println("3......Lister Directions");
            System.out.println("4......Quitter");
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1:
                    //Ajout direction
                    clearScreen();
                    title("AJOUT DE DIRECTION", "=");
                    System.out.println("Veuillez Entrer le nom du secteur");
                    name = sc.nextLine();
                    Direction direction = new Direction(name);
                    List<Secteur> secteurs = saisieSecteur(direction);
                    direction.setSecteurs(secteurs);
                    directionService.save(direction);
                    clearScreen();
                    System.out.println("\n\n    DIRECTION BIEN ENREGISTREÉ");
                    continuer();
                    break;
            
                case 2:
                    //Lister secteur
                    clearScreen();
                    title("LISTE DES SECTEURS", "=");
                    secteurService.getAll().forEach(System.out::println);
                    continuer();
                    break;
                
                case 3:
                    clearScreen();
                    title("LISTE DES DIRECTIONS", "=");
                    directionService.getAll().forEach(System.out::println);
                    continuer();
                    break;
            
                case 4:

                    break;

                default:
                    break;
            }

        } while (choix!=4);
        
    }

    public static void clearScreen(){
        System.out.println("\033c");
    }

    public static void continuer(){
        System.out.println("\n\nAppuyez sur une touche pour continuer...");
        sc.nextLine();
    }

    public static void title(String msg, String motif){
        clearScreen();
        int espace = Math.round((taille - msg.length())/2);
        String texteAligne = String.format("%" + espace + "s%s%" + espace + "s", " ", msg, " ");
        String ch = "";
        for (int i = 1; i <=taille; i++) {
            ch =ch+motif;
        }
        System.out.println(ch+"\n"+texteAligne+"\n"+ch+"\n"); 
    }

    public static List<Secteur> saisieSecteur(Direction direction ){
        List<Secteur> secteurs = secteurService.getAll();
        List<Secteur> secteurSansDirections =  new ArrayList<>();
        List<Secteur> secteursSaisis = new ArrayList<>();

        for (Secteur sect : secteurs) {
            if(sect.getDirection()==null){
                secteurSansDirections.add(sect);
            }
        }
        
        Long id2;
        do {
            title("LES SECTEURS DISPONIBLES ", "+");
            for (Secteur sect : secteurSansDirections) {
                if(!secteursSaisis.contains(sect)){
                    System.out.println(sect);
                }
            }
            System.out.println("Entrez l'ID pour affecter le secteur à la direction [-1 ==> Nouveau Secteur, 0 ==> Enregistrer] :");
            id2 = sc.nextLong();
            sc.nextLine();
            if(id2 == 0L){
                if(secteursSaisis.isEmpty()){
                    System.out.println("Veuillez mentionner au moins un secteur");
                }else{
                    clearScreen();
                    System.out.println("\n\n     LES SECTEURS SONT BIEN ENREGISTRÉS");
                }
            }else{
                if (id2 == -1L) {
                    //saisi secteur
                    clearScreen();
                    title("SAISIE DU NOUVEAU SECTEUR", "=");
                    System.out.println("Veuillez Entrer le nom du secteur");
                    String nom = sc.nextLine();
                    System.out.println("Veuillez Entrer le code du secteur");
                    String code = sc.nextLine();
                    Secteur secteur = new Secteur(nom, code, direction);
                    secteursSaisis.add(secteur);
                    System.out.println("\n\n    SECTEUR BIEN AJOUTÉ");

                } else {
                    Secteur secteur = secteurService.show(id2);
                    if (secteur == null || !secteurSansDirections.contains(secteur)) {
                        System.out.println("Ce secteur n'existe pas");
                    } else {
                        if (secteursSaisis.contains(secteur) ) {
                            System.out.println("Ce secteur est déjà ajouté");
                        }else{
                            secteur.setDirection(direction);
                            secteursSaisis.add(secteur);
                            System.out.println("\n\nLe secteur a été ajouté à la direction");
                        }
                    }
                }
            }
            continuer();
        } while (secteursSaisis.isEmpty() || id2!=0);
        return secteursSaisis;
    }

}