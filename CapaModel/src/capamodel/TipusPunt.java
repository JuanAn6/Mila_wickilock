/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package capamodel;

/**
 *
 * @author Juan Antonio
 */
public class TipusPunt{
    private int id;
    private String nom;

    public TipusPunt(int id , String nom) {
        this.id = id;
        this.nom = nom;
    }
    
    public int getId(){
        return this.id;
    }
    public String getNom(){
        return this.nom;
    }
    
    @Override
    public String toString(){
        return "[ Tipus: "+this.id+", "+this.nom+" ]";
    }
    
    
    @Override
    public boolean equals(Object obj){
        return this == obj;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        return hash;
    }
}
