/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Estudiante
 */
public class Profesor extends Persona {
    private final double salarioBase;
    private List<Curso> cursosAsignados;

    public Profesor() {
        super();
        this.salarioBase = 0;
        this.cursosAsignados = new ArrayList<>();
    }

    public Profesor(double salarioBase, String nombre, String apellido, int id) {
        super(nombre, apellido, id);
        this.salarioBase = salarioBase;
        this.cursosAsignados = new ArrayList<>();
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public List<Curso> getCursosAsignados() {
        return cursosAsignados;
    }

    public void setCursosAsignados(List<Curso> cursosAsignados) {
        this.cursosAsignados = cursosAsignados;
    }

    @Override
    public double calcularPago() {
        int horasSemanales = 40;
        double pagoPorHora = 50000.0;
        return this.salarioBase + (horasSemanales * pagoPorHora);
    }
    
    public void impartirClase() {
        System.out.print("El profesor " + getNombre() + " esta impartiendo su clase ");
    }
}