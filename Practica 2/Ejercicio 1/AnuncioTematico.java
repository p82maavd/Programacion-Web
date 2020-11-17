package ejercicio1;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Declaracion de la clase AnuncioTematico.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 * 
 */
public class AnuncioTematico extends Anuncio{
	
	
	private static final long serialVersionUID = 1L;
	private ArrayList <Interes> intereses;
	

	/**
	 * Constructor de la clase AnuncioTematico
	 * @param id de AnuncioTematico
	 * @param titulo de AnuncioTematico
	 * @param cuerpo de AnuncioTematico
	 * @param usuario de AnuncioTematico
	 * @param destinatarios de AnuncioTematico
	 * @param intereses de AnuncioTematico
	 * @param estado de AnuncioTematico
	 */

	public AnuncioTematico(int id, String titulo, String cuerpo, Contacto usuario, ArrayList<Interes> intereses, Date fecha, Estados estado) {
		super(id, titulo, cuerpo, usuario, estado);
		this.intereses=intereses;
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.fecha=fecha;
		this.estado=estado;
		
	}

	/**
	 * Devuelve un string de los intereses de la clase AnuncioTematico
	 * @return intereses de AnuncioTematico
	 */
	public ArrayList <Interes> getIntereses() {
		return intereses;
	}
	
	/**
	 * Modifica los intereses de la clase AnuncioTematico
	 * @param intereses
	 */
	public void setIntereses(ArrayList <Interes> intereses) {
		this.intereses = intereses;
	}
	
	/**
	 * Este método se encarga de convertir la instancia de la clase AnuncioTematico a string
	 * @return string
	 */
	public String tooString() {
		
		String anuncioInfo = "Id: "+this.id + " Titulo: " + this.titulo + " Cuerpo: " + this.cuerpo + " Propietario: " + this.usuario.getNombre()+  " Estado: " + this.getEstado().getEstados() +"\n"+ "Destinatarios: "; // Another way to concat strings
		//Actualizar.
		/*for(int i=0; i<this.getDestinatarios().size();i++) {
			anuncioInfo=anuncioInfo +"\n" +this.getDestinatarios().get(i).getEmail();
		}*/
		anuncioInfo=anuncioInfo+"\n"+"Intereses: ";
		for(int i=0; i<this.getIntereses().size();i++) {
			anuncioInfo=anuncioInfo +"\n" +this.getIntereses().get(i).getInteres();
		}
		
		return anuncioInfo;
	}

}
