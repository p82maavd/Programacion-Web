package ejercicio1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Declaracion de la clase AbstractFactory.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 * 
 */

public abstract class AbstractFactory {

	// Factory methods for each anuncio.
	
	/**
	 * 
	 * @param e Creador del anuncio
	 * @param a Lista de destinatarios
	 * @return Anuncio de tipo flash
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	
	public abstract AnuncioFlash createAnuncioFlash(Contacto e, Date fecha);
	
	/**
	 * 
	 * @param e Creador del anuncio
	 * @param destinatarios Lista de destinatarios
	 * @return Anuncio de tipo individualizado
	 */
	
	public abstract AnuncioIndividualizado createAnuncioIndividualizado(Contacto e, Date fecha);
	/**
	 * 
	 * @param e Creador del anuncio
	 * @param a Lista de destinatarios
	 * @return Anuncio de tipo tematico
	 */
	
	
	public abstract AnuncioTematico createAnuncioTematico(Contacto e, ArrayList <Interes> aux, Date fecha);
	
	/**
	 * 
	 * @param e Creador del anuncio
	 * @param a Lista de destinatarios
	 * @return Anuncio de tipo general
	 */
	
	
	public abstract AnuncioGeneral createAnuncioGeneral(Contacto e, Date fecha);
}
