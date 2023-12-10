/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capamodel;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Juan Antonio
 */
public class Ruta {
    private int id;
    private String titol;
    private User propietari;
    private long time;
    private String texthtml;
    private String descripcio;
    private float valoracions;
    private int desn_p;
    private int desn_n;
    private float distancia;
    private int dificultat;
    private Date data;

    public Ruta(int id, String titol, User propietari, long time, String texthtml, String descripcio, float valoracions, int desn_p, int desn_n, float distancia, int dificultat, Date data) {
        this.id = id;
        this.titol = titol;
        this.propietari = propietari;
        this.time = time;
        this.texthtml = texthtml;
        this.descripcio = descripcio;
        this.valoracions = valoracions;
        this.desn_p = desn_p;
        this.desn_n = desn_n;
        this.distancia = distancia;
        if(dificultat > 5 || dificultat < 0){
            throw new RuntimeException("La dificultat ha de ser valors entre 0-5 inclosos");
        }
        this.dificultat = dificultat;
        this.data = data;
    }

    public Ruta(String titol, User propietari, long time, String descripcio, float valoracions, int desn_p, int desn_n, float distancia, int dificultat, Date data) {
        this.titol = titol;
        this.propietari = propietari;
        this.time = time;
        this.descripcio = descripcio;
        this.valoracions = valoracions;
        this.desn_p = desn_p;
        this.desn_n = desn_n;
        this.distancia = distancia;
        if(dificultat > 5 || dificultat < 0){
            throw new RuntimeException("La dificultat ha de ser valors entre 0-5 inclosos");
        }
        this.dificultat = dificultat;
        this.data = data;
    }
    

    public Ruta() {
        
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }
    
    public void setPropietari(User propietari) {
        this.propietari = propietari;
    }

    public void setTime(long time) {
        this.time = time;
    }
    public void setTime(Date time){
        //long stamp = System.currentTimeMillis();
        //Date date = new Date(stamp);
        //date.getTime();
        //System.out.println(date);
    
        this.time = time.getTime();
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setTexthtml(String texthtml) {
        this.texthtml = texthtml;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public void setValoracions(float valoracions) {
        this.valoracions = valoracions;
    }

    public void setDesn_p(int desn_p) {
        this.desn_p = desn_p;
    }

    public void setDesn_n(int desn_n) {
        this.desn_n = desn_n;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public void setDificultat(int dificultat) {
        if(dificultat > 5 || dificultat < 0){
            throw new RuntimeException("La dificultat ha de ser valors entre 0-5 inclosos");
        }
        this.dificultat = dificultat;
    }
    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitol() {
        return titol;
    }

    public User getPropietari() {
        return propietari;
    }

    public long getTime() {
        return time;
    }

    public String getTexthtml() {
        return texthtml;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public float getValoracions() {
        return valoracions;
    }

    public int getDesn_p() {
        return desn_p;
    }

    public int getDesn_n() {
        return desn_n;
    }

    public float getDistancia() {
        return distancia;
    }

    public int getDificultat() {
        return dificultat;
    }
    
    
    
    @Override
    public String toString(){
        return "[ ID: "+id+", "+titol+", "+propietari+", "+distancia+", "+dificultat+", "+time+", "+data+" ]";
    }
    
    @Override
    public boolean equals(Object obj){
        return this == obj;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.titol);
        return hash;
    }
    
}
