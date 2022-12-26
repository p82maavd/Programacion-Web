package es.uco.pw.practica3.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import es.uco.pw.practica3.business.Configuracion;
import es.uco.pw.practica3.business.Interes;

/**
 * Declaracion de la clase InteresDAO.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 */
public class InteresDAO {

	private Connection con=null;
	
	private Configuracion config;
	
	private static InteresDAO instance;
	
	/**
	 * Constructor de la clase InteresDAO
	 * @param e Objeto de la Clase Connection para la base de datos
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private InteresDAO(Connection e) throws SQLException, ClassNotFoundException, IOException {
		this.con=e;
		config=Configuracion.getInstance(null);
		
	}
	
	/**
	 * Metodo Singleton para obtener una instancia de InteresDAO
	 * @param e Objeto de la Clase Connection para la base de datos
	 * @return Instancia Unica de InteresDAO
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public static InteresDAO getInstance(Connection e) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		
		if(instance==null) {
			instance=new InteresDAO(e);
		}
		return instance;
	}
	
	
	/**
	 * Metodo para añadir un nuevo interes a la base de datos
	 * @param nuevointeres Interes que se quiere añadir
	 */
	public void crearInteres(String nuevointeres) {
		
		int status=0;
		
		try{
			
			
			PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_INTERES"));
			
			ps.setString(1,nuevointeres);
			
			status = ps.executeUpdate();
			
			
		} catch(Exception es) { 
				es.printStackTrace();
				
		}
		
		if(status!=1) {
			System.out.println("No se ha podido insertar el interes a la base de datos");
		}
	
	}
	
	/**
	 * Metodo para borrar un interes
	 * @param id Identificador de un anucnio
	 */
	public void borrarInteres(int id) {
		
		int status=0;
		
		try{
			
			PreparedStatement ps=con.prepareStatement(config.getProperty("BORRAR_INTERES"));
			ps.setInt(1,id);
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("No se ha podido insertar el interes a la base de datos");
			}
			
			
		} catch(Exception es) { 
				es.printStackTrace();
				
		}
		
	}
	
	/**
	 * Metodo que devuelve los intereses de la base de datos.
	 * @return Devuelve todos los intereses de la base de datos.
	 * @throws SQLException
	 */
	public ArrayList<Interes> getIntereses() throws SQLException{
		ArrayList<Interes> listaintereses = new ArrayList<Interes>();
		Statement stmt=con.createStatement();
		ResultSet rs= stmt.executeQuery(config.getProperty("OBTENER_INTERESES"));
		
		String interes= new String();
		int id=0;
	
		try {
			
			while(rs.next()) {
				id=rs.getInt(1);
				interes = rs.getString(2);
				listaintereses.add(new Interes(id,interes));
				
			}
			
		}catch(Exception e) {
			System.out.println("Error al cargar los contactos de la Base de Datos");
		}
		
		return listaintereses;
		
	}
	
	
	/**
	 * Metodo para obtener los intereses de un contacto
	 * @param email Email del contacto del cual queremos obtener los intereses.
	 * @return Devuelve los intereses del contacto
	 * @throws SQLException
	 */
	public ArrayList<Interes> getInteresesContacto(String email) throws SQLException{
		ArrayList<Interes> interesescontacto=new ArrayList<Interes>();
		PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_INTERESES_CONTACTO"));
		ps.setString(1,email);
		ResultSet rs= ps.executeQuery();
		
		while(rs.next()) {
			for(Interes i: getIntereses()) {
				if(rs.getInt(2) == i.getId()) {
					interesescontacto.add(i);
				}
			}
		}
		
		return interesescontacto;
		
	}
	
	/**
	 * Metodo para obtener los intereses de un anuncio
	 * @param id Id del anuncio del cual queremos obtener los intereses.
	 * @return Devuelve los intereses del anuncio
	 * @throws SQLException
	 */
	public ArrayList<Interes> getInteresesAnuncio(int id) throws SQLException{
		ArrayList<Interes> interesesanuncio=new ArrayList<Interes>();
		PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_INTERESES_ANUNCIO"));
		ps.setInt(1,id);
		ResultSet rs= ps.executeQuery();
		
		while(rs.next()) {
			for(Interes i: getIntereses()) {
				if(rs.getInt(1) == i.getId()) {
					interesesanuncio.add(i);
				}
			}
		}
		
		return interesesanuncio;
		
	}
	

}