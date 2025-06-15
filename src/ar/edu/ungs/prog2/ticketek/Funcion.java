package ar.edu.ungs.prog2.ticketek;
public class Funcion {
	private String nombre;
	private String fecha;
    private String sede;
    private double precioBase;

    public Funcion(String nombre,String fecha, String sede, double precioBase) {
    	if (nombre == null || nombre.isEmpty()) {
			throw new RuntimeException("El nombre de la función no puede ser nulo o vacío");
		}
    	if (fecha == null) {
			throw new RuntimeException("La fecha no puede ser nula");
		}
    	if (sede == null || sede.isEmpty()) {
    		throw new RuntimeException("La sede no puede ser nula o vacía");			
    	}
    	if (precioBase <= 0) {
    		throw new RuntimeException("El precio base debe ser mayor a cero");
    	}
    	this.nombre = nombre;
        this.fecha = fecha;
        this.sede = sede;
        this.precioBase = precioBase;
    }
	public String obtenerFecha() {
		return this.fecha;
	}
	public String obtenerSede() {
		return this.sede;
	}
	public double obtenerPrecioBase() {
		return this.precioBase;
	}
	
	public String obtenerNombre() {
		return this.nombre;
	}
}
