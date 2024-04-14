package sio.projetbuffteauv3.entities;

public class Soutien {

    private int idSoutient;
    private int idDemandeSoutient;
    private int idCompSoutient;
    private String date;
    private  int statut;

    public Soutien(int idSoutient, int idDemandeSoutient, int idCompSoutient, String date, int statut) {
        this.idSoutient = idSoutient;
        this.idDemandeSoutient = idDemandeSoutient;
        this.idCompSoutient = idCompSoutient;
        this.date = date;
        this.statut = statut;
    }
    public int getIdSoutient() {
        return idSoutient;
    }

    public void setIdSoutient(int idSoutient) {
        this.idSoutient = idSoutient;
    }

    public int getIdDemandeSoutient() {
        return idDemandeSoutient;
    }

    public void setIdDemandeSoutient(int idDemandeSoutient) {
        this.idDemandeSoutient = idDemandeSoutient;
    }

    public int getIdCompSoutient() {
        return idCompSoutient;
    }

    public void setIdCompSoutient(int idCompSoutient) {
        this.idCompSoutient = idCompSoutient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }
}

