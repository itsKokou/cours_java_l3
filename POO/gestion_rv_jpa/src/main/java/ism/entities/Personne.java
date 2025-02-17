package ism.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity 
@Table(name="personnes")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="type")

public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(nullable=false)
    protected String nomComplet;
    
    public Personne(String nomComplet) {
        this.nomComplet = nomComplet;
    }
    
}
