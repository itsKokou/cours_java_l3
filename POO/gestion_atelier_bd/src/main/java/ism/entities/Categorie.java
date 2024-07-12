package ism.entities;

public class Categorie extends AbstractEntity {
 
    public Categorie(int id, String libelle) {
        super(id, libelle);
    }
 
    //Contructeur Par Defaut
    public Categorie() {
        super();
    }
 
    @Override
    public String toString() {
        return "Categorie "+super.toString();
    }

     
}
