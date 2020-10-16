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
	
	public String tooString() {
		
		String anuncioInfo = "Id: "+this.id + " Titulo: " + this.titulo + " Cuerpo: " + this.cuerpo + " Propietario: " + this.usuario.getNombre()+  " Estado: " + this.getEstado().getEstados() +"\n"+ "Destinatarios: "; // Another way to concat strings
		for(int i=0; i<this.getDestinatarios().size();i++) {
			anuncioInfo=anuncioInfo +"\n" +this.getDestinatarios().get(i).getEmail();
		}
		anuncioInfo=anuncioInfo+"\n"+"Intereses: ";
		for(int i=0; i<this.getIntereses().size();i++) {
			anuncioInfo=anuncioInfo +"\n" +this.getIntereses().get(i);
		}
		
		return anuncioInfo;
	}

}
