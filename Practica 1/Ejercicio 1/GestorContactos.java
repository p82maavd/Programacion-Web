package practica1entrega;

/**
 * 
 * @author Damian Martinez
 * @author Daniel Ortega
 */


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.EOFException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import practica1entrega.Contacto.Intereses;


public class GestorContactos {
	
	private static GestorContactos instance =null;
	
	private ArrayList <Contacto> listaContactos;

	/**
	 * Este método se encarga de crear una instancia en el caso de que no haya una ya creada. Patron de diseño Singleton
	 * @return Instancia única de GestorContactos.
	*/
	public static GestorContactos getInstance() {
		
		if(instance==null) {
			instance=new GestorContactos();
		}
		return instance;
	}
	
	/**
	 * Constructor de la clase GestorContactos.
	*/
	private GestorContactos() {
		
		this.listaContactos = new ArrayList<Contacto>();
		
	}
	
	/**
	 * Este método se encarga de dar de alta a un contacto.
	*/
	
	public Contacto buscarContacto()  {
		
		System.out.println("Elige parametro de busqueda: ");
		System.out.println("1. Nombre y Apellidos");
		System.out.println("2. Email");
		System.out.println("3. Intereses");
		System.out.println("4. Fecha de nacimiento");
		Scanner sc = new Scanner(System.in);
		Scanner sl = new Scanner(System.in);
		Scanner sv = new Scanner(System.in);
		System.out.print("Introduzca un número entero: ");
		Integer a = sc.nextInt();
		
		
		if(a==1) {
			
			String nombreaux;
			String apellidosaux;
			int n = 0;
			System.out.print("Introduzca el nombre de la persona a buscar: ");
			nombreaux = sl.nextLine();
			System.out.print("Introduzca sus apellidos: ");
			apellidosaux = sv.nextLine();
			for(int i=0; i<this.listaContactos.size();i++) {
				 if(this.listaContactos.get(i).getNombre().equals(nombreaux) && this.listaContactos.get(i).getApellidos().equals(apellidosaux)) {
					 n = n + 1;
					 Contacto e=this.listaContactos.get(i);
					 return e;
				 }
				}
			if(n==0) {
				System.out.print("No se ha encontrado ninguna persona que se llame así ");
			}
		}
		
		else if(a==2) {
			String emailaux;
			int n = 0;
			System.out.print("Indique el email a buscar: ");
			emailaux = sl.nextLine();
			for(int i=0; i<this.listaContactos.size();i++) {
			 if(this.listaContactos.get(i).getEmail().equals(emailaux)) {
				 n = n + 1;
				 Contacto e=this.listaContactos.get(i);
				 return e;
			 }
			}
			if(n==0) {
				System.out.print("No se ha encontrado ninguna persona con ese email.");
			}

		}
		
		else if(a==3) {
			ArrayList<Intereses> intereses;
			int n = 0;
			String interesaux;
			System.out.print("Indique el interes a buscar: ");
		    interesaux = sl.nextLine();
			System.out.println("¿Desea eliminar algun interes? 1.Si 2.No");
			for(int i=0; i<this.listaContactos.size();i++) {
				intereses=this.listaContactos.get(i).getIntereses();
				for(int j=1; j<=intereses.size();j++) {
					if(intereses.get(i).getInteres().equals(interesaux)) {
						n = n + 1;
						Contacto e=this.listaContactos.get(i);
						return e;
					}
				}
			}
			if(n==0) {
				System.out.print("No se ha encontrado ninguna persona con ese interes.");
			}

		}
		
		else if(a==4) {
			String fechaaux="01/01/1970";
			int n = 0;
			System.out.print("Indique la fecha de nacimiento(dd/mm/yyyy) a buscar: ");
			fechaaux = sl.nextLine();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 
			Date dnuevafecha = null;
			try {
				dnuevafecha = formatter.parse(fechaaux);
			} catch (ParseException e1) {
				
				e1.printStackTrace();
			}
			for(int i=0; i<this.listaContactos.size();i++) {
				 if(this.listaContactos.get(i).getFechanacimiento().equals(dnuevafecha)) {
					 n = n + 1;
					 Contacto e=this.listaContactos.get(i);
					 return e;
					 
				 }
				}
			if(n==0) {
				System.out.print("No se ha encontrado ninguna persona que naciera ese día.");
			}

		}
		sc.close();
		sv.close();
		sl.close();
		return null;
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
			
			//NO CERRAR SCANNERS, NO FUNCIONA EL MAIN.
			//alt.close();
			//sc.close();
			
			Contacto e=new Contacto(nuevonombre,nuevoapellido,dnuevafecha,nuevoemail,intereses);
			
			this.listaContactos.add(e);
			
			this.guardarDatos();
		
	}
	
	/**
	 * Este método se encarga de dar de baja a un contacto.
	 * @param Contacto que desea dar de baja
	*/
	public void darBaja(Contacto e) throws FileNotFoundException, IOException {
		
		for(int i=0; i<this.listaContactos.size();i++) {
			if(e.getEmail().equals(this.listaContactos.get(i).getEmail())) {
				this.listaContactos.remove(i);
			}
		}
		
		this.guardarDatos();
	}
	
	/**
	 * Este método se encarga de actualizar un contacto.
	*/
	public void actualizarContacto(Contacto e) {
		
		
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
			System.out.println("¿Desea eliminar algun inter			es? 1.Si 2.No");
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
				int newinteres=0;
				for (Intereses myVar : Intereses.values()) {
					System.out.println(myVar.getId()+" "+myVar.getInteres());
				}
				
				newinteres= sc.nextInt();
				/*
				int foo;
				try {
				   foo = Integer.parseInt(newinteres);
				}
				catch (NumberFormatException es)
				{
				   foo = 0;
				}
				*/
				
				for (Intereses myVar : Intereses.values()) {
					if(newinteres==myVar.getId()) {
						intereses.add(myVar);
					}
				}
			}
			
			
		}
		
		//sc.close();
		
	}
	
	/**
	 * Este método se encarga de leer los contactos del fichero.
	 * @return Instancia única de GestorContactos.
	*/
	public void cargarDatos() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream file = new ObjectInputStream(new FileInputStream("fich.dat"));
		ArrayList<Intereses> aux=new ArrayList<Intereses>();
		Date auxi= new Date();
		Contacto clase= new Contacto("Auxiliar","Auxiliar",auxi, "auxiliar@hotmail.es",aux);
		
		while(clase!=null) {
			 try {
				 clase = (Contacto) file.readObject(); 
		         } catch (EOFException e) {
		            System.out.println("");
		            System.out.println("Contactos cargados correctamente");
		            break;
		         } 
			
			this.listaContactos.add(clase);        	
		}
		file.close();
		 
		
	}
	
	/**
	 * Este método se encarga de guardar los contactos en el fichero de datos.
	*/
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
	
	/**
	 * Este método se encarga de imprimir los datos de todos los contactos.
	*/
	public void imprimirDatos() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		for(int i=0; i<this.listaContactos.size();i++) {
			
			consultarContacto(this.listaContactos.get(i));
		}
	
	}
	
	/**
	 * Este método se encarga de imprimir los datos de un contacto.
	*/
	public void consultarContacto(Contacto e) {
		String cadena=new String();
		System.out.println("Nombre: "+e.getNombre()+" Apellidos: "+ e.getApellidos()+" Email: "+e.getEmail()+" Fecha de Nacimiento: "+ e.getFechanacimiento());
		for(int i=0; i<e.getIntereses().size();i++) {
			cadena=e.getIntereses().get(i).getInteres();
			
			System.out.println(cadena);
		}
	}

}
