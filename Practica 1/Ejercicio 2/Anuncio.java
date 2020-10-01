	package practica1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import practica1entrega.Contacto;

public class Anuncio {
	
	private int id;
	private String titulo;
	private String cuerpo;
	private Contacto usuario;
	private ArrayList<Contacto> destinatarios;
	private Date fecha;
	
	public Anuncio(int id, String titulo, String cuerpo, Contacto usuario, ArrayList<Contacto> destinatarios) {
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.destinatarios=destinatarios;
		  
		Date fecha=new Date(System.currentTimeMillis());
		this.fecha= fecha;
		
	
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public Contacto getUsuario() {
		return usuario;
	}

	public void setUsuario(Contacto usuario) {
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String toString() {
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		String fecha=formatter.format(this.fecha);
		
		String anuncioInfo = "Anuncio: " + this.titulo + " " + this.cuerpo + " " + this.usuario+ " "+fecha; // Another way to concat strings
		return anuncioInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Contacto> getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(ArrayList<Contacto> destinatarios) {
		this.destinatarios = destinatarios;
	}
	
	
}
