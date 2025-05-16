package ticketek;

public class Teatro extends Sede{
	private double precioBase;
	private int recargoPlateaVIP;
	private int recargoPlateaComun;
	private int recargoPlateaBaja;
	private int recargaPlateaAlta;
	private int asiento;

	public Teatro(String nombre, String direccion, int capacidadMaxima, int sector, double precioBase, int recargoPlateaVIP,int recargoPlateaComun,
			int recargoPlateaBaja, int recargoPlateaAlta, int asiento) {
		super(nombre, direccion, capacidadMaxima, sector);
		this.precioBase = precioBase;
		this.recargoPlateaVIP = recargoPlateaVIP;
		this.recargoPlateaComun = recargoPlateaComun;
		this.recargoPlateaBaja = recargoPlateaBaja;
		this.recargaPlateaAlta = recargoPlateaAlta;
		this.asiento = asiento;
		
	}
	
	public double precioDeEntrada() {
		return 1; //VER COMO SEGUIR
	}
	
	public int getAsiento() {
		return this.asiento;
	}

}
