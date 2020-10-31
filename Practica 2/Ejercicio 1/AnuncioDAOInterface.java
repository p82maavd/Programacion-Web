package ejercicio1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public interface AnuncioDAOInterface {
	
	public void crearAnuncio() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException;
	public void borrarAnuncio(Anuncio e);
	public Anuncio buscarAnuncio();
	public void mostrarANuncios();
	public void actualizarAnuncio(Anuncio e) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException;
	public void cargarAnuncios() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException;
	

}
