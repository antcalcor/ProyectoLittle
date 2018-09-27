/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectolittle;

import controlador.Controlador;
import vista.PantallaInicial;

/**
 *
 * @author Usuario
 */
public class ProyectoLittle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        new Controlador(new PantallaInicial()).Iniciar();
        
    }
    
}
