package edu.upc.eetac.dsa.minim2android;

/**
 * Created by rosa on 17/12/2016.
 */
public class Etakemon {

    String nom, descripcio,tipus;
    int id, puntuacio;

    public Etakemon() {}

    public Etakemon(String tipus, String nom, String descripcio) {
        this.tipus = tipus;
        this.nom = nom;
        this.descripcio = descripcio;
        this.puntuacio = getPuntuacioPerTipus(tipus);
    }

    public int getId() {
        return this.id;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public int getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }

    public static int getPuntuacioPerTipus(String tipus) {
        int puntuacio = 0;
        if(tipus.equals("director")) {
            puntuacio = 200;
        } else if (tipus.equals("professor")) {
            puntuacio = 100;
        } else if (tipus.equals("alumne")) {
            puntuacio = 50;
        }
        return puntuacio;
    }
}
