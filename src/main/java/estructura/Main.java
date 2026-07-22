package estructura;

import database.AsignaturaDAO;
import database.EstudianteDAO;
import database.GrupoDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final Main servicio = new Main();
    private final Map<String, Estudiante> estudiantes;
    private final Map<String, Asignatura> asignaturas;
    private final Map<String, Grupo> grupos;

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

        this.estudiantes.putAll(estudianteDAO.findAll());
        this.asignaturas.putAll(asignaturaDAO.findAll());
        this.grupos.putAll(grupoDAO.findAll());
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
}
