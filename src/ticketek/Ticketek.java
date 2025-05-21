package ticketek;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Ticketek implements ITicketek {
	private Map<String, Sede> sede = new HashMap<String, Sede>();
	private Map<String, Funcion> funcion = new HashMap<>();
	private Map<String, Espectaculo> espectaculo = new HashMap<>();
	private Map<String, Usuario> usuarioRegistrado = new HashMap<>();
	private Map<String, Integer> entradasRegistrada = new HashMap<>();
	
	
	public Ticketek() {
	       this.sedes = new HashMap<>();
               this.usuarios = new HashMap<>();;
               this.espectaculos = new HashMap<>();
               this.entradas = new HashMap<>();
               this.contadorEntradas = 1000;
               this.funcionList = new ArrayList<>();
               this.funciones = new HashMap<>();
	}
	
	@Override
	public void registrarSede(java.lang.String nombre, java.lang.String direccion, int capacidadMaxima) {
		// Verificar si el nombre ya está registrado
        if (sede.containsKey(nombre)) {
            throw new RuntimeException("El nombre de la sede ya está registrado");
        }
		// TODO Auto-generated method stub
		sede.put(nombre, new Estadio(nombre, direccion, capacidadMaxima));
		
	}
	
	@Override
	public void registrarSede(java.lang.String nombre, java.lang.String direccion, int capacidadMaxima,
			int asientosPorFila, java.lang.String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		  
		// Verificar si el nombre ya está registrado
        if (sede.containsKey(nombre)) {
            throw new RuntimeException("El nombre de la sede ya está registrado");
        }

        // Crear y registrar el estadio
        sede.put(nombre, new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional));
		
	}
	
	@Override
	public void registrarSede(java.lang.String nombre, java.lang.String direccion, int capacidadMaxima,
			int asientosPorFila,int cantidadPuestos, double precioConsumicion, java.lang.String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		  
		// Verificar si el nombre ya está registrado
        if (sede.containsKey(nombre)) {
            throw new RuntimeException("El nombre de la sede ya está registrado");
        }

        // Crear y registrar el estadio
        sede.put(nombre, new Miniestadio(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos, precioConsumicion, sectores, capacidad, porcentajeAdicional));
		
	}
	
	@Override
	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
		if (usuarioRegistrado.containsKey(email))
            throw new RuntimeException("Ya existe un usuario con ese email.");
		
		usuarioRegistrado.put(email, new Usuario(email, nombre, apellido, contrasenia));
		
	}
	
	@Override
	public void registrarEspectaculo(String nombre) {
		if (espectaculo.containsKey(nombre)) {
            throw new RuntimeException("El espectáculo " + nombre + " ya está registrado");
        }
        espectaculo.put(nombre, new Espectaculo(nombre));
	}
	
	@Override
	public void agregarFuncion(java.lang.String nombreEspectaculo, java.lang.String fecha, java.lang.String sede,
			double precioBase) {
		// Verificar si el nombre ya está registrado
        if (!funcion.containsKey(nombreEspectaculo)) {
            throw new RuntimeException("Espectaculo no registrada");
        }
        if (!funcion.containsKey(nombreEspectaculo)) {
            throw new RuntimeException("Espectaculo no registrada");
        }
        
        //Agrega una funcion a espectaculo
        this.espectaculo.get(nombreEspectaculo).agregarFuncion(new Funcion(fecha, sede,precioBase));
	}
	
	/*
	 * 
	 * Pendiente
	 * 
	 * */
	@Override
    public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia, int cantidadEntradas) {

        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo == null) {
            throw new IllegalArgumentException("Espectáculo no registrado.");
        }

        //LocalDate fecha = parsearFecha(fechaStr);
        Funcion funcion = espectaculo.getFuncion(fecha);
        if (funcion == null) {
            throw new IllegalArgumentException("No hay función en esa fecha.");
        }

        Sede sede = sedes.get(cantidadEntradas);
        if (sede instanceof Teatro) {
            throw new IllegalArgumentException("La sede es numerada, no se puede usar este método.");
        }

        List<IEntrada> entradasVendidas = new ArrayList<>();
        for (int i = 0; i < cantidadEntradas; i++) {
            String codEntrada = generarCodigo4Digitos();
            //int codigo, Espectaculo espectaculo, String fecha, Sede sede, String ubicacion, double precio
            Entrada entrada = new Entrada(codEntrada, nombreEspectaculo, fecha, funcion.getSede().nombre, funcion.getPrecio());
            funcion.agregarEntrada(entrada);
            funcion.incrementarEntradasCampoVendidas(1);
            entradasVendidas.add(entrada);
     }

        return entradasVendidas;
    }
	
	@Override
	public List<IEntrada> venderEntrada(java.lang.String nombreEspectaculo, java.lang.String fecha,
			java.lang.String email, java.lang.String contrasenia, java.lang.String sector, int[] asientos) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void venderEntrada(String espectaculo, String fecha, String email, int cantidad) {
		// Asignar asientos consecutivos (o lógica de campo)  
	    // Precio base sin % adicional 
	}
	
	@Override
	public java.lang.String listarFunciones(java.lang.String nombreEspectaculo) {
		if(!this.espectaculo.containsKey(nombreEspectaculo)) {
			throw new RuntimeException("No se encuentra ese espectaculo");
		}
		
		Funcion[] funciones = this.espectaculo.get(nombreEspectaculo).listaFunciones();
		StringBuilder sb = new StringBuilder();
		
		for (Funcion show : funciones) {
			//for (Funcion funcion : funciones) {
				//Si es estadio: " - ({FECHA}) {NOMBRE SEDE} - {ENTRADAS VENDIDAS} / {CAPACIDAD SEDE}"
				if (show.obtenerSede() instanceof Estadio) {  				  
					sb.append(show.obtenerFecha()).append(" ").append(show.obtenerSede().obtenerNombre()).append(" - ").append(show.obtenerSede().obtenerCapacidadMaxima()).append("\n");
				} else {
					// si no es estadio: " - ({FECHA}) {NOMBRE SEDE} - {NOMBRE SECTOR1}: {ENTRADAS VENDIDAS 1} / {CAPACIDAD SECTOR} | {NOMBRE SECTOR 2}: {ENTRADAS VENDIDAS 2} / {CAPACIDAD SECTOR 2} ..."
					sb.append(show.obtenerFecha()).append(" ").append(show.obtenerSede().obtenerNombre()).append(" - ").append(show.obtenerSede().obtenerCapacidadMaxima()).append("\n"); //FALTA
				}
			//}
		}
		return sb.toString().trim();
	}
	
	@Override
    public String listarFunciones(String nombreEspectaculo) {
        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo == null) {
            throw new IllegalArgumentException("El espectáculo no existe.");
        }

        StringBuilder resultado = new StringBuilder();

        for (Funcion funcion : funcionList) {
            Sede sede = funcion.getSede();
            String fechaStr = funcion.getFecha();
            resultado.append(String.format(" - (%s) %s - %d/%d\n",
                    fechaStr, sede.nombre, funcion.getEntradasVendidas(), sede.capacidadMaxima));
        }
        return resultado.toString();
    }

    @Override
    public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) {
        List<IEntrada> entradas = new ArrayList<>();
        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        for (Funcion funcion : espectaculo.getFunciones()) {
            entradas.addAll(funcion.getEntradasVendidas());
        }

        return entradas;
    }
	
	@Override
    public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
        List<IEntrada> entradasFuturas = new ArrayList<>();
        Usuario usuario = usuarios.get(email);
        for (IEntrada entrada : usuario.listarEntradasFuturas()) {
            entradasFuturas.add(entrada);
        }
        return entradasFuturas;
    }

    @Override
    public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
        List<IEntrada> entradasUsuario = new ArrayList<>();
        Usuario usuario = usuarios.get(email);
        for (IEntrada entrada : usuario.listarTodasLasEntradas()) {
            entradasUsuario.add(entrada);
        }
        return entradasUsuario;
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

	/*
	 * 
	 * Implementacion de la interfaz
	 * 
	 * */
	
	
	


	
	
	
	

	/*
	 * 
	 * 
	 * */
	
	/*Vender entradas a un usuario debe recibir el nombre del espectáculo, la fecha y
	la cantidad o el sector y números de asientos, dependiendo del tipo de sede de la
	función. Además de la contraseña para autenticar al usuario. Devolverá una lista de
	objetos de tipo IEntrada*/
	
	@Override
	public List<IEntrada> venderEntrada(java.lang.String nombreEspectaculo, java.lang.String fecha,
			java.lang.String email, java.lang.String contrasenia, int cantidadEntradas) {
		// TODO Auto-generated method stub
		if(!this.usuarioRegistrado.containsKey(email)) {
			throw new RuntimeException("No se encuentra registrado");
		}
		if(!this.usuarioRegistrado.get(email).validarContrasenia(contrasenia)) {
			throw new RuntimeException("La contraseña no es correcta");
		}
		
		this.usuarioRegistrado.get(email).agregarEntradaALaLista(new Entrada()));
		return null;
	}

	

	

	@Override
	public List<IEntrada> listarEntradasEspectaculo(java.lang.String nombreEspectaculo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IEntrada> listarEntradasFuturas(java.lang.String email, java.lang.String contrasenia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IEntrada> listarTodasLasEntradasDelUsuario(java.lang.String email, java.lang.String contrasenia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean anularEntrada(IEntrada entrada, java.lang.String contrasenia) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, java.lang.String contrasenia, java.lang.String fecha,
			java.lang.String sector, int asiento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, java.lang.String contrasenia, java.lang.String fecha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double costoEntrada(java.lang.String nombreEspectaculo, java.lang.String fecha) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double costoEntrada(java.lang.String nombreEspectaculo, java.lang.String fecha, java.lang.String sector) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double totalRecaudado(java.lang.String nombreEspectaculo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double totalRecaudadoPorSede(java.lang.String nombreEspectaculo, java.lang.String nombreSede) {
		// TODO Auto-generated method stub
		return 0;
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
