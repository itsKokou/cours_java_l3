package ism;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ism.entities.Classe;
import ism.entities.ClasseEnseignee;
import ism.entities.Cours;
import ism.entities.Filiere;
import ism.entities.Niveau;
import ism.entities.Professeur;
import ism.entities.Salle;
import ism.entities.Module;
import ism.repositories.ClasseRepository;
import ism.repositories.CoursRepository;
import ism.repositories.FiliereRepository;
import ism.repositories.ModuleRepository;
import ism.repositories.NiveauRepository;
import ism.repositories.ProfesseurRepository;
import ism.repositories.SalleRepository;
import ism.repositories.impl.ClasseRepositoryImpl;
import ism.repositories.impl.CoursRepositoryImpl;
import ism.repositories.impl.FiliereRepositoryImpl;
import ism.repositories.impl.ModuleRepositoryImpl;
import ism.repositories.impl.NiveauRepositoryImpl;
import ism.repositories.impl.ProfesseurRepositoryImpl;
import ism.repositories.impl.SalleRepositoryImpl;
import ism.services.ClasseService;
import ism.services.CoursService;
import ism.services.FiliereService;
import ism.services.ModuleService;
import ism.services.NiveauService;
import ism.services.ProfesseurService;
import ism.services.SalleService;
import ism.services.impl.ClasseServiceImpl;
import ism.services.impl.CoursServiceImpl;
import ism.services.impl.FiliereServiceImpl;
import ism.services.impl.ModuleServiceImpl;
import ism.services.impl.NiveauServiceImpl;
import ism.services.impl.ProfesseurServiceImpl;
import ism.services.impl.SalleServiceImpl;


public class App {
    private static Scanner sc = new Scanner(System.in);
    private static final int taille = 90;
    private static final Date dateDuJour = new Date();
    private static final LocalTime heureActuelle = LocalTime.now();
    
    private static ModuleService moduleService;
    private static NiveauService niveauService ;
    private static FiliereService filiereService;
    private static SalleService salleService; 
    private static ClasseService classeService;
    private static ProfesseurService professeurService;
    private static CoursService coursService;
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("COURS_MYSQL");
        EntityManager em = emf.createEntityManager();

        ModuleRepository moduleRepository = new ModuleRepositoryImpl(em);
        moduleService = new ModuleServiceImpl(moduleRepository);

        NiveauRepository niveauRepository = new NiveauRepositoryImpl(em);
        niveauService = new NiveauServiceImpl(niveauRepository);

        FiliereRepository filiereRepository = new FiliereRepositoryImpl(em);
        filiereService = new FiliereServiceImpl(filiereRepository);

        SalleRepository salleRepository = new SalleRepositoryImpl(em);
        salleService  = new SalleServiceImpl(salleRepository);

        ClasseRepository classeRepository = new ClasseRepositoryImpl(em);
        classeService = new ClasseServiceImpl(classeRepository);

        ProfesseurRepository professeurRepository = new ProfesseurRepositoryImpl(em);
        professeurService= new ProfesseurServiceImpl(professeurRepository);

        CoursRepository coursRepository = new CoursRepositoryImpl(em);
        coursService = new CoursServiceImpl(coursRepository);
    
