package ejercicio1;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Declaracion del main del Ejercicio 2.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 * 
 */
public class mainEjercicio2 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		
		Configuracion config= Configuracion.getInstance(args[0]);
		DAOFactory factory=DAOFactory.getInstance();
		ContactoDAO contactos= factory.getContactoDAO();
		InteresDAO intereses= factory.getInteresDAO();
		AnuncioDAO anuncios= factory.getAnuncioDAO();
		TablonAnuncios tablon=TablonAnuncios.getInstance();
		
		int a;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		boolean condicion=true;
		
		boolean condicion5=true;
		
		// Menu del Gestor de Contactos
		while(condicion) {
			System.out.println("");
			System.out.println("Que quieres gestionar: ");
			System.out.println("1. Contactos del Sistema");
			System.out.println("2. Anuncios");
			System.out.println("3. Tablon Personal");
			System.out.println("4. Configuracion");
			System.out.println("5. Salir");
			
			
			condicion5=true;
			
			try {
				a=sc.nextInt();
			
				if(a==1) {
					//Comprobar
					contactos.main();
				} //Fin if
			    
				// Menu del gestor de Anuncios
				else if(a==2) {
					anuncios.main();
				}
				
				else if(a==3) {
					
					tablon.main();
					
				}
				
				else if(a==4) {
					
					while(condicion5) {
						
						System.out.println("Que quieres realizar: ");
						System.out.println("1. Añadir Interes");
						System.out.println("2. Salir");
						
						
						try {
							a=sc.nextInt();
							sc.nextLine();
						
							if(a==1) {
								intereses.crearInteres();
								
							}
							
						
							else{
								condicion5=false;
							}
						
						} catch (NoSuchElementException e) {
			                System.out.println("Debes insertar un número");
			             
			                a=sc.nextInt();

			            }
					}
				}
				
			
				else{
					condicion=false;
				}
			
			} catch (NoSuchElementException e) {
                System.out.println("Debes insertar un número");
             
                sc.nextLine();

            }
		
		}
		
	}

}
