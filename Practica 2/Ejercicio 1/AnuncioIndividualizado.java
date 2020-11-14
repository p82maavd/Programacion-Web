package ejercicio1;

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
	public AnuncioIndividualizado(int id, String titulo, String cuerpo, Contacto usuario, Date fecha,Estados estado) {
		super(id, titulo, cuerpo, usuario, estado);
		// TODO Auto-generated constructor stub
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.fecha=fecha;
		this.estado=estado;
	}
	
	public String tooString() {
		/**
		 * Este método se encarga de convertir la instancia de la clase AnuncioIndividualizado a string
		 * @return string Cadena con informacion sobre el anuncio
		 */
		
		String anuncioInfo = "Id: "+this.id + " Titulo: " + this.titulo + " Cuerpo: " + this.cuerpo + " Propietario: " + this.usuario.getNombre()+ " Estado: " + this.getEstado().getEstados() + "\n"+"Destinatarios: "; // Another way to concat strings
		//Actualizar.
		/*for(int i=0; i<this.getDestinatarios().size();i++) {
			anuncioInfo=anuncioInfo +"\n" +this.getDestinatarios().get(i).getEmail() ;
		}*/
		return anuncioInfo;
	}

}
