package ar.edu.ungs.prog2.ticketek;

public class Entrada implements IEntrada{
	private String codEntrada; 
    private String espectaculo;
    private Fecha fecha;
    private String sede;
    private String sector;
    private int fila;
    private int asiento;
    private boolean activa;

    public Entrada(String espectaculo, Fecha fecha, String sede, String sector, int fila, int asiento) {
		if (espectaculo == null || espectaculo.isEmpty()) {
			throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
		}
		if (fecha == null) {
			throw new RuntimeException("La fecha no puede ser nula");
		}
		if (sede == null || sede.isEmpty()) {
			throw new RuntimeException("La sede no puede ser nula o vacía");
		}
		if (sector == null || sector.isEmpty()) {
			throw new RuntimeException("El sector no puede ser nulo o vacío");
		}
    	this.codEntrada = String.valueOf((int) (Math.random() * 10000)); // Genera un código de entrada aleatorio de 4 dígitos
        this.espectaculo = espectaculo;
        this.fecha = fecha;
        this.sede = sede;
        this.sector = sector;
        this.fila = fila;
        this.asiento = asiento;
        this.activa = true;
    }
    public Entrada(String espectaculo, Fecha fecha, String sede, String sector) {
    	this(espectaculo, fecha, sede, sector, 0, 0);
    }
    
	@Override
	public double precio() {
		if (sector.equals("CAMPO")) {
			return 0;
		}
		return 0;
	}

	@Override
	public String ubicacion() {
		if (sector.equals("CAMPO")) {
			return "CAMPO";
		} else if (fila > 0 && asiento > 0) {
			return this.sector + " f:" + this.fila + " a:" + this.asiento;
		}
		return null;
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
	}
	
	public void activarEntrada() {
		this.activa = true;
	}
	public void anularEntrada() {
		this.activa = false;
	}
	public boolean estaActiva() {
		return this.activa;
	}
}
