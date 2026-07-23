package estructura;

public class Inscripcion {
    private String codPeriodoAcademico;
    private String idEstudiante;
    private String codAsignatura;
    private String numGrupo;

    public Inscripcion(String codPeriodoAcademico, String idEstudiante, String codAsignatura, String numGrupo) {
        this.codPeriodoAcademico = codPeriodoAcademico;
        this.idEstudiante = idEstudiante;
        this.codAsignatura = codAsignatura;
        this.numGrupo = numGrupo;
    }

    public String getCodPeriodoAcademico() {
        return codPeriodoAcademico;
    }

    public void setCodPeriodoAcademico(String codPeriodoAcademico) {
        this.codPeriodoAcademico = codPeriodoAcademico;
    }

    public String getId() {
        return idEstudiante;
    }

    public void setId(String id) {
        this.idEstudiante = id;
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
}
