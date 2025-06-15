package ar.edu.ungs.prog2.ticketek;
public class Teatro extends EstadiosConSecciones{

    public Teatro(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, String[] nombresSectores, int[] capacidades, int[] porcentajes) {
    	super(nombre, direccion, capacidadMaxima, asientosPorFila, nombresSectores, capacidades, porcentajes); 
    }

	
	@Override
	public String toString() {
		return null;
	}

	@Override
	public int[] obtenerCapacidadPorSector() {
		return super.obtenerCapacidadPorSector();
	}

	@Override
	public int[] obtenerCapacidadOriginal() {
		return super.obtenerCapacidadOriginal();
	}


	@Override
	public String[] obtenerSector() {
		return super.obtenerSector();
	}

	@Override
	public String entradasVendidas(int index) {
		return super.entradasVendidas(index);
	}


	@Override
	public int porcentajeRecargo(String sector) {
		return super.porcentajeRecargo(sector);
	}
	


	

}
