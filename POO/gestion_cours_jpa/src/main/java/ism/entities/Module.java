package ism.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name="modules")

@NamedQueries({
    @NamedQuery(name = "SELECT_ALL_M",query = "select m from Module m where is_archived=:isArchived"),
    @NamedQuery(name = "SELECT_M_BY_ID",query = "select m from Module m where is_archived=:isArchived and id=:id")

})
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true,length = 20)
    private String libelle;
    @Column(name="is_archived", columnDefinition = "boolean default false")
    private Boolean isArchived = false;

    @ManyToMany(mappedBy = "modules")
    private List<Classe> classes = new ArrayList<>();
    @OneToMany(mappedBy = "module")
    private List<Cours>cours = new ArrayList<>();

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
