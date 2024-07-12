package rv.ism.entities;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@DiscriminatorValue(value = "bbw")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bbw extends Voiture {
    private String  model;
   public Bbw(String imat, String model){ 
      super(imat);
      this.model=model;
    }
     public Bbw(Long id,String imat, String model){ 
      super(id,imat);
      this.model=model;
    }

}
