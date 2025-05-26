package ar.edu.ungs.prog2.ticketek;

public class Teatro extends EstadiosConSecciones{
	
	public Teatro(String nombre, String direccion, int capacidadMaxima, int asientoPorFila, String[] sector,
			int[] capacidadSector, int[] porcentajeAdicional) {
		super(nombre, direccion, capacidadMaxima, asientoPorFila, sector, capacidadSector, porcentajeAdicional);
		// TODO Auto-generated constructor stub
	}

	/*public double precioDeEntrada() {
		return 1; //VER COMO SEGUIR
	}
	
	public int getAsiento() {
		return 1;
	}*/

	/*@Override
	public void reservarAsiento(String sector, int asiento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean esSectorValido(String sector) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Integer> getAsientosDisponibles(String sector) {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public void descontarAsiento(String sector, int[] asientos) {
		// TODO Auto-generated method stub
		super.descontarAsiento(sector, asientos);
	}

	@Override
	public int obtenerCapcidadMaxima() {
		// TODO Auto-generated method stub
		return super.obtenerCapcidadMaxima();
	}

	@Override
	public int porcentajeRecargo(String sector) {
		// TODO Auto-generated method stub
		return super.porcentajeRecargo(sector);
	}

	@Override
	public boolean compararSede(String sede) {
		// TODO Auto-generated method stub
		return super.compararSede(sede);
	}
	
	


}
