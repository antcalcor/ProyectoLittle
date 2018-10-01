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
import modelo.Cliente;
import modelo.MetodosCliente;
import vista.PantallaInicial;

/**
 *
 * @author ldami
 */
public class ControladorCliente implements ActionListener, MouseListener{
    
    
    public PantallaInicial pIni = new PantallaInicial();
    public MetodosCliente mCli = new MetodosCliente ();
    public Cliente cliente=null;
    String nif, nombre, direccion;

    //Declaramos en un enum las acciones relacionadas con los clientes
    public enum accionesCliente{
        BUSCAR,INSERTAR,MODIFICAR,BORRAR,VOLVER,
	}
    
    //CONSTRUCTOR DE CLASE
    public ControladorCliente(PantallaInicial pantallaInicial) {
		this.pIni = pantallaInicial;
	}
    
    //INICIAMOS
	public void Iniciar() {
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
		//TABLA DE CLIENTES
		this.pIni.jTable1.addMouseListener(this);
		this.pIni.jTable1.setModel(new DefaultTableModel());
		
				
		//Declaramos las acciones y escuchas al evento producido por el
		//componente
		
		//TODO a�adir acciones y escuchas
		pIni.jButton4.setActionCommand("MODIFICAR");
		pIni.jButton4.addActionListener(this);
		pIni.jButton5.setActionCommand("INSERTAR");
		pIni.jButton5.addActionListener(this);
		pIni.jButton6.setActionCommand("BUSCAR");
		pIni.jButton6.addActionListener(this);
		pIni.jButton7.setActionCommand("VOLVER");
		pIni.jButton7.addActionListener(this);
		pIni.jButton8.setActionCommand("BORRAR");
		pIni.jButton8.addActionListener(this);
		
		/*pIni.ComprarEntrada.setLocationRelativeTo(null);
		pIni.ConsultarCompras.setLocationRelativeTo(null);
		pIni.AgnadirOpinion.setLocationRelativeTo(null);
		pIni.ConsultarCartelera.setLocationRelativeTo(null);*/
		
                pIni.jTable1.setModel(mCli.cogerClientesBBDDTodo());
		
		
	}
    
        public void actionPerformed(ActionEvent e) {
		switch (accionesCliente.valueOf(e.getActionCommand())) {
		//PARA VOLVER A LA PANTALLA INICIAL
		case VOLVER:
			pIni.setVisible(true);
			pIni.jDialog1.setVisible(false);
			break;
                 
                //PARA INSERTAR UN CLIENTE        
		case INSERTAR:
			//cogemos los datos de los campos		
			nombre = pIni.jTextField2.getText();
			System.out.println(nombre);
			direccion = pIni.jTextField3.getText();
			nif = pIni.jFormattedTextField1.getText();
                            
			mCli.CrearCliente(nif, nombre, direccion); 
			pIni.jTable1.setModel(mCli.cogerClientesBBDDTodo());
			
			break;
                        
                //PARA BORRAR UN CLIENTE
		case BORRAR:
                        
                        mCli.eliminarCliente(nif);
			pIni.jTable1.setModel(mCli.cogerClientesBBDDTodo());
                        
			break;
                        
                //PARA MODIFICAR UN CLIENTE
		case MODIFICAR:

                    mCli.modificarCliente(nif, nombre, direccion);
			
			break;
                        
                //PARA BUSCAR UN CLIENTE
                case BUSCAR:

                    cliente = mCli.buscarCliente(nif);
                    pIni.jTextField2.setText(cliente.getNombre());
                    pIni.jFormattedTextField1.setText(cliente.getNIF());
                    pIni.jTextField3.setText(cliente.getDireccion());
			
			break;
                        
		default:
			break;
		}
	}
	
	private void presionarJTable1(java.awt.event.MouseEvent e) {

		if (e.getButton() == 1)// boton izquierdo
		{
			int fila = this.pIni.jTable1.rowAtPoint(e.getPoint());
			if (fila > -1) {
				
				pIni.jFormattedTextField1.setText(String.valueOf(this.pIni.jTable1.getValueAt(fila, 0)));
				pIni.jTextField2.setText(String.valueOf(this.pIni.jTable1.getValueAt(fila, 1)));
                                pIni.jTextField3.setText(String.valueOf(this.pIni.jTable1.getValueAt(fila, 2)));
			}
		}
	}
	
	
    @Override
    public void mouseClicked(MouseEvent e) {
            // para que tenga en cuenta el click en la tabla
            presionarJTable1(e);
                
    }
  

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
    
    
    
