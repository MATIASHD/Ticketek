package ar.edu.ungs.prog2.ticketek;
import java.util.Random;
public class Entrada implements IEntrada{
	private String codEntrada; 
    private String espectaculo;
    private String fecha;
    private String nombreSede;
    private String sector;
    private int fila;
    private int asiento;
    private boolean activa;
    private Random random = new Random();

    public Entrada(String espectaculo, String fecha, String sector, int asiento, String nombreSede, int fila) {
		
		if (espectaculo.isEmpty()) {
			throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
		}
		if (fecha == null) {
			throw new RuntimeException("La fecha no puede ser nula");
		}
		if (sector.isEmpty()) {
			throw new RuntimeException("El sector no puede ser nulo o vacío");
		}
		if (asiento < 0) {
			throw new RuntimeException("El número de asiento no puede ser negativo");
		}
		if (nombreSede.isEmpty()) {
			throw new RuntimeException("El nombre de la sede no puede ser nulo o vacío");
		}
		if (fila < 0) {
			throw new RuntimeException("El número de fila no puede ser negativo");
		}
    	this.codEntrada = String.valueOf(random.nextInt(1000));
        this.espectaculo = espectaculo;
        this.fecha = fecha;
        this.sector = sector;
        this.asiento = asiento;
        this.nombreSede = nombreSede;
        this.fila = (asiento - 1) / fila + 1;
        this.activa = true;
    }
    
    public Entrada(String espectaculo, String fecha, String sede) {
    	if (espectaculo == null || espectaculo.isEmpty()) {
			throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
		}
		if (fecha == null) {
			throw new RuntimeException("La fecha no puede ser nula");
		}
    	this.codEntrada = String.valueOf(random.nextInt(1000));
        this.espectaculo = espectaculo;
        this.fecha = fecha;
        this.nombreSede = sede;
        this.sector = "CAMPO";
        this.asiento = 0;
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
	
	public boolean estadoEntrada() {
		return this.activa;
	}
	public String obtenerFecha() {
		return this.fecha;
	}
	
	public void activarEntrada() {
		this.activa = true;
	}
	
	public void anularEntrada() {
		this.activa = false;
	}
	
	public String obtenerSede() {
		return this.nombreSede;
	}
	public String obtenerEspectaculo() {
		return this.espectaculo;
	}
	public void cambiarSector(String sector) {
		if (sector == null || sector.isEmpty()) {
			throw new RuntimeException("El sector no puede ser nulo o vacío");
		}
		this.sector = sector;
	}
	public void cambiarAsiento(int asiento) {
		if (asiento < 0) {
			throw new RuntimeException("El número de asiento no puede ser negativo");
		}
		this.asiento = asiento;
		this.fila = (asiento - 1) / this.fila + 1;
	}
	public void cambiarFecha(String fecha) {
		if (fecha == null || fecha.isEmpty()) {
			throw new RuntimeException("La fecha no puede ser nula o vacía");
		}
		this.fecha = fecha;
	}
	
	@Override
	public String ubicacion() {
		if("CAMPO".equals(this.sector)) {
			return "CAMPO";
		} else {
			return " f:"+ this.fila + " a:" + this.asiento;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
	    sb.append("- ").append(this.codEntrada)
	      .append(" - ").append(this.espectaculo)
	      .append(" - ").append(this.fecha);
	      if (new Fecha(this.fecha).esPasada()) {
	          sb.append(" P");
	      }
	      sb.append(" - ").append(this.nombreSede)
	      .append(" - ").append(ubicacion());
	      return sb.toString();
	}
	
	public String obtenerFuncion() {
		StringBuilder sb = new StringBuilder();
	      sb.append("- (").append(this.fecha)
	        .append(") ").append(this.nombreSede);
	      return sb.toString().trim();
	}
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Entrada other = (Entrada) obj;
	    return this.espectaculo.equals(other.espectaculo) &&
	    this.fecha.equals(other.fecha) &&
	    this.nombreSede.equals(other.nombreSede);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + espectaculo.hashCode();
		result = 31 * result + fecha.hashCode();
		result = 31 * result + nombreSede.hashCode();
		return result;
	}
}
