package ar.edu.ungs.prog2.ticketek;

public class Estadio extends Sede {
	private int entradasVendidas;

    public Estadio(String nombre, String direccion, int capacidadMaxima) {
        super(nombre, direccion, capacidadMaxima);
        this.entradasVendidas = 0;
    }

    public boolean asignarEntrada() {
        if (entradasVendidas < capacidadMaxima) {
            entradasVendidas++;
            return true;
        }
        return false;
    }

    public void liberarEntrada() {
        if (entradasVendidas > 0) {
            entradasVendidas--;
        }
    }

    @Override
    public boolean esNumerada() {
        return false;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    @Override
    public String toString() {
        return nombre + " (Estadio, Capacidad: " + capacidadMaxima + ")";
    }
}
