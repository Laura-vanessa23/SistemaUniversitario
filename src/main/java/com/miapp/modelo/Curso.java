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
public class Curso {
    private String codigo;
    private String credito;
    private List <Estudiante> estudiantes;

    public Curso() {
    }

    public Curso(String codigo, String credito, List<Estudiante> estudiantes) {
        this.codigo = codigo;
        this.credito = credito;
        this.estudiantes = estudiantes;
    }

   
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
    
    public void agregarEstudiante (Estudiante estudiantes) {
     
       }
    
}
