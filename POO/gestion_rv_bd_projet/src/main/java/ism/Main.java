package ism;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;

import ism.entites.Medecin;
import ism.entites.Patient;
import ism.entites.Rv;
import ism.repositories.bd.MedecinRepository;
import ism.repositories.bd.PatientRepository;
import ism.repositories.bd.RvRepository;
import ism.repositories.bd.impl.MedecinRepositoryImpl;
import ism.repositories.bd.impl.PatientRepositoryImpl;
import ism.repositories.bd.impl.RvRepositoryImpl;
import ism.repositories.core.DataBase;
import ism.repositories.core.MySql;
import ism.services.PersonneService;
import ism.services.RvService;
import ism.services.impl.PersonneServiceImpl;
import ism.services.impl.RvServiceImpl;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main( String[] args ){
       int choix;
       DataBase dataBase =  new MySql();

        RvRepository rvRepository = new RvRepositoryImpl(dataBase);
        RvService rvService = new RvServiceImpl(rvRepository);

        PatientRepository patientRepository = new PatientRepositoryImpl(dataBase);
        PersonneService<Patient> patientService = new PersonneServiceImpl<>(patientRepository);

        MedecinRepository medecinRepository = new MedecinRepositoryImpl(dataBase) ;
        PersonneService<Medecin> medecinService = new PersonneServiceImpl<>(medecinRepository);

        Medecin medecin=null;
        Patient patient=null;
        Rv rv;
        Date date;
        ArrayList<Rv> rvs = new ArrayList<>();
        int id,saisiMedecin=0,saisiPatient=0;

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
            System.out.println("7......Quitter");

            choix = sc.nextInt();
            sc.nextLine();
            clearScreen();
            switch (choix) {
                case 1:
                    patient = saisiPatient();
                    clearScreen();
                    try {
                        patientService.add(patient);
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
                        medecinService.add(medecin);
                        System.out.println("\n\n        MÉDECIN ENREGISTRÉ AVEC SUCCÈS\n");
                    } catch (Exception e) {
                        System.out.println("\n\n        ÉCHEC D'ENREGISTREMENT DU MÉDECIN \n");
                    }
                    continuer();
                    break;
            
                case 3:
                    //Planifier RV
                    System.out.println("****************************************************");
                    System.out.println("              PLANIFICATION DE RV");
                    System.out.println("****************************************************\n");
                    System.out.println("Entrez la date du RV sous format AAAA-MM-JJ :");
                    try {
                        date = Date.valueOf(sc.nextLine());
                        clearScreen();
                        System.out.println("****************************************************");
                        System.out.println("              CHOIX DU MÉDECIN");
                        System.out.println("****************************************************\n");
                        medecinService.getAll().forEach(System.out::println);
                        System.out.println("\nEntrez l'ID du médecin du RV [0==>Nouveau médecin] :");
                        id = sc.nextInt();
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
                        id = sc.nextInt();
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
                        rv = new Rv(date, patient, medecin);
                        rvService.add(rv);
                        System.out.println("\n\n        RV ENREGISTRÉ AVEC SUCCÈS\n");

                    } catch (Exception e) {
                        System.out.println("\n\n        LE FORMAT DE LA DATE EST INCORRECT\n");
                    }
                    continuer();
                    break;
            
                case 4:
                    // RV DU JOUR
                    date = Date.valueOf(LocalDate.now());
                    rvs = rvService.getByStatutAndDate("En cours", date);
                    if (rvs.isEmpty()) {
                        System.out.println("\n\n        AUCUN RV PRÉVU POUR AUJOURD'HUI\n");
                    } else {
                        System.out.println("****************************************************");
                        System.out.println("             LISTE DES RV D'AUJOURD'HUI");
                        System.out.println("****************************************************\n");
                        rvs.forEach(System.out::println);
                    }
                    continuer();
                    break;
            
                case 5:
                    System.out.println("****************************************************");
                    System.out.println("            LISTE DES RV D'UN MÉDECIN");
                    System.out.println("****************************************************\n");
                    medecinService.getAll().forEach(System.out::println);
                    System.out.println("\nEntrez l'ID du médecin :");
                    id = sc.nextInt();
                    sc.nextLine();
                    medecin = medecinService.show(id);
                    if (medecin==null) {
                        System.out.println("\n\n        CE MÉDECIN N'EXISTE PAS\n");
                    }else{
                        System.out.println("\nEntrez la date du jour sous format AAAA-MM-JJ :");
                        try {
                            date = Date.valueOf(sc.nextLine());
                            rvs = rvService.getByStatutAndDateAndMed("En cours", date, medecin);
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
                    rvService.getByStatut("En cours").forEach(System.out::println);
                    System.out.println("Entrez l'ID du RV à annuler :");
                    id = sc.nextInt();
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
                            rv.setStatut("Annule");
                            rvService.update(rv);
                            System.out.println("\n\n           RV ANNULÉ AVEC SUCCÈS\n");
                        }
                    }
                    continuer();
                    break;
            
                case 7:
                    clearScreen();
                    System.out.println("\n\n        AU REVOIR ET À BIENTÔT :)\n\n");
                    //continuer();
                    break;
            
                default:
                    break;
            }
       } while (choix!=7);
    }

    public static void clearScreen(){
        System.out.println("\033c");
    }

    public static void continuer(){
        System.out.println("\n\nAppuyez sur une touche pour continuer...");
        sc.nextLine();
    }

    public static Medecin saisiMedecin(){
        String nomComplet, specialite;

        System.out.println("****************************************************");
        System.out.println("                AJOUT DE MÉDECIN");
        System.out.println("****************************************************\n");
        System.out.println("Entrez le nom et prénoms du médecin :");
        nomComplet = sc.nextLine().trim();
        System.out.println("Entrez la spécialité du médecin :");
        specialite = sc.nextLine().trim();

        return new Medecin(nomComplet, specialite);
    }

    public static Patient saisiPatient(){
        String nomComplet, antecedent;
        ArrayList<String> antecedents = new ArrayList<>();

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

        return new Patient(nomComplet, antecedents);
    }
}
