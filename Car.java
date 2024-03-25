package P6b;

public abstract class Car implements Comparable<Car> { // Clase abstracta que genera un coche, que puede ser electrico,
														// hibrido o de combustion.

	// Atributos comunes

	protected String plate; // matricula del coche
	protected String manufacturer; // fabricante del coche

	public static String PLATE_FORMAT = "DDDDLLL"; // formato que debe tener la matricula

	//METODOS
	
	// Metodo constructor vacio

	public Car() {

	}

	// Metodo constructor que crea un coche a partir de su matricula y su
	// fabricante.

	public Car(String plate, String manufacturer) {

		this.plate = plate;
		this.manufacturer = manufacturer;

	}

	// Metodo abstracto que devuelve la potencia total del coche.
	
	public abstract int getTotalPower();

	// Metodo abstracto que se usa para mostrar los atributos de los coches.
	
	public abstract String toString();

	// Metodo boolean que comprueba que la matricula de un coche tenga el formato
	// correcto para que sea valida.
	
	public static boolean isValidPlate(String plate) {

		if (plate.length() != 7) {
			return false;
		}

		for (int i = 0; i < 4; i++) {
			if (plate.charAt(i) < '0' || plate.charAt(i) > '9') {
				return false;
			}
		}
		for (int i = 4; i < 7; i++) {
			if (plate.charAt(i) < 'A' || plate.charAt(i) > 'Z') {
				return false;
			}
		}

		return true;
	}

	// Metodo que compara la matricula y la del coche que se pasa con parametro y
	// las ordena de menor a mayor, para ello pasamos los numeros que forman la
	// matricula y los convertimos en enteros, para asi ver cual es mayor o menor.
	
	public int compareTo(Car coche) {

		int plateInt = Integer.parseInt(this.plate.substring(0, 3));
		int cochePlateInt = Integer.parseInt(coche.getPlate().substring(0, 3));
		if (plateInt < cochePlateInt)
			return -1;

		else if (plateInt == cochePlateInt)
			return 0;

		else
			return 1;

	}

	// getters and setters

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

}
