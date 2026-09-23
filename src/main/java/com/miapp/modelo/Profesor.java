/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.modelo;

import java.util.List;

/**
 *
 * @author Estudiante
 */
public  class  Profesor extends Persona {
     private final  double salarioBase ;
     private List<Curso> cursosasignados;

    public Profesor() {
        super();
        this.salarioBase = 0;
    }

    public Profesor(double salarioBase, String nombre, int id) {
        super(nombre, id);
        this.salarioBase = salarioBase;
    }

    public double calcularpago(){
        return salarioBase;
    } 
    
    public void impartirClase() {
        System.out.print("El profesor " + getnombre() + "esta impartiendo su clase ");
    }

    private String getnombre() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
     
    
}
