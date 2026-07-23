package estructura;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PeriodoAcademico {
    private String codPeriodoAcademico;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin; // Agregado: necesario para validar los rangos
    private Date fechaInicioClases;
    private Date fechaFinClases;
    private Date fechaLimitePrematricula;
    private Date fechaLimiteRetiro;
    private Date fechaLimitePublicacionCalif;

    public PeriodoAcademico() {}

    public PeriodoAcademico(String codPeriodoAcademico, String descripcion, Date fechaInicio, Date fechaFin,
                            Date fechaInicioClases, Date fechaFinClases, Date fechaLimitePrematricula,
                            Date fechaLimiteRetiro, Date fechaLimitePublicacionCalif) {
        this.codPeriodoAcademico = codPeriodoAcademico;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaInicioClases = fechaInicioClases;
        this.fechaFinClases = fechaFinClases;
        this.fechaLimitePrematricula = fechaLimitePrematricula;
        this.fechaLimiteRetiro = fechaLimiteRetiro;
        this.fechaLimitePublicacionCalif = fechaLimitePublicacionCalif;
    }

    // --- GETTERS Y SETTERS ---
    public String getCodPeriodoAcademico() { return codPeriodoAcademico; }
    public void setCodPeriodoAcademico(String codPeriodoAcademico) { this.codPeriodoAcademico = codPeriodoAcademico; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public Date getFechaInicioClases() { return fechaInicioClases; }
    public void setFechaInicioClases(Date fechaInicioClases) { this.fechaInicioClases = fechaInicioClases; }

    public Date getFechaFinClases() { return fechaFinClases; }
    public void setFechaFinClases(Date fechaFinClases) { this.fechaFinClases = fechaFinClases; }

    public Date getFechaLimitePrematricula() { return fechaLimitePrematricula; }
    public void setFechaLimitePrematricula(Date fechaLimitePrematricula) { this.fechaLimitePrematricula = fechaLimitePrematricula; }

    public Date getFechaLimiteRetiro() { return fechaLimiteRetiro; }
    public void setFechaLimiteRetiro(Date fechaLimiteRetiro) { this.fechaLimiteRetiro = fechaLimiteRetiro; }

    public Date getFechaLimitePublicacionCalif() { return fechaLimitePublicacionCalif; }
    public void setFechaLimitePublicacionCalif(Date fechaLimitePublicacionCalif) { this.fechaLimitePublicacionCalif = fechaLimitePublicacionCalif; }

    // --- VALIDACIONES ---

    // Validar formato del código (Ej: 202520261)
    public boolean esCodigoValido() {
        if (codPeriodoAcademico == null || !codPeriodoAcademico.matches("^[0-9]{8}[1-3]$")) {
            return false;
        }

        int anioInicio = Integer.parseInt(codPeriodoAcademico.substring(0, 4));
        int anioFin = Integer.parseInt(codPeriodoAcademico.substring(4, 8));

        return anioInicio == (anioFin - 1);
    }

    // Validar que las clases duren al menos 70 días
    public boolean esDuracionClasesValida() {
        if (fechaInicioClases == null || fechaFinClases == null) return false;

        // Convertimos java.sql.Date a LocalDate solo para calcular la diferencia de días
        long dias = ChronoUnit.DAYS.between(fechaInicioClases.toLocalDate(), fechaFinClases.toLocalDate());
        return dias >= 70;
    }

    // Validar que las fechas estén dentro del rango del período
    public boolean sonFechasEnRango() {
        if (fechaInicio == null || fechaFin == null) return false;

        return estaEnRango(fechaInicioClases) &&
                estaEnRango(fechaFinClases) &&
                estaEnRango(fechaLimitePrematricula) &&
                estaEnRango(fechaLimiteRetiro) &&
                estaEnRango(fechaLimitePublicacionCalif);
    }

    private boolean estaEnRango(Date fechaSql) {
        if (fechaSql == null) return true;

        LocalDate fecha = fechaSql.toLocalDate();
        LocalDate inicio = fechaInicio.toLocalDate();
        LocalDate fin = fechaFin.toLocalDate();

        return !fecha.isBefore(inicio) && !fecha.isAfter(fin);
    }
}