        int choix;
        Long id,nbre;
        String libelle;
        Classe classe = null;
        Filiere filiere =null;
        Niveau niveau =null;
        Salle salle = null;
        Module module = null;
        Professeur professeur = null;
        Cours cours = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        do {
            menu();
            choix = sc.nextInt();
            sc.nextLine();
            switch (choix) {
                case 1:
                //Gerer Classes
                    int choix1;
                    do {
                        choix1 = menuClasse();
                        switch (choix1) {
                            case 1:
                                //Ajout classe
                                List<Niveau> niveaux = niveauService.getAll();
                                List<Filiere> filieres = filiereService.getAll();
                                do {
                                    title("AJOUT DE CLASSE", "+");
                                    niveaux.forEach(System.out::println);
                                    System.out.println("\nVeullez entrez l'ID du niveau de la classe :");
                                    id = sc.nextLong();
                                } while (id<1 || id>niveaux.size());
                                sc.nextLine();
                                niveau= niveauService.show(id);
                                do {
                                    clearScreen();
                                    filieres.forEach(System.out::println);
                                    System.out.println("\nVeullez entrez l'ID de la filiere de la classe :");
                                    id = sc.nextLong();
                                } while (id<1 || id>filieres.size());
                                sc.nextLine();
                                filiere= filiereService.show(id);
                                clearScreen();
                                System.out.println("Le libelle de la classe sera : "+niveau.getLibelle()+" "+filiere.getLibelle());
                                System.out.println("\nAjoutez une lettre (A,B,...)? Laissez vide pour conserver le libelle :");
                                String LibFin = sc.nextLine().trim();
                                libelle = niveau.getLibelle()+" "+filiere.getLibelle()+" "+LibFin;
                                System.out.println("Entrez l'effectif de la classe :");
                                nbre = sc.nextLong();
                                sc.nextLine();
                                if (confirmer("l'ajout de la classe")==1) {
                                    clearScreen();
                                    try {
                                        classe = new Classe(libelle, nbre,niveau,filiere);
                                        classeService.save(classe);
                                        System.out.println("\n\n          CLASSE AJOUTÉE AVEC SUCCÈS\n");
                                    } catch (Exception e) {
                                        System.out.println("\n\n          UNE CLASSE DE CE NOM EXISTE DÉJA\n");
                                    }
                                }else{
                                    clearScreen();
                                    System.out.println("\n\n          AJOUT DE CLASSE ANNULÉ\n");
                                }
                                continuer();
                                break;
                            case 2:
                                //Lister classe
                                title("LISTE DES CLASSES", "+");
                                classeService.getAll().stream().forEach(System.out::println);
                                continuer();
                                break;
                            case 3:
                                //Editer classe
                                title("ÉDITER UNE CLASSE", "+");
                                classeService.getAll().forEach(System.out::println);
                                System.out.println("Entrez l'ID de la classe à éditer :");
                                id = sc.nextLong();
                                sc.nextLine();
                                classe = classeService.show(id);
                                if (classe==null) {
                                    System.out.println("Cette Classe n'existe pas");
                                }else{
                                    clearScreen();
                                    System.out.println(classe);
                                    System.out.println("Entrez le nouveau libelle [0 ==>Conserver l'ancien]");
                                    libelle = sc.nextLine();
                                    if (!libelle.equals("0")) {
                                        classe.setLibelle(libelle);
                                    }
                                    System.out.println("Entrez le nouvel effectif [0 ==>Conserver l'ancien]");
                                    nbre = sc.nextLong();
                                    sc.nextLine();
                                    if (nbre != 0L ) {
                                        classe.setEffectif(nbre);
                                    }
                                    if (confirmer("la modification de cette classe")==1) {
                                        classeService.save(classe);
                                        clearScreen();
                                        System.out.println("\n\n          CLASSE MODIFIÉE AVEC SUCCÈS\n");
                                    }else{
                                        clearScreen();
                                       System.out.println("\n\n          MODIFICATION DE CLASSE ANNULÉE\n"); 
                                    }
                                }
                                continuer();
                                break;
                            case 4:
                                //Retirer module de classe
                                title("SUPPRESSION DE MODULE DANS CLASSE", "*");
                                classeService.getAll().forEach(System.out::println);
                                System.out.println("Entrez l'ID de la classe :");
                                id = sc.nextLong();
                                sc.nextLine();
                                classe = classeService.show(id);
                                if (classe==null) {
                                    System.out.println("\nCette Classe n'existe pas");
                                }else{
                                    if (!classe.getModules().isEmpty()) {
                                        clearScreen();
                                        classe.getModules().forEach(System.out::println);
                                        System.out.println("Entrez l'ID pour retirer le module de la classe :");
                                        id = sc.nextLong();
                                        sc.nextLine();
                                        module = moduleService.show(id);
                                        if (!classe.getModules().contains(module)) {
                                            System.out.println("Ce module n'existe pas dans cette classe");
                                        } else {
                                            if (confirmer("le retrait de ce module de cette classe")==1) {
                                                classe.getModules().remove(module);
                                                classeService.save(classe);
                                                clearScreen();
                                                System.out.println("\n\n          MODULE RETIRÉ DE LA CLASSE AVEC SUCCÈS\n");
                                            }else{
                                                clearScreen();
                                                System.out.println("\n\n          RETRAIT DE MODULE ANNULÉ\n");
                                            }
                                        }
                                    }else{
                                        System.out.println("Cette Classe n'a aucun module");
                                    }
                                }
                                continuer();
                                break;
                        
                            case 5:
                                //Archiver classe
                                title("ARCHIVER UNE CLASSE", "+");
                                classeService.getAll().forEach(System.out::println);
                                System.out.println("Entrez l'ID de la classe à Archiver :");
                                id = sc.nextLong();
                                sc.nextLine();
                                classe = classeService.show(id);
                                if (classe==null) {
                                    System.out.println("Cette Classe n'existe pas");
                                }else{
                                    if (confirmer("l'archivage de cette classe")==1) {
                                        classe.setIsArchived(true);
                                        classeService.save(classe);
                                        clearScreen();
                                        System.out.println("\n\n          LA CLASSE "+classe.getLibelle().toUpperCase()+" EST ARCHIVÉE AVEC SUCCÈS\n");
                                    }else{
                                        clearScreen();
                                       System.out.println("\n\n          ARCHIVAGE DE LA CLASSE "+classe.getLibelle().toUpperCase()+" ANNULÉ\n"); 
                                    }
                                }
                                continuer();
                                break;
                        
                            default:
                                break;
                        }
                    } while (choix1!=6);
                    break;
            
                case 2:
                    //Gerer Module 
                    int choix2;
                    do {
                        choix2=menuModule();
                        switch (choix2) {
                            case 1:
                                //Ajouter Module
                                title("AJOUT DE MODULE", "+");
                                System.out.println("Entrez le libellé du module :");
                                libelle = sc.nextLine();
                                if (confirmer("l'ajout du module")==1) {
                                    clearScreen();
                                    try {
                                        module = new Module(libelle);
                                        moduleService.save(module);
                                        System.out.println("\n\n          MODULE AJOUTÉ AVEC SUCCÈS\n");
                                    } catch (Exception e) {
                                        System.out.println("\n\n          UN MODULE DE CE NOM EXISTE DÉJA\n");
                                    }
                                }else{
                                    clearScreen();
                                    System.out.println("\n\n          AJOUT DE MODULE ANNULÉ\n");
                                }
                                continuer();
                                break;
                            case 2:
                                //Lister Module
                                title("LISTE DES MODULES", "+");
                                moduleService.getAll().forEach(System.out::println);
                                continuer();
                                break;
                            case 3:
                                //Editer Module
                                title("ÉDITER UN MODULE", "+");
                                moduleService.getAll().forEach(System.out::println);
                                System.out.println("Entrez l'ID du module à éditer :");
                                id = sc.nextLong();
                                sc.nextLine();
                                module = moduleService.show(id);
                                if (module==null) {
                                    System.out.println("Ce module n'existe pas");
                                }else{
                                    System.out.println("Entrez le nouveau libelle [0 ==>Conserver l'ancien]");
                                    libelle = sc.nextLine();
                                    if (!libelle.equals("0")) {
                                        module.setLibelle(libelle);
                                    }
                                    if (confirmer("la modification de ce module")==1) {
                                        clearScreen();
                                        moduleService.save(module);
                                        System.out.println("\n\n          LE MODULE MODIFIÉ AVEC SUCCÈS\n");
                                    }else{
                                        clearScreen();
                                       System.out.println("\n\n          MODIFICATION DU MODULE ANNULÉE\n"); 
                                    }
                                }
                                continuer();
                                break;

                            case 4:
                                //Archiver Module
                                title("ARCHIVER UN MODULE", "+");
                                moduleService.getAll().forEach(System.out::println);
                                System.out.println("Entrez l'ID du module à Archiver :");
                                id = sc.nextLong();
                                sc.nextLine();
                                module = moduleService.show(id);
                                if (module==null) {
                                    System.out.println("Ce module n'existe pas");
                                }else{
                                    if (confirmer("l'archivage de ce module")==1) {
                                        clearScreen();
                                        module.setIsArchived(true);
                                        moduleService.save(module);
                                        System.out.println("\n\n          LE MODULE "+module.getLibelle().toUpperCase()+ " EST ARCHIVÉ AVEC SUCCÈS\n");
                                    }else{
                                        clearScreen();
                                       System.out.println("\n\n          ARCHIVAGE DU MODULE "+module.getLibelle().toUpperCase()+ " ANNULÉ\n"); 
                                    }
                                }
                                continuer();
                                break;
                        
                            default:
                                break;
                        }
                    } while (choix2!=5);
                    break;
            
                case 3:
                    //Affecter Module à classe
                    title("AFFECTATION DE MODULE À CLASSE", "*");
                    classeService.getAll().forEach(System.out::println);
                    System.out.println("Entrez l'ID de la classe :");
                    id = sc.nextLong();
                    sc.nextLine();
                    classe = classeService.show(id);
                    if (classe==null) {
                        System.out.println("Cette Classe n'existe pas");
                    }else{
                        clearScreen();
                        List<Module> modules = moduleService.getAll();
                        for (Module mod : modules) {
                            if(!classe.getModules().contains(mod)){
                                System.out.println(mod);
                            }
                        }
                        System.out.println("Entrez l'ID pour affecter le module à la classe :");
                        id = sc.nextLong();
                        sc.nextLine();
                        module = moduleService.show(id);
                        if (module == null) {
                            System.out.println("\nCe module n'existe pas");
                        } else {
                            if (classe.getModules().contains(module)) {
                                System.out.println("\nCe module a déjà été affecté à cette classe");
                            }else{
                                if (confirmer("l'ajout de ce module à cette classe")==1) {
                                    clearScreen();
                                    classe.getModules().add(module);
                                    classeService.save(classe);
                                    System.out.println("\n\n          MODULE AJOUTÉ À LA CLASSE AVEC SUCCÈS\n");
                                }else{
                                    clearScreen();
                                    System.out.println("\n\n          AFFECTATION DE MODULE ANNULÉE\n");
                                }
                            }
                        }
                    }
                    continuer();
                    break;
            
                case 4:
                    //Lister Module d'une classe
                    title("AFFICHAGE DES MODULES D'UNE CLASSE", "*");
                    classeService.getAll().forEach(System.out::println);
                    System.out.println("Entrez L'ID de la classe pour voir ses modules :");
                    id = sc.nextLong();
                    sc.nextLine();
                    classe = classeService.show(id);
                    if (classe==null) {
                        System.out.println("Cette classe n'existe pas !");
                    }else{
                        if(classe.getModules().isEmpty()){
                            System.out.println("\nCette classe n'a aucun module");
                        }else{
                            title("LISTE DES MODULES DE "+classe.getLibelle(), "*");
                            classe.getModules().forEach(System.out::println);
                        }
                    }
                    continuer();
                    break;
            
                case 5:
                    //Lister classe filtrer par module
                    title("AFFICHAGE DES CLASSES SUIVANT UN MODULE", "*");
                    moduleService.getAll().forEach(System.out::println);
                    System.out.println("Entrez L'ID du module pour voir les classes concernées :");
                    id = sc.nextLong();
                    sc.nextLine();
                    module = moduleService.show(id);
                    if (module==null) {
                        System.out.println("Ce module n'existe pas !");
                    }else{
                        if(module.getClasses().isEmpty()){
                            System.out.println("\nAucune classe n'étudie ce module");
                        }else{
                            title("LISTE DES CLASSES QUI FONT "+module.getLibelle(), "*");
                           module.getClasses().forEach(System.out::println);
                        }
                    }
                    continuer();
                    break;
            
                case 6:
                    //Gerer Professeur
                    int choix6;
                    do {
                        choix6 = menuProfesseur();
                        switch (choix6) {
                            case 1:
                                //Ajouter Professeur
                                title("AJOUT D'UN PROFESSEUR", "+");
                                System.out.println("Entrez le nom et prénoms du professeur :");
                                libelle = sc.nextLine();
                                System.out.println("Entrez son numéro de téléphone :");
                                String portable = sc.nextLine();
                                List<ClasseEnseignee> classesEnseignees = saisieClasseEnseignee(professeur);
                                if (confirmer("l'ajout du professeur")==1) {
                                    clearScreen();
                                    try {
                                        professeur = new Professeur(libelle, portable);
                                        professeur.getClassesEnseignees().addAll(classesEnseignees);
                                        professeurService.save(professeur);
                                        System.out.println("\n\n          PROFESSEUR AJOUTÉ AVEC SUCCÈS\n");
                                    } catch (Exception e) {
                                        System.out.println("\n\n          UN PROFESSEUR QUI A CE TÉLÉPHONE EXISTE DÉJA\n");
                                        e.printStackTrace();
                                    }
                                }else{
                                    clearScreen();
                                    System.out.println("\n\n          AJOUT DE PROFESSEUR ANNULÉ\n");
                                }
                                continuer();
                                break;
                            case 2:
                                //Lister professeurs
                                title("LISTE DES PROFESSEURS", "+");
                                professeurService.getAll().forEach(System.out::println);
                                continuer();
                                break;
                            case 3:
                                //Editer professeur
                                title("ÉDITER UN PROFESSEUR", "+");
                                professeurService.getAll().forEach(System.out::println);
                                System.out.println("Entrez l'ID du professeur à Éditer :");
                                id = sc.nextLong();
                                sc.nextLine();
                                professeur = professeurService.show(id);
                                if (professeur==null) {
                                    System.out.println("Ce professeur n'existe pas");
                                }else{
                                    System.out.println("Entrez les nouveaux nom et prénoms [0 ==>Conserver l'ancien]");
                                    libelle = sc.nextLine();
                                    if (!libelle.equals("0")) {
                                        professeur.setNomComplet(libelle);
                                    }
                                    System.out.println("Entrez le nouveau téléphone [0 ==>Conserver l'ancien]");
                                    libelle = sc.nextLine();
                                    if (!libelle.equals("0")) {
                                        professeur.setPortable(libelle);
                                    }
                                    if (confirmer("la modification de ce professeur")==1) {
                                        professeurService.save(professeur);
                                        clearScreen();
                                        System.out.println("\n\n          PROFESSEUR MODIFIÉ AVEC SUCCÈS\n");
                                    }else{
                                        clearScreen();
                                       System.out.println("\n\n          MODIFICATION DU PROFESSEUR ANNULÉE\n"); 
                                    }
                                }
                                continuer();
                                break;
                
                            case 4:
                                //Ajouter classe et modules enseignes
                                title("AJOUTER CLASSE AVEC MODULES AU PROFESSEUR", "*");
                                professeurService.getAll().forEach(System.out::println);
                                System.out.println("Entrez l'ID du professeur pour lui affecter une classe :");
                                professeur = professeurService.show( sc.nextLong());
                                sc.nextLine();
                                if(professeur==null){
                                    System.out.println("\nCe professeur n'existe pas !");
                                }else{
                                    List<Classe> classes = classeService.getAll();
                                    classesEnseignees = professeur.getClassesEnseignees();
                                    title("AFFECTER AU PROFESSEUR UNE CLASSE", "+");
                                    for (Classe cl : classes) {
                                        if(checkClasseInClassesEnseignees(classesEnseignees, cl)==-1L){
                                            System.out.println(cl);
                                        }
                                    }
                                    System.out.println("Entrez l'ID de la Classe pour l'affecter au prof :");
                                    classe = classeService.show(sc.nextLong());
                                    sc.nextLine();
                                    if (classe == null) {
                                        System.out.println("\nCette Classe n'existe pas");
                                    } else {
                                        if (checkClasseInClassesEnseignees(classesEnseignees, classe)!=-1L) {
                                            System.out.println("\nCette classe lui a déjà été affectée");
                                        }else{
                                            ClasseEnseignee classeEnseignee = new ClasseEnseignee();
                                            classeEnseignee.setClasse(classe);
                                            List<Module> modules = classe.getModules();
                                            if(modules.isEmpty()){
                                                System.out.println("Cette classe n'apprend aucun module pour le moment !");
                                            }else{
                                                List<Module> modulesSaisis = saisieModulesEnseignes(classe);
                                                if (modulesSaisis.isEmpty()) {
                                                    clearScreen();
                                                    System.out.println("\n\n          AFFECTION DE CLASSE AVORTÉE \n          CETTE CLASSE A DÉJA TOUS SES MODULES AFFECTÉS A DES PROFS\n");
                                                } else {
                                                    classeEnseignee.getModules().addAll(modulesSaisis);
                                                    if (confirmer("l'affectation de la classe au professeur")==1) {
                                                        professeur.getClassesEnseignees().add(classeEnseignee);
                                                        professeurService.save(professeur);
                                                        clearScreen();
                                                        System.out.println("\n\n          CLASSE AFFECTÉE AU PROF AVEC SUCCÈS\n");
                                                    }else{
                                                        clearScreen();
                                                        System.out.println("\n\n          AFFECTION DE CLASSE ANNULÉE\n"); 
                                                    }
                                                }
                                            }
                                        }
                                    }                                 
                                }
                                continuer();
                                break;

                            case 5:
                                // supprimer classe enseignée
                                title("SUPPRESSION DE CLASSE DANS PROFESSEUR", "*");
                                professeurService.getAll().forEach(System.out::println);
                                System.out.println("Entrez l'ID du professeur :");
                                id = sc.nextLong();
                                sc.nextLine();
                                professeur = professeurService.show(id);
                                if (professeur==null) {
                                    System.out.println("\nCe professeur n'existe pas");
                                }else{
                                    clearScreen();
                                    professeur.getClassesEnseignees().forEach(System.out::println);
                                    System.out.println("Entrez l'ID de la classe pour la retirer :");
                                    id = sc.nextLong();
                                    sc.nextLine();
                                    ClasseEnseignee classeEnseignee = professeurService.findClasseEnseignee(professeur.getId(), id);
                                    if (classeEnseignee == null) {
                                        System.out.println("Cette classe n'existe pas dans ce professeur");
                                    } else {
                                        if (confirmer("le retrait de cette classe du professeur")==1) {
                                            professeur.getClassesEnseignees().remove(classeEnseignee);
                                            professeurService.save(professeur);
                                            clearScreen();
                                            System.out.println("\n\n          CLASSE RETIRÉE DU PROFESSEUR AVEC SUCCÈS\n");
                                        }else{
                                            clearScreen();
                                            System.out.println("\n\n          RETRAIT DE CLASSE ANNULÉ\n");
                                        }
                                        
                                    }
                                }
                                continuer();
                                break;
                        
                            case 6:
                                //Ajouter Module dans classe enseignee
                                title("AJOUT DE MODULE DANS CLASSE D'UN PROF", "*");
                                professeurService.getAll().forEach(System.out::println);
                                System.out.println("Entrez l'ID du professeur :");
                                id = sc.nextLong();
                                sc.nextLine();
                                professeur = professeurService.show(id);
                                if (professeur==null) {
                                    System.out.println("\nCe professeur n'existe pas");
                                }else{
                                    clearScreen();
                                    List<ClasseEnseignee> classeEnseignees = professeur.getClassesEnseignees();
                                    if (classeEnseignees.isEmpty()) {
                                        System.out.println("\n Ce professeur n'enseigne aucune classe");
                                    } else {
                                        classeEnseignees.forEach(System.out::println);
                                        System.out.println("Entrez l'ID de la classe pour l'ajout de module :");
                                        id = sc.nextLong();
                                        sc.nextLine();
                                        ClasseEnseignee classeEnseignee = professeurService.findClasseEnseignee(professeur.getId(), id);
                                        if (classeEnseignee == null) {
                                            System.out.println("Cette classe n'existe pas dans ce professeur");
                                        } else {
                                            List<Module> modules = classeService.show(classeEnseignee.getClasse().getId()).getModules();
                                            List<Module> modulesEnseignes = professeurService.findModulesEnseignesByClasse(classeEnseignee.getClasse().getId());
                                            Long id2;
                                            if (modules.size()==modulesEnseignes.size()) {
                                                clearScreen();
                                                System.out.println("\n\n    TOUS LES MODULES DE LA CLASSE SONT DÉJA PRIS PAR DES PROFS");
                                            } else {
                                                title("LES MODULES DE CETTE CLASSE", "+");
                                                for (Module mod : modules) {
                                                    if(!modulesEnseignes.contains(mod)){
                                                        System.out.println(mod);
                                                    }
                                                }
                                                System.out.println("Entrez l'ID pour affecter le module à la classe:");
                                                id2 = sc.nextLong();
                                                sc.nextLine();{
                                                    module = moduleService.show(id2);
                                                    if (module == null || !modules.contains(module) ) {
                                                        System.out.println("Ce module n'existe pas");
                                                    } else {
                                                        if (modulesEnseignes.contains(module)) {
                                                            System.out.println("Ce module est déjà enseigné dans cette classe");
                                                        }else{
                                                            if (confirmer("l'enseignement de ce module dans cette classe")==1) {
                                                                professeur.getClassesEnseignees().remove(classeEnseignee);
                                                                classeEnseignee.getModules().add(module);
                                                                professeur.getClassesEnseignees().add(classeEnseignee);
                                                                professeurService.save(professeur);
                                                                clearScreen();
                                                                System.out.println("\n\n     MODULE AFFECTÉ  AVEC SUCCÈS");
                                                            }else{
                                                                clearScreen();
                                                                System.out.println("\n\n          AFFECTATION DE MODULE ANNULÉE\n");
                                                            }
                                                        }
                                                    }     
                                                }
                                            }        
                                        }       
                                    }
                                }
                                continuer();
                                break;
                        
                            case 7:
                                //Retirer Module de classe enseignee
                                title("SUPPRESSION DE MODULE DANS CLASSE D'UN PROF", "*");
                                professeurService.getAll().forEach(System.out::println);
                                System.out.println("Entrez l'ID du professeur :");
                                id = sc.nextLong();
                                sc.nextLine();
                                professeur = professeurService.show(id);
                                if (professeur==null) {
                                    System.out.println("\nCe professeur n'existe pas");
                                }else{
                                    clearScreen();
                                    List<ClasseEnseignee> classeEnseignees = professeur.getClassesEnseignees();
                                    if (classeEnseignees.isEmpty()) {
                                        System.out.println("\n Ce professeur n'enseigne aucune classe");
                                    } else {
                                        classeEnseignees.forEach(System.out::println);
                                        System.out.println("Entrez l'ID de la classe pour retirer un module :");
                                        id = sc.nextLong();
                                        sc.nextLine();
                                        ClasseEnseignee classeEnseignee = professeurService.findClasseEnseignee(professeur.getId(), id);
                                        if (classeEnseignee == null) {
                                            System.out.println("Cette classe n'existe pas dans ce professeur");
                                        } else {
                                            title("LES MODULES ENSEIGNÉS DE CETTE CLASSE PAR LE PROF", "+");
                                            for (Module mod : classeEnseignee.getModules()) {
                                                System.out.println(mod);
                                            }
                                            System.out.println("Entrez l'ID pour retirer le module de la classe:");
                                            Long id2 = sc.nextLong();
                                            sc.nextLine();
                                            module = moduleService.show(id2);
                                            if (module == null || !classeEnseignee.getModules().contains(module) ) {
                                                System.out.println("Ce module n'existe pas");
                                            } else {
                                                if (confirmer("l'annulation de l'enseignement de ce module dans cette classe")==1) {
                                                    professeur.getClassesEnseignees().remove(classeEnseignee);
                                                    classeEnseignee.getModules().remove(module);
                                                    professeur.getClassesEnseignees().add(classeEnseignee);
                                                    professeurService.save(professeur);
                                                    clearScreen();
                                                    System.out.println("\n\n     MODULE SUPPRIMÉ  AVEC SUCCÈS");
                                                }else{
                                                    clearScreen();
                                                    System.out.println("\n\n          RETRAIT DE MODULE ANNULÉ\n");
                                                }
                                            }     
                                        }        
                                    }       
                                }
                                continuer();
                                break;
                            case 8:
                                //Archiver
                                title("ARCHIVER UN PROFESSEUR", "+");
                                professeurService.getAll().forEach(System.out::println);
                                System.out.println("\nEntrez l'ID du professeur à Archiver :");
                                id = sc.nextLong();
                                sc.nextLine();
                                professeur = professeurService.show(id);
                                if (professeur==null) {
                                    System.out.println("Ce professeur n'existe pas");
                                }else{
                                    if (confirmer("l'archivage de ce professeur")==1) {
                                        professeur.setIsArchived(true);
                                        professeurService.save(professeur);
                                        clearScreen();
                                        System.out.println("\n\n          LE PROFESSEUR "+professeur.getNomComplet().toUpperCase()+ " EST ARCHIVÉ AVEC SUCCÈS\n");
                                    }else{
                                        clearScreen();
                                       System.out.println("\n\n          ARCHIVAGE DU PROFESSEUR "+professeur.getNomComplet().toUpperCase()+" ANNULÉ\n"); 
                                    }
                                }
                                continuer();
                                break;
                        
                            default:
                                break;
                        }
                    } while (choix6!=9);
                    break;
            
                case 7:
                    //Lister Classe enseignee d'un prof
                    title("AFFICHAGE DES CLASSES D'UN PROFESSEUR AVEC LES MODULES", "*");
                    professeurService.getAll().forEach(System.out::println);
                    System.out.println("Entrez L'ID du professeur pour voir ses classes :");
                    id = sc.nextLong();
                    sc.nextLine();
                    professeur = professeurService.show(id);
                    if (professeur==null) {
                        System.out.println("Ce professeur n'existe pas !");
                    }else{
                        if(professeur.getClassesEnseignees().isEmpty()){
                            System.out.println("\nCe professeur n'enseigne aucune classe");
                        }else{
                            title("LISTE DES CLASSES DU PROFESSEUR  "+professeur.getNomComplet().toUpperCase(), "*");
                            professeur.getClassesEnseignees().forEach(System.out::println);
                        }
                    }
                    continuer();
                    break;
            
                case 8:
                    //Gerer Salle
                    int choix8;
                    do {
                        choix8=menuSalle();
                        switch (choix8) {
                            case 1:
                                //Ajouter salle
                                title("AJOUT DE SALLE", "+");
                                System.out.println("Entrez le libellé de la salle :");
                                libelle = sc.nextLine();
                                System.out.println("Entrez le nombre de place de la salle :");
                                nbre = sc.nextLong();
                                sc.nextLine();
                                if (confirmer("l'ajout de la salle")==1) {
                                    clearScreen();
                                    try {
                                        salle = new Salle(libelle, nbre);
                                        salleService.save(salle);
                                        System.out.println("\n\n          SALLE AJOUTÉE AVEC SUCCÈS\n");
                                    } catch (Exception e) {
                                        System.out.println("\n\n          UNE SALLE DE CE NOM EXISTE DÉJA\n");
                                    }
                                }else{
                                    clearScreen();
                                    System.out.println("\n\n          AJOUT DE SALLE ANNULÉ\n");
                                }
                                continuer();
                                break;
                            case 2:
                                //Lister salle
                                title("LISTE DES SALLES", "+");
                                salleService.getAll().forEach(System.out::println);
                                continuer();
                                break;
                            case 3:
                                //Editer
                                title("ÉDITER UNE SALLE", "+");
                                salleService.getAll().forEach(System.out::println);
                                System.out.println("Entrez l'ID de la salle à éditer :");
                                id = sc.nextLong();
                                sc.nextLine();
                                salle = salleService.show(id);
                                if (salle==null) {
                                    System.out.println("Cette salle n'existe pas");
                                }else{
                                    System.out.println("Entrez le nouveau libelle [0 ==>Conserver l'ancien]");
                                    libelle = sc.nextLine();
                                    if (!libelle.equals("0")) {
                                        salle.setLibelle(libelle);
                                    }
                                    System.out.println("Entrez le nouveau nbre de place [0 ==>Conserver l'ancien]");
                                    nbre = sc.nextLong();
                                    sc.nextLine();
                                    if (nbre != 0L ) {
                                        salle.setNbrePlace(nbre);
                                    }
                                    if (confirmer("la modification de cette salle")==1) {
                                        clearScreen();
                                        salleService.save(salle);
                                        System.out.println("\n\n          SALLE MODIFIÉE AVEC SUCCÈS\n");
                                    }else{
                                        clearScreen();
                                       System.out.println("\n\n          MODIFICATION DE SALLE ANNULÉE\n"); 
                                    }
                                }
                                continuer();
                                break;
                            case 4:
                                //Archiver salle
                                title("ARCHIVER UNE SALLE", "+");
                                salleService.getAll().forEach(System.out::println);
                                System.out.println("Entrez l'ID de la salle à Archiver :");
                                id = sc.nextLong();
                                sc.nextLine();
                                salle = salleService.show(id);
                                if (salle==null) {
                                    System.out.println("Cette salle n'existe pas");
                                }else{
                                    if (confirmer("l'archivage de cette salle")==1) {
                                        clearScreen();
                                        salle.setIsArchived(true);
                                        salleService.save(salle);
                                        System.out.println("\n\n          LA SALLE "+salle.getLibelle().toUpperCase()+ " EST ARCHIVÉE AVEC SUCCÈS\n");
                                    }else{
                                        clearScreen();
                                       System.out.println("\n\n          ARCHIVAGE DE LA SALLE "+module.getLibelle().toUpperCase()+ " ANNULÉ\n"); 
                                    }
                                }
                                continuer();
                                break;
                                
                            default:
                                break;
                        }
                    } while (choix8!=5);
                    break;
            
                case 9:
                    //Planifier cours
                    Date date =null;
                    int dateIsValid=0;
                   // title("PLANIFICATION DE COURS", "*");
                    do {
                        System.out.println("\nEntrez la date du cours sous format AAAA-MM-JJ :");
                        try {
                            date = formatter.parse(sc.nextLine());;
                            if (date.before(dateDuJour)) {
                                System.out.println("\n    Un cours ne peut se faire dans le passé!");
                            } else {
                                dateIsValid=1;
                            }
                        } catch (Exception e) {
                            System.out.println("\n    Le Format de la Date est INCORRECT");
                        }
                    } while (dateIsValid==0);
                    LocalTime heureD = saisieHeureDebut(date);
                    if (heureD!=null) {
                        LocalTime heureF = saisieHeureFin(heureD);
                        title("CHOIX DU MODULE", "*");
                        moduleService.getAll().forEach(System.out::println);
                        System.out.println("\nVeuillez entrer l'ID du module de ce cours :");
                        id = sc.nextLong();
                        sc.nextLine();
                        module = moduleService.show(id);
                        if (module==null) {
                            System.out.println("\n Ce module n'existe pas !");
                        }else{
                            title("CHOIX DU PROFESSEUR", "*");
                            List<Professeur> professeurs = professeurService.findProfDisponibles(module, date, heureD, heureF);
                            if (professeurs.isEmpty()) {
                                System.out.println("\nAucun professeur disponible pour cet horaire de cours");
                            } else {
                                professeurs.forEach(System.out::println);
                                System.out.println("\nVeuillez entrer l'ID du professeur de ce cours :");
                                id = sc.nextLong();
                                sc.nextLine();
                                professeur = professeurService.show(id);
                                if(professeur==null || !professeurs.contains(professeur)){
                                    System.out.println("Ce professeur n'existe pas !");
                                }else{
                                    title("CHOIX DE LA CLASSE", "*");
                                    List<Classe> classes = classeService.findClasseDisponibles(module, professeur, date, heureD, heureF);
                                    classes.forEach(System.out::println);
                                    if (classes.isEmpty()) {
                                        System.out.println("\nAucune Classe disponible pour cet horaire de cours");
                                    } else {
                                        List<Classe> classesDuCours = saisieClassesDisponibles(classes);
                                        Long nbrePlace = calculateNbrePlace(classesDuCours);
                                        int choixLieu;
                                        String codeCours = null;
                                        salle = null;
                                        do {
                                            title("LIEU DU COURS", "*");
                                            System.out.println("\nOù se fera le cours ?");
                                            System.out.println("1..............En Ligne");
                                            System.out.println("2..............En Présentiel");
                                            choixLieu = sc.nextInt();
                                        } while (choixLieu!=1 && choixLieu!=2);
                                        sc.nextLine();
                                        int choixFaireCours=1;
                                        if (choixLieu==1) {
                                            System.out.println("\nVeuillez Entrez le code du cours :");
                                            codeCours = sc.nextLine();
                                        } else {
                                            title("CHOIX DE LA SALLE", "*");
                                            List<Salle> salles = salleService.findSalleDisponibles(nbrePlace, date, heureD, heureF);
                                            if (salles.isEmpty()) {
                                                System.out.println("\nAucune Salle disponible pour ce cours");
                                                do {
                                                    System.out.println("\nSouhaitez-vous le programmer en ligne ?\n");
                                                    System.out.println("1.................OUI");
                                                    System.out.println("2.................NON");
                                                    choixFaireCours=sc.nextInt();
                                                } while (choixFaireCours!=1 && choixFaireCours!=2);
                                                sc.nextLine();
                                                if (choixFaireCours==1) {
                                                    System.out.println("\nVeuillez Entrez le code du cours :");
                                                    codeCours = sc.nextLine();
                                                } 
                                            } else {
                                                salles.forEach(System.out::println);
                                                do {
                                                    System.out.println("\nVeuillez entrer l'ID de la salle du cours");
                                                    id = sc.nextLong();
                                                    salle = salleService.show(id);
                                                    if (salle==null || !salles.contains(salle)) {
                                                        System.out.println("Cette salle n'existe pas !");
                                                    }
                                                } while (salle==null || !salles.contains(salle)); 
                                                sc.nextLine();
                                            }
                                        }
                                        if (choixFaireCours==1) {
                                            cours = new Cours(date, heureD, heureF, codeCours, module, professeur, salle, classesDuCours);
                                            title("RECAPITULATIF DU COURS", "*");
                                            System.out.println(cours);
                                            if (confirmer("l'enregistrement de ce cours ")==1) {
                                                coursService.save(cours);
                                                clearScreen();
                                                System.out.println("\n\n          COURS PLANIFIÉ AVEC SUCCÈS\n");
                                            } else {
                                                clearScreen();
                                                System.out.println("\n\n          ENREGISTREMENT DE COURS ANNULÉ\n");
                                            }  
                                        }
                                    
                                    }
                                }
                            }
                        } 
                    } 
                    continuer();
                    break;
            
                case 10:
                    //Lister cours d'une classe
                    title("AFFICHAGE DES COURS D'UNE CLASSE", "*");
                    classeService.getAll().forEach(System.out::println);
                    System.out.println("Entrez L'ID de la classe pour voir ses cours :");
                    id = sc.nextLong();
                    sc.nextLine();
                    classe = classeService.show(id);
                    if (classe==null) {
                        System.out.println("Cette classe n'existe pas !");
                    }else{
                        if(classe.getCours().isEmpty()){
                            System.out.println("\nCette classe n'a aucun cours prévu");
                        }else{
                            title("LISTE DES COURS DE "+classe.getLibelle(), "*");
                            classe.getCours().forEach(System.out::println);
                        }
                    }
                    continuer();
                    break;
                    
                    case 11:
                        //Lister cours d'un prof
                        title("AFFICHAGE DES COURS D'UN PROFESSEUR", "*");
                        professeurService.getAll().forEach(System.out::println);
                        System.out.println("Entrez L'ID du professeur pour voir ses cours :");
                        id = sc.nextLong();
                        sc.nextLine();
                        professeur = professeurService.show(id);
                        if (professeur==null) {
                            System.out.println("Ce professeur n'existe pas !");
                        }else{
                            if(professeur.getCours().isEmpty()){
                                System.out.println("\n  Ce professeur n'a aucun cours prévu");
                            }else{
                                title("LISTE DES COURS DE "+professeur.getNomComplet().toUpperCase(), "*");
                                professeur.getCours().forEach(System.out::println);
                            }
                        }
                        continuer();
                        break;
            
                case 12:
                    //Quitter
                    clearScreen();
                    System.out.println("\n\n        AU REVOIR ET À BIENTÔT :)\n\n");
                    break;
            
                default:
                    break;
            }
        } while (choix!=12);
        sc.close(); 
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

