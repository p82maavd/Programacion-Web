package ejercicio1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class ContactoDAO implements ContactoDAOInterface {

	
	private Connection con=null;
	
	private ControlDeErrores control=new ControlDeErrores();
	
	private DAOFactory factory=DAOFactory.getInstance();
	
	private InteresDAO interesesDAO= factory.getInteresDAO();
	private AnuncioDAO anunciosDAO;
	Configuracion config;
	
	private static ContactoDAO instance =null;
	
	private ContactoDAO(Connection e) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		this.con=e;
		System.out.println("Constructor del ContactoDAO");
		config= Configuracion.getInstance(null);
		
		//cargarContactos();
	}
	
	public static ContactoDAO getInstance(Connection e) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException {
		if(instance==null) {
			instance=new ContactoDAO(e);
		}
		return instance;
	}
	
	public ArrayList<Contacto> getContactos() throws SQLException{
		Statement stmt=con.createStatement();
		ResultSet rs= stmt.executeQuery(config.getProperty("OBTENER_CONTACTOS"));
		ArrayList<Contacto> contactos =new ArrayList<Contacto>();
		ArrayList<Interes> interesesaux = new ArrayList<Interes>();
		String email= new String();
		String nombre= new String();
		String apellidos= new String();
		Date date= new Date(0);
		String password=new String();
		Contacto a = null;
		
		try {
			
			while(rs.next()) {
				
				email = rs.getString(1);
				nombre=rs.getString(2);
				apellidos=rs.getString(3);
				date=rs.getDate(4);	
				password=rs.getString(5);
				interesesaux=interesesDAO.getInteresesContacto(email);
				
				a=new Contacto(email,nombre,apellidos,date,password, interesesaux);
				contactos.add(a);
				
			}
		
		}catch(Exception e) {
			System.out.println("Error al cargar los contactos de la Base de Datos");
		}
		return contactos;
		
	}
	
	@Override
	public void crearContacto() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		anunciosDAO=factory.getAnuncioDAO();
		Contacto e= new Contacto();
		
		
		int status=0;
		//Esto habira que ponerlo despues.
		
		try{
			for(int i=0; i<e.getIntereses().size();i++) {
				
				PreparedStatement ps2=con.prepareStatement(config.getProperty("INSERTAR_INTERES_CONTACTO"));
				ps2.setString(1, e.getEmail().toLowerCase());
				ps2.setInt(2, e.getIntereses().get(i).getId());
				status= ps2.executeUpdate();
				
				if(status!=1) {
					System.out.println("Error al añadir el interes "+e.getIntereses().get(i).getId()+ " al contacto");
				}
				
			}
			
			
			PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_CONTACTO"));
			
			ps.setString(1,e.getEmail().toLowerCase());
			ps.setString(2,e.getNombre());
			ps.setString(3,e.getApellidos());
			ps.setDate(4, e.getFechanacimiento());
			ps.setString(5, e.getPassword());
			
			
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al añadir el contacto");
			}
			
		} catch(Exception es) { 
				es.printStackTrace();
				
		}
		
		//Actualiza los destinatarios de los anuncios.
		
		for(Anuncio a: anunciosDAO.getAnuncios()) {
			System.out.println("Vamos a insertar el nuevo contacto como destinatario del anuncio : "+a.getTitulo());
			//COmprobar argumentos del if. ACTUALIZAR
			if(a.getClass().toString().equals("class ejercicio1.AnuncioFlash") || a.getClass().toString().equals("class ejercicio1.AnuncioGeneral")) {
				
				PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_DESTINATARIOS"));
		        ps.setInt(2, a.getId());
		        ps.setString(1, e.getEmail());
		        status = ps.executeUpdate();
		        
		        if(status!=1) {
		        	System.out.println("Error al añadir el usuario como destinatario del anuncio :"+a.getTitulo());
		        }
		        
			}
			
		}

	}

	@Override
	public void borrarContacto(Contacto e) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		anunciosDAO=factory.getAnuncioDAO();
		if(e==null) {
			System.out.println("No existe un contacto con dichos atributos");
			return;
		}
		
		int status=0;
			
		PreparedStatement ps=con.prepareStatement(config.getProperty("BORRAR_CONTACTO"));
		ps.setString(1,e.getEmail());
		status=ps.executeUpdate();

		if(status!=1) {
			System.out.println("No se ha podido eliminar el contacto de la base de datos");
		}
		
		//Actualiza los intereses de los contactos y los destinatarios.
        
        //Si funciona lo del delete on cascade esto no hace falta :D. No funciona D:
		PreparedStatement ps2=con.prepareStatement(config.getProperty("BORRAR_INTERESES_CONTACTO"));
        ps2.setString(1, e.getEmail());
        status = ps2.executeUpdate();
        
        if(status<1) {
			System.out.println("No se ha podido eliminar los intereses contacto de la base de datos");
		}
        
        PreparedStatement ps1=con.prepareStatement(config.getProperty("BORRAR_DESTINATARIO_EMAIL"));
        ps1.setString(1, e.getEmail());
        status = ps1.executeUpdate();
        if(status<1) {
			System.out.println("No se ha podido eliminar como destinatario el contacto seleccionado");
		}
        
	}
	
	@Override
	public void actualizarContacto(Contacto e) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		
		if(e==null) {
			System.out.println("No existe contacto con dichos atributos");
			return;
		}
		System.out.println("Que quieres modificar: 1. Nombre 2. Apellidos 3. Fecha Nacimiento 4. Intereses");
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca un número entero: ");
		Integer a = sc.nextInt();
		sc.nextLine();
		
		if(a==1) {
			
		    String nombreaux;
		    System.out.print("Introduzca el nuevo nombre: ");
		    nombreaux = sc.nextLine();
			actualizarContactoNombre(e,nombreaux);
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
		    actualizarContactoApellido(e,nuevoapellido);
		}
		
		else if(a==3) {
			String nuevafecha=new String();
			System.out.print("Introduzca la nueva fecha de nacimiento(dd/mm/yyyy): ");
			nuevafecha = sc.nextLine();
			 
			Date dnuevafecha = new Date(0);
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
					dnuevafecha = Date.valueOf(nuevafecha);
				} catch (IllegalArgumentException e1) {
					System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy hh:mm:ss): ");
					cont++;
				}
			}
			actualizarContactoFecha(e,nuevafecha);
			
		}
		//Comprobar que funciona
		
		else if(a==4) {
			actualizarContactoIntereses(e);
		}
		
	}
	
	public void actualizarContactoNombre(Contacto a, String nombreaux) throws SQLException {
        
        int status=0;
        PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_NOMBRE_CONTACTO"));
        ps.setString(1, nombreaux);
        ps.setString(2,a.getEmail());
        status = ps.executeUpdate();
		if(status!=1) {
			System.out.println("Error al actualizar en la base de datos");
		}
		
	}

	public void actualizarContactoApellido(Contacto a, String apellidos) throws SQLException {
		int status=0;
		PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_APELLIDOS_CONTACTO"));
	    ps.setString(1, apellidos);
	    ps.setString(2, a.getEmail());
	    status = ps.executeUpdate();
		if(status!=1) {
			System.out.println("Error al actualizar en la base de datos");
		}
		
	}
	
	public void actualizarContactoFecha(Contacto a, String fecha) throws SQLException {
		Integer status =0;
		
		Date dnuevafecha = Date.valueOf(fecha);
		PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_FECHANACIMIENTO_CONTACTO"));
		ps.setDate(1, dnuevafecha);
		ps.setString(2, a.getEmail());
		status = ps.executeUpdate();
		if(status!=1) {
			System.out.println("Error al actualizar en la base de datos");
		}
		
	}
	
	public void actualizarContactoIntereses(Contacto e) throws SQLException {
		ArrayList<Interes> listaintereses;
		Scanner sc= new Scanner(System.in);
		int status = 0;
		Integer eleccion;
		System.out.println("¿Desea eliminar algun interes? 1.Si 2.No");
		eleccion = sc.nextInt();
		//SI FALLA QUITAR ESTO.
		sc.nextLine();
		
		
		if(eleccion==1) {
			listaintereses=e.getIntereses();
			System.out.println("Actuales Intereses");
			for(int i=0; i<listaintereses.size();i++) {
			
				System.out.println(listaintereses.get(i).getId() +". "+ listaintereses.get(i).getInteres());
			}
			
			System.out.print("Que interes desea eliminar: ");
			
			
			Integer linea;
			linea = sc.nextInt();
			sc.nextLine();
			
			PreparedStatement ps=con.prepareStatement(config.getProperty("BORRAR_INTERES_CONTACTO"));
			ps.setInt(1,linea);
			ps.setString(2,e.getEmail());
			status=ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al eliminar el interes del contacto");
			}
			
			
		}
		else {
			listaintereses=interesesDAO.getIntereses();
			for(int i=0; i<listaintereses.size();i++) {
				
				System.out.println(listaintereses.get(i).getId() +". "+ listaintereses.get(i).getInteres());
			}
			
			System.out.print("Que interes añadir: ");
			
			Integer linea;
			linea = sc.nextInt();
			sc.nextLine();
			status=0;
			PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_INTERES_CONTACTO"));
			ps.setInt(2,linea);
			ps.setString(1,e.getEmail());
			status=ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al añadir el interes");
			}
			
		}
		
	}
	
	//Es posible que sea util separar las funciones.
	@Override
	public Contacto buscarContacto()  {
		
		System.out.println("Elige parametro de busqueda: ");
		System.out.println("1. Nombre ");
		System.out.println("2. Apellidos");
		System.out.println("3. Email");
		System.out.println("4. Fecha de nacimiento");
		System.out.println("5. Interes");
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca un número entero: ");
		Integer a = sc.nextInt();
		sc.nextLine();
		Contacto buscado;
		Statement stmt=null;
		try {
			stmt= con.createStatement();
			
			Integer size=0;
			try {
				ResultSet rs= stmt.executeQuery(config.getProperty("SIZE"));
				rs.next();
				size=rs.getInt("count(email)");
			
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			if(size==0) {
				System.out.println("No hay ningun usuario en la base de datos");
				return null;
			}
			
			//Preguntar si se puede hacer con los vectores o directamente en la base de datos.
			if(a==1) {
				System.out.print("Introduce el nombre a buscar:");
				String nombre= new String();
				nombre = sc.nextLine();
				buscado=buscarContactoNombre(nombre);
				return buscado;
			}
			
			else if(a==2) {
				System.out.print("Introduce los apellidos a buscar:");
				String apellidos= new String();
				apellidos = sc.nextLine();
				buscado=buscarContactoApellidos(apellidos);
				return buscado;
			}
			
			else if(a==3) {
				System.out.print("Introduce el email a buscar:");
				String email= new String();
				email = sc.nextLine();
				email=email.toLowerCase();
				buscado=buscarContactoEmail(email);
				return buscado;
	
			}
			
			else if(a==4) {
				System.out.print("Introduce la fecha a buscar:");
				String nuevafecha=new String();
				Date dnuevafecha = new Date(0);
				int cont=1;
				while(cont!=0) {
					cont=0;
					try {
						nuevafecha = sc.nextLine();
						while(!(control.esFecha(nuevafecha))) {
							System.out.println("Formato de la fecha (yyyy-mm-dd)");
							System.out.print("Vuelva a introducir la fecha: ");
							nuevafecha=sc.nextLine();
						}
						dnuevafecha = Date.valueOf(nuevafecha);
					} catch (IllegalArgumentException e1) {
						System.out.print("Error con la fecha. Vuelva a introducirla(yyyy-mm-dd): ");
						cont++;
					}
				}
				buscado=buscarContactoFecha(nuevafecha);
				return buscado;
	
			}
			
			
			else if(a==5) {
				System.out.println("Introduce el interes a buscar:");
				//Poner algo aqui.
				buscado=buscarContactoInteres();
				return buscado;
				
	
			}	
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
		return null;
	}
	
	//Si se hace sin vectores
	
	public Contacto buscarContactoNombre(String nombre) throws SQLException {
		
		PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_CONTACTOS_NOMBRE"));
		Integer cont=1;
		Contacto contacto=null;
		Scanner sc =new Scanner(System.in);
		ArrayList<Contacto> contactos = new ArrayList<Contacto>();
		ArrayList<Integer> interesesid=new ArrayList<Integer>();
		ArrayList<Interes> intereses = new ArrayList<Interes>();
		PreparedStatement ps2;
		ResultSet rs2;
		try{
		
			nombre= nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
			
			ps.setString(1,nombre);
			ResultSet rs= ps.executeQuery();
		
			while(rs.next()) {
				
				ps2=con.prepareStatement(config.getProperty("OBTENER_INTERESES_CONTACTO"));
				ps2.setString(1, rs.getString(1));
				rs2=ps2.executeQuery();
				
				while(rs2.next()) {
					interesesid.add(rs2.getInt(2));
				}
				for(Interes i: interesesDAO.getIntereses()) {
					if(interesesid.contains(i.getId())) {
						intereses.add(i);
					}
				}
				contacto=new Contacto(rs.getString(1),rs.getString(2),rs.getString(3), rs.getDate(4), rs.getString(5),intereses);
				contactos.add(contacto);
				System.out.println(cont + ". "+toString(contacto));
				cont++;
			}
			
			if(contactos.size()==0) {
				System.out.println("No existe ningun usuario con dicho nombre");
				return null;
			}
			int seleccion=0;
			if(contactos.size()==1) {
				return contactos.get(0);
			}
			else {
				System.out.print("Que usuario es el que busca: ");
				seleccion=sc.nextInt();
				sc.nextLine();
			}
			rs.beforeFirst();
			return contactos.get(seleccion-1);
			
		} catch(Exception e) { 
				e.printStackTrace();
				
		}
		return null;
	}
	
	
	public Contacto buscarContactoApellidos(String apellidos) throws SQLException {
		PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_CONTACTOS_APELLIDOS"));
		Integer cont=1;
		Contacto contacto=null;
		Scanner sc =new Scanner(System.in);
		ArrayList<Contacto> contactos = new ArrayList<Contacto>();
		ArrayList<Integer> interesesid=new ArrayList<Integer>();
		ArrayList<Interes> intereses = new ArrayList<Interes>();
		PreparedStatement ps2;
		ResultSet rs2;
		try{
			
			apellidos = apellidos.substring(0, 1).toUpperCase() + apellidos.substring(1).toLowerCase();
			
			ps.setString(1,apellidos);
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				
				ps2=con.prepareStatement(config.getProperty("OBTENER_INTERESES_CONTACTO"));
				ps2.setString(1, rs.getString(1));
				rs2=ps2.executeQuery();
				while(rs2.next()) {
					interesesid.add(rs2.getInt(2));
				}
				for(Interes i: interesesDAO.getIntereses()) {
					if(interesesid.contains(i.getId())) {
						intereses.add(i);
					}
				}
				contacto = new Contacto(rs.getString(1),rs.getString(2),rs.getString(3), rs.getDate(4),rs.getString(5),intereses);
				contactos.add(contacto);
				System.out.println(cont + ". "+toString(contacto));
				cont++;
			}
			
			if(contactos.size()==0) {
				System.out.println("No existe ningun usuario con dicho nombre");
				return null;
			}
			int seleccion=0;
			if(contactos.size()==1) {
				return contactos.get(0);
			}
			else {
				System.out.print("Que usuario es el que busca: ");
				seleccion=sc.nextInt();
				sc.nextLine();
			}
			
			return contactos.get(seleccion-1);
			
			
		} catch(Exception e) { 
				e.printStackTrace();
				
		}
		return null;
	}
	
	public Contacto buscarContactoEmail(String email) throws SQLException {
		
		PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_CONTACTOS_EMAIL"));
		ArrayList<Integer> interesesid=new ArrayList<Integer>();
		ArrayList<Interes> intereses = new ArrayList<Interes>();
		PreparedStatement ps2;
		Contacto contacto=null;
		ResultSet rs2;
		try {
			
			ps.setString(1,email);
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				
				ps2=con.prepareStatement(config.getProperty("OBTENER_INTERESES_CONTACTO"));
				ps2.setString(1, rs.getString(1));
				rs2=ps2.executeQuery();
				while(rs2.next()) {
					interesesid.add(rs2.getInt(2));
				}
				for(Interes i: interesesDAO.getIntereses()) {
					if(interesesid.contains(i.getId())) {
						intereses.add(i);
					}
				}
				contacto = new Contacto(rs.getString(1),rs.getString(2),rs.getString(3), rs.getDate(4), rs.getString(5),intereses);
				return contacto;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("No hay ningun usuario con dicho email.");
		return contacto;
	}
	
	public Contacto buscarContactoFecha(String fecha) throws SQLException {
		PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_CONTACTOS_FECHA"));
		Integer cont=1;
		Date dnuevafecha = Date.valueOf(fecha);
		Contacto contacto=null;
		Scanner sc =new Scanner(System.in);
		ArrayList<Contacto> contactos = new ArrayList<Contacto>();
		ArrayList<Integer> interesesid=new ArrayList<Integer>();
		ArrayList<Interes> intereses = new ArrayList<Interes>();
		PreparedStatement ps2;
		ResultSet rs2;
		try{
			
			ps.setDate(1,dnuevafecha);
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				ps2=con.prepareStatement(config.getProperty("OBTENER_INTERESES_CONTACTO"));
				ps2.setString(1, rs.getString(1));
				rs2=ps2.executeQuery();
				while(rs2.next()) {
					interesesid.add(rs2.getInt(2));
				}
				for(Interes i: interesesDAO.getIntereses()) {
					
					if(interesesid.contains(i.getId())) {
						intereses.add(i);
					}
				}
				contacto = new Contacto(rs.getString(1),rs.getString(2),rs.getString(3), rs.getDate(4),rs.getString(5),intereses);
				contactos.add(contacto);
				System.out.println(cont + ". "+toString(contacto));
				cont++;
			}
			
			if(contactos.size()==0) {
				System.out.println("No existe ningun usuario con dicho nombre");
				return null;
			}
			int seleccion=0;
			if(contactos.size()==1) {
				return contactos.get(0);
			}
			else {
				System.out.print("Que usuario es el que busca: ");
				seleccion=sc.nextInt();
				sc.nextLine();
			}
			
			return contactos.get(seleccion-1);
			
		} catch(Exception e) { 
				e.printStackTrace();
				
		}
		return null;
	}
	
	public Contacto buscarContactoInteres() throws SQLException{
		
		ArrayList<Contacto> aux=new ArrayList<Contacto>();
		ArrayList<String> auxemail=new ArrayList<String>();
		Contacto buscado=new Contacto(null,null,null,null,null,null);
		Scanner sc=new Scanner(System.in);
		for(Interes i: interesesDAO.getIntereses()) {
			System.out.println(i.getId()+". "+i.getInteres());
			
		}
		
		System.out.print("Indique que interes buscar: ");
	    int seleccion = sc.nextInt();
	    sc.nextLine();
	    Interes interesaux=null;
		
		for(Interes i: interesesDAO.getIntereses()) {
			
			if(seleccion==i.getId()) {
				interesaux=i;		
			}
			
		}
		
		//Falta el status
		PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_CONTACTOS_INTERES"));
		ps.setInt(1, interesaux.getId());
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			
			auxemail.add(rs.getString(1));
		}
		
		for(Contacto d: getContactos()) {
			
			if(auxemail.contains(d.getEmail())) {	
				
				aux.add(d);
				
			}
		}
		
		
		if(aux.size()==0) {
			
			return buscado;
		}
		
		if(aux.size()==1) {
			
			return aux.get(0);
		}
		
		//Imprime todos los contactos con dicho interes.
		
		for(Integer i=0;i<aux.size();i++) {
			System.out.println(i.toString()+". Nombre: "+aux.get(i).getNombre()+" Email: "+ aux.get(i).getEmail());
			
		}
		
		System.out.print("Selecciona el contacto buscado: ");
			
		int seleccion2=sc.nextInt();
		sc.nextLine();
		
		buscado = aux.get(seleccion2);
		return buscado;
	}
	
	/*public String imprimirContacto(String a, String b, String string, String d) {
		//Mirar algo para formatear fecha sql.date.
		String cadena= "Nombre: "+a+ " Apellidos: "+b+" Fecha de Nacimiento: "+string+" Email: "+d;
		return cadena;
	}*/
	@Override
	public void mostrarContactos() throws SQLException {
		for(Contacto e: getContactos()) {
			System.out.println(e.getEmail()+" "+e.getNombre());
		}
	}
	//Revisar
	public void añadirInteres(Contacto e) throws SQLException {
		Scanner sc=new Scanner(System.in);
		
		ArrayList<Interes> aux=e.getIntereses();
		
		ArrayList<Interes> listaintereses=new ArrayList<Interes>();
		listaintereses=interesesDAO.getIntereses();
		for(int i=0; i<listaintereses.size();i++) {
			if(aux.contains(listaintereses.get(i))){
				
			}
			
			else {
				System.out.println(listaintereses.get(i).getId() +". "+ listaintereses.get(i).getInteres());
			}
		}
		
		System.out.print("Que interes añadir: ");
		
		Integer linea;
		linea = sc.nextInt();
		sc.nextLine();
		
		PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_INTERES_CONTACTO"));
		ps.setInt(2,linea);
		ps.setString(1,e.getEmail());
		int status=0;
		status=ps.executeUpdate();
		if(status!=1) {
			System.out.println("Error al insertar el nuevo interes");
		}
		
	}
	
	public String toString(Contacto a) {
		
		String s =a.getEmail() + " " +a.getNombre();
		return s;
		
	}
	
	public void main() {
		
		Scanner sc= new Scanner(System.in);
		int a=0;
		boolean condicion=true;
			
		try {
			
			while(condicion) {
				System.out.println("Que quieres realizar: ");
				System.out.println("1. Añadir Contacto");
				System.out.println("2. Mostrar Contactos");
				System.out.println("3. Eliminar Contacto");
				System.out.println("4. Actualizar Contacto");
				System.out.println("5. Consultar Contacto");
				System.out.println("6. Salir");
				try {
					a=sc.nextInt();
						
					if(a==1) {
						crearContacto();
								
					}
						
					else if(a==2) {
						mostrarContactos();
					}
					
					else if(a==3) {
						borrarContacto(buscarContacto());
					
					}
							
					else if(a==4) {
						try {
							actualizarContacto(buscarContacto());
						}catch(NullPointerException e) {
									
						}
								
					}
							
					else if(a==5) {
						try {
							System.out.println(toString(buscarContacto()));
						}catch(NullPointerException e) {
									
						}
					}
					
					
					else if(a==6) {
						
						condicion=false;
					}
						
					else{
						condicion=false;
					}
						
				} catch (NoSuchElementException e) {
					System.out.println("Debes insertar un número");
			             
			        a=sc.nextInt();

			    }
					
			} //Fin While
				
			
		}catch(Exception e) {
			System.out.println("Error");
		}
	}//Fin del metodo main	

}//Fin de la clase	
