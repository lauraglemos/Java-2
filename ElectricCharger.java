package P6b;

public class ElectricCharger { // Clase que modela el cargador

	// Atributos

	private boolean connected; // indica si el cargador está conectado (true) o no (false)
	public static final int POWER = 25; // potencia del cargador

	// Metodo constructor vacio que inicializa connected a false

	public ElectricCharger() {
		connected = false;
	}

	// METODOS

	// Metodo que pone el atributo connected a true

	public void connect() {

		connected = true;
	}

	// Metodo que pone el atributo connected a false

	public void disconnect() {

		connected = false;
	}

	// getters and setters

	public boolean getConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

}
