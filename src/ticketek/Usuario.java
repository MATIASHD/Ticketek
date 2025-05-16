package ticketek;

import java.util.ArrayList;
import java.util.Iterator;

public class Usuario {
	private String email;
	private String nombre;
	private String apellido;
	private String contrasenia;
	private ArrayList<Entrada> entradas;
	
	public Usuario(String email, String nombre, String apellido, String contrasenia) {
		this.email = email;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contrasenia = contrasenia;
		this.entradas = new ArrayList<>();
	}
	
	public void agregarEntradaALaLista(Entrada entrada) {
		this.entradas.add(entrada);
	}
	
	public boolean validarContrasenia(String contrasenia) {	
		return this.contrasenia == contrasenia;
	}
	
	public Entrada[] obtenerTodasLasEntradas() {
		return this.entradas.toArray(new Entrada[0]);
	}
	
	public Entrada[] obtenerEntradasVencidas() {
		//definir como tratar la fecha
	}
	
	public Entrada[] obtenerEntradasFuturas() {
		//definir como tratar la fecha
		
	}
	
	public void borrarEntrada(String codEntrada) {
		Iterator<Entrada> entrada = this.entradas.iterator();
		while(entrada.hasNext()) {
			Entrada boleto = entrada.next();
			if(boleto.compararCodEntrada(codEntrada)) {
				entrada.remove();
			}
		}
	}
	
	public int entradasTotales() {
		//Cant de entrada o precio de la entrada?
		return this.entradas.size();
	}
	
	public void remplazarEntradas(String codEntrada, Entrada nvaEntrada) {
		for (int i = 0; i < this.entradas.size() ; i++) {
			if(entradas.get(i).compararCodEntrada(codEntrada)) {
				this.entradas.set(i, nvaEntrada);
			}
		}
	}
	
	public void anularEntrada(String codEntrada) {
		this.borrarEntrada(codEntrada);
	}
	
}
