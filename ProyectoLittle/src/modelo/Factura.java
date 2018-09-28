/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Factura {
    
    private int codFact;
    
    private static int aux=1;
    
    private double importe;
    
    private double total;
    
    private Cliente cliente;
    
    private ArrayList<Integer> codArt;
    
    private ArrayList<Double> precArt;
    
    private ArrayList<Integer> cantidadArt;
    
    public Factura (Cliente cliente){
        
        codFact=aux++;
        this.cliente=cliente;
        codArt = new ArrayList<Integer>();
        precArt = new ArrayList<Double>();
        cantidadArt = new ArrayList<Integer>();
        
    }
    
    public void setImporte(double importe){
        this.importe=importe;
    }
    
    public double getImporte(){
        return importe;
    }
    
    public void setCliente(Cliente cliente){
        this.cliente=cliente;
    }
    
    public Cliente getCliente(){
        return cliente;
    }
    
    public void insertarArticulo(int codArt, double precArt,int cantidadArt){
        
        this.codArt.add(codArt);
        this.precArt.add(precArt);
        this.cantidadArt.add(cantidadArt);
        
        importe=importe + (precArt*cantidadArt);
        total=importe*1.21;
        
    }
    
}
