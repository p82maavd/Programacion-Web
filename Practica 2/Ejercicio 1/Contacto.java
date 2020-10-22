package ejercicio1;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
/**
 * 
 * Declaracion de la clase Contacto.
 * @author Damian Martinez
 * @author Daniel Ortega
 */
public class Contacto implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String apellidos;
	private Date fechanacimiento;
	private String email;
	private ArrayList<String> intereses;
	
	/**
	 * Constructor de la clase Contacto
	 * @param nombre Nombre del Contacto
	 * @param apellidos Apellidos del Contacto
	 * @param fechanacimiento Fecha de nacimiento del Contacto
	 * @param email Email del Contacto
	 * @param intereses Intereses del contacto
	 */
	public Contacto( String email,String nombre, String apellidos, Date fechanacimiento, ArrayList<String> intereses) {
		
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechanacimiento = fechanacimiento;
		this.email = email;
		this.intereses = intereses;
	}

	/**
	 * Devuelve el nombre de Contacto
	 * @return nombre de Contacto
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Modifica el nombre de Contacto
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Devuelve los apellidos de Contacto
	 * @return apellidos de Contacto
	 */
	public String getApellidos() {
		return apellidos;
	}
	
	/**
	 * Modifica los apellidos de Contacto
	 * @param apellidos
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	/**
	 * Devuelve la fecha de nacimiento de Contacto
	 * @return fecha de nacimiento de Contacto
	 */
	public Date getFechanacimiento() {
		return fechanacimiento;
	}
	
	/**
	 * Modifica la fecha de nacimiento de Contacto
	 * @param fechanacimiento
	 */
	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}
	
	/**
	 * Devuelve el email de Contacto
	 * @return email de Contacto
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Modifica el email de Contacto
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Devuelve una lista de los intereses de Contacto
	 * @return lista de los intereses 
	 */
	public ArrayList<String> getIntereses() {
		return intereses;
	}
	
	/**
	 * Modifica los intereses de Contacto
	 * @param intereses
	 */
	public void setIntereses(ArrayList<String> intereses) {
		this.intereses = intereses;
	}
	
	
}
