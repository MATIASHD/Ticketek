package ticketek;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Funcion {
	private String fecha;
	private Sede sede;
	private double precioBase;
	private Espectaculo espectaculo;
	private HashMap<String, Sector> sectores;
	private int entradasVendidas;
	private List<IEntrada> entradaList;
	
	public Funcion(String fecha, Sede sede, double precioBase) {
		this.sede = sede;
		this.fecha = fecha;
		this.precioBase = precioBase;
		this.sectores = new HashMap<String, Sector>();
		this.entradasVendidas = 0;
		this.entradaList = new ArrayList<>();
	}
	
	public boolean venderEntrada(String sector, int fila, int asiento) {
		//Ver como agregar sector
		return true;
	}
	
	
	public int compararFecha(String fecha) {
		return compararDate(this.fecha, fecha);
	}
	
	public String obtenerFecha() {
		return this.fecha;
	}
	public Sede obtenerSede() {
		return this.sede;
	}
	
	public int getEntradasVendidas() {
        return entradasVendidas;
    }
	
	public int compararDate(String fecha1, String fecha2) {  
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");  
	    LocalDate date1 = LocalDate.parse(fecha1, formatter);  
	    LocalDate date2 = LocalDate.parse(fecha2, formatter);  
	    return date1.compareTo(date2);
	    // Uso:  
		// int resultado = compararFechas("25/07/25", "28/07/25");  
		// resultado < 0 → fecha1 es anterior  
		// resultado == 0 → iguales  
		// resultado > 0 → fecha1 es posterior 
	}

	public String getFecha() {
		return fecha;
	}

	public Sede getSede() {
		return sede;
	}

	public double getPrecioBase() {
		return precioBase;
	}
	
	public List<IEntrada> getEntradaList() {
        return entradaList;
    }
}
