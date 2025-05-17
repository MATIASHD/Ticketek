package ticketek;

public class Estadio extends Sede {
	
	public Estadio(String nombre, String direccion, int capacidadMaxima) {
		super(nombre, direccion, capacidadMaxima,new String[]{"CAMPO"});
	}
	
	public double calcularPrecioEntrada(Funcion funcion, String sector, int fila, int asiento) {
		return 1;
	}
	
	public boolean esUbicacionValida(String sector, int fila, int asiento) {
		return true;
	}
}
