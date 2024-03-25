package P6b;

public class ElectricCarSpace extends CarSpace { // Clase hija de CarSpace para incluir un cargador

	// Atributos

	private ElectricCharger charger; // el cargador, que es un objeto de la Clase ElectricCharger

	// Metodo constructor que inicializa los atributos de CarSpace y el objeto
	// charger.

	public ElectricCarSpace(Coordinate coordinate, String plate, String hora, ElectricCharger charger) {
		super(coordinate, plate, hora);
		this.charger = charger;
	}

}
