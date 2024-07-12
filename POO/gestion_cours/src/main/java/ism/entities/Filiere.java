package ism.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Filiere {
    private int id;
    private String libelle;
    private Boolean isArchived;

    public Filiere(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString(){
        return  "------------------------------------------------\n"+
                "  FILIERE [ ID = "+id+" | LIBELLÉ = "+libelle+" ]\n"+
                "------------------------------------------------";
    }
}