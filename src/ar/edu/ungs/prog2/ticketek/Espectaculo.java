package ar.edu.ungs.prog2.ticketek;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Espectaculo {
	private int idEntrada;
	private String nombre;
	private Map<Fecha,Funcion> funciones;

    public Espectaculo(String nombre) {
    	if (nombre == null || nombre.isEmpty()) {
            throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
        }
    	this.idEntrada = ThreadLocalRandom.current().nextInt(1000, 10000); // 4-digit random number
        this.nombre = nombre;
        this.funciones = new HashMap<Fecha, Funcion>();
    }

   public void agregarFuncion(Fecha fecha, String sede, double precioBase) {
	if (this.funciones.containsKey(fecha)) {
            throw new RuntimeException("Esta función ya se encuentra");
        }
        this.funciones.put(fecha, new Funcion(fecha, sede, precioBase));
    }
	
   public int obtenerIdEntrada() {
		return this.idEntrada;
	}
	
	public boolean estaLaFuncion(String fecha) {
		for (Fecha date : funciones.keySet()) {
			if (date.compararFecha(fecha)) {
				return true;
			}
		}
		return false;
	}
	
	public Funcion buscarLaFuncion(String fecha) {
		for(Map.Entry<Fecha, Funcion> date : funciones.entrySet()) {
			if (date.getKey().compararFecha(fecha)) {
				return date.getValue();
			}
		}
		return null;
	}

	public String obtenerNombre() {
		return this.nombre;
	}
	@Override
        public String toString() {
            return "Espectáculo: " + nombre + ", Funciones: " + funciones.size();
   }
}
