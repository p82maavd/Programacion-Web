package practica1;

import java.util.ArrayList;
import java.util.Scanner;

import practica1.Anuncio.Estados;
import practica1.Contacto;
import practica1.Intereses;

public class ConcreteFactory extends AbstractFactory {

	// Implementation of creation methods
	//Comprobar argumentos de los new, sin terminar.
	//VER COMO CONFIGURAR ID, posiblemente cuando guarde o cargue datos.
	
	@Override
	public AnuncioFlash createAnuncioFlash(Contacto e,ArrayList <Contacto> a, int ids) {
		Scanner sc=new Scanner(System.in);
		int id=ids;
		String titulo=new String();
		System.out.print("Titulo del Anuncio: ");
		titulo=sc.nextLine();
		String cuerpo=new String();
		System.out.print("Cuerpo del Anuncio: ");
		cuerpo=sc.nextLine();
		Estados estado=Estados.Editado;
		
		AnuncioFlash product = new AnuncioFlash(id, titulo, cuerpo, e, a, estado);
		return product;
	}

	@Override
	public AnuncioIndividualizado createAnuncioIndividualizado(Contacto e, ArrayList<Contacto> destinatarios,int ids) {
		Scanner sc=new Scanner(System.in);
		int id=ids;
		String titulo=new String();
		System.out.print("Titulo del Anuncio: ");
		titulo=sc.nextLine();
		String cuerpo=new String();
		System.out.print("Cuerpo del Anuncio: ");
		cuerpo=sc.nextLine();
		Estados estado=Estados.Editado;
		AnuncioIndividualizado product = new AnuncioIndividualizado(id, titulo, cuerpo, e, destinatarios,estado);
		return product;
	}
	
	@Override
	public AnuncioTematico createAnuncioTematico(Contacto e, ArrayList <String> intereses, ArrayList<Contacto> a,int ids) {
		Scanner sc=new Scanner(System.in);
		int id=ids;
		String titulo=new String();
		System.out.println("Introduzca el titulo del anuncio: ");
		titulo=sc.nextLine();
		String cuerpo=new String();
		System.out.println("Introduzca el cuerpo del anuncio: ");
		cuerpo=sc.nextLine();
		ArrayList<Contacto> destinatarios=new ArrayList <Contacto>();
		Estados estado=Estados.Editado;
		
		for(Contacto d: a) {
			
			for(String s: d.getIntereses()) {
				
				if(intereses.contains(s)) {
					destinatarios.add(d);
					//Comprobar como funciona el break este.
					break;
				}
				
			}
			
		}
		
		AnuncioTematico product = new AnuncioTematico( id, titulo, cuerpo, e, a, intereses, estado);
		
		return product;
	}

	@Override
	public AnuncioGeneral createAnuncioGeneral(Contacto e,ArrayList <Contacto> a,int ids) {
		
		Scanner sc=new Scanner(System.in);
		int id=ids;
		String titulo=new String();
		System.out.print("Titulo del Anuncio: ");
		titulo=sc.nextLine();
		String cuerpo=new String();
		System.out.print("Cuerpo del Anuncio: ");
		cuerpo=sc.nextLine();
		Estados estado=Estados.Editado;
		
		AnuncioGeneral product = new AnuncioGeneral(id, titulo, cuerpo, e, a, estado);
		return product;
	}

}