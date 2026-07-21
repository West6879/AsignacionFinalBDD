package estructura;

import java.util.ArrayList;

public class Administración {

    private ArrayList<Estudiante> estudiantes;
    private ArrayList<Asignatura> asignaturas;

    public Administración() {
        this.asignaturas = asignaturas;
        this.estudiantes = estudiantes;
    }

    public void setAsignaturas(ArrayList<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(ArrayList<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas;
    }
}
