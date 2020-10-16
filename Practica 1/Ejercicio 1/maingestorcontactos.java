package Ejercicio1;

/**
 * 
 * @author Damian Martinez
 * @author Daniel Ortega
 */

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class maingestorcontactos {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		Configuracion config=Configuracion.getInstance(args[0]);
		GestorContactos gestor = GestorContactos.getInstance();
		
	
		Scanner sc = new Scanner(System.in);
		Boolean condicion=true;
	
		int a= 0;
		
		while(condicion) {
			System.out.println("");
			System.out.println("Que quieres realizar: ");
			System.out.println("1. Añadir Contacto");
			System.out.println("2. Mostrar Contactos");
			System.out.println("3. Eliminar Contacto");
			System.out.println("4. Actualizar Contacto");
			System.out.println("5. Consultar Contacto");
			System.out.println("6.Salir");
			
			try {
				a=sc.nextInt();
				sc.nextLine();
			
				if(a==1) {
					gestor.darAlta();
					
				}
			
				else if(a==2) {
					gestor.imprimirDatos();
				}
		
				else if(a==3) {
					try {
					gestor.darBaja(gestor.buscarContacto());
					}catch(NullPointerException e) {
						
					}
			
				}
				
				else if(a==4) {
					try {
					gestor.actualizarContacto(gestor.buscarContacto());
					}catch(NullPointerException e) {
						
					}
					
				}
				
				
				else if(a==5) {
					try {
					gestor.consultarContacto(gestor.buscarContacto());
					}catch(NullPointerException e) {
						
					}
				}
		
		
				else if(a==6) {
				
					condicion=false;
				}
			
				else{
					condicion=false;
				}
			
			} catch (NoSuchElementException e) {
                System.out.println("Debes insertar un número");
                sc.nextLine();

            }
		
		}
		
		sc.close();

	}

}
