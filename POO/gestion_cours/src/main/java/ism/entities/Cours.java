package ism.entities;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cours {
    private int id;
    private Date date;
    private Time heureD;
    private Time heureF;
    private String codeCours; //null si cours en présentiel

    //Attributs navigationnels 
    private Module module ;
    private Professeur professeur ;
    private Salle salle = null; //null si cours en ligne
    private ArrayList<Classe>classes = new ArrayList<>();

    public Cours(int id, Date date, Time heureD, Time heureF) {
        this.id = id;
        this.date = date;
        this.heureD = heureD;
        this.heureF = heureF;
    }


    public Cours(Date date, Time heureD, Time heureF, String codeCours, Module module, Professeur professeur,Salle salle, ArrayList<Classe> classes) {
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

        return  "-----------------------------------------------\n"+
                 "  COURS [ ID = "+id+ 
                 "\n          DATE = " + new SimpleDateFormat("dd-MM-yyyy").format(date) +
                 "\n          HORAIRE = " + new SimpleDateFormat("HH:mm").format(heureD)+" à "+new SimpleDateFormat("HH:mm").format(heureF)+
                 "\n          MODULE = " +module.getLibelle()+
                 "\n          PROFESSEUR = "+professeur.getNomComplet()+
                 "\n          CLASSE(S) = "+ch+
                 "\n          "+lib+" = "+val+ " ]\n"+
                 "----------------------------------------------";
        // return  "-------------------------------------------------------------------------------------------------------------------------------\n"+
        //         "  COURS [ ID = "+id+ " | DATE = " + new SimpleDateFormat("dd-MM-yyyy").format(date) +
        //                   " | HORAIRE = " + new SimpleDateFormat("HH:mm").format(heureD)+" à "+new SimpleDateFormat("HH:mm").format(heureF)+" | MODULE = " +module.getLibelle()+" | PROF = "+professeur.getNomComplet()+" | "+lib+" = "+val+ " ]\n"+
        //         "-------------------------------------------------------------------------------------------------------------------------------";

        // return  "-------------------------------------------------------------------------------------------------------------------------------\n"+
        //         "  COURS [ ID = "+id+ " | DATE = " + new SimpleDateFormat("dd-MM-yyyy").format(date) +
        //                   " | HORAIRE = " + new SimpleDateFormat("HH:mm").format(heureD)+" à "+new SimpleDateFormat("HH:mm").format(heureF)+" | "+"\n          MODULE = " +module.getLibelle()+" | PROF = "+professeur.getNomComplet()+" | "+lib+" = "+val+ " ]\n"+
        //         "-------------------------------------------------------------------------------------------------------------------------------";

       
    }
}
