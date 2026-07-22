package estructura;

public class PeriodoAcademico {
    private char codPeriodoAcademico;
    private char descripcion;
    private char fechaInicio;
    private char fechaInicioClases;
    private char fechaFinClases;
    private char fechaLimitePrematricula;
    private char fechaLimiteRetiro;
    private char fechaLimitePublicacionCalif;

    public PeriodoAcademico(char codPeriodoAcademico, char descripcion, char fechaInicio, char fechaInicioClases, char fechaLimitePrematricula, char fechaFinClases, char fechaLimiteRetiro, char fechaLimitePublicacionCalif) {
        this.codPeriodoAcademico = codPeriodoAcademico;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaInicioClases = fechaInicioClases;
        this.fechaLimitePrematricula = fechaLimitePrematricula;
        this.fechaFinClases = fechaFinClases;
        this.fechaLimiteRetiro = fechaLimiteRetiro;
        this.fechaLimitePublicacionCalif = fechaLimitePublicacionCalif;
    }

    public char getCodPeriodoAcademico() {
        return codPeriodoAcademico;
    }

    public void setCodPeriodoAcademico(char codPeriodoAcademico) {
        this.codPeriodoAcademico = codPeriodoAcademico;
    }

    public char getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(char descripcion) {
        this.descripcion = descripcion;
    }

    public char getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(char fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public char getFechaInicioClases() {
        return fechaInicioClases;
    }

    public void setFechaInicioClases(char fechaInicioClases) {
        this.fechaInicioClases = fechaInicioClases;
    }

    public char getFechaFinClases() {
        return fechaFinClases;
    }

    public void setFechaFinClases(char fechaFinClases) {
        this.fechaFinClases = fechaFinClases;
    }

    public char getFechaLimitePrematricula() {
        return fechaLimitePrematricula;
    }

    public void setFechaLimitePrematricula(char fechaLimitePrematricula) {
        this.fechaLimitePrematricula = fechaLimitePrematricula;
    }

    public char getFechaLimiteRetiro() {
        return fechaLimiteRetiro;
    }

    public void setFechaLimiteRetiro(char fechaLimiteRetiro) {
        this.fechaLimiteRetiro = fechaLimiteRetiro;
    }

    public char getFechaLimitePublicacionCalif() {
        return fechaLimitePublicacionCalif;
    }

    public void setFechaLimitePublicacionCalif(char fechaLimitePublicacionCalif) {
        this.fechaLimitePublicacionCalif = fechaLimitePublicacionCalif;
    }
}
