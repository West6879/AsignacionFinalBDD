package estructura;

import java.sql.Date;

public class Asignatura {

    private String codAsginatura;
    private String Nombre;
    private int creditos;
    private int horasTeoricas;
    private int horasPracticas;
    private String actividad;
    private String usuario;
    private Date fechaHora;

    public Asignatura(String codAsginatura, String nombre, int creditos, int horasTeoricas, int horasPracticas, String actividad, String usuario, Date fechaHora) {
        this.codAsginatura = codAsginatura;
        Nombre = nombre;
        this.creditos = creditos;
        this.horasTeoricas = horasTeoricas;
        this.horasPracticas = horasPracticas;
        this.actividad = actividad;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
    }

    public Asignatura() {

    }

    public String getCodAsginatura() {
        return codAsginatura;
    }

    public void setCodAsginatura(String codAsginatura) {
        this.codAsginatura = codAsginatura;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getHorasTeoricas() {
        return horasTeoricas;
    }

    public void setHorasTeoricas(int horasTeoricas) {
        this.horasTeoricas = horasTeoricas;
    }

    public int getHorasPracticas() {
        return horasPracticas;
    }

    public void setHorasPracticas(int horasPracticas) {
        this.horasPracticas = horasPracticas;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
}
