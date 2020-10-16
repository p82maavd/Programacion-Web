package Ejercicio2;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class mainEjercicio2 {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Configuracion config= Configuracion.getInstance(args[0]);
		GestorAnuncios gestor = GestorAnuncios.getInstance();
		GestorContactos gestorC = GestorContactos.getInstance();
		
		TablonAnuncios tablon= TablonAnuncios.getInstance();
		Intereses gestorIntereses= Intereses.getInstance();
		
	
		
		int a;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		boolean condicion=true;
		boolean condicion2=true;
		boolean condicion3=true;
		boolean condicion4=true;
		boolean condicion5=true;
		gestorC.cargarDatos();
		
		while(condicion) {
			System.out.println("");
			System.out.println("Que quieres gestionar: ");
			System.out.println("1. Contactos del Sistema");
			System.out.println("2. Anuncios");
			System.out.println("3. Tablon Personal");
			System.out.println("4. Configuracion");
			System.out.println("5. Salir");
			condicion2=true;
			condicion3=true;
			condicion4=true;
			condicion5=true;
			
			try {
				a=sc.nextInt();
			
				if(a==1) {
					while(condicion2) {
						System.out.println("Que quieres realizar: ");
						System.out.println("1. Añadir Contacto");
						System.out.println("2. Mostrar Contactos");
						System.out.println("3. Eliminar Contacto");
						System.out.println("4. Actualizar Contacto");
						System.out.println("5. Consultar Contacto");
						System.out.println("6. Salir");
						
						try {
							a=sc.nextInt();
						
							if(a==1) {
								gestorC.darAlta();
								System.out.println("Contacto dado de alta");
								System.out.println("");
							}
						
							else if(a==2) {
								gestorC.imprimirDatos();
							}
					
							else if(a==3) {
								gestorC.darBaja(gestorC.buscarContacto());
						
							}
							
							else if(a==4) {
								gestorC.actualizarContacto(gestorC.buscarContacto());
								
							}
							
							
							else if(a==5) {
								gestorC.consultarContacto(gestorC.buscarContacto());
							}
					
					
							else if(a==6) {
							
								condicion2=false;
							}
						
							else{
								condicion2=false;
							}
						
						} catch (NoSuchElementException e) {
			                System.out.println("Debes insertar un número");
			             
			                a=sc.nextInt();

			            }
					
					} //Fin While
				} //Fin if
			    
				
				else if(a==2) {
					
					while(condicion3) {
						System.out.println("Que quieres realizar: ");
						System.out.println("1. Editar Anuncio");
						System.out.println("2. Publicar Anuncio");
						System.out.println("3. Archivar Anuncio");
						System.out.println("4. Buscar Anuncio");
						System.out.println("5. Mostrar todos los anuncios");
						System.out.println("6. Salir");
						
						try {
							a=sc.nextInt();
							if(a==1) {
								try {
								gestor.modificarAnuncio(gestor.buscarAnuncio());
								}catch(NullPointerException e) {
									System.out.println("No existen anuncios con dichos parametros");
								}
							}
						
							else if(a==2) {
								try {
								gestor.publicarAnuncio();
								}catch(NullPointerException e) {
									System.out.println("Anuncio seleccionado no valido");
								}
							}
					
							else if(a==3) {
								try {
								gestor.archivarAnuncio();
								}catch(NullPointerException e) {
									System.out.println("Anuncio seleccionado no valido");
								}
						
							}
							
							else if(a==4) {
								try {
								Anuncio an=gestor.buscarAnuncio();
								
								String string=new String();
								string=an.toString();
								
								if(string.equals("class practica1.AnuncioTematico")) {
									string=((AnuncioTematico)an).tooString();
									System.out.println(string);
								}
								else if(string.equals("class practica1.AnuncioFlash")) {
									string=((AnuncioFlash)an).tooString();
									System.out.println(string);
								}
								else if(string.equals("class practica1.AnuncioIndividualizado")) {
									string=((AnuncioIndividualizado)an).tooString();
									System.out.println(string);
								}
								else if(string.equals("class practica1.AnuncioGeneral")) {
									string=((AnuncioGeneral)an).tooString();
									System.out.println(string);
								}
								}catch(NullPointerException e) {
									System.out.println("No se ha encontrado ningun anuncio con dichos parametros");
									System.out.println("");
								}
								
								
							}
							
							else if(a==5) {
								gestor.mostrarAnuncios();
							}
							
							else{
								condicion3=false;
							}
						
						} catch (NoSuchElementException e) {
			                System.out.println("Debes insertar un número");
			             
			                a=sc.nextInt();

			            }
					
					}
					
				}
				
		
		
				else if(a==3) {
					
					boolean usuario=tablon.identificarUsuario(gestorC.getContactos());
					while(condicion4) {
						
						if(usuario) {
						
							System.out.println("Que quieres realizar: ");
							System.out.println("1. Inscribirse como usuario a temas de interes");
							System.out.println("2. Crear Anuncio");
							System.out.println("3. Mostrar mi tablon");
							System.out.println("4. Salir");
							
							try {
								a=sc.nextInt();
							
								if(a==1) {
									
									tablon.inscribirseInteres();
									
								}
							
								else if(a==2) {
									
									gestor.addNewAnuncio(tablon.crearAnuncio(gestor.getListaAnuncios().size()));
									System.out.println("Anuncio creado en estado editado");
									
								}
						
								else if(a==3) {
									
									tablon.mostrarAnuncios(tablon.getUsuario(), gestor.getListaAnuncios());
							
								}
								
							
								else{
									condicion4=false;
								}
							
							} catch (NoSuchElementException e) {
				                System.out.println("Debes insertar un número");
				             
				                a=sc.nextInt();
	
				            }
						}
						
						else {
							System.out.println("El usuario no existe");
							condicion4=false;
						}
					
					}
					
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
								String linea=new String();
								System.out.print("Introduce un nuevo interes: ");
								linea=sc.nextLine();
								gestorIntereses.addInteres(linea);
								
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
             
                a=sc.nextInt();

            }
		
		}
		
	}

}
