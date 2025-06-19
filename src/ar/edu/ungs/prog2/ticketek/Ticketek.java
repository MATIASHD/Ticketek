package ar.edu.ungs.prog2.ticketek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Ticketek implements ITicketek {
	private Map<String, Sede> sedes;
	private Map<String, Funcion> funcion;
	private Set<String> espectaculos;
	private Map<String, Usuario> usuarios;
	private Map<Integer,IEntrada> entradas;
	private int codEntrada;
	public Ticketek() {
		this.sedes = new HashMap<String, Sede>();
        this.usuarios = new HashMap<String,Usuario>(); 
        this.espectaculos = new HashSet<String>();
        this.entradas = new HashMap<Integer,IEntrada>();
        this.funcion = new HashMap<String, Funcion>();
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
		if (this.espectaculos.contains(nombre)) {
			throw new RuntimeException("Espectáculo ya registrado");
		}
		espectaculos.add(nombre);
	}
	
	@Override
	public void agregarFuncion(String nombreEspectaculo, String fecha, String sede, double precioBase) {
		  for (Funcion event : funcion.values()) {
		        if (event.obtenerSede().equals(sede) && event.obtenerFecha().equals(fecha)) {
		            throw new RuntimeException("Ya existe un evento en esa sede y fecha");
		        }
		    }
		funcion.put(nombreEspectaculo, new Funcion(nombreEspectaculo, fecha, sede, precioBase));
	}

	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,int cantidadEntradas) {
		List<IEntrada> nuevasEntradas = new ArrayList<IEntrada>();
		Funcion show = funcion.get(nombreEspectaculo);
		if (nombreEspectaculo.isEmpty() || !espectaculos.contains(nombreEspectaculo)) {
			throw new RuntimeException("Espectáculo no registrado");
		}
		if(!validarContrasenia(email, contrasenia)) {
			throw new RuntimeException("Usuario no registrado o contraseña incorrecta");
		}
		/*if(!existeFuncion(nombreEspectaculo, fecha)) {
			throw new RuntimeException("Función no registrada");
		}*/
		for (int i = 0; i < cantidadEntradas; i++) {
			codEntrada++;
			IEntrada entrada = new Entrada(nombreEspectaculo, fecha, show.obtenerSede(), email, codEntrada,show.obtenerPrecioBase());
			nuevasEntradas.add(entrada);
			entradas.put(codEntrada,entrada);	
		}
		return nuevasEntradas;
	}
	
	private boolean existeFuncion(String nombreEspectaculo, String fecha) {
		for (Funcion show : funcion.values()) {
			if (show.obtenerNombre().equals(nombreEspectaculo) && new Fecha(show.obtenerFecha()).compararFecha(fecha)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			String sector, int[] asientos) {
		if (!espectaculos.contains(nombreEspectaculo) || nombreEspectaculo.isEmpty()) {
			throw new RuntimeException("Espectáculo no registrado");
		}
		if (!usuarios.containsKey(email)) {
			throw new RuntimeException("Usuario no registrado");
		}
		if (!usuarios.get(email).validarContrasenia(contrasenia)) {
			throw new RuntimeException("Contraseña incorrecta");
		}
		List<IEntrada> nuevasEntradas = new ArrayList<>();
		
		for (int i = 0; i < asientos.length; i++) {
			for (Funcion show : funcion.values()) {
				
					Sede sede = sedes.get(show.obtenerSede());
	                int fila = 1;
	                if (sede instanceof EstadiosConSecciones) {
	                    int asientosPorFila = ((EstadiosConSecciones) sede).obtenerAsientoPorFila();
	                    fila = ((asientos[i] - 1) / asientosPorFila) + 1;
	                }
	                codEntrada++;
					Entrada entrada = new Entrada(nombreEspectaculo, fecha, sector, asientos[i], show.obtenerSede(),fila, email, codEntrada, 0);
					nuevasEntradas.add(entrada);
					entradas.put(codEntrada, entrada);
				}
		}
		
		return nuevasEntradas;
	}
	
	@Override
	public String listarFunciones(String nombreEspectaculo) {
		if (nombreEspectaculo == null || nombreEspectaculo.isEmpty()) {
			throw new RuntimeException("Espectáculo no debe estar vacío");
		}
		StringBuilder sb = new StringBuilder();
	    for (Funcion show : funcion.values()) {
	       if (show.obtenerNombre().equals(nombreEspectaculo)) {
	            Sede sede = sedes.get(show.obtenerSede());
	            sb.append("- (").append(show.obtenerFecha()).append(") ").append(show.obtenerSede()).append(" - ");
	            sb.append(sede.estadosSectores());
	       }
	    }
	   
	    return sb.substring(0, sb.length() - 1).toString().trim();
	    
	}
	
	@Override
	public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) {
		if (!espectaculos.contains(nombreEspectaculo)) {
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
		if (!usuarios.get(email).validarContrasenia(contrasenia)) {
			throw new RuntimeException("Contraseña incorrecta");
		}
		List<IEntrada> resultado = new ArrayList<>();
		for (IEntrada entrada : entradas.values()) {
			Entrada ticket = (Entrada) entrada;
			if (ticket.validarUsuario(email)) {
				resultado.add(entrada);
		    }
		}
			return resultado; 
	}

	@Override
	public boolean anularEntrada(IEntrada entrada, String contrasenia) {
		Entrada ticket = (Entrada) entrada;
		if(entrada == null) {
			throw new RuntimeException("La entrada no es válida");
	    }
	    
	    if (!validarContrasenia(ticket.obtenerUsuario(), contrasenia)) {
	        throw new RuntimeException("La entrada ya está anulada");
	    }
	    ticket.anularEntrada();
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
	
	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha) {
		for (Funcion show : funcion.values()) {
			if(show.obtenerNombre().equals(nombreEspectaculo) && new Fecha(show.obtenerFecha()).compararFecha(fecha)) {
				return show.obtenerPrecioBase();
			}
		}
		throw new RuntimeException("Función no encontrada");
	}

	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha, String sector) {
	    for (Funcion show : funcion.values()) {
	        if (show.obtenerNombre().equals(nombreEspectaculo) /*&& new Fecha(show.obtenerFecha()).compararFecha(fecha)*/) {
	        	return funcion.get(nombreEspectaculo).obtenerPrecioBase() + (funcion.get(nombreEspectaculo).obtenerPrecioBase() * sedes.get(funcion.get(nombreEspectaculo).obtenerSede()).porcentajeDeRecargoSector(sector) / 100.0);
	        }
	    }
	    throw new IllegalArgumentException("Función o sector no encontrado");
	}
	
	@Override
	public double totalRecaudado(String nombreEspectaculo) {
		double total = 0.0;
		for (IEntrada entrada : listarEntradasEspectaculo(nombreEspectaculo)) {
			Entrada ticket = (Entrada) entrada;
			if(funcion.get(nombreEspectaculo) != null) {
				if (ticket.obtenerSector().equals("CAMPO")) {
					total += costoEntrada(nombreEspectaculo, ticket.obtenerFecha());
				} else {					
					total += costoEntrada(nombreEspectaculo, ticket.obtenerFecha(), ticket.obtenerSector());
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
			if(ticket.obtenerFuncion().equals(nombreEspectaculo) && ticket.obtenerSede().equals(nombreSede)) {
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
	
	}