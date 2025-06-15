package ar.edu.ungs.prog2.ticketek;
import java.util.Random;
public class Entrada implements IEntrada{
	private String codEntrada; 
    private String espectaculo;
    private String fecha;
    private String sector;
    private int []asiento;
    private boolean activa;
    private Random random = new Random();

    public Entrada(String espectaculo, String fecha, String sector, int[] asiento) {
		if (espectaculo == null || espectaculo.isEmpty()) {
			throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
		}
		if (fecha == null) {
			throw new RuntimeException("La fecha no puede ser nula");
		}
		if (sector == null || sector.isEmpty()) {
			throw new RuntimeException("El sector no puede ser nulo o vacío");
		}
    	this.codEntrada = String.valueOf(random.nextInt(1000)); // Genera un código de entrada aleatorio de 4 dígitos
        this.espectaculo = espectaculo;
        this.fecha = fecha;
        this.sector = sector;
        this.asiento = asiento;
        this.activa = true;
    }
    
    public Entrada(String espectaculo, String fecha, int cantAsientos) {
    	if (espectaculo == null || espectaculo.isEmpty()) {
			throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
		}
		if (fecha == null) {
			throw new RuntimeException("La fecha no puede ser nula");
		}
		if (cantAsientos < 0) {
			throw new RuntimeException("No puede ser cero o negativo la cantidad de Entradas");
		}
    	this.codEntrada = String.valueOf(random.nextInt(1000));
        this.espectaculo = espectaculo;
        this.fecha = fecha;
        this.sector = "CAMPO";
        this.asiento = new int[] {cantAsientos};
        this.activa = true;
    }
    
	@Override
	public double precio() {
		if (sector.equals("CAMPO")) {
			return 1000; // Precio fijo para entradas de campo
		} else if (sector.equals("VIP")) {
			return 2000 + (2000 * 0.20); // Precio base más recargo del 20%
		} else if (sector.equals("Comun")) {
			return 1500 + (1500 * 0.10); // Precio base más recargo del 10%
		} else if (sector.equals("Baja")) {
			return 1200 + (1200 * 0.05); // Precio base más recargo del 5%
		} else if (sector.equals("Alta")) {
			return 800; // Precio base sin recargo
		} else {
			throw new RuntimeException("Sector no válido");
		}
	}

	@Override
	/*public String ubicacion() {
		if (sector.equals("CAMPO")) {
			return "CAMPO";
		} else if (fila > 0) {
			return this.sector + " f:" + this.fila + " a:" + this.asiento;
		}
		return null;
	}
	
	public String obtenerNombreEspectaculo() {
		return this.espectaculo;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(this.sector.equals("CAMPO")) {
			sb.append(this.codEntrada).append(" - ").append(this.espectaculo).append(" - ").append(this.fecha).append(" - ").append(this.sede).append(" - CAMPO");
		} else {
			sb.append(this.codEntrada).append(" - ").append(this.espectaculo).append(" - ").append(this.fecha).append(" - ").append(this.sede).append(" - ").append(this.sector)
			  .append(" f:").append(this.fila).append(" a:").append(this.asiento);
		}
		return sb.toString();
	}*/
	
	public boolean activarEntrada() {
		this.activa = true;
		return true;
	}
	public boolean anularEntrada() {
		this.activa = false;
		return false;
	}
	public boolean estaActiva() {
		return this.activa;
	}
	public String obtenerFecha() {
		return this.fecha;
	}
	/*public String obtenerSede() {
		return this.sede;
	}*/
	/*public void cambiarSede(String sede) {
		if (sede == null || sede.isEmpty()) {
			throw new RuntimeException("La sede no puede ser nula o vacía");
		}
		this.sede = sede;
	}*/
	public void cambiarSector(String sector) {
		if (sector == null || sector.isEmpty()) {
			throw new RuntimeException("El sector no puede ser nulo o vacío");
		}
		this.sector = sector;
	}
	public void cambiarAsiento(int asiento) {
		if (asiento <= 0) {
			throw new RuntimeException("El asiento debe ser un número positivo");
		}
		this.asiento = new int[] {asiento};
	}
}
