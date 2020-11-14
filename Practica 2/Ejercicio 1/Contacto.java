package ejercicio1;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Scanner;
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
	private ArrayList<Interes> intereses;
	
	
	/**
	 * Constructor de la clase Contacto
	 * @param nombre Nombre del Contacto
	 * @param apellidos Apellidos del Contacto
	 * @param fechanacimiento Fecha de nacimiento del Contacto
	 * @param email Email del Contacto
	 * @param intereses Intereses del contacto
	 */
	public Contacto( String email,String nombre, String apellidos, Date fechanacimiento, ArrayList<Interes> intereses) {
		
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechanacimiento = fechanacimiento;
		this.email = email;
		this.intereses=intereses;
		
	}
	
	public Contacto() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		ControlDeErrores control=new ControlDeErrores();
		InteresDAO interesesDAO= InteresDAO.getInstance(null);
		
		Scanner sc = new Scanner(System.in);
        String nuevonombre;
        String nombreaux;

        System.out.print("Introduzca el nuevo nombre: ");
        nombreaux = sc.nextLine();
        while(!(control.esNombre(nombreaux))) {
			System.out.println("No se pueden introducir numeros en el nombre");
			System.out.print("Vuelva a introducir el nombre: ");
			nombreaux=sc.nextLine();
		}
        nuevonombre = nombreaux.substring(0, 1).toUpperCase() + nombreaux.substring(1).toLowerCase();
        this.nombre=nuevonombre;

        
        String nuevoapellido;
        String apellidoaux;
        System.out.print("Introduzca el nuevo apellido: ");
        apellidoaux = sc.nextLine();
        while(!(control.esNombre(apellidoaux))) {
			System.out.println("No se pueden introducir numeros en el apellido");
			System.out.print("Vuelva a introducir el apellido: ");
			apellidoaux=sc.nextLine();
		}
        nuevoapellido = apellidoaux.substring(0, 1).toUpperCase() + apellidoaux.substring(1).toLowerCase();
        this.apellidos=nuevoapellido;
        
		String nuevoemail=new String();
		
		System.out.print("Introduzca el nuevo email: ");
		nuevoemail = sc.nextLine();
			
		while(!(control.esEmail(nuevoemail))) {
			System.out.println("El email debe tener @");
			System.out.print("Vuelva a introducir el email: ");
			nuevoemail=sc.nextLine();
		}
		this.email=nuevoemail;
			
		
		String nuevafecha="01/01/1970";
		System.out.print("Introduzca la nueva fecha de nacimiento(yyyy-mm-dd): ");
	
		Date dnuevafecha = new Date(0);	
		
		int conta=1;
		while(conta!=0) {
			conta=0;
			nuevafecha = sc.nextLine();
			while(!(control.esFecha(nuevafecha))) {
				System.out.println("Formato de la fecha (yyyy-mm-dd)");
				System.out.print("Vuelva a introducir la fecha: ");
				nuevafecha=sc.nextLine();
			}
			dnuevafecha=Date.valueOf(nuevafecha);
		}
		this.fechanacimiento=dnuevafecha;
		
		//ACTUALIZAR
		ArrayList<Interes> interesesaux=new ArrayList<Interes>();
		Integer neweleccion=0;
		int newinteres=0;
		Boolean condicion=true;
		
		while(condicion) {
			
			System.out.println("Seleccione un nuevo interes: ");
			
			if(interesesDAO.getIntereses().size()==0) {
				System.out.println("No existen Intereses");
				break;
			}
				
			
			for (Interes myVar : interesesDAO.getIntereses()) {
				System.out.println(myVar.getId()+". "+myVar.getInteres());
				
			}
			
			
			newinteres= sc.nextInt();
			sc.nextLine();
			
			for (Interes myVar: interesesDAO.getIntereses()) {
				if(myVar.getId()==newinteres) {
					interesesaux.add(myVar);
				}
				
			}
			
			//Control de errores.
			
			
			//interesesaux.add(intereses.getIntereses().get(i-1));
				
			
			
			System.out.println("Desea añadir mas intereses: 1. Si 2. No");
			neweleccion=sc.nextInt();
			sc.nextLine();
			if(neweleccion!=1) {
				condicion=false;
			}
			
		
		}
		this.intereses=interesesaux;
		
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

	public ArrayList<Interes> getIntereses() {
		return intereses;
	}

	public void setIntereses(ArrayList<Interes> intereses) {
		this.intereses = intereses;
	}
	
	/**
	 * Devuelve una lista de los intereses de Contacto
	 * @return lista de los intereses 
	 */
	
	
	
}
