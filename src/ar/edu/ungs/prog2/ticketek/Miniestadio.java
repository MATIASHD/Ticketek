package ar.edu.ungs.prog2.ticketek;
public class Miniestadio extends EstadiosConSecciones{
	private int cantidadPuestos;
    private double precioConsumicion;

    public Miniestadio(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,int cantidadPuestos, double precioConsumicion, String[] nombresSectores,int[] capacidades, int[] porcentajes) {
        super(nombre, direccion, capacidadMaxima, asientosPorFila, nombresSectores, capacidades, porcentajes);
        
        if (cantidadPuestos > 0 || precioConsumicion > 0) {
            throw new RuntimeException("Datos de miniestadio no válidos");
        }
        
        this.cantidadPuestos = cantidadPuestos;
        this.precioConsumicion = precioConsumicion;
    }

    public int obtenerCantidadPuestos() {
		return cantidadPuestos;
	}
    public double obtenerPrecioConsumicion() {
		return precioConsumicion;
	}
	@Override
	public boolean esNumerada() {
		return false;
	}

	@Override
	public String toString() {
		return null;
	}

    
}
