package practica1;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import practica1.*;

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
		System.out.println("Introduce tu correo electronico: ");
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
	
	public Anuncio crearAnuncio(ArrayList<Contacto> a, int id) {
		
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
			// Revisar pero creo que este esta ya.
			Anuncio anuncio = anuncioFactory.createAnuncioGeneral(getUsuario(),a,id);
			
			return anuncio;
		}
		
		else if(as==2) {
			
			Anuncio anuncio = anuncioFactory.createAnuncioFlash(getUsuario(),a,id);
			
			return anuncio;
		}
		
		else if(as==3) {
			
			ArrayList<Contacto> destinatarios=new ArrayList<Contacto>();
			boolean condicion=true;
			String linea=new String();
			
			while(condicion) {
				
				try {
					
					System.out.print("Introduzca el Email del destinatario: ");
				
					linea=sl.nextLine();
					
					for(Contacto d: a) {
						if(d.getEmail().equals(linea)) {
							destinatarios.add(d);
							//Comprobar este break;
							break;
						}
					}
					System.out.println("Quieres añadir otro destinatario: 1. Si 2.No");
					as=sc.nextInt();
					
					if(as!=1) {
						condicion=false;
					}
					
				} catch (NoSuchElementException e) {
					//Comprobar si coge el del nextLine o del nextInt
	                System.out.println("Debes insertar un numero");
		             
	               as=sc.nextInt();

	            }
			}
			
			Anuncio anuncio = anuncioFactory.createAnuncioIndividualizado(getUsuario(),destinatarios,id);
			
			return anuncio;
		}
		
		else if(as==4) {
		
			Intereses gestorIntereses= new Intereses();
			ArrayList<String> interesesaux=new ArrayList<String>();
			Integer neweleccion=0;
			String newinteres=new String();
			Scanner alt = new Scanner(System.in);
			Boolean condicion=true;
			int cont =1;
			while(condicion) {
				
				System.out.println("Seleccione un interes para el anuncio: ");
				
				for (String myVar : gestorIntereses.getIntereses()) {
					System.out.println(cont+" "+myVar);
					cont++;
				}
				cont=1;
				
				newinteres= alt.nextLine();
				
				int foo;
				try {
				   foo = Integer.parseInt(newinteres);
				}
				catch (NumberFormatException e)
				{
				   foo = 0;
				}
				
				for (int i=1;i<=gestorIntereses.getIntereses().size();i++) {
					if(foo==i) {
						
						interesesaux.add(gestorIntereses.getIntereses().get(i-1));
					}
				}
				
				System.out.println("Desea añadir mas intereses: 1. Si 2. No");
				neweleccion=alt.nextInt();
				alt.nextLine();
				if(neweleccion!=1) {
					condicion=false;
				}
			}
			Anuncio anuncio = anuncioFactory.createAnuncioTematico(getUsuario(),interesesaux,a,id);
			
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
			//System.out.println(a.getDestinatarios().get(0).getNombre());
			//System.out.println(e.getNombre());
			
			//System.out.print((a.getDestinatarios().get(cont).getEmail().equals(e.getEmail())));
			
			System.out.println("");
			if((a.getDestinatarios().get(cont).getEmail().equals(e.getEmail())) & (a.getEstado().getId()==3)){
				
				System.out.println(a.tooString());
			}
		}
	}
	
	

}
