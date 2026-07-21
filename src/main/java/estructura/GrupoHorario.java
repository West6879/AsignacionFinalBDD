package estructura;

public class GrupoHorario {
    private char codPeriodoAcademico;
    private char codAsignatura;
    private int dia;
    private char horaIncial;
    private char horaFinal;
    private char numGrupo;

    public GrupoHorario(char codPeriodoAcademico, char codAsignatura, int dia, char horaIncial, char horaFinal, char numGrupo) {
        this.codPeriodoAcademico = codPeriodoAcademico;
        this.codAsignatura = codAsignatura;
        this.dia = dia;
        this.horaIncial = horaIncial;
        this.horaFinal = horaFinal;
        this.numGrupo = numGrupo;
    }

    public char getCodPeriodoAcademico() {
        return codPeriodoAcademico;
    }

    public void setCodPeriodoAcademico(char codPeriodoAcademico) {
        this.codPeriodoAcademico = codPeriodoAcademico;
    }

    public char getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(char codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public int getDia() {
        return dia;
    }

    public char getNumGrupo() {
        return numGrupo;
    }

    public void setNumGrupo(char numGrupo) {
        this.numGrupo = numGrupo;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public char getHoraIncial() {
        return horaIncial;
    }

    public void setHoraIncial(char horaIncial) {
        this.horaIncial = horaIncial;
    }

    public char getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(char horaFinal) {
        this.horaFinal = horaFinal;
    }
}
