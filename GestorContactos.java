package practica1entrega;

import java.util.ArrayList;

public class GestorContactos {
  private static GestorContactos instance =null;
	
	private ArrayList <Contacto> listaContactos;

	//Singleton
	
	public static GestorContactos getInstance() {
		if(instance==null) {
			instance=new GestorContactos();
		}
		return instance;
	}
	//Final Singleton
	
	public GestorContactos() {
		
		this.listaContactos = new ArrayList<Contacto>();
		
  }
}
