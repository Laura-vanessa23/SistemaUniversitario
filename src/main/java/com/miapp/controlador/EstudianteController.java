/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.controlador;

import com.miapp.modelo.Curso;
import com.miapp.modelo.Estudiante;
import com.miapp.modelo.Profesor;
import com.miapp.servicios.IBuscador;
import com.miapp.vista.EstudianteView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EstudianteController implements IBuscador {

    // ── Constantes finales ────────────────────────────────────────────────────
    private static final int CANTIDAD_ESTUDIANTES_INICIALES = 12;
    private static final String MENSAJE_BUSQUEDA_VACIA = "Por favor ingrese un nombre para buscar.";
    private static final String MENSAJE_BUSQUEDA_CARRERA_VACIA = "Por favor seleccione una carrera para buscar.";
    private static final String MENSAJE_SIN_RESULTADOS = "No se encontraron estudiantes con ese criterio.";

    // ── Vista ─────────────────────────────────────────────────────────────────
    private EstudianteView vista;

    // ── Array de estudiantes (fuente de datos) ────────────────────────────────
    private Estudiante[] estudiantes;
    // ── Lista de Cursos (para la asociación N:M) ─────────────────────────────
    private List<Curso> cursosDisponibles;

    // ── Lista de Profesores y asignaciones ────────────────────────────────────
    private List<Profesor> profesoresDisponibles;
    private Map<String, Profesor> cursosConProfesor;

    // ── Constructor ───────────────────────────────────────────────────────────

    public EstudianteController(EstudianteView vista) {
        this.vista = vista;
        cargarDatos();
        this.vista.setControlador(this);
    }

    // ── Implementación de la interfaz IBuscador ───────────────────────────────

    @Override
    public void cargarDatos() {
        inicializarEstudiantes();
    }

    @Override
    public void buscarEstudiante(String criterio) {
        buscarPorCriterio(criterio);
    }

    @Override
    public void buscarEstudiantePorCarrera(String carrera) {
        buscarPorCarrera(carrera);
    }

    public void buscarEstudiantePorEstado(String estado) {
        if (estado == null || estado.isEmpty() || estado.equals("Seleccionar...")) {
            vista.mostrarError("Por favor seleccione un estado para buscar.");
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();

        for (Estudiante e : estudiantes) {
            if (e != null && e.getEstadoMatricula().equalsIgnoreCase(estado)) {
                resultados.add(e);
            }
        }

        if (resultados.isEmpty()) {
            vista.mostrarEstudiantes(new ArrayList<>());
        } else {
            vista.mostrarEstudiantes(convertirAFilas(resultados));
        }
    }

    public boolean actualizarEstadoEstudiante(int idEstudiante, String nuevoEstado) {
        if (nuevoEstado == null || nuevoEstado.trim().isEmpty() || nuevoEstado.equals("Seleccionar...")) {
            vista.mostrarError("Por favor seleccione un estado válido.");
            return false;
        }

        Estudiante estudiante = obtenerEstudiantePorId(idEstudiante);
        if (estudiante == null) {
            vista.mostrarError("Estudiante no encontrado.");
            return false;
        }

        estudiante.setEstadoMatricula(nuevoEstado);
        vista.mostrarMensaje("El estado del estudiante " + estudiante.getNombre() + " ha sido actualizado a: " + nuevoEstado);
        return true;
    }

    public void verEstudiantesDelCurso(String nombreCurso) {
        if (nombreCurso == null || nombreCurso.trim().isEmpty() || nombreCurso.equals("Seleccionar...")) {
            vista.mostrarError("Por favor seleccione un curso válido.");
            return;
        }

        List<Estudiante> estudiantesDelCurso = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            if (e != null && e.getCursosInscritos() != null) {
                for (Curso c : e.getCursosInscritos()) {
                    if (c.getCodigo().equalsIgnoreCase(nombreCurso)) {
                        estudiantesDelCurso.add(e);
                        break;
                    }
                }
            }
        }

        if (estudiantesDelCurso.isEmpty()) {
            vista.mostrarMensaje("No hay estudiantes inscritos en el curso de " + nombreCurso + ".");
            vista.mostrarEstudiantes(new ArrayList<>());
        } else {
            vista.mostrarEstudiantes(convertirAFilas(estudiantesDelCurso));
        }
    }

    // ── Carga de datos iniciales ──────────────────────────────────────────────

    private void inicializarEstudiantes() {
        estudiantes = new Estudiante[CANTIDAD_ESTUDIANTES_INICIALES];

        Estudiante.reiniciarContador();

        estudiantes[0]  = new Estudiante(1,  "Ana ","García",        "Ingeniería de Sistemas",   4.5, "Activo");
        estudiantes[1]  = new Estudiante(2,  "Carlos"," López",      "Ingeniería Civil",         3.8, "Activo");
        estudiantes[2]  = new Estudiante(3,  "María", "Rodríguez",   "Medicina",                 4.9, "Activo");
        estudiantes[3]  = new Estudiante(4,  "José ","Martínez",     "Derecho",                  3.5, "Activo");
        estudiantes[4]  = new Estudiante(5,  "Laura ","Sánchez",     "Administración",           4.1, "Activo");
        estudiantes[5]  = new Estudiante(6,  "Andrés ","Torres",     "Ingeniería de Sistemas",   3.9, "Activo");
        estudiantes[6]  = new Estudiante(7,  "Valentina ","Gómez",   "Psicología",               4.3, "Activo");
        estudiantes[7]  = new Estudiante(8,  "Luis ","Herrera",      "Economía",                 3.7, "Activo");
        estudiantes[8]  = new Estudiante(9,  "Sofía ","Díaz",        "Ingeniería Civil",         4.6, "Activo");
        estudiantes[9]  = new Estudiante(10, "Juliana ","Morales",   "Medicina",                 4.8, "Activo");
        estudiantes[10] = new Estudiante(11, "Ana Milena ","Ruiz",    "Derecho",                  4.0, "Activo");
        estudiantes[11] = new Estudiante(12, "Carlos Andrés ","Paz", "Administración",           3.6, "Activo");
        
        this.cursosDisponibles = new ArrayList<>();
        Curso curso1 = new Curso("Cosmetología y Uñas", 3);
        Curso curso2 = new Curso("Sistemas e Informática", 4);
        Curso curso3 = new Curso("Desarrollo de Software", 4);
        Curso curso4 = new Curso("Estilismo y Pelo", 3);

        cursosDisponibles.add(curso1);
        cursosDisponibles.add(curso2);
        cursosDisponibles.add(curso3);
        cursosDisponibles.add(curso4);

        this.profesoresDisponibles = new ArrayList<>();
        this.cursosConProfesor = new HashMap<>();

        Profesor prof1 = new Profesor(3500000.0, "Carlos Mendoza", "Docente Titular", 1);
        Profesor prof2 = new Profesor(4000000.0, "Sofia Gomez", "Docente Titular", 2);
        Profesor prof3 = new Profesor(3200000.0, "Miguel Ramirez", "Docente Asistente", 3);

        profesoresDisponibles.add(prof1);
        profesoresDisponibles.add(prof2);
        profesoresDisponibles.add(prof3);

        cursosConProfesor.put(curso1.getCodigo(), prof1);
        cursosConProfesor.put(curso2.getCodigo(), prof1);
        cursosConProfesor.put(curso3.getCodigo(), prof2);
        cursosConProfesor.put(curso4.getCodigo(), prof3);

        estudiantes[0].inscribir(curso1);
        estudiantes[0].inscribir(curso2);
        estudiantes[1].inscribir(curso2);
        estudiantes[2].inscribir(curso3);
        estudiantes[5].inscribir(curso2);
        estudiantes[8].inscribir(curso3);

        System.out.println("Total de estudiantes cargados: " + Estudiante.getTotalEstudiantes());
    }
    
    
    // ── Lógica de Inscripción ─────────────────────────────────────────────────
    public boolean inscribirEstudianteACurso(int idEstudiante, String nombreCurso) {
        Estudiante estudiante = obtenerEstudiantePorId(idEstudiante);
        if (estudiante == null) {
            vista.mostrarError("Estudiante no encontrado.");
            return false;
        }

        Curso cursoEncontrado = null;
        for (Curso c : cursosDisponibles) {
            if (c.getCodigo().equalsIgnoreCase(nombreCurso)) {
                cursoEncontrado = c;
                break;
            }
        }

        if (cursoEncontrado == null) {
            vista.mostrarError("El curso '" + nombreCurso + "' no existe.");
            return false;
        }

        boolean exito = estudiante.inscribir(cursoEncontrado);
        
        if (exito) {
            vista.mostrarMensaje("¡Inscripción exitosa! El estudiante " + estudiante.getNombre() +  
                                 " se inscribió en el curso " + cursoEncontrado.getCodigo());
        } else {
            vista.mostrarError("No se pudo realizar la inscripción (límite de materias alcanzado o ya inscrito).");
        }

        return exito;
    }

    // ── Lógica de búsqueda ────────────────────────────────────────────────────

  
    private void buscarPorCriterio(String criterio) {
        if (criterio == null || criterio.isEmpty()) {
            vista.mostrarError(MENSAJE_BUSQUEDA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();
        String criterioBajo = criterio.toLowerCase();

        for (Estudiante e : estudiantes) {
            if (e != null && (e.getNombre().toLowerCase().contains(criterioBajo) ||
                e.getApellido().toLowerCase().contains(criterioBajo))) {
                resultados.add(e);
            }
        }

        if (resultados.isEmpty()) {
            vista.mostrarError("Este estudiante no existe o este estudiante no está en la lista.");
            vista.mostrarEstudiantes(new ArrayList<>());
        } else if (resultados.size() == 1) {
            vista.mostrarEstudiante(convertirAFila(resultados.get(0)));
        } else {
            vista.mostrarEstudiantes(convertirAFilas(resultados));
        }
    }

   
    private void buscarPorCarrera(String carrera) {
        if (carrera == null || carrera.isEmpty() || carrera.equals("Seleccionar...")) {
            vista.mostrarError(MENSAJE_BUSQUEDA_CARRERA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();

        for (Estudiante e : estudiantes) {
            if (e != null && e.getCarrera().equalsIgnoreCase(carrera)) {
                resultados.add(e);
            }
        }

        vista.mostrarEstudiantes(convertirAFilas(resultados));
    }

    
    private Object[] convertirAFila(Estudiante e) {
        return new Object[]{
            e.getId(),
            e.getNombre(),
            e.getApellido(),
            e.getCarrera(),
            String.format("%.2f", e.getPromedio()),
            e.getEstadoMatricula()
        };
    }

  
    private List<Object[]> convertirAFilas(List<Estudiante> lista) {
        List<Object[]> filas = new ArrayList<>();
        for (Estudiante e : lista) {
            filas.add(convertirAFila(e));
        }
        return filas;
    }

    public Estudiante obtenerEstudiantePorId(int id) {
        for (Estudiante e : estudiantes) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public String[] obtenerCarrerasUnicas() {
        List<String> carreras = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            if (e != null) {
                String carrera = e.getCarrera();
                if (!carreras.contains(carrera)) {
                    carreras.add(carrera);
                }
            }
        }
        return carreras.toArray(new String[0]);
    }

 
    public final int obtenerTotalEstudiantes() {
        return Estudiante.getTotalEstudiantes();
    }

   
    public boolean agregarEstudiante(String nombre, String apellido, String carrera, double promedio, String estadoMatricula) {
        if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() ||
            carrera == null || carrera.isEmpty() || estadoMatricula == null || estadoMatricula.isEmpty()) {
            vista.mostrarError("Todos los campos son obligatorios.");
            return false;
        }

        if (estudiantes.length == Estudiante.getTotalEstudiantes()) {
            Estudiante[] nuevoArray = new Estudiante[estudiantes.length + 5];
            System.arraycopy(estudiantes, 0, nuevoArray, 0, estudiantes.length);
            estudiantes = nuevoArray;
        }

        int indiceNuevoEstudiante = Estudiante.getTotalEstudiantes();

        int proximoId = Estudiante.getProximoId();
        Estudiante nuevoEstudiante = new Estudiante(proximoId, nombre, apellido, carrera, promedio, estadoMatricula);

        estudiantes[indiceNuevoEstudiante] = nuevoEstudiante;

        vista.mostrarMensaje("Estudiante agregado correctamente.\nTotal de estudiantes: " +
                            Estudiante.getTotalEstudiantes());

        return true;
    }

    // ── Lógica de Profesores ──────────────────────────────────────────────────

    public boolean agregarProfesor(String nombre, double salario) {
        if (nombre == null || nombre.trim().isEmpty()) {
            vista.mostrarError("Por favor ingrese el nombre del profesor.");
            return false;
        }
        
        int idGenerico = profesoresDisponibles.size() + 1;
        Profesor nuevoProfesor = new Profesor(salario, nombre.trim(), "Docente", idGenerico);
        profesoresDisponibles.add(nuevoProfesor);
        
        vista.mostrarMensaje("Profesor " + nombre + " agregado con éxito.");
        return true;
    }

    public List<String> obtenerNombresProfesores() {
        List<String> nombres = new ArrayList<>();
        for (Profesor p : profesoresDisponibles) {
            nombres.add(p.getNombre());
        }
        return nombres;
    }

    public boolean asignarProfesorACurso(String nombreProfesor, String nombreCurso) {
        Profesor profesorEncontrado = null;
        for (Profesor p : profesoresDisponibles) {
            if (p.getNombre().equalsIgnoreCase(nombreProfesor)) {
                profesorEncontrado = p;
                break;
            }
        }

        if (profesorEncontrado == null) {
            vista.mostrarError("El profesor seleccionado no existe.");
            return false;
        }

        cursosConProfesor.put(nombreCurso, profesorEncontrado);
        
        vista.mostrarMensaje("¡Profesor " + profesorEncontrado.getNombre() +  
                           " asignado exitosamente al curso de " + nombreCurso + "!");
        return true;
    }
    
    public Profesor obtenerProfesorDelCurso(String nombreCurso) {
        return cursosConProfesor.get(nombreCurso);
    }

    public void verCursosDelProfesor(String nombreProfesor) {
        if (nombreProfesor == null || nombreProfesor.isEmpty()) {
            return;
        }

        List<String> cursosDelProfesor = new ArrayList<>();
        for (Map.Entry<String, Profesor> entry : cursosConProfesor.entrySet()) {
            if (entry.getValue().getNombre().equalsIgnoreCase(nombreProfesor)) {
                cursosDelProfesor.add(entry.getKey());
            }
        }

        if (cursosDelProfesor.isEmpty()) {
            vista.mostrarMensaje("El profesor " + nombreProfesor + " no tiene cursos asignados actualmente.");
        } else {
            vista.mostrarMensaje("Cursos del profesor " + nombreProfesor + ":\n- " + String.join("\n- ", cursosDelProfesor));
        }
    }
}