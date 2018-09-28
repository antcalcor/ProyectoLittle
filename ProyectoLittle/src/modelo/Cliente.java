/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Usuario
 */
public class Cliente {
    
    private String NIF;
    
    private String nombre;
    
    private String direccion;
    
    public Cliente (String dni, String nom, String direc){
        
        nombre=nom;
        direccion=direc;
        NIF=dni;
        
    }
    
    public void setNombre(String nom){
        nombre=nom;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNIF(String dni){
        NIF=dni;
    }
    
    public String getNIF(){
        return NIF;
    }
    
    public void setDireccion(String direc){
        direccion=direc;
    }
    
    public String getDireccion(){
        return direccion;
    }
    
    public String toString(){
        return "\nNIF: " + NIF + "\nNombre: " + nombre + "\nDireccion: " + direccion;
    }

}
