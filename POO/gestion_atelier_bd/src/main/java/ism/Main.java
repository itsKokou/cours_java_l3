package ism;

import java.util.Arrays;
import java.util.Scanner;

import ism.entities.ArticleConfection;
import ism.entities.Categorie;
import ism.entities.Unite;
import ism.repositories.bd.CategorieRepository;
import ism.repositories.bd.impl.CategorieRepositoryImpl;
import ism.repositories.core.Database;
import ism.repositories.core.ITables;
import ism.repositories.core.MySqlImpl;
import ism.repositories.list.TableArticleConfections;
import ism.repositories.list.TableCategories;
import ism.repositories.list.TableUnites;
import ism.services.ArticleConfectionService;
import ism.services.ArticleConfectionServiceImpl;
import ism.services.CategorieService;
import ism.services.CategorieServiceImpl;
import ism.services.UniteService;
import ism.services.UniteServiceImpl;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        
        int choix,choix2=0;

        //Dependance MySql
        Database database = new MySqlImpl();

        CategorieRepository categorieRepository = new CategorieRepositoryImpl(database);
        CategorieService categorieService = new CategorieServiceImpl(categorieRepository);

        ITables<Unite> uniteRepository = new TableUnites();
        UniteService uniteService = new UniteServiceImpl(uniteRepository);

        ITables<ArticleConfection> articleRepository = new TableArticleConfections();
        ArticleConfectionService articleService = new ArticleConfectionServiceImpl(articleRepository);

        Categorie categorie;
        Unite unite;
        ArticleConfection article;


        do {
            System.out.println("\033c");
            System.out.println("*********************************");
            System.out.println("             MENU");
            System.out.println("*********************************");
            System.out.println("1......Ajouter");
            System.out.println("2......Lister");
            System.out.println("3......Lister par ID");
            System.out.println("4......Modifier");
            System.out.println("5......Supprimer");
            System.out.println("6......Supprimer plusieurs éléments");
            System.out.println("7......Quitter");
            choix = sc.nextInt();
            sc.nextLine();
            System.out.println("\033c");
            if (choix>=1 && choix<=7) {
                choix2 = sousMenu();
            }
            switch (choix) {
                case 1:
                    System.out.println("Entrez l'Id :");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Entrez le libelle :");
                    String lib = sc.nextLine();
                    if (choix2==1) {
                        System.out.println("Entrez le prix :");
                        double prix = sc.nextDouble();
                        System.out.println("Entrez la quantité :");
                        double qte = sc.nextDouble();
                        sc.nextLine();
                        article = new ArticleConfection(id, lib, prix, qte);
                        articleService.add(article);
                    } else {
                        if (choix2==2) {
                            categorie = new Categorie(id, lib);
                            categorieService.add(categorie);
                        } else {
                            unite = new Unite(id, lib);
                            uniteService.add(unite);
                        }
                    }
                    break;
                    
                case 2:
                    if (choix2==1) {
                        articleService.getAll().forEach(System.out::println);
                        continuer();
                    } else {
                        if (choix2==2) {
                            categorieService.getAll().forEach(System.out::println);
                            continuer();
                        } else {
                            uniteService.getAll().forEach(System.out::println);
                            continuer();
                        }
                    }
                    break;
            
                case 3:
                    System.out.println("Entrez l'Id :");
                    id = sc.nextInt();
                    sc.nextLine();
                    if (choix2==1) {
                        article = articleService.show(id);
                        if(article==null){
                            System.out.println("Cet article n'existe pas.");
                        }else{
                            System.out.println(article);
                        }
                        continuer();
                    } else {
                        if (choix2==2) {
                            categorie = categorieService.show(id);
                            if(categorie==null){
                                System.out.println("Cette categorie n'existe pas.");
                            }else{
                                System.out.println(categorie);
                            }
                            continuer();
                        } else {
                            unite = uniteService.show(id);
                            if(unite==null){
                                System.out.println("Cette unite n'existe pas.");
                            }else{
                                System.out.println(unite);
                            }
                            continuer();
                        }
                    }

                    break;
            
                case 4:
                    System.out.println("Entrez l'Id :");
                    id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("\033c");
                    if (choix2==1) {
                        article = articleService.show(id);
                        if(article==null){
                            System.out.println("Cet article n'existe pas.");
                        }else{
                            int ch;
                            do {
                                System.out.println("1.......Editer article");
                                System.out.println("2.......Ajouter Categorie");
                                System.out.println("3.......Ajouter Unite");
                                ch = sc.nextInt();
                            } while (ch<1 || ch>3);
                            sc.nextLine();

                            if (ch==1) {
                                System.out.println(article);
                                System.out.println("\nEntrez le nouveau libelle [0==>Conserver l'ancien] :");
                                lib = sc.nextLine();
                                if(!lib.equals("0")){
                                    article.setLibelle(lib);
                                }
                                System.out.println("\nEntrez le nouveau prix [0==>Conserver l'ancien] :");
                                double prix = sc.nextDouble();
                                if(prix!=0){
                                    article.setPrix(prix);;
                                }
                                System.out.println("\nEntrez la nouvelle quantité [0==>Conserver l'ancienne] :");
                                double qte = sc.nextDouble();
                                if(qte!=0){
                                    article.setQte(qte);;
                                }
                                
                            } else {
                                if (ch==2) {
                                    System.out.println("Entrez l'Id de la categorie:");
                                    id = sc.nextInt();
                                    sc.nextLine();
                                    System.out.println("\033c");
                                    categorie = categorieService.show(id);
                                    if(categorie==null){
                                        System.out.println("Cette categorie n'existe pas.");
                                    }else{
                                        article.setCategorie(categorie);
                                        System.out.println("Cette categorie Ajoutee");
                                    }
                                } else {
                                    System.out.println("Entrez l'Id de l'unite:");
                                    id = sc.nextInt();
                                    sc.nextLine();
                                    System.out.println("\033c");
                                    unite = uniteService.show(id);
                                    if(unite==null){
                                        System.out.println("Cette unite n'existe pas.");
                                    }else{
                                        article.addUnite(unite);
                                        System.out.println("Unite Ajoutee");
                                    }
                                }
                            }
                        }
                        articleService.update(article);
                        continuer();
                    } else {
                        if (choix2==2) {
                            categorie = categorieService.show(id);
                            if(categorie==null){
                                System.out.println("Cette categorie n'existe pas.");
                            }else{
                                System.out.println(categorie);
                                System.out.println("\nEntrez le nouveau libelle [0==>Conserver l'ancien] :");
                                lib = sc.nextLine();
                                if(!lib.equals("0")){
                                    categorie.setLibelle(lib);
                                    categorieService.update(categorie);
                                }
                            }
                            continuer();
                        } else {
                            unite = uniteService.show(id);
                            if(unite==null){
                                System.out.println("Cette unite n'existe pas.");
                            }else{
                                System.out.println(unite);
                                System.out.println("\nEntrez le nouveau libelle [0==>Conserver l'ancien] :");
                                lib = sc.nextLine();
                                if(!lib.equals("0")){
                                    unite.setLibelle(lib);
                                    uniteService.update(unite);
                                }
                            }
                            continuer();
                        }
                    }
                    break;
            
                case 5:
                    System.out.println("Entrez l'Id :");
                    id = sc.nextInt();
                    sc.nextLine();
                    if (choix2==1) {
                        int result = articleService.remove(id);
                        if(result==0){
                            System.out.println("Cet article n'existe pas.");
                        }else{
                            System.out.println("Article Supprimé");
                        }
                        continuer();
                    } else {
                        if (choix2==2) {
                            int result = categorieService.remove(id);
                            if(result==0){
                                System.out.println("Cette categorie n'existe pas.");
                            }else{
                                System.out.println("Categorie supprimee");
                            }
                            continuer();
                        } else {
                            int result = uniteService.remove(id);
                            if(result==0){
                                System.out.println("Cette unite n'existe pas.");
                            }else{
                                System.out.println("Unite supprimee");
                            }
                            continuer();
                        }
                    }
                    break;
            
                case 6:
                    int n;
                    do {
                        System.out.println("Combien d'élément à supprimer ?");
                        n = sc.nextInt();
                    } while (n<=0);
                    sc.nextLine();
                    int[] ids = new int[n];
                    for (int i = 0; i < n; i++) {
                        System.out.println("Entrez l'Id :");
                        id = sc.nextInt();
                        ids[i]=id;
                    }
                    sc.nextLine();
                    if (choix2==1) {
                        int[] result = articleService.remove(ids);
                        if(result.length!=0){
                            System.out.println("La liste des non supprimés :"+Arrays.toString(result));
                        }else{
                            System.out.println("Articles Supprimés");
                        }
                        continuer();
                    } else {
                        if (choix2==2) {
                            int[] result = categorieService.remove(ids);
                            if(result.length!=0){
                                System.out.println("La liste des non supprimés :"+Arrays.toString(result));
                            }else{
                                System.out.println("Categories supprimees");
                            }
                            continuer();
                        } else {
                            int[] result = uniteService.remove(ids);
                            if(result.length!=0){
                                System.out.println("La liste des non supprimés :"+Arrays.toString(result));
                            }else{
                                System.out.println("Unites supprimees");
                            }
                            continuer();
                        }
                    }
                    break;
                case 7:
                    break;

                default:
                    break;
            }
        } while (choix!=7);

    }

    public static int sousMenu(){
        int choice;
        do {
            System.out.println("\033c");
            System.out.println("1......Article de Confection");
            System.out.println("2......Categorie");
            System.out.println("3......Unite");
            choice = sc.nextInt();
            sc.nextLine();
        } while (choice<1 || choice>3);
        System.out.println("\033c");
        return choice;
        
    }

    public static void continuer(){
        System.out.println("\n\nAppuyez sur une touche pour continuer...");
        sc.nextLine();
    }
}
