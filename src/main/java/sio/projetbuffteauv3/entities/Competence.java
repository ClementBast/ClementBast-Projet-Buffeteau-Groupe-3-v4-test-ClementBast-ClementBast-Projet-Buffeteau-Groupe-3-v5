package sio.projetbuffteauv3.entities;

public class Competence {
    int idCompetence;
    private String matiereComp;
    private String sousMatiereComp;
    private int idEleve;

    public Competence(int idCompetence, String matiereComp, String sousMatiereComp, int idEleve){
        this.idCompetence = idCompetence;
        this.matiereComp = matiereComp;
        this.sousMatiereComp = sousMatiereComp;
        this.idEleve = idEleve;

    }

    public String getMatiereComp() {
        return matiereComp;
    }

    public int getIdCompetence() {
        return idCompetence;
    }

    public void setIdCompetence(int idCompetence) {
        this.idCompetence = idCompetence;
    }

    public void setMatiereComp(String matiereComp) {
        this.matiereComp = matiereComp;
    }

    public void setMatiere(String matiereComp) {
        this.matiereComp = matiereComp;
    }

    public String getSousMatiereComp() {
        return sousMatiereComp;
    }

    public void setSousMatiereComp(String sousMatiereComp) {
        this.sousMatiereComp = sousMatiereComp;
    }

    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }
}