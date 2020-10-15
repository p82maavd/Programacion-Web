package practica1;

import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import practica1.Anuncio.Estados;

/**
 * 
 * @author Damian Martinez
 * @author Daniel Ortega
 * Declaracion de la clase TablonAnuncios
 *
 */

public class TablonAnuncios {
	
	private Contacto usuario;
	
	/**
	 * Metodo get del usuario del tablon.
	 * @return Usuario propietario del tablon.
	*/
	
	public Contacto getUsuario() {
		return usuario;
	}
	
	/**
	 * Metodo set del usuario del tablon.
	 * @param Usuario propietario del tablon.
	*/

	public void setUsuario(Contacto e) {
		this.usuario = e;
	}

	private static TablonAnuncios instance =null;
	
	private Intereses claseintereses=Intereses.getInstance();
	private GestorContactos clasecontactos=GestorContactos.getInstance();
	
	public static TablonAnuncios getInstance() {
		
		if(instance==null) {
			instance=new TablonAnuncios();
		}
		return instance;
	}
	
	/**
	 * Metodo que asigna un usuario a la instancia unica de tablon.
	 * @param Lista de todos los contactos.
	 * @return boolean true si el usuario introducido existe y false en el caso contrario.
	*/
	
	public boolean identificarUsuario(ArrayList<Contacto> usuarios) {
		//Con correo electronico ya que es unico
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduce tu correo electronico: ");
		String correo= new String();
		correo=sc.nextLine();
		for(Contacto a: usuarios) {
			if (a.getEmail().equals(correo)) {
				
				System.out.println("Identificado Correctamente");
				setUsuario(a);
				return true;
			}
		}
		return false;
	}
	
	public void inscribirseInteres() {
		
		clasecontactos.añadirIntereses(getUsuario());
	}
	
	/**
	 * Metodo que crea un auncio mediante llamadas a los metodos de la factoria
	 * @param Identificador del anuncio. Desde el main se le pasa el size del vector anuncios por lo que no se repite.
	*/
	
	public Anuncio crearAnuncio(int id) {
		
		//añadir en el main cuando se le llame un try/catch de nullPointerException de cuando el else.
		ConcreteFactory anuncioFactory = new ConcreteFactory();
		Scanner sc=new Scanner(System.in);
		
		
		System.out.println("Que tipo de anuncio quieres crear: ");
		System.out.println("1. Anuncio General");
		System.out.println("2. Anuncio Flash");
		System.out.println("3. Anuncio Individualizado");
		System.out.println("4. Anuncio Tematico");
		int as=sc.nextInt();
		
		
		if(as==1) {
			
			Anuncio anuncio = anuncioFactory.createAnuncioGeneral(getUsuario(),clasecontactos.getContactos(),id);
			
			return anuncio;
		}
		
		else if(as==2) {
			
			Anuncio anuncio = anuncioFactory.createAnuncioFlash(getUsuario(),clasecontactos.getContactos(),id);
			
			return anuncio;
		}
		
		else if(as==3) {
			
			Anuncio anuncio = anuncioFactory.createAnuncioIndividualizado(getUsuario(),clasecontactos.getContactos(),id);
			
			return anuncio;
		}
		
		else if(as==4) {
		
			Anuncio anuncio = anuncioFactory.createAnuncioTematico(getUsuario(),claseintereses.getIntereses(),clasecontactos.getContactos(),id);
			
			return anuncio;
		}
		
		else {
			
			System.out.println("Opcion no valida");
			//Try catch en el main
			return null;
		}
		
	}
	
	/**
	 * Metodo que muestra los anuncios del contacto identificado en el tablon
	 * @param Usuario propietario del tablon.
	 * @param Lista de anuncios.
	*/
	
	public void mostrarAnuncios(Contacto e, ArrayList<Anuncio> anuncios) {
		System.out.println("Numero total de anuncios: " + anuncios.size());
		int cont=0;
		for(Anuncio a: anuncios) {
			//Te pone el anuncio en archivado directamente. Ya deberia estar arreglado.
			System.out.println("");
			//Si el email del que llama al tablon esta en la lista de destinatarios y el anuncio esta publicado o en espera.
			if((a.getDestinatarios().get(cont).getEmail().equals(e.getEmail())) & (a.getEstado().getId()>=2) & (a.getEstado().getId()<=3) ){
				
				if(!(a.getClass().toString().equals("class practica1.AnuncioFlash"))) {
					System.out.println(a.tooString());
					
				}
				
				else {
					
					Date fechaActual=new Date();
					
					if( (fechaActual.compareTo(((AnuncioFlash) a).getFechaInicio())>0) & (fechaActual.compareTo(((AnuncioFlash) a).getFechaFinal())<0) ){
						
						
						Estados estado=Estados.Publicado;
						a.setEstado(estado);
						System.out.println(a.tooString());
						
					}
					
					else {
						
						if(fechaActual.compareTo(((AnuncioFlash) a).getFechaFinal())>0) {
							Estados estado=Estados.Archivado;
							a.setEstado(estado);
						}
						
					}
					
				}
				
				
			}
		}
	}
	
}
