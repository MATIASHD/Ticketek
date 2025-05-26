package ticketek;

import java.util.*;


public class Espectaculo {
	private String nombre;
	private List<Funcion> funciones;
	
	private Map<String, Funcion> funcion; // clave: fecha en formato dd/mm/YY

    public Espectaculo(String nombre) {
    	if (nombre == null || nombre.isEmpty()) {
            throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
        }
        this.nombre = nombre;
        this.funciones = new ArrayList<>();
        this.funcion = new HashMap<>();
    }

    public void agregarFuncion(String fecha, Sede sede, double precioBase) {
        if (funcion.containsKey(fecha)) {
            throw new IllegalArgumentException("Ya hay una función para esa fecha");
        }
        funcion.put(fecha, new Funcion(fecha, sede, precioBase));
    }
	
    public boolean tieneFuncionEnFechaYSede(String fecha, String sede) {
        for (Funcion funcion : funciones) {
            if (funcion.getFecha().equals(fecha) && funcion.getSede().equals(sede)) {
                return true;
            }
        }
        return false;
    }
    
	public double consultarPrecioBase() {
		return 1; //VER COMO ABORDARLO
	}
	
	public String obtenerNombre() {
		return this.nombre;
	}
	
	public String consultarId() {
		return ""; //Ver como abordarlo
	}
	
	public Funcion[] listaFunciones() {;
		return this.funciones.toArray(new Funcion[0]);
	}
	public Funcion getFuncion(String fecha) {
        for (Funcion funcion : funciones) {
            if (funcion.getFecha().equals(fecha)) {
                return funcion;
            }
        }
        return null; // Si no se encuentra una función en la fecha dada
    }
	public Funcion buscarLaFuncion(String fecha) {
		for(Funcion funciones : this.funciones) {
			if(funciones.compararFecha(fecha) == 0) {
				return funciones;
			}
		}
		return null;
	}

	public List<Funcion> getFunciones() {
		return funciones;
	}
}
