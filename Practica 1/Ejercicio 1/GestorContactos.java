package Ejercicio1;

/**
 * 
 * @author Damian Martinez
 * @author Daniel Ortega
 * Declaracion de la clase GestorContactos
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

import Ejercicio1.Contacto.Intereses;


public class GestorContactos {
	
	private static GestorContactos instance =null;
	
	private ControlDeErrores control=new ControlDeErrores();
	
	private ArrayList <Contacto> listaContactos;
	
	
	/**
	 * Este método se encarga de crear una instancia en el caso de que no haya una ya creada. Patron de diseño Singleton
	 * @return Instancia única de GestorContactos.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	*/
	public static GestorContactos getInstance() throws ClassNotFoundException, IOException {
		
		if(instance==null) {
			instance=new GestorContactos();
		}
		return instance;
	}
	
	/**
	 * Constructor de la clase GestorContactos.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	*/
	private GestorContactos() throws ClassNotFoundException, IOException {
		
		this.listaContactos = new ArrayList<Contacto>();
		try {
			cargarDatos();
			}catch(FileNotFoundException e) {
				String ubicacion=new String();
				Configuracion config=Configuracion.getInstance(ubicacion);
				ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(config.getProperty("DATA_FILE") ));
		        file.close();
			}
		
	}
		
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
			nombreaux = nombreaux.substring(0, 1).toUpperCase() + nombreaux.substring(1).toLowerCase();
			System.out.print("Introduzca sus apellidos: ");
			apellidosaux = sc.nextLine();
			apellidosaux = apellidosaux.substring(0, 1).toUpperCase() + apellidosaux.substring(1).toLowerCase();
			for(int i=0; i<this.listaContactos.size();i++) {
				if(this.listaContactos.get(i).getNombre().equals(nombreaux) && this.listaContactos.get(i).getApellidos().equals(apellidosaux)) {
					n = n + 1;
					aux.add(this.listaContactos.get(i));
				}
			}
			if(n==0) {
				System.out.print("No se ha encontrado ninguna persona que se llame así ");
				return null;
			}

			if(n==1) {
				return aux.get(0);
			}

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
				return null;
			}

		}

		
			else if(a==3) {
				Integer cont=0;

				//Imprime todos los intereses

				for (Intereses myVar : Intereses.values()) {
					System.out.println(myVar.getId()+" "+myVar.getInteres());
				}
				
				System.out.print("Indique que interes buscar: ");
				Integer newinteres= sc.nextInt();
				sc.nextLine();
				

				//Busca los contactos que tengan el interes seleccionado arriba

				for(Contacto d: this.listaContactos) {

					for(int i=0;i<d.getIntereses().size();i++) {
						
						if(d.getIntereses().get(i).getId()==newinteres){	
							System.out.println(d.getIntereses().get(i).getInteres());
							aux.add(d);
							
						}
					}

				}

				if(aux.size()==0) {
					System.out.println("No existe ningun contacto con dichos intereses");
					return null;
				}

				if(aux.size()==1) {

					return aux.get(0);
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

		else if(a==4) {
			String fechaaux=new String();
			int n = 0;
			System.out.print("Indique la fecha de nacimiento(dd/mm/yyyy) a buscar: ");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dnuevafecha = new Date();
			int cont=1;
			while(cont!=0) {
				cont=0;
				try {
					fechaaux = sc.nextLine();
					dnuevafecha = formatter.parse(fechaaux);
				} catch (ParseException e1) {
					cont++;
					System.out.print("Fecha mal introducida. Vuelva a introducirla(dd/MM/yyyy): ");
				}
			}
			//Busca los contactos que tengan el interes seleccionado arriba

			for(Contacto d: this.listaContactos) {
				if(d.getFechanacimiento().equals(dnuevafecha)) {
					aux.add(d);			
				}
			}


			if(aux.size()==0) {
				System.out.println("No existe ningun contacto con dicha fecha de nacimiento");
				return null;

			}

			if(aux.size()==1) {
				return aux.get(0);
			}

			//Imprime todos los contactos con dicha fecha.

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

		
		return null;
	}
	
	/**
	 * Este método se encarga de dar de alta a un contacto.
	 * @throws ClassNotFoundException 
	*/

	public void darAlta() throws IOException, ClassNotFoundException {
		
		
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

			
			Boolean email= true;
			String nuevoemail=new String();
			
			while(email) {
				
				System.out.print("Introduzca el nuevo email: ");
				nuevoemail = sc.nextLine();
				
				while(!(control.esEmail(nuevoemail))) {
					System.out.println("El email debe tener @");
					System.out.print("Vuelva a introducir el email: ");
					nuevoemail=sc.nextLine();
				}
				
				if(this.listaContactos.size()==0) {
					email=false;
					continue;
				}
				int cont=0;
				for (Contacto myVar : this.listaContactos) {
					if(nuevoemail.equals(myVar.getEmail())) {
						cont++;
					}
					
				}
				if(cont>0) {
					System.out.println("Dicho email esta ya en uso");
					email=true;
				}
				
				else {
					email=false;
				}
				
			}
			
			String nuevafecha="01/01/1970";
			System.out.print("Introduzca la nueva fecha de nacimiento(dd/mm/yyyy): ");
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dnuevafecha=new Date();
			int conta=1;
			while(conta!=0) {
				conta=0;
				try {
					nuevafecha = sc.nextLine();
					//Este control es necesario porque la fecha se parsea aunque no tenga el formato de arriba. Por ejemplo no salta error si pones dd/mmm/yyy
					while(!(control.esFecha(nuevafecha))) {
						System.out.println("Formato de la fecha (dd/mm/yyyy)");
						System.out.print("Vuelva a introducir la fecha: ");
						nuevafecha=sc.nextLine();
					}
					dnuevafecha = formatter.parse(nuevafecha);
				} catch (ParseException e1) {
					System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy): ");
					
					conta++;
				}
			}
			
			
			ArrayList<Intereses> intereses=new ArrayList<Intereses>();
			Integer neweleccion=0;
			
			Boolean condicion=true;
			
			while(condicion) {
				
				System.out.println("Seleccione un nuevo interes: ");
				
				for (Intereses myVar : Intereses.values()) {
					System.out.println(myVar.getId()+" "+myVar.getInteres());
				}
				
				int newinteres= sc.nextInt();
				sc.nextLine();
				
				
				for (Intereses myVar : Intereses.values()) {
					if(newinteres==myVar.getId()) {
						intereses.add(myVar);
					}
				}
				
				System.out.println("Desea añadir mas intereses: 1. Si 2. No");
				neweleccion=sc.nextInt();
				sc.nextLine();
				if(neweleccion!=1) {
					condicion=false;
				}
				
			
			}
			
			
			Contacto e=new Contacto(nuevonombre,nuevoapellido,dnuevafecha,nuevoemail,intereses);
			
			this.listaContactos.add(e);
			
			this.guardarDatos();
			
			System.out.println("Contacto dado de alta");
	}
	
	/**
	 * Este método se encarga de dar de baja a un contacto.
	 * @param Contacto que desea dar de baja
	 * @throws ClassNotFoundException 
	*/
	public void darBaja(Contacto e) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		if(e==null) {
			return;
		}
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
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	*/
	public void actualizarContacto(Contacto e) throws FileNotFoundException, ClassNotFoundException, IOException {
		
		if(e==null) {
			return;
		}
		
		System.out.println("Que quieres modificar: 1. Nombre 2. Apellidos 3. Email 4. Fecha Nacimiento 5. Intereses");
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca un número entero: ");
		Integer a = sc.nextInt();
		sc.nextLine();
		
		
		if(a==1) {
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
			e.setNombre(nuevonombre);
		}
		
		else if(a==2) {
			String nuevoapellido;
		    String apellidoaux;
		    System.out.print("Introduzca el nuevo apellido: ");
		    apellidoaux = sc.nextLine();
		    while(!(control.esNombre(apellidoaux))) {
		    	System.out.println("No se pueden introducir numeros en el apellido");
				System.out.print("Vuelva a introducir el apellido: ");
				nuevoapellido=sc.nextLine();
			}
		    nuevoapellido = apellidoaux.substring(0, 1).toUpperCase() + apellidoaux.substring(1).toLowerCase();

			e.setApellidos(nuevoapellido);
		}
		
		else if(a==3) {
			Boolean email= true;
			String nuevoemail=new String();
			
			while(email) {
				
				System.out.print("Introduzca el nuevo email: ");
				nuevoemail = sc.nextLine();
				
				while(!(control.esEmail(nuevoemail))) {
					System.out.println("El email debe tener @");
					System.out.print("Vuelva a introducir el email: ");
					nuevoemail=sc.nextLine();
				}
				
				if(this.listaContactos.size()==0) {
					email=false;
					continue;
				}
				int cont=0;
				for (Contacto myVar : this.listaContactos) {
					if(nuevoemail.equals(myVar.getEmail())) {
						cont++;
					}
					
				}
				if(cont>0) {
					System.out.println("Dicho email esta ya en uso");
					email=true;
				}
				
				else {
					email=false;
				}
				
			}
			e.setEmail(nuevoemail);
		}
		
		else if(a==4) {
			String nuevafecha="01/01/1970";
			System.out.print("Introduzca la nueva fecha de nacimiento(dd/mm/yyyy): ");
			nuevafecha = sc.nextLine();
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 
			Date dnuevafecha = new Date();
			int cont=1;
			while(cont!=0) {
				cont=0;
				try {
					nuevafecha = sc.nextLine();
					while(!(control.esFecha(nuevafecha))) {
						System.out.println("Formato de la fecha (dd/mm/yyyy)");
						System.out.print("Vuelva a introducir la fecha: ");
						nuevafecha=sc.nextLine();
					}
					dnuevafecha = formatter.parse(nuevafecha);
				} catch (ParseException e1) {
					System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy hh:mm:ss): ");
					cont++;
				}
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
				e.setIntereses(intereses);
			}
			else {
				
				System.out.println("Seleccione un nuevo interes: ");
				int newinteres=0;
				for (Intereses myVar : Intereses.values()) {
					System.out.println(myVar.getId()+" "+myVar.getInteres());
				}
				
				newinteres= sc.nextInt();
				sc.nextLine();
				
				for (Intereses myVar : Intereses.values()) {
					if(newinteres==myVar.getId()) {
						intereses.add(myVar);
						e.setIntereses(intereses);
					}
				}
			}
			
			
		}
		
		guardarDatos();
		
	}
	
	/**
	 * Este método se encarga de leer los contactos del fichero.
	 * @return Instancia única de GestorContactos.
	*/
	public void cargarDatos() throws FileNotFoundException, IOException, ClassNotFoundException {
		try {
			String ubicacion=new String();
			Configuracion config=Configuracion.getInstance(ubicacion);
			ObjectInputStream file = new ObjectInputStream(new FileInputStream(config.getProperty("DATA_FILE")));
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
			}catch (EOFException e) {
				
			}
		 
		
	}
	
	/**
	 * Este método se encarga de guardar los contactos en el fichero de datos.
	 * @throws ClassNotFoundException 
	*/
	public void guardarDatos() throws FileNotFoundException, IOException, ClassNotFoundException {
		String ubicacion=new String();
		Configuracion config=Configuracion.getInstance(ubicacion);
		ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(config.getProperty("DATA_FILE")));
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
		
		if(e==null) {
			return;
		}
		String cadena=new String();
		SimpleDateFormat objSDF = new SimpleDateFormat("dd/MM/yyyy"); 
		System.out.println("Nombre: "+e.getNombre()+" Apellidos: "+ e.getApellidos()+" Email: "+e.getEmail()+" Fecha de Nacimiento: "+ objSDF.format(e.getFechanacimiento())+"\n"+"Intereses: ");
		for(int i=0; i<e.getIntereses().size();i++) {
			cadena=e.getIntereses().get(i).getInteres();
			
			System.out.println(cadena);
		}
	}

}
