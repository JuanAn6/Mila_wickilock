/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import capamodel.Punt;
import capamodel.Ruta;
import capamodel.TipusPunt;
import capamodel.User;
import capapersistenciajdbc.GestorBD;
import interficiepersistencia.IGestorException;
import java.security.Timestamp;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Juan Antonio
 */
public class main {
    
    /**
     *
     * @param args
     * @throws IGestorException
     * @throws SQLException
     */
    public static void main(String[] args) throws IGestorException, SQLException{
        GestorBD bd = new GestorBD();
        System.out.println("Auto Commit: "+bd.con.getAutoCommit());
        /*
        LLISTAT DE LES DADES DE LA BASE;
        List<User> users = bd.obtenirLlistaUsers();
        
        for(User s : users){
            System.out.println(s.toString()+"\n");
        }
        
        List<TipusPunt> tp = bd.obtenirLlistaTipusPunts();
        
        for(TipusPunt t : tp){
            System.out.println(t.toString()+"\n");
        }
        
        List<Ruta> rt = bd.obtenirLlistaRutes();
        for(Ruta t : rt){
            System.out.println(t.toString()+"\n");
            List<Punt> pu = bd.obtenirLlistaPuntsRuta(t);
            for(Punt p : pu){
                System.out.println(p.toString());
            }
        }
        User s = bd.obtenirUser("juan");
        System.out.println(s.toString());
        */
        
        /*
        //AUTENTICACIO D'USUARI;
        User u = new User("juan","juan@gmail.com", "juan1234");
        
        if(bd.confirmarCredencialsUsuari(u)){
            System.out.println(u.toString()+" Usuari valid");
        }else{
            System.out.println(u.toString()+" Usuari no valid");
        }
        */
        
        
        /*
        //AFEGIR RUTA;
        Ruta r = rt.get(0);
        
        r.setTitol("ruta de prova");
        r.setDescripcio("Ruta de prova per al codi bdManager");
        
        bd.afegirRuta(r);
        bd.confirmarCanvis();
        
        rt = bd.obtenirLlistaRutes();
        for(Ruta t : rt){
            System.out.println(t.toString()+"\n");
        }
        */
        
        /*
        //ACTUALITZAR RUTA;
        Ruta r = null;
        
        List<Ruta> rutes = bd.obtenirRutaTitol("ruta de prova");
        
        System.out.println(rutes.size());
        for(Ruta s: rutes){
            System.out.println(s.toString());
        }
        
        r = rutes.get(0);

        r.setTitol("ruta de prova udpated");
        r.setDescripcio("ruta de prova descricpio updated");
        
        bd.actualitzarRuta(r);
        
        rt = bd.obtenirLlistaRutes();
        
        if ( bd.confirmarCanvis()){
            System.out.println("canvis desats");
        }else{
            System.out.println("error canvis desats");
        }
        
        for(Ruta t : rt){
            System.out.println(t.toString()+"\n");
        }
        
        */
        
        
        
        /*
        //ELIMINAR RUTA;
        Ruta r = null;
        
        List<Ruta> rutes = bd.obtenirLlistaRutes();
        
        r = rutes.get(0);
        
        int x = bd.eliminarRuta(r);
        
        if (x == 0){ //te comentaris;
            System.out.println("La ruta te comentaris"); 
            bd.eliminarRutaCascade(r);
            rutes = bd.obtenirLlistaRutes();
            
            System.out.println(rutes.size());
            for(Ruta s: rutes){
                System.out.println(s.toString());
            }
        }else{
            rutes = bd.obtenirLlistaRutes();
            System.out.println(rutes.size());
            for(Ruta s: rutes){
                System.out.println(s.toString());
            }
        }
        */
        
        
        //bd.confirmarCanvis();

        
        
        /*
        //OBTENIR RUTA I PUNT DE FORMA INDIVIDUAL
        
        Ruta r = bd.obtenirRuta(1);
        Punt s = bd.obtenirPunt(r, 1);
        
        System.out.println(r.toString());
        System.out.println(s.toString());
        
        */
        
        /*
        //ELIMINAR PUNT
        Ruta r = bd.obtenirRuta(1);
        List<Punt> punts = bd.obtenirLlistaPuntsRuta(r);
        
        for(Punt p : punts){
            System.out.println(p.toString());
        }
        
        
        Punt s = bd.obtenirPunt(r, 1);
        
        bd.eliminarPunt(s);
        
        punts = bd.obtenirLlistaPuntsRuta(r);
        
        for(Punt p : punts){
            System.out.println(p.toString());
        }
        */
        /*
        //AFEGIR PUNT
        Ruta r = bd.obtenirRuta(1);
        
        
        Punt p = bd.obtenirPunt(r, 1);
        p.setNum(3);
        p.setNom("punto de prueva 78");
        
        bd.afegirPunt(p);
        
        List<Punt> punts = bd.obtenirLlistaPuntsRuta(r);
        
        for(Punt s : punts){
            System.out.println(s.toString());
        }
        
        */
        /*
        //ACTUALITZAR PUNT
        Ruta r = bd.obtenirRuta(1);
        Punt p = bd.obtenirPunt(r, 5);
        p.setNom("actualizar punto ");
        p.setNum(1);
        bd.actualitzarPunt(p);
        
        List<Punt> punts = bd.obtenirLlistaPuntsRuta(r);
        
        for(Punt s : punts){
            System.out.println(s.toString());
        }
        */
        
        //bd.confirmarCanvis();
        //bd.desferCanvis();
        
        /*
        long stamp = System.currentTimeMillis();
        Date date = new Date(stamp);
        date.getTime();
        System.out.println(date);
        */
    }
}
