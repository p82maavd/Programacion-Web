package es.uco.pw.practica3.display;

import java.util.ArrayList;

import es.uco.pw.practica3.business.Interes;

/**
 *  Declaracion de la clase AnuncioBean.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 *
 */
public class AnuncioBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String titulo = "";
	private String cuerpo= "";
	private String fechainicio;
	private String fechafinal;
	private ArrayList<Interes> intereses;
	private ArrayList<String> destinatarios;
	private String autor="";
	/**
	 * Devuelve el autor del anuncio
	 * @return Autor del anuncio
	 */
	public String getAutor() {
		return autor;
	}
	/**
	 * Modifica el autor del anuncio
	 * @param autor Autor del anuncio
	 *
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}
	/**
	 * Devuelve los destinatarios del anuncio
	 * @return Vector con los destinatarios del anuncio
	 */
	public ArrayList<String> getDestinatarios() {
		return destinatarios;
	}
	/**
	 * Modifica los destinatarios del anuncio
	 * @param destinatarios Nuevos destinatarios del anuncio
	 */
	public void setDestinatarios(ArrayList<String> destinatarios) {
		this.destinatarios = destinatarios;
	}
	private String tipo;
	private int id;
	/**
	 * Devuelve el identificador del anuncio
	 * @return Identificador del anuncio
	 */
	public int getId() {
		return id;
	}
	/**
	 * Modifica el identificador del anuncio
	 * @param id Identificador del anuncio
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Devuelve el tipo de anuncio
	 * @return Tipo del anuncio
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * Modifcia el tipo del anuncio
	 * @param tipo Tipo del anuncio
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * Devuelve el titulo del anuncio
	 * @return Titulo del anuncio
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * Modifica el titulo del anuncio
	 * @param titulo Titulo del anuncio
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * Devuelve el cuerpo del anuncio
	 * @return Cuerpo
	 */
	public String getCuerpo() {
		return cuerpo;
	}
	/**
	 * Modifica el cuerpo del anuncio
	 * @param cuerpo
	 */
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	/**
	 * Devuelve la fecha de inicio del anuncio
	 * @return Fecha de inicio del anuncio
	 */
	public String getFechainicio() {
		return fechainicio;
	}
	/**
	 * Modifica la fecha de inicio del anuncio
	 * @param fechainicio Fecha de inicio del anucio
	 */
	public void setFechainicio(String fechainicio) {
		this.fechainicio = fechainicio;
	}
	/**
	 * Devuelve la fecha de finalizacion del anuncio
	 * @return Fecha de finalizacion del anuncio
	 */
	public String getFechafinal() {
		return fechafinal;
	}
	/**
	 * Modifica la Fecha final del anuncio
	 * @param fechafinal Fecha final del anuncio
	 */
	public void setFechafinal(String fechafinal) {
		this.fechafinal = fechafinal;
	}
	/**
	 * Devuelve los intereses de un anuncio
	 * @return Intereses del anuncio
	 */
	public ArrayList<Interes> getIntereses() {
		return intereses;
	}
	/**
	 * Modifica los intereses de un anuncio
	 * @param intereses Intereses del anuncio
	 */
	public void setIntereses(ArrayList<Interes> intereses) {
		this.intereses = intereses;
	}
	
	
	
}