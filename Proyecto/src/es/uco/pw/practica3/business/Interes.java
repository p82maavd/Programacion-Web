package es.uco.pw.practica3.business;

import java.io.Serializable;

/**
 * Declaracion de la clase Interes.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 */
public class Interes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String interes;
	
	/**
	 * Devuelve el valor de un interes
	 * @return identificador del interes
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Modifica el identificador de un interes
	 * @param id Identificado
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Devuelve el nombre de un interes
	 * @return Nombre del interes
	 */
	public String getInteres() {
		return interes;
	}
	/**
	 * Modifica el nombre de un interes
	 * @param interes Nombre del interes
	 */
	public void setInteres(String interes) {
		this.interes = interes;
	}
	/**
	 * Constructor de la clase Interes
	 * @param id
	 * @param interes
	 */
	public Interes(int id, String interes) {
		
		this.id = id;
		this.interes = interes;
	}
}