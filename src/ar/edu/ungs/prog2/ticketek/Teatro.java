package ar.edu.ungs.prog2.ticketek;
import java.util.HashMap;
import java.util.Map;
public class Teatro extends EstadiosConSecciones{
	protected Map<String, Sector> sectores;
    protected int asientosPorFila;

    public Teatro(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, String[] nombresSectores, int[] capacidades, int[] porcentajes) {
        super(nombre, direccion, capacidadMaxima);
        if (asientosPorFila <= 0 || nombresSectores == null || capacidades == null || porcentajes == null ||
                nombresSectores.length != capacidades.length || capacidades.length != porcentajes.length) {
            throw new RuntimeException("Datos de sectores no válidos");
        }
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

    @Override
    public boolean esNumerada() {
        return true;
    }

    public Sector getSector(String sector) {
        return sectores.get(sector);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(nombre + " (Teatro, Capacidad: " + capacidadMaxima + ", Sectores: ");
        for (Sector s : sectores.values()) {
            sb.append(s.getNombre()).append(", ");
        }
        return sb.toString().substring(0, sb.length() - 2) + ")";
    }
}
