import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class maingestorcontactos {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		GestorContactos gestor = GestorContactos.getInstance();
	
		Scanner sc = new Scanner(System.in);
		Boolean condicion=true;
		try {
		gestor.cargarDatos();
		}catch(FileNotFoundException e) {
			ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("fich.dat"));
	        file.close();
		}
		int a= 0;
		
		while(condicion) {
			System.out.println("Que quieres realizar: ");
			System.out.println("1. Añadir Contacto");
			System.out.println("2. Mostrar Contactos");
			System.out.println("3. Eliminar Contacto");
			System.out.println("4. Actualizar Contacto");
			System.out.println("4. Salir");
			
			try {
				a=sc.nextInt();
			
				if(a==1) {
					gestor.darAlta();
					System.out.println("Contacto dado de alta");
				}
			
				else if(a==2) {
					gestor.imprimirDatos();
				}
		
				else if(a==3) {
					gestor.darBaja(0);
				}
		
				else if(a==4) {
					gestor.actualizarContacto();
				}
			
				else{
					condicion=false;
				}
			
			}catch (NoSuchElementException e) {
        System.out.println("Debes insertar un número");    
        a=sc.nextInt();
        }
		
		}
		
		sc.close();

	}


}