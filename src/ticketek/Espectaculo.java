package ticketek;

public class Espectaculo {
	private String nombre;
    private List<Funcion> funciones;

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
		return 1;
	}
	
	public String consultarId() {
		return "";
	}
	
	public void listaFunciones() {
		//La lista de funciones ver como armarlo
	}
}
