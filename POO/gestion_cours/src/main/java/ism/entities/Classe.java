package ism.entities;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classe {
    private int id;
    private String libelle;
    private int effectif;
    private Boolean isArchived;

    //Attributs navigationnels
    private Niveau niveau;
    private Filiere filiere;

    private ArrayList<Module>modules = new ArrayList<>();
    private ArrayList<Cours>cours = new ArrayList<>();

    public Classe(int id, String libelle, int effectif, Boolean isArchived, Niveau niveau, Filiere filiere) {
        this.id = id;
        this.libelle = libelle;
        this.effectif = effectif;
        this.isArchived = isArchived;
        this.niveau = niveau;
        this.filiere = filiere;
    }

    public Classe(int id, String libelle, int effectif, Boolean isArchived) {
        this.id = id;
        this.libelle = libelle;
        this.effectif = effectif;
        this.isArchived = isArchived;
    }

    public Classe(String libelle, int effectif) {
        this.libelle = libelle;
        this.effectif = effectif;
    }

    public Classe(String libelle, int effectif, Niveau niveau, Filiere filiere) {
        this.libelle = libelle;
        this.effectif = effectif;
        this.niveau = niveau;
        this.filiere = filiere;
    }
    

    
    @Override
    public String toString(){
        return  "---------------------------------------------------------------------------------------------------\n"+
                "  CLASSE [ ID = "+id+" | LIBELLÉ = "+libelle+" | EFFECTIF = "+effectif+" | NIVEAU = "+niveau.getLibelle()+" | FILIÈRE = "+filiere.getLibelle()+" ]\n"+
                "---------------------------------------------------------------------------------------------------";
    }
    
}
