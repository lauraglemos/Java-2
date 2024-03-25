package P6b;

public class CombustionCar extends Car { // Clase hija de Car que genera los coches de combustion.

	// Atributos de instancia

	private int mechanicalPower; // la potencia mecanica del coche

	// Atributos estáticos

	public static final int MECHANICALPOWER_MIN = 60;
	public static final int MECHANICALPOWER_MAX = 500;

	// METODOS

	// Metodo constructor vacio

	public CombustionCar() {

	}

	// Metodo constructor que crea un coche de combustion a partir de su matricula,
	// su fabricante y su potencia mecanica.

	public CombustionCar(String plate, String manufacturer, int mechanicalPower) {

		super(plate, manufacturer);

		this.mechanicalPower = mechanicalPower;

	}

	// Metodo que muestra los atributos del coche.

	public String toString() {

		return "C;" + plate + ";" + manufacturer + ";" + mechanicalPower;
	}

	// Metodo que devuelve la potencia total del coche (en este caso la potencia
	// mecanica).
	public int getTotalPower() {

		return mechanicalPower;
	}

	// Metodo boolean que devuelve false si la potencia no esta dentro de los
	// limites.
	public static boolean isValidMechanicalPower(int mechanicalPower) {

		if (mechanicalPower < MECHANICALPOWER_MIN || mechanicalPower > MECHANICALPOWER_MAX) {
			return false;
		}
		return true;
	}

	// getters and setters

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getMechanicalPower() {
		return mechanicalPower;
	}

	public void setMechanicalPower(int mechanicalPower) {
		this.mechanicalPower = mechanicalPower;
	}

}
