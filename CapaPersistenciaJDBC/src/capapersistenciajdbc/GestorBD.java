/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capapersistenciajdbc;

import capamodel.Punt;
import capamodel.Ruta;
import capamodel.TipusPunt;
import capamodel.User;
import interficiepersistencia.IGestorException;
import interficiepersistencia.IGestorPersistencia;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Antonio
 */
public class GestorBD implements IGestorPersistencia {

    public Connection con;

    public GestorBD() throws IGestorException {
        String nomFitxer = "dades.xml";
        try {
            
            Properties props = new Properties();
            props.loadFromXML(new FileInputStream(nomFitxer));
            String[] claus = {"url", "user", "password"};
            String[] valors = new String[3];
            for (int i = 0; i < claus.length; i++) {
                valors[i] = props.getProperty(claus[i]);
                if (valors[i] == null || valors[i].isEmpty()) {
                    throw new IGestorException("L'arxiu " + nomFitxer + " no troba la clau " + claus[i]);
                }
                //System.out.println(valors[i].toString());
            }
            
            //con = DriverManager.getConnection(valors[0], valors[1], valors[2]); //linia que execta la connexió;
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/XEPDB1", "alumne", "alumne");
            con.setAutoCommit(false); //linia imortant perque per defecte JDBCoracle fa autocommit;
        } catch (IOException ex) {
            throw new IGestorException("Problemes en recuperar l'arxiu de configuració " + nomFitxer, ex);
        } catch (SQLException ex) {
            throw new IGestorException("No es pot establir la connexió.", ex);
        }
    }

