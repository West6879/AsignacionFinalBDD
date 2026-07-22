package estructura;

public class DiaSemana {
    public DiaSemana(int dia, char nombre) {
        this.dia = dia;
        this.nombre = nombre;
    }

    private int dia;
    private char nombre;

    public char getNombre() {
        return nombre;
    }

    public void setNombre(char nombre) {
        this.nombre = nombre;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
}
