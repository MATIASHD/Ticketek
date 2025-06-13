package ar.edu.ungs.prog2.ticketek;

public abstract class Sede {
	protected String nombre;
	protected String direccion;
	protected int capacidadMaxima;

	
	public Sede(String nombre, String direccion, int capacidadMaxima) {
		// Validar datos
        if (nombre == null || nombre.isEmpty()) {
            throw new RuntimeException("El nombre no puede ser nulo o vacío");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new RuntimeException("La dirección no puede ser nula o vacía");
        }
        if (capacidadMaxima <= 0) {
            throw new RuntimeException("La capacidad máxima debe ser positiva");
        }
        this.nombre = nombre;
		this.direccion = direccion;
		this.capacidadMaxima = capacidadMaxima;
	}	

   	public String obtenerNombre() {
		return this.nombre;
	}
   		public String obtenerDireccion() {
		return this.direccion;
	}

	public int obtenerCapcidadMaxima() {
		return this.capacidadMaxima;
	}
	
	public boolean compararSede(String sede) {
		return this.nombre.equals(sede);
	}

}
