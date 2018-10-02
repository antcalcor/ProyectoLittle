/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ldami
 */
public class MetodosCliente extends Database{
    
    static HashMap<String, Cliente> Clientes = new HashMap<String, Cliente>();
    
    // Crear Cliente
	// ------------------------------------------------------
	public void CrearCliente(String nif, String nombre, String direccion) {
		if (nombre == null || direccion == null || nif == null) {
			JOptionPane.showMessageDialog(null, "No has introducido todos los valores");
		} else {
			insertarClienteBBDD(nif, nombre, direccion);
			
			
		}
	}
        
        //INSERTAR CLIENTE
        public void insertarClienteBBDD(String nif, String nombre, String direccion) {
			// se arma la consulta
			String q = "INSERT INTO clientes (nif, nombre, direcc)" + "VALUES ('" + nif + "','" + nombre + "','"
					+ direccion + "')";
			// se ejecuta la consulta
			try {
				PreparedStatement pstm = this.getConnection().prepareStatement(q);
				pstm.execute();
				pstm.close();
                                System.out.println("insertado");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "NIF duplicado, no se ha podido añadir cliente");
			}

		}
        
        //ELIMINAR CLIENTE 
        // ------------------------------------------------------
	public void eliminarCliente(String nif) {
		if(nif.length()==0) {
			JOptionPane.showMessageDialog(null, "No has elegido ningun cliente");
		}else {
		eliminarClienteBBDD(nif);
		JOptionPane.showMessageDialog(null, "Cliente eliminado con exito");
		}
	}
        
        //ELIMINAR CLIENTE DE LA BBDD
        public void eliminarClienteBBDD(String nif) {
		String q = " DELETE FROM clientes where nif='" + nif + "'";
		// se ejecuta la consulta
		try {
			PreparedStatement pstm = this.getConnection().prepareStatement(q);
			pstm.execute();
			pstm.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		devolverClientesArray();
	}
        
        
        
	// MODIFICAR CLIENTE
        //------------------------------------------------------
	public void modificarCliente(String nif, String nombre, String direccion) {
		actualizarClienteBBDD(nif, nombre, direccion);
		JOptionPane.showMessageDialog(null, "Cliente modificado con exito");
		
	}
        
        //ACTUALIZAMOS CLIENTE EN LA BBDD
        public void actualizarClienteBBDD(String nif, String nombre, String direccion) {
		String q = " UPDATE clientes " + "SET nombre='" + nombre
				+ "', direcc= '"+ direccion + "' WHERE nif= '" + nif + "'";

		try {
			PreparedStatement pstm = this.getConnection().prepareStatement(q);
			pstm.execute();
			pstm.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
        
        //BORRAR ARRAY DE CLIENTES
        public void eliminarClientesArray() {
		Iterator it = Clientes.keySet().iterator();
		while (it.hasNext()) {
			String clave = (String) it.next();
			Clientes.remove(clave);
		}
	}
    
    
        //DEVOLVER ARRAY DE CLIENTES
        public void devolverClientesArray() {
		// se arma la consulta
		String q = "SELECT nif, nombre, direcc FROM clientes ";
		// se ejecuta la consulta
		try {
			PreparedStatement pstm = this.getConnection().prepareStatement(q);
			ResultSet res = pstm.executeQuery();
			eliminarClientesArray();
			int i = 0;
			while (res.next()) {
				
				Cliente cliente = new Cliente(res.getString("nif"), res.getString("nombre"), res.getString("direcc"));
				
				Clientes.put(res.getString("nif"), cliente);
				i++;
			}
			pstm.execute();
			pstm.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
    
        //RELLENAR TABLA DE CLIENTES
         public DefaultTableModel cogerClientesBBDDTodo() {
		
		DefaultTableModel tablemodel = new DefaultTableModel();
		int registros = 0;
		PreparedStatement pstm = null;
		String[] columNames = { "NIF", "Nombre", "Direccion" };
		
                // obtenemos la cantidad de registros existentes en la tabla y se almacena en la
		// variable "registros"
		// para formar la matriz de datos
		try {
			
			pstm = getConnection().prepareStatement("SELECT count(*) as total FROM clientes");
			ResultSet res = pstm.executeQuery();
			res.next();
			registros = res.getInt("total");
			res.close();
                        
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		// se crea una matriz con tantas filas y columnas que necesite
		Object[][] data = new String[registros][3];
		try {
			// realizamos la consulta sql y llenamos los datos en la matriz "Object[][]
			// data"
			pstm = this.getConnection()
					.prepareStatement("SELECT nif, nombre, direcc FROM clientes");
			ResultSet res = pstm.executeQuery();
			int i = 0;
			while (res.next()) {
				data[i][0] = res.getString("nif");
				data[i][1] = res.getString("nombre");
				data[i][2] = res.getString("direcc");
				i++;
			}
			res.close();
			// se anade la matriz de datos en el DefaultTableModel
			tablemodel.setDataVector(data, columNames);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return tablemodel;

	}
         
         
         //METODO PARA BUSCAR CLIENTES
         public void buscarCliente(Cliente cliente, String nif){
             
             //Cliente cliente=null;
             
        try {
            String q = "SELECT nombre, direcc FROM clientes WHERE nif = '" + nif + "'";
            
            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            int i=0;
            while (res.next()){
                cliente.setNIF(nif);
                cliente.setNombre(res.getString("nombre"));
                cliente.setDireccion(res.getString("direcc"));
                i++;
            }
           // cliente = new Cliente(res.getString("nif"), res.getString("nombre"), res.getString("direcc"));        
            pstm.execute();
            pstm.close();
                 
            
        } catch (SQLException ex) {
            Logger.getLogger(MetodosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return cliente;
         }
    
    
    
    
    
    
    
    
    
    
    
    
}
