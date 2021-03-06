package Ejercicio1;


/**
 * 
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 * Declaracion de la clase Contacto.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Contacto implements Serializable{
	
	public enum Intereses {
		Leer("Leer",0), 
		Cine("Ir al Cine",1), 
		Futbol("Jugar al futbol",2), 
		Programar("Programar",3);
		
		private String interes;
		private int id;
		
		Intereses(String string, int i) {
			this.setInteres(string);
			this.setId(i);
		}
		public String getInteres() {
			return interes;
		}
		public void setInteres(String interes) {
			this.interes = interes;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}

	}
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String apellidos;
	private Date fechanacimiento;
	private String email;
	private ArrayList<Intereses> intereses;
	
	public Contacto(String nombre, String apellidos, Date fechanacimiento, String email, ArrayList<Intereses> intereses) {
		
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

	public ArrayList<Intereses> getIntereses() {
		return intereses;
	}

	public void setIntereses(ArrayList<Intereses> intereses) {
		this.intereses = intereses;
	}
	
	
}
