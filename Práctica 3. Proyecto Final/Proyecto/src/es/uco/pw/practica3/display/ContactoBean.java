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
	private String contraseñaUsuario= "";
	
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
	 * Metodo que devuelve la contraseña de un usuario
	 * @return contraseña del usuario
	 */
	public String getContraseñaUsuario() {
		return this.contraseñaUsuario;
	}

	/**
	 * Metodo que modifica la contrasela de un usuario
	 * @param contraseña del usuario
	 */
	public void setContraseñaUsuario(String contraseñaUsuario) {
		this.contraseñaUsuario = contraseñaUsuario;
	}
	
}