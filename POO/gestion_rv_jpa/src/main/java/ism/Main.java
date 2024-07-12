package ism;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ism.entities.Medecin;
import ism.entities.Patient;
import ism.entities.Rv;
import ism.enums.Specialite;
import ism.enums.Statut;
import ism.repositories.MedecinRepository;
import ism.repositories.PatientRepository;
import ism.repositories.RvRepository;
import ism.repositories.impl.MedecinRepositoryImpl;
import ism.repositories.impl.PatientRepositoryImpl;
import ism.repositories.impl.RvRepositoryImpl;
import ism.services.PersonneService;
import ism.services.RvService;
import ism.services.impl.PersonneServiceImpl;
import ism.services.impl.RvServiceImpl;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main( String[] args ){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RVISM_MYSQL");
        EntityManager em = emf.createEntityManager(); // sert de Database 
        int choix;

        RvRepository rvRepository = new RvRepositoryImpl(em);
        RvService rvService = new RvServiceImpl(rvRepository);

        PatientRepository patientRepository = new PatientRepositoryImpl(em);
        PersonneService<Patient> patientService = new PersonneServiceImpl<>(patientRepository);

        MedecinRepository medecinRepository = new MedecinRepositoryImpl(em) ;
        PersonneService<Medecin> medecinService = new PersonneServiceImpl<>(medecinRepository);

        Medecin medecin=null;
        Patient patient=null;
        Rv rv;
        Date date;
        List<Rv> rvs = new ArrayList<>();
        int saisiMedecin=0,saisiPatient=0;
        Long id;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

       do {
            clearScreen();
            System.out.println("****************************************************");
            System.out.println("                     MENU");
            System.out.println("****************************************************\n");
            System.out.println("1......Créer un patient");
            System.out.println("2......Créer un médecin");
            System.out.println("3......Planifier un RV");
            System.out.println("4......Afficher les RV du jour");
            System.out.println("5......Afficher les RV d'un Medecin par Jour");
            System.out.println("6......Annuler un RV");
            System.out.println("7......Lister RV");
            System.out.println("8......Lister Patient");
            System.out.println("9......Lister Medecin");
            System.out.println("10......Quitter");

            choix = sc.nextInt();
            sc.nextLine();
            clearScreen();
            switch (choix) {
                case 1:
                    patient = saisiPatient();
                    clearScreen();
                    try {
                        patientService.save(patient);
                        System.out.println("\n\n        PATIENT ENREGISTRÉ AVEC SUCCÈS\n");
                    } catch (Exception e) {
                        System.out.println("\n\n        ÉCHEC D'ENREGISTREMENT DU PATIENT \n");
                    }
                    continuer();
                    break;
            
                case 2:
                    medecin = saisiMedecin();
                    clearScreen();
                    try {
                        medecinService.save(medecin);
                        System.out.println("\n\n        MÉDECIN ENREGISTRÉ AVEC SUCCÈS\n");
                    } catch (Exception e) {
                        System.out.println("\n\n        ÉCHEC D'ENREGISTREMENT DU MÉDECIN \n");
                    }
                    continuer();
                    break;
            
                case 3:
                    //Planifier Rv
                    System.out.println("****************************************************");
                    System.out.println("              PLANIFICATION DE RV");
                    System.out.println("****************************************************\n");
                    System.out.println("Entrez la date du RV sous format AAAA-MM-JJ :");
                    String dateInString = sc.nextLine();
                    try {
                        date = formatter.parse(dateInString);
                        clearScreen();
                        System.out.println("****************************************************");
                        System.out.println("              CHOIX DU MÉDECIN");
                        System.out.println("****************************************************\n");
                        medecinService.getAll().forEach(System.out::println);
                        System.out.println("\nEntrez l'ID du médecin du RV [0==>Nouveau médecin] :");
                        id = sc.nextLong();
                        sc.nextLine();
                        if(id!=0){
                            clearScreen();
                            medecin = medecinService.show(id);
                            if(medecin==null){
                                System.out.println("\n\n        CE MÉDECIN N'EXISTE PAS\n");
                                saisiMedecin = 1;
                            }else{
                                System.out.println("\n\n        LE MÉDECIN "+medecin.getNomComplet().toUpperCase()+ " EST ENREGISTRÉ POUR CE RV\n");
                            }
                            continuer();
                        }
                        clearScreen();
                        if (id==0 || saisiMedecin==1) {
                            medecin = saisiMedecin();
                        }
                        clearScreen();
                        System.out.println("****************************************************");
                        System.out.println("              CHOIX DU PATIENT");
                        System.out.println("****************************************************\n");
                        patientService.getAll().forEach(System.out::println);
                        System.out.println("\nEntrez l'ID du patient du RV [0==>Nouveau patient] :");
                        id = sc.nextLong();
                        sc.nextLine();
                        if(id!=0){
                            clearScreen();
                            patient = patientService.show(id);
                            if(patient==null){
                                System.out.println("\n\n        CE PATIENT N'EXISTE PAS\n");
                                saisiPatient = 1;
                            }else{
                                System.out.println("\n\n        LE PATIENT "+patient.getNomComplet().toUpperCase()+ " EST ENREGISTRÉ POUR CE RV\n");
                            }
                            continuer();
                        }
                        clearScreen();
                        if (id==0 || saisiPatient==1) {
                            patient = saisiPatient();
                        }
                        clearScreen();
                        rv = new Rv(date, medecin, patient);
                        rvService.save(rv);
                        System.out.println("\n\n        RV ENREGISTRÉ AVEC SUCCÈS\n");

                    } catch (Exception e) {
                        System.out.println("\n\n        LE FORMAT DE LA DATE EST INCORRECT\n");
                    }
                    continuer();
                    break;
            
                case 4:
                     // RV DU JOUR
                    try {
                        date = new Date(); 
                        String formatted = formatter.format(date);
                        date = formatter.parse(formatted);
                        rvs = rvService.getByStatutAndDate(Statut.ENCOURS, date);
                        if (rvs.isEmpty()) {
                            System.out.println("\n\n        AUCUN RV PRÉVU POUR AUJOURD'HUI\n");
                        } else {
                            System.out.println("****************************************************");
                            System.out.println("             LISTE DES RV D'AUJOURD'HUI");
                            System.out.println("****************************************************\n");
                            rvs.forEach(System.out::println);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    
                    continuer();
                    break;
            
                case 5:
                    System.out.println("****************************************************");
                    System.out.println("            LISTE DES RV D'UN MÉDECIN");
                    System.out.println("****************************************************\n");
                    medecinService.getAll().forEach(System.out::println);
                    System.out.println("\nEntrez l'ID du médecin :");
                    id = sc.nextLong();
                    sc.nextLine();
                    medecin = medecinService.show(id);
                    if (medecin==null) {
                        System.out.println("\n\n        CE MÉDECIN N'EXISTE PAS\n");
                    }else{
                        System.out.println("\nEntrez la date du jour sous format AAAA-MM-JJ :");
                        try {
                            date = formatter.parse(sc.nextLine());
                            rvs = rvService.getByStatutAndDateAndMed(Statut.ENCOURS, date, medecin);
                            clearScreen();
                            if (rvs.isEmpty()) {
                                System.out.println("\n\n        AUCUN RV PRÉVU LE "+new SimpleDateFormat("dd-MM-yyyy").format(date)+ " POUR LE MÉDECIN "+medecin.getNomComplet().toUpperCase()+"\n");
                            } else {
                                System.out.println("********************************************************");
                                System.out.println("     LISTE DES RV DU "+new SimpleDateFormat("dd-MM-yyyy").format(date)+ " DU MÉDECIN "+medecin.getNomComplet().toUpperCase()+"");
                                System.out.println("********************************************************\n");
                                rvs.forEach(System.out::println);
                            }
                        } catch (Exception e) {
                            System.out.println("\n\n        LE FORMAT DE LA DATE EST INCORRECT\n");
                        }
                    }
                    continuer();
                    break;
            
                case 6:
                    System.out.println("****************************************************");
                    System.out.println("             ANNULATION DE RV");
                    System.out.println("****************************************************\n");
                    rvService.getByStatut(Statut.ENCOURS).forEach(System.out::println);
                    System.out.println("Entrez l'ID du RV à annuler :");
                    id = sc.nextLong();
                    sc.nextLine();
                    rv = rvService.show(id);
                    if (rv==null) {
                        System.out.println("\n\n        CE RV N'EXISTE PAS\n");
                    }else{
                        int ch;
                        do {
                            System.out.println("Confirmer l'annulation ? [0==>NON & 1==>OUI]");
                            ch = sc.nextInt();
                        } while (ch!=0 && ch!=1);
                        sc.nextLine();
                        clearScreen();
                        if (ch==0) {
                            System.out.println("\n\n          ANNULATION DU RV AVORTÉE\n");
                        }else {
                            rv.setStatut(Statut.ANNULE);
                            rvService.save(rv);
                            System.out.println("\n\n           RV ANNULÉ AVEC SUCCÈS\n");
                        }
                    }
                    continuer();
                    break;
            
                case 7:
                    clearScreen();
                    System.out.println("****************************************************");
                    System.out.println("            LISTE DES RV");
                    System.out.println("****************************************************\n");
                    rvService.getAll().forEach(System.out::println);
                    continuer();
                    break;
                case 8:
                    clearScreen();
                    System.out.println("****************************************************");
                    System.out.println("            LISTE DES PATIENTS");
                    System.out.println("****************************************************\n");
                    patientService.getAll().forEach(System.out::println);
                    continuer();
                    break;
                case 9:
                    clearScreen();
                    System.out.println("****************************************************");
                    System.out.println("            LISTE DES MÉDECINS");
                    System.out.println("****************************************************\n");
                    medecinService.getAll().forEach(System.out::println);
                    continuer();
                    break;
                case 10:
                    clearScreen();
                    System.out.println("\n\n        AU REVOIR ET À BIENTÔT :)\n\n");
                    //continuer();
                    break;
            
                default:
                    break;
            }
       } while (choix!=10);
    }

    public static void clearScreen(){
        System.out.println("\033c");
    }

    public static void continuer(){
        System.out.println("\n\nAppuyez sur une touche pour continuer...");
        sc.nextLine();
    }

    public static Medecin saisiMedecin(){
        String nomComplet; 
        Specialite specialite=null;
        int choix;

        System.out.println("****************************************************");
        System.out.println("                AJOUT DE MÉDECIN");
        System.out.println("****************************************************\n");
        System.out.println("Entrez le nom et prénoms du médecin :");
        nomComplet = sc.nextLine().trim();
        do {
            System.out.println("Entrez la spécialité du médecin :");
            System.out.println("1..........GENERALISTE");
            System.out.println("2..........DENTISTE");
            System.out.println("3..........PEDIATRE");
            System.out.println("4..........OPHTALMOLOGUE");
            System.out.println("5..........CARDIOLOGUE");
            choix = sc.nextInt();
        } while (choix<1 || choix>5);
        sc.nextLine();
        if (choix==1) {
            specialite=Specialite.GENERALISTE;
        } else {
            if (choix==2) {
                specialite=Specialite.DENTISTE;
            } else {
                if (choix==3) {
                    specialite=Specialite.PEDIATRE;
                } else {
                    if (choix==4) {
                        specialite=Specialite.OPHTALMOLOGUE;
                    } else {
                        specialite=Specialite.CARDIOLOGUE;
                    }
                }
            }
        }
        
        

        return new Medecin(nomComplet, specialite);
    }

    public static Patient saisiPatient(){
        String nomComplet, antecedent;
        List<String> antecedents = new ArrayList<>();

        System.out.println("****************************************************");
        System.out.println("                AJOUT DE PATIENT");
        System.out.println("****************************************************\n");
        System.out.println("Entrez le nom et prénoms du patient :");
        nomComplet = sc.nextLine().trim();
        do {
            System.out.println("Entrez un antécédent du patient [0 ==> Enregistrer] :");
            antecedent = sc.nextLine().trim();
            if(antecedent.equals("0") && antecedents.isEmpty()){
                System.out.println("Entrez au moins un antecedent :");
            }
            if (antecedent.equals("")){
                System.out.println("Saisissez au moins un quelque chose :");
            }else if (!antecedent.equals("0")) {
                antecedents.add(antecedent);
            }
        } while (!antecedent.equals("0") || antecedents.isEmpty());

        return new Patient(nomComplet, String.join(",",antecedents));
    }
}
