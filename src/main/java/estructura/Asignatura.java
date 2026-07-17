package estructura;

import java.sql.Date;

public class Asignatura {

    private String codAsginatura;
    private String Nombre;
    private int creditos;
    private int horasTeoricas;
    private int horasPracticas;
    private String actvidad;
    private String usuario;
    private Date fechaHora;

    public Asignatura(String codAsginatura, String nombre, int creditos, int horasTeoricas, int horasPracticas, String actvidad, String usuario, Date fechaHora) {
        this.codAsginatura = codAsginatura;
        Nombre = nombre;
        this.creditos = creditos;
        this.horasTeoricas = horasTeoricas;
        this.horasPracticas = horasPracticas;
        this.actvidad = actvidad;
        this.usuario = usuario;
        this.fechaHora = fechaHora;
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

    public String getActvidad() {
        return actvidad;
    }

    public void setActvidad(String actvidad) {
        this.actvidad = actvidad;
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
