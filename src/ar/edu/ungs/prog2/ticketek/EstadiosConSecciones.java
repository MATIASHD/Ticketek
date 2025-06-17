package ar.edu.ungs.prog2.ticketek;

public abstract class EstadiosConSecciones extends Sede {
	protected String[] sectores;
	protected int[] capacidadOriginal;
	protected int[] capacidadPorSector;
	protected int[] porcentajeAdicional;
	protected int asientoPorFila;
	
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
		this.asientoPorFila = asientoPorFila;
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
	public int obtenerPorcentajeAdicionalSector(String sector) {
		for (int i = 0; i < this.sectores.length; i++) {
			if (this.sectores[i].equals(sector)) {
				return this.porcentajeAdicional[i];
			}
		}
		throw new RuntimeException("Sector no encontrado");
	}
	public int obtenerRecargo(String sector) {
		for (int i = 0; i < this.sectores.length; i++) {
			if (this.sectores[i].equals(sector)) {
				return this.porcentajeAdicional[i];
			}
		}
		throw new RuntimeException("Sector no encontrado");
	}
	
	@Override
	public String obtenerNombre() {
		return super.obtenerNombre();
	}

	@Override
	public String obtenerDireccion() {
		return super.obtenerDireccion();
	}

	@Override
	public int obtenerCapcidadMaxima() {
		return super.obtenerCapcidadMaxima();
	}
	
	public String[] obtenerSectores() {
		return this.sectores;
	}
	
	public int[] obtenerCapacidadOriginal() {
		return this.capacidadOriginal;
	}
	
	public int[] obtenerCapacidadPorSector() {
		return this.capacidadPorSector;
	}
	
	public int[] obtenerPorcentajeAdicional() {
		return this.porcentajeAdicional;
	}
	
	public int obtenerAsientoPorFila() {
		return this.asientoPorFila;
	}
	
	public String estadosSectores() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.sectores.length; i++) {
			sb.append("Platea ").append(this.sectores[i]).append(": ").append(this.capacidadPorSector[i] - this.capacidadOriginal[i]).append("/").append(this.capacidadOriginal[i]).append(" | ");
		}
		return sb.toString().trim();
	}
	public String entradasVendidas(int index) {
		int vendida = capacidadPorSector[index] - capacidadOriginal[index];
		int entradaVendida =  ((vendida %  capacidadPorSector[index] + capacidadPorSector[index]) % capacidadPorSector[index]);
		return "Platea " + this.sectores[index] + ": " + entradaVendida + "/" + capacidadOriginal[index] + " "; 
	}
	
	
	
}
