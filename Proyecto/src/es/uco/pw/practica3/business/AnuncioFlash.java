package es.uco.pw.practica3.business;


import java.sql.Date;
import java.sql.Timestamp;

/**
 * Declaracion de la clase AnuncioFlash.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 * 
 */
public class AnuncioFlash extends Anuncio{
	
	
	private static final long serialVersionUID = 1L;
	private Timestamp fechaInicio;
	private Timestamp fechaFinal;
	

	/**
	 * Constructor de la clase AnuncioFlash
	 * @param id de AnuncioFlash
	 * @param titulo de AnuncioFlash
	 * @param cuerpo de AnuncioFlash
	 * @param usuario de AnuncioFlash
	 * @param destinatarios de AnuncioFlah
	 * @param estado de AnuncioFlash
	 * @param fechaInicio de AnuncioFlash
	 * @param fechaFinal de AnuncioFlash
	 */

	public AnuncioFlash(int id, String titulo, String cuerpo, String usuario, Estados estado,Date fecha,Timestamp fechaInicio, Timestamp fechaFinal) {
		super(id, titulo, cuerpo, usuario, estado);
		
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.estado=estado;
		this.fecha=fecha;
		this.fechaInicio=fechaInicio;
		this.fechaFinal=fechaFinal;
		
	}

	/**
	 * Devuelve la fecha en la que se va a publicar AnuncioFlash
	 * @return fecha de publicacion
	 */
	public Timestamp getFechaInicio() {
		return fechaInicio;
	}
	
	/**
	 * Modifica la fecha en la que se va a publicar AnuncioFlash
	 * @param fechaInicio
	 */
	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	/**
	 * Devuelve la fecha en la que se retira AnuncioFlash del tablon
	 * @return fecha de retiro
	 */
	public Timestamp getFechaFinal() {
		return fechaFinal;
	}
	
	/**
	 * Modifica la fecha en la que se retira AnuncioFlash del tablon
	 * @param fechaFinal
	 */
	public void setFechaFinal(Timestamp fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
	/**
	 * Este metodo se encarga de convertir la instancia de la clase AnuncioFlash a string
	 * @return string cadena con informacion del anuncio
	 */
	public String tooString() {
		
		String anuncioInfo = "Id: "+this.id + " Titulo: " + this.titulo + " Cuerpo: " + this.cuerpo + " Propietario: " + getUsuario()+ " Estado: "+ this.getEstado().getEstados() +" Fecha de inicio: " + fechaInicio + " Fecha de finalizacion " + fechaFinal+"\n";
		
		return anuncioInfo;
	

	}

}
