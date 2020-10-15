package practica1;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class Anuncio implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Estados {
		Editado("Editado",1), 
		En_espera("En espera",2), 
		Publicado("Publicado",3), 
		Archivado("Archivado",4);
		
		private String estado;
		private int id;
		
		Estados(String string, int i) {
			this.setEstados(string);
			this.setId(i);
		}
		public String getEstados() {
			return estado;
		}
		public void setEstados(String estado) {
			this.estado = estado;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}

	}
	
	//Propiedades Comunes
	
	protected int id;
	protected String titulo;
	protected String cuerpo;
	protected Contacto usuario;
	protected ArrayList<Contacto> destinatarios;
	protected Date fecha;
	protected Estados estado;
	
	public Anuncio(int id, String titulo, String cuerpo, Contacto usuario, ArrayList<Contacto> destinatarios, Estados estado) {
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.destinatarios=destinatarios;
		  
		Date fecha=new Date(System.currentTimeMillis());
		this.fecha= fecha;
		this.estado= estado;
		
	
	}
	
	public Anuncio() {
		
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
	
	public Estados getEstado() {
		return estado;
	}

	public void setEstado(Estados estado) {
		this.estado = estado;
	}
	//Esto ver como administrarlo luego.
	public String tooString() {
		
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		String fecha=formatter.format(this.fecha);
		
		String anuncioInfo = "Id: "+this.id + " Titulo: " + this.titulo + " Cuerpo: " + this.cuerpo + " Propietario: " + this.usuario.getNombre()+ " Fecha: "+fecha; // Another way to concat strings
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
