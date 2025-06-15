package ar.edu.ungs.prog2.ticketek;

public abstract class EstadiosConSecciones extends Sede {
	protected String[] sectores;
	protected int[] capacidadOriginal;
	protected int[] capacidadPorSector;
	protected int[] porcentajeAdicional;
	
	public EstadiosConSecciones(String nombre, String direccion, int capacidadMaxima,int asientoPorFila, String[] NombresDeSectores,int[] capacidadesDeSectores, int[] porcentajeAdicional) {
		super(nombre, direccion, capacidadMaxima);
		
		if(asientoPorFila <= 0 || NombresDeSectores == null || capacidadesDeSectores == null ||  porcentajeAdicional == null || 
				NombresDeSectores.length != capacidadesDeSectores.length || capacidadesDeSectores.length != porcentajeAdicional.length) {
			throw new RuntimeException("Datos de secciones no son validos");
		}
		this.sectores = NombresDeSectores.clone();
		this.capacidadPorSector = capacidadesDeSectores.clone();
		this.capacidadOriginal = capacidadesDeSectores.clone();
		this.porcentajeAdicional = porcentajeAdicional.clone();		
	}
	
	public void descontarAsiento(String sector, int[] asientos) {
		String[] sec = this.sectores;
		int[] cantPorSector = this.capacidadPorSector;
		
		for (int i = 0; i < sec.length; i++) {
			if (sec[i].equals(sector) && cantPorSector.length == asientos.length) {
				for (int j = 0; j < cantPorSector.length; j++) {
					cantPorSector[j] -= asientos[j];
				}
			}
		}
	}
	
	public String entradasVendidas(int index) {
		int vendida = capacidadPorSector[index] - capacidadOriginal[index];
		int entradaVendida =  ((vendida %  capacidadPorSector[index] + capacidadPorSector[index]) % capacidadPorSector[index]);
		return this.sectores[index] + ": " + entradaVendida + "/" + capacidadOriginal[index]; 
	}
	
	public int porcentajeRecargo(String sector) {
		if (porcentajeAdicional == null) {
			return 0; // o lanza una excepción si prefieres
		}
		switch (sector) {
			case "VIP":
				return porcentajeAdicional[0];
		    case "Comun":
		        return porcentajeAdicional[1];
		    case "Baja":
		        return porcentajeAdicional[2];
		    case "Alta":
		        return porcentajeAdicional[3];
		    default:
		            return 0; // o lanza una excepción si el sector no es válido
		}
	}
	
	public String[] obtenerSector() {
		return this.sectores;
	}
	
	public int[] obtenerCapacidadPorSector() {
		return this.capacidadPorSector;
	}
	public int[] obtenerCapacidadOriginal() {
		return this.capacidadOriginal;
	}
	public int[] obtenerPorcentajeAdicional() {
		return this.porcentajeAdicional;
	}
	
}
