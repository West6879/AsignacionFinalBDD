package estructura;

import java.sql.Time;

public class GrupoHorario {

    private String codPeriodoAcademico;
    private String codAsignatura;
    private String numGrupo;
    private int dia;
    private Time horaInicial;
    private Time horaFinal;

    public GrupoHorario(String codPeriodoAcademico, String codAsignatura, String numGrupo, int dia, Time horaInicial, Time horaFinal) {
        this.codPeriodoAcademico = codPeriodoAcademico;
        this.codAsignatura = codAsignatura;
        this.numGrupo = numGrupo;
        this.dia = dia;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
    }

    public GrupoHorario() {

    }

    public String getCodPeriodoAcademico() {
        return codPeriodoAcademico;
    }

    public void setCodPeriodoAcademico(String codPeriodoAcademico) {
        this.codPeriodoAcademico = codPeriodoAcademico;
    }

    public String getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(String codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public String getNumGrupo() {
        return numGrupo;
    }

    public void setNumGrupo(String numGrupo) {
        this.numGrupo = numGrupo;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public Time getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Time horaInicial) {
        this.horaInicial = horaInicial;
    }

    public Time getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Time horaFinal) {
        this.horaFinal = horaFinal;
    }
}
