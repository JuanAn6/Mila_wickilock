/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package capavista;

import capamodel.Punt;
import capamodel.Ruta;
import capamodel.User;
import static capavista.Login.gBD;
import interficiepersistencia.IGestorPersistencia;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.out;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Juan Antonio
 */
public class Rutes extends javax.swing.JFrame {

    /**
     * Creates new form Rutes
     */
    protected static ArrayList<Punt> punts = new ArrayList();
    private List<Ruta> rutes = null;
    
    // Variables per dades de connexió amb JRS
    private String urlJRS;
    private String userJRS;
    private String passwordJRS;
    
    public Rutes(User u) {
        initComponents();
        slideAltitud.setValue(0);
        slideDistancia.setValue(0);
        setTitle("WikiLoc Juan Antonio");
        
        punts = new ArrayList();
        
        //tabe primer cop:
        //titol data propietari distancia dificultat desnivell Punts Valoracions
        
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Titol");
        tableModel.addColumn("Data");
        tableModel.addColumn("Propietari");
        tableModel.addColumn("Distancia");
        tableModel.addColumn("Dificultat");
        tableModel.addColumn("Desnivell");
        tableModel.addColumn("Punts");
        tableModel.addColumn("Valoracio");

        // Agregar filas de datos al modelo
        rutes = gBD.obtenirLlistaRutes();
        
        for(Ruta r : rutes){
            int punts = gBD.obtenirLlistaPuntsRuta(r).size();
            if(!Double.isNaN(r.getValoracions())){
                tableModel.addRow(new Object[]{r.getTitol(), r.getData(), r.getPropietari(), r.getDistancia(), r.getDificultat(), r.getDesn_p(), punts, r.getValoracions()});
            }else{
                tableModel.addRow(new Object[]{r.getTitol(), r.getData(), r.getPropietari(), r.getDistancia(), r.getDificultat(), r.getDesn_p(), punts,"Senese valoracions"});
            }
            
        }
        
        table.setModel(tableModel);
        
        data1.setText("DD/MM/YYYY");
        data1.setForeground(Color.GRAY);
        data1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if ("DD/MM/YYYY".equals(data1.getText())) {
                    data1.setText("");
                    data1.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (data1.getText().isEmpty()) {
                    data1.setText("DD/MM/YYYY");
                    data1.setForeground(Color.GRAY);
                }
            }
        });
        data2.setText("DD/MM/YYYY");
        data2.setForeground(Color.GRAY);
        data2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if ("DD/MM/YYYY".equals(data2.getText())) {
                    data2.setText("");
                    data2.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (data2.getText().isEmpty()) {
                    data2.setText("DD/MM/YYYY");
                    data2.setForeground(Color.GRAY);
                }
            }
        });
        
        String fitxerConfigJRS = "informesJRS.xml";
        try {
            Properties props = new Properties();
            props.loadFromXML(new FileInputStream(fitxerConfigJRS));
            String[] claus = {"url", "user", "password"};
            String[] valors = new String[3];
            for (int i = 0; i < claus.length; i++) {
                valors[i] = props.getProperty(claus[i]);
                if (valors[i] == null || valors[i].isEmpty()) {
                    System.out.println("No es troba clau " + valors[i] + " en fitxer " + fitxerConfigJRS);
                }
            }
            urlJRS = valors[0];
            userJRS = valors[1];
            passwordJRS = valors[2];
            System.out.println("Paràmetres per connectar amb JRS recuperats.");
        } catch (FileNotFoundException ex) {
            System.out.println("No es troba fitxer " + fitxerConfigJRS + " - No es podrà executar cap informe");
        } catch (IOException ex) {
            System.out.println(ex.getMessage() + " - Probablement no es podrà executar cap informe");
        }
        
        this.addWindowListener(
                new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (gBD != null) {      // Per si no s'havia establert connexió
                    try {
                        gBD.tancarConnexio();
                    } catch (Exception ex) {
                        System.out.println("Error en tancar la connexió.\n\nMotiu:\n\n" + ex.getMessage());
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex1) {
                        }
                    }
                }
                System.exit(0);

            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        busquedaField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        busquedaButton = new javax.swing.JButton();
        netejarFiltre = new javax.swing.JButton();
        slideAltitud = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        slideDistancia = new javax.swing.JSlider();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        labelSlideAltitud = new javax.swing.JTextField();
        labelSlideDistancia = new javax.swing.JTextField();
        logOutButton = new javax.swing.JButton();
        crearButton = new javax.swing.JButton();
        eliminarButton = new javax.swing.JButton();
        modificarButton = new javax.swing.JButton();
        errorLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        data1 = new javax.swing.JTextField();
        data2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnInformes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Les meves rutes");

        jLabel2.setText("🔍");

        busquedaButton.setText("🔍 Buscar");
        busquedaButton.setToolTipText("");
        busquedaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busquedaButtonActionPerformed(evt);
            }
        });

        netejarFiltre.setText("Netejar filtres");
        netejarFiltre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                netejarFiltreActionPerformed(evt);
            }
        });

        slideAltitud.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slideAltitudStateChanged(evt);
            }
        });

        jLabel3.setText("Altitud");

        slideDistancia.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slideDistanciaStateChanged(evt);
            }
        });

        jLabel4.setText("Distancia");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table);

        logOutButton.setText("Log Out");
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });

        crearButton.setText("➕ Crear nova Ruta");
        crearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearButtonActionPerformed(evt);
            }
        });

        eliminarButton.setText("🗑️ Eliminar");
        eliminarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarButtonActionPerformed(evt);
            }
        });

        modificarButton.setText("⚙️ Modificar ruta");
        modificarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarButtonActionPerformed(evt);
            }
        });

        errorLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        errorLabel.setForeground(new java.awt.Color(204, 0, 0));

        jButton1.setText("Recaregar rutes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText("🔁");

        jLabel6.setText("-");

        jLabel7.setText("De data a data");
        jLabel7.setToolTipText("");

        btnInformes.setText("Informes");
        btnInformes.setActionCommand("");
        btnInformes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(189, 189, 189)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(netejarFiltre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addComponent(busquedaField, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(busquedaButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logOutButton)
                        .addGap(40, 40, 40))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 964, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(eliminarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(474, 474, 474)
                                .addComponent(crearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(modificarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnInformes, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(slideAltitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelSlideAltitud, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(slideDistancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelSlideDistancia, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(data1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel6)
                                        .addGap(23, 23, 23)
                                        .addComponent(data2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(88, 88, 88)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(12, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(busquedaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(busquedaButton)
                    .addComponent(logOutButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(netejarFiltre)
                    .addComponent(jButton1)
                    .addComponent(jLabel5))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(slideAltitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSlideAltitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(slideDistancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSlideDistancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(data1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(data2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(modificarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(crearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnInformes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(eliminarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void slideAltitudStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slideAltitudStateChanged
        // TODO add your handling code here:
        if(slideAltitud.getValue() == 0 || slideAltitud.getValue() == 100){
            labelSlideAltitud.setText("Tots");
        }else{
            labelSlideAltitud.setText(""+slideAltitud.getValue()*80+" m");
        }
    }//GEN-LAST:event_slideAltitudStateChanged

    private void slideDistanciaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slideDistanciaStateChanged
        // TODO add your handling code here:
        if(slideDistancia.getValue() == 0 || slideDistancia.getValue() == 100){
            labelSlideDistancia.setText("Tots");
        }else{
            labelSlideDistancia.setText(""+slideDistancia.getValue()*3+" Km");
        }
    }//GEN-LAST:event_slideDistanciaStateChanged

    private void crearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearButtonActionPerformed
        // TODO add your handling code here:
        CrearRuta c = new CrearRuta();
        c.setVisible(true);
        dispose();
        
        //update de ruta;
//        System.out.println(table.getSelectedRow());
//        if(table.getSelectedRow() != -1){
//            
//        }
    }//GEN-LAST:event_crearButtonActionPerformed

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
        // TODO add your handling code here:
        Login l = new Login();
        l.setVisible(true);
        dispose();
    }//GEN-LAST:event_logOutButtonActionPerformed

    private void modificarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarButtonActionPerformed
        // TODO add your handling code here:
        errorLabel.setText(" ");
        if(table.getSelectedRow() != -1){
            
            ModificarRuta r = new ModificarRuta(rutes.get(table.getSelectedRow()));
            r.setVisible(true);
            dispose();
        }else{
            errorLabel.setText("Has de seleccionar una ruta");
            //System.out.println("Has de seleccionar una ruta");
        }
        
    }//GEN-LAST:event_modificarButtonActionPerformed

    private void eliminarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarButtonActionPerformed
        // TODO add your handling code here:
        errorLabel.setText(" ");
        if(table.getSelectedRow() != -1){
            Esborrar esb = new Esborrar(this, true, "Vols esborar la ruta de titol: "+rutes.get(table.getSelectedRow()).getTitol() , rutes.get(table.getSelectedRow()));
            esb.setVisible(true);
            
       }else{
            errorLabel.setText("Has de seleccionar una ruta");
            //System.out.println("Has de seleccionar una ruta");
        }
    }//GEN-LAST:event_eliminarButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        rutes = gBD.obtenirLlistaRutes();
        recargarRutes();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void busquedaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_busquedaButtonActionPerformed
        // TODO add your handling code here:
        errorLabel.setText("");
        boolean altitudB = false;
        int alt = 0;
        boolean distanciaB = false;
        int dis = 0;
        boolean textB = false;
        
        boolean dataB = false;
        
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = null;
        Date d2 = null;
        try {
            //System.out.println("data1: "+data1.getText()+" data2: "+data2.getText());
            if(data1.getText().trim().length() == 10 && !data1.getText().equals("DD/MM/YYYY")){
                d1 = formateador.parse(data1.getText());
                dataB = true;
            }
            
            if(data2.getText().trim().length() == 10 && !data2.getText().equals("DD/MM/YYYY")){
                d2 = formateador.parse(data2.getText());
            }else{
                d2 = new Date();
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(Rutes.class.getName()).log(Level.SEVERE, null, ex);
            errorLabel.setText("Data invalida");
        }
 
        
        if(slideAltitud.getValue() == 0 || slideAltitud.getValue() == 100){
            altitudB = false;
        }else{
            altitudB = true;
            alt = slideAltitud.getValue()*80;
        }
        
        if(slideDistancia.getValue() == 0 || slideDistancia.getValue() == 100){
            distanciaB = false;
        }else{
            distanciaB = true;
            //en la base de dades la distancia esta guardad en metres
            dis = slideDistancia.getValue()*3*1000;   
        }
        
        String s = busquedaField.getText();
        if(s.trim().length() <= 0){
            textB = false;
        }else{
            textB = true;
        }
        
        List<Ruta> rutesBusqueda = null;
        if(textB){
            rutesBusqueda = gBD.obtenirRutaTitol(s);
        }else{
            rutesBusqueda = gBD.obtenirLlistaRutes();
        }
        
        if(altitudB){
            ArrayList<Ruta> aux = new ArrayList<Ruta>();
            
            for (Ruta r: rutesBusqueda){
                if(r.getDesn_p() <= alt){
                    aux.add(r);
                }
            }
            rutesBusqueda = aux;      
        }
        if(distanciaB){
            ArrayList<Ruta> aux = new ArrayList<Ruta>();
            for (Ruta r: rutesBusqueda){
                if(r.getDistancia() <= dis){
                    aux.add(r);
                }
            }
            rutesBusqueda = aux;  
        }
        
        if(dataB){
            ArrayList<Ruta> aux = new ArrayList<Ruta>();
            for (Ruta r: rutesBusqueda){
                
                if((r.getData().after(d1) && r.getData().before(d2)) || formateador.format(r.getData()).equals(formateador.format(d1))) {
                    aux.add(r);
                }
            }
            rutesBusqueda = aux; 
        }
        
        rutes = rutesBusqueda;
        recargarRutes();
    }//GEN-LAST:event_busquedaButtonActionPerformed

    private void netejarFiltreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_netejarFiltreActionPerformed
        // TODO add your handling code here:
        
        data1.setText("DD/MM/YYYY");
        data1.setForeground(Color.GRAY);
        data2.setText("DD/MM/YYYY");
        data2.setForeground(Color.GRAY);
        slideAltitud.setValue(0);
        slideDistancia.setValue(0);
        busquedaField.setText("");
        errorLabel.setText("");
        
    }//GEN-LAST:event_netejarFiltreActionPerformed

    private void btnInformesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformesActionPerformed
        // TODO add your handling code here:
        
        String codiProducte = ""+rutes.get(table.getSelectedRow()).getId();
        
        int BUFFER_SIZE = 4096;
        String url = urlJRS + "P1-T6-informe_JuanAntonioGarcia.pdf"
                + "?codi=" + codiProducte;      // Emplenem el paràmetre "codi" de l'informe
        // Si hi ha més paràmetres a passar, cal concatenar-los com "&" com:
        // + "&nomParametre=valor&nomParametre=valor..."
        URL obj;
        int responseCode = -1;
        HttpURLConnection con = null;
        try {
            obj = new URL(url);
        
        con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        String autenticacio = Base64.getEncoder().encodeToString((userJRS + ":" + passwordJRS).getBytes());
        con.setRequestProperty("Authorization", "Basic " + autenticacio);
        responseCode = con.getResponseCode();
        
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = con.getHeaderField("Content-Disposition");
            String contentType = con.getContentType();
            int contentLength = con.getContentLength();

            if (disposition != null) {
                // Obtenir el nom del fitxer a partir de la capçalera (Content-Disposition)
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // Obtenir el nom del fitxer de dins la URL
                int posArguments = url.lastIndexOf("?");
                if (posArguments == -1) { // No hi ha arguments
                    fileName = url.substring(url.lastIndexOf("/") + 1,
                            url.length());
                } else { // Hi ha arguments i cal eliminar-los per obtenir el nom del fitxer
                    fileName = url.substring(url.lastIndexOf("/") + 1, posArguments);
                }
            }

//            System.out.println("Content-Type = " + contentType);
//            System.out.println("Content-Disposition = " + disposition);
//            System.out.println("Content-Length = " + contentLength);
//            System.out.println("fileName = " + fileName);
//            System.out.println("url = " + url);

            // Obrim InputStream des de HTTP connection
            InputStream inputStream = con.getInputStream();
            // Obrim OutputStream per enregistrar el fitxer
            FileOutputStream outputStream = new FileOutputStream(fileName);
            // Llegim i escrivim
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();

//            System.out.println("Arxiu descarregat");
            // Intentem obrir-lo en alguna aplicació del SO
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(new File(fileName));
                } catch (IOException ex) {
                    System.out.println("No hi ha aplicacions disponibles per obrir el fitxer");
                }
            }
        } else {
            System.out.println("Mètode 'GET' : " + url);
            System.out.println("Codi resposta: " + responseCode);
            System.out.println("Cap fitxer a descarregar");
        }
        con.disconnect();
    
        } catch (MalformedURLException ex) {
            Logger.getLogger(Rutes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Rutes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInformesActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Rutes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Rutes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Rutes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Rutes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //User u = new User();
                //new Rutes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInformes;
    private javax.swing.JButton busquedaButton;
    private javax.swing.JTextField busquedaField;
    private javax.swing.JButton crearButton;
    private javax.swing.JTextField data1;
    private javax.swing.JTextField data2;
    private javax.swing.JButton eliminarButton;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTextField labelSlideAltitud;
    private javax.swing.JTextField labelSlideDistancia;
    private javax.swing.JButton logOutButton;
    private javax.swing.JButton modificarButton;
    private javax.swing.JButton netejarFiltre;
    private javax.swing.JSlider slideAltitud;
    private javax.swing.JSlider slideDistancia;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    
    public void recargarRutes(){
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Titol");
        tableModel.addColumn("Data");
        tableModel.addColumn("Propietari");
        tableModel.addColumn("Distancia");
        tableModel.addColumn("Dificultat");
        tableModel.addColumn("Desnivell");
        tableModel.addColumn("Punts");
        tableModel.addColumn("Valoracio");
        
        for(Ruta r : rutes){
            int punts = gBD.obtenirLlistaPuntsRuta(r).size();
            tableModel.addRow(new Object[]{r.getTitol(), r.getData(), r.getPropietari(), r.getDistancia(), r.getDificultat(), r.getDesn_p(), punts, r.getValoracions()});
        }
        
        table.setModel(tableModel);
    }
}
