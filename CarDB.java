package P6b;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class CarDB { 	//Clase en la cual se trabaja con los coches 
	
	//Atributos
	
	LinkedHashMap <String, Car> cityCars = new LinkedHashMap <String,Car>(); //Mapa que contiene los coches.
	 int nLinea=0;	// contador de las lineas del fichero
	 ArrayList<Car> coches;	 // lista de los coches que utilizaremos a la hora de ordenar por matricula
	
	 
	 //Metodo constructor vacio
	 
	public CarDB() {
		
	}
	
	
	//METODOS
	
	// Metodo con que leemos el fichero que contiene los coches de la ciudad.
	
	public void readCityCarsFile(String filename) throws FileNotFoundException {
		
		File fileCarsCity = new File(filename);

	    Scanner entry = new Scanner(fileCarsCity);
	    
	    String linea = null;
	    String[] array;
	    
	     
	    while(entry.hasNextLine()) {

	      linea=entry.nextLine();
	      
	      if(linea.startsWith("#")==false) {

	        char tipoMotor = linea.charAt(0);
	        array=linea.split(";");
	        float bateria;
	        
	        
	        if(this.cityCars == null) {	// si el mapa está vacio, se crea.
				this.cityCars= new LinkedHashMap <String, Car>();
			}
	        
	        switch(tipoMotor) {		//introducimos los coches en el mapa, diferenciando que tipo de coche es.

	          case 'C':
	        	  		if(Car.isValidPlate(array[1]) && CombustionCar.isValidMechanicalPower(Integer.parseInt(array[3]))) {
	                      
	        	  			Car cocheCombustion = new CombustionCar(array[1],array[2],Integer.parseInt(array[3]));

	                      cityCars.put(array[1], cocheCombustion);
	                      nLinea++;
	                    }
	          
	                    break;

	          case 'E': 
	        	  		bateria = Float.parseFloat(array[4].replaceAll(",", "."));
	        	  		if(Car.isValidPlate(array[1]) && ElectricCar.isValidElectricPower(Integer.parseInt(array[3])) && ElectricCar.isValidBatteryCharge(bateria)) {
	                      
	        	  			Car cocheElectrico = new ElectricCar(array[1],array[2],Integer.parseInt(array[3]),bateria);
	                     
	        	  			cityCars.put(array[1], cocheElectrico);
	                      nLinea++;
	                    }
	                    break;

	          case 'H': 
	        	  		bateria = Float.parseFloat(array[5].replaceAll(",", "."));
	        	  		if(Car.isValidPlate(array[1]) && HybridCar.isValidMechanicalPower(Integer.parseInt(array[3])) && HybridCar.isValidElectricPower(Integer.parseInt(array[4])) && HybridCar.isValidBatteryCharge(bateria)) {
	                     
	        	  			Car cocheHibrido = new HybridCar(array[1],array[2],Integer.parseInt(array[3]),Integer.parseInt(array[4]),bateria);
	                     
	        	  			cityCars.put(array[1], cocheHibrido);
	                      nLinea++;
	                    }
	                    break;
	        }
	      }
	    }
	    }
	
	//Metodo que devuelve un coche a partir de su matricula.
	
	public Car getCarFromPlate(String plate) {
				 
		for (Car coche: cityCars.values()) {
			
			if(coche.getPlate().equals(plate)) {
				return coche;
			}
				
}
	return null;
	}

	//Metodo que guarda los coches en un fichero nuevo.
	
	public void saveCarsToFile(String filename) {
		
		PrintWriter doc;
		try {
			doc = new PrintWriter(filename);
		
		
		for(Car coche: cityCars.values()) {

			doc.println(coche.toString());
			
		}
		doc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	
	// Metodo que calcula la potencia total sumando la potencia de todos los coches
	
	public int computeTotalPower() {


		int acumuladorPotencia =0;
		
		for(Car coche : cityCars.values()) {
			
					if(coche!=null) {
						
						 int potencia= coche.getTotalPower();
						 
						
						acumuladorPotencia = acumuladorPotencia + potencia;
						 
						
					}
					else {
						
					break;
					}
					
					}

		return acumuladorPotencia;
		
	
}
	
	//Metodo que calcula la la potencia media, suma la potencia de todos los coches y lo divide entre el numero de coches.
	public float computeAverageBatteryLevel() { 
		
		 float acumuladorBateria =0;
		 int nCoches=0;
		 float bateria;
		 HybridCar cocheHibrido =new HybridCar();
		 ElectricCar cocheElectrico =new ElectricCar();
	
		 for(Car coche : cityCars.values()) {
		
			if(coche instanceof HybridCar) {
				
				cocheHibrido=(HybridCar) coche;
				bateria = cocheHibrido.getBatteryCharge();
				acumuladorBateria = bateria+ acumuladorBateria;
				nCoches++;
			}
			
			if(coche instanceof ElectricCar) {
				
				cocheElectrico=(ElectricCar) coche;
				bateria = cocheElectrico.getBatteryCharge();
				acumuladorBateria = bateria+ acumuladorBateria;
				nCoches++;
			}
				
				
				
			}
		
		acumuladorBateria = acumuladorBateria/nCoches;
			return acumuladorBateria;
	
	
}


	//	Metodo que incrementa el nivel de bateria de los coches electricos e hibridos a partir del tiempo que estuvieron el parking conectados al cargador.
	
public void increaseCarBatteryChargeLevel(String plate, String entryTime, String departureTime) {
	
	for (Car coche : cityCars.values()) {
		
	if(coche.getPlate().equals(plate)) {
		
		Car cocheConectado = coche;
		HybridCar cocheHibrido =new HybridCar();
		 ElectricCar cocheElectrico =new ElectricCar();
		
		float tiempoCargador = intervalInHours(entryTime,departureTime);
		
		if (cocheConectado instanceof HybridCar) {
			
			
			cocheHibrido=(HybridCar) cocheConectado;
			cocheHibrido.increaseBatteryChargeLevel(tiempoCargador);
		
		}
		
		if (cocheConectado instanceof ElectricCar) {
			
			
			cocheElectrico = (ElectricCar) cocheConectado;
			cocheElectrico.increaseBatteryChargeLevel(tiempoCargador);
		}
	
		
	}
	}

}
	
	//Metodo que calcula el tiempo en horas  entre la hora de entrada y salida

private float intervalInHours(String inTime, String outTime) {
	int hi = Integer.parseInt(inTime.split(":")[0].trim());
	int mi = Integer.parseInt(inTime.split(":")[1].trim());
	int ho = Integer.parseInt(outTime.split(":")[0].trim());
	int mo = Integer.parseInt(outTime.split(":")[1].trim());
	int dif = (ho*60+mo)-(hi*60+mi);
	return ((float)dif/60);
	}

	
	//Metodo que ordena los coches por matricula de menor a mayor

public void sortByPlate() {
	
	
	
		 if (this.coches == null) {
			 this.coches = new ArrayList <Car>();
		 }
		 

		 this.coches.addAll(cityCars.values());
		 
		 Collections.sort(this.coches);

		 cityCars.clear();
		 
		 for(Car c : coches) {
			 
			 cityCars.put(c.getPlate(), c);
			 
		 }
		 	
}

//Metodo que ordena los coches por nivel de bateria y por matricula de menor a mayor.

public void sortByBatteryChargeAndPlate() {
	

	 if (this.coches == null) {
		 this.coches = new ArrayList <Car>();
	 }
	 

	 this.coches.addAll(cityCars.values());

	 Collections.sort(this.coches, new CarComparatorByBatteryLevelAndPlate());
	 cityCars.clear();
	 
	 for(Car c : coches) {
		 
		 cityCars.put(c.getPlate(), c);
	 }
	 
	
}


	public class CarComparatorByBatteryLevelAndPlate implements Comparator<Car>{ //Clase interna de CarDB que se usa para comparar dos coches por su nivel de bateria y matricula.
		
	public int compare(Car coche1, Car coche2) {
		
		int bateria1;
		int bateria2;
		if (coche1 instanceof CombustionCar) {
			bateria1 = 0;
		}
		else {
			
			bateria1 = coche1.getTotalPower();
		}
		
		if(coche2 instanceof CombustionCar) {
			bateria2 = 0;
		}
		
		else {
			bateria2 = coche2.getTotalPower();
		}
		
		if(bateria1 < bateria2) return-1;
		
		else if(bateria1 ==bateria2) {
			 coche1.getPlate().compareTo(coche2.getPlate());
			 return 0;
		}
		else return 1;


	}
	
	
}
	}
	
	
