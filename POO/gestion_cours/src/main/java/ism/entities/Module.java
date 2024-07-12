package ism.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module {
    private int id;
    private String libelle;
    private Boolean isArchived;

    public Module(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString(){
        return  "------------------------------------------------\n"+
                "  MODULE [ ID = "+id+" | LIBELLÉ = "+libelle+" ]\n"+
                "------------------------------------------------";
    }
}
