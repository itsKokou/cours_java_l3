package rv.ism.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "voitures")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Voiture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column(name = "matricule",length = 10,nullable = false,unique = true)
    private String imat;
    @Transient
    private String chassie;
    public Voiture(String imat){ 
         this.imat=imat;
    }

    public Voiture(Long id,String imat){ 
         this.id=id;
         this.imat=imat;
    }
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
     List<Personne>  personnes=new ArrayList<>();
}