    public static void menu(){
        title("MENU GÉNÉRAL", "=");
        System.out.println("1....................Gérer Classe");
        System.out.println("2....................Gérer Module");
        System.out.println("3....................Affecter Module à Classe");
        System.out.println("4....................Afficher les Modules d'une Classe");
        System.out.println("5....................Afficher les Classes qui font un Module");
        System.out.println("6....................Gérer Professeur");
        System.out.println("7....................Afficher les Classes d'un Prof avec les Modules enseignés");
        System.out.println("8....................Gérer Salle");
        System.out.println("9....................Planifier un Cours");
        System.out.println("10...................Afficher les Cours d'une Classe");
        System.out.println("11...................Afficher les Cours d'un Professeur");
        System.out.println("12...................Quitter");
        System.out.println("\nVeuillez entrer votre choix :");
    }

    public static int menuClasse(){
        int choice;
        do {
            title("GESTION DES CLASSES", "*");
            System.out.println("1....................Ajouter une Classe");
            System.out.println("2....................Lister les Classes");
            System.out.println("3....................Editer une Classe");
            System.out.println("4....................Retirer un Module d'une Classe");
            System.out.println("5....................Archiver une Classe");
            System.out.println("6...................Quitter");
            System.out.println("\nVeuillez entrer votre choix :");
            choice = sc.nextInt();
        } while (choice<1 || choice>6);
        sc.nextLine();
        return choice;
    }

