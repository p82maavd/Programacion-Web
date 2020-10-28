package ejercicio1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {
		 
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
	public ContactoDAO getContactoDAO() throws SQLException {
	
		return new ContactoDAO(createConnection());
		
	}
	/*public AnuncioDAO getAnuncioDAO() {
		
		return new AnuncioDAO();
	}*/
	
	public InteresDAO getInteresDAO() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		
		return  InteresDAO.getInstance(createConnection());
		
	}
	
		  

}
