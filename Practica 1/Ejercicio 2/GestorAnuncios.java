package practica1;

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

import practica1.Anuncio.Estados;

public class GestorAnuncios {
	
	private static GestorAnuncios instance =null;
	
	
	//Mejor separarlo por estado del anuncio.
	private ArrayList <Anuncio> listaAnuncios;
	

	/**
	 * Este método se encarga de crear una instancia en el caso de que no haya una ya creada. Patron de diseño Singleton
	 * @return Instancia única de GestorAnuncios.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	*/
	public static GestorAnuncios getInstance() throws FileNotFoundException, ClassNotFoundException, IOException {
		
		if(instance==null) {
			instance=new GestorAnuncios();
		}
		return instance;
	}
	
	/**
	 * Constructor de la clase GestorContactos.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	*/
	private GestorAnuncios() throws FileNotFoundException, ClassNotFoundException, IOException {
		
		listaAnuncios=new ArrayList<Anuncio>();
		cargarAnuncios();
		//Cargar del .dat.
	}
	
	public ArrayList<Anuncio> getListaAnuncios() {
		return listaAnuncios;
	}

	public void setListaAnuncios(ArrayList<Anuncio> listaAnuncios) {
		this.listaAnuncios = listaAnuncios;
	}

	
	//Probar guardar en distintos ficheros cada tipo de anuncio y cargar cada tipo de anuncio.
	public void guardarAnuncio() throws FileNotFoundException, IOException {
		
		
		ObjectOutputStream general = new ObjectOutputStream(new FileOutputStream( "general.dat" ));
		ObjectOutputStream tematico = new ObjectOutputStream(new FileOutputStream( "tematico.dat" ));
		ObjectOutputStream flash = new ObjectOutputStream(new FileOutputStream( "flash.dat" ));
		ObjectOutputStream individualizado = new ObjectOutputStream(new FileOutputStream( "individualizado.dat" ));
		String string=new String();
		
		Anuncio auxc=null;
		
		for(int i=0; i<this.listaAnuncios.size();i++) {
			
			auxc=this.listaAnuncios.get(i);
			Class<? extends Anuncio> a=auxc.getClass();
			string=a.toString();
			
			//System.out.println(string);
			if(string.equals("class practica1.AnuncioTematico")) {
				tematico.writeObject(auxc);
			}
			else if(string.equals("class practica1.AnuncioFlash")) {
				flash.writeObject(auxc);
			}
			else if(string.equals("class practica1.AnuncioIndividualizado")) {
				individualizado.writeObject(auxc);
			}
			else if(string.equals("class practica1.AnuncioGeneral")) {
				general.writeObject(auxc);
			}
	        	
			
		}
        
        	general.close();
        	tematico.close();
        	flash.close();
        	individualizado.close();
	}
	
	public void cargarAnuncios() throws FileNotFoundException, IOException, ClassNotFoundException {
		try {
		ObjectInputStream tematico = new ObjectInputStream(new FileInputStream("tematico.dat"));
		ObjectInputStream flash = new ObjectInputStream(new FileInputStream("flash.dat"));
		ObjectInputStream individualizado = new ObjectInputStream(new FileInputStream("individualizado.dat"));
		ObjectInputStream general = new ObjectInputStream(new FileInputStream("general.dat"));
		
		ArrayList<String> aux=new ArrayList<String>();
		
		Anuncio clase= null;
		do {
			try {
				 clase = (AnuncioTematico) tematico.readObject(); 
		         } catch (EOFException e) {
		            System.out.println("");
		            System.out.println("Anuncios Tematicos cargados correctamente");
		            break;
		         } 
			
			this.listaAnuncios.add(clase);
		}
		while(clase!=null); 
		tematico.close();
		
		do {
			try {
				 clase = (AnuncioFlash) flash.readObject(); 
		         } catch (EOFException e) {
		            System.out.println("");
		            System.out.println("Anuncios Flash cargados correctamente");
		            break;
		         } 
			
			this.listaAnuncios.add(clase);
		}
		while(clase!=null); 
		flash.close();
		
		do {
			try {
				 clase = (AnuncioIndividualizado) individualizado.readObject(); 
		         } catch (EOFException e) {
		            System.out.println("");
		            System.out.println("Anuncios Individualizados cargados correctamente");
		            break;
		         } 
			
			this.listaAnuncios.add(clase);
		}
		while(clase!=null); 
		individualizado.close();
		
		do {
			try {
				 clase = (AnuncioGeneral) general.readObject(); 
		         } catch (EOFException e) {
		            System.out.println("");
		            System.out.println("Anuncios Generales cargados correctamente");
		            break;
		         } 
			
			this.listaAnuncios.add(clase);
		}
		while(clase!=null); 
		general.close();
		
		}catch(FileNotFoundException e) {
			guardarAnuncio();
		}
		
	}
	
	
	
