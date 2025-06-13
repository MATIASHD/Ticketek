package ar.edu.ungs.prog2.ticketek;
public class Teatro extends EstadiosConSecciones{

    public Teatro(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, String[] nombresSectores, int[] capacidades, int[] porcentajes) {
    	super(nombre, direccion, capacidadMaxima, asientosPorFila, nombresSectores, capacidades, porcentajes); 
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
