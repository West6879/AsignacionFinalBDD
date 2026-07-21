package estructura;

public class Inscripcion {
    private char codPeriodoAcademico;
    private char id;
    private char codAsignatura;
    private char numGrupo;

    public Inscripcion(char codPeriodoAcademico, char id, char codAsignatura, char numGrupo) {
        this.codPeriodoAcademico = codPeriodoAcademico;
        this.id = id;
        this.codAsignatura = codAsignatura;
        this.numGrupo = numGrupo;
    }

    public char getCodPeriodoAcademico() {
        return codPeriodoAcademico;
    }

    public void setCodPeriodoAcademico(char codPeriodoAcademico) {
        this.codPeriodoAcademico = codPeriodoAcademico;
    }

    public char getId() {
        return id;
    }

    public void setId(char id) {
        this.id = id;
    }

    public char getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(char codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public char getNumGrupo() {
        return numGrupo;
    }

    public void setNumGrupo(char numGrupo) {
        this.numGrupo = numGrupo;
    }
}
