package ar.edu.ungs.prog2.ticketek;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Espectaculo {
	private String nombre;
	/*private String[] fecha;
	private String[] sede;
	private double precio;*/
	private Map<String,Funcion> funciones;
	/*private Map<String,Funcion> funcion;*/

    public Espectaculo(String nombre) {
    	
    	if (nombre == null || nombre.isEmpty()) {
            throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
        }
        this.nombre = nombre;
        this.funciones = new HashMap<String, Funcion>();
    }

    public void agregarFuncion(String nombre, String[] fecha, String[] sede, double precioBase) {
	if (this.funciones.containsKey(nombre)) {
            throw new RuntimeException("Esta función ya se encuentra");
        }
        this.funciones.put(, new Funcion(nombre, fecha, sede, precioBase));
    }
	
	public Map<String, Funcion> ListaDeFunciones(){
		return this.funcion;
	}
	
	public Funcion buscarLaFuncion(String fecha) {
		return funcion.get(fecha);
	}
	public Map<String, Funcion> getFuncion() {
            return this.funcion;
    }
	public String obtenerNombre() {
		return this.nombre;
	}
	@Override
        public String toString() {
            return "Espectáculo: " + nombre + ", Funciones: " + funciones.size();
        }
}
