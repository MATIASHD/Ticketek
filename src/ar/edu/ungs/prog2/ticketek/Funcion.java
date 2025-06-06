package ar.edu.ungs.prog2.ticketek;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Funcion {
	private String fecha;
    private Sede sede;
    private double precioBase;
    private List<IEntrada> entradasVendidas;

    public Funcion(String fecha, Sede sede, double precioBase) {
        if (!validarFecha(fecha) || sede == null || precioBase < 0) {
            throw new RuntimeException("Datos de función no válidos");
        }
        this.fecha = fecha;
        this.sede = sede;
        this.precioBase = precioBase;
        this.entradasVendidas = new ArrayList<>();
    }
	
	public String obtenerFecha() {
		return this.fecha;
	}
	public String obtenerSede() {
		return this.sede;
	}
	
	public double precioBase() {
		return this.precioBase;
	}

	public List<IEntrada> getEntradasVendidas() {
        return entradasVendidas;
    }

    public void agregarEntrada(IEntrada entrada) {
        entradasVendidas.add(entrada);
    }

    public void removerEntrada(IEntrada entrada) {
        entradasVendidas.remove(entrada);
    }

    private boolean validarFecha(String fecha) {
        LocalDate f;
        try {
            f = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yy"));
            return true;
        }catch (Exception e) {
            throw new RuntimeException("formato de fecha invalido. Use dd/MM/yy");
        }
    }

    public boolean esFutura() {
        // Definimos el formato de la fecha
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // Parseamos la fecha de la cadena
            LocalDate fechaIngresada = LocalDate.parse(fecha, formato);
            // Comparamos con la fecha actual
            return fechaIngresada.isAfter(LocalDate.now());
        } catch (Exception e) {
            // Manejo de excepciones si la fecha no tiene el formato correcto
            System.out.println("Formato de fecha incorrecto: " + fecha);
            return false; // O lanzar una excepción según tu lógica
        }
    }
}
