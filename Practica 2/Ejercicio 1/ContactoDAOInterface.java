package ejercicio1;

import java.sql.SQLException;

public interface ContactoDAOInterface {
	public void crearContacto();
	public void borrarContacto(Contacto e);
	public Contacto buscarContacto();
	public void actualizarContacto(Contacto e) throws SQLException;
	public void cargarContactos() throws SQLException;
	
}
