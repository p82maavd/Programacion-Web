package practica1;

import java.util.ArrayList;

import practica1.Contacto.Intereses;


public class AnuncioTematico extends Anuncio{
	
	private ArrayList <Intereses> intereses;

	public AnuncioTematico(int id, String titulo, String cuerpo, Contacto usuario, ArrayList<Contacto> destinatarios, ArrayList<Intereses> intereses) {
		super(id, titulo, cuerpo, usuario, destinatarios);
		this.intereses=intereses;
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.destinatarios=destinatarios;
		//Los destinatarios seran los que tengan los intereses en comun.
	}

	public ArrayList <Intereses> getIntereses() {
		return intereses;
	}

	public void setIntereses(ArrayList <Intereses> intereses) {
		this.intereses = intereses;
	}

}
