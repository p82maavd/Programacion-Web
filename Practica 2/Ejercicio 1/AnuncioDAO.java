package ejercicio1;

import java.io.EOFException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ejercicio1.Anuncio.Estados;

/**
 * Declaracion de la clase AnuncioDAO
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 *
 */

public class AnuncioDAO {
	
	private static AnuncioDAO instance =null;
	private DAOFactory factory = DAOFactory.getInstance();
	private Connection e;
	private ContactoDAO contactos= factory.getContactoDAO();
	private InteresDAO intereses= factory.getInteresDAO();
	

	private ArrayList <Anuncio> listaAnuncios;
	

	/**
	 * Este método se encarga de crear una instancia en el caso de que no haya una ya creada. Patron de diseño Singleton
	 * @return Instancia única de AnuncioDAO.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	*/
	public static AnuncioDAO getInstance(Connection e) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		
		if(instance==null) {
			instance=new AnuncioDAO(e);
		}
		return instance;
	}
	
	/**
	 * Constructor de la clase GestorContactos.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	*/
	private AnuncioDAO(Connection e) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		this.e =e;
		listaAnuncios=new ArrayList<Anuncio>();
		//cargarAnuncios();
		
	}
	/**
	 * Devuelve la lista de anuncios
	 * @return ArrayList de Anuncios
	 */
	public ArrayList<Anuncio> getListaAnuncios() {
		return listaAnuncios;
	}
	/**
	 * Modifica la lista de anuncios
	 * @param listaAnuncios ArrayList de Anuncios
	 */
	public void setListaAnuncios(ArrayList<Anuncio> listaAnuncios) {
		this.listaAnuncios = listaAnuncios;
	}
	/**
	 * Guarda los anuncios en ficheros de datos
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	public void guardarAnuncio(Anuncio a) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		Configuracion config=Configuracion.getInstance(null);
		
		String string=new String();
		
		Anuncio auxc=null;
		for(int i=0; i<this.listaAnuncios.size();i++) {
			
			auxc=this.listaAnuncios.get(i);
			Class<? extends Anuncio> a=auxc.getClass();
			string=a.toString();
			
			if(string.equals("class ejercicio2.AnuncioTematico")) {
				
			}
			else if(string.equals("class ejercicio2.AnuncioFlash")) {
				
			}
			else if(string.equals("class ejercicio2.AnuncioIndividualizado")) {
				
			}
			
			else if(string.equals("class ejercicio2.AnuncioGeneral")) {
				
			}
	        	
			
		}
        
	}
	
	/**
	 * Metodo que se encarga de leer todos los Anuncios de los ficheros
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void cargarAnuncios() throws FileNotFoundException, IOException, ClassNotFoundException {
		try {
			
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
	
	/**
	 * Añade un anuncio al ArrayList de anuncios.
	 * @param a Anuncio a añadir.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
	
	public void addNewAnuncio(Anuncio a) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		this.listaAnuncios.add(a);
		guardarAnuncio(a);
	}
	
	/**
	 * Cambia el estado de un anuncio a publicado
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
	public void publicarAnuncio() throws FileNotFoundException, IOException, ClassNotFoundException {
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
		System.out.print("Selecciona el id del anuncio a publicar: ");
		
		int seleccion=sc.nextInt();
		
		for(int i=0;i<this.listaAnuncios.size();i++) {
			
			if(listaAnuncios.get(i).getId()==seleccion & listaAnuncios.get(i).getEstado().getId()<3) {
				System.out.println("Anuncio seleccionado");
				buscado=this.listaAnuncios.get(i);
				
			}
		}
		
		Estados estado;
		
		if(!(buscado.getClass().toString().equals("class ejercicio2.AnuncioIndividualizado"))) {
			buscado.setDestinatarios(contactos.getContactos());
		}
		if(!(buscado.getClass().toString().equals("class ejercicio2.AnuncioFlash"))) {
			estado=Estados.Publicado;
		}
		
		else {
			
			Date fechaActual=new Date();
			//Si la fecha actual entre inicio y fin pasa a publicado, sino a espera. Si la fecha es mayor que la final se cambia al mostrarAnuncios
			if( (fechaActual.compareTo(((AnuncioFlash) buscado).getFechaInicio())>0) & (fechaActual.compareTo(((AnuncioFlash) buscado).getFechaFinal())<0) ){
				 estado=Estados.Publicado;
			}
			else {
				 estado=Estados.En_espera;
			}
			
		}
		
		
		try {
		buscado.setEstado(estado);
		guardarAnuncio();
		}catch(NullPointerException e) {
			System.out.println("Anuncio no valido");
		}
		
		
		
	}
	
	/**
	 * Modifica los parametros de un anuncio
	 * @param e Anuncio a modificar.
	 * @throws SQLException 
	 */
	
	public void modificarAnuncio(Anuncio e) throws SQLException {
		
		String string = new String();
		Class<? extends Anuncio> a=e.getClass();
		string=a.toString();
		
		if(e.getEstado().getId()>2) {
			System.out.println("No se puede modificar un anuncio ya publicado/archivado ");
		}
		
		
		if(string.equals("class ejercicio2.AnuncioTematico")) {
			modificarAnuncioTematico(e,intereses.getIntereses());
		}
		else if(string.equals("class ejercicio2.AnuncioFlash")) {
			modificarAnuncioFlash(e);
		}
		else if(string.equals("class ejercicio2.AnuncioIndividualizado")) {
			modificarAnuncioIndividualizado(e,contactos.getContactos());
		}
		else if(string.equals("class ejercicio2.AnuncioGeneral")) {
			modificarAnuncioGeneral(e);
		}
		
		
		
	}
	
	public void actualizarDestinatarios() throws SQLException {
		
		//Añadir connection como atributo a la clase ANUNCIODAO.
		
		//Esto funcionaria casi seguro. Habria que hacerlo de forma que se compararan con los que hay. Si no esta en la base de datos 
		//se añade y si no esta en destinatarios se elimina. Con añadirlo creo que ya estaria. ELiminarlo seria que ya no existe el usuario, o le han quitado un interes.
		
		
		//Esto si es tematico
		//Hacer que cuando se modifique los intereses de un contacto se borre el usuario de la tabla destinatarios y de intereses_contacto.
		
		int cont=0, status=0;
		ArrayList<Contacto> newDest=new ArrayList<Contacto>();
		for(Anuncio a: listaAnuncios) {
			if(a.getClass().toString()!="ej1 AnuncioTematico") {
				
				//Aqui quizas si seria conveniente borrar antes los destinatarios para que este bien actualizado. 
				PreparedStatement ps0=connection.prepareStatement("delete from dest where idanuncio=?");
				ps0.setInt(1,a.getId());
				status=ps0.executeUpdate();
				for(Contacto e: contactos.getContactos()){
					cont=0;
					for(Interes i: e.getIntereses()) {
						if((((AnuncioTematico) a).getIntereses().contains(i)) && (cont==0)) {
							//Connection. Atributo de la clase que le asigna el valor  el constructor. Añadirlo si no lo esta ya.
							PreparedStatement ps=connection.prepareStatement("insert into destinatarios(idanuncio,idemail) values(?,?)");
					        ps.setInt(1, a.getId());
					        ps.setString(2, e.getEmail());
					        status = ps.executeUpdate();
					        cont++;
					        newDest.add(e);
						}
					}
					
			        
				}
				a.setDestinatarios(newDest);
			}
			
		}
		
	}
	
	/**
	 * Modifica un anuncio tematico
	 * @param e Anuncio tematico
	 * @param intereses ArrayList con los intereses disponibles.
	 * @throws SQLException 
	 */
	
	public void modificarAnuncioTematico(Anuncio e, ArrayList<Interes> intereses) throws SQLException{
		
		Scanner sc=new Scanner(System.in);
		Scanner sl=new Scanner(System.in);
		
		System.out.println("Que quieres modificar: ");
		System.out.println("1. Titulo ");
		System.out.println("2. Cuerpo ");
		System.out.println("3. Intereses");
		
		int a=sc.nextInt();
		
		if(a==1) {
			
			System.out.print("Introduce el nuevo titulo: ");
			String titulo=new String();
			titulo=sl.nextLine();
			e.setTitulo(titulo);
			
		}
		
		else if(a==2) {
			System.out.print("Introduce el nuevo Cuerpo: ");
			String cuerpo=new String();
			cuerpo=sl.nextLine();
			e.setCuerpo(cuerpo);
		}
		//Actualizar
		else if(a==3) {
			ArrayList<Interes> actuales=new ArrayList<Interes>();
			//Esta puede que sea otra funcion. Se supone que coge los intereses de un anuncio.
			actuales=((AnuncioTematico) e).getIntereses();
			System.out.print("Que desea eliminar(1) o añadir(2) un intereses del anuncio: "); 
			a=sc.nextInt();
			if(a==1) {
				System.out.println("Cual desea eliminar: ");
				
				for(Interes var: actuales) {
					System.out.println(var.getId()+". "+var.getInteres());
					
				}
				a=sc.nextInt();
				
				actuales.remove(a);
				
				((AnuncioTematico) e).setIntereses(actuales);
				
			}
			else if(a==2) {
				
				System.out.println("Cual desea añadir: ");
				
				for(Interes var: this.intereses.getIntereses()) {
					System.out.println(var.getId()+". "+var.getInteres());
					
				}
				a=sc.nextInt();
				//Esto se puede optimizar creo. 
				for(int i=0; i<this.intereses.getIntereses().size();i++) {
					
					if(this.intereses.getIntereses().get(i).getId()==a) {
						actuales.add(intereses.get(a));
					}
				}
				
				((AnuncioTematico) e).setIntereses(actuales);
				
			}
			
			else {
				System.out.print("Opcion no valida");
			}
		}
		
		else {
			System.out.print("Opcion no valida");
		}
	}
	
	/**
	 * Modifica un anuncio flash
	 * @param e Anuncio flash
	 */
	
	public void modificarAnuncioFlash(Anuncio e){
		
		Scanner sc=new Scanner(System.in);
		Scanner sl=new Scanner(System.in);
		ControlDeErrores control=new ControlDeErrores();
		
		System.out.println("Que quieres modificar: ");
		System.out.println("1. Titulo ");
		System.out.println("2. Cuerpo ");
		System.out.println("3. Fecha Inicio");
		System.out.println("4. Fecha Final");
		
		int a=sc.nextInt();
		
		if(a==1) {
			
			System.out.print("Introduce el nuevo titulo: ");
			String titulo=new String();
			titulo=sl.nextLine();
			e.setTitulo(titulo);
			
		}
		
		else if(a==2) {
			System.out.print("Introduce el nuevo Cuerpo: ");
			String cuerpo=new String();
			cuerpo=sl.nextLine();
			e.setCuerpo(cuerpo);
		}
		
		else if(a==3) {
			
			Date fechaInicio=new Date();
			System.out.print("Introduzca la fecha de inicio(dd/mm/yyyy hh:mm:ss): ");
			String fechain=new String();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			int cont=1;
			while(cont!=0) {
				cont=0;
				try {
					fechain = sc.nextLine();
					while(!(control.esFecha(fechain))) {
						System.out.println("Formato de la fecha (dd/mm/yyyy)");
						System.out.print("Vuelva a introducir la fecha: ");
						fechain=sc.nextLine();
					}
					fechaInicio = formatter.parse(fechain);
				} catch (ParseException e1) {
					System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy hh:mm:ss): ");
					
					cont++;
				}
			}
			((AnuncioFlash) e).setFechaInicio(fechaInicio);
			
		}
		
		else if(a==4) {
			
			Date fechaFinal=new Date();
			System.out.print("Introduzca la fecha final(dd/mm/yyyy hh:mm:ss): ");
			String fechafin=new String();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			int cont=1;
			while(cont!=0) {
				cont=0;
				try {
					fechafin = sc.nextLine();
					while(!(control.esFecha(fechafin))) {
						System.out.println("Formato de la fecha (dd/mm/yyyy)");
						System.out.print("Vuelva a introducir la fecha: ");
						fechafin=sc.nextLine();
					}
					fechaFinal = formatter.parse(fechafin);
				} catch (ParseException e2) {
					System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy hh:mm:ss): ");
					
					cont++;
				}
			}
			
			((AnuncioFlash) e).setFechaFinal(fechaFinal);
		}
		
		else {
			System.out.print("Opcion no valida");
		}
		
	}
	/**
	 * Modifica un Anuncio Individualizado
	 * @param e Anuncio Individualizado
	 * @param destinatarios Posibles Destinatarios.
	 */
	public void modificarAnuncioIndividualizado(Anuncio e, ArrayList<Contacto> destinatarios){
		
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Que quieres modificar: ");
		System.out.println("1. Titulo ");
		System.out.println("2. Cuerpo ");
		System.out.println("3. Destinatarios");
		
		int a=sc.nextInt();
		sc.nextLine();
		
		if(a==1) {
			
			System.out.print("Introduce el nuevo titulo: ");
			String titulo=new String();
			titulo=sc.nextLine();
			e.setTitulo(titulo);
			
		}
		
		else if(a==2) {
			System.out.print("Introduce el nuevo Cuerpo: ");
			String cuerpo=new String();
			cuerpo=sc.nextLine();
			e.setCuerpo(cuerpo);
		}
		
		else if(a==3) {
			ArrayList<Contacto> actuales=new ArrayList<Contacto>();
			actuales=((AnuncioIndividualizado) e).getDestinatarios();
			System.out.print("Que desea eliminar(1) o añadir(2) un destinatario del anuncio: "); 
			a=sc.nextInt();
			sc.nextLine();
			if(a==1) {
				System.out.println("Cual desea eliminar: ");
				Integer cont=0;
				for(Contacto var: actuales) {
					System.out.println(cont.toString()+var.getEmail());
					cont++;
				}
				a=sc.nextInt();
				sc.nextLine();
				
				actuales.remove(a);
				
				((AnuncioTematico) e).setDestinatarios(actuales);
				
			}
			else if(a==2) {
				
				System.out.println("Cual desea añadir: ");
				Integer cont=0;
				for(Contacto var: destinatarios) {
					System.out.println(cont.toString()+var.getEmail());
					cont++;
				}
				a=sc.nextInt();
				sc.nextLine();
				
				for(int i=0; i<destinatarios.size();i++) {
					
					if(i==a) {
						actuales.add(destinatarios.get(a));
					}
				}
				
				((AnuncioIndividualizado) e).setDestinatarios(actuales);
				
			}
			
			else {
				System.out.print("Opcion no valida");
			}
		}
		
		else {
			System.out.print("Opcion no valida");
		}
	}
	
	/**
	 * Modifica un Anuncio General
	 * @param e Anuncio de tipo General.
	 */
	
	public void modificarAnuncioGeneral(Anuncio e){
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Que quieres modificar: ");
		System.out.println("1. Titulo ");
		System.out.println("2. Cuerpo ");
		
		int a=sc.nextInt();
		sc.nextLine();
		
		if(a==1) {
			
			System.out.print("Introduce el nuevo titulo: ");
			String titulo=new String();
			titulo=sc.nextLine();
			e.setTitulo(titulo);
			
		}
		
		else if(a==2) {
			System.out.print("Introduce el nuevo Cuerpo: ");
			String cuerpo=new String();
			cuerpo=sc.nextLine();
			e.setCuerpo(cuerpo);
		}
		
		else {
			System.out.print("Opcion no valida");
		}
		
		
	}
	
	/**
	 * Cambia el estado de un anuncio a archivado.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
	public void archivarAnuncio() throws FileNotFoundException, IOException, ClassNotFoundException {
		
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
			System.out.println("No hay anuncios para archivar");
			return ;
		}
			
		System.out.print("Selecciona el anuncio a archivar: ");
		
		int seleccion=sc.nextInt();
		sc.nextLine();
		
		for(int i=0;i<this.listaAnuncios.size();i++) {
			
			if((listaAnuncios.get(i).getId()==seleccion) & (listaAnuncios.get(i).getEstado().getId()==3)) {
				System.out.println("Anuncio seleccionado");
				buscado=this.listaAnuncios.get(i);
				
				break;
			}
			
		}
		Estados estado=Estados.Archivado;
		buscado.setEstado(estado);
		
		guardarAnuncio();
	}
	/**
	 * Busca un anuncio segun lo que decida el usuario
	 * @return Anuncio buscado
	 */
	public Anuncio buscarAnuncio() {
		int opcion;
		Scanner sc= new Scanner(System.in);
		System.out.println("Buscar por: 1. Fecha 2.Interes 3. Propietario 4. Destinatario");
		Anuncio e=null;
        opcion = sc.nextInt();
        sc.nextLine();
                
        if (opcion==1) {
        	String fecha=new String();
			System.out.print("Introduzca la fecha: ");
			fecha = sc.nextLine();
        	e=buscarFecha(fecha);
        }
       //Actualizar 
        else if (opcion==2) {
        	
        	
        	int cont=0;
        	for (Interes myVar : intereses.getIntereses()) {
				System.out.println(cont+" "+myVar);
				cont++;
			}
			System.out.print("Seleccionar Interes: ");
			int newinteres= sc.nextInt();
			sc.nextLine();
			
			
        	for (int i=0; i<intereses.getIntereses().size();i++) {
				if(newinteres==i) {
					interes=intereses.getIntereses().get(i);
				}
				
			}
			
        	e=buscarIntereses(interes,intereses.getIntereses());
        }
        
        else if (opcion==3) {
        	String nombre=new String();
			System.out.print("Introduzca el email del propietario: ");
			nombre = sc.nextLine();
        	e=buscarPropietario(nombre);
        }
        
        else if (opcion==4) {
        	String destinatario=new String();
			System.out.print("Introduzca el email del destinatario: ");
			destinatario = sc.nextLine();
        	e=buscarDestinatario(destinatario);
        }
        
        
        else {
        	
        	System.out.println("Opcion no valida");
        	
        	return null;
        }
        
        return e;
 
	}
	
	/**
	 * Busca un anuncio por fecha
	 * @param fecha fecha del anuncio a buscar.
	 * @return anuncio buscado
	 */
	public Anuncio buscarFecha(String fecha) {
		ArrayList<Anuncio> anunciosusuario = new ArrayList<Anuncio>();
		Scanner sc = new Scanner(System.in);
		Anuncio buscado= null;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		 
		Date dnuevafecha = new Date();
		try {
			dnuevafecha = formatter.parse(fecha);
		} catch (ParseException e1) {
			
			e1.printStackTrace();
		}
		for(int i=0 ; i<getListaAnuncios().size() ; i++) {
			if(getListaAnuncios().get(i).getFecha() == dnuevafecha) {
				 anunciosusuario.add(getListaAnuncios().get(i));
			}
		}
		
		
		if(anunciosusuario.size()==1) {
			return anunciosusuario.get(0);
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
				
				break;
			}
		}
		

		return buscado;
	}
	
	/**
	 * Busca un anuncio segun sus intereses
	 * @param interes interes buscado
	 * @param intereses intereses disponibles
	 * @return anuncio buscado
	 */
	
	public Anuncio buscarIntereses(String interes, ArrayList<String> intereses) {
		ArrayList <Anuncio> anunciosusuario=new ArrayList<Anuncio>();
		Anuncio buscado= null;
		Scanner sc = new Scanner(System.in);
		for(Anuncio d: getListaAnuncios()) {
			
			if(d.getClass().toString().equals("class ejercicio2.AnuncioTematico")) {
				for(String s: intereses) {
					
					if(((AnuncioTematico) d).getIntereses().contains(s)) {
						anunciosusuario.add(d);
						
						break;
					}
					
				}
			}
			
		}
		
		
		if(anunciosusuario.size()==1) {
			return anunciosusuario.get(0);
		}
		
		for(int i=0;i<anunciosusuario.size();i++) {
			System.out.println("Id: "+anunciosusuario.get(i).getId()+" Titulo: "+ anunciosusuario.get(i).getTitulo());
			
		}
		Integer seleccion=null;
		if(anunciosusuario.size()>0) {
			System.out.println("Selecciona el id del anuncio");
			seleccion=sc.nextInt();
		}
			
		
		
		
		for(int i=0;i<anunciosusuario.size();i++) {
			
			if(anunciosusuario.get(i).getId()==seleccion) {
				System.out.println("Anuncio seleccionado");
				buscado=anunciosusuario.get(i);
				
				break;
			}
		}
		

		return buscado;
		
		
		
	}
	
	
	/**
	 * Busca un Anuncio por su propietario
	 * @param propietario Email del autor del anuncio
	 * @return Anuncio buscado
	 */
	
	public Anuncio buscarPropietario(String propietario) {
		ArrayList<Anuncio> anunciosusuario = new ArrayList<Anuncio>();
		String email;
		
		Scanner sc = new Scanner(System.in);
		
		Anuncio a = null;
		Anuncio buscado=null;
		for(int i=0 ; i<getListaAnuncios().size(); i++) {
			if(getListaAnuncios().get(i).getUsuario().getEmail().equals(propietario)) {
				
				a=getListaAnuncios().get(i);
				
				anunciosusuario.add(a);
			}
		}
		
		
		if(anunciosusuario.size()==1) {
			return anunciosusuario.get(0);
		}
		
		
		for(int i=0;i<anunciosusuario.size();i++) {
			System.out.println("Id: "+anunciosusuario.get(i).getId()+" Titulo: "+ anunciosusuario.get(i).getTitulo());
			
		}
		
		//Aqui seleccion de cual es.
		
		System.out.println("Selecciona el id del anuncio");
			
		
		int seleccion=sc.nextInt();
		
		for(int i=0;i<anunciosusuario.size();i++) {
			
			if(anunciosusuario.get(i).getId()==seleccion) {
				System.out.println("Anuncio seleccionado");
				buscado=anunciosusuario.get(i);
				break;
			}
		}
		

		return buscado;
		
	}
	
	/**
	 * Busca un anuncio por destinatario
	 * @param email email del destinatario
	 * @return anuncio buscado
	 */
	public Anuncio buscarDestinatario(String email) {
		ArrayList<Anuncio> anunciosusuario = new ArrayList<Anuncio>();
		Anuncio a = null;
		Scanner sc=new Scanner(System.in);
		Anuncio buscado=null;
		
		for(int i=0 ; i<getListaAnuncios().size(); i++) {
			for(int n=0 ; n<getListaAnuncios().get(i).getDestinatarios().size() ; n++) {
				if(getListaAnuncios().get(i).getDestinatarios().get(n).getEmail().equals(email)) {
					
					a=getListaAnuncios().get(i);
					
					anunciosusuario.add(a);
					
					break;
				}
			}
		}
		
		if(anunciosusuario.size()==1) {
			return anunciosusuario.get(0);
		}
		
		System.out.println("Anuncios encontrados: ");

		for(int i=0;i<anunciosusuario.size();i++) {
			System.out.println("Id: "+anunciosusuario.get(i).getId()+" Titulo: "+ anunciosusuario.get(i).getTitulo());
			
		}
		
		//Seleccion del anuncio buscado
		
		System.out.print("Selecciona el id del anuncio: ");
			
		
		int seleccion=sc.nextInt();
		
		for(int i=0;i<anunciosusuario.size();i++) {
			
			if(anunciosusuario.get(i).getId()==seleccion) {
				System.out.println("Anuncio seleccionado");
				buscado=anunciosusuario.get(i);
				break;
			}
		}
		

		return buscado;
		
	}
	/**
	 * Muestra todos los anuncios ordenados segun quiera el usuario.
	 */
	public void mostrarAnuncios() {
		
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Como quiere que esten ordenados los anuncios: 1. Fecha 2. Propietario");
		int seleccion=sc.nextInt();
		
		if(seleccion==1) {
			setListaAnuncios(ordenarFecha());
		}
		else if(seleccion==2) {
			setListaAnuncios(ordenarPropietario());
		}
		else {
			System.out.println("Opcion no valida");
		}
		String string=new String();
		for(Anuncio a: getListaAnuncios()) {
			System.out.println("");
			if(a.getClass().toString().equals("class ejercicio2.AnuncioGeneral")) {
				string=((AnuncioGeneral) a).tooString();
				System.out.println(string);
			}
			
			else if(a.getClass().toString().equals("class ejercicio2.AnuncioTematico")) {
				string=((AnuncioTematico) a).tooString();
				System.out.println(string);
			}
			else if(a.getClass().toString().equals("class ejercicio2.AnuncioIndividualizado")) {
				string= ((AnuncioIndividualizado) a).tooString();
				System.out.println(string);
			}
			
			else {
				string=((AnuncioFlash) a).tooString();
				System.out.println(string);
			}
			
		}
	}
	
	/**
	 * Metodo que ordena los anuncios por el nombre del propietario.
	 * @return El ArrayList de Anuncios ordenados.
	*/
	
	public ArrayList<Anuncio> ordenarPropietario() {
		ArrayList<Anuncio> aux= new ArrayList<Anuncio>();
		ArrayList<String> propietarios= new ArrayList<String>();
		ArrayList<Anuncio> ordenado= new ArrayList<Anuncio>();
		
		//Coge todos los nombres de los propietarios de todos los anuncios.
		aux= getListaAnuncios();
		int i=0;
		for(Anuncio a: aux) {
			propietarios.add(a.getUsuario().getNombre());
		}
		
		//Ordena los nombres.
		Collections.sort(propietarios);
		
		//Va comparando los nombres ordenados extraidos anteriormente con los que coge del vector principal hasta que sean iguales. Si coinciden pues los añade a otro vector y los elimina de los otros dos.
	
		while(aux.size()!=0) {
			for(int j=0; j<getListaAnuncios().size();) {
				
				if(propietarios.get(i).equals(aux.get(j).getUsuario().getNombre())) {
					ordenado.add(aux.get(j));
					
					propietarios.remove(i);
					aux.remove(j);
					j=0;
					
				}
				else {
					j++;
				}
				
			}
		}
		
		return ordenado;
	}
	
	/**
	 * Metodo que ordena los anuncios por fecha de publicacion.
	 * @return El ArrayList de Anuncios ordenados.
	*/
	
	public ArrayList<Anuncio> ordenarFecha() {
		
		ArrayList<Anuncio> aux= new ArrayList<Anuncio>();
		ArrayList<Date> fechas= new ArrayList<Date>();
		ArrayList<Anuncio> ordenado= new ArrayList<Anuncio>();
		
		//Coge la fecha de publicacion de todos los anuncios.
		aux= getListaAnuncios();
		int i=0;
		for(Anuncio a: aux) {
			fechas.add(a.getFecha());
		}
		
		//Ordena las fechas
		Collections.sort(fechas);
			
		//Va comparando las fechas ordenadas extraidas anteriormente con las que coge del vector principal hasta que sean iguales. Si coinciden pues las añade a otro vector y las elimina de los otros dos.
		
		while(aux.size()!=0) {
			for(int j=0; j<getListaAnuncios().size();) {
				
				if(fechas.get(i).equals(aux.get(j).getFecha())) {
					ordenado.add(aux.get(j));
					
					fechas.remove(i);
					aux.remove(j);
					j=0;
					
				}
				else {
					j++;
				}
				
			}
		}
		
		return ordenado;
		
	}
	
	public void main() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		boolean condicion =true;
		int a=0;
		Scanner sc=new Scanner(System.in);
		while(condicion) {
			System.out.println("Que quieres realizar: ");
			System.out.println("1. Editar Anuncio");
			System.out.println("2. Publicar Anuncio");
			System.out.println("3. Archivar Anuncio");
			System.out.println("4. Buscar Anuncio");
			System.out.println("5. Mostrar todos los anuncios");
			System.out.println("6. Salir");
			
			try {
				a=sc.nextInt();
				if(a==1) {
					try {
					modificarAnuncio(buscarAnuncio());
					}catch(NullPointerException e) {
						System.out.println("No existen anuncios con dichos parametros");
					}
				}
			
				else if(a==2) {
					try {
					publicarAnuncio();
					}catch(NullPointerException e) {
						System.out.println("Anuncio seleccionado no valido");
					}
				}
		
				else if(a==3) {
					try {
					archivarAnuncio();
					}catch(NullPointerException e) {
						System.out.println("Anuncio seleccionado no valido");
					}
			
				}
				
				else if(a==4) {
					try {
					Anuncio an=buscarAnuncio();
					
					String string=new String();
					string=an.toString();
					
					if(string.equals("class practica1.AnuncioTematico")) {
						string=((AnuncioTematico)an).tooString();
						System.out.println(string);
					}
					else if(string.equals("class practica1.AnuncioFlash")) {
						string=((AnuncioFlash)an).tooString();
						System.out.println(string);
					}
					else if(string.equals("class practica1.AnuncioIndividualizado")) {
						string=((AnuncioIndividualizado)an).tooString();
						System.out.println(string);
					}
					else if(string.equals("class practica1.AnuncioGeneral")) {
						string=((AnuncioGeneral)an).tooString();
						System.out.println(string);
					}
					}catch(NullPointerException e) {
						System.out.println("No se ha encontrado ningun anuncio con dichos parametros");
						System.out.println("");
					}
					
					
				}
				
				else if(a==5) {
					mostrarAnuncios();
				}
				
				else{
					condicion=false;
				}
			
			} catch (NoSuchElementException e) {
                System.out.println("Debes insertar un número");
             
                a=sc.nextInt();

            }
		
		}
	}
	
}
