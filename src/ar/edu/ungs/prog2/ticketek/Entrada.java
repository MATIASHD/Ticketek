package ar.edu.ungs.prog2.ticketek;
public class Entrada implements IEntrada{
	private String usuario;
	private int codEntrada; 
    private String espectaculo;
    private String fecha;
    private String nombreSede;
    private String sector;
    private int fila;
    private int asiento;
    private boolean activa;
    private double costoEntrada;
    public Entrada(String espectaculo, String fecha, String sector, int asiento, String nombreSede, int fila, String usuario, int codEntrada, double precio) {
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
		if (usuario == null || usuario.isEmpty()) {
			throw new RuntimeException("El usuario no puede ser nulo o vacío");
		}
		if (codEntrada < 0) {
			throw new RuntimeException("El código de entrada no puede ser negativo");
		}
		if (precio <= 0) {
			throw new RuntimeException("No puede ser negativo el precio");
		}
    	this.codEntrada = codEntrada;
    	this.usuario = usuario;
        this.espectaculo = espectaculo;
        this.fecha = fecha;
        this.sector = sector;
        this.asiento = asiento;
        this.nombreSede = nombreSede;
        this.fila = (asiento - 1) / fila + 1;
        this.activa = true;
        this.costoEntrada = precio;
    }
    
    public Entrada(String espectaculo, String fecha, String sede, String usuario, int codEntrada, double precio) {
    	if (espectaculo == null || espectaculo.isEmpty()) {
			throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
		}
		if (fecha == null) {
			throw new RuntimeException("La fecha no puede ser nula");
		}
		if (sede.isEmpty()) {
			throw new RuntimeException("El nombre de la sede no puede ser nulo o vacío");
		}
		if (usuario == null || usuario.isEmpty()) {
			throw new RuntimeException("El usuario no puede ser nulo o vacío");
		}
		if (codEntrada < 0) {
			throw new RuntimeException("El código de entrada no puede ser negativo");
		}
		if (precio <= 0) {
			throw new RuntimeException("No puede ser negativo el precio");
		}
    	this.codEntrada = codEntrada;
    	this.usuario = usuario;
        this.espectaculo = espectaculo;
        this.fecha = fecha;
        this.nombreSede = sede;
        this.sector = "CAMPO";
        this.asiento = 0;
        this.activa = true;
        this.costoEntrada = precio;
    }
    
	@Override
	public double precio() {
		return this.costoEntrada;
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
	
	public String obtenerSector() {
		return this.sector;
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
	
	public String obtenerUsuario() {
		return this.usuario;
	}
	
	public boolean validarUsuario(String email) {
		if (usuario == null || usuario.isEmpty()) {
			throw new RuntimeException("El usuario no puede ser nulo o vacío");
		}
		return this.usuario.equals(usuario);
	}
	
	public int obtenerCodigoEntrada() {
		return this.codEntrada;
	}
	
	@Override
	public String ubicacion() {
		if("CAMPO".equals(this.sector)) {
			return "CAMPO";
		} else {
			return " f:"+ this.fila + " a:" + this.asiento;
		}
	}
	
	public int obtenerAsiento() {
		return this.asiento;
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
	      .append(" - ").append(ubicacion()).append("\n");
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
	    if (this == obj) {
	    	return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	    	return false;
	    }
	    Entrada pass = (Entrada) obj;
	    return this.codEntrada == pass.codEntrada;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + codEntrada;
		return result;
	}
}
