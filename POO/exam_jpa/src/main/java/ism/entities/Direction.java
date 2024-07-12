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
@Table(name="directions")

@NamedQueries({
    @NamedQuery(name = "SELECT_ALL_DIRECTION",query = "from Direction"),
})
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,unique = true,length = 20)
    private String name;
    

    //Attributs navigationnels
    @OneToMany(mappedBy = "direction",cascade ={CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Secteur>secteurs = new ArrayList<>();

    
    
    public Direction(String name, List<Secteur> secteurs) {
        this.name = name;
        this.secteurs = secteurs;
    }


    public Direction(String name) {
        this.name = name;
    }



    @Override
    public String toString(){
        return  "-----------------------------------------------\n"+
                "  DIRECTION [ ID = "+id+" | NOM = "+name+" ]\n"+
                "-----------------------------------------------";
    }
    
}

