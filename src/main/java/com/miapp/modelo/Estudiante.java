package com.miapp.modelo;

import com.miapp.servicios.Inscribible;
import com.miapp.utilidades.EstadoMatricula;
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
    private String apellido;
    private String carrera;
    private double promedio;
    private List<Curso> cursosInscritos;
    private  EstadoMatricula estadoMatricula;
    

    // ── Constructor ───────────────────────────────────────────────────────────

   public Estudiante(int id, String nombre, String apellido, String carrera, double promedio) {
        super(nombre, id); // Usamos 'super' para enviarle nombre e id a la clase Persona
        this.apellido = apellido;
        this.carrera  = carrera;
        this.cursosInscritos = new ArrayList<>(); // Inicializamos la lista de cursos
        this.estadoMatricula  = EstadoMatricula.ACTIVO ;
   
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

    public String getApellido() {
        return apellido;
    }

    public String getCarrera() { 
        return carrera; 
    }

    public double getPromedio() { 
        return promedio; 
    }

    // ── Setters ──────────────────────────────────────────────────────────────


    public void setApellido(String apellido) { 
        this.apellido = apellido; 
    }

    public void setCarrera(String carrera) { 
        this.carrera = carrera; 
    }

    /**
     Valida el promedio antes de asignarlo usando constantes finales
     * @param p promedio a validar (debe estar entre PROMEDIO_MINIMO y PROMEDIO_MAXIMO)
     */
    public void setPromedio(double p) {
        // nuevo: Uso de constantes finales para validación
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
             + " | Apellido: " + apellido   
             + " | Carrera: " + carrera
             + " | Promedio: " + String.format("%.2f", promedio);
    }

    @Override
    public boolean inscribir(Curso curso) {
        // Validamos que no supere el máximo de materias y que no esté ya inscrito
        if (cursosInscritos.size() < MAX_MATERIAS && !cursosInscritos.contains(curso)) {
            cursosInscritos.add(curso);
            curso.agregarEstudiante(this); // Mantiene la relación bidireccional N:M
            return true;
        }
        return false;
    }

    @Override
    public double calcularPago() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    boolean contains(Estudiante estudiantes) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}