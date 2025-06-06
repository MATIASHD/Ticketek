package ar.edu.ungs.prog2.ticketek;
import java.util.HashMap;
import java.util.Map;
public class Miniestadio extends EstadiosConSecciones{
	private int cantidadPuestos;
    private double precioConsumicion;
    private Map<String, Sector> sectores;
    private int asientosPorFila;

    public Miniestadio(String nombre, String direccion, int capacidadMaxima, int asientosPorFila,
                       int cantidadPuestos, double precioConsumicion, String[] nombresSectores,
                       int[] capacidades, int[] porcentajes) {
        super(nombre, direccion, capacidadMaxima);
        if (asientosPorFila <= 0 || cantidadPuestos <= 0 || precioConsumicion < 0 ||
                nombresSectores == null || capacidades == null || porcentajes == null ||
                nombresSectores.length != capacidades.length || capacidades.length != porcentajes.length) {
            throw new RuntimeException("Datos de miniestadio no válidos");
        }
        this.cantidadPuestos = cantidadPuestos;
        this.precioConsumicion = precioConsumicion;
        this.asientosPorFila = asientosPorFila;
        this.sectores = new HashMap<>();
        int totalCapacidad = 0;
        for (int i = 0; i < nombresSectores.length; i++) {
            sectores.put(nombresSectores[i], new Sector(nombresSectores[i], capacidades[i], asientosPorFila, porcentajes[i]));
            totalCapacidad += capacidades[i];
        }
        if (totalCapacidad > capacidadMaxima) {
            throw new RuntimeException("Capacidad de sectores excede capacidad máxima");
        }
    }

    public boolean asignarAsiento(String sector, int asiento) {
        Sector s = sectores.get(sector);
        if (s == null) {
            throw new IllegalArgumentException("Sector no existe");
        }
        return s.asignarAsiento(asiento);
    }

    public void liberarAsiento(String sector, int asiento) {
        Sector s = sectores.get(sector);
        if (s != null) {
            s.liberarAsiento(asiento);
        }
    }

    public Sector getSector(String sector) {
        return sectores.get(sector);
    }

    public double getPrecioConsumicion() {
        return precioConsumicion;
    }

    @Override
    public boolean esNumerada() {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(nombre + " (MiniEstadio, Capacidad: " + capacidadMaxima +
                ", Puestos: " + cantidadPuestos + ", Sectores: ");
        for (Sector s : sectores.values()) {
            sb.append(s.getNombre()).append(", ");
        }
        return sb.toString().substring(0, sb.length() - 2) + ")";
    }
}
