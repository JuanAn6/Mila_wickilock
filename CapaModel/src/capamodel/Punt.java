/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capamodel;

import java.util.Objects;

/**
 *
 * @author Juan Antonio
 */
public class Punt {
    private int id;
    private int num;
    private Ruta ruta;
    private String nom;
    private String descripcio;
    private int alt;
    private float lat;
    private float lon;
    private TipusPunt tipus;

    public Punt(int id, int num, Ruta ruta, String nom, String descripcio, int alt, float lat, float lon, TipusPunt tipus) {
        this.id = id;
        this.num = num;
        this.ruta = ruta;
        this.nom = nom;
        this.descripcio = descripcio;
        this.alt = alt;
        this.lat = lat;
        this.lon = lon;
        this.tipus = tipus;
    }

    public Punt() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public String getNom() {
        return nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public int getAlt() {
        return alt;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public TipusPunt getTipus() {
        return tipus;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public void setTipus(TipusPunt tipus) {
        this.tipus = tipus;
    }
    
    @Override
    public String toString(){
        return "[ Num: "+num+" Ruta: "+ruta.getId()+" Nom: "+nom+" Descripcio: "+descripcio+" Lat: "+lat+" Lon: "+lon+" Alt: "+alt+" Tipus: "+tipus.getNom()+"]";
    }
    @Override
    public boolean equals(Object obj){
        return this == obj;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.num;
        hash = 89 * hash + Objects.hashCode(this.ruta);
        return hash;
    }
}
