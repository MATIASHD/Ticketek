package ticketek;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Ticketek implements ITicketek {
	private Map<String, Usuario> usuarios;
    private Map<String, Espectaculo> espectaculos;
    private Map<String, Sede> sedes;
    private Map<String, Entrada> entradas;
    private Map<IEntrada, Usuario> entradaUsuarioMap;
    private Map<String, String> ticketToUser;
    private Map<IEntrada, Funcion> entradaFuncionMap;
    private Map<String, Funcion> funciones;
    
    private List<Espectaculo> espectaculosList;
    private List<Funcion> funcions;
    private List<Usuario> usuariosList;

    Funcion funcion;
    Espectaculo espectaculo;

    public Ticketek() {
        this.usuarios = new HashMap<>();
        espectaculos = new HashMap<>();
        sedes = new HashMap<>();
        entradas = new HashMap<>();
        this.entradaUsuarioMap = new HashMap<>();
        ticketToUser = new HashMap<>();
        this.entradaFuncionMap = new HashMap<>();
        this.funciones = new HashMap<>();
        this.funcions = new ArrayList<>();
        this.usuariosList = new ArrayList<Usuario>();
        this.espectaculosList = new ArrayList<Espectaculo>();
    }
	
	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
		// Verificar si el nombre ya está registrado
        if (sedes.containsKey(nombre)) {
            throw new RuntimeException("El nombre de la sede ya está registrado");
        }
		// TODO Auto-generated method stub
		sedes.put(nombre, new Estadio(nombre, direccion, capacidadMaxima));
		
	}
	
	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima,
			int asientosPorFila, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		  
		// Verificar si el nombre ya está registrado
        if (sedes.containsKey(nombre)) {
            throw new RuntimeException("El nombre de la sede ya está registrado");
        }

        // Crear y registrar el estadio
        sedes.put(nombre, new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional));
		
	}
	
	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima,
			int asientosPorFila,int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		  
		// Verificar si el nombre ya está registrado
        if (sedes.containsKey(nombre)) {
            throw new RuntimeException("El nombre de la sede ya está registrado");
        }

        // Crear y registrar el estadio
        sedes.put(nombre, new Miniestadio(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos, precioConsumicion, sectores, capacidad, porcentajeAdicional));
		
	}
	
	@Override
	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
		if (usuarios.containsKey(email))
            throw new RuntimeException("Ya existe un usuario con ese email.");
		
		usuarios.put(email, new Usuario(email, nombre, apellido, contrasenia));
		usuariosList.add(new Usuario(email, nombre, apellido, contrasenia));
		
	}
	
	@Override
	public void registrarEspectaculo(String nombre) {
		if (espectaculos.containsKey(nombre)) {
            throw new RuntimeException("El espectáculo " + nombre + " ya está registrado");
        }
        espectaculos.put(nombre, new Espectaculo(nombre));
	}
	
	@Override
	public void agregarFuncion(String nombreEspectaculo, String fecha, String sede, double precioBase) {
		// Verificar si el nombre ya está registrado
        //if (!funciones.containsKey(nombreEspectaculo)) {
          //  throw new RuntimeException("Espectaculo no registrado");
        //}
        //if (!funciones.containsKey(nombreEspectaculo)) {
          //  throw new RuntimeException("Espectaculo no registrado");
        //}
		
		if (fecha == null || !esFomatoValido(fecha)) {
            throw new RuntimeException("Formato de fecha inválido: debe ser dd/MM/yy");
        }
		
        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo.tieneFuncionEnFechaYSede(fecha, sede)) {
            throw new RuntimeException("Ya existe una función para esa fecha y sede");
        }
        //Agrega una funcion a espectaculo
        //Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        Sede sed = sedes.get(sede);
        espectaculo.agregarFuncion(fecha, sed, precioBase);
        funciones.put(fecha, new Funcion(fecha, sed, precioBase));
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
        Funcion funcion = funciones.get(fecha);//espectaculo.getFuncion(fecha);
        //if (funcion == null) {
          //  throw new IllegalArgumentException("No hay función en esa fecha.");
        //}

        Sede sede = sedes.get(cantidadEntradas);
        //if (sede instanceof Teatro) {
          //  throw new IllegalArgumentException("La sede es numerada, no se puede usar este método.");
        //}

        List<IEntrada> entradasVendidas = new ArrayList<>();
        for (int i = 0; i < cantidadEntradas; i++) {

            //double precio = calcularPrecioEntrada(funcion, sector);
            String codEntrada = generarCodigo4Digitos();
            Entrada entrada = new Entrada(codEntrada, nombreEspectaculo, fecha,
                    funcion.getSede().nombre, funcion.getPrecioBase());
            entradas.put(codEntrada, entrada);
            //usuarios.get(email).agregarEntrada(entrada);
            ticketToUser.put(codEntrada, email); // Add mapping
            entradasVendidas.add(entrada);
        }
        return entradasVendidas;
    }
	
	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, java.lang.String fecha, String email, 
			String contrasenia, String sector, int[] asientos) {
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        Funcion funcion = funciones.get(fecha);//espectaculo.getFuncion(fecha);

        List<IEntrada> entradasVendidas = new ArrayList<>();
        for (int asiento : asientos) {
            //if (!funcion.getSede().esSectorValido(sector) ||
              //      !funcion.getSede().getAsientosDisponibles(sector).contains(asiento)) {
                //throw new IllegalArgumentException("Sector o asiento inválido");
            //}

            //funcion.getSede().reservarAsiento(sector, asiento);
            double precio = calcularPrecioEntrada(funcion, sector);
            String codEntrada = generarCodigo4Digitos();
            Entrada entrada = new Entrada(codEntrada, nombreEspectaculo, fecha,
                    funcion.getSede().nombre, sector, 1, asiento, precio);
            entradas.put(codEntrada, entrada);
            //usuarios.get(email).agregarEntrada(entrada);
            ticketToUser.put(codEntrada, email); // Add mapping
            entradasVendidas.add(entrada);
        }
        return entradasVendidas;
	}
	
	/*@Override
	public String listarFunciones(String nombreEspectaculo) {
		//if(!this.espectaculos.containsKey(nombreEspectaculo)) {
			//throw new RuntimeException("No se encuentra ese espectaculo");
		//}
		
		Funcion[] funciones = this.espectaculos.get(nombreEspectaculo).listaFunciones();
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
	}*/
	
	@Override
    public String listarFunciones(String nombreEspectaculo) {
        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        //if (espectaculo == null) {
          //  throw new IllegalArgumentException("El espectáculo no existe.");
        //}

        StringBuilder resultado = new StringBuilder();

		for (Funcion funcion : funcions) {
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
        for (Usuario usuario : usuariosList) {
			for (IEntrada funcion : usuario.getEntradas()) {
					entradas.add(funcion);
					return entradas;
			}
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
        for (Usuario entrada : usuariosList) {
            //entradasUsuario.add((IEntrada) entrada);
        	return entrada.getEntradas();
        }
        return entradasUsuario;
    }
	
	

	/*
	 * 
	 * 
	 * */


	@Override
	public boolean anularEntrada(IEntrada entrada, String contrasenia) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha,
			java.lang.String sector, int asiento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha, String sector) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double totalRecaudado(String nombreEspectaculo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
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
	
	// Métodos auxiliares
    private static String generarCodigo4Digitos(){
        Random r = new Random();
        int numero = 1000 + r.nextInt(8000);
        StringBuilder sb = new StringBuilder();
        sb.append(numero);
        return sb.toString();
    }
    private boolean esFomatoValido(String fecha) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
            LocalDate.parse(fecha, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // Helper method to calculate ticket price
    private double calcularPrecioEntrada(Funcion funcion, String sector) {
        double precioBase = funcion.getPrecioBase();
        Sede sede = funcion.getSede();

        if (sede instanceof Estadio) {
            return precioBase;
        }

        double precio;
        switch (sector.toLowerCase()) {
            case "platea vip":
                precio = precioBase * 1.7;
                break;
            case "platea común":
                precio = precioBase * 1.4;
                break;
            case "platea baja":
                precio = precioBase * 1.5;
                break;
            case "platea alta":
                precio = precioBase;
                break;
            default:
                throw new IllegalArgumentException("Sector inválido");
        }

        if (sede instanceof Miniestadio) {
            precio += ((Miniestadio) sede).getPrecioConsumicion();
        }

        return precio;
    }
    //private String sectorPorDefecto(Sede sede) {
        // Return a default sector (e.g., Platea Alta for Teatro/Miniestadio)
      //  return sede.getSectores().containsKey("Platea Alta") ? "Platea Alta" :
        //        sede.getSectores().keySet().iterator().next();
    //}
	
}
