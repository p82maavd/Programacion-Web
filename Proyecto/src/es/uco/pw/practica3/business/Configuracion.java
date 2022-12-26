package es.uco.pw.practica3.business;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;
/**
 * 
 * Declaracion de la clase Configuracion.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 * 
 */
public class Configuracion {

	Properties properties=null;
	
	private static Configuracion instance =null;
	
	/**
	 * Constructor de la clase Configuracion
	 * @param ubicacion del archivo .properties
	 * @throws FileNotFoundException
	 */
	        
	public Configuracion(InputStream ubicacion) throws FileNotFoundException {
		this.properties = new Properties();
		
	    try {
	    	properties.load(ubicacion);
	    	
	    } catch (FileNotFoundException ex) {
	    	ex.printStackTrace();
	    } catch (IOException ex) {
	    } 
	}
	/**
	 * Este metodo se encarga de crear una instancia en el caso de que no haya una ya creada. Patron de diseño Singleton
	 * @return Instancia unica de Configuracion.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	*/
	public static Configuracion getInstance(InputStream ubicacion) throws ClassNotFoundException, IOException {
		
		if(instance==null) {
			instance=new Configuracion(ubicacion);
			
		}
		
		return instance;
	}
	       
	/**
	 * Retorna la propiedad de configuracion solicitada
	 * @param key
	 * @return cadena que constituye la clave valor que le hemos dado en properties
	 */
	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}
}