    @Override   
    public boolean desferCanvis() {
        try {
            con.rollback();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean confirmarCanvis() {
        try {
            con.commit();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean tancarConnexio() {
        try {
            con.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean confirmarCredencialsUsuari(User u) {
        Statement q = null;
        
        try {
            String sql = "select pwd from usuaris where login = '"+u.getLogin()+"' or  email = '"+u.getEmail()+"'";
            q = con.createStatement();
            ResultSet rs = q.executeQuery(sql);
            if(rs.next()){
                if(rs.getString("pwd").equals(u.getPwd())){
                    return true;
                }
            }
            
            return false;
            
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en validacio de usuari.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                return false;
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en validacio de usuari.", ex);
                        
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
    }
    private void update(String sql){
        Statement q = null;
            try {
                q = con.createStatement();
                q.executeUpdate(sql);

            } catch (SQLException ex) {
                try {
                    throw new IGestorException("Error en update de punt per controlar nums.", ex);
                } catch (IGestorException ex1) {
                    Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                if (q != null) {
                    try {
                        q.close();
                    } catch (SQLException ex) {
                        try {
                            throw new IGestorException("Error en update de punt per controlar nums.", ex);

                        } catch (IGestorException ex1) {
                            Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }
            }
    }
    private void controlarNums(Punt p, Punt old){
        List<Punt> punts = this.obtenirLlistaPuntsRuta(p.getRuta());   
        if(p.getNum() < old.getNum()){
            for(int i = old.getNum(); i > p.getNum(); i--){
                String sql = "";
                if(p.getId() == punts.get(i-1).getId()){
                    sql = "update punts set num = "+i+" where id = "+punts.get(i-2).getId();
                }else{
                    sql = "update punts set num = "+i+" where id = "+punts.get(i-1).getId();
                }
                this.update(sql);
            }
        }
        if(p.getNum() > old.getNum()){
            for(int i = old.getNum(); i < p.getNum(); i++){
                String sql = "";
                if(p.getId() == punts.get(i-1).getId()){
                    sql = "update punts set num = "+i+" where id = "+punts.get(i-2).getId();
                }else{
                    sql = "update punts set num = "+i+" where id = "+punts.get(i-1).getId();
                }
                this.update(sql);
            }
        }
        
    }
    
    @Override
    public boolean actualitzarPunt(Punt p) {
        String sql = "update punts set nom ='" + p.getNom() + "', descripcio = '" + p.getDescripcio() + "', lat = " + p.getLat()+ ", lon = " + p.getLon() + ", alt = "+p.getAlt()+", tipus = "+p.getTipus().getId()+", num = "+p.getNum()+"  where id = "+p.getId()+" and ruta = " + p.getRuta().getId();
        
        Statement q = null;
        try {
            Punt s = this.obtenirPuntId(p.getId());
            
            System.out.println("Puntold"+p.getId());
            
            q = con.createStatement();
            int i = q.executeUpdate(sql);
            
            this.controlarNums(p,s); //control perque no hi hagi num repetits per el ordre dels punts;
            
            return i == 1;
            
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en update de punt.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                return false;
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en update de punt.", ex);
                        
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
    }

    @Override
    public boolean actualitzarRuta(Ruta r) {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        
        String sql = "update rutes set titol = '" + r.getTitol() + "', propietari = '" + r.getPropietari().getLogin() + "', descripcio = '" + r.getDescripcio() + "', texthtml = '" + r.getTexthtml() + "', distancia = " + r.getDistancia() + ", temps = " + r.getTime() + ", desn_p = "+r.getDesn_p()+", desn_n = "+r.getDesn_n()+", dificultat = "+r.getDificultat()+", data = TO_DATE('"+formateador.format(r.getData())+"','DD/MM/YYYY') where id = "+r.getId();
        
        Statement q = null;
        try {
            q = con.createStatement();
            int i = q.executeUpdate(sql);
            
            return i == 1;
            
            
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en insercio de ruta.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                return false;
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en insercio de ruta.", ex);
                        
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
    }

    public void agumentarNumPunts(Ruta r, Punt p){
        List<Punt> punts = this.obtenirLlistaPuntsRuta(r);
        int i = p.getNum()+1;
        
        for(Punt item : punts){
            if(item.getNum() >= p.getNum()){
                item.setNum(i);
                this.actualitzarPunt(item);
                i++;
            }
        }
    }
    
    @Override
    public boolean afegirPunt(Punt p) {
        
        List<Punt> s = this.obtenirLlistaPuntsRuta(p.getRuta());
        //p.setNum(s.size()+1); //control dels numeros dels punts;
        
        String sql = "insert into punts (num, ruta, nom, descripcio, lat, lon, alt, tipus) values (" + p.getNum() + "," + p.getRuta().getId() + ",'" + p.getNom() + "', '" + p.getDescripcio() + "', " + p.getLat()+ ", " + p.getLon() + ", "+p.getAlt()+", "+p.getTipus().getId()+")";
        
        Statement q = null;
        try {
            
            this.agumentarNumPunts(p.getRuta(), p); //control perque no hi hagi num repetits per el ordre dels punts;
            
            q = con.createStatement();
            int i = q.executeUpdate(sql);
            
            return i == 1;
            
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en insercio de punt.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                return false;
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en insercio de punt.", ex);
                        
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
    }

    @Override
    public int afegirRuta(Ruta r) {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        
        String sql = "insert into rutes (titol, propietari, descripcio, texthtml, distancia, temps, desn_p, desn_n, dificultat, data) values ('" + r.getTitol() + "','" + r.getPropietari().getLogin() + "','" + r.getDescripcio() + "', '" + r.getTexthtml() + "', " + r.getDistancia() + ", " + r.getTime() + ","+r.getDesn_p()+","+r.getDesn_n()+","+r.getDificultat()+",TO_DATE('"+formateador.format(r.getData())+"','DD/MM/YYYY'))";
        
        PreparedStatement q = null;
        try {
            int lastId = -1;
            
            q = con.prepareStatement(sql);
            int i = q.executeUpdate();
            
            
            List<Ruta> rutes = this.obtenirLlistaRutes();
            for(Ruta s : rutes){
                if(s.getTitol().equals(r.getTitol()) && r.getPropietari().getLogin().equals(s.getPropietari().getLogin()) && s.getTime() == r.getTime()){
                    lastId = s.getId();
                }
            }
            return lastId;
        
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en insercio de ruta.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                return -1;
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en insercio de ruta.", ex);
                        
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
        
    }
    
    private int obtenirUltimId() {
        try (Statement statement = con.createStatement()) {
            String sql = "SELECT LAST_INSERT_ID() as last_id from dual";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("last_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Manejar el caso en el que no se puede obtener el último ID
    }

    public void corretgirOrdrePunts(Ruta r){
        List<Punt> punts = this.obtenirLlistaPuntsRuta(r);
        int i = 1;
        for(Punt p : punts){
            p.setNum(i);
            this.actualitzarPunt(p);
            i++;
        }
    }
    

    @Override
    public boolean eliminarPunt(Punt p) {
        String sql = "delete from punts where ruta = "+p.getRuta().getId()+" and id = "+p.getId();
        
        Statement q = null;
        try {
            q = con.createStatement();
            int i = q.executeUpdate(sql);
            this.corretgirOrdrePunts(p.getRuta());
            return i == 1;
            
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en eliminacio de punt.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                return false;
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en eliminacio de punt.", ex);
                        
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
    }
    
    @Override
    public int eliminarRuta(Ruta r) {
        String sql = "delete from rutes where id = "+r.getId();
        String sql1 = "delete from punts where ruta = "+r.getId();
        String sql2 = "select count(*) as num from comentaris where ruta = "+r.getId();
        Statement q = null;
        Statement q1 = null;
        Statement q2 = null;

        try {
            q2 = con.createStatement();
            
            ResultSet rs = q2.executeQuery(sql2);
            rs.next();
            if( rs.getInt("num") > 0){
                return 0;
            }else{
                q1 = con.createStatement();
                q = con.createStatement();
                q1.executeUpdate(sql1);
            
                if(1 == q.executeUpdate(sql)){
                    return 1;
                }
            }
            
            return -1;
            
            
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en eliminarcio de ruta.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                return -1;
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en eliminacio de ruta.", ex);
                        
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
    }
    
    
    @Override
    public boolean eliminarRutaCascade(Ruta r){
        String sql = "delete from rutes where id = "+r.getId();
        String sql1 = "delete from punts where ruta = "+r.getId();
        String sql2 = "delete from comentaris where ruta = "+r.getId();
        String sql3 = "select id from comentaris where ruta = "+r.getId();
        
        Statement q = null;
        Statement q1 = null;
        Statement q2 = null;
        Statement q3 = null;        
        Statement q4 = null;

        try {
            
            q4 = con.createStatement();
            q3 = con.createStatement();
            q2 = con.createStatement();
            q1 = con.createStatement();
            q = con.createStatement();
            
            ResultSet rs = q3.executeQuery(sql3);
            
            while(rs.next()){
                String sql4 = "delete from companys where comentari = "+rs.getInt("id");
                q4.executeUpdate(sql4);
            }
            
            
            q2.executeUpdate(sql2);
            q1.executeUpdate(sql1);
            
            
            int i = q.executeUpdate(sql);
            
            return i == 1;
            
            
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en eliminarcio de ruta.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                return false;
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en eliminacio de ruta.", ex);
                        
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
    }
    

    @Override
    public List<Punt> obtenirLlistaPuntsRuta(Ruta r) {
        List<Punt> punts = new ArrayList<>();

        Statement q = null;
        try {
            q = con.createStatement();
            String sql = "select id, num, nom, descripcio, lat, lon, alt, tipus from punts where ruta = " + r.getId()+" order by num";
            ResultSet rs = q.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                int num = rs.getInt("num");
                String nom = rs.getString("nom");
                String descripcio = rs.getString("descripcio");
                float lat = rs.getFloat("lat");
                float lon = rs.getFloat("lon");
                int alt = rs.getInt("alt");
                TipusPunt tipus = obtenirTipusPunt(rs.getInt("tipus"));

                Punt p = new Punt(id, num, r, nom, descripcio, alt, lat, lon, tipus);
                punts.add(p);

            }
            rs.close();
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en intentar recuperar la llista de rutes.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en intentar tancar la sentència que ha recuperat la llista de rutes.", ex);
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }

        return punts;
    }
    
    
    @Override
    public List<Ruta> obtenirLlistaRutes() {

        List<Ruta> rutes = new ArrayList<>();

        Statement q = null;
        try {
            q = con.createStatement();
            String sql = "select id, propietari, titol, descripcio, texthtml, distancia, temps, desn_p, desn_n, dificultat, s_info, n_per_coment, data from rutes";
            ResultSet rs = q.executeQuery(sql);
            while (rs.next()) {

                int id = rs.getInt("id");
                String titol = rs.getString("titol");
                User propietari = obtenirUser(rs.getString("propietari"));
                String descripcio = rs.getString("descripcio");
                String texthtml = rs.getString("texthtml");
                float distancia = rs.getFloat("distancia");
                long temps = rs.getLong("temps");
                int desn_p = rs.getInt("desn_p");
                int desn_n = rs.getInt("desn_n");
                int dificultat = rs.getInt("dificultat");

                int valoracions = rs.getInt("s_info");
                int nPer = rs.getInt("n_per_coment");
                float val = (float) nPer / (float) valoracions;
                
                Date data = rs.getDate("data");

                Ruta r = new Ruta(id, titol, propietari, temps, texthtml, descripcio, val, desn_p, desn_n, distancia, dificultat, data);
                rutes.add(r);

            }
            rs.close();
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en intentar recuperar la llista de rutes.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en intentar tancar la sentència que ha recuperat la llista de rutes.", ex);
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }

        return rutes;

    }

    @Override
    public List<TipusPunt> obtenirLlistaTipusPunts() {
        List<TipusPunt> tipusPunts = new ArrayList<>();

        Statement q = null;
        try {
            q = con.createStatement();
            ResultSet rs = q.executeQuery("select id, nom from tipus");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");

                TipusPunt tp = new TipusPunt(id, nom);
                tipusPunts.add(tp);
            }
            rs.close();
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en intentar recuperar la llista de TipusPunts.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en intentar tancar la sentència que ha recuperat la llista de TipusPutns.", ex);
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
        return tipusPunts;
    }

    @Override
    public List<User> obtenirLlistaUsers() {
        List<User> users = new ArrayList<>();

        Statement q = null;
        try {
            q = con.createStatement();
            ResultSet rs = q.executeQuery("select login, email, pwd  from usuaris");
            while (rs.next()) {
                String login = rs.getString("login");
                String email = rs.getString("email");
                String pwd = rs.getString("pwd");
                User u = new User(login, email, pwd);
                users.add(u);
            }
            rs.close();
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en intentar recuperar la llista de usuaris.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en intentar tancar la sentència que ha recuperat la llista de usuaris.", ex);
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
        return users;
    }

    @Override
    public User obtenirUser(String s) {
        User u = null;
        Statement q = null;
        try {
            q = con.createStatement();
            ResultSet rs = q.executeQuery("select email, pwd, login from usuaris where login='" + s + "' or email = '"+s+"'");
            rs.next();

            String email = rs.getString("email");
            String pwd = rs.getString("pwd");
            String login = rs.getString("login");
            u = new User(login, email, pwd);

            rs.close();
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en intentar recuperar el usuari.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en intentar tancar la sentència que ha recuperat el usuari.", ex);
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }

        return u;
    }

    @Override
    public List<Ruta> obtenirRutaTitol(String s){
        
        List<Ruta> rutes = new ArrayList<>();

        Statement q = null;
        try {
            q = con.createStatement();
            String sql = "select id, propietari, titol, descripcio, texthtml, distancia, temps, desn_p, desn_n, dificultat, s_info, n_per_coment, data from rutes where titol LIKE '%"+s+"%'";
            ResultSet rs = q.executeQuery(sql);
            while (rs.next()) {

                int id = rs.getInt("id");
                String titol = rs.getString("titol");
                User propietari = obtenirUser(rs.getString("propietari"));
                String descripcio = rs.getString("descripcio");
                String texthtml = rs.getString("texthtml");
                float distancia = rs.getFloat("distancia");
                long temps = rs.getLong("temps");
                int desn_p = rs.getInt("desn_p");
                int desn_n = rs.getInt("desn_n");
                int dificultat = rs.getInt("dificultat");

                int valoracions = rs.getInt("s_info");
                int nPer = rs.getInt("n_per_coment");
                float val = (float) nPer / (float) valoracions;
                
                Date data = rs.getDate("data");

                Ruta r = new Ruta(id, titol, propietari, temps, texthtml, descripcio, val, desn_p, desn_n, distancia, dificultat, data);
                rutes.add(r);

            }
            rs.close();
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en intentar recuperar la llista de rutes.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en intentar tancar la sentència que ha recuperat la llista de rutes.", ex);
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }

        return rutes;

        
    }
    
    @Override
    public Ruta obtenirRuta(int id) {
        Ruta r = null;
        Statement q = null;
        try {
            q = con.createStatement();
            String sql = "select propietari, titol, descripcio, texthtml, distancia, temps, desn_p, desn_n, dificultat, s_info, n_per_coment, data from rutes where id = " + id;
            ResultSet rs = q.executeQuery(sql);
            rs.next();

            String titol = rs.getString("titol");
            User propietari = obtenirUser(rs.getString("propietari"));
            String descripcio = rs.getString("descripcio");
            String texthtml = rs.getString("texthtml");
            float distancia = rs.getFloat("distancia");
            long temps = rs.getLong("temps");
            int desn_p = rs.getInt("desn_p");
            int desn_n = rs.getInt("desn_n");
            int dificultat = rs.getInt("dificultat");

            int valoracions = rs.getInt("s_info");
            int nPer = rs.getInt("n_per_coment");
            float val = (float) nPer / (float) valoracions;

            Date data = rs.getDate("data");
            
            r = new Ruta(id, titol, propietari, temps, texthtml, descripcio, val, desn_p, desn_n, distancia, dificultat, data);

            rs.close();
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en intentar recuperar la llista de rutes.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en intentar tancar la sentència que ha recuperat la llista de rutes.", ex);
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }

        return r;
    }

    @Override
    public Punt obtenirPunt(Ruta r, int num) {
        
        Punt p = null;
        Statement q = null;
        try {
            q = con.createStatement();
            String sql = "select id, nom, descripcio, lat, lon, alt, tipus from punts where ruta = " + r.getId()+" and num = "+num;
            ResultSet rs = q.executeQuery(sql);

            rs.next();
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String descripcio = rs.getString("descripcio");
                float lat = rs.getFloat("lat");
                float lon = rs.getFloat("lon");
                int alt = rs.getInt("alt");
                TipusPunt tipus = obtenirTipusPunt(rs.getInt("tipus"));

                p = new Punt(id, num, r, nom, descripcio, alt, lat, lon, tipus);
                

            
            rs.close();
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en intentar recuperar un punt.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en intentar tancar la sentència que ha recuperat un punt.", ex);
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }

        return p;
    }

    @Override
    public Punt obtenirPuntId(int id){
        Punt p = null;
        Statement q = null;
        try {
            q = con.createStatement();
            String sql = "select num, nom, descripcio, lat, lon, alt, tipus, ruta from punts where id = "+id;
            ResultSet rs = q.executeQuery(sql);

            rs.next();
                Ruta r = this.obtenirRuta(rs.getInt("ruta"));
                int num = rs.getInt("num");
                String nom = rs.getString("nom");
                String descripcio = rs.getString("descripcio");
                float lat = rs.getFloat("lat");
                float lon = rs.getFloat("lon");
                int alt = rs.getInt("alt");
                TipusPunt tipus = obtenirTipusPunt(rs.getInt("tipus"));

                p = new Punt(id, num, r, nom, descripcio, alt, lat, lon, tipus);
                
                rs.close();
                
                return p;
                
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en intentar recuperar un punt.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en intentar tancar la sentència que ha recuperat un punt.", ex);
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
        return null;

    }
    
    @Override
    public TipusPunt obtenirTipusPunt(int id) {
        TipusPunt tp = null;

        Statement q = null;
        try {
            q = con.createStatement();
            ResultSet rs = q.executeQuery("select nom from tipus where id = " + id);
            rs.next();
            String nom = rs.getString("nom");
            tp = new TipusPunt(id, nom);
            rs.close();
        } catch (SQLException ex) {
            try {
                throw new IGestorException("Error en intentar recuperar la llista de TipusPunts.", ex);
            } catch (IGestorException ex1) {
                Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (q != null) {
                try {
                    q.close();
                } catch (SQLException ex) {
                    try {
                        throw new IGestorException("Error en intentar tancar la sentència que ha recuperat la llista de TipusPutns.", ex);
                    } catch (IGestorException ex1) {
                        Logger.getLogger(GestorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
        return tp;
    }

}
