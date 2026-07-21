package estructura;

public class Grupo {
    private char pedriodoAcademico;
    private char numGrupo;
    private int cupoGrupo;
    private char horario;
    private char codAsigantura;

    public Grupo(char pedriodoAcademico, char numGrupo, int cupoGrupo, char horario, char codAsigantura) {
        this.pedriodoAcademico = pedriodoAcademico;
        this.numGrupo = numGrupo;
        this.cupoGrupo = cupoGrupo;
        this.horario = horario;
        this.codAsigantura = codAsigantura;
    }

    public char getCodAsigantura() {
        return codAsigantura;
    }

    public void setCodAsigantura(char codAsigantura) {
        this.codAsigantura = codAsigantura;
    }

    public char getPedriodoAcademico() {
        return pedriodoAcademico;
    }

    public void setPedriodoAcademico(char pedriodoAcademico) {
        this.pedriodoAcademico = pedriodoAcademico;
    }

    public char getNumGrupo() {
        return numGrupo;
    }

    public void setNumGrupo(char numGrupo) {
        this.numGrupo = numGrupo;
    }

    public int getCupoGrupo() {
        return cupoGrupo;
    }

    public void setCupoGrupo(int cupoGrupo) {
        this.cupoGrupo = cupoGrupo;
    }

    public char getHorario() {
        return horario;
    }

    public void setHorario(char horario) {
        this.horario = horario;
    }
}
