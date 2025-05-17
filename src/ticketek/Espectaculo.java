package ticketek;
import java.util.ArrayList;


public class Espectaculo {
	private String nombre;
	private ArrayList<Funcion> funciones;

    public Espectaculo(String nombre) {
    	if (nombre == null || nombre.isEmpty()) {
            throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
        }
        this.nombre = nombre;
        this.funciones = new ArrayList<>();
    }

    public void agregarFuncion(Funcion funcion) {
        funciones.add(funcion);
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
}
