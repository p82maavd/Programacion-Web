package es.uco.pw.practica3.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import es.uco.pw.practica3.business.Anuncio;
import es.uco.pw.practica3.business.Configuracion;
import es.uco.pw.practica3.business.Contacto;
import es.uco.pw.practica3.business.Interes;

/**
 * Declaracion de la clase ContactoDAO.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 */
public class ContactoDAO {

	
	private Connection con=null;
	
	private DAO factory=DAO.getInstance(null,null,null);
	
	private InteresDAO interesesDAO= factory.getInteresDAO();
	private AnuncioDAO anunciosDAO;
	Configuracion config;
	
	private static ContactoDAO instance =null;
	
	private ContactoDAO(Connection e) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		this.con=e;
		
		config= Configuracion.getInstance(null);
		
	}
	
	/**
	 * Devuelve una instancia de la clase ContactoDAO
	 * @param e Objeto de la clase Connection para conectarse a la base de datos
	 * @return instancia de la clase ContactoDAO
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static ContactoDAO getInstance(Connection e) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException {
		
		if(instance==null) {
			instance=new ContactoDAO(e);
			return instance;
		}
		return instance;
	}
	
	/**
	 * Metodo para obtener los contactos existentes en la base de datos.
	 * @return ArrayList<Contacto> con los contactos de la base de datos
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public ArrayList<Contacto> getContactos() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException{
		anunciosDAO=factory.getAnuncioDAO();
		Statement stmt=con.createStatement();
		ResultSet rs= stmt.executeQuery(config.getProperty("OBTENER_CONTACTOS"));
		ArrayList<Contacto> contactos =new ArrayList<Contacto>();
		ArrayList<Interes> interesesaux = new ArrayList<Interes>();
		String email= new String();
		String nombre= new String();
		String apellidos= new String();
		String password = new String();
		Date date= new Date(0);
		Contacto a = null;
		
		try {
			
			while(rs.next()) {
				
				email = rs.getString(1);
				nombre=rs.getString(2);
				apellidos=rs.getString(3);
				date=rs.getDate(4);
				password=rs.getString(5);
				interesesaux=interesesDAO.getInteresesContacto(email);
				
				a=new Contacto(email,nombre,apellidos,date,password,interesesaux);
				contactos.add(a);
				
			}
		
		}catch(Exception e) {
			System.out.println("Error al cargar los contactos de la Base de Datos");
		}
		return contactos;
		
	}
	
	public ArrayList<String> getContactosInteres(int id) throws SQLException{
		
		ArrayList<String> contactos = new ArrayList<String>();
		
				PreparedStatement ps3=con.prepareStatement(config.getProperty("OBTENER_CONTACTO_INTERES"));
				ps3.setInt(1,id);
				
				
				ResultSet rs=ps3.executeQuery();
				
				while(rs.next()) {
					contactos.add(rs.getString(1));
				}
				
				return contactos;
		
	}
	
	/**
	 * A�ade un contacto y sus intereses a la base de datos.
	 * @param e Contacto que se quiere crear
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void crearContacto(Contacto e) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		anunciosDAO= factory.getAnuncioDAO();
		
		int status=0;
		
		
		try{
			
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
		} catch(com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException es) { 
			System.out.println("Email del nuevo usuario ya esta registrado..");
			return;
			
			
		}
		try {
			
			for(int i=0; i<e.getIntereses().size();i++) {
				
				PreparedStatement ps2=con.prepareStatement(config.getProperty("INSERTAR_INTERES_CONTACTO"));
				ps2.setString(1, e.getEmail().toLowerCase());
				ps2.setInt(2, e.getIntereses().get(i).getId());
				
				status= ps2.executeUpdate();
				
				if(status!=1) {
					System.out.println("Error al añadir los intereses de un usuario");
				}
				
			}
			
		} catch(Exception es) { 
				es.printStackTrace();
				
		}
		
		if(status!=1) {
			System.out.println("No se ha podido añadir el contacto a la base de datos");
		}
		
		//Actualiza los destinatarios de los anuncios.
		
		for(Anuncio a: anunciosDAO.getAnuncios()) {
			System.out.println("Vemos anuncio: "+a.getTitulo()+a.getClass().toString());
			if(a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioFlash") || a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioGeneral")) {
				System.out.println("A�ado destinatario anuncio"+a.getTitulo());
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


	/**
	 * Borra un contacto de la base de datos
	 * @param e Contacto que se quiere borrar
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	
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
        
       
	}
	
	
	/**
	 * Cambia el nombre de un contacto en la base de datos.
	 * @param a Contacto del que se cambia el nombre
	 * @param nombreaux nuevo nombre del contacto
	 * @throws SQLException
	 */
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
	
	/**
	 * Cambia los apellidos de un contacto en la base de datos
	 * @param a Contacto del que se cambian los apellidos
	 * @param apellidos Nuevos apellidos del contacto
	 * @throws SQLException
	 */
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
	
	/**
	 *  Cambia la fecha de nacimiento de un contacto en la base de datos
	 * @param a Contacto del que se cambia la fecha de nacimiento
	 * @param fecha Nueva Nueva fecha de nacimiento del contacto
	 * @throws SQLException
	 * @throws ParseException
	 */
	public void actualizarContactoFecha(Contacto a, String fecha) throws SQLException, ParseException {
		Integer status =0;
		java.util.Date date = new java.util.Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		date = format.parse(fecha);
		
		Date dfecha = new Date(date.getTime());
		PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_FECHANACIMIENTO_CONTACTO"));
		ps.setDate(1, dfecha);
		ps.setString(2, a.getEmail());
		status = ps.executeUpdate();
		if(status!=1) {
			System.out.println("Error al actualizar en la base de datos");
		}
		
	}
	
	
	/**
	 * Cambia los datos de un contacto en la base de datos
	 * @param a Contacto del que se cambia sus intereses
	 * @param intereses Nuevos intereses del contacto
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public void actualizarContactoInteres(Contacto a, String[] intereses) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException{
		anunciosDAO=factory.getAnuncioDAO();
		int status=0;
		PreparedStatement ps2=con.prepareStatement(config.getProperty("BORRAR_INTERESES_CONTACTO"));
        ps2.setString(1, a.getEmail());
        status = ps2.executeUpdate();
        
        if(status<1) {
			System.out.println("No se ha podido eliminar los intereses contacto de la base de datos");
		}
        
        for(Anuncio e: anunciosDAO.getAnunciosTematicos()) {  	
	        PreparedStatement ps0=con.prepareStatement(config.getProperty("BORRAR_DESTINATARIO_DOSPARAMETROS"));
	        ps0.setInt(2, e.getId());
	        ps0.setString(1,a.getEmail());
	        status=ps0.executeUpdate();
	        if(status<1) {
				System.out.println("No se ha podido eliminar el contacto como destinatario");
			}
        
        }
        if(intereses!=null) {
			for(String s:intereses) {			
			
				PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_INTERES_CONTACTO"));
				ps.setInt(2,Integer.parseInt(s));
				ps.setString(1,a.getEmail());
				status=ps.executeUpdate();
				
				if(status!=1) {
					System.out.println("No se ha a�adido el interes...");
				}
				
				for(Integer b: anunciosDAO.getAnunciosInteres(Integer.parseInt(s))) {
					try {
						PreparedStatement ps3=con.prepareStatement(config.getProperty("INSERTAR_DESTINATARIOS"));
						ps3.setString(1,a.getEmail());
						ps3.setInt(2,b);
						
						status=ps3.executeUpdate();
					}catch(Exception e) {
						System.out.println("El contacto ya esta como destinatario del anuncio");
					}
					
				}
			}
        }
		
	}
	
	/**
	 * Metodo que permite obtener un contacto con su email
	 * @param email String que se corresponde con el email del contacto a buscar
	 * @return Contacto buscado
	 * @throws SQLException
	 */
	public Contacto buscarContactoEmail(String email) throws SQLException {
		PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_CONTACTOS_EMAIL"));
		
		ArrayList<Interes> intereses = new ArrayList<Interes>();
		
		Contacto contacto=null;
		
		try {
			
			ps.setString(1,email);
			ResultSet rs= ps.executeQuery();
			
			rs.next();
			
			intereses=interesesDAO.getInteresesContacto(rs.getString(1));
			contacto = new Contacto(rs.getString(1),rs.getString(2),rs.getString(3), rs.getDate(4),rs.getString(5),intereses);
			return contacto;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("No hay ningun usuario con dicho email.");
		return contacto;
	}

}//Fin de la clase	