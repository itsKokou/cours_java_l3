package ism.entities;


import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salle {
    
    private int id;
    private String libelle;
    private int nbrePlace;
    private Boolean isArchived;
    
    //Attribut Navigationnel
    private ArrayList<Cours>cours = new ArrayList<>();

    public Salle(String libelle, int nbrePlace) {
        this.libelle = libelle;
        this.nbrePlace = nbrePlace;
    }

    public Salle(int id, String libelle, int nbrePlace, Boolean isArchived) {
        this.id = id;
        this.libelle = libelle;
        this.nbrePlace = nbrePlace;
        this.isArchived = isArchived;
    }
    
    @Override
    public String toString(){
        return  "-------------------------------------------------------------------\n"+
                "  SALLE [ ID = "+id+" | LIBELLÉ = "+libelle+" | NBRE DE PLACE = "+nbrePlace+" ]\n"+
                "-------------------------------------------------------------------";
    }
    
}
