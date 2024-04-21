package sio.projetbuffteauv3.entities;

public class Matiere {
    private String matiere;
    private String sousMatiere;
    public Matiere(String matiere, String sousMatiere){
        this.matiere = matiere;
        this.sousMatiere = sousMatiere;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getSousMatiere() {
        return sousMatiere;
    }

    public void setSousMatiere(String sousMatiere) {
        this.sousMatiere = sousMatiere;
    }

    @Override
    public String toString() {
        return sousMatiere;
    }

}