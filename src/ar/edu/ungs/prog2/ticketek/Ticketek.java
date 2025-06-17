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
	private Map<IEntrada, String> entradas = new HashMap<>();
	public Ticketek() {
		this.sedes = new HashMap<String, Sede>();
        this.usuarios = new HashMap<>(); 
        this.espectaculos = new HashSet<>();
        this.entradas = new HashMap<IEntrada,String>();
        this.funcion = new HashMap<String, Funcion>();
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
//PROBLEMA CON LA FUNCION.
	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,int cantidadEntradas) {
		List<IEntrada> nuevasEntradas = new ArrayList<IEntrada>();
		if (nombreEspectaculo.isEmpty() || !espectaculos.contains(nombreEspectaculo)) {
			throw new RuntimeException("Espectáculo no registrado");
		}
		if (!usuarios.containsKey(email)) {
			throw new RuntimeException("Usuario no registrado");
		}
		if (!usuarios.get(email).validarContrasenia(contrasenia)) {
			throw new RuntimeException("Contraseña incorrecta");
		}
		Funcion show = existeFuncion(nombreEspectaculo, fecha);
		if(show !=null) {
			for (int i = 0; i < cantidadEntradas; i++) {
				IEntrada entrada = new Entrada(nombreEspectaculo, fecha, show.obtenerSede());
				
				nuevasEntradas.add(entrada);
				entradas.put(entrada, email);	
			}
		}
		return nuevasEntradas;
	}
	
	private Funcion existeFuncion(String nombreEspectaculo, String fecha) {
		for (Funcion show : funcion.values()) {
			if (show.obtenerNombre().equals(nombreEspectaculo) && new Fecha(show.obtenerFecha()).compararFecha(fecha)) {
				return show;
			}
		}
		return null;
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
					Entrada entrada = new Entrada(nombreEspectaculo, fecha, sector, asientos[i], show.obtenerSede(),fila);
					nuevasEntradas.add(entrada);
					entradas.put(entrada, email);
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
	            if (sede instanceof Miniestadio) {
	                sb.append(((Miniestadio) sede).estadosSectores());
	            }
	            if (sede instanceof Teatro) {
	                sb.append(((Teatro) sede).estadosSectores());
	            }
	            if (sede instanceof Estadio) {
	                sb.append(((Estadio) sede).obtenerSector());
	            }
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
		for (IEntrada entrada : entradas.keySet()) {
			Entrada ticket = (Entrada) entrada;
				if (ticket.obtenerEspectaculo().equals(nombreEspectaculo) && ticket.estadoEntrada()) {
					entradasVendidas.add(entrada);
				}
			}
		return entradasVendidas;
	}
	
	@Override
	public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
		if (!usuarios.containsKey(email)) {
			throw new RuntimeException("Usuario no registrado");
		}
		if (!usuarios.get(email).validarContrasenia(contrasenia)) {
			throw new RuntimeException("Contraseña incorrecta");
		}
		List<IEntrada> entradasVendidas = new ArrayList<>();
		for (IEntrada entrada : entradas.keySet()) {
			Entrada ticket = (Entrada) entrada;
			if (new Fecha(ticket.obtenerFecha()).esFutura() && ticket.estadoEntrada()) {
				entradasVendidas.add(entrada);
			}
		}
		return entradasVendidas;
		
	}
	

	
	@Override
	public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
		if (!usuarios.containsKey(email)) {
			throw new RuntimeException("Usuario no registrado");
		}
		if (!usuarios.get(email).validarContrasenia(contrasenia)) {
			throw new RuntimeException("Contraseña incorrecta");
		}
		List<IEntrada> resultado = new ArrayList<>();
			for (Map.Entry<IEntrada, String> entry : entradas.entrySet()) {
				if (entry.getValue().equals(email)) {
					resultado.add(entry.getKey());
		        }
		    }
			return resultado; 
	}

	@Override
	public boolean anularEntrada(IEntrada entrada, String contrasenia) {
	    if (entrada == null) {
	        throw new RuntimeException("La entrada no es válida");
	    }
	    String email = entradas.get(entrada); // uses equals/hashCode
	    if (email == null) {
	        throw new RuntimeException("La entrada no pertenece a ningún usuario");
	    }
	    Usuario usuario = usuarios.get(email);
	    if (usuario == null) {
	        throw new RuntimeException("Usuario no encontrado");
	    }
	    if (!usuario.validarContrasenia(contrasenia)) {
	        throw new RuntimeException("Contraseña incorrecta");
	    }
	    Entrada ticket = (Entrada) entrada;
	    if (!ticket.estadoEntrada()) {
	        throw new RuntimeException("La entrada ya está anulada");
	    }
	    if (new Fecha(ticket.obtenerFecha()).esPasada()) {
	        return false;
	    }
	    ticket.anularEntrada();
	    entradas.remove(entrada);
	    return true;
	}


	/*----------------------------------------------------------------------------------------*/

	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha, String sector, int asiento) {
		if (entrada == null) {
	        throw new RuntimeException("La entrada no se encontró o no es válida");
	    }
		String email = entradas.get(entrada); // uses equals/hashCode
		if (email == null) {
			throw new RuntimeException("La entrada no pertenece a ningún usuario");
		}
		Usuario usuario = usuarios.get(email);
		if (usuario == null) {
			throw new RuntimeException("Usuario no encontrado");
		}
		if (!usuario.validarContrasenia(contrasenia)) {
		    throw new RuntimeException("Contraseña incorrecta");
		}
		
	    Entrada ticket = (Entrada) entrada;
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
		if (entrada == null) {
	        throw new RuntimeException("La entrada no se encontró o no es válida");
	    }
		String email = entradas.get(entrada); // uses equals/hashCode
		if (email == null) {
			throw new RuntimeException("La entrada no pertenece a ningún usuario");
		}
		Usuario usuario = usuarios.get(email);
		if (usuario == null) {
			throw new RuntimeException("Usuario no encontrado");
		}
		if (!usuario.validarContrasenia(contrasenia)) {
		    throw new RuntimeException("Contraseña incorrecta");
		}
		
	    Entrada ticket = (Entrada) entrada;
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
	        if (show.obtenerNombre().equals(nombreEspectaculo) && new Fecha(show.obtenerFecha()).compararFecha(fecha)) {
	        	double precioBase = show.obtenerPrecioBase();
	            int porcentajeAdicional =  buscarUnTeatroOMiniestadio(show.obtenerNombre()).obtenerPorcentajeAdicionalSector(sector);
	            System.out.println("Porcentaje adicional: " + precioBase + (precioBase * porcentajeAdicional / 100.0));
	            return precioBase + (precioBase * porcentajeAdicional / 100.0);
	        }
	    }
	    throw new IllegalArgumentException("Función o sector no encontrado");
	}
	
	public EstadiosConSecciones buscarUnTeatroOMiniestadio(String sede) {
		for (Sede predio : sedes.values()) {
			if (sede == null) {
		        throw new RuntimeException("Sede no encontrada");
		    }
			if(predio.obtenerNombre().equals(sede)) {
				if (predio instanceof Teatro) {
					return (EstadiosConSecciones) predio;
				}
				if (predio instanceof Miniestadio) {
					return (EstadiosConSecciones) predio;
				}
			}
		}
		throw new RuntimeException("No se encontro");
	}

	@Override
	public double totalRecaudado(String nombreEspectaculo) {
	    double precio = 0.0;
	    List<Entrada> ticket = new ArrayList<>();
	    double preciobase = 0.0;
	    for (Map.Entry<String, Funcion> evento : funcion.entrySet()) {
	        preciobase = evento.getValue().obtenerPrecioBase();
	    }
	    
	    for (Map.Entry<String, List<Entrada>> listaEntradas : entradas.entrySet()) {
	    	List<Entrada> lista = listaEntradas.getValue();
	        for (Entrada entrada : lista) {
	        	if(entrada.estaActiva()){
	        		if(entrada.obtenerEspectaculo().equals(nombreEspectaculo)) {
	        			ticket.add(entrada);
	        		}	              
	            }
	        }
	    }

	    for (Entrada entrada : ticket) {
	    	Sede predio = sedes.get(entrada.obtenerSector());
	    	 if (predio instanceof Miniestadio) {
                 Miniestadio mini = (Miniestadio) predio;
                 double adicional = mini.obtenerPrecioConsumicion();
                 precio += adicional;
                 int porcentaje = mini.porcentajeRecargo(entrada.obtenerSector());
                 precio += preciobase * porcentaje / 100.0;
             } else if (predio instanceof Teatro) {
                 Teatro teatro = (Teatro) predio;
                 int porcentaje = teatro.porcentajeRecargo(entrada.obtenerSector());
                 precio += precio * porcentaje / 100.0;
             } else if (predio instanceof Estadio) {
				 precio += preciobase;
			 }
	       
	    }
	    return precio;
	}

	@Override
	public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
	    double precio = 0.0;
	    double preciobase = 0.0;
	    for (Map.Entry<String, Funcion> evento : funcion.entrySet()) {
	        preciobase = evento.getValue().obtenerPrecioBase();
	    }
		List<Entrada> ticket = new ArrayList<>();
		for (Map.Entry<String, List<Entrada>> listaEntradas : entradas.entrySet()) {
			List<Entrada> lista = listaEntradas.getValue();
			for (Entrada entrada : lista) {
				if(entrada.estaActiva()){
					if(entrada.obtenerEspectaculo().equals(nombreEspectaculo) && entrada.obtenerSector().equals(nombreSede)) {
						ticket.add(entrada);
					}	              
				}
			}
		}
	    for (Entrada entrada : ticket) {
		  	Sede predio = sedes.get(entrada.obtenerSector());
		   	if (predio instanceof Miniestadio) {
		   		 Miniestadio mini = (Miniestadio) predio;
	             double adicional = mini.obtenerPrecioConsumicion();
	             precio += adicional;
	             int porcentaje = mini.porcentajeRecargo(entrada.obtenerSector());
	             precio += preciobase * porcentaje / 100.0;
	        } else if (predio instanceof Teatro) {
	             Teatro teatro = (Teatro) predio;
	             int porcentaje = teatro.porcentajeRecargo(entrada.obtenerSector());
	             precio += precio * porcentaje / 100.0;
	        } else if (predio instanceof Estadio) {
	        	 precio += preciobase;
	        }   
		}
		    return precio;
	    }
	}
