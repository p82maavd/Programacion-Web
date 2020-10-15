package practica1;


/**
 * 
 * @author Damian Martinez
 * @author Daniel Ortega
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Contacto implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String apellidos;
	private Date fechanacimiento;
	private String email;
	private ArrayList<String> intereses;
	
	public Contacto(String nombre, String apellidos, Date fechanacimiento, String email, ArrayList<String> intereses) {
		
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechanacimiento = fechanacimiento;
		this.email = email;
		this.intereses = intereses;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<String> getIntereses() {
		return intereses;
	}

	public void setIntereses(ArrayList<String> intereses) {
		this.intereses = intereses;
	}
	
	
}
