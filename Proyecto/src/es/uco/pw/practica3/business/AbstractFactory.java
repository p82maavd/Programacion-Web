package es.uco.pw.practica3.business;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
/**
 * Declaracion de la clase AbstractFactory.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 * 
 */

public abstract class AbstractFactory {
	
	
	/**
	 * Metodo que crea un AnuncioFlash
	 * @param autor Autor del Anuncio
	 * @param titulo Titulo del Anuncio
	 * @param cuerpo Cuerpo del Anuncio
	 * @param fecha Fecha de publicacion del Anuncio
	 * @param fechainicio Fecha de inicio en la que se publica el anuncio flash
	 * @param fechafin Fecha de finalizacion del anuncio flash
	 * @return Anuncio Flash
	 */
	public abstract AnuncioFlash createAnuncioFlash(String autor, String titulo, String cuerpo, Date fecha, Timestamp fechainicio,Timestamp fechafin);
	
	/**
	 * Metodo que crea un AnuncioIndividualizado
	 * @param autor Autor del Anuncio
	 * @param titulo Titulo del Anuncio
	 * @param cuerpo Cuerpo del Anuncio
	 * @param fecha Fecha de publicacion del Anuncio
	 * @return Anuncio Individualizado
	 */
	public abstract AnuncioIndividualizado createAnuncioIndividualizado(String autor, String titulo, String cuerpo, Date fecha);
	
	/**
	 * Metodo que crea un AnuncioTematico
	 * @param autor Autor del Anuncio
	 * @param titulo Titulo del Anuncio
	 * @param cuerpo Cuerpo del Anuncio
	 * @param fecha Fecha de publicacion del Anuncio
	 * @param aux Intereses del anuncio
	 * @return Anuncio Tematico
	 */
	
	
	public abstract AnuncioTematico createAnuncioTematico(String autor, String titulo, String cuerpo, ArrayList <Interes> aux, Date fecha);
	
	/**
	 * Metodo que crea un AnuncioGeneral
	 * @param autor Autor del Anuncio
	 * @param titulo Titulo del Anuncio
	 * @param cuerpo Cuerpo del Anuncio
	 * @param fecha Fecha de publicacion del Anuncio
	 * @return Anuncio General
	 */
	
	
	public abstract AnuncioGeneral createAnuncioGeneral(String autor, String titulo, String cuerpo, Date fecha);
}
