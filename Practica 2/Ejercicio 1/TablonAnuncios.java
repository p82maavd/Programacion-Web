package ejercicio1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ejercicio1.Anuncio.Estados;

/**
 * 
 * Declaracion de la clase tablon Anuncios
 * @author Damian Martinez
 * @author Daniel Ortega
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
	 * @param e Usuario propietario del tablon.
	*/

	public void setUsuario(Contacto e) {
		this.usuario = e;
	}

	private static TablonAnuncios instance =null;
	
	
	private ContactoDAO contactosDAO;
	private InteresDAO interesesDAO;
	private AnuncioDAO anunciosDAO;
	/**
	 * Este método se encarga de crear una instancia en el caso de que no haya una ya creada. Patron de diseño Singleton
	 * @return Instancia única de TablonAnuncios
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	
	private TablonAnuncios() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		DAOFactory factory = DAOFactory.getInstance();
		this.contactosDAO=factory.getContactoDAO();
		this.interesesDAO=factory.getInteresDAO();
		this.anunciosDAO=factory.getAnuncioDAO();
		
	}
	public static TablonAnuncios getInstance() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		
		if(instance==null) {
			instance=new TablonAnuncios();
		}
		return instance;
	}
	
	/**
	 * Metodo que asigna un usuario a la instancia unica de tablon.
	 * @param usuarios Lista de todos los contactos.
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
	
	public void inscribirseInteres() throws SQLException {
		
		contactosDAO.añadirInteres(getUsuario());
	}
	
	/**
	 * Metodo que crea un auncio mediante llamadas a los metodos de la factoria
	 * @param id Identificador del anuncio. Desde el main se le pasa el size del vector anuncios por lo que no se repite.
	 * @throws SQLException 
	*/
	
	public Anuncio crearAnuncio() throws SQLException {
		
		ConcreteFactory anuncioFactory = new ConcreteFactory();
		Scanner sc=new Scanner(System.in);
		Date fecha= new Date(System.currentTimeMillis());
		
		System.out.println("Que tipo de anuncio quieres crear: ");
		System.out.println("1. Anuncio General");
		System.out.println("2. Anuncio Flash");
		System.out.println("3. Anuncio Individualizado");
		System.out.println("4. Anuncio Tematico");
		int as=sc.nextInt();
		
		
		if(as==1) {
			
			Anuncio anuncio = anuncioFactory.createAnuncioGeneral(getUsuario(),fecha);
			
			return anuncio;
		}
		
		else if(as==2) {
			
			Anuncio anuncio = anuncioFactory.createAnuncioFlash(getUsuario(),fecha);
			
			return anuncio;
		}
		
		else if(as==3) {
			
			Anuncio anuncio = anuncioFactory.createAnuncioIndividualizado(getUsuario(),fecha);
			
			return anuncio;
		}
		
		else if(as==4) {
		
			Anuncio anuncio = anuncioFactory.createAnuncioTematico(getUsuario(),interesesDAO.getIntereses(),fecha);
			
			return anuncio;
		}
		
		else {
			
			System.out.println("Opcion no valida");
		
			return null;
		}
		
	}
	
	/**
	 * Metodo que muestra los anuncios del contacto identificado en el tablon
	 * @param e Usuario propietario del tablon.
	 * @param anuncios Lista de anuncios.
	 * @throws SQLException 
	*/
	
	public void mostrarAnuncios(Contacto e) throws SQLException {
		
		anunciosDAO.actualizarDestinatarios();
		ArrayList<Anuncio> anuncios=new ArrayList<Anuncio>();
		anuncios = anunciosDAO.getAnunciosContacto(e);
		for(Anuncio a: anuncios) {
			System.out.println("");
			//Actualiza los destinatarios para que les llegue a todos los contactos actuales.
			//Se supone que esto ya se hace en los otros DAO y los intereses en la llamada a la funcion de arriba actualizarDestinatarios()
			
			
			//Si el email del que llama al tablon esta en la lista de destinatarios y el anuncio esta publicado o en espera se imprime.

			if( (a.getEstado().getId()>=2) & (a.getEstado().getId()<=3) ){
				
				if(!(a.getClass().toString().equals("class ejercicio1.AnuncioFlash"))) {
					
					if(a.getClass().toString().equals("class ejercicio1.AnuncioGeneral")) {
						System.out.println(((AnuncioGeneral) a).tooString());
					}
					
					else if(a.getClass().toString().equals("class ejercicio1.AnuncioTematico")) {
						System.out.println(((AnuncioTematico) a).tooString());
					}
					
					else {
						System.out.println(((AnuncioIndividualizado) a).tooString());
					}
					
					
				}
				
				else {
					
					Date fechaActual=new Date(System.currentTimeMillis());
					
					//Si esta en una fecha entre inicio y fin te lo publica
					
					if( (fechaActual.compareTo(((AnuncioFlash) a).getFechaInicio())>0) & (fechaActual.compareTo(((AnuncioFlash) a).getFechaFinal())<0) ){
						
						//Llamar a una funcion que actualize el estado
						//UPDATE ESTADO where idanuncio = a.getId()
						Estados estado=Estados.Publicado;
						a.setEstado(estado);
						System.out.println(((AnuncioFlash) a).tooString());
						
					}
					
					else {
						
						//Si se ha pasado la fecha final se archiva
						
						if(fechaActual.compareTo(((AnuncioFlash) a).getFechaFinal())>0) {
							//Llamar a una funcion que actualize el estado.
							Estados estado=Estados.Archivado;
							a.setEstado(estado);
						}
						
					}
					
				}
					
			}
		}
	}
	
	public void main() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		Scanner sc= new Scanner(System.in);
		int a;
		boolean condicion=true;
		boolean usuario=identificarUsuario(contactosDAO.getContactos());
		while(condicion) {
			
			if(usuario) {
			
				System.out.println("Que quieres realizar: ");
				System.out.println("1. Inscribirse como usuario a temas de interes");
				System.out.println("2. Crear Anuncio");
				System.out.println("3. Mostrar mi tablon");
				System.out.println("4. Salir");
				
				try {
					a=sc.nextInt();
				
					if(a==1) {
						
						inscribirseInteres();
						
					}
				
					else if(a==2) {
						
						
						try {
						anunciosDAO.guardarAnuncio(crearAnuncio());
						System.out.println("Anuncio creado en estado editado");
						}catch(NullPointerException e) {
							
						}
						
					}
			
					else if(a==3) {
						
						mostrarAnuncios(getUsuario());
				
					}
					
				
					else{
						condicion=false;
					}
				
				} catch (NoSuchElementException e) {
	                System.out.println("Debes insertar un número");
	             
	                a=sc.nextInt();

	            }
			}
			
			else {
				System.out.println("El usuario no existe");
				condicion=false;
			}
		
		}
	}
	
}