	public void addNewAnuncio(Anuncio a) throws FileNotFoundException, IOException {
		
		
		// Set estado de Anuncio a a editado.
		this.listaAnuncios.add(a);
		guardarAnuncio();
	}
	
	public void publicarAnuncio() throws FileNotFoundException, IOException {
		Scanner sc=new Scanner(System.in);
		Anuncio buscado = null;
		int cont=0;
		for(int i=0;i<this.listaAnuncios.size();i++) {
			if(listaAnuncios.get(i).getEstado().getId()==1) {
				System.out.println("Id: "+listaAnuncios.get(i).getId()+" Titulo: "+ listaAnuncios.get(i).getTitulo());
				cont++;
			}
			
		}
		if(cont==0) {
			System.out.println("No hay anuncios para publicar");
			return ;
		}
		System.out.print("Selecciona el anuncio a publicar: ");
		
		int seleccion=sc.nextInt();
		
		for(int i=0;i<this.listaAnuncios.size();i++) {
			
			if(listaAnuncios.get(i).getId()==seleccion) {
				System.out.println("Anuncio seleccionado");
				buscado=this.listaAnuncios.get(i);
				//Comprobar break
				break;
			}
		}
		Estados estado=Estados.Publicado;
		try {
		buscado.setEstado(estado);
		guardarAnuncio();
		}catch(NullPointerException e) {
			System.out.println("Anuncio no valido");
		}
		
		
		
	}
	
	public void archivarAnuncio() throws FileNotFoundException, IOException {
		
		Scanner sc=new Scanner(System.in);
		Anuncio buscado = null;
		int cont=0;
		for(int i=0;i<this.listaAnuncios.size();i++) {
			if(listaAnuncios.get(i).getEstado().getId()==3) {
				System.out.println("Id: "+listaAnuncios.get(i).getId()+" Titulo: "+ listaAnuncios.get(i).getTitulo());
				cont++;
			}
			
		}
		
		if(cont==0) {
			System.out.println("No hay anuncios para publicar");
			return ;
		}
			
		System.out.println("Selecciona el anuncio a archivar");
		
		int seleccion=sc.nextInt();
		
		for(int i=0;i<this.listaAnuncios.size();i++) {
			
			if(listaAnuncios.get(i).getId()==seleccion) {
				System.out.println("Anuncio seleccionado");
				buscado=this.listaAnuncios.get(i);
				//Comprobar break
				break;
			}
		}
		Estados estado=Estados.Archivado;
		buscado.setEstado(estado);
		
		guardarAnuncio();
	}
	
	public Anuncio buscarAnuncio() {
		int opcion;
		Scanner sc= new Scanner(System.in);
		Scanner sl= new Scanner(System.in);
		System.out.println("Buscar por: 1. Fecha 2.Interes 3. Propietario 4. Destinatario");
		//SI HAY UN NULLPOINTEREXCEPTION, HACER ANUNCIO AUXILIAR;
		Anuncio e=null;
        opcion = sc.nextInt();
        //No se puede hacer un nextline() despues de un nextint(), gran lenguaje java
        
        if (opcion==1) {
        	String fecha=new String();
			System.out.print("Introduzca la fecha: ");
			fecha = sl.nextLine();
        	e=buscarFecha(fecha);
        }
        
        else if (opcion==2) {
        	String interes=new String();
			System.out.print("Introduzca el interes: ");
			interes = sl.nextLine();
        	e=buscarIntereses(interes);
        }
        
        else if (opcion==3) {
        	String nombre=new String();
			System.out.print("Introduzca el nombre del propietario: ");
			nombre = sl.nextLine();
        	e=buscarPropietario(nombre);
        }
        
        else if (opcion==4) {
        	String destinatario=new String();
			System.out.print("Introduzca el nombre del destinatario: ");
			destinatario = sl.nextLine();
        	e=buscarDestinatario(destinatario);
        }
        
        
        else {
        	
        	System.out.println("Opcion no valida");
        	return null;
        }
        
        return e;
 
	}
	
	//EN ESTAS FUNCIONES COMO PUEDE HABER MAS DE UN ANUNCIO QUE CUMPLA LAS CONDICIONES AÑADIR SELECCION DE CUAL ES.
	//Yo creo que en estas funciones deben devolver un solo anuncio, para que se puedan usar en las funciones del main(Mirar como esta hecho)
	
