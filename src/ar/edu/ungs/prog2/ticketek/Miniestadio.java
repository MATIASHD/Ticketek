package ar.edu.ungs.prog2.ticketek;

public class Miniestadio extends EstadiosConSecciones{
	private int cantidadPuestos;
    private double precioConsumicion;

    public Miniestadio(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,int cantidadPuestos, double precioConsumicion, String[] nombresSectores,int[] capacidades, int[] porcentajes) {
    	super(nombre, direccion, capacidadMaxima, asientosPorFila, nombresSectores, capacidades, porcentajes);        
    	if (cantidadPuestos <= 0 || precioConsumicion <= 0) {
    		throw new RuntimeException("Datos de miniestadio no válidos");
    	}
        this.cantidadPuestos = cantidadPuestos;
        this.precioConsumicion = precioConsumicion;
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
	public String estadosSectores() {S
		return super.estadosSectores();
	}

	public int obtenerCantidadPuestos() {
		return this.cantidadPuestos;
	}
	
	public double obtenerPrecioConsumicion() {
		return this.precioConsumicion;
	}

	@Override
	public int obtenerPorcentajeAdicionalSector(String sector) {
		// TODO Auto-generated method stub
		return super.obtenerPorcentajeAdicionalSector(sector);
	}
	
	
}
