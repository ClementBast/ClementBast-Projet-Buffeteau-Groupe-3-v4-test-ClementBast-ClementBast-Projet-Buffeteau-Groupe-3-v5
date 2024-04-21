package sio.projetbuffteauv3.entities;

import java.util.Date;

public class Demande {
    private String matiereDem;
    private String sousMatiereDem;
    private  int id;
    private String date;

    public Demande(String matiereDem, String sousMatiereDem, int id, String date) {
        this.matiereDem = matiereDem;
        this.sousMatiereDem = sousMatiereDem;
        this.id = id;
        this.date = date;
    }

    public String getMatiereDem() {
        return matiereDem;
    }

    public void setMatiereDem(String matiereDem) {
        this.matiereDem = matiereDem;
    }

    public String getSousMatiereDem() {
        return sousMatiereDem;
    }

    public void setSousMatiereDem(String sousMatiereDem) {
        this.sousMatiereDem = sousMatiereDem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return matiereDem;
    }
}