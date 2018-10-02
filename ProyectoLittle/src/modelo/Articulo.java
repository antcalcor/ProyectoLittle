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
public class Articulo {
    
    private int codArt;
    
    //private static int aux=1;
    
    private String nombre;
    
    private double precio;
    
    public Articulo(int codArt, String nom, double prec){
        this.codArt=codArt;
        nombre=nom;
        precio=prec;
    }

    public Articulo() {

    }
    
    public void setCodArt(int cod){
        codArt=cod;
    }
    
    public int getCodArt(){
        return codArt;
    }
    
    public void setNombre(String nom){
        nombre=nom;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setPrecio(double prec){
        precio=prec;
    }
    
    public double getPrecio(){
        return precio;
    }
    
    public String toString(){
        return "\nCodigo Articulo: " + codArt + "\nNombre: " + nombre + "\nPrecio: " + precio; 
    }
    
}
