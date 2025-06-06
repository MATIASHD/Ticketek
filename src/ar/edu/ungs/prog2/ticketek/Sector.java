package ar.edu.ungs.prog2.ticketek;

import java.util.ArrayList;
import java.util.List;

public class Sector {
	private String nombre;
    private int capacidad;
    private int asientosPorFila;
    private int porcentajeAdicional;
    private boolean[] asientosOcupados;

    public Sector(String nombre, int capacidad, int asientosPorFila, int porcentajeAdicional) {
        if (nombre == null || nombre.isEmpty() || capacidad <= 0 || asientosPorFila <= 0 || porcentajeAdicional < 0) {
            throw new RuntimeException("Datos de sector no válidos");
        }
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.asientosPorFila = asientosPorFila;
        this.porcentajeAdicional = porcentajeAdicional;
        this.asientosOcupados = new boolean[capacidad];
    }

    public boolean asignarAsiento(int asiento) {
        if (asiento < 0 || asiento >= capacidad || asientosOcupados[asiento]) {
            return false;
        }
        asientosOcupados[asiento] = true;
        return true;
    }

    public void liberarAsiento(int asiento) {
        if (asiento >= 0 && asiento < capacidad) {
            asientosOcupados[asiento] = false;
        }
    }

    public int getEntradasVendidas() {
        int count = 0;
        for (boolean ocupado : asientosOcupados) {
            if (ocupado) count++;
        }
        return count;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int obtenerAdicional() {
        return porcentajeAdicional;
    }

    public int getFila(int asiento) {
        return asiento / asientosPorFila + 1;
    }

    public int getNumeroAsiento(int asiento) {
        return asiento % asientosPorFila + 1;
    }
}
