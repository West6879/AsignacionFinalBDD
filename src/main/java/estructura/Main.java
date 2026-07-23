package estructura;

import database.*;

import java.util.*;

public class Main {

    private static final Main servicio = new Main();
    private final Map<String, Estudiante> estudiantes;
    private final Map<String, Asignatura> asignaturas;
    private final Map<String, Grupo> grupos;
    private final HashSet<Inscripcion> inscripciones;
    private final Map<String, PeriodoAcademico> periodos;

    static void main() {

        Main servicio = Main.getInstance();

        System.out.println("Estudiantes cargados:");
        for (Estudiante e : servicio.getEstudiantes().values()) {
            System.out.println(e);
        }
        System.out.println();

        System.out.println("Rutas cargadas:");
        for (Asignatura a : servicio.getAsignaturas().values()) {
            System.out.println(a);
        }
    }

    private Main() {
        this.estudiantes = new HashMap<>();
        this.asignaturas = new HashMap<>();
        this.grupos = new HashMap<>();
        this.inscripciones = new HashSet<>();
        this.periodos = new HashMap<>();

        cargarDatos();
    }


    private void cargarDatos() {
        IO.println("Cargando estudiantes!!");
        EstudianteDAO estudianteDAO = EstudianteDAO.getInstance();
        IO.println("Estudiantes cargados!!");

        IO.println("Cargando asginaturas!!");
        AsignaturaDAO asignaturaDAO = AsignaturaDAO.getInstance();
        IO.println("Asignaturas cargadas!!");

        IO.println("Cargando grupos!!");
        GrupoDAO grupoDAO = GrupoDAO.getInstance();
        IO.println("Grupos cargados!!");

        IO.println("Cargando inscripciones!!");
        InscripcionDAO inscripcionDAO = InscripcionDAO.getInstance();
        IO.println("inscripciones cargadas!!");

        IO.println("Cargando Periodos!!");
        PeriodoAcademicoDAO PeriodoAcademicoDAO1 = PeriodoAcademicoDAO.getInstance();
        IO.println("Periodos cargados!!");

        this.estudiantes.putAll(estudianteDAO.findAll());
        this.asignaturas.putAll(asignaturaDAO.findAll());
        this.grupos.putAll(grupoDAO.findAll());
        this.inscripciones.addAll(inscripcionDAO.findAll());
        this.periodos.putAll(PeriodoAcademicoDAO1.findAll());
    }

    public void guardarEstudiante(Estudiante estudiante) {
        this.estudiantes.put(estudiante.getId(), estudiante);
        EstudianteDAO.getInstance().save(estudiante);
    }

    public void actualizarEstudiante(Estudiante estudiante) {
        this.estudiantes.put(estudiante.getId(), estudiante);
        EstudianteDAO.getInstance().update(estudiante);
    }

    public void eliminarEstudiante(String id) {
        this.estudiantes.remove(id);
        EstudianteDAO.getInstance().delete(id);
    }

    public boolean existeEstudiante(String id) {
        return this.estudiantes.containsKey(id);
    }

    public void guardarAsignatura(Asignatura asignatura) {
        this.asignaturas.put(asignatura.getCodAsignatura(), asignatura);
        AsignaturaDAO.getInstance().save(asignatura);
    }

    public void actualizarAsignatura(Asignatura asignatura) {
        this.asignaturas.put(asignatura.getCodAsignatura(), asignatura);
        AsignaturaDAO.getInstance().update(asignatura);
    }

    public void eliminarAsignatura(String id) {
        this.asignaturas.remove(id);
        AsignaturaDAO.getInstance().delete(id);
    }

    public void guardarInscripcion(Inscripcion inscripcion){
        String claveGrupo = inscripcion.getCodPeriodoAcademico() + "|" + inscripcion.getCodAsignatura() + "|" + inscripcion.getNumGrupo();
        Grupo objetivo = grupos.get(claveGrupo);
        this.grupos.put(objetivo.getClaveGrupo(), objetivo);
        GrupoDAO.getInstance().actualizarGrupo(objetivo);
        this.inscripciones.add(inscripcion);
        InscripcionDAO.getInstance().save(inscripcion);
    }

    public void eliminarInscripcion(Inscripcion inscripcion){
        String claveGrupo = inscripcion.getCodPeriodoAcademico() + "|" + inscripcion.getCodAsignatura() + "|" + inscripcion.getNumGrupo();
        Grupo objetivo = grupos.get(claveGrupo);
        GrupoDAO.getInstance().actualizarGrupo(objetivo);
        this.grupos.put(objetivo.getClaveGrupo(), objetivo);
        this.inscripciones.remove(inscripcion);
        InscripcionDAO.getInstance().delete(inscripcion.getCodPeriodoAcademico(), inscripcion.getId(), inscripcion.getCodAsignatura(), inscripcion.getNumGrupo());
    }

    public void guardarGrupo(Grupo grupo) {
        this.grupos.put(grupo.getClaveGrupo(), grupo);
        GrupoDAO.getInstance().save(grupo);
    }

    public boolean existeGrupo(String claveGrupo) {
        return this.grupos.containsKey(claveGrupo);
    }

    public void guardarPeriodo(PeriodoAcademico periodo) {
        this.periodos.put(periodo.getCodPeriodoAcademico(), periodo);
        database.PeriodoAcademicoDAO.getInstance().save(periodo);
    }

    public Map<String, PeriodoAcademico> getPeriodos() {
        return periodos;
    }

    public boolean existePeriodo(String codigo) {
        return this.periodos.containsKey(codigo);
    }

    public boolean existeAsignatura(String id) {
        return this.asignaturas.containsKey(id);
    }

    public void actualizarGrupo(Grupo grupo) {
        this.grupos.put(grupo.getClaveGrupo(), grupo);
    }

    public static Main getInstance() { return servicio; }

    public Map<String, Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public Map<String, Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public Map<String, Grupo> getGrupos() { return grupos; }

    public HashSet<Inscripcion> getInscripciones() {
        return inscripciones;
    }
}
