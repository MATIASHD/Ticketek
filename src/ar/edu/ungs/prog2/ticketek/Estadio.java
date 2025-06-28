package ar.edu.ungs.prog2.ticketek;

public class Estadio extends Sede {
	private String sector;
	private int capacidadOriginal;

    public Estadio(String nombre, String direccion, int capacidadMaxima) {
        super(nombre, direccion, capacidadMaxima);
        this.capacidadOriginal = capacidadMaxima;
        this.sector = "CAMPO";
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

	@Override
	public String estadosSectores() {
		return this.sector;
	}

	@Override
	public double costoDeLaEntrada(double precioBase) {
		return super.costoDeLaEntrada(precioBase);
	}

	@Override
	public int recargo(String sector) {
		return 1;
	}

	@Override
	public double calcularCostoEntrada(String espectaculo, String fecha) {
		// TODO Auto-generated method stub
		return 0;
	}
}
