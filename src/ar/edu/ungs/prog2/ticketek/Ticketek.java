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
	private int codEntrada;
	public Ticketek() {
		this.sedes = new HashMap<String, Sede>();
        this.usuarios = new HashMap<String,Usuario>(); 
        this.espectaculos = new HashMap<String, Espectaculo>();
        this.entradas = new HashMap<Integer,IEntrada>();
        this.codEntrada = 0;
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
		
		
		if (predio instanceof EstadiosConSecciones) {
			EstadiosConSecciones lugar = (EstadiosConSecciones) predio;
			precio = lugar.costoDeLaEntrada(show.obtenerPrecioBase(), sector);
		}
		
		for (int i = 0; i < asientos.length; i++) {
			codEntrada++;
			IEntrada entrada = new Entrada(nombreEspectaculo, fecha, sector, asientos[i], show.obtenerSede(),1, email, codEntrada, precio);
			usuarios.get(email).agregarEntrada(entrada);
			entradas.put(codEntrada,entrada);			
		}
		return nuevasEntradas;
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
		for (IEntrada pass : entradas.values()) {
			if (pass.equals(ticket)) {
				if (!validarContrasenia(ticket.obtenerUsuario(), contrasenia)) {
					throw new RuntimeException("La entrada ya está anulada");
		        }
		        ticket.anularEntrada();
		        entradas.remove(ticket.obtenerCodigoEntrada());
		        return true;
		    }
		}
		throw new RuntimeException("La entrada no existe");
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
	
	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha) {
		Map<String, Funcion> event = obtenerFunciones(nombreEspectaculo);
		for (Funcion show : event.values()) {
			if(show.obtenerNombre().equals(nombreEspectaculo) && new Fecha(show.obtenerFecha()).compararFecha(fecha)) {
				return sedes.get(show.obtenerSede()).costoDeLaEntrada(show.obtenerPrecioBase());
			}
		}
		throw new RuntimeException("Función no encontrada");
	}
	
	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha, String sector) {
		Map<String, Funcion> event = espectaculos.get(nombreEspectaculo).obtenerLista();
	    for (Funcion show : event.values()) {
	    		if(new Fecha(show.obtenerFecha()).compararFecha(fecha)){
	    			EstadiosConSecciones predio = (EstadiosConSecciones) sedes.get(show.obtenerSede());
	    			return predio.costoDeLaEntrada(show.obtenerPrecioBase(),sector);
	    		}
	    }
		throw new IllegalArgumentException("Función o sector no encontrado");
	}
	
	
	@Override
	public double totalRecaudado(String nombreEspectaculo) {
		double total = 0.0;
		for (IEntrada entrada : entradas.values()) {
			Entrada pass = (Entrada) entrada;
			if(pass.obtenerEspectaculo().equals(nombreEspectaculo)) {
				if (sedes.get(pass.obtenerSede()) instanceof Estadio) {
					total += costoEntrada(pass.obtenerEspectaculo(), pass.obtenerFecha());
				}
				if (sedes.get(pass.obtenerSede()) instanceof Miniestadio) {
					total += costoEntrada(pass.obtenerEspectaculo(), pass.obtenerFecha(), pass.obtenerSector());
				}
				if (sedes.get(pass.obtenerSede()) instanceof Teatro) {
					total += costoEntrada(pass.obtenerEspectaculo(), pass.obtenerFecha(), pass.obtenerSector());
				}
			}
		}
			return total;
	}
	
	@Override
	public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
		double total = 0.0;
		for (IEntrada entrada : listarEntradasEspectaculo(nombreEspectaculo)) {
			Entrada ticket = (Entrada) entrada;
			if(ticket.obtenerSede().equals(nombreSede)) {		
				if (ticket.obtenerSector().equals("CAMPO")) {
					total += costoEntrada(nombreEspectaculo, ticket.obtenerFecha());
				} else {					
					total += costoEntrada(nombreEspectaculo, ticket.obtenerFecha(), ticket.obtenerSector());
				}
			}
		}
		return total;
	}

	private boolean validarContrasenia(String email, String contrasenia) {
		return usuarios.containsKey(email) && usuarios.get(email).validarContrasenia(contrasenia);
	}
	
	private Map<String, Funcion> obtenerFunciones(String nombreEspectaculo){
		Map<String, Funcion> event = espectaculos.get(nombreEspectaculo).obtenerLista();
		return event;
	}
	
}