package ejercicio1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface ContactoDAOInterface {
	
	public void crearContacto() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException;
	public void borrarContacto(Contacto e);
	public Contacto buscarContacto();
	public void mostrarContactos();
	public void actualizarContacto(Contacto e) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException;
	public void cargarContactos() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException;
	
	
}
