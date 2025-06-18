package ar.edu.ungs.prog2.ticketek;
public class Teatro extends EstadiosConSecciones{

    public Teatro(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, String[] nombresSectores, int[] capacidades, int[] porcentajes) {
    	super(nombre, direccion, capacidadMaxima, asientosPorFila, nombresSectores, capacidades, porcentajes); 
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

	@Override
	public String[] obtenerSectores() {
		return super.obtenerSectores();
	}

	@Override
	public int[] obtenerCapacidadOriginal() {
		return super.obtenerCapacidadOriginal();
	}

	@Override
	public int[] obtenerCapacidadPorSector() {
		return super.obtenerCapacidadPorSector();
	}

	@Override
	public int[] obtenerPorcentajeAdicional() {
		return super.obtenerPorcentajeAdicional();
	}

	@Override
	public int obtenerAsientoPorFila() {
		return super.obtenerAsientoPorFila();
	}

	@Override
	public String estadosSectores() {
		return super.estadosSectores();
	}

	@Override
	public int obtenerPorcentajeAdicionalSector(String sector) {
		return super.obtenerPorcentajeAdicionalSector(sector);
	}

	

}
