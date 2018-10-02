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
import javax.swing.table.DefaultTableModel;
import modelo.Factura;
import modelo.MetodosFactura;
import vista.PantallaInicial;

/**
 *
 * @author Usuario
 */
public class ControladorFactura implements ActionListener, MouseListener{

    public PantallaInicial pIni = new PantallaInicial();
    public MetodosFactura mFac = new MetodosFactura();
    
    //Declaramos en un enum las acciones relacionadas con la factura
    public enum accionesFactura{
        BUSCAR_CLI,BUSCAR_PROD,COMPRAR_PROD,CALCULAR_FAC,BORRAR_PROD,BUSCAR_FAC,BORRAR_FAC,GUARDAR_FAC,VOLVER;
    }
    
    //CONSTRUCTOR DE CLASE
    public ControladorFactura(PantallaInicial pantallaInicial){
        
        this.pIni = pantallaInicial;
        
    }
    
    //INICIAMOS
    public void Iniciar(){
        try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                SwingUtilities.updateComponentTreeUI(pIni);
                pIni.setVisible(false);
                pIni.jDialog1.setVisible(true);
                pIni.setLocationRelativeTo(null);
        } catch (UnsupportedLookAndFeelException ex) {
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }
        //TABLA DE FACTURAS
        this.pIni.jTable3.addMouseListener(this);
        this.pIni.jTable3.setModel(new DefaultTableModel());
        
        //rellenamos los jcombobox
        mFac.rellenarJComboBox(pIni.jComboBox1);
        
        //añadimos acciones y escuchas de los componentes
        pIni.jButton14.setActionCommand("BUSCAR_CLI");
        pIni.jButton14.addActionListener(this);
        pIni.jButton22.setActionCommand("BUSCAR_PROD");
        pIni.jButton22.addActionListener(this);
        pIni.jButton15.setActionCommand("COMPRAR_PROD");
        pIni.jButton15.addActionListener(this);
        pIni.jButton19.setActionCommand("CALCULAR_FAC");
        pIni.jButton19.addActionListener(this);
        pIni.jButton16.setActionCommand("BORRAR_PROD");
        pIni.jButton16.addActionListener(this);
        pIni.jButton20.setActionCommand("BUSCAR_FAC");
        pIni.jButton20.addActionListener(this);
        pIni.jButton21.setActionCommand("BORRAR_FAC");
        pIni.jButton21.addActionListener(this);
        pIni.jButton17.setActionCommand("GUARDAR_FAC");
        pIni.jButton17.addActionListener(this);
        pIni.jButton18.setActionCommand("VOLVER");
        pIni.jButton18.addActionListener(this);
        
        pIni.jDialog3.setLocationRelativeTo(null);
        
        //TODO pIni.jTable3.setModel(mFac.cogerFacturasBBDDTodo());
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