	public Anuncio buscarFecha(String fecha) {
		ArrayList<Anuncio> anunciosusuario = null;
		Scanner sc = new Scanner(System.in);
		String nuevafecha="01/01/1970";
		System.out.print("Introduzca la fecha de publicacion: ");
		nuevafecha = sc.nextLine();
		Anuncio buscado= null;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		 
		Date dnuevafecha = null;
		try {
			dnuevafecha = formatter.parse(nuevafecha);
		} catch (ParseException e1) {
			
			e1.printStackTrace();
		}
		for(int i=0 ; i<getListaAnuncios().size() ; i++) {
			if(getListaAnuncios().get(i).getFecha() == dnuevafecha) {
				 anunciosusuario.add(getListaAnuncios().get(i));
			}
		}
		
		//Seleccionar anuncio buscado

		for(int i=0;i<anunciosusuario.size();i++) {
			System.out.println("Id: "+anunciosusuario.get(i).getId()+" Titulo: "+ anunciosusuario.get(i).getTitulo());
			
		}
		
		System.out.println("Selecciona el id del anuncio");
			
		
		int seleccion=sc.nextInt();
		
		for(int i=0;i<anunciosusuario.size();i++) {
			
			if(anunciosusuario.get(i).getId()==seleccion) {
				System.out.println("Anuncio seleccionado");
				buscado=anunciosusuario.get(i);
				//Comprobar break
				break;
			}
		}
		

		return buscado;
	}
	
	public Anuncio buscarIntereses(String intereses) {
		return null;
		
	}
	/*
	public Anuncio buscarPropietario(String propietario) {
		ArrayList<Anuncio> anunciosusuario = null;
		String email;
		System.out.println("Introduzca el email del propietario:");
		Scanner sc = new Scanner(System.in);
		email = sc.nextLine();
		Contacto a = null;
		for(int i=0 ; i<getListaAnuncios().size(); i++) {
			if(getListaAnuncios().get(i).getUsuario().getEmail()==email) {
				//a =new Contacto(getListaAnuncios().get(i).getUsuario().getNombre(), getListaAnuncios().get(i).getUsuario().getApellidos(),getListaAnuncios().get(i).getUsuario().getFechanacimiento(),getListaAnuncios().get(i).getUsuario().getEmail(),getListaAnuncios().get(i).getUsuario().getIntereses());
				//No se puede hacer esto?
				a=getListaAnuncios().get(i).getUsuario();
				//¿Porque no se puede añadir al vector directamente aqui?
				//De hecho, si no lo añades aqui te lo reescribe en la siguiente iteracion del primer for.
			}
		}
		//Esto es explicacion: Buscar todos los anuncios de un propietario.
		for(int j=0 ; j<getListaAnuncios().size() ; j++) {
			if(getListaAnuncios().get(j).getUsuario() == a) {
				 anunciosusuario.add(getListaAnuncios().get(j));
			}
		}

		return anunciosusuario;
		
	}*/
	
	public Anuncio buscarPropietario(String propietario) {
		ArrayList<Anuncio> anunciosusuario = new ArrayList<Anuncio>();
		String email;
		System.out.println("Introduzca el email del propietario:");
		Scanner sc = new Scanner(System.in);
		email = sc.nextLine();
		Anuncio a = null;
		Anuncio buscado=null;
		for(int i=0 ; i<getListaAnuncios().size(); i++) {
			if(getListaAnuncios().get(i).getUsuario().getEmail()==email) {
				//a =new Contacto(getListaAnuncios().get(i).getUsuario().getNombre(), getListaAnuncios().get(i).getUsuario().getApellidos(),getListaAnuncios().get(i).getUsuario().getFechanacimiento(),getListaAnuncios().get(i).getUsuario().getEmail(),getListaAnuncios().get(i).getUsuario().getIntereses());
				//No se puede hacer esto?
				a=getListaAnuncios().get(i);
				//¿Porque no se puede añadir al vector directamente aqui?
				//De hecho, si no lo añades aqui te lo reescribe en la siguiente iteracion del primer for.
				anunciosusuario.add(a);
			}
		}
		
		//Aqui seleccion de cual es.
		
		for(int i=0;i<anunciosusuario.size();i++) {
			System.out.println("Id: "+anunciosusuario.get(i).getId()+" Titulo: "+ anunciosusuario.get(i).getTitulo());
			
		}
		
		System.out.println("Selecciona el id del anuncio");
			
		
		int seleccion=sc.nextInt();
		
		for(int i=0;i<anunciosusuario.size();i++) {
			
			if(anunciosusuario.get(i).getId()==seleccion) {
				System.out.println("Anuncio seleccionado");
				buscado=anunciosusuario.get(i);
				//Comprobar break
				break;
			}
		}
		

		return buscado;
		
	}
	
