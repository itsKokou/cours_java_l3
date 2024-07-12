package ism.entities;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name="patients")
@DiscriminatorValue(value="PATIENT")

public class Patient extends Personne{

    @Column(nullable =false)
    private String antecedents;
    @OneToMany(mappedBy = "patient")
    private List<Rv>rvs;

    public Patient(String nomComplet, String antecedents) {
        super(nomComplet);
        this.antecedents = antecedents;
    }

    public String toString() {
        return  "--------------------------------------------------------------------------------------------------------\n"+
                "  PATIENT [ ID = "+id+" | NOM ET PRÉNOMS = "+nomComplet+" | ANTÉCÉDENTS = "+Arrays.asList(antecedents.split(","))+" ]\n"+
                "--------------------------------------------------------------------------------------------------------";
    }
}
