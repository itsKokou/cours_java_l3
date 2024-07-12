package ism.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "directions")

@NamedQuery(name = "ALL_DIRECTION", query = "from Direction")
public class Direction extends AbstractEntity {
    
    @OneToMany(mappedBy = "direction", cascade = {CascadeType.PERSIST})
    private List<Secteur> secteurs;

    @Override
    public String toString(){
        return "DIRECTION [ ID : "+id+" | Nom : "+name +"]";
    }
}
