package ar.edu.ungs.prog2.ticketek;

public class Estadio extends Sede {
	private String sector; 

    public Estadio(String nombre, String direccion, int capacidadMaxima) {
        super(nombre, direccion, capacidadMaxima);
        this.sector = "CAMPO";
    }
	
    public String obtenerSector() {
		return sector;
	}

    @Override
    public boolean esNumerada() {
        return false;
    }


    @Override
    public String toString() {
        return nombre + " (Estadio, Capacidad: " + capacidadMaxima + ")";
    }
}