    public static int menuModule(){
        int choice;
        do {
            title("GESTION DES MODULES", "*");
            System.out.println("1....................Ajouter un Module");
            System.out.println("2....................Lister les Modules");
            System.out.println("3....................Editer un Module");
            System.out.println("4....................Archiver un Module");
            System.out.println("5...................Quitter");
            System.out.println("\nVeuillez entrer votre choix :");
            choice = sc.nextInt();
        } while (choice<1 || choice>5);
        sc.nextLine();
        return choice;
    }

    public static int menuSalle(){
        int choice;
        do {
            title("GESTION DES SALLES", "*");
            System.out.println("1....................Ajouter une Salle");
            System.out.println("2....................Lister les Salles");
            System.out.println("3....................Editer une Salle");
            System.out.println("4....................Archiver une Salle");
            System.out.println("5...................Quitter");
            System.out.println("\nVeuillez entrer votre choix :");
            choice = sc.nextInt();
        } while (choice<1 || choice>5);
        sc.nextLine();
        return choice;
    }

    public static int menuProfesseur(){
        int choice;
        do {
            title("GESTION DES PROFESSEURS", "*");
            System.out.println("1....................Ajouter un Professeur");
            System.out.println("2....................Lister les Professeurs");
            System.out.println("3....................Editer un Professeur");
            System.out.println("4....................Ajouter une Classe et les modules enseignés");
            System.out.println("5....................Retirer une Classe enseignée");
            System.out.println("6....................Ajouter dans une Classe, un Module enseigné");
            System.out.println("7....................Retirer d'une Classe enseignée, un Module enseigné");
            System.out.println("8....................Archiver un Professeur");
            System.out.println("9...................Quitter");
            System.out.println("\nVeuillez entrer votre choix :");
            choice = sc.nextInt();
        } while (choice<1 || choice>9);
        sc.nextLine();
        return choice;
    }

