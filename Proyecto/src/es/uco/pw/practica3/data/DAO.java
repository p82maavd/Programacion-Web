package es.uco.pw.practica3.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Declaracion de la clase DAO, que funciona como una factoria.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 */
public class DAO {
	
	private static DAO instance=null;
	
	private Connection con;
	
	public void createConnection(String string1,String string2,String string3) {
		Connection con=null;
		try {
		  Class.forName("com.mysql.jdbc.Driver");
		  
		  con= DriverManager.getConnection(string1,string2,string3);
		
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e);
			
		}
		this.con=con;
	}
	private DAO(String string1,String string2,String string3) {
		
		createConnection(string1,string2,string3);
		
	}
	public static DAO getInstance(String string1,String string2,String string3) {
		//Probar segunda condicion
		if(instance==null || instance.con==null) {
			instance=new DAO(string1,string2,string3);
			return instance;
		}
		
		return instance;
	}
	public ContactoDAO getContactoDAO() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		
		return ContactoDAO.getInstance(this.con);
		
	}
	public AnuncioDAO getAnuncioDAO() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		
		return AnuncioDAO.getInstance(this.con);
	}
	
	public InteresDAO getInteresDAO() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		
		return  InteresDAO.getInstance(this.con);
		
	}
	
		  

}