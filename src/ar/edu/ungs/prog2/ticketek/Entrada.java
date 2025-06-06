package ar.edu.ungs.prog2.ticketek;

public class Entrada implements IEntrada{
	private String codEntrada; //crear numero de entrada
    private String nombreEspectaculo;
    private String fecha;
    private String sector;
    private int[] ubicacion;
    private boolean activa;

    public Entrada(String nombreDelESpectaculo, String fecha, String sector, int[] asiento) {
        this.nombreEspectaculo = nombreDelESpectaculo;
        this.fecha = fecha;
        this.sector = sector;
        this.ubicacion = asiento;
        this.activa = true;
    }
    public Entrada(String nombreDelESpectaculo, String fecha, int cantEntrada) {
        this.nombreEspectaculo = nombreDelESpectaculo;
        this.fecha = fecha;
        this.sector = "CAMPO";
        this.ubicacion = new int[]{cantEntrada};
        this.activa = true;
    }

	@Override
	public double precio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String ubicacion() {
		// TODO Auto-generated method stub
		String asientos = "";
		if(sector.equals("CAMPO")) {
			return this.sector;
		}
		for (int i = 0; i < ubicacion.length; i++) {
			asientos += "a: " + ubicacion[i];
		}
		return asientos;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.sector.equals("CAMPO")) {			
			sb.append(this.codEntrada).append(" - ").append(this.nombreEspectaculo).append(" - ").append(this.fecha).append(" - ").append(this.sector);
		} else {
			sb.append(this.codEntrada).append(" - ").append(this.nombreEspectaculo).append(" - ").append(this.fecha).append(" - ").append(this.sector);
			for (int i = 0; i < ubicacion.length; i++) {				
				sb.append(" a:").append(this.ubicacion[i]).append(" ");
			}
		}
		return sb.toString();
	}
	
	public String obtenerNombreDelEspectaculo() {
		return this.nombreEspectaculo; 
	}
	
	public String obtenerCodigoDelEspectaculo() {
		return this.codEntrada; 
	}
	
	public String obtenerFecha() {
		return this.fecha;
	}
	
	public void anular() {
		this.activa = false;
	}
	
	public void cambiarFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public void cambiarSector(String sector) {
		this.sector = sector;
	}
	
	public void cambiarAsiento(int asiento) {
		this.ubicacion[0] = asiento;
	}
	
	public boolean compararFecha(String fecha) {
		Fecha estaFecha = new Fecha(this.fecha);
		return estaFecha.compararFecha(fecha);
	}
	
	public boolean estadoDeLaEntrada() {
		return this.activa;
	}
	
	@Override
	public boolean equals(Object obj) {	
	    if (obj == null || getClass() != obj.getClass()) {
	    	return false;
	    }
	    
	    Entrada entrada = (Entrada) obj;
	    return this.codEntrada.equals(entrada.codEntrada) && fecha.equals(entrada.fecha);
	}

	@Override
	public int hashCode() {
	    int result = codEntrada.hashCode();
	    result = 31 * result + fecha.hashCode();
	    return result;
	}
}
