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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class MetodosFactura extends Database{
    
    public static ArrayList<ArtFact> articulosFactura = new ArrayList<ArtFact>();
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

    public void devolverCliente(String dni,Cliente cliente){

        String q = "SELECT nombre, direcc FROM clientes WHERE nif='" + dni + "'";

        try{

            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            int i=0;
            while (res.next()){
                cliente.setNombre(res.getString("nombre"));
                cliente.setDireccion(res.getString("direcc"));
                cliente.setNIF(dni);
                i++;
            }
            pstm.execute();
            pstm.close();

        }catch (SQLException e){
        System.err.println(e.getMessage());
        }
    }
    
    public double devolverPrecio(int codArt){
        
        String q = "SELECT precio FROM articulos WHERE CodArt='" + codArt + "'";

        double precio=0;
        
        try{

            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            
            int i=0;
            
            while(res.next()){
                precio = res.getDouble("precio");
                i++;
            }
            pstm.execute();
            pstm.close();
            
            return precio;

        }catch (SQLException e){
        System.err.println(e.getMessage());
        
        return precio;
        
        }
        
    }
    
    public void agnadirArticulo(int codArt,int cantidad,double precio) throws SQLException{
        
        String q = "SELECT nombreArt FROM articulos WHERE CodArt = '" + codArt + "'";
        
        String q2 = "SELECT max(codFact) FROM artfact";
        
        PreparedStatement pstm = this.getConnection().prepareStatement(q);
        ResultSet res = pstm.executeQuery();
        
        int codFact=0;
        String nombreArt="";
        int i=0;
        while(res.next()){
            
            nombreArt=res.getString("nombreArt");
            i++;
        }
        pstm.execute();
        pstm.close();
        
        PreparedStatement pstm2 = this.getConnection().prepareStatement(q2);
        ResultSet res2 = pstm2.executeQuery(q2);
        
        i=0;
        
        while(res2.next()){
            
            codFact=res2.getInt("max(codFact)");
            
        }
        
        ArtFact articulo = new ArtFact(codArt,codFact+1,nombreArt,cantidad,precio);
        articulosFactura.add(articulo);
        
    }
    
    public DefaultTableModel agnadirArticulosTabla(){
        
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros=articulosFactura.size();
        String[] columNames = {"Codigo","Nombre","Cantidad","Precio"};
        
        Object[][] data = new String[registros][4];
        
        for(int i=0;i>registros;i++){
            data[i][0] = articulosFactura.get(i).getCodArt();
            data[i][1] = articulosFactura.get(i).getNombreArt();
            data[i][2] = articulosFactura.get(i).getCantidad();
            data[i][3] = articulosFactura.get(i).getPrecio();
        }
        
        tablemodel.setDataVector(data, columNames);
        
        return tablemodel;
    }
    
}
