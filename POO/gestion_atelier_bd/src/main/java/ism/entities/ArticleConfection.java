package ism.entities;

import java.util.ArrayList;

public class ArticleConfection extends AbstractEntity {

    private double prix;
    private double qte;

    //Attributs Navigationnels
    //@ManyToOne
    Categorie categorie;

    //@ManyToMany
    //Couplage Fort
    ArrayList <Unite> unites =new ArrayList<>();

  

    public void addUnite(Unite unite){
        unites.add(unite);
    }
  
    public ArrayList<Unite> getUnites() {
        return unites;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }


    
    public ArticleConfection(int id, String libelle, double prix, double qte) {
        super(id, libelle);
        this.prix = prix;
        this.qte = qte;
    }

    public ArticleConfection() {
        super();
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getQte() {
        return qte;
    }

    public void setQte(double qte) {
        this.qte = qte;
    }

    @Override
    public String toString() {
        return "ArticleConfection [id=" + id + ", libelle=" + libelle + ", prix=" + prix + ", qte=" + qte + "]"+
                "\nSa Categorie = "+this.getCategorie()+
                "\nSes Unites = "+this.getUnites();
    }

}
