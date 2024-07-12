package ism.entities;

import java.util.ArrayList;
import java.util.List;

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
@Table(name="salles")

@NamedQueries({
    @NamedQuery(name = "SELECT_ALL_SALLE",query = "select s from Salle s where is_archived=:isArchived"),
    @NamedQuery(name = "SELECT_SALLE_BY_ID",query = "select s from Salle s where is_archived=:isArchived and id=:id")

})
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true,length = 20)
    private String libelle;
    private Long nbrePlace;
    @Column(name="is_archived",columnDefinition = "boolean default false")
    private Boolean isArchived = false;
    
    //Attribut Navigationnel
    @OneToMany(mappedBy = "salle")
    private List<Cours>cours = new ArrayList<>();

    public Salle(String libelle, Long nbrePlace) {
        this.libelle = libelle;
        this.nbrePlace = nbrePlace;
    }

    public Salle(Long id, String libelle, Long nbrePlace, Boolean isArchived) {
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
