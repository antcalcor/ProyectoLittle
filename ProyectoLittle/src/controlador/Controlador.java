/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import vista.PantallaInicial;

/**
 *
 * @author Usuario
 */
public class Controlador implements ActionListener, MouseListener {

    public PantallaInicial pIni = new PantallaInicial();
    
    //Declaramos enum de acciones
    public enum accionesPIni {
        
        CLIENTE, ARTICULO, FACTURA;
        
    }
    
    //Constructor
    public Controlador (PantallaInicial pIni){
        
        this.pIni=pIni;
        
    }
    
    //Iniciamos
    public void Iniciar(){
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(pIni);
			pIni.setVisible(true);
			pIni.setLocationRelativeTo(null);
                        //pIni.setExtendedState(MAXIMIZED_BOTH);
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
        }

    //Declaramos las acciones y anidamos las escuchas al evento producido por el componente
       
        pIni.jButton1.setActionCommand("CLIENTE");
        pIni.jButton1.addActionListener(this);
        pIni.jButton2.setActionCommand("ARTICULO");
        pIni.jButton2.addActionListener(this);
        pIni.jButton3.setActionCommand("FACTURA");
        pIni.jButton3.addActionListener(this);
        pIni.jDialog1.setLocationRelativeTo(null);
        pIni.jDialog2.setLocationRelativeTo(null);
        pIni.jDialog3.setLocationRelativeTo(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        switch(accionesPIni.valueOf(ae.getActionCommand())){
            
            case CLIENTE:
                
                pIni.setVisible(false);
                new ControladorCliente (pIni).Iniciar();
                
                break;
                
            case ARTICULO:
                
                pIni.setVisible(false);
                new ControladorArticulo (pIni).Iniciar();
                
                break;
                
            case FACTURA:
                
                pIni.setVisible(false);
                pIni.jDialog3.setVisible(true);
                
                break;
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
