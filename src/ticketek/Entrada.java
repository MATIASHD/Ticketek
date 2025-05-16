package ticketek;

import java.util.ArrayList;

public class Entrada {
	private String codEntrada;
	private Funcion funcion;
	private Sede sede;
	private String fecha;
	private String sector;
	private ArrayList<Integer> asientos;
	private int fila;
	private Usuario comprador;
	private double precio; 
	
	public Entrada(Funcion funcion, Sede sede, String fecha, String sector, int fila, Usuario usuario, double precio) {
		this.funcion = funcion;
		this.sede = sede;
		this.fecha = fecha;
		this.sector = sector;
		this.fila = fila;
		this.comprador = usuario;
		this.precio = precio;	
	}
	
	public boolean esFechaFutura() {
		return true;
	}
	
	public int calcularCosto() {
		return 1;
	}
	
	public void modificarSede(String sede) {
		
	}
	
	public String codigoEntrada() {
		return "hola";
	}
	
	public boolean compararCodEntrada(String codEntrada) {
		return this.codEntrada == codEntrada;
	}
	
}
