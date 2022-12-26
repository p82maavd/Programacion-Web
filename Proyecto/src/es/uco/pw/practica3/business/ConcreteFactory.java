package es.uco.pw.practica3.business;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.Timestamp;

import es.uco.pw.practica3.business.Anuncio.Estados;

/**
 * Declaracion de la clase ConcretreFactory.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 * 
 */

public class ConcreteFactory extends AbstractFactory {

	
	@Override
	public AnuncioFlash createAnuncioFlash(String autor, String titulo, String cuerpo, Date fecha, Timestamp fechainicio,Timestamp fechafin){
		
		AnuncioFlash product = new AnuncioFlash(0, titulo, cuerpo, autor, Estados.Editado,fecha, fechainicio, fechafin);
		return product;
	}

	@Override
	public AnuncioIndividualizado createAnuncioIndividualizado(String autor, String titulo, String cuerpo, Date fecha) {
		
		AnuncioIndividualizado product = new AnuncioIndividualizado(0, titulo, cuerpo, autor, fecha, Estados.Editado);
		return product;
	}
	
	@Override
	public AnuncioTematico createAnuncioTematico(String autor,String titulo, String cuerpo, ArrayList <Interes> intereses, Date fecha) {
		
		AnuncioTematico product = new AnuncioTematico( 0, titulo, cuerpo, autor, intereses, fecha, Estados.Editado);
		return product;
	}

	@Override
	public AnuncioGeneral createAnuncioGeneral(String autor, String titulo, String cuerpo, Date fecha) {
		
		AnuncioGeneral product = new AnuncioGeneral(0, titulo, cuerpo, autor, fecha, Estados.Editado);
		return product;
	}

}