package ism.entites;

import java.util.ArrayList;

public class Patient extends Personne {
    ArrayList<String> antecedents = new ArrayList<>();

    public Patient(int id, String nomComplet, String type, ArrayList<String> antecedents) {
        super(id, nomComplet, type);
        this.antecedents = antecedents;
    }

    public Patient() {
        super();
        this.type="Patient";
    }

    public Patient(String nomComplet, ArrayList<String> antecedents) {
        super(nomComplet);
        this.type="Patient";
        this.antecedents = antecedents;
    }

    public ArrayList<String> getAntecedents() {
        return antecedents;
    }

    public void setAntecedents(ArrayList<String> antecedents) {
        this.antecedents = antecedents;
    }

    @Override
    public String toString() {
        return  "--------------------------------------------------------------------------------------------------------\n"+
                "  PATIENT [ ID = "+id+" | NOM ET PRÉNOMS = "+nomComplet+" | ANTÉCÉDENTS = "+antecedents+" ]\n"+
                "--------------------------------------------------------------------------------------------------------";

    }
    

    

    

    
}
