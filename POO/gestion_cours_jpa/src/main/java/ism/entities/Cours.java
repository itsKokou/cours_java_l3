package ism.entities;


import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="cours")

@NamedQueries({
    @NamedQuery(name = "SELECT_ALL_COURS",query = "select c from Cours c"),
    @NamedQuery(name = "SELECT_COURS_BY_ID",query = "select c from Cours c where id=:id")

})
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private LocalTime heureD;
    @Column(nullable = false)
    private LocalTime heureF;
    @Column(nullable = true,length = 20)
    private String codeCours; //null si cours en présentiel

    //Attributs navigationnels 
    @ManyToOne
    @JoinColumn(nullable = false)
    private Module module ;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Professeur professeur ;
    @ManyToOne
    @JoinColumn(nullable = true)
    private Salle salle = null; //null si cours en ligne
    @ManyToMany
    private List<Classe>classes = new ArrayList<>();

    public Cours(Long id, Date date, LocalTime heureD, LocalTime heureF) {
        this.id = id;
        this.date = date;
        this.heureD = heureD;
        this.heureF = heureF;
    }


    public Cours(Date date, LocalTime heureD, LocalTime heureF, String codeCours, Module module, Professeur professeur,Salle salle, List<Classe> classes) {
        this.date = date;
        this.heureD = heureD;
        this.heureF = heureF;
        this.codeCours = codeCours;
        this.module = module;
        this.professeur = professeur;
        this.salle = salle;
        this.classes = classes;
    }

    
    @Override
    public String toString(){
        String lib="",val="",ch="";
        if(salle!=null){
            lib = "SALLE";
            val = salle.getLibelle();
        }else{
            lib = "CODE DU COURS";
            val = codeCours;
        }
        for (Classe classe : classes) {
            ch=ch+classe.getLibelle()+", ";
        }
        
        // return  "-------------------------------------------------------------------------------------------------------------------------------\n"+
        //         "  COURS [ ID = "+id+ " | DATE = " + new SimpleDateFormat("dd-MM-yyyy").format(date) +
        //                   " | HORAIRE = " + new SimpleDateFormat("HH:mm").format(heureD)+" à "+new SimpleDateFormat("HH:mm").format(heureF)+" | MODULE = " +module.getLibelle()+" | PROF = "+professeur.getNomComplet()+" | "+lib+" = "+val+ " ]\n"+
        //         "-------------------------------------------------------------------------------------------------------------------------------";

        // return  "-------------------------------------------------------------------------------------------------------------------------------\n"+
        //         "  COURS [ ID = "+id+ " | DATE = " + new SimpleDateFormat("dd-MM-yyyy").format(date) +
        //                   " | HORAIRE = " + new SimpleDateFormat("HH:mm").format(heureD)+" à "+new SimpleDateFormat("HH:mm").format(heureF)+" | "+"\n          MODULE = " +module.getLibelle()+" | PROF = "+professeur.getNomComplet()+" | "+lib+" = "+val+ " ]\n"+
        //         "-------------------------------------------------------------------------------------------------------------------------------";

        return  "-----------------------------------------------\n"+
                 "  COURS [ ID = "+id+ 
                 "\n          DATE = " + new SimpleDateFormat("dd-MM-yyyy").format(date) +
                 "\n          HORAIRE = " + heureD +" à "+ heureF +
                 "\n          MODULE = " +module.getLibelle()+
                 "\n          PROFESSEUR = "+professeur.getNomComplet()+
                 "\n          CLASSE(S) = "+ch+
                 "\n          "+lib+" = "+val+ " ]\n"+
                 "----------------------------------------------";
    }
}
