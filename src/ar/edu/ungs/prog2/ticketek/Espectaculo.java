package ar.edu.ungs.prog2.ticketek;

import java.util.HashMap;
import java.util.Map;

public class Espectaculo {
	private String nombre;
	private Map<String,Funcion> funciones;

    public Espectaculo(String nombre) {
    	if (nombre == null || nombre.isEmpty()) {
            throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
        }
        this.nombre = nombre;
        this.funciones = new HashMap<String, Funcion>();
    }

   public void agregarFuncion(String nombre,String fecha, String sede, double precioBase) {
	if (this.funciones.containsKey(fecha)) {
            throw new RuntimeException("Esta función ya se encuentra");
        }
        this.funciones.put(fecha, new Funcion(nombre, fecha, sede, precioBase));
    }
	
   public boolean checkFecha(String fecha) {
	   return funciones.containsKey(fecha);
   }
   public int tamanio() {
	   return funciones.size();
   }
   public String obtenerNombreSede(String fecha) {
	   return funciones.get(fecha).obtenerSede();
   }
   public Funcion obtenerLaFuncion(String fecha) {
	   return funciones.get(fecha);
   }
   public Map<String, Funcion> obtenerLista(){
	   return funciones;
   }

   public String obtenerNombre() {
		return this.nombre;
   }
	@Override
   public String toString() {
		return "Espectáculo: " + nombre + ", Funciones: " + funciones.size();
   }
}
