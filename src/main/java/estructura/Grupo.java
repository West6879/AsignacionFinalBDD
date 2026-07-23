package estructura;

public class Grupo {

    private String codPeriodoAcademico;
    private String codAsignatura;
    private String numGrupo;
    private int cupoGrupo;
    private String horario;

    public Grupo(String codPeriodoAcademico, String codAsignatura, String numGrupo, int cupoGrupo, String horario) {
        this.codPeriodoAcademico = codPeriodoAcademico;
        this.codAsignatura = codAsignatura;
        this.numGrupo = numGrupo;
        this.cupoGrupo = cupoGrupo;
        this.horario = horario;
    }

    public Grupo() {

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

    public int getCupoGrupo() {
        return cupoGrupo;
    }

    public void setCupoGrupo(int cupoGrupo) {
        this.cupoGrupo = cupoGrupo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getClaveGrupo() {
        return codPeriodoAcademico + '|' + codAsignatura + '|' + numGrupo;
    }

    public boolean esGrupoValido() {
        if (codPeriodoAcademico == null || codPeriodoAcademico.trim().isEmpty()) return false;
        if (codAsignatura == null || codAsignatura.trim().isEmpty()) return false;
        if (numGrupo == null || numGrupo.trim().isEmpty()) return false;

        if (cupoGrupo <= 0) return false;

        return true;
    }
}
