package estructura;

import java.sql.Date;

public class Estudiante {

    private String id;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private String carrera;
    private String categoriaPago;
    private String nacionalidad;
    private String direccion;
    private Date fechaNacimiento;

    public Estudiante(String id, String nombre1, String nombre2, String apellido1, String apellido2, String carrera,
                      String categoriaPago, String nacionalidad, String direccion, Date fechaNacimiento) {
        this.id = id;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.carrera = carrera;
        this.categoriaPago = categoriaPago;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Estudiante() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCategoriaPago() {
        return categoriaPago;
    }

    public void setCategoriaPago(String categoriaPago) {
        this.categoriaPago = categoriaPago;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
