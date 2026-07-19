package estructura;

import database.AsignaturaDAO;
import database.EstudianteDAO;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Main servicio = new Main();
    private final List<Estudiante> estudiantes;
    private final List<Asignatura> asignaturas;

    static void main() {

        Main servicio = Main.getInstance();

        System.out.println("Estudiantes cargados:");
        for (Estudiante e : servicio.getEstudiantes()) {
            System.out.println(e);
        }
        System.out.println();

        System.out.println("Rutas cargadas:");
        for (Asignatura a : servicio.getAsignaturas()) {
            System.out.println(a);
        }
    }

    private Main() {
        this.estudiantes = new ArrayList<>();
        this.asignaturas = new ArrayList<>();

        cargarDatos();
    }


    private void cargarDatos() {
        IO.println("Cargando estudiantes!!");
        EstudianteDAO estudianteDAO = EstudianteDAO.getInstance();
        IO.println("Estudiantes cargados!!");
        IO.println("Cargando asginaturas!!");
        AsignaturaDAO asignaturaDAO = AsignaturaDAO.getInstance();
        IO.println("Asignaturas cargadas!!");

        this.estudiantes.addAll(estudianteDAO.findAll());
        this.asignaturas.addAll(asignaturaDAO.findAll());
    }

    public static Main getInstance() { return servicio; }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }
}