    public static int confirmer(String msg){
        int ch;
        do {
            System.out.println("Confirmer "+msg+" ? [0==>NON & 1==>OUI]");
            ch = sc.nextInt();
        } while (ch!=0 && ch!=1);
        sc.nextLine();
        return ch;
    }

    public static Long checkClasseInClassesEnseignees(List<ClasseEnseignee> classesEnseignees,Classe classe){
        for (ClasseEnseignee cl : classesEnseignees) {
            if (cl.getClasse().getId()==classe.getId()) {
                return classe.getId();
            }
        }
        return -1L;
    }

    public static List<Module> saisieModulesEnseignes(Classe classe){
        List<Module> modules = classe.getModules();
        List<Module> moduleSaisis = new ArrayList<>();
        List<Module> modulesEnseignes = professeurService.findModulesEnseignesByClasse(classe.getId());

        Long id2;
        if (modules.size()==modulesEnseignes.size()) {
            clearScreen();
            System.out.println("\n\n    TOUS LES MODULES DE LA CLASSE SONT DÉJA PRIS PAR DES PROFS");
        } else {
            do {
                title("LES MODULES ENSEIGNÉS DANS CETTE CLASSE", "+");
                for (Module mod : modules) {
                    if(!moduleSaisis.contains(mod) && !modulesEnseignes.contains(mod)){
                        System.out.println(mod);
                    }
                }
                System.out.println("Entrez l'ID pour affecter le module enseigné [0 ==> Enregistrer] :");
                id2 = sc.nextLong();
                sc.nextLine();
                if(id2!=0L){
                    Module module = moduleService.show(id2);
                    if (module == null || !modules.contains(module) ) {
                        System.out.println("Ce module n'existe pas");
                    } else {
                        if (moduleSaisis.contains(module) || modulesEnseignes.contains(module)) {
                            System.out.println("Ce module est déjà enseigné dans cette classe");
                        }else{
                            moduleSaisis.add(module);
                            System.out.println("Le module a été ajouté pour enseignement");
                        }
                    }
                }else{
                    if(moduleSaisis.isEmpty()){
                        System.out.println("Veuillez mentionner au moins un module enseigné");
                    }else{
                        clearScreen();
                        System.out.println("\n\n     LES MODULES SONT BIEN ENREGISTRÉS");
                    }
                }
                continuer();
            } while (moduleSaisis.isEmpty() || id2!=0);
        }
        return moduleSaisis;
    }


