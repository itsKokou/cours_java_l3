package rv.ism.entities;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rv.ism.enums.Specialite;

@Entity
@Table(name = "medecins")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue(value = "MED")
public class Medecin extends Personne{
    @Enumerated(EnumType.STRING)
    private Specialite specialite;

       @OneToMany(mappedBy = "medecin")
       private List <RV>rvs;

      public Medecin(String nomComplet, Specialite specialite) {
        super(nomComplet);
        this.specialite = specialite;
      }

    @Override
    public String toString() {
        return super.toString()+ " Medecin [specialite=" + specialite +  "]";
    }

   
    
}
