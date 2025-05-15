package ticketek;

public class Espectaculo {
	private Sede sede;
	private String fecha;
	private double precioBase;
	private String codigo;
	private String nombre;
	
	public Espectaculo(Sede sede, String fecha, double precioBase, String codigo, String nombre) {
		this.sede = sede;
		this.fecha = fecha;
		this.precioBase = precioBase;
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public void crearFuncion(Sede sede, String fecha, double precio, String nombre) {
		
	}
	
	public Sede consultarSede() {
		return sede;
	}
	
	public double consultarPrecioBase() {
		return 1;
	}
	
	public String consultarId() {
		return "";
	}
	
	public void listaFunciones() {
		//La lista de funciones ver como armarlo
	}
}
