package practica1;

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



public class GestorContactos {
	
	private static GestorContactos instance =null;
	
	private Intereses claseintereses=Intereses.getInstance();
	
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
	
	
	public ArrayList <Contacto> getContactos(){
		
		return this.listaContactos;
	}
	
	//Actualizar estas funciones teniendo en cuenta que pueden devolver varios contactos y al final seleccionar uno. Menos en email que es unico y en intereses ya esta creo, falta comprobarlo.
	//Creo que estaria mejor separarlo en funciones. Si sobra tiempo.
	public Contacto buscarContacto()  {
		
		ArrayList<Contacto> aux=new ArrayList<Contacto>();
		
		System.out.println("Elige parametro de busqueda: ");
		System.out.println("1. Nombre y Apellidos");
		System.out.println("2. Email");
		System.out.println("3. Intereses");
		System.out.println("4. Fecha de nacimiento");
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca un número entero: ");
		Integer a = sc.nextInt();
		sc.nextLine();
		Contacto buscado=null;
		
		if(a==1) {
			
			String nombreaux;
			String apellidosaux;
			int n = 0;
			System.out.print("Introduzca el nombre de la persona a buscar: ");
			nombreaux = sc.nextLine();
			System.out.print("Introduzca sus apellidos: ");
			apellidosaux = sc.nextLine();
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
			emailaux = sc.nextLine();
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
		
		//Comprobar
		else if(a==3) {
			Integer cont=0;
			
			//Imprime todos los intereses
			
			for(String s: claseintereses.getIntereses()) {
				System.out.println(cont.toString()+s);
				cont++;
			}
			
			
			System.out.print("Indique que interes buscar: ");
		    int seleccion = sc.nextInt();
		    sc.nextLine();
		    String interesaux=new String();
			
			for(int i=0; i<claseintereses.getIntereses().size();i++) {
				
				if(seleccion==i) {
					
					interesaux=claseintereses.getIntereses().get(i);
					
				}
				
			}
			
			//Busca los contactos que tengan el interes seleccionado arriba
			
			for(Contacto d: this.listaContactos) {
				
				for(int i=0;i<d.getIntereses().size();i++) {
					
					if(d.getIntereses().get(i).equals(interesaux)) {
						
						aux.add(d);
						break;
						
					}
					
				}
				
			}
			
			//Imprime todos los contactos con dicho interes.
			
			for(Integer i=0;i<aux.size();i++) {
				System.out.println(i.toString()+"Nombre: "+aux.get(i).getNombre()+" Email: "+ aux.get(i).getEmail());
				
			}
			
			System.out.println("Selecciona el contacto buscado");
				
			
			int seleccion2=sc.nextInt();
			sc.nextLine();
			
			
			for(int i=0;i<aux.size();i++) {
				
				if(i==seleccion2) {
					System.out.println("Contacto Seleccionado");
					buscado=aux.get(i);
					
					break;
				}
			}
			

			return buscado;
			

		}
		
		//Sin revisar
		else if(a==4) {
			String fechaaux="01/01/1970";
			int n = 0;
			System.out.print("Indique la fecha de nacimiento(dd/mm/yyyy) a buscar: ");
			fechaaux = sc.nextLine();
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
		//Try catch en el main.
		return null;
	}

	/**
	 * Este método se encarga de dar de alta a un contacto.
	*/
	
	public void darAlta() throws IOException {
		
			Scanner sc = new Scanner(System.in);
	        String nuevonombre;
	        String nombreaux;
	
	        System.out.print("Introduzca el nuevo nombre: ");
	        nombreaux = sc.nextLine();
	        nuevonombre = nombreaux.substring(0, 1).toUpperCase() + nombreaux.substring(1);
	
	        String nuevoapellido;
	        String apellidoaux;
	        System.out.print("Introduzca el nuevo apellido: ");
	        apellidoaux = sc.nextLine();
	        nuevoapellido = apellidoaux.substring(0, 1).toUpperCase() + apellidoaux.substring(1);
			
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
			 
			Date dnuevafecha = new Date();
			try {
				dnuevafecha = formatter.parse(nuevafecha);
			} catch (ParseException e1) {
				System.out.println("Error con la fecha");
				e1.printStackTrace();
			}
			
			
			ArrayList<String> interesesaux=new ArrayList<String>();
			Integer neweleccion=0;
			int newinteres=0;
			Boolean condicion=true;
			
			int cont =1;
			while(condicion) {
				
				System.out.println("Seleccione un nuevo interes: ");
				
				if(claseintereses.getIntereses().size()==0) {
					System.out.println("No existen Intereses");
					break;
				}
				
				for (String myVar : claseintereses.getIntereses()) {
					System.out.println(cont+" "+myVar);
					cont++;
				}
				cont=1;
				
				newinteres= sc.nextInt();
				sc.nextLine();
				
			
				
				for (int i=1;i<=claseintereses.getIntereses().size();i++) {
					if(newinteres==i) {
						
						interesesaux.add(claseintereses.getIntereses().get(i-1));
					}
				}
				
				System.out.println("Desea añadir mas intereses: 1. Si 2. No");
				neweleccion=sc.nextInt();
				sc.nextLine();
				if(neweleccion!=1) {
					condicion=false;
				}
				
			
			}
			
			//NO CERRAR SCANNERS, NO FUNCIONA EL MAIN.
			//alt.close();
			//sc.close();
			
			
			Contacto e=new Contacto(nuevonombre,nuevoapellido,dnuevafecha,nuevoemail.toLowerCase(),interesesaux);
			
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
	 * @throws IOException 
	 * @throws FileNotFoundException 
	*/
	
	public void actualizarContacto(Contacto e) throws FileNotFoundException, IOException {
		
		
		System.out.println("Que quieres modificar: 1. Nombre 2. Apellidos 3. Email 4. Fecha Nacimiento 5. Intereses");
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca un número entero: ");
		Integer a = sc.nextInt();
		sc.nextLine();
		
		if(a==1) {
			String nuevonombre;
			System.out.print("Introduzca el nuevo nombre: ");
			nuevonombre = sc.nextLine();
			e.setNombre(nuevonombre);
		}
		
		else if(a==2) {
			String nuevoapellido;
			System.out.print("Introduzca el nuevo apellido: ");
			nuevoapellido = sc.nextLine();
			e.setApellidos(nuevoapellido);
		}
		
		else if(a==3) {
			String nuevoemail;
			System.out.print("Introduzca el nuevo email: ");
			nuevoemail = sc.nextLine();
			e.setEmail(nuevoemail);
		}
		
		else if(a==4) {
			String nuevafecha=new String();
			System.out.print("Introduzca la nueva fecha de nacimiento(dd/mm/yyyy): ");
			nuevafecha = sc.nextLine();
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 
			Date dnuevafecha = new Date();
			
			
			int cont=1;
			while(cont!=0) {
				cont=0;
				try {
					nuevafecha = sc.nextLine();
					dnuevafecha = formatter.parse(nuevafecha);
				} catch (ParseException e1) {
					System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy hh:mm:ss): ");
					
					cont++;
				}
			}
			
			e.setFechanacimiento(dnuevafecha);
		}
		
		else if(a==5) {
			ArrayList<String> intereses;
			intereses=e.getIntereses();
			
			Integer eleccion;
			System.out.println("¿Desea eliminar algun interes? 1.Si 2.No");
			eleccion = sc.nextInt();
			
			
			if(eleccion==1) {
				System.out.println("Actuales Intereses");
				for(int i=1; i<=intereses.size();i++) {
				
					System.out.println(i+intereses.get(i-1));
				}
				
				System.out.println("Que interes desea eliminar");
				
				
				Integer linea;
				linea = sc.nextInt();
				sc.nextLine();
				intereses.remove(linea-1);
				e.setIntereses(intereses);
			}
			else {
				
				añadirIntereses(e);
			}
			
			
		}
		
		guardarDatos();
		
		//sc.close();
		
	}
	
	public void añadirIntereses(Contacto e) {
		
			ArrayList<String> intereses;
			intereses=e.getIntereses();
			Scanner sc=new Scanner(System.in);
			System.out.println("Seleccione un nuevo interes: ");
			int newinteres=0;
			int cont=1;
			for (String myVar : claseintereses.getIntereses()) {
				System.out.println(cont+" "+myVar);
				cont++;
			}
			
			newinteres= sc.nextInt();
			sc.nextLine();
			
			for (int i=1;i<=claseintereses.getIntereses().size();i++) {
				if(newinteres==i) {
					intereses.add(claseintereses.getIntereses().get(i-1));
				}
			}
			e.setIntereses(intereses);
	}
	
	/**
	 * Este método se encarga de leer los contactos del fichero.
	 * @return Instancia única de GestorContactos.
	*/
	public void cargarDatos() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		try {
		ObjectInputStream file = new ObjectInputStream(new FileInputStream("fich.dat"));
		Contacto clase=null;
		do {
			try {
				 clase = (Contacto) file.readObject(); 
		         } catch (EOFException e) {
		            System.out.println("");
		            System.out.println("Contactos cargados correctamente");
		            break;
		         } 
			
			this.listaContactos.add(clase);
			
		}
		while(clase!=null) ;
			         	
		
		file.close();
		}catch(FileNotFoundException e) {
			guardarDatos();
		}
		 
		
	}
	
	/**
	 * Este método se encarga de guardar los contactos en el fichero de datos.
	*/
	public void guardarDatos() throws FileNotFoundException, IOException {
		
		ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream( "fich.dat" ));
		
		Contacto auxc=null;
		
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
		System.out.println("");
	
	}
	
	/**
	 * Este método se encarga de imprimir los datos de un contacto.
	*/
	public void consultarContacto(Contacto e) {
		String cadena=new String();
		//Poner esto para cuando quieras imprimir una Date. Fijarse en el println de abajo como esta.
        SimpleDateFormat objSDF = new SimpleDateFormat("dd/MM/yyyy"); 
		
		System.out.println("Nombre: "+e.getNombre()+" Apellidos: "+ e.getApellidos()+" Email: "+e.getEmail()+" Fecha de Nacimiento: "+ objSDF.format(e.getFechanacimiento()));
		for(int i=0; i<e.getIntereses().size();i++) {
			cadena=e.getIntereses().get(i);
			
			System.out.println(cadena);
		}
	}

}

