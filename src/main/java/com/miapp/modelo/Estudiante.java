/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.modelo;

import com.miapp.servicios.Inscribible;
import java.util.List;
import java.util.ArrayList;
/**
 * Modelo: representa la entidad Estudiante.
 */
public final class Estudiante extends Persona implements Inscribible {  

    private static int totalEstudiantes = 0;
    public static final int PROMEDIO_MINIMO = 0;
    public static final int PROMEDIO_MAXIMO = 5;
    public static final String CARRERA_PREDETERMINADA = "Sin especificar";
    public static final int MAX_MATERIAS = 9 ;
    
    // ── Atributos de instancia ────────────────────────────────────────────────
    private String carrera;
    private double promedio;
    private String estadoMatricula;
    private List<Curso> cursosInscritos;
    

    // ── Constructor ───────────────────────────────────────────────────────────

    public Estudiante(int id, String nombre, String apellido, String carrera, double promedio, String estadoMatricula) {
        super(nombre, apellido, id); // Usamos 'super' para enviar nombre, apellido e id a la clase Persona
        this.carrera  = carrera;
        this.estadoMatricula = estadoMatricula;
        this.cursosInscritos = new ArrayList<>(); // Inicializamos la lista de cursos
   
        if (promedio >= PROMEDIO_MINIMO && promedio <= PROMEDIO_MAXIMO) {
            this.promedio = promedio;
        } else {
            this.promedio = 0.0;  // Por defecto si está fuera de rango
        }
        
        totalEstudiantes++;
    }

    // ── Métodos estáticos (de clase) ──────────────────────────────────────────

    public static int getTotalEstudiantes() {
        return totalEstudiantes;
    }

    public static void reiniciarContador() {
        totalEstudiantes = 0;
    }

    public static int getProximoId() {  
        return totalEstudiantes + 1;
    
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public String getCarrera() { 
        return carrera; 
    }

    public double getPromedio() { 
        return promedio; 
    }

    public String getEstadoMatricula() {
        return estadoMatricula;
    }

    public List<Curso> getCursosInscritos() {
        return cursosInscritos;
    }

    // ── Setters ──────────────────────────────────────────────────────────────

    public void setCarrera(String carrera) { 
        this.carrera = carrera; 
    }

    public void setEstadoMatricula(String estadoMatricula) {
        this.estadoMatricula = estadoMatricula;
    }

    /**
     Valida el promedio antes de asignarlo usando constantes finales
     * @param p promedio a validar (debe estar entre PROMEDIO_MINIMO y PROMEDIO_MAXIMO)
     */
    public void setPromedio(double p) {
        if (p >= PROMEDIO_MINIMO && p <= PROMEDIO_MAXIMO) {
            this.promedio = p;
        }
    }

    /**
     Método final: no puede ser sobrescrito por subclases
     */
    
    public final String toString() {
        return "ID: " + id
             + " | Nombre: " + getNombre()
             + " | Apellido: " + getApellido()    
             + " | Carrera: " + carrera
             + " | Promedio: " + String.format("%.2f", promedio)
             + " | Estado: " + estadoMatricula;
    }

    @Override
    public boolean inscribir(Curso curso) {
        if (cursosInscritos.size() < MAX_MATERIAS && !cursosInscritos.contains(curso)) {
            cursosInscritos.add(curso);
            curso.agregarEstudiante(this); // Mantiene la relación bidireccional N:M
            return true;
        }
        return false;
    }

    @Override
    public double calcularPago() {
        return 0.0;
    }
}