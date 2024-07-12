package ism.entities;

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
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="filieres")

@NamedQueries({
    @NamedQuery(name = "SELECT_ALL_F",query = "select f from Filiere f where is_archived=:isArchived"),
    @NamedQuery(name = "SELECT_F_BY_ID",query = "select f from Filiere f where is_archived=:isArchived and id=:id")

})
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true,length = 20)
    private String libelle;
    @Column(name="is_archived",columnDefinition = "boolean default false")
    private Boolean isArchived = false;

    @OneToMany(mappedBy = "filiere")
    private List<Classe>classes;

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