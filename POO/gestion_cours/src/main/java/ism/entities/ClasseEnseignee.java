package ism.entities;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClasseEnseignee {
    private int id;
    private Professeur professeur;
    private Classe classe;
    private ArrayList<Module> modules = new ArrayList<>();

    public ClasseEnseignee(Classe classe, ArrayList<Module> modules) {
        this.classe = classe;
        this.modules = modules;
    }

    public ClasseEnseignee(Professeur professeur, Classe classe, ArrayList<Module> modules) {
        this.professeur = professeur;
        this.classe = classe;
        this.modules = modules;
    }

    @Override
    public String toString(){
        String m = "";
        for (Module module : modules) {
            m=m+module.getLibelle()+", ";
        };
        return  "-------------------------------------------------------------------------------------------------------\n"+
                "  CLASSE [ ID = "+classe.getId()+" | LIBELLÉ = "+classe.getLibelle()+" | EFFECTIF = "+classe.getEffectif()+" | MODULES ENSEIGNÉS = "+m+ "]\n"+
                "-------------------------------------------------------------------------------------------------------";
    }

    
}