    public static List<ClasseEnseignee> saisieClasseEnseignee(Professeur profeseur){
        List<ClasseEnseignee> classesEnseignees = new ArrayList<>();
        List<Classe> classes = classeService.getAll();
        Long id;
        do {
            ClasseEnseignee classeEnseignee = new ClasseEnseignee();
            title("AFFECTER AU PROFESSEUR LES CLASSES", "+");
            for (Classe cl : classes) {
                if(checkClasseInClassesEnseignees(classesEnseignees, cl)==-1){
                    System.out.println(cl);
                }
            }
            System.out.println("Entrez l'ID pour affecter la classe au prof [0 ==> Enregistrer] :");
            id = sc.nextLong();
            sc.nextLine();
            if(id!=0L){
                Classe classe = classeService.show(id);
                if (classe == null) {
                    System.out.println("Cette Classe n'existe pas");
                } else {
                    if (checkClasseInClassesEnseignees(classesEnseignees, classe)!=-1) {
                        System.out.println("\nCette classe lui a déjà été affectée");
                    }else{
                        classeEnseignee.setClasse(classe);
                        List<Module> modules = classe.getModules();
                        if(modules.isEmpty()){
                            System.out.println("Cette classe n'apprend aucun module pour le moment !");
                        }else{
                            List<Module> modulesSaisis = saisieModulesEnseignes(classe);
                            if (modulesSaisis.isEmpty()) {
                                clearScreen();
                                System.out.println("\n\n          AFFECTION DE CLASSE AVORTÉE \n          CETTE CLASSE A DÉJA TOUS SES MODULES AFFECTÉS A DES PROFS\n");
                            } else {
                                classeEnseignee.getModules().addAll(modulesSaisis);
                                classeEnseignee.setProfesseur(profeseur);
                                classesEnseignees.add(classeEnseignee);
                                clearScreen();
                                System.out.println("\n\n          CLASSE AJOUTÉE AU PROF AVEC SUCCÈS\n");
                            }
                        }
                    }
                }
            }else{
                if (classesEnseignees.isEmpty()) {
                    System.out.println("Veuillez lui affecter au moins une classe");
                }else{
                    clearScreen();
                    System.out.println("\n\n         LES CLASSES ENSEIGNÉES SONT BIEN ENREGISTRÉES\n");  
                }
            }
            continuer();
        } while (classesEnseignees.isEmpty() || id !=0);

        return classesEnseignees;
    }

