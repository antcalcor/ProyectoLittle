/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author Usuario
 */
public class MetodosFactura extends Database{
    
    public ArrayList<String> nifClientes(){
        
        ArrayList<String> aux = new ArrayList<String>();
        String q = "SELECT nif FROM clientes";
        
        try{
            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            int i=0;
            while (res.next()){
                aux.add(res.getString("nif"));
                i++;
            }
            pstm.execute();
            pstm.close();
            
            return aux;

        }catch (SQLException e){
            System.err.println(e.getMessage());
            return aux;
        }
    }
    
        public ArrayList<Integer> codigoArticulos(){
        
        ArrayList<Integer> aux = new ArrayList<Integer>();
        String q = "SELECT CodArt FROM articulos";
        
        try{
            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            int i=0;
            while (res.next()){
                aux.add(res.getInt("CodArt"));
                i++;
            }
            pstm.execute();
            pstm.close();
            
            return aux;

        }catch (SQLException e){
            System.err.println(e.getMessage());
            return aux;
        }
    }
        
        public Cliente devolverCliente(String dni){
            
        String q = "SELECT nombre, direcc FROM clientes WHERE dni='" + dni + "'";
            
        Cliente aux = new Cliente();
        
            try{
                
                PreparedStatement pstm = this.getConnection().prepareStatement(q);
                ResultSet res = pstm.executeQuery();
                int i=0;
                while (res.next()){
                    aux.setNombre(res.getString("nombre"));
                    aux.setDireccion(res.getString("direcc"));
                    aux.setNIF(dni);
                    i++;
                }
                pstm.execute();
                pstm.close();
                return aux;
            
            }catch (SQLException e){
            System.err.println(e.getMessage());
            return aux;
            }
        }
        
        public Articulo devolverArticulo(int codigo){
            
            String q = "SELECT nombre, precio FROM clientes WHERE CodArt='" + codigo + "'";
            
            Articulo aux = new Articulo();
        
            try{
                
                PreparedStatement pstm = this.getConnection().prepareStatement(q);
                ResultSet res = pstm.executeQuery();
                int i=0;
                while (res.next()){
                    aux.setNombre(res.getString("nombre"));
                    aux.setPrecio(res.getDouble("precio"));
                    aux.setCodArt(codigo);
                    i++;
                }
                pstm.execute();
                pstm.close();
                return aux;
            
            }catch (SQLException e){
            System.err.println(e.getMessage());
            return aux;
            }
            
        }
    
}
