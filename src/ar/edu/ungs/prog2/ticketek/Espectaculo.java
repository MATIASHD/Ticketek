package ar.edu.ungs.prog2.ticketek;

import java.util.HashMap;
import java.util.Map;

public class Espectaculo {
	private String nombre;
	private Map<String,Funcion> funciones;
	private List<Funcion> funcion;

    public Espectaculo(String nombre) {
    	if (nombre == null || nombre.isEmpty()) {
            throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
        }
        this.nombre = nombre;
        this.funcion = new HashMap<String, Funcion>();
    }

    public void agregarFuncion(String fecha, Sede sede, double precioBase) {
	if (funciones.containsKey(fecha)) {
            throw new RuntimeException("Ya existe una función para esa fecha");
        }
        this.funciones.put(fecha, new Funcion(fecha, sede, precioBase));
    }
	
	public Map<String, Funcion> ListaDeFunciones(){
		return this.funcion;
	}
	
	public Funcion buscarLaFuncion(String fecha) {
		return funcion.get(fecha);
	}
	public List<Funcion> getFuncion() {
            return funcion;
        }
	public String obtenerNombre() {
		return this.nombre;
	}
	@Override
        public String toString() {
            return "Espectáculo: " + nombre + ", Funciones: " + funciones.size();
        }
}
