package ar.edu.ungs.prog2.ticketek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ticketek implements ITicketek {
	private Map<String, Sede> sedes;
	private Map<String, Espectaculo> espectaculos;
	private Map<String, Usuario> usuarios;
	private Map<Integer,IEntrada> entradas;
	private Map<String, Double> totalRecaudadoPorEspectaculo;
	private Map<String, Map<String, Double>> totalRecaudadoPorSede;
	private int codEntrada;
	public Ticketek() {
		this.sedes = new HashMap<String, Sede>();
        this.usuarios = new HashMap<String,Usuario>(); 
        this.espectaculos = new HashMap<String, Espectaculo>();
        this.entradas = new HashMap<Integer,IEntrada>();
        this.codEntrada = 0;
        this.totalRecaudadoPorEspectaculo = new HashMap<String, Double>();
        this.totalRecaudadoPorSede  = new HashMap<>();
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
		if (sedes.containsKey(nombre)) {
			throw new RuntimeException("Sede ya registrada");
		}
		sedes.put(nombre, new Estadio(nombre, direccion, capacidadMaxima));
	}
	
	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		if (sedes.containsKey(nombre)) {
			throw new RuntimeException("Sede ya registrada");
		}
		sedes.put(nombre, new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional));
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {
		if (sedes.containsKey(nombre)) {
			throw new RuntimeException("Sede ya registrada");
		}
		sedes.put(nombre, new Miniestadio(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos, precioConsumicion, sectores, capacidad, porcentajeAdicional));
	}

	@Override
	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
		if (usuarios.containsKey(email)) {
			throw new RuntimeException("Usuario ya registrado");
		}
		usuarios.put(email, new Usuario(email, nombre, apellido, contrasenia));
	}

	@Override
	public void registrarEspectaculo(String nombre) {
		if (this.espectaculos.containsKey(nombre)) {
			throw new RuntimeException("Espectáculo ya registrado");
		}
		espectaculos.put(nombre, new Espectaculo(nombre));
	}
	
	@Override
	public void agregarFuncion(String nombreEspectaculo, String fecha, String sede, double precioBase) {
		if (espectaculos.get(nombreEspectaculo).checkFecha(fecha)) {
			throw new RuntimeException("Ya existe un evento en esa sede y fecha");
		}
		espectaculos.get(nombreEspectaculo).agregarFuncion(nombreEspectaculo, fecha, sede, precioBase);
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,int cantidadEntradas) {
		List<IEntrada> nuevasEntradas = new ArrayList<IEntrada>();
		
		Espectaculo evento = espectaculos.get(nombreEspectaculo);
		Funcion show = evento.obtenerLaFuncion(fecha);

		if (nombreEspectaculo.isEmpty() || !espectaculos.containsKey(nombreEspectaculo)) {
			throw new RuntimeException("Espectáculo no registrado");
		}
		if(!validarContrasenia(email, contrasenia)) {
			throw new RuntimeException("Usuario no registrado o contraseña incorrecta");
		}
		for (int i = 0; i < cantidadEntradas; i++) {
			codEntrada++;
			IEntrada entrada = new Entrada(nombreEspectaculo, fecha, show.obtenerSede(), email, codEntrada, show.obtenerPrecioBase());
			totalRecaudadoPorEspectaculo.put(nombreEspectaculo, totalRecaudadoPorEspectaculo.getOrDefault(nombreEspectaculo, 0.0) + show.obtenerPrecioBase());
			recudarPorSede(nombreEspectaculo, show.obtenerSede(), show.obtenerPrecioBase());
			nuevasEntradas.add(entrada);
			usuarios.get(email).agregarEntrada(entrada);
			entradas.put(codEntrada,entrada);	
		}
		return nuevasEntradas;
		
	}
	
	
	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			String sector, int[] asientos) {
		List<IEntrada> nuevasEntradas = new ArrayList<>();
		Espectaculo evento = espectaculos.get(nombreEspectaculo);
		Funcion show = evento.obtenerLaFuncion(fecha);

		if (!espectaculos.containsKey(nombreEspectaculo) || nombreEspectaculo.isEmpty()) {
			throw new RuntimeException("Espectáculo no registrado");
		}
		if (!usuarios.containsKey(email)) {
			throw new RuntimeException("Usuario no registrado");
		}
		if (!validarContrasenia(email, contrasenia)) {
			throw new RuntimeException("Contraseña incorrecta");
		}
		double precio = 0;
		Sede predio = sedes.get(evento.obtenerNombreSede(fecha));
		
		if (predio instanceof Teatro) {
			Teatro lugar = (Teatro) predio;
			precio = lugar.costoDeLaEntrada(show.obtenerPrecioBase(), sector);
		} else {
			Miniestadio lugar = (Miniestadio) predio;
			precio = lugar.costoDeLaEntrada(show.obtenerPrecioBase(), sector);
		}
		
		for (int i = 0; i < asientos.length; i++) {
			codEntrada++;
			totalRecaudadoPorEspectaculo.put(nombreEspectaculo, totalRecaudadoPorEspectaculo.getOrDefault(nombreEspectaculo, 0.0) + precio);
			recudarPorSede(nombreEspectaculo, show.obtenerSede(), precio);
			IEntrada entrada = new Entrada(nombreEspectaculo, fecha, sector, asientos[i], show.obtenerSede(),1, email, codEntrada, precio);
			nuevasEntradas.add(entrada);
			usuarios.get(email).agregarEntrada(entrada);
			entradas.put(codEntrada,entrada);			
		}
		return nuevasEntradas;
	}
	
	private void recudarPorSede(String espectaculo, String sede, double precio) {
		if(!totalRecaudadoPorSede.containsKey(espectaculo)) {
			totalRecaudadoPorSede.put(espectaculo, new HashMap<>());
		}
		Map<String, Double> listaDeSedePorEspectaculo = totalRecaudadoPorSede.get(espectaculo);
		listaDeSedePorEspectaculo.put(sede, listaDeSedePorEspectaculo.getOrDefault(sede, 0.0) + precio);
	}
	
	@Override
	public String listarFunciones(String nombreEspectaculo) {
		if (nombreEspectaculo == null || nombreEspectaculo.isEmpty()) {
			throw new RuntimeException("Espectáculo no debe estar vacío");
		}
		
		StringBuilder sb = new StringBuilder();
		Map<String, Funcion> event = espectaculos.get(nombreEspectaculo).obtenerLista();
		
	    for (Funcion show : event.values()) {
	       if (show.obtenerNombre().equals(nombreEspectaculo)) {
	            Sede sede = sedes.get(show.obtenerSede());
	            sb.append("- (").append(show.obtenerFecha()).append(") ").append(show.obtenerSede()).append(" - ");
	            sb.append(sede.estadosSectores()).substring(0, sb.length() - 1);
	            sb.append("\n");
	       }
	    }
	    return sb.toString().trim();
	}
	
	@Override
	public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) {
		if (!espectaculos.containsKey(nombreEspectaculo)) {
			throw new RuntimeException("Espectáculo no registrado");
		}
		List<IEntrada> entradasVendidas = new ArrayList<>();
		for (IEntrada entrada : entradas.values()) {
			Entrada ticket = (Entrada) entrada;
			if (ticket.obtenerEspectaculo().equals(nombreEspectaculo) && ticket.estadoEntrada()) {
				entradasVendidas.add(entrada);
			}
		}
		return entradasVendidas;
	}
	
	@Override
	public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
		if (!usuarios.get(email).validarContrasenia(contrasenia)) {
			throw new RuntimeException("Contraseña incorrecta");
		}
		List<IEntrada> entradasVendidas = new ArrayList<>();
		for (IEntrada entrada : entradas.values()) {
			Entrada ticket = (Entrada) entrada;
			if (new Fecha(ticket.obtenerFecha()).esFutura() && ticket.estadoEntrada()) {
				entradasVendidas.add(entrada);	
			}
		}
		return entradasVendidas;
	}
	
	@Override
	public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
		if (!validarContrasenia(email,contrasenia)) {
			throw new RuntimeException("Contraseña incorrecta");
		}
		List<IEntrada> resultado = new ArrayList<>();
		for (IEntrada entrada : entradas.values()) {
			Entrada ticket = (Entrada) entrada;
			if (ticket.obtenerUsuario().equals(email)) {
				resultado.add(entrada);
		    }
		}
			return resultado; 
	}

	@Override
	public boolean anularEntrada(IEntrada entrada, String contrasenia) {
		if (entrada == null) {
			throw new RuntimeException("La entrada no es válida");
		}    
		Entrada ticket = (Entrada) entrada;
		if (!validarContrasenia(ticket.obtenerUsuario(),contrasenia)) {
			throw new RuntimeException("Contraseña incorrecta");
		}
		
		if(!entradas.get(ticket.obtenerCodigoEntrada()).equals(ticket)) {
			throw new RuntimeException("La entrada ya está anulada");
		}
	
	    entradas.remove(ticket.obtenerCodigoEntrada());
	    return true;
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha, String sector, int asiento) {
		Entrada ticket = (Entrada) entrada;
		if (entrada == null) {
	        throw new RuntimeException("La entrada no se encontró o no es válida");
	    }
		if (!validarContrasenia(ticket.obtenerUsuario(), contrasenia)) {
	        throw new RuntimeException("Contraseña incorrecta");
	    }
	    if (!ticket.estadoEntrada()) {
	        throw new RuntimeException("La entrada ya está anulada");
	    }
	    ticket.cambiarFecha(fecha); 
	    ticket.cambiarSector(sector);
	    ticket.cambiarAsiento(asiento);
	    return ticket;
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha) {
		Entrada ticket = (Entrada) entrada;
		if (entrada == null) {
	        throw new RuntimeException("La entrada no se encontró o no es válida");
	    }
		
		if(!validarContrasenia(ticket.obtenerUsuario(), contrasenia)) {
			throw new RuntimeException("Contraseña incorrecta");
		}
		
	    if (!ticket.estadoEntrada()) {
	        throw new RuntimeException("La entrada ya está anulada");
	    }
	    ticket.cambiarFecha(fecha);
	    return ticket;
	}
	
	public IEntrada buscarEntrada(String nombreEspectaculo, String fecha) {
	    for (IEntrada entrada : entradas.values()) {
	    	Entrada ticket = (Entrada) entrada;
	        if (ticket.obtenerEspectaculo().equals(nombreEspectaculo) && new Fecha(ticket.obtenerFecha()).compararFecha(fecha)) {
	            return entrada;
	        }
	    }
	    return null;
	}
	
	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha) {
		 IEntrada entrada = buscarEntrada(nombreEspectaculo, fecha);
		    if (entrada == null) {
		        throw new RuntimeException("Función no encontrada");
		    }
		    Entrada ticket = (Entrada) entrada;
		    return ticket.precio();
		
	}
	
	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha, String sector) {
		IEntrada entrada = buscarEntrada(nombreEspectaculo, fecha);
	    if (entrada == null) {
	        throw new IllegalArgumentException("Función o sector no encontrado");
	    }
	    Espectaculo evento = espectaculos.get(nombreEspectaculo);
	    Sede predio = sedes.get(evento.obtenerNombreSede(fecha));
	    Funcion show = evento.obtenerLaFuncion(fecha);
	    
		if (predio instanceof Teatro) {
			Teatro lugar = (Teatro) predio;
			return lugar.costoDeLaEntrada(show.obtenerPrecioBase(), sector);
		} else {
			Miniestadio lugar = (Miniestadio) predio;
			return lugar.costoDeLaEntrada(show.obtenerPrecioBase(), sector);
		}
	}
		
	@Override
	public double totalRecaudado(String nombreEspectaculo) {
		if (!espectaculos.containsKey(nombreEspectaculo)) {
			throw new RuntimeException("Espectáculo no registrado");
		}
		return totalRecaudadoPorEspectaculo.getOrDefault(nombreEspectaculo, 0.0);
	}
	
	@Override
	public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
		
		Map<String, Double> totalRecaudadoPorSedeEspectaculo = totalRecaudadoPorSede.get(nombreEspectaculo);
	    if (totalRecaudadoPorSedeEspectaculo == null) {
	        return 0.0;
	    }
	    return totalRecaudadoPorSedeEspectaculo.getOrDefault(nombreSede, 0.0);
	}

	private boolean validarContrasenia(String email, String contrasenia) {
		return usuarios.containsKey(email) && usuarios.get(email).validarContrasenia(contrasenia);
	}
	
	
}