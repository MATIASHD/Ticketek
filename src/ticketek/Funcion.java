package ticketek;

import java.util.ArrayList;

public class Funcion {
	private Sede sede;
	private String fecha;
	private double precioBase;
	private ArrayList<Entrada> entradas;
	private Espectaculo espectaculos;
	private int entradaVendidas;
	
	public Funcion(Sede sede, String fecha, double precioBase,Espectaculo espectaculo) {
		this.sede = sede;
		this.fecha = fecha;
		this.precioBase = precioBase;
		this.espectaculos = espectaculo;
	}
	
	public boolean venderEntrada(Sector sector, int fila, int asiento) {
		//Ver como agregar sector
		return true;
	}
}
