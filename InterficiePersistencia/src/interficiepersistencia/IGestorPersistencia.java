/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interficiepersistencia;

import capamodel.Punt;
import capamodel.Ruta;
import capamodel.TipusPunt;
import capamodel.User;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Juan Antonio
 */
public interface IGestorPersistencia {
    
    
    //Control de la base de dades:
    /**
     * fer rollback a la base de dades;
     * @return Si s'ha pogut fer o no;
     */
    public boolean desferCanvis();
    /**
     * fer comit a la base de dades
     * @return Si s'ha pogut fer o no;
     */
    public boolean confirmarCanvis();
    /**
     * Tanca la connexio amb la base de dades;
     * @return si s'ha pogut tancar la connexio correctament;
     */
    public boolean tancarConnexio();
    
    /**
     * Update a la base de dades de punt
     * @param p punt que s'ha d'actualitzar;
     * @return si s'ha pogut realitzar el update;
     */
    public boolean actualitzarPunt(Punt p);
    
    /**
     * Update a la base de dades de ruta
     * @param r ruta que s'ha d'actualitzar
     * @return si s'ha pogut realitzar el update;
     */
    
    public boolean actualitzarRuta(Ruta r);
    
    /**
     * Insert en la base de dades de Punt
     * @param p Punt que s'ha d'iserir
     * @return Si s'ha pogut inserir
     */
    
    public boolean afegirPunt(Punt p);
    
    /**
      * Insert en la base de dades de Punt
     * @param r Punt que s'ha d'iserir
     * @return Si s'ha pogut inserir retorna el id de la ruta, si no retorna null.
     */
    
    public int afegirRuta(Ruta r);
    
    /**
     * Retorna si el usuari ha isnerit les credencials correctes per fer un log in
     * @param u Usuari amb totes les credencials
     * @return Si son correctes les credencials
     */
    
    public boolean confirmarCredencialsUsuari(User u);
    
    /**
     * Delete d'un punt a la base de dades
     * @param p Punt que s'ha de elminiar
     * @return Si s'ha pogut eliminar;
     */
    
    public boolean eliminarPunt(Punt p);
    
    //public boolean eliminarPunt(int num , Ruta r); posible metode que faria el mateix pero amb diferents parametres.
    
    /**
     * Delete d'una Ruta a la base de dades
     * @param r Ruta que s'ha de elminiar
     * @return Si s'ha pogut eliminar, -1 no s'ha pogut, 0 te comentaris, 1 s'ha eliminat;
     */
    
    public int eliminarRuta(Ruta r);
    /**
     * Delete d'una Ruta a la base de dades i tot el que l'apunta;
     * @param r Ruta que s'ha de elminiar
     * @return Si s'ha pogut eliminar
     */
    public boolean eliminarRutaCascade(Ruta r);
    
    /**
     * Obte tots els punts d'una ruta
     * @param r Ruta per obtenir els punts
     * @return List de punts de la ruta
     */
    
    public List<Punt> obtenirLlistaPuntsRuta(Ruta r);
    
    /**
     * Obtenir llista de rutes
     * @return List de rutes
     */
    
    public List<Ruta> obtenirLlistaRutes();
    
    /**
     * Obtenir llista dels tipus de punts
     * @return List de TipusPunt
     */
    
    public List<TipusPunt> obtenirLlistaTipusPunts();
    
    /**
     * Obtenir llista de usuaris
     * @return List de Users
     */
    
    public List<User> obtenirLlistaUsers();
    
    /**
     * Obtenir una ruta mitjançant el id
     * @param id Id per saber la ruta
     * @return retorna la Ruta
     */
    
    public Ruta obtenirRuta(int id);
    
    /**
     * Obtenir un llistat de rutes segons el titol
     * @param titol String per fer la busqueda
     * @return List de rutes amb conincidencia al titol
     */
    
    public List<Ruta> obtenirRutaTitol(String titol);
    
    
    /**
     * Obtenir un punt d'una ruta
     * @param r Ruta a la que pertany el punt
     * @param num Id del punt
     * @return Retorna el Punt
     */
    
    public Punt obtenirPunt(Ruta r, int num);
    
    /**
     * Obtenir un punt d'una ruta
     * @param num Id del punt
     * @return Retorna el Punt
     */
    
    public Punt obtenirPuntId(int num);
    
    /**
     * Obtenir el TipusPunt mitjançant l'id
     * @param id Id del tipus
     * @return Retorna TipusPunt
     */
    
    public TipusPunt obtenirTipusPunt(int id);
    
    /**
     * Obtenir un usuari a partir del seu login o mail
     * @param s cadena per comparar
     * @return retorna User;
     */
    
    public User obtenirUser(String s);
    
}