	/*
	public Anuncio buscarDestinatario(String destinatario) {
		ArrayList<Anuncio> anunciosusuario = null;
		String email;
		System.out.println("Introduzca el email del destinatario:");
		Scanner sc = new Scanner(System.in);
		email = sc.nextLine();
		Contacto a = null;
		
		for(int i=0 ; i<getListaAnuncios().size(); i++) {
			for(int n=0 ; n<getListaAnuncios().get(i).getDestinatarios().size() ; n++) {
				if(getListaAnuncios().get(i).getDestinatarios().get(n).getEmail()==email) {
					//a =new Contacto(getListaAnuncios().get(i).getDestinatarios().get(n).getNombre(), getListaAnuncios().get(i).getDestinatarios().get(n).getApellidos(),getListaAnuncios().get(i).getDestinatarios().get(n).getFechanacimiento(),getlistaAnuncios().get(i).getDestinatarios().get(n).getEmail(),getlistaAnuncios().get(i).getDestinatarios().get(n).getIntereses());
					//Igual que arriba
					a=getListaAnuncios().get(i).getUsuario();
					
				}
			}
		}
		
		for(int i=0 ; i<getListaAnuncios().size(); i++) {
			for(int n=0 ; n<getListaAnuncios().get(i).getDestinatarios().size() ; n++) {
				if(getListaAnuncios().get(i).getDestinatarios().get(n)==a){
					anunciosusuario.add(getListaAnuncios().get(i));
				}
			}
		}

		return anunciosusuario;
		
	}
	*/
	
	//Modificada 
	public Anuncio buscarDestinatario(String destinatario) {
		ArrayList<Anuncio> anunciosusuario = null;
		String email;
		System.out.println("Introduzca el email del destinatario:");
		Scanner sc = new Scanner(System.in);
		email = sc.nextLine();
		Anuncio a = null;
		Anuncio buscado=null;
		
		for(int i=0 ; i<getListaAnuncios().size(); i++) {
			for(int n=0 ; n<getListaAnuncios().get(i).getDestinatarios().size() ; n++) {
				if(getListaAnuncios().get(i).getDestinatarios().get(n).getEmail()==email) {
					//a =new Contacto(getListaAnuncios().get(i).getDestinatarios().get(n).getNombre(), getListaAnuncios().get(i).getDestinatarios().get(n).getApellidos(),getListaAnuncios().get(i).getDestinatarios().get(n).getFechanacimiento(),getlistaAnuncios().get(i).getDestinatarios().get(n).getEmail(),getlistaAnuncios().get(i).getDestinatarios().get(n).getIntereses());
					//Igual que arriba
					a=getListaAnuncios().get(i);
					//¿Porque no se puede añadir al vector directamente aqui?
					anunciosusuario.add(a);
					//Se supone que este break sale solo del segundo for y pasa a la siguiente iteracion del primero
					//Si no se sale te puede añadir dos veces al destinatario.
					break;
				}
			}
		}
		
		//Seleccion del anuncio buscado

		for(int i=0;i<anunciosusuario.size();i++) {
			System.out.println("Id: "+anunciosusuario.get(i).getId()+" Titulo: "+ anunciosusuario.get(i).getTitulo());
			
		}
		
		System.out.println("Selecciona el id del anuncio");
			
		
		int seleccion=sc.nextInt();
		
		for(int i=0;i<anunciosusuario.size();i++) {
			
			if(anunciosusuario.get(i).getId()==seleccion) {
				System.out.println("Anuncio seleccionado");
				buscado=anunciosusuario.get(i);
				//Comprobar break
				break;
			}
		}
		

		return buscado;
		
	}

	
	public void editarAnuncio() {
	}
	
	public void consultarAnuncio(Anuncio e) {
		//Esto mejor en cada clase de Anuncio hacer un metodo toString segun sus atributos y quitar el de la clase Anuncio.
		String cadena=new String();
		System.out.println("Id: "+e.getId()+" Titulo: "+ e.getTitulo()+" Cuerpo: "+e.getCuerpo()+" Propietario: "+ e.getUsuario().getEmail());
		System.out.println("Estado: " + e.getEstado().getEstados());
		for(int i=0; i<e.getDestinatarios().size();i++) {
			cadena=e.getDestinatarios().get(i).getEmail();
			System.out.println(cadena);
		}
		
	}
	
	public void mostrarAnuncios() {
		
		for(Anuncio a: getListaAnuncios()) {
			System.out.println("");
			consultarAnuncio(a);
		}
	}
	

}
