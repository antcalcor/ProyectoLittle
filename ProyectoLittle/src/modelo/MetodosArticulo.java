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
public class MetodosArticulo extends Database{
    
    static HashMap<String, Articulo> Articulos = new HashMap<String, Articulo>();
    
    // Crear ARTICULO
	// ------------------------------------------------------
	public void CrearArticulo(int codArt, String nombre, double precio) {
		if (nombre == null || precio == 0 || codArt == 0) {
			JOptionPane.showMessageDialog(null, "No has introducido todos los valores");
		} else {
			insertarArticuloBBDD(codArt, nombre, precio);
			
			
		}
	}
        
        //INSERTAR ARTICULO
        public void insertarArticuloBBDD(int codArt, String nombre, double precio) {
			// se arma la consulta
			String q = "INSERT INTO articulos (CodArt, nombreArt, precio)" + "VALUES ('" + codArt + "','" + nombre + "','"
					+ precio + "')";
			// se ejecuta la consulta
			try {
				PreparedStatement pstm = this.getConnection().prepareStatement(q);
				pstm.execute();
				pstm.close();
                                System.out.println("insertado");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Codigo artículo duplicado, no se ha podido añadir artículo");
			}

		}
        
        //ELIMINAR ARTICULO 
        // ------------------------------------------------------
	public void eliminarArticulo(int codArt) {
		if(codArt==0) {
			JOptionPane.showMessageDialog(null, "No has elegido ningun artículo");
		}else {
		eliminarArticuloBBDD(codArt);
		JOptionPane.showMessageDialog(null, "Artículo eliminado con exito");
		}
	}
        
        //ELIMINAR ARTICULO DE LA BBDD
        public void eliminarArticuloBBDD(int codArt) {
		String q = " DELETE FROM articulos where CodArt='" + codArt + "'";
		// se ejecuta la consulta
		try {
			PreparedStatement pstm = this.getConnection().prepareStatement(q);
			pstm.execute();
			pstm.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		devolverArticulosArray();
	}
        
        
        
	// MODIFICAR ARTICULO
        //------------------------------------------------------
	public void modificarArticulo(int codArt, String nombre, double precio) {
		actualizarArticuloBBDD(codArt, nombre, precio);
		JOptionPane.showMessageDialog(null, "Articulo modificado con exito");
		
	}
        
        //ACTUALIZAMOS ARTICULO EN LA BBDD
        public void actualizarArticuloBBDD(int codArt, String nombre, double precio) {
		String q = " UPDATE articulos " + "SET nombreArt='" + nombre
				+ "', precio= '"+ precio + "' WHERE CodArt= '" + codArt + "'";

		try {
			PreparedStatement pstm = this.getConnection().prepareStatement(q);
			pstm.execute();
			pstm.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
        
        //BORRAR ARRAY DE ARTICULOS
        public void eliminarArticulosArray() {
		Iterator it = Articulos.keySet().iterator();
		while (it.hasNext()) {
			String clave = (String) it.next();
			Articulos.remove(clave);
		}
	}
    
    
        //DEVOLVER ARRAY DE ARTICULOS
        public void devolverArticulosArray() {
		// se arma la consulta
		String q = "SELECT CodArt, nombreArt, precio FROM articulos ";
		// se ejecuta la consulta
		try {
			PreparedStatement pstm = this.getConnection().prepareStatement(q);
			ResultSet res = pstm.executeQuery();
			eliminarArticulosArray();
			int i = 0;
			while (res.next()) {
				
				Articulo articulo = new Articulo(res.getInt("CodArt"), res.getString("nombre"), res.getDouble("precio"));
				
				Articulos.put(res.getString("CodArt"), articulo);
				i++;
			}
			pstm.execute();
			pstm.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
    
        //RELLENAR TABLA DE CLIENTES
         public DefaultTableModel cogerArticulosBBDDTodo() {
		System.out.println("pruebaaaaaaaa");
		DefaultTableModel tablemodel = new DefaultTableModel();
		int registros = 0;
		PreparedStatement pstm = null;
		String[] columNames = { "Codigo Articulo", "Nombre", "Precio" };
		// obtenemos la cantidad de registros existentes en la tabla y se almacena en la
		// variable "registros"
		// para formar la matriz de datos
		try {
			System.out.println("pruebass");
			pstm = getConnection().prepareStatement("SELECT count(*) as total FROM articulos");
			ResultSet res = pstm.executeQuery();
			res.next();
			registros = res.getInt("total");
			res.close();
                        System.out.println("hasta aquí");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		// se crea una matriz con tantas filas y columnas que necesite
		Object[][] data = new String[registros][3];
		try {
			// realizamos la consulta sql y llenamos los datos en la matriz "Object[][]
			// data"
			pstm = this.getConnection()
					.prepareStatement("SELECT CodArt, nombreArt, precio FROM articulos");
			ResultSet res = pstm.executeQuery();
			int i = 0;
			while (res.next()) {
				data[i][0] = res.getInt("CodArt");
				data[i][1] = res.getString("nombreArt");
				data[i][2] = res.getDouble("precio");
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
         public Articulo buscarArticulo(int codArt){
             
             Articulo articulo=null;
             
        try {
            String q = "SELECT CodArt, nombreArt, precio FROM articulos WHERE CodArt = " + codArt;
            
            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            articulo = new Articulo(res.getInt("CodArt"), res.getString("nombreArt"), res.getDouble("precio"));        
            pstm.execute();
            pstm.close();
                 
            
        } catch (SQLException ex) {
            Logger.getLogger(MetodosCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articulo;
         }
    
    
    
    
    
    
    
    
    
    
    
    
}
