package ticketek;

public abstract class Sede {
	protected String nombre;
	protected String direccion;
	protected int capacidadMaxima;
	private int sector;
	
	public Sede(String nombre, String direccion, int capacidadMaxima, int sector) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.capacidadMaxima = capacidadMaxima;
		this.sector = sector;
	}
	
	public double calcularPrecioEntrada(Funcion funcion, String sector, int fila, int asiento) {
		return 1;
	}
	
	public boolean esUbicacionValida(String sector, int fila, int asiento) {
		return true;
	}
	
	public String obtenerNombre() {
		return "";
	}
}
