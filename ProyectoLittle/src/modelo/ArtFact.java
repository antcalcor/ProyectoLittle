/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author ldami
 */
public class ArtFact {
    
    private int codArt;
    
    private int codFact;
    
    private String nombreArt;
    
    private int cantidad;
    
    private double precio;
    
    //creamos un constructor
    public ArtFact(int codArt, int codFact, String nombreArt, int cantidad, double precio){
        this.codArt=codArt;
        this.codFact=codFact;
        this.nombreArt=nombreArt;
        this.cantidad=cantidad;
        this.precio=precio;
    }
    
    public ArtFact(){}

    public int getCodArt() {
        return codArt;
    }

    public void setCodArt(int codArt) {
        this.codArt = codArt;
    }

    public int getCodFact() {
        return codFact;
    }

    public void setCodFact(int codFact) {
        this.codFact = codFact;
    }

    public String getNombreArt() {
        return nombreArt;
    }

    public void setNombreArt(String nombreArt) {
        this.nombreArt = nombreArt;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
    
    
    
}
