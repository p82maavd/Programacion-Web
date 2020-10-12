package practica1;

import java.util.ArrayList;

public class AnuncioIndividualizado extends Anuncio{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Contacto> destinatarios;

	public AnuncioIndividualizado(int id, String titulo, String cuerpo, Contacto usuario, ArrayList<Contacto> destinatarios, Estados estado) {
		super(id, titulo, cuerpo, usuario, destinatarios, estado);
		// TODO Auto-generated constructor stub
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.destinatarios=destinatarios;
		this.estado=estado;
	}

}
