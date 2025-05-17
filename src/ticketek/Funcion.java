package ticketek;

import java.util.HashMap;

public class Funcion {
	private String fecha;
	private String sede;
	private double precioBase;
	private Espectaculo espectaculo;
	private HashMap<String, Sector> sectores;
	
	
	public Funcion(String fecha, String sede, double precioBase) {
		this.sede = sede;
		this.fecha = fecha;
		this.precioBase = precioBase;
		this.sectores = new HashMap<String, Sector>();
	}
	
	public boolean venderEntrada(String sector, int fila, int asiento) {
		//Ver como agregar sector
		return true;
	}
	
	public String obtenerNombreDeLaFuncion() {
		return this.nombreEspectaculo;
	}
}
