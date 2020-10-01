
import java.io.EOFException;
import java.io.IOException;
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
//Cambiar dependiendo de donde lo tengas guardado
import practica1entrega.Contacto;
import practica1entrega.Contacto.Intereses;

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
		
		ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream( "fich.dat" ));
		Date auxi= new Date();
        ArrayList<Intereses> aux=new ArrayList<Intereses>();
		Contacto auxc=new Contacto("Auxiliar","Auxiliar",auxi, "auxiliar@hotmail.es",aux);
		
		for(int i=0; i<this.listaContactos.size();i++) {
			
			auxc=this.listaContactos.get(i);
	        file.writeObject(auxc);
			
		}
        
        	file.close();
	}
	
	public void consultarContacto(Contacto e) {
		String cadena=new String();
		System.out.println("Nombre: "+e.getNombre()+" Apellidos: "+ e.getApellidos()+" Email: "+e.getEmail()+" Fecha de Nacimiento: "+ e.getFechanacimiento());
		for(int i=0; i<e.getIntereses().size();i++) {
			cadena=e.getIntereses().get(i).getInteres();
			System.out.println(cadena);
		}
	}
	
	public void imprimirDatos() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		for(int i=0; i<this.listaContactos.size();i++) {
			
			consultarContacto(this.listaContactos.get(i));
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
			
			
			Boolean email= true;
			String nuevoemail=new String();
			
			while(email) {
				
				System.out.print("Introduzca el email: ");
				nuevoemail = sc.nextLine();
				
				if(this.listaContactos.size()==0) {
					email=false;
					continue;
				}
				
				for (Contacto myVar : this.listaContactos) {
					if(nuevoemail.equals(myVar.getEmail())) {
							
					}
					else {
						email=false;
					}
				}
				if(email) {
					System.out.println("Dicho email esta ya en uso");
				}
				
			}
			
			
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
			
			
			ArrayList<Intereses> intereses=new ArrayList<Intereses>();
			Integer neweleccion=0;
			String newinteres=new String();
			Boolean condicion=true;
			Scanner alt = new Scanner(System.in);
			
			while(condicion) {
				
				System.out.println("Seleccione un nuevo interes: ");
				
				for (Intereses myVar : Intereses.values()) {
					System.out.println(myVar.getId()+" "+myVar.getInteres());
				}
				
				newinteres= alt.nextLine();
				
				int foo;
				try {
				   foo = Integer.parseInt(newinteres);
				}
				catch (NumberFormatException e)
				{
				   foo = 0;
				}
				
				for (Intereses myVar : Intereses.values()) {
					if(foo==myVar.getId()) {
						intereses.add(myVar);
					}
				}

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
		//Esto esta sin implementar. Solo para limpiar el archivo.
		this.listaContactos.remove(index);
		this.guardarDatos();
		
	}
	
	public void actualizarContacto() {
		
		//Esta para poner de parametro la funcion buscarContacto(), de mientras coge el primero.
		
		Contacto e=this.listaContactos.get(0);
		
		System.out.println("Que quieres modificar: 1. Nombre 2. Apellidos 3. Email 4. Fecha Nacimiento 5. Intereses");
		Scanner sc = new Scanner(System.in);
		Scanner sl = new Scanner(System.in);
		System.out.print("Introduzca un número entero: ");
		Integer a = sc.nextInt();
		
		
		if(a==1) {
			String nuevonombre;
			System.out.print("Introduzca el nuevo nombre: ");
			nuevonombre = sl.nextLine();
			e.setNombre(nuevonombre);
		}
		
		else if(a==2) {
			String nuevoapellido;
			System.out.print("Introduzca el nuevo apellido: ");
			nuevoapellido = sl.nextLine();
			e.setApellidos(nuevoapellido);
		}
		
		else if(a==3) {
			String nuevoemail;
			System.out.print("Introduzca el nuevo email: ");
			nuevoemail = sl.nextLine();
			e.setEmail(nuevoemail);
		}
		
		else if(a==4) {
			String nuevafecha="01/01/1970";
			System.out.print("Introduzca la nueva fecha de nacimiento(dd/mm/yyyy): ");
			nuevafecha = sl.nextLine();
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 
			Date dnuevafecha = null;
			try {
				dnuevafecha = formatter.parse(nuevafecha);
			} catch (ParseException e1) {
				
				e1.printStackTrace();
			}
			
			e.setFechanacimiento(dnuevafecha);
		}
		
		else if(a==5) {
			ArrayList<Intereses> intereses;
			intereses=e.getIntereses();
			
			Integer eleccion;
			System.out.println("¿Desea eliminar algun interes? 1.Si 2.No");
			eleccion = sc.nextInt();
			
			
			if(eleccion==1) {
				System.out.println("Actuales Intereses");
				for(int i=1; i<=intereses.size();i++) {
				
					System.out.println(i+intereses.get(i-1).getInteres());
				}
				
				System.out.println("Que interes desea eliminar");
				
				
				Integer linea;
				linea = sc.nextInt();
				intereses.remove(linea-1);
			}
			else {
				
				System.out.println("Seleccione un nuevo interes: ");
				String newinteres=new String();
				for (Intereses myVar : Intereses.values()) {
					System.out.println(myVar.getId()+" "+myVar.getInteres());
				}
				//Probar, posible fallo por scanner
				newinteres= sc.nextLine();
				
				int foo;
				try {
				   foo = Integer.parseInt(newinteres);
				}
				catch (NumberFormatException es)
				{
				   foo = 0;
				}
				
				for (Intereses myVar : Intereses.values()) {
					if(foo==myVar.getId()) {
						intereses.add(myVar);
					}
				}
			}
			
			
		}
		
		//sc.close();
		
	}

}
