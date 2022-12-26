package es.uco.pw.practica3.business;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Declaracion de la clase AnuncioIndividualizado.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 * 
 */

public class AnuncioIndividualizado extends Anuncio{
	
	private static final long serialVersionUID = 1L;
	ArrayList<Contacto> destinatarios;

	/**
	 * Constructor de la clase AnuncioIndividualizado
	 * @param id de AnuncioIndividualizado
	 * @param titulo de AnuncioIndividualizado
	 * @param cuerpo de AnuncioIndividualizado
	 * @param usuario de AnuncioIndividualizado
	 * @param destinatarios de AnuncioIndividualizado
	 * @param estado de AnuncioIndividualizado
	 */
	public AnuncioIndividualizado(int id, String titulo, String cuerpo, String usuario, Date fecha,Estados estado) {
		super(id, titulo, cuerpo, usuario, estado);
		
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.fecha=fecha;
		this.estado=estado;
	}
	
	public String tooString() {
		/**
		 * Este metodo se encarga de convertir la instancia de la clase AnuncioIndividualizado a string
		 * @return string Cadena con informacion sobre el anuncio
		 */
		
		String anuncioInfo = "Id: "+this.id + " Titulo: " + this.titulo + " Cuerpo: " + this.cuerpo + " Propietario: " + getUsuario()+ " Estado: " + this.getEstado().getEstados() + "\n";
		
		return anuncioInfo;
	}

}
