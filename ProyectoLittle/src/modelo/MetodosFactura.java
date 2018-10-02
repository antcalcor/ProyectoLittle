/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;

/**
 *
 * @author Usuario
 */
public class MetodosFactura extends Database{
    
    public void rellenarJComboBox(JComboBox jcombobox){
        
        String q = "SELECT nif FROM clientes";
        
        try{
            PreparedStatement pstm = this.getConnection().prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            int i=0;
            while (res.next()){
                jcombobox.addItem(res.getString("nif"));
                i++;
            }
            pstm.execute();
            pstm.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        
    }
    
}
