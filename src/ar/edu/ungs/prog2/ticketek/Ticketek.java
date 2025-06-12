package ar.edu.ungs.prog2.ticketek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Ticketek implements ITicketek {
	private Map<String, Sede> sedes;
	private Map<String, Funcion> funcion;
	private Map<String, Espectaculo> espectaculos;
	private Map<String, Usuario> usuarios;
	private Map<String, Entrada> entradasRegistradas;
	private Map<String, IEntrada> entradasPorCodigo;
        private int contadorEntradas;
	
	public Ticketek() {
		this.sedes = new HashMap<>();
        this.usuarios = new HashMap<>();
        this.espectaculos = new HashMap<>();
        this.entradasRegistradas = new HashMap<>();
        this.funcion = new HashMap<>();
		contadorEntradas = 1;
	}


	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
		if (sedes.containsKey(nombre)) {
                    throw new IllegalArgumentException("Estadio ya registrada");
                }
		sedes.put(nombre, new Estadio(nombre, direccion, capacidadMaxima));
	}


	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
		if (sedes.containsKey(nombre)) {
                    throw new IllegalArgumentException("Teatro ya registrada");
                }
		sedes.put(nombre, new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional));
	}


	@Override
	public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
			int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad,
			int[] porcentajeAdicional) {
		if (sedes.containsKey(nombre)) {
                    throw new IllegalArgumentException("Miniestadio ya registrada");
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
		if (this.espectaculos.containsKey(nombre)) {
            throw new IllegalArgumentException("Espectáculo ya registrado");
        }
		espectaculos.put(nombre, new Espectaculo(nombre));
	}

	@Override
	public void agregarFuncion(String nombre, String fecha, String sede, double precio) {
		if (espectaculos.containsKey(nombre)) {
                    throw new IllegalArgumentException("Espectáculo ya registrado");
        }
		espectaculos.put(nombre, new Espectaculo(nombre));
	}


	@Override
	/*public void agregarFuncion(String nombreEspectaculo, String fecha, String sede, double precioBase) {
		Espectaculo show = espectaculos.get(nombreEspectaculo);
		Sede sedeObj = sedes.get(sede);
		if(show == null || sedeObj == null) {
			throw new IllegalArgumentException("Espectáculo o sede no existen");
		}
		show.agregarFuncion(nombreEspectaculo,fecha, sede, precioBase);
	}*/


	@Override
	public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			int cantidadEntradas) {
		Usuario user = usuarios.get(email);
		Espectaculo show = espectaculos.get(nombreEspectaculo);
		if (show == null || user == null || !user.validarContrasenia(contrasenia)) {
                    throw new IllegalArgumentException("Espectáculo, usuario o contraseña no válidos");
                }
        Funcion funcion = show.getFuncion(fecha);
        if (funcion == null || funcion.obtenerSede().esNumerada()) {
            throw new IllegalArgumentException("Función no existe o sede es numerada");
        }
        Estadio estadio = (Estadio) funcion.obtenerSede();
        List<IEntrada> entradas = new ArrayList<>();
        for (int i = 0; i < cantidadEntradas; i++) {
            if (estadio.asignarEntrada()) {
                String codEntrada = generarCodigo4Digitos();
                Entrada entrada = new Entrada(nombreEspectaculo, fecha, cantidadEntradas);
                entradas.add(entrada);
                funcion.agregarEntrada(entrada);
                user.agregarEntrada(entrada);
                //entradasPorCodigo.put(entrada.getCodEntrada(), entrada);
            } else {
                throw new IllegalArgumentException("No hay capacidad suficiente");
            }
        }
        return entradas;
	}


	@Override
	public void venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia,
			String sector, int[] asientos) {
		Usuario user = usuario.get(email);
		Espectaculo show = espectaculo.get(nombreEspectaculo);
		if (show == null || user == null || !user.validarContrasenia(contrasenia)) {
                    throw new IllegalArgumentException("Espectáculo, usuario o contraseña no válidos");
                }
        Funcion funcion = show.getFuncion(fecha);
        if (funcion == null || funcion.obtenerSede().esNumerada()) {
            throw new IllegalArgumentException("Función no existe o sede es numerada");
        }
        Teatro teatro = (Teatro) funcion.obtenerSede();
        double precio = calcularPrecioEntrada(funcion, sector);
        List<IEntrada> entradas = new ArrayList<>();
        for (int asiento : asientos) {
            if (teatro.asignarAsiento(sector, asiento)) {
                String codEntrada = generarCodigo4Digitos();
                Entrada entrada = new Entrada(nombreEspectaculo, fecha, sector, asientos);
                entradas.add(entrada);
                funcion.agregarEntrada(entrada);
                user.agregarEntrada(entrada);
                //entradasPorCodigo.put(entrada.getCodEntrada(), entrada);
            } else {
                throw new IllegalArgumentException("No hay capacidad suficiente");
            }
        }
        //return entradas;
	}


	@Override
	public String listarFunciones(String nombreEspectaculo) {
		Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo == null) {
            throw new IllegalArgumentException("Espectáculo no existe");
        }
        StringBuilder sb = new StringBuilder();
        for (Funcion funcion : espectaculo.ListaDeFunciones().values()) {
            Sede sede = funcion.obtenerSede();
            sb.append(" - (").append(funcion.obtenerFecha()).append(") ").append(sede.getNombre());
            if (sede instanceof Estadio) {
                Estadio estadio = (Estadio) sede;
                sb.append(" - ").append(estadio.getEntradasVendidas()).append("/").append(estadio.capacidadMaxima);
            } else {
                Teatro teatro = (Teatro) sede;
                for (Sector sector : teatro.sectores.values()) {
                    sb.append(" - ").append(sector.getNombre()).append(": ")
                            .append(sector.getEntradasVendidas()).append("/").append(sector.getCapacidad());
                }
            }
            sb.append("\n");
        }
        return sb.toString().trim();
	}


	@Override
	public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) {
		 Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo == null) {
            throw new IllegalArgumentException("Espectáculo no existe");
        }
        List<IEntrada> entradas = new ArrayList<>();
        for (Funcion funcion : espectaculo.ListaDeFunciones().values()) {
            entradas.addAll(funcion.getEntradasVendidas());
        }
        return entradas;
	}


	@Override
	public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
		List<IEntrada> listaEntradas = new ArrayList<>();
		
		for (Map.Entry<String, Entrada> ticket : entradasRegistradas.entrySet()) {
		    if (ticket.getKey().equals(email) && new Fecha(ticket.getValue().obtenerFecha()).esFutura() && usuario.get(email).validarContrasenia(contrasenia)) {
		    	listaEntradas.add(ticket.getValue());
		    } else {
		    	throw new IllegalArgumentException("No es valida la contraseña o el usuario");
		    }
		}
		return listaEntradas;
	}


	@Override
	public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
		Usuario usuario = usuarios.get(email);
        if (usuario == null || !usuario.validarContrasenia(contrasenia)) {
            throw new IllegalArgumentException("Usuario o contraseña no válidos");
        }
        return new ArrayList<>(usuario.getEntradas());
	}

	@Override
	public boolean anularEntrada(IEntrada entrada, String contrasenia) {	
		// TODO Auto-generated method stub
		if (entrada == null) {
			for (Map.Entry<String, Entrada> ticket : entradasRegistradas.entrySet()) {
				if(ticket.equals(entrada)) {
					ticket.getValue().anular();
					return true;
				}
			}
		} else {
			throw new NoSuchElementException("La entrada no se encontro o no es valida");
		}
		return false;
	}

	/*@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha, String sector, int asiento) {
		// TODO Auto-generated method stub
		if (entrada == null) {
			for (Map.Entry<String, Entrada> ticket : entradasRegistradas.entrySet()) {
				if(ticket.equals(entrada) && usuario.get(ticket.getKey()).validarContrasenia(contrasenia)) {
					ticket.getValue().cambiarFecha(fecha);
					ticket.getValue().cambiarSector(sector);
					ticket.getValue().cambiarAsiento(asiento);
					
					return ticket.getValue();
				}
			}
		} else {
			throw new NoSuchElementException("La entrada no se encontro o no es valida");
		}
		return null;
	}*/


	@Override
	public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha) {
		// TODO Auto-generated method stub
		if (entrada == null) {
			for (Map.Entry<String, Entrada> ticket : entradasRegistradas.entrySet()) {
				if(ticket.getValue().compararFecha(fecha)&& usuario.get(ticket.getKey()).validarContrasenia(contrasenia)) {
					if(new Fecha(ticket.getValue().obtenerFecha()).esPasada()) {
						throw new NoSuchElementException("La entrada vencio");
					}
					entradasRegistradas.put(usuario.get(ticket.getKey()).obtenerEmail(), ticket.getValue());
					return ticket.getValue();
				}
			}
		}
		return null;
	}


	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha) {
		// TODO Auto-generated method stub
		if (espectaculo.get(nombreEspectaculo) == null) {
			throw new NoSuchElementException("No se encuentra este espectaculo");
		}
		Funcion funcionActual = espectaculo.get(nombreEspectaculo).buscarLaFuncion(fecha);
		//Sede locacion = sede.get(espectaculo.get(nombreEspectaculo).buscarLaFuncion(fecha).obtenerSede());
		return funcionActual.precioBase();
	}


	@Override
	public double costoEntrada(String nombreEspectaculo, String fecha, String sector) {
		// TODO Auto-generated method stub
		if (espectaculo.get(nombreEspectaculo) == null) {
			throw new NoSuchElementException("No se encuentra este espectaculo");
		}
		Funcion funcionActual = espectaculo.get(nombreEspectaculo).buscarLaFuncion(fecha);
		EstadiosConSecciones locacion = (EstadiosConSecciones) sede.get(espectaculo.get(nombreEspectaculo).buscarLaFuncion(fecha).obtenerSede());
		double recargo = 0;
		if (locacion instanceof Miniestadio) {
			recargo = ((Miniestadio) locacion).adicional();
		}
		return recargo + funcionActual.precioBase() + funcionActual.precioBase() * locacion.porcentajeRecargo(sector)  ;
	}


	@Override
	public double totalRecaudado(String nombreEspectaculo) {
		// TODO Auto-generated method stub
		double total = 0;
		if (espectaculo.get(nombreEspectaculo) == null) {
			throw new NoSuchElementException("No se encuentra este espectaculo");
		}
		for (Map.Entry<String, Espectaculo> evento : espectaculo.entrySet()) {
			for (Map.Entry<String, Funcion> show : evento.getValue().ListaDeFunciones().entrySet()) {
				total += show.getValue().precioBase();
			}
		}
		return total;
	}


	@Override
	public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
		// TODO Auto-generated method stub
		double total = 0;
		if (espectaculo.get(nombreEspectaculo) == null) {
			throw new NoSuchElementException("No se encuentra este espectaculo");
		}
		for (Map.Entry<String, Espectaculo> evento : espectaculo.entrySet()) {
			for (Map.Entry<String, Funcion> show : evento.getValue().ListaDeFunciones().entrySet()) {
				if (sede.get(show.getValue().obtenerSede()).compararSede(nombreSede)) {
					total += show.getValue().precioBase();
					total += calcular(show.getValue().obtenerSede());					
				}
			}
		}
		return total;
	}

// Métodos auxiliares
	
	public double calcular(String ubicacion) {
		double total = 0;
		//Sede locacion =  sede.get(ubicacion);
		return total;
	}

private static String generarCodigo4Digitos(){
        Random r = new Random();
        int numero = 1000 + r.nextInt(8000);
        StringBuilder sb = new StringBuilder();
        sb.append(numero);
        return sb.toString();
    }
    private double calcularPrecioEntrada(Funcion funcion, String sector) {
        double precioBase = funcion.precioBase();
        Sede sede = funcion.obtenerSede();

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sistema Ticketek:\n");
        sb.append("Sedes registradas (").append(sedes.size()).append("):\n");
        for (Sede sede : sedes.values()) {
            sb.append(" - ").append(sede.toString()).append("\n");
        }
        sb.append("Espectáculos registrados (").append(espectaculos.size()).append("):\n");
        for (Espectaculo espectaculo : espectaculos.values()) {
            sb.append(" - ").append(espectaculo.toString()).append("\n");
        }
        sb.append("Usuarios registrados: ").append(usuarios.size()).append("\n");
        sb.append("Entradas vendidas: ").append(entradasPorCodigo.size()).append("\n");
        return sb.toString().trim();
    }	
	
	
}
