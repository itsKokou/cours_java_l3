package ism.entites;

public class Medecin extends Personne{
    private String specialite;

    public Medecin(int id, String nomComplet, String type, String specialite) {
        super(id, nomComplet, type);
        this.specialite = specialite;
    }

    public Medecin(String nomComplet, String specialite) {
        super(nomComplet);
        this.type= "Medecin";
        this.specialite = specialite;
    }

    public Medecin() {
        super();
        this.type="Medecin";
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return  "--------------------------------------------------------------------------------------------------------\n"+
                "  MÉDECIN [ ID = "+id+" | NOM ET PRÉNOMS = "+nomComplet+" | SPÉCIALITÉ = "+specialite+" ]\n"+
                "--------------------------------------------------------------------------------------------------------";
    }


    
}
