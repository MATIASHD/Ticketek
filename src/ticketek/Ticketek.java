package ticketek;
import java.util.HashMap;
import java.util.Map;

public class Ticketek {
	private Map<String, Sede> sede = new HashMap<>();
	private Map<String, Espectaculo> espectaculo = new HashMap<>();
	private Map<String, Usuario> usuarioRegistrado = new HashMap<>();
	private Map<String, Integer> entradasRegistrada = new HashMap<>();
	
	public Ticketek(Sede sede, Espectaculo espectaculo, Usuario usuario, Integer cantidad) {
		this.sede = sede;
		this.espectaculo = espectaculo;
		this.usuarioRegistrado = usuario;
		this.entradasRegistrada = cantidad;
	}
	
	/*14. Agregar función, luego de registrar un espectáculo, se le deben poder agregar las
	funciones de a una. Para identificar al espectáculo se usa el nombre del mismo que es
	único. y para identificar una función se usa el nombre del espectáculo y la fecha.*/
	public void agregarFunciones(String nombres, Funciones funciones) {
		
	}
	
	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
		
	}
	
	/*Al registrar un espectáculo solo se indica el nombre que no puede repetirse.
	Este nombre se usará para identificar el espectáculo. Para definir las funciones del
	espectáculo se agregó el punto 14.*/
	public void registrarEspectaculo(Sede sede, String fecha, double precioBase, String nombre) {
		
	}
	
	/*Vender entradas a un usuario debe recibir el nombre del espectáculo, la fecha y
	la cantidad o el sector y números de asientos, dependiendo del tipo de sede de la
	función. Además de la contraseña para autenticar al usuario. Devolverá una lista de
	objetos de tipo IEntrada*/
	public void venderEntrada(String email, String contrasenia, String codigoEspectaculo, String nombreSede, String sector, int fila, int asiento) {
		
	}
	
	/*
	 * Punto 5. Donde dice listar sedes cambia a listar funciones. devuelve un String con el
	 * listado de funciones con un formato en particular (ver ejemplos en la interfaz).
	 * ○ Si es estadio:
	 * "({FECHA}) {NOMBRE SEDE} - {ENTRADAS VENDIDAS} / {CAPACIDAD SEDE}
	 * ○ si no es estadio:
	 * "({FECHA}) {NOMBRE SEDE} - {NOMBRE SECTOR 1}: {ENTRADAS VENDIDAS 1} /
	 * {CAPACIDAD SECTOR} | {NOMBRE SECTOR 2}: {ENTRADAS VENDIDAS 2} /
	 * {CAPACIDAD SECTOR 2} ..."
	 */
		//TOSTRING
	public Espectaculo[] listaSedePorEspectaculos(String codEspectaculos) {
		return espectaculo;
	}
	
	public Entrada[] listaDeEntradaPorUsuario(Usuario usuario) {
		return entradas;
	}
	
	public Entrada[] listaDeEntradaFuturaPorUsuario(Usuario usuario) {
		return entrada;
	}
	
	/*
	 * Punto 8. Anular una entrada, dada la entrada (de tipo IEntrada) y la contraseña del
	 * usuario que la compró. Debe resolverse en O(1).
	 * También se deberá entregar en el documento el siguiente análisis de la complejidad:
	 * Explicar la complejidad lograda y justificar por medio de Álgebra de Órdenes para el punto:8.
	 * Anular una entrada.
	 */
	public boolean anularEntrada(String codEntrada, String contrasenia) {
		return true;
	}
	
	/*
	 * Punto 9. Cambiar una entrada para otra función del mismo espectáculo. Además de la
	 * entrada, se recibe la fecha de la función y si corresponde, el sector y número de asiento.
	 * Y también, la contraseña para autenticar al usuario. Devuelve una entrada nueva para la
	 * función solicitada.
	 */
	public boolean cambiarLaEntradaPorLaMismaEnOtraSede(String codEntrada) {
		return true;
	}
	
	//Punto 11. Al consultar el valor de la entrada para una función se debe resolver en O(1)
	public double totalDeLaEntrada(String codEntrada, String contrasenia) {
		return 1;
	}
	
	/*Punto 10. Calcular el costo de una entrada ya no es necesario porque se dispone del
	objeto IEntrada, a quien se le pregunta el precio. Se debe resolver en O(1)*/
	public double costoDeLaEntrada(Sede sede, String sector) {
		return 1;
	}
	
	//13. Consultar lo recaudado por un espectáculo en una sede en particular en O(1)
	//15. Listar todas las entradas vendidas de un espectáculo, es decir, de todas sus funciones. Devuelve una lista de IEntrada.
	public double TotalRecaudadoPorEspectaculos(String idEspectaculo) {
		return 1;
	}
	
	//Usar StringBuilder
	//Iteradores y Foreach
	//Herencia (sede)
	//polimorfismo (Costo de las entradas)
	//Sobreescritura
	//sobrecarga
	//interfaces (Ticketek y entrada)
	//Clase y metodos astratos (sede)
	//Implementar toString de TAD principal
	
	/*
	 * Los nombres de las sedes son únicos.
	 *  Los estadios tienen un único Sector llamado CAMPO.
	 *  Los nombres de los espectáculos no se repiten.
	 *  Las funciones de los espectáculos se identifican por fecha. Cada espectáculo sólo
	 *  puede tener una función por fecha.
	 */
	
	
	
}
