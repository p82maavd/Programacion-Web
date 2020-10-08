package practica1;

import java.util.ArrayList;
import java.util.Date;

import practica1entrega.Contacto;

public class AnuncioFlash extends Anuncio{
	
	private Date fechaInicio;
	private Date fechaFinal;

	public AnuncioFlash(int id, String titulo, String cuerpo, Contacto usuario, ArrayList<Contacto> destinatarios) {
		super(id, titulo, cuerpo, usuario, destinatarios);
		// TODO Auto-generated constructor stub
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.destinatarios=destinatarios;
		//Destinatarios como General
		//Poner fecha inicio y final.
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
