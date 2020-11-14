package ejercicio1;

import java.sql.Date;

/**
 * Declaracion de la clase AnuncioGeneral.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 * 
 */
public class AnuncioGeneral extends Anuncio{

	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase AnuncioGeneral
	 * @param id de AnuncioGeneral
	 * @param titulo de AnuncioGeneral
	 * @param cuerpo de AnuncioGeneral
	 * @param usuario de AnuncioGeneral
	 * @param destinatarios de AnuncioGeneral
	 * @param estado de AnuncioGeneral
	 */
	public AnuncioGeneral(int id, String titulo, String cuerpo, Contacto usuario, Date fecha, Estados estado) {
		super(id, titulo, cuerpo, usuario, estado);
		
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.fecha=fecha;
		this.estado=estado;
		
	}
	
	/**
	 * Este método se encarga de convertir la instancia de la clase AnuncioGeneral a string
	 * @return string
	 */
	public String tooString() {
		
		String anuncioInfo = "Id: "+this.id + " Titulo: " + this.titulo + " Cuerpo: " + this.cuerpo + " Propietario: " + this.usuario.getNombre()+ " Estado: "+ this.getEstado().getEstados()+"\n"+ "Destinatarios: "; // Another way to concat strings
		//actualizar
		/*for(int i=0; i<this.getDestinatarios().size();i++) {
			anuncioInfo=anuncioInfo + "\n"+this.getDestinatarios().get(i).getEmail();
		}*/
		return anuncioInfo;
	}
	

}

