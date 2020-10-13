package practica1;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;



public class TablonAnuncios {
	
	private Contacto usuario;
	
	public Contacto getUsuario() {
		return usuario;
	}

	public void setUsuario(Contacto e) {
		this.usuario = e;
	}

	private static TablonAnuncios instance =null;
	
	
	public static TablonAnuncios getInstance() {
		
		if(instance==null) {
			instance=new TablonAnuncios();
		}
		return instance;
	}
	
	public boolean identificarUsuario(ArrayList<Contacto> usuarios) {
		//Con correo electronico ya que es unico
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduce tu correo electronico: ");
		String correo= new String();
		correo=sc.nextLine();
		for(Contacto a: usuarios) {
			if (a.getEmail().equals(correo)) {
				
				System.out.println("Identificado Correctamente");
				setUsuario(a);
				return true;
			}
		}
		return false;
	}
	
	public void inscribirseInteres() {
	}
	
	public Anuncio crearAnuncio(ArrayList<Contacto> a, int id,ArrayList<String> intereses) {
		
		//añadir en el main cuando se le llame un try/catch de nullPointerException de cuando el else.
		ConcreteFactory anuncioFactory = new ConcreteFactory();
		Scanner sc=new Scanner(System.in);
		Scanner sl=new Scanner(System.in);
		
		//REVISAR ESTO.
		
		System.out.println("Que tipo de anuncio quieres crear: ");
		System.out.println("1. Anuncio General");
		System.out.println("2. Anuncio Flash");
		System.out.println("3. Anuncio Individualizado");
		System.out.println("4. Anuncio Tematico");
		int as=sc.nextInt();
		
		
		if(as==1) {
			
			Anuncio anuncio = anuncioFactory.createAnuncioGeneral(getUsuario(),a,id);
			
			return anuncio;
		}
		
		else if(as==2) {
			
			Anuncio anuncio = anuncioFactory.createAnuncioFlash(getUsuario(),a,id);
			
			return anuncio;
		}
		
		else if(as==3) {
			
			Anuncio anuncio = anuncioFactory.createAnuncioIndividualizado(getUsuario(),a,id);
			
			return anuncio;
		}
		
		else if(as==4) {
		
			Anuncio anuncio = anuncioFactory.createAnuncioTematico(getUsuario(),intereses,a,id);
			
			return anuncio;
		}
		
		else {
			
			System.out.println("Opcion no valida");
			return null;
		}
		
	}
	
	public void mostrarAnuncios(Contacto e, ArrayList<Anuncio> anuncios) {
		System.out.println("Numero total de anuncios: " + anuncios.size());
		int cont=0;
		for(Anuncio a: anuncios) {
			
			System.out.println("");
			if((a.getDestinatarios().get(cont).getEmail().equals(e.getEmail())) & (a.getEstado().getId()==3)){
				
				System.out.println(a.tooString());
			}
		}
	}
	
}
