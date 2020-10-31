package ejercicio1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {
	
	private static DAOFactory instance=null;
	// method to create connections
	public static Connection createConnection() {
		Connection con=null;
		try {
		  Class.forName("com.mysql.jdbc.Driver");
		  
		  con= DriverManager.getConnection("jdbc:mysql://oraclepr.uco.es:3306/p82maavd","p82maavd","1234");
		
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return con;
	}
	private DAOFactory() {
		
	}
	public static DAOFactory getInstance() {
		if(instance==null) {
			instance=new DAOFactory();
			return instance;
		}
		
		return instance;
	}
	public ContactoDAO getContactoDAO() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
	
		return ContactoDAO.getInstance(createConnection());
		
	}
	public AnuncioDAO getAnuncioDAO() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		
		return AnuncioDAO.getInstance(createConnection());
	}
	
	public InteresDAO getInteresDAO() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		
		return  InteresDAO.getInstance(createConnection());
		
	}
	
		  

}
