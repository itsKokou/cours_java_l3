package ism.entities;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professeur {
    private int id;
    private String nomComplet;
    private String portable;
    private Boolean isArchived;

    //Attributs navigationnels
    private ArrayList<ClasseEnseignee> classesEnseignees = new ArrayList<>();
    private ArrayList<Cours>cours = new ArrayList<>();

    public Professeur(int id, String nomComplet, String portable, Boolean isArchived) {
        this.id = id;
        this.nomComplet = nomComplet;
        this.portable = portable;
        this.isArchived = isArchived;
    }

    public Professeur(String nomComplet, String portable, ArrayList<ClasseEnseignee> classesEnseignees) {
        this.nomComplet = nomComplet;
        this.portable = portable;
        this.classesEnseignees = classesEnseignees;
    }

    

    @Override
    public String toString(){
        return  "-------------------------------------------------------------------------------------\n"+
                "  PROFESSEUR [ ID = "+id+" | NOM ET PRÉNOMS = "+nomComplet+" | TÉLÉPHONE = "+portable+" ]\n"+
                "-------------------------------------------------------------------------------------";
    }
}
