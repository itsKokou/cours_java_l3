package ism.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Niveau {
    private int id;
    private String libelle;
    private Boolean isArchived;

    public Niveau(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString(){
        return  "------------------------------------------------\n"+
                "  NIVEAU [ ID = "+id+" | LIBELLÉ = "+libelle+" ]\n"+
                "------------------------------------------------";
    }
}