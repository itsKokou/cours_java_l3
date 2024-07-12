package ism.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "secteurs")
@NamedQuery(name = "ALL_SECTEUR", query = "from Secteur")
public class Secteur extends AbstractEntity{
    @Column(nullable = false, unique = true, length = 20)
    private String codeUO;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Direction direction;

    @Override
    public String toString(){
        String nomDirection = "Aucune";
        if (this.direction!=null) {
            nomDirection = this.direction.name;
        }
        return "SECTEUR [ ID : "+id+" | Nom : "+name +" | Direction : "+ nomDirection+"]";
    }
}
