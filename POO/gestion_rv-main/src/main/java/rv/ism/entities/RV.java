package rv.ism.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Entity
@Table(name = "rvs")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RV{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private Date dateRv;
   @ManyToOne
   private Patient patient;
    @ManyToOne
   private Medecin medecin;
    
}
