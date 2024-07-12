package ism.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name="secteurs")

@NamedQueries({
    @NamedQuery(name = "SELECT_ALL_SECTEUR",query = "from Secteur"),
})
public class Secteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,unique = true,length = 20)
    private String name;
    @Column(nullable=false,unique = true,length = 10)
    private String codeUO;

    //Attributs navigationnels
    @ManyToOne
    @JoinColumn(name = "direction_id")
    private Direction direction;

    

    public Secteur(String name, String codeUO, Direction direction) {
        this.name = name;
        this.codeUO = codeUO;
        this.direction = direction;
    }



    @Override
    public String toString(){
        String direction;
        if (this.getDirection()==null) {
            direction = "AUCUNE";
        }else{
            direction = this.getDirection().getName();
        }
        return  "------------------------------------------------------------------------------\n"+
                "  SECTEUR [ ID = "+id+" | NOM = "+name+" | CODE UO = "+codeUO+" | DIRECTION  = "+direction+" ]\n"+
                "------------------------------------------------------------------------------";
    }
}
