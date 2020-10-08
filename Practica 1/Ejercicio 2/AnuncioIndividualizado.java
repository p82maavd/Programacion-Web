package practica1;

import java.util.ArrayList;

import practica1entrega.Contacto;

public class AnuncioIndividualizado extends Anuncio{
	
	ArrayList<Contacto> destinatarios;
	//Poner algo para rellenarlo con los usuarios que quieras. En la creacion del anuncio?

	public AnuncioIndividualizado(int id, String titulo, String cuerpo, Contacto usuario,
			ArrayList<Contacto> destinatarios) {
		super(id, titulo, cuerpo, usuario, destinatarios);
		// TODO Auto-generated constructor stub
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.destinatarios=destinatarios;
	}

}
