package Ejercicio2;

import java.util.ArrayList;


public class AnuncioTematico extends Anuncio{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList <String> intereses;

	public AnuncioTematico(int id, String titulo, String cuerpo, Contacto usuario, ArrayList<Contacto> destinatarios, ArrayList<String> intereses, Estados estado) {
		super(id, titulo, cuerpo, usuario, destinatarios, estado);
		this.intereses=intereses;
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.destinatarios=destinatarios;
		this.estado=estado;
		
	}

	public ArrayList <String> getIntereses() {
		return intereses;
	}

	public void setIntereses(ArrayList <String> intereses) {
		this.intereses = intereses;
	}

}
