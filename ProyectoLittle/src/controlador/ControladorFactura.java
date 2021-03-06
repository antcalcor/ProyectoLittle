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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import modelo.Articulo;
import modelo.Cliente;
import modelo.Factura;
import modelo.MetodosFactura;
import static modelo.MetodosFactura.articulosFactura;
import vista.PantallaInicial;

/**
 *
 * @author Usuario
 */
public class ControladorFactura implements ActionListener, MouseListener {

    public PantallaInicial pIni = new PantallaInicial();
    public MetodosFactura mFac = new MetodosFactura();
    //creo un atributo estatico para poderlo usar en otra clase, es la fila que se pulsa en la tabla creada
    public static int fila;

    //Declaramos en un enum las acciones relacionadas con la factura
    public enum accionesFactura {
        BUSCAR_CLI, BUSCAR_ART, COMPRAR_PROD, CALCULAR_FAC, BORRAR_PROD, BUSCAR_FAC, BORRAR_FAC, GUARDAR_FAC, VOLVER, CLIENTE, ARTICULO;
    }

    //CONSTRUCTOR DE CLASE
    public ControladorFactura(PantallaInicial pantallaInicial) {

        this.pIni = pantallaInicial;

    }

    //INICIAMOS
    public void Iniciar() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(pIni);
            pIni.setVisible(false);
            pIni.jDialog3.setVisible(true);
            pIni.setLocationRelativeTo(null);
        } catch (UnsupportedLookAndFeelException ex) {
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }
        //TABLA DE FACTURAS
        this.pIni.jTable3.addMouseListener(this);
        this.pIni.jTable3.setModel(new DefaultTableModel());

        pIni.jComboBox1.addActionListener(this);
        pIni.jComboBox1.setActionCommand("CLIENTE");
        pIni.jComboBox2.addActionListener(this);
        pIni.jComboBox2.setActionCommand("ARTICULO");

        //rellenamos los jcombobox
        ArrayList<String> aux = new ArrayList<String>();
        aux = mFac.nifClientes();

        for (int i = 0; i < aux.size(); i++) {

            pIni.jComboBox1.addItem(aux.get(i));

        }

        ArrayList<Integer> aux1 = new ArrayList<Integer>();
        aux1 = mFac.codigoArticulos();

        for (int i = 0; i < aux1.size(); i++) {

            pIni.jComboBox2.addItem(String.valueOf(aux1.get(i)));

        }

        //añadimos acciones y escuchas de los componentes
        pIni.jButton14.setActionCommand("BUSCAR_CLI");
        pIni.jButton14.addActionListener(this);
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
        pIni.jButton22.setActionCommand("BUSCAR_ART");
        pIni.jButton22.addActionListener(this);

        pIni.jDialog3.setLocationRelativeTo(null);

        pIni.jTable3.setModel(mFac.agnadirArticulosTabla());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (accionesFactura.valueOf(e.getActionCommand())) {

            //añade los campos cuando seleccionas un item en el jcombobox
            case CLIENTE:

                Cliente cliente = new Cliente();

                mFac.devolverCliente(pIni.jComboBox1.getSelectedItem().toString(), cliente);
                pIni.jTextField4.setText(cliente.getNombre());
                pIni.jTextField5.setText(cliente.getNIF());
                pIni.jTextField6.setText(cliente.getDireccion());

                break;

            //añade los campos cuando seleccionas un item en el jcombobox
            case ARTICULO:

                pIni.jTextField7.setText(pIni.jComboBox2.getSelectedItem().toString());
                pIni.jTextField11.setText(String.valueOf(mFac.devolverPrecio(Integer.parseInt(pIni.jTextField7.getText()))));

                break;

            //cuando pulsa buscar devuelve en los jtextfield los parametros de cliente
            case BUSCAR_CLI:

                Cliente cliente1 = new Cliente();

                mFac.devolverCliente(pIni.jFormattedTextField6.getText(), cliente1);
                pIni.jTextField4.setText(cliente1.getNombre());
                pIni.jTextField5.setText(cliente1.getNIF());
                pIni.jTextField6.setText(cliente1.getDireccion());

                break;

            //cuando pulsa buscar articulo rellena los campos de articulo
            case BUSCAR_ART:

                pIni.jTextField11.setText(String.valueOf(mFac.devolverPrecio(Integer.parseInt(pIni.jTextField7.getText()))));
                pIni.jComboBox2.setSelectedItem(pIni.jTextField7.getText());

                break;
                
            //para volver a la vista principal
            case VOLVER:

                pIni.jDialog3.setVisible(false);
                pIni.setVisible(true);

                break;

            //cuando pulsa se añade el producto a la factura
            case COMPRAR_PROD:

                int cod,
                 cantidad;
                double precio;

                cod = Integer.parseInt(pIni.jTextField7.getText());
                cantidad = Integer.parseInt(pIni.jFormattedTextField7.getText());
                precio = Double.parseDouble(pIni.jTextField11.getText());

                try {
                    mFac.agnadirArticulo(cod, cantidad, precio);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorFactura.class.getName()).log(Level.SEVERE, null, ex);
                }

                pIni.jTable3.setModel(mFac.agnadirArticulosTabla());

                break;

            //cuando pulsa se borra el producto de la factura
            case BORRAR_PROD:

                if (articulosFactura.size() != 0) {
                    mFac.borrarProducto_array(fila);
                    pIni.jTable3.setModel(mFac.agnadirArticulosTabla());
                    fila = 0;
                }

                break;

            //cuando pulsa añade los campos de importe y total dependiendo de los productos que hay en la factura
            case CALCULAR_FAC:

                pIni.jTextField8.setText(String.format("%.2f", mFac.calcularImporte()));
                //pIni.jTextField8.setText(String.valueOf(mFac.calcularImporte()));
                pIni.jTextField9.setText(String.format("%.2f", mFac.calcularImporte() * 1.21));
                //pIni.jTextField9.setText(String.valueOf(mFac.calcularImporte()*1.21));

                break;

            //cuando pulsa guarda la factura en la bbdd y los campos se resetean
            case GUARDAR_FAC:

                if (mFac.guardarFacturaBBDD(pIni.jTextField5.getText()) == true) {

                    pIni.jFormattedTextField7.setText(String.valueOf(0));
                    pIni.jTextField8.setText("");
                    pIni.jTextField9.setText("");
                    articulosFactura.clear();
                    pIni.jTable3.setModel(mFac.agnadirArticulosTabla());
                }

                break;

            //cuando pulsa añade todos los campos de la factura que ha buscado
            case BUSCAR_FAC:

                Cliente cliente2 = new Cliente();

                mFac.devolverCliente(mFac.buscarFactura(Integer.parseInt(pIni.jTextField10.getText())), cliente2);

                pIni.jTable3.setModel(mFac.agnadirArticulosTabla());

                pIni.jTextField4.setText(cliente2.getNombre());
                pIni.jTextField5.setText(cliente2.getNIF());
                pIni.jTextField6.setText(cliente2.getDireccion());

                pIni.jTextField8.setText(String.format("%.2f", mFac.calcularImporte()));
                pIni.jTextField9.setText(String.format("%.2f", mFac.calcularImporte() * 1.21));

                break;

            //cuando pulsa elimina la factura de la bbdd y resetea los campos
            case BORRAR_FAC:

                if (mFac.borrarFactura(Integer.parseInt(pIni.jTextField10.getText())) == true) {

                    pIni.jTextField10.setText("");
                    pIni.jFormattedTextField7.setText(String.valueOf(0));
                    pIni.jTextField8.setText("");
                    pIni.jTextField9.setText("");
                    articulosFactura.clear();
                    pIni.jTable3.setModel(mFac.agnadirArticulosTabla());

                }

                break;

        }

    }

    //metodo para que cuando se presiona la tabla se rellenen los campos correspondientes
    private void presionarJTable3(java.awt.event.MouseEvent e) {

        if (e.getButton() == 1) {

            fila = this.pIni.jTable3.rowAtPoint(e.getPoint());
            if (fila > -1) {

                pIni.jTextField7.setText(String.valueOf(pIni.jTable3.getValueAt(fila, 0)));
                pIni.jComboBox2.setSelectedItem(pIni.jTextField7.getText());
                pIni.jFormattedTextField7.setText(String.valueOf(pIni.jTable3.getValueAt(fila, 2)));
                pIni.jTextField11.setText(String.valueOf(pIni.jTable3.getValueAt(fila, 3)));
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //To change body of generated methods, choose Tools | Templates.
        presionarJTable3(me);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //To change body of generated methods, choose Tools | Templates.
    }

}
