package ism.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="classes_enseignees")

@NamedQueries({
    @NamedQuery(name = "SELECT_ALL_CE",query = "select ce from ClasseEnseignee ce"),
    @NamedQuery(name = "SQL_FIND_CE",query = "select ce from ClasseEnseignee ce where classe_id=:idC and professeur_id=:idProf"),
    @NamedQuery(name = "SQL_FIND_CE_BY_CLASSE",query = "select ce from ClasseEnseignee ce where classe_id=:idC")
})
public class ClasseEnseignee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne 
    @JoinColumn(nullable = false)
    private Professeur professeur;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Classe classe;
    @ManyToMany
    @JoinColumn(nullable = false)
    private List<Module> modules = new ArrayList<>();

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
