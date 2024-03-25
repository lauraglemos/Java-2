package P6b;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;


public class Parking {   //	Clase con la que se crea el parking.

	//Atributos
	
	private char maxZone; // el maximo numero de zonas del parking
	private int sizeZone; // numero de plazas por zona
	private char lowerElectric; // indica la zona que separa el parking en dos
	
	
	private HashSet<CarSpace> busyCarSpaces= new HashSet<CarSpace>(); //plazas ocupadas, no es una coleccion ordenada
	private TreeSet<CarSpace> freeCarSpaces = new TreeSet<CarSpace>(); //plazas libres, ordenadas por coordenada
	
	ElectricCharger electricCharger = new ElectricCharger();  //cargador de la clase ElectricCharger

	//Metodo constructor, en el cual se construye el parking inicial a partir de una fichero que contiene los coches que hay en ese momento en el parking

	public Parking(String fileName) { 

		File fichero = new File(fileName);
		String linea = null;
		Scanner entrada = null;
		int nLinea = 0;

		try {
			entrada = new Scanner(fichero);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		while (entrada.hasNextLine()) {
			linea = entrada.nextLine();

			if (!linea.startsWith("#")) { // aqui leemos la primera linea que contiene la informacion de las dimensiones
											// del parking

				if (nLinea == 0) {

					primeraLinea(linea); // con esto lee la primera linea

				}

				else {

					crearRegistro(linea); // con esto lee el resto
				}

				nLinea++;
			}

		}

		entrada.close();// cerramos el fichero
	}

	// Metodo que lee la primera linea, la cual tiene las dimensiones del parking.

	private void primeraLinea(String linea) {

		if (linea.length() > 0) {

			String[] dimensiones = linea.split(";");

			maxZone = dimensiones[0].charAt(0);
			sizeZone = Integer.parseInt(dimensiones[1]);
			lowerElectric = dimensiones[2].charAt(0);


			for (int i =0; i <=  maxZone-'A'; i++) { // recorremos el array bidimensional
				char letra = (char) (i + 'A');
				for (int j = 1; j <= sizeZone; j++) {

					Coordinate coordenada = new Coordinate(letra, j); // nueva coordenada
					
				
					
					freeCarSpaces.add(new CarSpace(coordenada, null,null)); // creamos una plaza pero con matricula null
					
				}
			}
		}
	}

	// Metodo que lee los coches del fichero y los introduce en el parking
	
	private void crearRegistro(String linea) { 

		if (linea.length() > 0) {
			String[] elementos = linea.split(";");
			char letraZona = elementos[0].charAt(0);

			int numero = Integer.parseInt(elementos[0].substring(1, elementos[0].length()));

			String matricula = elementos[1];
			Coordinate plaza = new Coordinate(letraZona, numero);
			String hora = elementos[2];
			
			freeCarSpaces.remove(new CarSpace(plaza,null,null));// actualiza la matricula
			busyCarSpaces.add(new CarSpace(plaza,matricula,hora));

		}

	}
	
	// Metodo que devuelve un TreeSet con los coches del parking ordenados por tiempo de entrada.
	
	public TreeSet<CarSpace> ordenarParkingporTiempo(HashSet <CarSpace> parking) {
		
		TreeSet<CarSpace> TreeSetordenado= new TreeSet<CarSpace>(new CarComparatorByTimeEntry() );
       if(parking !=null) {
		TreeSetordenado.addAll(parking);
        
		
		return TreeSetordenado;
       }
       return null;
	}
	
	// Metodo que devuelve un TreeSet con los coches del parking ordenados por coordenada.
	
public TreeSet<CarSpace> ordenarParkingporCoordenada(TreeSet<CarSpace> busyCarSpacesordenado) {
		
		TreeSet<CarSpace> TreeSetordenado= new TreeSet<CarSpace>(new CarComparatorByCoordinate() );
       if(busyCarSpacesordenado !=null) {
		TreeSetordenado.addAll(busyCarSpacesordenado);
        
		return TreeSetordenado;
       }
       return null;
	}
	

	//Metodo que guarda la nueva informacion del parking en un fichero nuevo.

	public void saveParking(String fileName) { 

		try {

			PrintWriter doc = new PrintWriter(fileName);

			doc.println(maxZone + ";" + sizeZone + ";" + lowerElectric); // primera linea

			if(busyCarSpaces!=null) {
				TreeSet <CarSpace>busyCarSpacesordenado = ordenarParkingporTiempo(busyCarSpaces);
//					
					for(CarSpace coche: busyCarSpacesordenado) {
						
						doc.println(coche.toString());
					}
			}

			doc.close(); // cerramos el fichero

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

	// Metodo que sirve para implementar la entrada de coches en el parking
	
	public void carEntry(Car cocheEntrada, String time) { 

		if(cocheEntrada instanceof CombustionCar) {	// si el coche es C, solo puede ir a su seccion del parking, el resto es para
			// electricos e hibridos

			
					if (freeCarSpaces.first().getPlate()== null) {

						Coordinate coordenadaLibre = freeCarSpaces.first().getCoordinate();
						
						freeCarSpaces.remove(new CarSpace(coordenadaLibre,null,null));
						busyCarSpaces.add(new CarSpace(coordenadaLibre,cocheEntrada.plate,time));

						return;
					}

				}


		if(cocheEntrada instanceof HybridCar||cocheEntrada instanceof ElectricCar) {

		 char principioZonaElectricos = (char) (lowerElectric);

			for (int j = 0; j <= sizeZone; j++) {

;						if (freeCarSpaces.higher(new CarSpace(new Coordinate(principioZonaElectricos,j),null,null)).getPlate()== null) {
						
							Coordinate coordenadaLibre = freeCarSpaces.higher(new CarSpace(new Coordinate(principioZonaElectricos,j),null,null)).getCoordinate();
						
						freeCarSpaces.remove(new CarSpace(coordenadaLibre,null,null));
						busyCarSpaces.add(new CarSpace(coordenadaLibre,cocheEntrada.plate,time));
						
						electricCharger.connect();
						
						return;

					}

				}

			}
		
		}



	// Metodo que sirve para implementar la salida de coches en el parking

	public String carDeparture (String plate) { 

					for(CarSpace cocheSalida : busyCarSpaces) {
						
						if(cocheSalida.getPlate().equals(plate)) {
				
					busyCarSpaces.remove(cocheSalida);
					
					cocheSalida.setPlate(null);
					
					String horaSalida = cocheSalida.getEntryTime();
						
					cocheSalida.setEntryTime(null);
					freeCarSpaces.add(cocheSalida);

					electricCharger.disconnect();
					return horaSalida;
					
						}
						
					
					}
					
					return null;

	}
	
	//Metodo que crea el mapa en el cual se representa el parking
	
	 public String toMap() {
		 
		 
		 TreeSet<CarSpace> parkingOrdenado=new TreeSet<CarSpace>();

		 for(CarSpace car : busyCarSpaces) {
			 parkingOrdenado.add(car);
		 }
		 
		
		 parkingOrdenado.addAll(freeCarSpaces);
		 
		parkingOrdenado = ordenarParkingporCoordenada(parkingOrdenado);
		
		
	
		String mapa= "";	 

		
		   for(CarSpace coche: parkingOrdenado) {
			   
						 if(coche.getCoordinate().getZone()<lowerElectric) {
					        	
						        if(coche.getPlate()!= null) {
     	
						         mapa= mapa + coche.getCoordinate()+"   "+coche.getPlate()+"|";
						       
						        }
						        
						        else {
						        	mapa= mapa + coche.getCoordinate()+"          |";

						        }}    
	 
		        else {

		        if(coche.getPlate()!=null) {

		        	mapa= mapa + coche.getCoordinate()+" E "+coche.getPlate()+"|";

		        }
		        else {
		        	 mapa= mapa + coche.getCoordinate()+" E        |";

		        }

		    }
			    
			   if(coche.getCoordinate().getNumber()==sizeZone) {
				   mapa = mapa + "\n";
			   }
				   
				  
			   }
		
		   
		   return mapa;
		  
	 }

	// getters y setters

	public char getMaxZone() {
		return maxZone;
	}

	public void setMaxZone(char maxZone) {
		this.maxZone = maxZone;
	}

	public int getSizeZone() {
		return sizeZone;
	}

	public void setSizeZone(int sizeZone) {
		this.sizeZone = sizeZone;
	}

	public char getLowerElectric() {
		return lowerElectric;
	}

	public void setLowerElectric(char lowerElectric) {
		this.lowerElectric = lowerElectric;
	}

public class CarComparatorByTimeEntry implements Comparator<CarSpace>{ //Clase interna de parking que ordena los coches por hora de entrada. Si entraron a la misma hora se ordenan por matricula.
		
		public int compare(CarSpace c1,CarSpace c2) {
			int hi = Integer.parseInt(c1.getEntryTime().split(":")[0].trim());
			int mi = Integer.parseInt(c1.getEntryTime().split(":")[1].trim());
			int ho = Integer.parseInt(c2.getEntryTime().split(":")[0].trim());
			int mo = Integer.parseInt(c2.getEntryTime().split(":")[1].trim());
			
			if(ho>hi)  return -1;
			else if(ho==hi) {
				if(mo>mi) return -1;
				else if (mo==mi) {
					
				
			return c1.getCoordinate().compareTo(c2.getCoordinate());
		}
			else return 1;
	}
			else return 1;

}
}

public class CarComparatorByCoordinate implements Comparator<CarSpace>{  //Clase interna de parking que ordena las coordenadas.
	
	public int compare(CarSpace c1, CarSpace c2) {
		return c1.getCoordinate().compareTo(c2.getCoordinate());
	}
}

}
