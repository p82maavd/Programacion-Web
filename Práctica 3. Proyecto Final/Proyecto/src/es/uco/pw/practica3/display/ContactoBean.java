package es.uco.pw.practica3.display;

/**
 *  Declaracion de la ContactoBean.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 *
 */
public class ContactoBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String emailUsuario = "";
	private String contrase�aUsuario= "";
	
	/**
	 * Metodo que modifica el email de un usuario
	 * @param email del usuario
	 */
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	/**
	 * Metodo que devuelve el email de un usuario
	 * @return email del usuario
	 */
	public String getEmailUsuario() {
		return emailUsuario;
	}

	/**
	 * Metodo que devuelve la contrase�a de un usuario
	 * @return contrase�a del usuario
	 */
	public String getContrase�aUsuario() {
		return this.contrase�aUsuario;
	}

	/**
	 * Metodo que modifica la contrasela de un usuario
	 * @param contrase�a del usuario
	 */
	public void setContrase�aUsuario(String contrase�aUsuario) {
		this.contrase�aUsuario = contrase�aUsuario;
	}
	
}