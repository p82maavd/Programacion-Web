
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class GestorContactos {
  private static GestorContactos instance =null;
	
	private ArrayList <Contacto> listaContactos;

	//Singleton
	
	public static GestorContactos getInstance() {
		if(instance==null) {
			instance=new GestorContactos();
		}
		return instance;
	}
	//Final Singleton
	
	public GestorContactos() {
		
		this.listaContactos = new ArrayList<Contacto>();
		
  }
	
	public void cargarDatos() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream file = new ObjectInputStream(new FileInputStream("fich.dat"));
		ArrayList<String> aux=new ArrayList<String>();
		Date auxi= new Date();
		Contacto clase= new Contacto("Auxiliar","Auxiliar",auxi, "auxiliar@hotmail.es",aux);
		
		while(clase!=null) {
			 try {
				 clase = (Contacto) file.readObject(); 
		         } catch (EOFException e) {
		            System.out.println("");
		            System.out.println("End of file reached");
		            break;
		         } 
			
			this.listaContactos.add(clase);        	
		}
		file.close();
		 
		
	}
	
	public void guardarDatos() throws FileNotFoundException, IOException {
		
		ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream( "patatilla.dat" ));
		Date auxi= new Date();
        ArrayList<String> aux=new ArrayList<String>();
		Contacto auxc=new Contacto("Auxiliar","Auxiliar",auxi, "auxiliar@hotmail.es",aux);
		
		for(int i=0; i<this.listaContactos.size();i++) {
			
			auxc=this.listaContactos.get(i);
	        file.writeObject(auxc);
			
		}
        
        file.close();
	}
	
	public void imprimirContacto(Contacto e) {
		String cadena=new String();
		System.out.println("Nombre: "+e.getNombre()+" Apellidos: "+ e.getApellidos()+" Email: "+e.getEmail()+" Fecha de Nacimiento: "+ e.getFechanacimiento());
		for(int i=0; i<e.getIntereses().size();i++) {
			cadena=e.getIntereses().get(i);
			System.out.println(cadena);
		}
	}
	
	public void imprimirDatos() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		for(int i=0; i<this.listaContactos.size();i++) {
			
			imprimirContacto(this.listaContactos.get(i));
		}
	
	}
	
	public void darAlta() throws IOException {
		
			Scanner sc = new Scanner(System.in);
			String nuevonombre;
			System.out.print("Introduzca el nuevo nombre: ");
			nuevonombre = sc.nextLine();
			
			
			String nuevoapellido;
			System.out.print("Introduzca el nuevo apellido: ");
			nuevoapellido = sc.nextLine();
			
			
			String nuevoemail;
			System.out.print("Introduzca el nuevo email: ");
			nuevoemail = sc.nextLine();
			
			
			String nuevafecha="01/01/1970";
			System.out.print("Introduzca la nueva fecha de nacimiento(dd/mm/yyyy): ");
			nuevafecha = sc.nextLine();
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 
			Date dnuevafecha = null;
			try {
				dnuevafecha = formatter.parse(nuevafecha);
			} catch (ParseException e1) {
				
				e1.printStackTrace();
			}
			
			
			ArrayList<String> intereses=new ArrayList<String>();
			Integer neweleccion=0;
			String newinteres=new String();
			Boolean condicion=true;
			Scanner alt = new Scanner(System.in);
			
			while(condicion) {
				
				System.out.println("Introduzca un nuevo interes: ");
				
				newinteres= alt.nextLine();
				
				intereses.add(newinteres);
				
				
				System.out.println("Desea añadir mas intereses: 1. Si 2. No");
				neweleccion=alt.nextInt();
				alt.nextLine();
				if(neweleccion!=1) {
					condicion=false;
				}
				
			
			}
			
			Contacto e=new Contacto(nuevonombre,nuevoapellido,dnuevafecha,nuevoemail,intereses);
			
			this.listaContactos.add(e);
			
			this.guardarDatos();
	}
	
	public void darBaja(int index) {
		
		this.listaContactos.remove(index);
		
	}

}
