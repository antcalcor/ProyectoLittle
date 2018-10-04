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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class MetodosFactura extends Database {

    //Creamos un array para guardar las lineas de la factura(articulos de cada factura)
    public static ArrayList<ArtFact> articulosFactura = new ArrayList<ArtFact>();

    //metodo para añadir a un arraylist el listado de dni de los clientes de la bbdd
    public ArrayList<String> nifClientes() {

        ArrayList<String> aux = new ArrayList<String>();
        String q = "SELECT nif FROM clientes";

        try {
            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                aux.add(res.getString("nif"));
                i++;
            }
            pstm.execute();
            pstm.close();

            return aux;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return aux;
        }
    }

    //metodo para añadir a un arraylist el listado de codigos de los articulos de la bbdd
    public ArrayList<Integer> codigoArticulos() {

        ArrayList<Integer> aux = new ArrayList<Integer>();
        String q = "SELECT CodArt FROM articulos";

        try {
            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                aux.add(res.getInt("CodArt"));
                i++;
            }
            pstm.execute();
            pstm.close();

            return aux;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return aux;
        }
    }

    //Método para sacar los datos de un cliente
    public void devolverCliente(String dni, Cliente cliente) {

        String q = "SELECT nombre, direcc FROM clientes WHERE nif='" + dni + "'";

        try {

            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                cliente.setNombre(res.getString("nombre"));
                cliente.setDireccion(res.getString("direcc"));
                cliente.setNIF(dni);
                i++;
            }
            pstm.execute();
            pstm.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //Método para sacar el precio de un artículo
    public double devolverPrecio(int codArt) {

        String q = "SELECT precio FROM articulos WHERE CodArt='" + codArt + "'";

        double precio = 0;

        try {

            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            ResultSet res = pstm.executeQuery();

            int i = 0;

            while (res.next()) {
                precio = res.getDouble("precio");
                i++;
            }
            pstm.execute();
            pstm.close();

            return precio;

        } catch (SQLException e) {
            System.err.println(e.getMessage());

            return precio;

        }

    }

    //Método para añadir un artículo al array de lineas de una factura
    public void agnadirArticulo(int codArt, int cantidad, double precio) throws SQLException {

        boolean duplicado = false;

        for (int i = 0; i < articulosFactura.size(); i++) {
            if (articulosFactura.get(i).getCodArt() == codArt) {
                duplicado = true;
            }
        }

        if (duplicado == false && cantidad>0) {

            String q = "SELECT nombreArt FROM articulos WHERE CodArt = " + codArt;

            String q2 = "SELECT max(codFact) FROM facturas";

            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            ResultSet res = pstm.executeQuery();

            int codFact = 0;
            String nombreArt = "";
            int i = 0;
            while (res.next()) {

                nombreArt = res.getString("nombreArt");
                i++;
            }
            pstm.execute();
            pstm.close();

            PreparedStatement pstm2 = this.getConnection().prepareStatement(q2);
            ResultSet res2 = pstm2.executeQuery(q2);

            i = 0;

            while (res2.next()) {

                codFact = res2.getInt("max(codFact)");

            }

            ArtFact articulo = new ArtFact(codArt, codFact + 1, nombreArt, cantidad, precio);
            articulosFactura.add(articulo);

        }

    }

    //Método para añadir los artículos a la tabla
    public DefaultTableModel agnadirArticulosTabla() {

        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = articulosFactura.size();
        String[] columNames = {"Codigo", "Nombre", "Cantidad", "Precio"};

        Object[][] data = new String[registros][4];

        int i = 0;
        while (i < articulosFactura.size()) {
            data[i][0] = String.valueOf(articulosFactura.get(i).getCodArt());
            data[i][1] = articulosFactura.get(i).getNombreArt();
            data[i][2] = String.valueOf(articulosFactura.get(i).getCantidad());
            data[i][3] = String.valueOf(articulosFactura.get(i).getPrecio());
            i++;
        }
        /*for(int i=0;i<registros;i++){
            System.out.println("prueba tabla 2");
            data[i][0] = String.valueOf(articulosFactura.get(i).getCodArt());
            data[i][1] = articulosFactura.get(i).getNombreArt();
            data[i][2] = String.valueOf(articulosFactura.get(i).getCantidad());
            data[i][3] = String.valueOf(articulosFactura.get(i).getPrecio());
        }*/
        tablemodel.setDataVector(data, columNames);

        return tablemodel;
    }

    //metodo para borrar los productos que hay en el arraylist de productos de cada factura
    public void borrarProducto_array(int posicion) {

        articulosFactura.remove(posicion);

    }

    //metodo para calcular el importe de todos los productos de una factura
    public double calcularImporte() {

        int cantidad;
        double precio;
        double total = 0;

        for (int i = 0; i < articulosFactura.size(); i++) {

            cantidad = articulosFactura.get(i).getCantidad();
            precio = articulosFactura.get(i).getPrecio();

            total = total + (cantidad * precio);

        }

        return total;
    }

    //metodo para guardar la factura completa en la bbdd
    public boolean guardarFacturaBBDD(String nif) {

        if (articulosFactura.size() != 0) {

            String q = "INSERT INTO facturas (codFact, importe, total, nif) VALUES ('"
                    + articulosFactura.get(0).getCodFact() + "','" + calcularImporte() + "','"
                    + (calcularImporte() * 1.21) + "','" + nif + "')";

            try {

                PreparedStatement pstm = this.getConnection().prepareStatement(q);
                pstm.execute();
                pstm.close();

                for (int i = 0; i < articulosFactura.size(); i++) {

                    String q2 = "INSERT INTO artfact (codArt, codFact, precio, cantidad, nombreArt) VALUES ('"
                            + articulosFactura.get(i).getCodArt() + "','" + articulosFactura.get(i).getCodFact()
                            + "','" + articulosFactura.get(i).getPrecio() + "','" + articulosFactura.get(i).getCantidad()
                            + "','" + articulosFactura.get(i).getNombreArt() + "')";

                    try {

                        PreparedStatement pstm2 = this.getConnection().prepareStatement(q2);
                        pstm2.execute();
                        pstm2.close();

                    } catch (SQLException e) {

                        JOptionPane.showMessageDialog(null, "No se ha podido Guardar la Factura");
                        return false;
                    }

                }
                System.out.println("insertado");
                JOptionPane.showMessageDialog(null, "Factura Guardada");
                return true;

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(null, "No se ha podido Guardar la Factura");
                return false;
            }

        }

        return false;

    }

    //metodo que devuelve el dni del cliente de una factura y rellena el arraylist de los articulos de esa factura
    public String buscarFactura(int codigoFactura) {

        String q = "SELECT nif FROM facturas WHERE codFact = " + codigoFactura;

        String nif = "";
        try {
            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            ResultSet res = pstm.executeQuery();

            int i = 0;
            while (res.next()) {

                nif = res.getString("nif");
                i++;
            }
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {

        }

        String q2 = "SELECT codArt, nombreArt, cantidad, precio FROM artfact WHERE codFact = " + codigoFactura;

        articulosFactura.clear();
        
        int codArt,cantidad;
        double precio;
        String nombreArt;

        try {

            PreparedStatement pstm = this.getConnection().prepareStatement(q2);
            ResultSet res2 = pstm.executeQuery();

            int i = 0;

            while (res2.next()) {
                codArt = res2.getInt("codArt");
                nombreArt = res2.getString("nombreArt");
                cantidad = res2.getInt("cantidad");
                precio = res2.getDouble("precio");
                
                articulosFactura.add(new ArtFact(codArt,codigoFactura,nombreArt,cantidad,precio));
                i++;
            }
            pstm.execute();
            pstm.close();


        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }

        return nif;
    }
    
    //metodo para borrar la factura completa de la bbdd
    public boolean borrarFactura(int codFact){
        
        String q = "DELETE FROM artfact WHERE codFact=" + codFact;
        
        String q2 = "DELETE FROM facturas WHERE codFact=" + codFact;
        
        try{
            
            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            pstm.execute();
            pstm.close();
            
            try{
                
                PreparedStatement pstm2 = this.getConnection().prepareStatement(q2);
                pstm2.execute();
                pstm2.close();
                articulosFactura.clear();
                JOptionPane.showMessageDialog(null, "Factura Eliminada");
                return true;
                
            }catch(SQLException e){
               
                JOptionPane.showMessageDialog(null, "No se ha podido eliminar la factura");
                return false;
            }
            
        }catch(SQLException e){
            
            JOptionPane.showMessageDialog(null, "No se ha podido eliminar la factura");
            return false;
        }
    }

}
