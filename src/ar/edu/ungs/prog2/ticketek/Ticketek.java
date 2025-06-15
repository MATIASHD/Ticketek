package ar.edu.ungs.prog2.ticketek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class Ticketek implements ITicketek {
	private Map<String, Sede> sedes;
	private Map<String, Funcion> funcion;
	private Set<String> espectaculos;
	private Map<String, Usuario> usuarios;
	private Map<String, List<Entrada>> entradas;
	
    
	
	public Ticketek() {
		this.sedes = new HashMap<String, Sede>();
        this.usuarios = new HashMap<>(); 
        this.espectaculos = new HashSet<>();
        this.entradas = new HashMap<String, List<Entrada>>();
        this.funcion = new HashMap<String, Funcion>();
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
		if (sedes.containsKey(nombre)) {
			throw new IllegalArgumentException("Sede ya registrada");
		}
		sedes.put(nombre, new Estadio(nombre, direccion, capacidadMaxima));
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		if (sedes.containsKey(nombre)) {
			throw new IllegalArgumentException("Teatro ya registrado");
		}
		sedes.put(nombre, new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional));
	}

	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {
		if (sedes.containsKey(nombre)) {
			throw new IllegalArgumentException("Miniestadio ya registrado");
		}
		sedes.put(nombre, new Miniestadio(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos, precioConsumicion, sectores, capacidad, porcentajeAdicional));
	}

	@Override
	public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
		if (usuarios.containsKey(email)) {
			throw new IllegalArgumentException("Usuario ya registrado");
		}
		usuarios.put(email, new Usuario(email, nombre, apellido, contrasenia));
	}

	@Override
	public void registrarEspectaculo(String nombre) {
		if (this.espectaculos.contains(nombre)) {
			throw new IllegalArgumentException("Espectáculo ya registrado");
		}
		espectaculos.add(nombre);		
	}

	@Override
	public void agregarFuncion(String nombreEspectaculo, String fecha, String sede, double precioBase) {
		
		if(!this.espectaculos.contains(nombreEspectaculo)) {
			throw new IllegalArgumentException("Espectáculo no registrado");
		}
		if(estaLaFecha(fecha)) {			
			throw new IllegalArgumentException("Fecha no válida");
		}
		funcion.put(fecha, new Funcion(nombreEspectaculo, fecha, sede, precioBase));
	}
	
	private boolean estaLaFecha(String fecha) {
		boolean esta = false;
		for(String fechaClave : funcion.keySet()) {
		    if (fechaClave.equals(fecha)) {
		    	esta &= true;
		    }
		}
		return esta;
	}
	private boolean estaEspectaculo(String nombreEspectaculo) {
		return espectaculos.contains(nombreEspectaculo);
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,int cantidadEntradas) {
		if (!estaEspectaculo(nombreEspectaculo)) {
			throw new IllegalArgumentException("Espectáculo no registrado");
		}
		if (!usuarios.containsKey(email)) {
			throw new IllegalArgumentException("Usuario no registrado");
		}
		if (!usuarios.get(email).validarContrasenia(contrasenia)) {
			throw new IllegalArgumentException("Contraseña incorrecta");
		}
		List<IEntrada> nuevasEntradas = new ArrayList<>();
		for (int i = 1; i <= cantidadEntradas; i++) {
			Entrada entrada = new Entrada(nombreEspectaculo, fecha, i);
				nuevasEntradas.add(entrada);
		    	entradas.computeIfAbsent(email, k -> new ArrayList<>()).add(entrada);
		    }
		return nuevasEntradas;
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			String sector, int[] asientos) {
		if (!estaEspectaculo(nombreEspectaculo)) {
			throw new IllegalArgumentException("Espectáculo no registrado");
		}
		if (!usuarios.containsKey(email)) {
			throw new IllegalArgumentException("Usuario no registrado");
		}
		if (!usuarios.get(email).validarContrasenia(contrasenia)) {
			throw new IllegalArgumentException("Contraseña incorrecta");
		}
		List<IEntrada> nuevasEntradas = new ArrayList<>();
		Entrada entrada = new Entrada(nombreEspectaculo, fecha, sector, asientos);
			nuevasEntradas.add(entrada);
		    entradas.computeIfAbsent(email, k -> new ArrayList<>()).add(entrada);  
		return nuevasEntradas;
	}

	@Override
	public String listarFunciones(String nombreEspectaculo) {
		if (this.espectaculos.contains(nombreEspectaculo)) {
			throw new IllegalArgumentException("Espectáculo ya registrado");
		}
		
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Funcion> entry : funcion.entrySet()) {
			if(entry.getValue().obtenerNombre().equals(nombreEspectaculo)) {
				sb.append("- (").append(entry.getValue().obtenerFecha()).append(") ");
				sb.append(entry.getValue().obtenerSede()).append(" - ");
				Sede sede = sedes.get(entry.getValue().obtenerSede());
		    String fecha = entry.getKey();
		    Funcion func = entry.getValue();
		    // Use fecha and func as needed
			}
		}
		// - (31/07/2025) Teatro Colón - Platea VIP: 30/50 | Platea Común: 60/70 | Platea Baja: 0/70 | Platea Alta: 50/50
		
		private Sede buscarUbicacion(String nombreSede) {
			return sedes.get(nombreSede);
		}
		
		
		for (Funcion funcion : funciones.values()) {
			Sede sede = sedes.get(funcion.obtenerSede());
			if (sede == null) {
				throw new IllegalArgumentException("Sede no registrada");
			}
			sb.append(" - (").append(funcion.obtenerFecha()).append(") ").append(sede.obtenerNombre());
			if (sede instanceof Estadio) {
				Estadio estadio = (Estadio) sede;
				sb.append(" - ").append(estadio.entradasVendidas()).append("/").append(estadio.obtenerCapcidadMaxima());
			} else if (sede instanceof Teatro) {
				Teatro teatro = (Teatro) sede;
				for (String sector : teatro.obtenerSector()) {
					sb.append(" - ").append(sector).append(": ");
					for (int i = 0; i < teatro.obtenerCapacidadPorSector().length; i++) {
						sb.append(teatro.entradasVendidas(i)).append(" | ");
					}
				}
			} else if (sede instanceof Miniestadio) {
				Miniestadio miniestadio = (Miniestadio) sede;
				for (String sector : miniestadio.obtenerSector()) {
					sb.append(" - ").append(sector).append(": ");
					for (int i = 0; i < miniestadio.obtenerCapacidadPorSector().length; i++) {
						sb.append(miniestadio.entradasVendidas(i)).append(" | ");
					}
				}
			}
			sb.append("\n");
		}
		return sb.toString().trim();
	}

	@Override
	public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) {
		List<IEntrada> entradasVendidas = new ArrayList<>();
		if (!espectaculos.contains(nombreEspectaculo)) {
			throw new IllegalArgumentException("Espectáculo no registrado");
		}
		for(Map.Entry<String, Entrada> ticket : entradas.entrySet()) {
			if (ticket.getValue().obtenerNombreEspectaculo().equals(nombreEspectaculo)) {
				entradasVendidas.add(ticket.getValue());
			}
		}
		return entradasVendidas;
	}

	@Override
	public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
		if (!usuarios.containsKey(email)) {
			throw new IllegalArgumentException("Usuario no registrado");
		}
		if (!usuarios.get(email).validarContrasenia(contrasenia)) {
			throw new IllegalArgumentException("Contraseña incorrecta");
		}
		List<IEntrada> listaEntradas = new ArrayList<>();
		for(Map.Entry<String, Entrada> ticket : entradas.entrySet()) {
			if (ticket.getKey().equals(email)){
				if(ticket.getValue().obtenerFecha().esFutura()) {
					listaEntradas.add(ticket.getValue());
				}
			}
		}
		return listaEntradas;
	}

	@Override
	public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
		if (!usuarios.containsKey(email)) {
			throw new IllegalArgumentException("Usuario no registrado");
		}
		if (!usuarios.get(email).validarContrasenia(contrasenia)) {
			throw new IllegalArgumentException("Contraseña incorrecta");
		}
		List<IEntrada> listaEntradas = new ArrayList<>();
		Iterator<Map.Entry<String, Entrada>> ticket = entradas.entrySet().iterator();
		while (ticket.hasNext()) {
			Map.Entry<String, Entrada> entry = ticket.next();
			if (entry.getKey().equals(email)) {
				listaEntradas.add(entry.getValue());
			}
		}
		return listaEntradas;
	}

	@Override
	public boolean anularEntrada(IEntrada entrada, String contrasenia) {
		Entrada ticket = (Entrada) entrada;
		if (ticket == null) {
			throw new NoSuchElementException("La entrada no se encontró o no es válida");
		}
		if (!usuarios.containsKey(contrasenia)) {
			throw new IllegalArgumentException("Usuario no registrado");
		}
		return ticket.anularEntrada();
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha, String sector, int asiento) {
		Entrada ticket = (Entrada) entrada;
		if (!usuarios.containsKey(contrasenia)) {
			throw new IllegalArgumentException("Usuario no registrado");
		}
		if (!usuarios.get(contrasenia).validarContrasenia(contrasenia)) {
			throw new IllegalArgumentException("Contraseña incorrecta");
		}
		if (ticket.obtenerFecha().compararFecha(fecha)) {
			throw new IllegalArgumentException("Fecha no válida");
		}
		
		ticket.cambiarAsiento(asiento);
		ticket.cambiarSector(sector);
		return ticket;
	}

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha) {
		if (entrada == null) {
			throw new NoSuchElementException("La entrada no se encontró o no es válida");
		}
		if (!usuarios.get(contrasenia).validarContrasenia(contrasenia)) {
			throw new IllegalArgumentException("Contraseña incorrecta");
		}
		Entrada ticket = (Entrada) entrada;
		Iterator<Map.Entry<String, Entrada>> ticketActual = entradas.entrySet().iterator();
		while (ticketActual.hasNext()) {
			Map.Entry<String, Entrada> entry = ticketActual.next();
			if (entry.getValue().equals(entrada)) {
				entry.setValue(ticket);
			}
		}
		return ticket;
	}

	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha) {
		if (!espectaculos.contains(nombreEspectaculo)) {
			throw new IllegalArgumentException("Espectáculo no registrado");
		}
		/*if (!espectaculos.get(nombreEspectaculo).estaLaFuncion(fecha)) {
			throw new IllegalArgumentException("Función no registrada");
		}
		return espectaculos.get(nombreEspectaculo).buscarLaFuncion(fecha).costoEntrada();*/
		return 1.0;
	}

	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha, String sector) {
		return 0;
	}

	@Override
	public double totalRecaudado(String nombreEspectaculo) {
		return 0;
	}

	@Override
	public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
		return 0;
	}
	
}
