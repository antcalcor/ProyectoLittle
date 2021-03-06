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
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import modelo.Articulo;

import modelo.MetodosArticulo;

import vista.PantallaInicial;

/**
 *
 * @author ldami
 */
public class ControladorArticulo implements ActionListener, MouseListener{
    
    
    public PantallaInicial pIni = new PantallaInicial();
    public MetodosArticulo mArt = new MetodosArticulo ();
    public Articulo articulo=null;
    String nombre;
    int codArt;
    double precio;

    //Declaramos en un enum las acciones relacionadas con los clientes
    public enum accionesArticulo{
        BUSCAR,INSERTAR,MODIFICAR,BORRAR,VOLVER,
	}
    
    //CONSTRUCTOR DE CLASE
    public ControladorArticulo(PantallaInicial pantallaInicial) {
            super();
            this.pIni = pantallaInicial;
	}
    
    //INICIAMOS
	public void Iniciar() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(pIni);
			pIni.setVisible(false);
                        pIni.jDialog2.setVisible(true);
			pIni.setLocationRelativeTo(null);
		} catch (UnsupportedLookAndFeelException ex) {
		} catch (ClassNotFoundException ex) {
		} catch (InstantiationException ex) {
		} catch (IllegalAccessException ex) {
		}
		//TABLA DE CLIENTES
		this.pIni.jTable2.addMouseListener(this);
		this.pIni.jTable2.setModel(new DefaultTableModel());
		
				
		//Declaramos las acciones y escuchas al evento producido por el
		//componente
		
		// añadir acciones y escuchas
		pIni.jButton10.setActionCommand("MODIFICAR");
		pIni.jButton10.addActionListener(this);
		pIni.jButton11.setActionCommand("INSERTAR");
		pIni.jButton11.addActionListener(this);
		pIni.jButton12.setActionCommand("BUSCAR");
		pIni.jButton12.addActionListener(this);
		pIni.jButton13.setActionCommand("VOLVER");
		pIni.jButton13.addActionListener(this);
		pIni.jButton9.setActionCommand("BORRAR");
		pIni.jButton9.addActionListener(this);
		
		pIni.jDialog2.setLocationRelativeTo(null);
		
		
                pIni.jTable2.setModel(mArt.cogerArticulosBBDDTodo());
		
	}
    
    @Override
        public void actionPerformed(ActionEvent e) {
            
            switch (accionesArticulo.valueOf(e.getActionCommand())) {
		//PARA VOLVER A LA PANTALLA INICIAL
		case VOLVER:
                    
			pIni.jDialog2.setVisible(false);
                        pIni.setVisible(true);
                        
			break;
                 
                //PARA INSERTAR UN CLIENTE        
		case INSERTAR:
                    
                    try{
                        
                        //cogemos los datos de los campos		
			nombre = pIni.jTextField1.getText();
                        codArt = Integer.parseInt(pIni.jFormattedTextField4.getText());
                        precio = Double.parseDouble(pIni.campoPrecio.getText());
			System.out.println(nombre);
			                            
			mArt.CrearArticulo(codArt, nombre, precio);  
			pIni.jTable2.setModel(mArt.cogerArticulosBBDDTodo());
                        
                    }catch (NumberFormatException a){
                        JOptionPane.showMessageDialog(null, "El precio tiene que ser un número y el decimal se separa con '.'");
                    }
			
		break;
                        
                //PARA BORRAR UN CLIENTE
		case BORRAR:
                    try{
                        
                        codArt = Integer.parseInt(pIni.jFormattedTextField4.getText());
                        mArt.eliminarArticulo(codArt);
			pIni.jTable2.setModel(mArt.cogerArticulosBBDDTodo());
                        
                    }   catch (NumberFormatException a){
                        JOptionPane.showMessageDialog(null, "El precio tiene que ser un número y el decimal se separa con '.'");
                    }                 

		break;
                        
                //PARA MODIFICAR UN CLIENTE
		case MODIFICAR:
                    
                    try{
                        
                        nombre = pIni.jTextField1.getText();
                        codArt = Integer.parseInt(pIni.jFormattedTextField4.getText());
                        precio = Double.parseDouble(pIni.campoPrecio.getText());
                        mArt.modificarArticulo(codArt, nombre, precio);
                        pIni.jTable2.setModel(mArt.cogerArticulosBBDDTodo());
                        
                    }catch (NumberFormatException a){
                        JOptionPane.showMessageDialog(null, "El precio tiene que ser un número y el decimal se separa con '.'");
                    }
                      
                    			
			break;
                        
                //PARA BUSCAR UN ARTICULO
                case BUSCAR:

                    Articulo articulo=new Articulo ();
                    int codArt1 = Integer.parseInt(pIni.jFormattedTextField3.getText());
                    mArt.buscarArticulo(articulo, codArt1);
                    pIni.jTextField1.setText(articulo.getNombre());
                    pIni.jFormattedTextField4.setText(pIni.jFormattedTextField3.getText());
                    pIni.campoPrecio.setText(String.valueOf(articulo.getPrecio()));
		
			break;
                        
		default:
			break;
		}
	}
	
	private void presionarJTable2(java.awt.event.MouseEvent e) {

		if (e.getButton() == 1)// boton izquierdo
		{
			int fila = this.pIni.jTable2.rowAtPoint(e.getPoint());
			if (fila > -1) {
                            
                            pIni.jFormattedTextField4.setText(String.valueOf(this.pIni.jTable2.getValueAt(fila, 0)));
                            pIni.jTextField1.setText(String.valueOf(this.pIni.jTable2.getValueAt(fila, 1)));
                            pIni.campoPrecio.setText(String.valueOf(this.pIni.jTable2.getValueAt(fila, 2)));
			}
                        
                        }}
	
	
    @Override
    public void mouseClicked(MouseEvent e) {
            // para que tenga en cuenta el click en la tabla
            //presionarJTable1(e);
                
    }
  

    @Override
    public void mousePressed(MouseEvent e) {
        presionarJTable2(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
         //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
    
    
    

