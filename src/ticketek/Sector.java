package ticketek;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sector {
	private String nombre;
	private int capacidadMaxima;
	private List<Integer> asientosOcupados;
	private double porcentajeAdicional;
	
	private Set<Integer> asientosDisponibles;
	private int entradasVendidas;
	
	//Estadio/miniestadio
	public Sector(String nombre, int capacidadMaxima) {
		this.nombre = nombre;
		this.capacidadMaxima = capacidadMaxima;
		this.asientosOcupados = new ArrayList<>();
		
		this.asientosDisponibles = new HashSet<>();
        for (int i = 1; i <= capacidadMaxima; i++) {
            asientosDisponibles.add(i);
        }
        this.entradasVendidas = 0;
	}
	
	//para teatros
	public Sector(String nombre, int capacidadMaxima, double porcentajeAdicional) {
		this.nombre = nombre;
		this.capacidadMaxima = capacidadMaxima;
		this.asientosOcupados = new ArrayList<>();
		this.porcentajeAdicional = porcentajeAdicional;
	}
	
	public void reservarAsiento(int asiento) {
        if (!asientosDisponibles.remove(asiento)) {
            throw new IllegalArgumentException("Asiento no disponible");
        }
        entradasVendidas++;
    }
	
	public int CantidadAsientosDisponibles() {
		return this.capacidadMaxima - this.asientosOcupados.size();
	}
	public Set<Integer> getAsientosDisponibles() {
        return asientosDisponibles;
    }
}
