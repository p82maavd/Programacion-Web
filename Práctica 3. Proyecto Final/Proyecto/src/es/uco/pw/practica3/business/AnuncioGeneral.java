package es.uco.pw.practica3.business;


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
	public AnuncioGeneral(int id, String titulo, String cuerpo, String usuario, Date fecha, Estados estado) {
		super(id, titulo, cuerpo, usuario, estado);
		
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.fecha=fecha;
		this.estado=estado;
		
	}
	
	/**
	 * Este metodo se encarga de convertir la instancia de la clase AnuncioGeneral a string
	 * @return string
	 */
	public String tooString() {
		
		
		String anuncioInfo = "Id: "+this.id + " Titulo: " + this.titulo + " Cuerpo: " + this.cuerpo + " Propietario: " + getUsuario()+ " Estado: "+ this.getEstado().getEstados()+"\n"; 
		
		return anuncioInfo;
	}
	

}

