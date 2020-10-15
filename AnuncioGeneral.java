package Ejercicio2;

import java.util.ArrayList;

public class AnuncioGeneral extends Anuncio{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnuncioGeneral(int id, String titulo, String cuerpo, Contacto usuario, ArrayList<Contacto> destinatarios, Estados estado) {
		super(id, titulo, cuerpo, usuario, destinatarios, estado);
		
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.destinatarios=destinatarios;
		this.estado=estado;
		
		//Habria que poner algo para que destinatarios contenga a todos los usuarios pero no se como ponerlo :(
		//Probar con algo como:
		//this.destinatarios=gestorContactos.getContactos() que dicha funcion devuelva Array de Contactos
		//Pero deberia de actualizarse cada vez que se publica un anuncio y se registra un usuario y tal. Para que se le muestre a todos.
		
		//CREO QUE YA ESTA ARREGLADO
		
	}
	
	

}
