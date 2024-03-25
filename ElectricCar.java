package P6b;

public class ElectricCar extends Car { // Clase hija de Car que genera los coches electricos.

	// Atributos de instancia

	private int electricPower; // la potencia electrica del coche
	private float batteryCharge; // el porcentaje de carga de la bateria del coche

	// Atributos estáticos

	public static final int ELECTRICPOWER_MIN = 50;
	public static final int ELECTRICPOWER_MAX = 800;
	public static float BATTERYCHARGE_MIN = 0.0f;
	public static float BATTERYCHARGE_MAX = 100.0f;

	public static float BATTERY_CAPACITY = 100.0f; // la capacidad de la bateria del coche en kWh

	// METODOS

	// Metodo constructor vacio

	public ElectricCar() {

	}

	// Metodo constructor que crea un coche electrico a partir de su matricula, su
	// fabricante, su potencia y su porcentaje de bateria.

	public ElectricCar(String plate, String manufacturer, int electricPower, float batteryCharge) {

		super(plate, manufacturer);

		this.electricPower = electricPower;
		this.batteryCharge = batteryCharge;

	}

	// Metodo que muestra los atributos del coche.

	public String toString() {

		String bateriaString = Float.toString(batteryCharge);

		bateriaString = bateriaString.replace(".", ",");
		return "E;" + plate + ";" + manufacturer + ";" + electricPower + ";" + bateriaString;
	}

	// Metodo que devuelve la potencia total del coche (en este caso la potencia
	// electrica).
	public int getTotalPower() {

		return electricPower;
	}

	// Metodo boolean que devuelve false si la potencia no esta dentro de los
	// limites.

	public static boolean isValidElectricPower(int electricPower) {

		if (electricPower < ELECTRICPOWER_MIN || electricPower > ELECTRICPOWER_MAX) {
			return false;
		}
		return true;
	}

	// Metodo boolean que devuelve false si la bateria no esta dentro de los
	// limites.
	public static boolean isValidBatteryCharge(float batteryCharge) {

		if (batteryCharge < BATTERYCHARGE_MIN || batteryCharge > BATTERYCHARGE_MAX) {
			return false;
		}
		return true;
	}

	// Metodo que calcula el nuevo valor de la bateria del coche a partir del tiempo
	// que este ha pasado conectado a un cargador electrico
	public void increaseBatteryChargeLevel(float chargeTime) {

		int chargerPower = ElectricCharger.POWER; // potencia del cargador en kW
		float actualLevel = batteryCharge; // nivel de bateria inicial

		float newLevel = actualLevel + ((-chargeTime) * chargerPower / BATTERY_CAPACITY) * 100;

		if (newLevel > 100) { // si el nuevo nivel de bateria es mayor que 100, se recorta a 100.
			newLevel = 100;

		}
		batteryCharge = newLevel;
	}

	// getters and setters

	public int getElectricPower() {
		return electricPower;
	}

	public void setElectricPower(int electricPower) {
		this.electricPower = electricPower;
	}

	public float getBatteryCharge() {
		return batteryCharge;
	}

	public void setBatteryCharge(float batteryCharge) {
		this.batteryCharge = batteryCharge;
	}

}
