package ar.edu.ungs.prog2.ticketek;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String email;
	private String nombre;
	private String apellido;
	private String contrasenia;
	private List<IEntrada> entradas;
	
	public Usuario(String email, String nombre, String apellido, String contrasenia) {
		if (email == null || email.isEmpty() || nombre == null || nombre.isEmpty() ||
                    apellido == null || apellido.isEmpty() || contrasenia == null || contrasenia.isEmpty()) {
                    throw new IllegalArgumentException("Datos de usuario no válidos");
        }
		this.email = email;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contrasenia = contrasenia;
		this.entradas = new ArrayList<>();
	}
	
	public String obtenerNombreCompleto() {
			return this.nombre + " " + this.apellido;
	}
	public String obtenerEmail() {
	        return this.email;
	}
	public String obtenerContrasenia() {
	        return this.contrasenia;
	}
	public List<IEntrada> obtenerEntradas() {
	        return this.entradas;
	}
	public boolean validarContrasenia(String contrasenia) {
        return this.contrasenia.equals(contrasenia);
    }
	public void agregarEntrada(IEntrada entrada) {
		this.entradas.add(entrada);
	}


}
	
