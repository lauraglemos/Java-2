package P6b;

public class CarSpace implements Comparable<CarSpace> { // Clase que crea las plazas del parking.

	private Coordinate coordinate; // coordenadas de la plaza
	private String plate = null; // matricula del coche aparcado en una determinada plaza (tamaño 7)
	private String entryTime = null; // hora en la que entra el coche al parking.

	// Metodo constructor que crea una plaza del parking a partir de la coordenada,
	// la matricula y la hora de entrada del coche.

	public CarSpace(Coordinate coordinate, String plate, String entryTime) {
		this.coordinate = coordinate;
		this.plate = plate;
		this.entryTime = entryTime;

	}

	// METODOS

	// Metodo que muestra los atributos de la plaza.

	public String toString() {
		if (plate != null) {

			return coordinate + ";" + plate + ";" + entryTime;

		} else {

			return coordinate.toString();
		}
	}

	// Metodo que compara la plaza y otra que se pasa como parametro y las ordena.

	public int compareTo(CarSpace plazaCoche) {
		if (this.plate != plazaCoche.getPlate())
			return -1;

		else if (this.plate == plazaCoche.getPlate()) {

			return this.coordinate.compareTo(plazaCoche.getCoordinate());
		}

		else
			return 1;
	}

	// Getters y setters

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public String getPlate() {

		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

}
