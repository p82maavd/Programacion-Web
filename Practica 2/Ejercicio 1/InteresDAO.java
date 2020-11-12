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
import java.util.Scanner;

public class InteresDAO implements InteresDAOInterface{

	private Connection con=null;
	
	private static InteresDAO instance;
	
	public InteresDAO(Connection e) throws SQLException {
		this.con=e;
	}
	
	public static InteresDAO getInstance(Connection e) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		
		if(instance==null) {
			instance=new InteresDAO(e);
		}
		return instance;
	}
	
	
	@Override
	public void crearInteres() {
		
		int status=0;
		String nuevointeres=new String();
		Scanner sc=new Scanner(System.in);
		try{
			System.out.print("Introduce el nuevo interes: ");
			nuevointeres=sc.nextLine();
			
			PreparedStatement ps=con.prepareStatement("insert into intereses (interes) values (?)");
			
			ps.setString(1,nuevointeres);
			
			status = ps.executeUpdate();
			
			
		} catch(Exception es) { 
				es.printStackTrace();
				
		}
		
		if(status!=1) {
			System.out.println("No se ha podido añadir el interes a la base de datos");
		}
	
	}
	//Esto es para cargar los intereses.
	@Override
	public ArrayList<Interes> getIntereses() throws SQLException{
		Statement stmt=con.createStatement();
		ArrayList<Interes> listaintereses = new ArrayList<Interes>();
		ResultSet rs= stmt.executeQuery("SELECT idintereses,interes FROM intereses");
		
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
	
	//Te devuelve los intereses de un contacto
	//No sirve porque se cargan en el vector xd. Ahora sirve de algo en cargarContactos
	public ArrayList<Interes> getInteresesContacto(String email) throws SQLException{
		ArrayList<Interes> interesescontacto=new ArrayList<Interes>();
		PreparedStatement ps=con.prepareStatement("select idinteres from intereses_contactos where emailcontacto=?");
		ps.setString(1,email);
		ResultSet rs= ps.executeQuery();
		
		while(rs.next()) {
			for(Interes i: getIntereses()) {
				if(rs.getInt(1) == i.getId()) {
					interesescontacto.add(i);
				}
			}
		}
		
		return interesescontacto;
		
	}
	
	public ArrayList<Interes> getInteresesAnuncio(int id) throws SQLException{
		ArrayList<Interes> interesesanuncio=new ArrayList<Interes>();
		PreparedStatement ps=con.prepareStatement("select idinteres from intereses_anuncios where idanuncio=?");
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