    public static LocalTime saisieHeureDebut(Date date){
        int succes = 0;
        LocalTime heureD = null;
        if (date.equals(dateDuJour)) {
            if (heureActuelle.isAfter(LocalTime.parse("17:00:00"))) {
                System.out.println("\nIl est déjà 17h00  !\nVous ne pouvez plus programmer de cours pour aujourd'hui !");
            } else {
                do {
                    System.out.println("\nEntrez l'heure de début du cours sous format hh:mm :");
                    String heure = sc.nextLine();
                    heure=heure+":00";
                    try {
                        heureD = LocalTime.parse(heure);
                        if(heureD.isBefore(LocalTime.parse("08:00:00")) || heureD.isAfter(LocalTime.parse("17:00:00"))){
                            System.out.println("\nL'heure de début doit être entre 08h et 17h");
                        }else{
                            if (heureD.isBefore(heureActuelle)) {
                                System.out.println("\nComment veux-tu faire un cours aujourd'hui à "+heureD+ " S'il est déjà "+heureActuelle+" ?");
                                System.out.println("Tu penses faire du mal à qui ?");
                            }else{
                                succes = 1;
                            } 
                        }
                    } catch (Exception e) {
                        System.out.println("\n        LE FORMAT DE L'HEURE EST INCORRECT\n");
                    }
                } while (succes==0);
            }
        }else{
            do {
                System.out.println("\nEntrez l'heure de début du cours sous format hh:mm :");
                String heure = sc.nextLine();
                heure=heure+":00";
                try {
                    heureD = LocalTime.parse(heure);
                    if(heureD.isBefore(LocalTime.parse("08:00:00")) || heureD.isAfter(LocalTime.parse("17:00:00"))){
                        System.out.println("\nL'heure de début doit être entre 08h et 17h");
                    }else{
                        succes = 1;
                    }
                } catch (Exception e) {
                    System.out.println("\n        LE FORMAT DE L'HEURE EST INCORRECT\n");
                }
            } while (succes==0);
        }
        return heureD;
    }

