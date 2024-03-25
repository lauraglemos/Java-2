package P6b;

public class Coordinate implements Comparable<Coordinate> { // Clase que genera las coordenadas que tiene cada plaza del
															// parking.

	private char zone; // caracter entre la A y la Z
	private int number; // numero entero estrictamente positivo

	// Metodo constructor que crea coordenadas a partir de su zona y numero.

	public Coordinate(char zone, int number) {

		this.zone = zone;
		this.number = number;
	}

	// METODOS

	// Metodo que compara la coordenada y otra que se pasa como parametro zona por
	// zona y numero por numero, y las ordena de forma ascendente.

	public int compareTo(Coordinate coordenada) {
		if (this.zone < coordenada.getZone())
			return -1;

		else if (this.zone == coordenada.getZone()) {

			if (this.number < coordenada.getNumber())
				return -1;

			else if (this.number == coordenada.getNumber())
				return 0;

		}

		return 1;
	}

	// Metodo que comprueba si dos coordenadas son iguales

	public boolean equals(Coordinate coordenada) {

		if (this.zone == coordenada.zone && this.number == coordenada.number) {

			return true;

		}

		else {

			return false;
		}
	}

	// Metodo que devuelve una cadena con la coordenada

	public String toString() {

		return this.zone + Integer.toString(number);
	}

	// Getters y setters

	public char getZone() {
		return zone;
	}

	public void setZone(char zone) {
		this.zone = zone;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
