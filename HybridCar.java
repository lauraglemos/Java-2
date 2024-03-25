package P6b;

public class HybridCar extends Car { // Clase hija de Car que genera los coches hibridos.

	// Atributos de instancia

	private int electricPower; // la potencia electrica del coche
	private float batteryCharge;// el porcentaje de carga de la bateria del coche
	private int mechanicalPower;// la potencia mecanica del coche

	// Atributos estáticos

	public static final int ELECTRICPOWER_MIN = 20;
	public static final int ELECTRICPOWER_MAX = 100;
	public static final int MECHANICALPOWER_MIN = 60;
	public static final int MECHANICALPOWER_MAX = 500;
	public static float BATTERYCHARGE_MIN = 0.0f;
	public static float BATTERYCHARGE_MAX = 100.0f;
	public static float BATTERY_CAPACITY = 15.0f;

	// METODOS

	// Metodo constructor vacio

	public HybridCar() {

	}

	// Metodo constructor que crea un coche hibrido a partir de su matricula, su
	// fabricante, su potencia mecanica,su potencia electrica y su porcentaje de
	// bateria.

	public HybridCar(String plate, String manufacturer, int mechanicalPower, int electricPower, float batteryCharge) {

		super(plate, manufacturer);

		this.mechanicalPower = mechanicalPower;
		this.electricPower = electricPower;
		this.batteryCharge = batteryCharge;

	}

	// Metodo que muestra los atributos del coche.

	public String toString() {

		String bateriaString = Float.toString(batteryCharge);

		bateriaString = bateriaString.replace(".", ",");

		return "H;" + plate + ";" + manufacturer + ";" + mechanicalPower + ";" + electricPower + ";" + bateriaString;
	}

	// Metodo que devuelve la potencia total del coche (potencia electrica+potencia
	// mecanica).
	public int getTotalPower() {

		int potenciaTotal = electricPower + mechanicalPower;

		return potenciaTotal;
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

	// Metodo boolean que devuelve false si la potencia no esta dentro de los
	// limites.

	public static boolean isValidMechanicalPower(int electricPower) {

		if (electricPower < MECHANICALPOWER_MIN || electricPower > MECHANICALPOWER_MAX) {
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

	public int getMechanicalPower() {
		return mechanicalPower;
	}

	public void setMechanicalPower(int mechanicalPower) {
		this.mechanicalPower = mechanicalPower;
	}

}
