package es.uco.pw.practica3.display;

import java.sql.Date;
import java.util.ArrayList;

import es.uco.pw.practica3.business.Interes;

/**
 *  Declaracion de la clase ContactoCompletoBean.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 *
 */
public class ContactoCompletoBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String emailUsuario = "";
	private String contraseñaUsuario= "";
	private String nombreUsuario="";
	private String apellidosUsuario="";
	private Date fechanacimientoUsuario;
	private ArrayList<Interes> interesesUsuario;

	/**
	 * Devuelve el email del usuario
	 * @return Email del usuari
	 */
	public String getEmailUsuario() {
		return emailUsuario;
	}
	
	/**
	 * Modifica el email del Usuario
	 * @param emailUsuario Email del Usuario
	 */
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario=emailUsuario;
	}
	
	/**
	 * Devuelve la contraseña del usuario
	 * @return Contraseña del usuario
	 */
	public String getContraseñaUsuario() {
		return contraseñaUsuario;
	}

	/**
	 * Modifica la contraseña del usuario
	 * @param contraseñaUsuario Contraseña del usuario
	 */
	public void setContraseñaUsuario(String contraseñaUsuario) {
		this.contraseñaUsuario = contraseñaUsuario;
	}

	/**
	 * Devuelve el nombre del Usuario
	 * @return Nombre del Usuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/** 
	 * Modifica el nombre del Usuario 
	 * @param nombreUsuario Nombre del Usuario
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * Devuelve los apellidos del Usuario
	 * @return Apellidos del usuario.
	 */
	public String getApellidosUsuario() {
		return apellidosUsuario;
	}

	/**
	 * Modifica los apellidos del Usuario
	 * @param apellidosUsuario Apellidos del usuario
	 */
	public void setApellidosUsuario(String apellidosUsuario) {
		this.apellidosUsuario = apellidosUsuario;
	}

	/**
	 * Devuelve la fecha de nacimiento del usuario
	 * @return Fecha de nacimiento del Usuario.
	 */
	public Date getFechanacimientoUsuario() {
		return fechanacimientoUsuario;
	}

	/**
	 * Modifica la fecha de nacimiento del usuario
	 * @param fechanacimientoUsuario Fecha de nacimiento del Usuario
	 */
	public void setFechanacimientoUsuario(Date fechanacimientoUsuario) {
		this.fechanacimientoUsuario = fechanacimientoUsuario;
	}


	/**
	 * Devuelve los intereses del usuario
	 * @return Intereses del usuario
	 */
	public ArrayList<Interes> getInteresesUsuario() {
		return interesesUsuario;
	}


	/**
	 * Modifica los intereses del usuario
	 * @param interesesUsuario Intereses del usuario
	 */
	public void setInteresesUsuario(ArrayList<Interes> interesesUsuario) {
		this.interesesUsuario = interesesUsuario;
	}
	
}