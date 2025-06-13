package ar.edu.ungs.prog2.ticketek;
public class Funcion {
	private Fecha fecha;
    private String sede;
    private double precioBase;

    public Funcion(Fecha fecha, String sede, double precioBase) {
    	if (fecha == null) {
			throw new RuntimeException("La fecha no puede ser nula");
		}
    	if (sede == null || sede.isEmpty()) {
    		throw new RuntimeException("La sede no puede ser nula o vacía");			
    	}
        this.fecha = fecha;
        this.sede = sede;
        this.precioBase = precioBase;
    }
	public Fecha obtenerFecha() {
		return this.fecha;
	}
	public String obtenerSede() {
		return this.sede;
	}
	public double obtenerPrecioBase() {
		return this.precioBase;
	}
}
