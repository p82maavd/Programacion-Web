package practica1;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import practica1.Anuncio.Estados;



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
	public AnuncioIndividualizado createAnuncioIndividualizado(Contacto e, ArrayList<Contacto> a,int ids) {
		Scanner sc=new Scanner(System.in);
		Scanner sl=new Scanner(System.in);
		int as=0;
		int id=ids;
		String titulo=new String();
		System.out.print("Titulo del Anuncio: ");
		titulo=sc.nextLine();
		String cuerpo=new String();
		System.out.print("Cuerpo del Anuncio: ");
		cuerpo=sc.nextLine();
		Estados estado=Estados.Editado;
		
		ArrayList<Contacto> destinatarios=new ArrayList<Contacto>();
		boolean condicion=true;
		String linea=new String();
		
		while(condicion) {
			
			try {
				
				System.out.print("Introduzca el Email del destinatario: ");
			
				linea=sc.nextLine();
				
				for(Contacto d: a) {
					if(d.getEmail().equals(linea)) {
						destinatarios.add(d);
						//Comprobar este break;
						break;
					}
				}
				System.out.println("Quieres añadir otro destinatario: 1. Si 2.No");
				as=sl.nextInt();
				
				if(as!=1) {
					condicion=false;
				}
				
			} catch (NoSuchElementException w) {
				//Comprobar si coge el del nextLine o del nextInt
                System.out.println("Debes insertar un numero");
	             
               as=sl.nextInt();

            }
		}
		
		AnuncioIndividualizado product = new AnuncioIndividualizado(id, titulo, cuerpo, e, destinatarios,estado);
		return product;
	}
	
	@Override
	public AnuncioTematico createAnuncioTematico(Contacto e, ArrayList <String> intereses, ArrayList<Contacto> a,int ids) {
		
		ArrayList<String> interesesaux=new ArrayList<String>();
		Integer neweleccion=0;
		Integer newinteres;
		Scanner sc = new Scanner(System.in);
		Scanner sl = new Scanner(System.in);
		Boolean condicion=true;
		int cont =1;
		while(condicion) {
			
			System.out.println("Seleccione un interes para el anuncio: ");
			
			for (String myVar : intereses) {
				System.out.println(cont+" "+myVar);
				cont++;
			}
			cont=1;
			
			newinteres= sl.nextInt();
			
			for (int i=1;i<=intereses.size();i++) {
				if(newinteres==i) {
					
					interesesaux.add(intereses.get(i-1));
				}
			}
			
			System.out.println("Desea añadir mas intereses: 1. Si 2. No");
			neweleccion=sl.nextInt();
			
			if(neweleccion!=1) {
				condicion=false;
			}
		}
		
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
		
		AnuncioTematico product = new AnuncioTematico( id, titulo, cuerpo, e, destinatarios, interesesaux, estado);
		
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