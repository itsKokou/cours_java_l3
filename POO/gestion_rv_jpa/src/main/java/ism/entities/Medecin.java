package ism.entities;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import ism.enums.Specialite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name="medecins")
@DiscriminatorValue(value="MEDECIN")

public class Medecin extends Personne{
    
    @Enumerated(EnumType.STRING)
    private Specialite specialite;
    @OneToMany(mappedBy = "medecin")
    private List<Rv> rvs ;

    public Medecin(String nomComplet, Specialite specialite) {
        super(nomComplet);
        this.specialite = specialite;
    } 

    @Override
    public String toString() {
        return  "--------------------------------------------------------------------------------------------------------\n"+
                "  MÉDECIN [ ID = "+id+" | NOM ET PRÉNOMS = "+nomComplet+" | SPÉCIALITÉ = "+specialite+" ]\n"+ rvs+
                "--------------------------------------------------------------------------------------------------------";
    }
}
