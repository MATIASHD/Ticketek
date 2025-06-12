package ar.edu.ungs.prog2.ticketek;

public abstract class EstadiosConSecciones extends Sede {
	protected String[] sector;
	protected int[] capacidadOriginal;
	protected int[] capacidadPorSector;
	protected int asientoPorFila;
	protected int[] porcentajeAdicional;
	
	public EstadiosConSecciones(String nombre, String direccion, int capacidadMaxima,int asientoPorFila, String[] NombresDeSectores,int[] capacidadesDeSectores, int[] porcentajeAdicional) {
		super(nombre, direccion, capacidadMaxima);
		if(asientoPorFila <= 0 || NombresDeSectores == null || capacidadesDeSectores == null ||  porcentajeAdicional == null || 
				NombresDeSectores.length != capacidadesDeSectores.length || capacidadesDeSectores.length != porcentajeAdicional.length) {
			throw new RuntimeException("Datos de secciones no son validos");
		}
		this.asientoPorFila = asientoPorFila;
		this.porcentajeAdicional = porcentajeAdicional;
		this.sector = NombresDeSectores;
		this.capacidadOriginal = new int[capacidadesDeSectores.length];
		this.capacidadPorSector = new int[capacidadesDeSectores.length];
	}
	
	public void descontarAsiento(String sector, int[] asientos) {
		String[] sectores = this.sector;
		int[] cantPorSector = this.capacidadPorSector;
		
		for (int i = 0; i < sectores.length; i++) {
			if (sectores[i].equals(sector) && cantPorSector.length == asientos.length) {
				for (int j = 0; j < cantPorSector.length; j++) {
					cantPorSector[j] -= asientos[j];
				}
			}
		}
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
		return this.sector;
	}
	
	public String devolverCapacidadPorSector(int indiceSector) {
		if(indiceSector < 0 || indiceSector >= this.sector.length) {
			throw new IllegalArgumentException("Indice del sector invalido");
		}
		StringBuilder sb = new StringBuilder();
		sb.append(this.sector[indiceSector]).append(": ").append(this.capacidadPorSector[indiceSector]).append(" / ").append(this.capacidadOriginal[indiceSector]);
		String capacidad = sb.toString();
		return capacidad;
	}
	
}
