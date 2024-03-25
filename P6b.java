package P6b;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class P6b {  // Clase que recibe las instrucciones y requiere informacion para la creacion del parking.

	//Atributos globales
	
	static CarDB cdb;	//coches de la ciudad
	static Parking miParking;	//el parking que construimos.

	 // Metodo principal desde el cual a partir de nombres de ficheros se va construyendo el parking.

	public static void main(String[] args) { 
		
		String file1 = args[0];	//un fichero existente con la estructura y contenido de un parking
		String file2 = args[1];	//un fichero existente de entradas y salidas
		String file3 = args[2];	//el nombre del fichero en el que guardar el resultado de la actualización del parking
		String file4 = args[3];	//un fichero existente con los coches de ciudad
		String file5 = args[4];	//el nombre del fichero donde guardar la colección de coches cityCars
		String file6 = args[5];	//el nombre del fichero donde guardar el dibujo del parking
		
		 cdb =new CarDB();
		
		try {
			cdb.readCityCarsFile("P6b/" + file4);	//leemos los coches del fichero que contiene todos los de la ciudad.
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		 miParking = new Parking("P6b/" + file1);	//creamos el parking a partir de los coches que ya estan dentro
		 
		processIO("P6b/" + file2);					//metodo con el que leemos el fichero de entradas y salidas
		
		miParking.saveParking("P6b/" + file3);	//guardamos el nuevo fichero
		
		cdb.sortByPlate();	//ordenamos la lista de coches de la ciudad por matricula
		
		cdb.saveCarsToFile("P6b/" + file5);	//guardamos la nueva lista en otro fichero
		
		String dibujoParking =miParking.toMap();	//guardamos el dibujo del mapa en un nuevo fichero 
		
		PrintWriter doc = null;
		
		try {
			doc = new PrintWriter("P6b/" + file6);
			
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		doc.println(dibujoParking);
		doc.close();
		
	}
	
	//Metodo con el que leemos el fichero de entradas y salidas
	
		private static void processIO (String filename){

			Scanner input = null;
			String linea = null;
			File file = new File(filename);
			
			try {
				
				input = new Scanner(file);
				
			while(input.hasNextLine()){  //para leer el fichero que tiene las entradas y salidas
				linea = input.nextLine();
				
				if(!linea.startsWith("#")) {
					
					String [] elementos = linea.split(";");
					char typeES = elementos[0].charAt(0);
					String matricula = elementos[1];
					String hora = elementos[2];
					
					Car car = cdb.getCarFromPlate(matricula);
					
					
					switch(typeES) {
					case 'I':
								
						miParking.carEntry(car,hora); //para meter coches en el parking
						
						break;
						
					case 'O':

						String departureTime = miParking.carDeparture(matricula); // para sacar coches del parking
					
				cdb.increaseCarBatteryChargeLevel(matricula, hora, departureTime); //para actualizar la bateria de los coches que salieron del parking despues de estar cargando
						break;
					}
					
				
				}
			
			
		}
			
			input.close();
			
			
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}	
			}
}	
		

