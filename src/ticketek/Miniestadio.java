package ticketek;

public class Miniestadio extends Sede{
	private int cantidadDePuestos;
	private int[] capacidad;
	private int[] porcentajeAdicional;
	private double precioConsumision;
	private int asientoPorFila;
	
	public Miniestadio(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, int cantidadDePuestos, 
			double precioConsumicion, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		super(nombre, direccion, capacidadMaxima, sectores);
		this.asientoPorFila = asientosPorFila;
		this.cantidadDePuestos = cantidadDePuestos;
		this.precioConsumision = precioConsumicion;
		this.capacidad = capacidad;
		this.porcentajeAdicional = porcentajeAdicional;
	}
	
	public double precioDeEntrada() {
		return 1;
	}
	
	public int obtenerCantMaxPuesto() {
		return 1;
	}
	
	public int getAsientosPorFila() {
		return 1;
	}
}
