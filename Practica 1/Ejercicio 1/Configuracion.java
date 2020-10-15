package Ejercicio1;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class Configuracion {

	Properties properties=null;
	InputStream entrada=null;
	        
	private static Configuracion instance =null;
	        
	private Configuracion(String ubicacion) throws FileNotFoundException {
		this.properties = new Properties();
		//Hacer que se cree si no existe.
	    entrada = new FileInputStream("datos1.properties");
	    try {
	    	properties.load(entrada);
	    } catch (FileNotFoundException ex) {
	    	ex.printStackTrace();
	    } catch (IOException ex) {
	    } 
	}
	
	public static Configuracion getInstance(String ubicacion) throws ClassNotFoundException, IOException {
		
		if(instance==null) {
			instance=new Configuracion(ubicacion);
		}
		return instance;
	}
	       

	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}
}

