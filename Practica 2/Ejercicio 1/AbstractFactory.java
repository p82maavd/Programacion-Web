package ejercicio1;

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
	 * @param id Identificador del anuncio
	 * @return Anuncio de tipo flash
	 */
	
	public abstract AnuncioFlash createAnuncioFlash(Contacto e,ArrayList <Contacto> a, int id);
	
	/**
	 * 
	 * @param e Creador del anuncio
	 * @param destinatarios Lista de destinatarios
	 * @param id Identificador del anuncio
	 * @return Anuncio de tipo individualizado
	 */
	
	public abstract AnuncioIndividualizado createAnuncioIndividualizado(Contacto e, ArrayList<Contacto> destinatarios, int id);
	/**
	 * 
	 * @param e Creador del anuncio
	 * @param a Lista de destinatarios
	 * @param id Identificador del anuncio
	 * @return Anuncio de tipo tematico
	 */
	
	
	public abstract AnuncioTematico createAnuncioTematico(Contacto e, ArrayList <String> aux, ArrayList <Contacto> a, int id);
	
	/**
	 * 
	 * @param e Creador del anuncio
	 * @param a Lista de destinatarios
	 * @param id Identificador del anuncio
	 * @return Anuncio de tipo general
	 */
	
	
	public abstract AnuncioGeneral createAnuncioGeneral(Contacto e,ArrayList <Contacto> a, int id);
}
