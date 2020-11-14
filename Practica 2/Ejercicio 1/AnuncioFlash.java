package ejercicio1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Declaracion de la clase AnuncioFlash.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 * 
 */
public class AnuncioFlash extends Anuncio{
	
	
	private static final long serialVersionUID = 1L;
	private Date fechaInicio;
	private Date fechaFinal;
	

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

	public AnuncioFlash(int id, String titulo, String cuerpo, Contacto usuario, Estados estado,Date fechaInicio, Date fechaFinal) {
		super(id, titulo, cuerpo, usuario, estado);
		
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.estado=estado;
		this.fechaInicio=fechaInicio;
		this.fechaFinal=fechaFinal;
		
	}

	/**
	 * Devuelve la fecha en la que se va a publicar AnuncioFlash
	 * @return fecha de publicacion
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	/**
	 * Modifica la fecha en la que se va a publicar AnuncioFlash
	 * @param fechaInicio
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	/**
	 * Devuelve la fecha en la que se retira AnuncioFlash del tablon
	 * @return fecha de retiro
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}
	
	/**
	 * Modifica la fecha en la que se retira AnuncioFlash del tablon
	 * @param fechaFinal
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
	/**
	 * Este método se encarga de convertir la instancia de la clase AnuncioFlash a string
	 * @return string cadena con informacion del anuncio
	 */
	public String tooString() {
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
		String fechaInicio=formatter.format(this.getFechaInicio());
		String fechaFinal=formatter.format(this.getFechaFinal());
		
		
		String anuncioInfo = "Id: "+this.id + " Titulo: " + this.titulo + " Cuerpo: " + this.cuerpo + " Propietario: " + this.usuario.getNombre()+ " Estado: "+ this.getEstado().getEstados() +" Fecha de inicio: " + fechaInicio + " Fecha de finalizacion " + fechaFinal+"\n" +"Destinatarios: "; // Another way to concat strings
		//ACTUALIZAR
		/*for(int i=0; i<this.getDestinatarios().size();i++) {
			anuncioInfo=anuncioInfo + "\n"+this.getDestinatarios().get(i).getEmail();
		}*/
		return anuncioInfo;
	

	}

}
