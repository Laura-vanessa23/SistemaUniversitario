/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.vista;

import com.miapp.controlador.EstudianteController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EstudianteView extends JFrame {

    // ── Constantes finales para dimensiones (Ventana más amplia y cómoda) ───
    private static final int ANCHO_VENTANA = 1100;
    private static final int ALTO_VENTANA = 800;
    private static final int ANCHO_CAMPO_BUSQUEDA = 15;
    private static final int ANCHO_CAMPO_AGREGAR = 10;
    private static final int ALTO_FILA_TABLA = 26;

    // ── Constantes finales para textos ─────────────────────────────────────────
    private static final String TITULO_VENTANA = "Gestión de Estudiantes — MVC (Optimizado y Organizado)";
    private static final String TITULO_PANEL_BUSQUEDA = "Búsqueda y Filtros de Estudiantes";
    private static final String TITULO_PANEL_AGREGAR = "Registro de Nuevo Estudiante";
    private static final String TITULO_PANEL_RESULTADOS = "Resultados de la Búsqueda / Estudiantes";
    private static final String LABEL_NOMBRE = "Nombre:";
    private static final String LABEL_APELLIDO = "Apellido:";
    private static final String LABEL_CARRERA = "Carrera:";
    private static final String LABEL_PROMEDIO = "Promedio:";
    private static final String LABEL_ESTADO = "Estado:";
    private static final String BOTON_BUSCAR = "Buscar Nombre";
    private static final String BOTON_BUSCAR_CARRERA = "Buscar Carrera";
    private static final String BOTON_LIMPIAR = "Limpiar Todo";
    private static final String BOTON_AGREGAR = "Agregar Estudiante";
    private static final String OPCION_SELECCIONAR = "Seleccionar...";
    private static final String MENSAJE_INICIAL = "Listo. Utilice los filtros superiores para buscar estudiantes.";
    private static final String MENSAJE_ENCONTRADO_UNO = "Se encontró 1 estudiante.";
    private static final String MENSAJE_ENCONTRADOS_VARIOS = "Se encontraron {0} estudiante(s).";
    private static final String MENSAJE_SIN_RESULTADOS = "No se encontraron estudiantes con ese criterio.";

    // ── Constantes de colores profesionales ────────────────────────────────────
    private static final Color COLOR_BOTON_AZUL = new Color(41, 128, 185);
    private static final Color COLOR_BOTON_VERDE = new Color(39, 174, 96);
    private static final Color COLOR_BOTON_NARANJA = new Color(230, 126, 34);
    private static final Color COLOR_BOTON_ROJO = new Color(192, 57, 43);
    private static final Color COLOR_BOTON_MORADO = new Color(142, 68, 173);
    private static final Color COLOR_TEXTO_BLANCO = Color.WHITE;

    // ── Columnas de la tabla ───────────────────────────────────────────────────
    private static final String[] COLUMNAS_TABLA = {"ID", "Nombre", "Apellido", "Carrera", "Promedio", "Estado"};

    // ── Componentes UI ────────────────────────────────────────────────────────
    private JTextField             txtNombre;
    private JButton                btnBuscar;

    private JComboBox<String>      cmbCarrera;
    private JButton                btnBuscarCarrera;
    private JButton                btnLimpiar;

    private JTextField             txtAgregarNombre;
    private JTextField             txtAgregarApellido;
    private JComboBox<String>      cmbAgregarCarrera;
    private JSpinner               spinPromedio;
    private JComboBox<String>      cmbAgregarEstado;
    private JButton                btnAgregar;

    private JTable                 tblResultados;
    private DefaultTableModel      modeloTabla;
    private JLabel                 lblEstado;
    private JLabel                 lblTotalEstudiantes;
    
    // Cursos
    private JComboBox<String>      cmbCursos;
    private JButton                btnVerEstudiantesCurso;
    private JButton                btnInscribirCurso;
    private JLabel                 lblProfesorAsignado;
    
    // Profesores
    private JTextField             txtProfesorNombre;
    private JTextField             txtProfesorSalario;
    private JButton                btnAgregarProfesor;
    private JComboBox<String>      cmbProfesorAsignar;
    private JButton                btnVerCursosProfesor;
    private JComboBox<String>      cmbCursoAsignar;
    private JButton                btnAsignarCursoProfesor;
    
    // Estados de Matrícula
    private JComboBox<String>      cmbEstadoMatricula;
    private JButton                btnBuscarEstado;
    private JButton                btnCambiarEstado;

    // Controlador
    private EstudianteController controlador;

    // ── Constructor ───────────────────────────────────────────────────────────
    public EstudianteView() {
        initComponentes();
        initEventos();
    }

    // ── Inicialización de componentes ordenados ───────────────────────────────
    private void initComponentes() {
        setTitle(TITULO_VENTANA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setLocationRelativeTo(null);
        
        // Usamos un diseño general limpio
        setLayout(new BorderLayout(8, 8));

        // ────────────────────────────────────────────────────────────────────────
        // PANEL SUPERIOR: Organizado por pestañas (JTabbedPane) para que no se amontone
        // ────────────────────────────────────────────────────────────────────────
        JTabbedPane pestañasSuperiores = new JTabbedPane();

        // ── PESTAÑA 1: Estudiantes (Búsqueda, Filtros y Agregar) ───────────────
        JPanel panelTabEstudiantes = new JPanel();
        panelTabEstudiantes.setLayout(new BoxLayout(panelTabEstudiantes, BoxLayout.Y_AXIS));
        panelTabEstudiantes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Sub-panel: Búsqueda por Nombre y por Carrera
        JPanel panelFiltrosNomCarrera = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelFiltrosNomCarrera.setBorder(BorderFactory.createTitledBorder("Filtrar Estudiantes"));
        
        txtNombre = new JTextField(ANCHO_CAMPO_BUSQUEDA);
        btnBuscar = crearBoton(BOTON_BUSCAR, COLOR_BOTON_AZUL);
        
        cmbCarrera = new JComboBox<>();
        cmbCarrera.addItem(OPCION_SELECCIONAR);
        btnBuscarCarrera = crearBoton(BOTON_BUSCAR_CARRERA, COLOR_BOTON_VERDE);
        btnLimpiar = crearBoton(BOTON_LIMPIAR, COLOR_BOTON_ROJO);

        panelFiltrosNomCarrera.add(new JLabel(LABEL_NOMBRE));
        panelFiltrosNomCarrera.add(txtNombre);
        panelFiltrosNomCarrera.add(btnBuscar);
        panelFiltrosNomCarrera.add(Box.createHorizontalStrut(15));
        panelFiltrosNomCarrera.add(new JLabel(LABEL_CARRERA));
        panelFiltrosNomCarrera.add(cmbCarrera);
        panelFiltrosNomCarrera.add(btnBuscarCarrera);
        panelFiltrosNomCarrera.add(btnLimpiar);

        // Sub-panel: Filtrar y Cambiar Estado de Matrícula
        JPanel panelFiltrosEstado = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelFiltrosEstado.setBorder(BorderFactory.createTitledBorder("Filtrar o Cambiar por Estado de Matrícula"));
        
        cmbEstadoMatricula = new JComboBox<>();
        cmbEstadoMatricula.addItem(OPCION_SELECCIONAR);
        cmbEstadoMatricula.addItem("Activo");
        cmbEstadoMatricula.addItem("Inactivo");
        
        btnBuscarEstado = crearBoton("Buscar por Estado", COLOR_BOTON_VERDE);
        btnCambiarEstado = crearBoton("Cambiar Estado a Seleccionado", COLOR_BOTON_NARANJA);

        panelFiltrosEstado.add(new JLabel("Estado:"));
        panelFiltrosEstado.add(cmbEstadoMatricula);
        panelFiltrosEstado.add(btnBuscarEstado);
        panelFiltrosEstado.add(Box.createHorizontalStrut(20));
        panelFiltrosEstado.add(btnCambiarEstado);

        // Sub-panel: Agregar Nuevo Estudiante
        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelAgregar.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_AGREGAR));

        txtAgregarNombre = new JTextField(ANCHO_CAMPO_AGREGAR);
        txtAgregarApellido = new JTextField(ANCHO_CAMPO_AGREGAR);
        cmbAgregarCarrera = new JComboBox<>();
        cmbAgregarCarrera.addItem(OPCION_SELECCIONAR);
        spinPromedio = new JSpinner(new SpinnerNumberModel(3.0, 0.0, 5.0, 0.1));
        spinPromedio.setPreferredSize(new Dimension(55, 25));
        
        cmbAgregarEstado = new JComboBox<>();
        cmbAgregarEstado.addItem("Activo");
        cmbAgregarEstado.addItem("Inactivo");
        
        btnAgregar = crearBoton(BOTON_AGREGAR, COLOR_BOTON_MORADO);

        panelAgregar.add(new JLabel(LABEL_NOMBRE));
        panelAgregar.add(txtAgregarNombre);
        panelAgregar.add(new JLabel(LABEL_APELLIDO));
        panelAgregar.add(txtAgregarApellido);
        panelAgregar.add(new JLabel(LABEL_CARRERA));
        panelAgregar.add(cmbAgregarCarrera);
        panelAgregar.add(new JLabel(LABEL_PROMEDIO));
        panelAgregar.add(spinPromedio);
        panelAgregar.add(new JLabel(LABEL_ESTADO));
        panelAgregar.add(cmbAgregarEstado);
        panelAgregar.add(Box.createHorizontalStrut(10));
        panelAgregar.add(btnAgregar);

        panelTabEstudiantes.add(panelFiltrosNomCarrera);
        panelTabEstudiantes.add(Box.createVerticalStrut(5));
        panelTabEstudiantes.add(panelFiltrosEstado);
        panelTabEstudiantes.add(Box.createVerticalStrut(5));
        panelTabEstudiantes.add(panelAgregar);

        pestañasSuperiores.addTab("Gestión de Estudiantes", panelTabEstudiantes);

        // ── PESTAÑA 2: Cursos y Profesores ─────────────────────────────────────
        JPanel panelTabAcademico = new JPanel();
        panelTabAcademico.setLayout(new BoxLayout(panelTabAcademico, BoxLayout.Y_AXIS));
        panelTabAcademico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Sub-panel: Cursos
        JPanel panelCursos = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelCursos.setBorder(BorderFactory.createTitledBorder("Gestión de Cursos e Inscripciones"));

        cmbCursos = new JComboBox<>();
        cmbCursos.addItem("Cosmetología y Uñas");        
        cmbCursos.addItem("Sistemas e Informática");    
        cmbCursos.addItem("Desarrollo de Software");   
        cmbCursos.addItem("Estilismo y Pelo");        

        btnVerEstudiantesCurso = crearBoton("Ver estudiantes del curso", COLOR_BOTON_AZUL);
        btnInscribirCurso = crearBoton("Inscribir estudiante seleccionado", COLOR_BOTON_NARANJA);
        lblProfesorAsignado = new JLabel("(Seleccione un estudiante en la tabla inferior)");

        panelCursos.add(new JLabel("Curso:"));
        panelCursos.add(cmbCursos);
        panelCursos.add(btnVerEstudiantesCurso);
        panelCursos.add(btnInscribirCurso);

        // Sub-panel: Profesores
        JPanel panelProfesores = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelProfesores.setBorder(BorderFactory.createTitledBorder("Gestión de Profesores y Asignación"));

        txtProfesorNombre = new JTextField(10);
        txtProfesorSalario = new JTextField("3.000.000", 8);
        btnAgregarProfesor = crearBoton("Registrar Profesor", COLOR_BOTON_MORADO);

        cmbProfesorAsignar = new JComboBox<>();
        btnVerCursosProfesor = crearBoton("Ver cursos", COLOR_BOTON_AZUL);
        
        cmbCursoAsignar = new JComboBox<>();
        cmbCursoAsignar.addItem("Cosmetología y Uñas");
        cmbCursoAsignar.addItem("Sistemas e Informática");
        cmbCursoAsignar.addItem("Desarrollo de Software");
        cmbCursoAsignar.addItem("Estilismo y Pelo");

        btnAsignarCursoProfesor = crearBoton("Asignar a curso", COLOR_BOTON_VERDE);

        panelProfesores.add(new JLabel("Nombre:"));
        panelProfesores.add(txtProfesorNombre);
        panelProfesores.add(new JLabel("Salario:"));
        panelProfesores.add(txtProfesorSalario);
        panelProfesores.add(btnAgregarProfesor);
        panelProfesores.add(Box.createHorizontalStrut(15));
        panelProfesores.add(new JLabel("Profesor:"));
        panelProfesores.add(cmbProfesorAsignar);
        panelProfesores.add(btnVerCursosProfesor);
        panelProfesores.add(new JLabel("Asignar:"));
        panelProfesores.add(cmbCursoAsignar);
        panelProfesores.add(btnAsignarCursoProfesor);

        panelTabAcademico.add(panelCursos);
        panelTabAcademico.add(Box.createVerticalStrut(10));
        panelTabAcademico.add(panelProfesores);

        pestañasSuperiores.addTab("Cursos y Profesores", panelTabAcademico);

        // ────────────────────────────────────────────────────────────────────────
        // PANEL CENTRAL: Tabla de resultados con Scroll
        // ────────────────────────────────────────────────────────────────────────
        modeloTabla = new DefaultTableModel(COLUMNAS_TABLA, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblResultados = new JTable(modeloTabla);
        tblResultados.setRowHeight(ALTO_FILA_TABLA);
        tblResultados.getTableHeader().setReorderingAllowed(false);
        tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollTabla = new JScrollPane(tblResultados);
        scrollTabla.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_RESULTADOS));

        // ────────────────────────────────────────────────────────────────────────
        // PANEL INFERIOR: Barra de estado y contadores
        // ────────────────────────────────────────────────────────────────────────
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));

        lblEstado = new JLabel(MENSAJE_INICIAL);
        lblEstado.setForeground(Color.DARK_GRAY);

        lblTotalEstudiantes = new JLabel();
        lblTotalEstudiantes.setForeground(new Color(41, 128, 185));
        lblTotalEstudiantes.setFont(lblTotalEstudiantes.getFont().deriveFont(Font.BOLD));
        actualizarTotalEstudiantes();

        panelInferior.add(lblEstado, BorderLayout.WEST);
        panelInferior.add(lblTotalEstudiantes, BorderLayout.EAST);

        // Añadir los componentes principales a la ventana principal
        add(pestañasSuperiores, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    // ── Método auxiliar para crear botones uniformes y estilizados ────────────
    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(COLOR_TEXTO_BLANCO);
        boton.setFocusPainted(false);
        boton.setFont(boton.getFont().deriveFont(Font.BOLD, 12f));
        return boton;
    }

    private void cargarCarreras() {
        if (controlador != null) {
            String[] carreras = controlador.obtenerCarrerasUnicas();
            for (String carrera : carreras) {
                cmbCarrera.addItem(carrera);
            }
        }
    }

    private void cargarCarrerasAgregar() {
        if (controlador != null) {
            String[] carreras = controlador.obtenerCarrerasUnicas();
            for (String carrera : carreras) {
                cmbAgregarCarrera.addItem(carrera);
            }
        }
    }

    private void actualizarComboProfesores() {
        if (controlador != null) {
            List<String> nombresProfesores = controlador.obtenerNombresProfesores();
            cmbProfesorAsignar.removeAllItems();
            for (String nombre : nombresProfesores) {
                cmbProfesorAsignar.addItem(nombre);
            }
        }
    }

    // ── Configuración de Eventos ──────────────────────────────────────────────
    private void initEventos() {
        // 1. Buscar por nombre
        btnBuscar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                controlador.buscarEstudiante(txtNombre.getText().trim());
            }
        });
        txtNombre.addActionListener((ActionEvent e) -> btnBuscar.doClick());

        // 2. Buscar por carrera
        btnBuscarCarrera.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String carreraSeleccionada = (String) cmbCarrera.getSelectedItem();
                if (carreraSeleccionada != null && !carreraSeleccionada.equals(OPCION_SELECCIONAR)) {
                    controlador.buscarEstudiantePorCarrera(carreraSeleccionada);
                } else {
                    mostrarError("Por favor seleccione una carrera válida.");
                }
            }
        });

        // 3. Limpiar filtros y tabla
        btnLimpiar.addActionListener((ActionEvent e) -> limpiarBusqueda());

        // 4. NUEVO: Buscar por estado de matrícula
        btnBuscarEstado.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String estadoSeleccionado = (String) cmbEstadoMatricula.getSelectedItem();
                if (estadoSeleccionado != null && !estadoSeleccionado.equals(OPCION_SELECCIONAR)) {
                    controlador.buscarEstudiantePorEstado(estadoSeleccionado);
                } else {
                    mostrarError("Seleccione un estado de matrícula (Activo o Inactivo).");
                }
            }
        });

        // 5. NUEVO: Cambiar estado de matrícula del estudiante seleccionado en la tabla
        btnCambiarEstado.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                int filaSeleccionada = tblResultados.getSelectedRow();
                if (filaSeleccionada == -1) {
                    mostrarError("Por favor, seleccione un estudiante en la tabla para cambiar su estado.");
                    return;
                }
                int idEstudiante = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                String nuevoEstado = (String) cmbEstadoMatricula.getSelectedItem();
                
                if (nuevoEstado == null || nuevoEstado.equals(OPCION_SELECCIONAR)) {
                    mostrarError("Seleccione un estado válido en el menú desplegable superior.");
                    return;
                }

                controlador.actualizarEstadoEstudiante(idEstudiante, nuevoEstado);
            }
        });

        // 6. Agregar nuevo estudiante
        btnAgregar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String nombre = txtAgregarNombre.getText().trim();
                String apellido = txtAgregarApellido.getText().trim();
                String carrera = (String) cmbAgregarCarrera.getSelectedItem();
                double promedio = (double) spinPromedio.getValue();
                String estadoMatricula = (String) cmbAgregarEstado.getSelectedItem();

                if (controlador.agregarEstudiante(nombre, apellido, carrera, promedio, estadoMatricula)) {
                    txtAgregarNombre.setText("");
                    txtAgregarApellido.setText("");
                    cmbAgregarCarrera.setSelectedIndex(0);
                    spinPromedio.setValue(3.0);
                    cmbAgregarEstado.setSelectedIndex(0);
                    actualizarTotalEstudiantes();
                }
            }
        });
        
        // 7. Inscribir estudiante seleccionado a un curso
        btnInscribirCurso.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                int filaSeleccionada = tblResultados.getSelectedRow();
                if (filaSeleccionada == -1) {
                    mostrarError("Primero busque y seleccione un estudiante en la tabla.");
                    return;
                }
                int idEstudiante = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                String cursoSeleccionado = (String) cmbCursos.getSelectedItem();
                controlador.inscribirEstudianteACurso(idEstudiante, cursoSeleccionado);
            }
        });

        // Ver estudiantes del curso (Conexión faltante agregada aquí)
        btnVerEstudiantesCurso.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String cursoSeleccionado = (String) cmbCursos.getSelectedItem();
                controlador.verEstudiantesDelCurso(cursoSeleccionado);
            }
        });

        // 8. Agregar profesor
        btnAgregarProfesor.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String nombreProfesor = txtProfesorNombre.getText().trim();
                String salarioStr = txtProfesorSalario.getText().trim().replace(".", "").replace(",", "");
                try {
                    double salario = Double.parseDouble(salarioStr);
                    if (controlador.agregarProfesor(nombreProfesor, salario)) {
                        txtProfesorNombre.setText("");
                        txtProfesorSalario.setText("3.000.000");
                        actualizarComboProfesores();
                    }
                } catch (NumberFormatException ex) {
                    mostrarError("Por favor ingrese un salario válido en números.");
                }
            }
        });

        // 9. Asignar profesor a curso
        btnAsignarCursoProfesor.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String profesorSeleccionado = (String) cmbProfesorAsignar.getSelectedItem();
                String cursoSeleccionado = (String) cmbCursoAsignar.getSelectedItem();
                
                if (profesorSeleccionado == null || profesorSeleccionado.isEmpty()) {
                    mostrarError("No hay ningún profesor seleccionado.");
                    return;
                }
                controlador.asignarProfesorACurso(profesorSeleccionado, cursoSeleccionado);
            }
        });

        // 10. Ver cursos del profesor
        btnVerCursosProfesor.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String profesorSeleccionado = (String) cmbProfesorAsignar.getSelectedItem();
                if (profesorSeleccionado == null || profesorSeleccionado.isEmpty()) {
                    mostrarError("Seleccione un profesor para ver sus cursos.");
                    return;
                }
                controlador.verCursosDelProfesor(profesorSeleccionado);
            }
        });
    }

    public void mostrarEstudiante(Object[] fila) {
        limpiarTabla();
        modeloTabla.addRow(fila);
        setEstado(MENSAJE_ENCONTRADO_UNO);
    }

    public void mostrarEstudiantes(List<Object[]> filas) {
        limpiarTabla();
        if (filas == null || filas.isEmpty()) {
            setEstado(MENSAJE_SIN_RESULTADOS);
            return;
        }
        for (Object[] fila : filas) {
            modeloTabla.addRow(fila);
        }
        setEstado(String.format(MENSAJE_ENCONTRADOS_VARIOS, filas.size()));
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        setEstado("Error: " + mensaje);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
        setEstado(mensaje);
    }

    public String getNombreBuscado() {
        return txtNombre.getText().trim();
    }
   
    public void setControlador(EstudianteController controlador) {
        this.controlador = controlador;
        cargarCarreras();
        cargarCarrerasAgregar();
        actualizarComboProfesores();
        actualizarTotalEstudiantes();
    }

    private void actualizarTotalEstudiantes() {
        int total = (controlador != null) ? controlador.obtenerTotalEstudiantes() : 0;
        lblTotalEstudiantes.setText("Total de estudiantes registrados: " + total);
    }

    private void limpiarBusqueda() {
        txtNombre.setText("");
        cmbCarrera.setSelectedIndex(0);
        cmbEstadoMatricula.setSelectedIndex(0);
        limpiarTabla();
        setEstado(MENSAJE_INICIAL);
    }

    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    private void setEstado(String texto) {
        lblEstado.setText(texto);
    }
}