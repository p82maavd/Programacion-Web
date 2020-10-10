package practica1;

import java.util.ArrayList;
import java.util.Scanner;

import practica1.Contacto;
import practica1.Contacto.Intereses;

public class ConcreteFactory extends AbstractFactory {

	// Implementation of creation methods
	//Comprobar argumentos de los new, seguramente mal.
	
	@Override
	public AnuncioFlash createAnuncioFlash(Contacto e,ArrayList <Contacto> a) {
		Scanner sc=new Scanner(System.in);
		int id=0;
		String titulo=new String();
		titulo=sc.nextLine();
		String cuerpo=new String();
		cuerpo=sc.nextLine();
		
		AnuncioFlash product = new AnuncioFlash(id, titulo, cuerpo, null, null);
		return product;
	}

	@Override
	public AnuncioIndividualizado createAnuncioIndividualizado(Contacto e) {
		Scanner sc=new Scanner(System.in);
		int id=0;
		String titulo=new String();
		titulo=sc.nextLine();
		String cuerpo=new String();
		cuerpo=sc.nextLine();
		AnuncioIndividualizado product = new AnuncioIndividualizado(id, titulo, cuerpo, null, null);
		return product;
	}
	
	@Override
	public AnuncioTematico createAnuncioTematico(Contacto e) {
		Scanner sc=new Scanner(System.in);
		int id=0;
		String titulo=new String();
		titulo=sc.nextLine();
		String cuerpo=new String();
		cuerpo=sc.nextLine();
		
		//if al recorrer lista de contactos sus intereses coinciden con los intereses del anuncio add a vector destinatarios.
		
		AnuncioTematico product = new AnuncioTematico( id, titulo, cuerpo, e, null, null);
		//Lo de e.getIntereses esta mal para ver si compila.
		return product;
	}

	@Override
	public AnuncioGeneral createAnuncioGeneral(Contacto e,ArrayList <Contacto> a) {
		
		Scanner sc=new Scanner(System.in);
		int id=0;
		String titulo=new String();
		titulo=sc.nextLine();
		String cuerpo=new String();
		cuerpo=sc.nextLine();
		
		AnuncioGeneral product = new AnuncioGeneral(id, titulo, cuerpo, e, a);
		return product;
	}

}