package ism.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="professeurs")

@NamedQueries({
    @NamedQuery(name = "SELECT_ALL_PROF",query = "select p from Professeur p where is_archived=:isArchived"),
    @NamedQuery(name = "SELECT_PROF_BY_ID",query = "select p from Professeur p where is_archived=:isArchived and id=:id")

})
public class Professeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,length = 20)
    private String nomComplet;
    @Column(nullable=false,unique = true,length = 20)
    private String portable;
    @Column(name="is_archived",columnDefinition = "boolean default false")
    private Boolean isArchived = false;

    //Attributs navigationnels
    @OneToMany(mappedBy = "professeur",cascade ={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<ClasseEnseignee> classesEnseignees = new ArrayList<>();
    @OneToMany(mappedBy = "professeur")
    private List<Cours>cours = new ArrayList<>();

    public Professeur(String nomComplet, String portable) {
        this.nomComplet = nomComplet;
        this.portable = portable;
    }

    public Professeur(Long id, String nomComplet, String portable, Boolean isArchived) {
        this.id = id;
        this.nomComplet = nomComplet;
        this.portable = portable;
        this.isArchived = isArchived;
    }

    public Professeur(String nomComplet, String portable, List<ClasseEnseignee> classesEnseignees) {
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
