package practica1;

import java.util.ArrayList;
import java.util.Date;


public class AnuncioFlash extends Anuncio{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date fechaInicio;
	private Date fechaFinal;

	public AnuncioFlash(int id, String titulo, String cuerpo, Contacto usuario, ArrayList<Contacto> destinatarios, Estados estado,Date fechaInicio, Date fechaFinal) {
		super(id, titulo, cuerpo, usuario, destinatarios, estado);
		
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.destinatarios=destinatarios;
		this.estado=estado;
		this.fechaInicio=fechaInicio;
		this.fechaFinal=fechaFinal;
		
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

}
