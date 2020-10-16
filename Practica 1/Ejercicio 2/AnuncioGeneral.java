package Ejercicio2;

import java.util.ArrayList;

public class AnuncioGeneral extends Anuncio{

	
	private static final long serialVersionUID = 1L;

	public AnuncioGeneral(int id, String titulo, String cuerpo, Contacto usuario, ArrayList<Contacto> destinatarios, Estados estado) {
		super(id, titulo, cuerpo, usuario, destinatarios, estado);
		
		this.id=id;
		this.titulo=titulo;
		this.cuerpo=cuerpo;
		this.usuario=usuario;
		this.destinatarios=destinatarios;
		this.estado=estado;
		
		
		
	}
	
	public String tooString() {
		
		String anuncioInfo = "Id: "+this.id + " Titulo: " + this.titulo + " Cuerpo: " + this.cuerpo + " Propietario: " + this.usuario.getNombre()+ " Estado: "+ this.getEstado().getEstados()+"\n"+ "Destinatarios: "; // Another way to concat strings
		for(int i=0; i<this.getDestinatarios().size();i++) {
			anuncioInfo=anuncioInfo + "\n"+this.getDestinatarios().get(i).getEmail();
		}
		return anuncioInfo;
	}
	
	

}