    public static LocalTime saisieHeureFin(LocalTime heureD){
        int succes = 0;
        LocalTime heureF = null;
        do {
            System.out.println("\nEntrez l'heure de fin du cours sous format hh:mm :");
            String heure = sc.nextLine();
            heure=heure+":00";
            try {
                heureF = LocalTime.parse(heure);
                if(heureF.isBefore(heureD) || heureF.isAfter(LocalTime.parse("21:00:00"))){
                    System.out.println("\nL'heure de fin doit être entre "+ heureD+ " et 21:00:00");
                }else{
                    long difference = Duration.between(heureD, heureF).getSeconds()/(60);
                    if (difference<60) {
                        System.out.println("\nLa durée du cours ne peut être inférieure à 1h");
                    }else{
                        if(difference>240){
                            System.out.println("\nLa durée du cours ne peut excéder 4h");
                        }else{
                            succes=1;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("\n        LE FORMAT DE L'HEURE EST INCORRECT\n");
            }
        } while (succes==0);
        return heureF;
    }

    public static List<Classe> saisieClassesDisponibles(List<Classe> classes){
        List<Classe> classesSaisies = new ArrayList<>();
        Long id2;
            Niveau niveau = null;
            do {
                title("CHOIX DES CLASSES DU COURS", "+");
                for (Classe cl : classes) {
                    if(!classesSaisies.contains(cl)){
                        System.out.println(cl);
                    }
                }
                System.out.println("Entrez l'ID de la classe pour l'ajouter à ce cours [0 ==> Enregistrer] :");
                id2 = sc.nextLong();
                sc.nextLine();
                if(id2!=0L){
                    Classe classe = classeService.show(id2);
                    if (classe == null || !classes.contains(classe) ) {
                        System.out.println("\nCette classe n'existe pas");
                    } else {
                        if (classesSaisies.contains(classe)) {
                            System.out.println("Cette classe a déjà été ajoutée à ce cours");
                        }else{
                            if (niveau==null) {
                                classesSaisies.add(classe);
                                niveau = classe.getNiveau();
                                System.out.println("\nLa classe a été ajoutée pour suivre ce cours"); 
                            } else {
                                if (classe.getNiveau().equals(niveau)) {
                                    classesSaisies.add(classe);
                                    System.out.println("\nLa classe a été ajoutée pour suivre ce cours");
                                } else {
                                    System.out.println("\nSeules les classes de même niveau peuvent suivre le même cours");
                                }
                            }
                            
                        }
                    }
                }else{
                    if(classesSaisies.isEmpty()){
                        System.out.println("Veuillez mentionner au moins une classe");
                    }else{
                        clearScreen();
                        System.out.println("\n\n     LES CLASSES SONT BIEN ENREGISTRÉS POUR CE COURS");
                    }
                }
                continuer();
            } while (classesSaisies.isEmpty() || id2!=0);

        return classesSaisies;
    }

    public static Long calculateNbrePlace(List<Classe> classes){
        Long nbrePlace=0L;
        for (Classe classe : classes) {
            nbrePlace = nbrePlace+classe.getEffectif();
        }
        return nbrePlace;
    }

}