package ticketek;

import java.util.ArrayList;

public class Entrada implements IEntrada{
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
		//Como deberia realizar el costo??
		return 1;
	}
	
	public void modificarSede(Sede sede) {
		this.sede = sede;
	}
	
	public String codigoEntrada() {
		return this.codEntrada;
	}
	
	public boolean compararCodEntrada(String codEntrada) {
		return this.codEntrada == codEntrada;
	}

	@Override
	public double precio() {
		// TODO Auto-generated method stub
		return this.precio;
	}

	@Override
	public String ubicacion() {
		if(this.sede.obtenerNombre() == "campo") {
			return "CAMPO";
		} else {			
			return this.sector + " Fila: " + this.fila + " asiento: " + this.asientos ;
		}
	}
	
	@Override
	public String toString() {
		return this.codEntrada + " - " + this.funcion.obtenerNombreDeLaFuncion() + " - " + this.fecha + " - " + this.sede.obtenerNombre() + " - " + this.ubicacion();
	}
	
}
