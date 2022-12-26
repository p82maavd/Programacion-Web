package es.uco.pw.practica3.display;

import java.util.ArrayList;

import es.uco.pw.practica3.business.Interes;

/**
 *  Declaracion de la clase InteresesBean
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 *
 */
public class InteresesBean implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Interes> intereses;
	
	/**
	 * Metodo que devuelve un ArrayList de los intereses
	 * @return ArrayList de intereses
	 */
	public ArrayList<Interes> getIntereses(){
		return intereses;
	}
	
	/**
	 * Metodo que modifica el ArrayList de intereses
	 * @param intereses ArrayList de intereses
	 */
	public void setIntereses(ArrayList<Interes> intereses) {
		this.intereses=intereses;
	}

	

}