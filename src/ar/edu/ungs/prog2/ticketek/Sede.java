package ar.edu.ungs.prog2.ticketek;

public abstract class Sede {
	protected String nombre;
	protected String direccion;
	protected int capacidadMaxima;
	/*protected String[] sector;
	protected int[] capacidadOriginal;
	protected int[] capacidadPorSector;*/
	
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

    public abstract boolean esNumerada();
    public abstract String toString();

    /*public int[] obtenerSector() {
        return new int[0];
    }
	
	public String[] obtenerSector() {
		return this.obtenerSector();
	}*/
	
	public int obtenerCapcidadMaxima() {
		return this.capacidadMaxima;
	}
	
	public boolean compararSede(String sede) {
		return this.nombre.equals(sede);
	}
	
	/*public String devolverCapacidadPorSector(int sector) {
		StringBuilder sb = new StringBuilder(); 
		// Platea VIP: 30/50 
		sb.append(this.sector[sector]).append(": ").append(this.capacidadPorSector[sector]).append(" / ").append(this.capacidadOriginal[sector]);
		String capacidad = sb.toString();
		return capacidad;
	}*/
}
