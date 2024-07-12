package ism.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
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
@Table(name="classes")

@NamedQueries({
    @NamedQuery(name = "SELECT_ALL_CLASSE",query = "select c from Classe c where is_archived=:isArchived"),
    @NamedQuery(name = "SELECT_CLASSE_BY_ID",query = "select c from Classe c where is_archived=:isArchived and id=:id")
})
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,unique = true,length = 20)
    private String libelle;
    @Column(nullable=false)
    private Long effectif;
    @Column(name="is_archived",columnDefinition = "boolean default false")
    private Boolean isArchived = false;

    //Attributs navigationnels
    @ManyToOne
    @JoinColumn(nullable = false)
    private Niveau niveau;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Filiere filiere;
    
    @ManyToMany
    private List<Module>modules = new ArrayList<>();
    @ManyToMany(mappedBy = "classes")
    private List<Cours>cours = new ArrayList<>();

    public Classe(Long id, String libelle, Long effectif, Boolean isArchived, Niveau niveau, Filiere filiere) {
        this.id = id;
        this.libelle = libelle;
        this.effectif = effectif;
        this.isArchived = isArchived;
        this.niveau = niveau;
        this.filiere = filiere;
    }

    public Classe(Long id, String libelle, Long effectif, Boolean isArchived) {
        this.id = id;
        this.libelle = libelle;
        this.effectif = effectif;
        this.isArchived = isArchived;
    }

    public Classe(String libelle, Long effectif) {
        this.libelle = libelle;
        this.effectif = effectif;
    }

    public Classe(String libelle, Long effectif, Niveau niveau, Filiere filiere) {
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
