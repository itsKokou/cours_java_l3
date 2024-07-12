package rv.ism.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "nissan")
public class Nissan extends Voiture {
      private String marque;
}
