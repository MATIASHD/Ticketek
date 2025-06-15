package ar.edu.ungs.prog2.ticketek;

public class Estadio extends Sede {
	private String sector;
	private int capacidadOriginal;

    public Estadio(String nombre, String direccion, int capacidadMaxima) {
        super(nombre, direccion, capacidadMaxima);
        this.capacidadOriginal = capacidadMaxima;
        this.sector = "CAMPO";
    }
	
    public String obtenerSector() {
		return sector;
	}
    public int obtenerCapacidadOriginal() {
    	return capacidadOriginal;
    }

    @Override
	public int obtenerCapcidadMaxima() {
		return super.obtenerCapcidadMaxima();
	}

    @Override
    public String toString() {
        return nombre + " (Estadio, Capacidad: " + capacidadMaxima + ")";
    }

	@Override
	public String obtenerNombre() {
		return super.obtenerNombre();
	}

	@Override
	public String obtenerDireccion() {
		return super.obtenerDireccion();
	}
	
	public String entradasVendidas() {
		int vendida = super.obtenerCapcidadMaxima() - capacidadOriginal;
		int entradaVendida =  ((vendida %  super.obtenerCapcidadMaxima() + super.obtenerCapcidadMaxima()) % super.obtenerCapcidadMaxima());
		return this.sector + ": " + entradaVendida + "/" + capacidadOriginal; 
	}

    
}
