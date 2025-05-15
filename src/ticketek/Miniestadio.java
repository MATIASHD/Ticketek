package ticketek;

public class Miniestadio extends Sede{
	private int puestoComida;
	private int puestoMerchandising;
	private double valorComision;
	private int asientoPorFila;
	
	public Miniestadio(String nombre, String direccion, int capacidadMaxima, int sector, int puestoComida, int puestoMerchandising, double valorComision,int asientoPorFila) {
		super(nombre, direccion, capacidadMaxima, sector);
		this.puestoComida = puestoComida;
		this.puestoMerchandising = puestoMerchandising;
		this.valorComision = valorComision;
		this.asientoPorFila = asientoPorFila;
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
