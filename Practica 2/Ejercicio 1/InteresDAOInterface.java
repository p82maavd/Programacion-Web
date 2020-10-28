package ejercicio1;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InteresDAOInterface {
	
	public void crearInteres();

	public ArrayList<Interes> getIntereses() throws SQLException;

}
