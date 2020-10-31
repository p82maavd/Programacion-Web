package ejercicio1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *  Declaracion de la clase abstracta Anuncio.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 *
 */

public abstract class Anuncio implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * Enumeracion de los distinto estados de un anuncio.
	 *
	 */

	public enum Estados {
		Editado("Editado",1), 
		En_espera("En espera",2), 
		Publicado("Publicado",3), 
		Archivado("Archivado",4);
		
		private String estado;
		private int id;
		
		/**
		 * Constructor de Estados
		 * @param cadena que constituye el estado de Anuncio
		 * @param entero que señala el id en la enumeracion
		 */
		Estados(String string, int i) {
			this.setEstados(string);
			this.setId(i);
		}
		
		/**
		 * Devuelve el estado de Anuncio
		 * @return estado de Anuncio
		 */
		public String getEstados() {
			return estado;
		}
		
		/**
		 * Modifica el estado de Anuncio
		 * @param estado
		 */
		public void setEstados(String estado) {
			this.estado = estado;
		}
		
		/**
		 * Devuelve el id del estado en la enumeracion
		 * @return id del estado
		 */
		public int getId() {
			return id;
		}
		
		/**
		 * Modifica el id del estado 
		 * @param id
		 */
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
	
	/**
	 * Constructor de la clase Anuncio
	 * @param id de Anuncio
	 * @param titulo de Anuncio
	 * @param cuerpo de Anuncio
	 * @param usuario de Anuncio
	 * @param destinatarios de Anuncio
	 * @param estado de Anuncio
	 */
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
	
	/**
	 * Constructor de la clase Anuncio
	 */
	public Anuncio() {
		
	}
	
	/**
	 * Devuelve el titulo de Anuncio
	 * @return titulo de Anuncio
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * Modifica el titulo de Anuncio
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/**
	 * Devuelve el cuerpo de Anuncio
	 * @return cuerpo de Anuncio
	 */
	public String getCuerpo() {
		return cuerpo;
	}
	
	/**
	 * Modifica el cuerpo de Anuncio
	 * @param cuerpo
	 */
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	
	/**
	 * Devuelve el usuario propietario de Anuncio
	 * @return usuario propietario
	 */
	public Contacto getUsuario() {
		return usuario;
	}
	
	/**
	 * Modifica el usuario propietario de Anuncio
	 * @param usuario
	 */
	public void setUsuario(Contacto usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Devuelve la fecha de publicacion de Anuncio
	 * @return fecha de publicacion
	 */
	public Date getFecha() {
		return fecha;
	}
	
	/**
	 * Modifica la fecha de publicacion de Anuncio
	 * @param fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	/**
	 * Devuelve el estado de Anuncio
	 * @return estado de Anuncio
	 */
	public Estados getEstado() {
		return estado;
	}
	
	/**
	 * Modifica el estado de Anuncio
	 * @param estado
	 */
	public void setEstado(Estados estado) {
		this.estado = estado;
	}
	
	/**
	 * Devuelve el id de Anuncio
	 * @return id de Anuncio
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Modifica el id de Anuncio
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Devuelve una lista con los destinatarios para los que va destinado Anuncio
	 * @return destinatarios de Anuncio
	 */
	public ArrayList<Contacto> getDestinatarios() {
		return destinatarios;
	}
	
	/**
	 * Modifica la lista de destinatarios de Anuncio
	 * @param destinatarios
	 */
	public void setDestinatarios(ArrayList<Contacto> destinatarios) {
		this.destinatarios = destinatarios;
	}
	
}