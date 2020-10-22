package ejercicio1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactoDAO implements ContactoDAOInterface {

	
	private Connection con=null;
	
	private ArrayList<Contacto> listaContactos;
	private ControlDeErrores control=new ControlDeErrores();
	
	public ContactoDAO(Connection e) {
		this.con=e;
		listaContactos=new ArrayList<Contacto>();
	}
	
	public void cargarContactos() throws SQLException {
		
		Statement stmt=con.createStatement();
		ResultSet rs= stmt.executeQuery("SELECT email, nombre, apellidos, fechanacimiento FROM contactos");
		
		String email= new String();
		String nombre= new String();
		String apellidos= new String();
		Date date= new Date(0);
		try {
			
			while(rs.next()) {
				email = rs.getString(1);
				nombre=rs.getString(2);
				apellidos=rs.getString(3);
				date=rs.getDate(4);
				
				this.listaContactos.add(new Contacto(email,nombre,apellidos,date,null));
			}
			
			//if(stmt!= null) stmt.close();
		}catch(Exception e) {
			System.out.println("Error al cargar los contactos de la Base de Datos");
		}
		
	}
	
	@Override
	public void crearContacto() {
		
		Scanner sc = new Scanner(System.in);
        String nuevonombre;
        String nombreaux;

        System.out.print("Introduzca el nuevo nombre: ");
        nombreaux = sc.nextLine();
        while(!(control.esNombre(nombreaux))) {
			System.out.println("No se pueden introducir numeros en el nombre");
			System.out.print("Vuelva a introducir el nombre: ");
			nombreaux=sc.nextLine();
		}
        nuevonombre = nombreaux.substring(0, 1).toUpperCase() + nombreaux.substring(1).toLowerCase();

        
        String nuevoapellido;
        String apellidoaux;
        System.out.print("Introduzca el nuevo apellido: ");
        apellidoaux = sc.nextLine();
        while(!(control.esNombre(apellidoaux))) {
			System.out.println("No se pueden introducir numeros en el apellido");
			System.out.print("Vuelva a introducir el apellido: ");
			apellidoaux=sc.nextLine();
		}
        nuevoapellido = apellidoaux.substring(0, 1).toUpperCase() + apellidoaux.substring(1).toLowerCase();

        
		String nuevoemail=new String();
		
		System.out.print("Introduzca el nuevo email: ");
		nuevoemail = sc.nextLine();
			
		while(!(control.esEmail(nuevoemail))) {
			System.out.println("El email debe tener @");
			System.out.print("Vuelva a introducir el email: ");
			nuevoemail=sc.nextLine();
		}
			
		
		String nuevafecha="01/01/1970";
		System.out.print("Introduzca la nueva fecha de nacimiento(yyyy-mm-dd): ");
	
		Date dnuevafecha = new Date(0);	
		
		int conta=1;
		while(conta!=0) {
			conta=0;
			nuevafecha = sc.nextLine();
			while(!(control.esFecha(nuevafecha))) {
				System.out.println("Formato de la fecha (yyyy-mm-dd)");
				System.out.print("Vuelva a introducir la fecha: ");
				nuevafecha=sc.nextLine();
			}
			dnuevafecha=Date.valueOf(nuevafecha);
		}
		
		//ACTUALIZAR
		ArrayList<String> interesesaux=new ArrayList<String>();
		/*Integer neweleccion=0;
		int newinteres=0;
		Boolean condicion=true;
		
		int cont =1;
		while(condicion) {
			
			System.out.println("Seleccione un nuevo interes: ");
			
			if(claseintereses.getIntereses().size()==0) {
				System.out.println("No existen Intereses");
				break;
			}
			
			for (String myVar : claseintereses.getIntereses()) {
				System.out.println(cont+" "+myVar);
				cont++;
			}
			cont=1;
			
			newinteres= sc.nextInt();
			sc.nextLine();
			
		
			
			for (int i=1;i<=claseintereses.getIntereses().size();i++) {
				if(newinteres==i) {
					
					interesesaux.add(claseintereses.getIntereses().get(i-1));
				}
			}
			
			System.out.println("Desea añadir mas intereses: 1. Si 2. No");
			neweleccion=sc.nextInt();
			sc.nextLine();
			if(neweleccion!=1) {
				condicion=false;
			}
			
		
		}*/
		
		int status=0;
		try{
			
			PreparedStatement ps=con.prepareStatement("insert into contactos (email,nombre,apellidos,fechanacimiento) values (?,?,?,?)");
			
			ps.setString(1,nuevoemail);
			ps.setString(2,nuevonombre);
			ps.setDate(4, dnuevafecha);
			ps.setString(3,nuevoapellido);
			
			status = ps.executeUpdate();
			
		} catch(Exception es) { 
				es.printStackTrace();
				
		}
		
		if(status!=1) {
			System.out.println("No se ha podido añadir el contacto a la base de datos");
		}
		else {
			Contacto e=new Contacto(nuevoemail.toLowerCase(),nuevonombre,nuevoapellido,dnuevafecha,interesesaux);
			this.listaContactos.add(e);
		}

	}

	@Override
	public void borrarContacto(Contacto e) {
		if(e==null) {
			System.out.println("No existe un contacto con dichos atributos");
			return;
		}
		
		int status=0;
		try{
			
			PreparedStatement ps=con.prepareStatement("delete from contactos where email=?");
			ps.setString(1,e.getEmail());
			status=ps.executeUpdate();
		}catch(Exception es){System.out.println(es);}

		if(status!=1) {
			System.out.println("No se ha podido eliminar el contacto de la base de datos");
		}
		
		else {
			this.listaContactos.remove(e);
		}
	}
	
	@Override
	public void actualizarContacto(Contacto e) throws SQLException {
		
		if(e==null) {
			System.out.println("No existe contacto con dichos atributos");
			return;
		}
		System.out.println("Que quieres modificar: 1. Nombre 2. Apellidos 3. Email 4. Fecha Nacimiento 5. Intereses");
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca un número entero: ");
		Integer a = sc.nextInt();
		sc.nextLine();
		String sqlUpdate=new String();
		PreparedStatement ps=con.prepareStatement(sqlUpdate);
		ps.setString(2, e.getEmail());
		int status=0;
		
		if(a==1) {
			sqlUpdate = "UPDATE contactos " + "SET email = ? " + "WHERE email = ?";
			Boolean email=true;
			String nuevoemail=new String();
			
				
			System.out.print("Introduzca el nuevo email: ");
			nuevoemail = sc.nextLine();
				
			while(!(control.esEmail(nuevoemail))) {
				System.out.println("El email debe tener @");
				System.out.print("Vuelva a introducir el email: ");
				nuevoemail=sc.nextLine();
			}
				
			if(this.listaContactos.size()==0) {
				email=false;
				
			}
			int cont=0;
			for (Contacto myVar : this.listaContactos) {
				if(nuevoemail.equals(myVar.getEmail())) {
					cont++;
				}
					
			}
			if(cont>0) {
				System.out.println("El correo que quieres introducir ya esta asociado a otro usuario");
				return;
			}
			ps.setString(1, nuevoemail);
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			else {
				e.setEmail(nuevoemail);
			}
		}
		
		else if(a==2) {
			sqlUpdate = "UPDATE contactos " + "SET nombre = ? " + "WHERE email = ?";
			String nuevonombre;
	        String nombreaux;
	
	        System.out.print("Introduzca el nuevo nombre: ");
	        nombreaux = sc.nextLine();
	        while(!(control.esNombre(nombreaux))) {
				System.out.println("No se pueden introducir numeros en el nombre");
				System.out.print("Vuelva a introducir el nombre: ");
				nombreaux=sc.nextLine();
			}
	        nuevonombre = nombreaux.substring(0, 1).toUpperCase() + nombreaux.substring(1).toLowerCase();
	        ps.setString(1, nuevonombre);
	        status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			else {
				e.setNombre(nuevonombre);
			}
		}
		
		else if(a==3) {
			sqlUpdate = "UPDATE contactos " + "SET apellidos = ? " + "WHERE email = ?";
			String nuevoapellido;
		    String apellidoaux;
		    System.out.print("Introduzca el nuevo apellido: ");
		    apellidoaux = sc.nextLine();
		    while(!(control.esNombre(apellidoaux))) {
		    	System.out.println("No se pueden introducir numeros en el apellido");
				System.out.print("Vuelva a introducir el apellido: ");
				nuevoapellido=sc.nextLine();
			}
		    nuevoapellido = apellidoaux.substring(0, 1).toUpperCase() + apellidoaux.substring(1).toLowerCase();
		    ps.setString(1, nuevoapellido);
		    status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			else {
				e.setApellidos(nuevoapellido);
			}
		}
		
		
		
		else if(a==4) {
			sqlUpdate = "UPDATE contactos " + "SET fechanacimiento = ? " + "WHERE email = ?";
			String nuevafecha=new String();
			System.out.print("Introduzca la nueva fecha de nacimiento(dd/mm/yyyy): ");
			nuevafecha = sc.nextLine();
			 
			Date dnuevafecha = new Date(0);
			int cont=1;
			while(cont!=0) {
				cont=0;
				try {
					nuevafecha = sc.nextLine();
					while(!(control.esFecha(nuevafecha))) {
						System.out.println("Formato de la fecha (dd/mm/yyyy)");
						System.out.print("Vuelva a introducir la fecha: ");
						nuevafecha=sc.nextLine();
					}
					dnuevafecha = Date.valueOf(nuevafecha);
				} catch (IllegalArgumentException e1) {
					System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy hh:mm:ss): ");
					cont++;
				}
			}
			ps.setDate(1, dnuevafecha);
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			else {
				e.setFechanacimiento(dnuevafecha);
			}
		}
		//ACTUALIZAR
		/*
		else if(a==5) {
			ArrayList<String> intereses;
			intereses=e.getIntereses();
			
			Integer eleccion;
			System.out.println("¿Desea eliminar algun interes? 1.Si 2.No");
			eleccion = sc.nextInt();
			
			
			if(eleccion==1) {
				System.out.println("Actuales Intereses");
				for(int i=1; i<=intereses.size();i++) {
				
					System.out.println(i+intereses.get(i-1));
				}
				
				System.out.println("Que interes desea eliminar");
				
				
				Integer linea;
				linea = sc.nextInt();
				sc.nextLine();
				intereses.remove(linea-1);
				e.setIntereses(intereses);
			}
			else {
				
				//añadirIntereses(e);
			}
			
			
		}*/
		
	}
	@Override
	public Contacto buscarContacto()  {
		
		ArrayList<Contacto> aux=new ArrayList<Contacto>();
		
		System.out.println("Elige parametro de busqueda: ");
		System.out.println("1. Nombre y ");
		System.out.println("2. Apellidos");
		System.out.println("3. Email");
		System.out.println("4. Fecha de nacimiento");
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduzca un número entero: ");
		Integer a = sc.nextInt();
		sc.nextLine();
		Contacto buscado=null;
		Statement stmt=null;
		try {
			stmt= con.createStatement();
			
			Integer size=0;
			try {
				ResultSet rs= stmt.executeQuery("select count(email) FROM contactos");
				rs.next();
				size=rs.getInt("count(email)");
			
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			if(size==0) {
				System.out.println("No hay ningun usuario en la base de datos");
				return null;
			}
			
			
			int status=0;
			Integer cont=0;
			String cad=new String();
			
			
			/*
			if(a==1) {
				//Cuando pones * en el select te cambia el orden por la cara.
				PreparedStatement ps=con.prepareStatement("select email, nombre, apellidos, fechanacimiento from contactos where nombre = ?");
				
				String nombreaux=new String();
				try{
					System.out.print("Introduce el nombre a buscar: ");
					nombreaux = sc.nextLine();
					nombreaux = nombreaux.substring(0, 1).toUpperCase() + nombreaux.substring(1).toLowerCase();
					
					ps.setString(1,nombreaux);
					ResultSet rs= ps.executeQuery();
					cont =1;
					System.out.println("A ver si pilla algo");
					while(rs.next()) {
						System.out.println("Pilla alguno");
						cad=cont.toString()+". "+ imprimirContacto(rs.getString(1),rs.getString(2),rs.getString(3).toString(), rs.getDate(4).toString());
						System.out.println(cad);
						cont++;
					}
					
					if(cont==1) {
						System.out.println("No existe ningun usuario con dicho nombre");
						return null;
					}
					int seleccion=0;
					if(cont==2) {
						seleccion=1;
					}
					else {
						System.out.print("Que usuario es el que busca: ");
						seleccion=sc.nextInt();
						sc.nextLine();
					}
					rs.beforeFirst();
					cont=1;
					while(rs.next()) {
						System.out.println(cont+" "+seleccion);
						//Comprobar que selecciona el que tu quieres. Creo que si.
						if(cont==seleccion) {
							return rs.getString(1);
						}
						
						cont++;
					}
					
					return null;
					
				} catch(Exception e) { 
						e.printStackTrace();
						
				}
			
				
			}*/
			
			if(a==1) {
				
				String nombreaux=new String();
				
				System.out.print("Introduce el nombre a buscar: ");
				nombreaux = sc.nextLine();
				nombreaux = nombreaux.substring(0, 1).toUpperCase() + nombreaux.substring(1).toLowerCase();
				
				for(Contacto e: listaContactos) {
					if(e.getNombre().equals(nombreaux)) {
						return e;
					}
				}
				System.out.println("No hay ningun usuario con dicho nombre");
				return null;
			}
		
		
			/*	
		
			else if(a==2) {
				String emailaux;
				int n = 0;
				System.out.print("Indique el email a buscar: ");
				emailaux = sc.nextLine();
				for(int i=0; i<this.listaContactos.size();i++) {
				 if(this.listaContactos.get(i).getEmail().equals(emailaux)) {
					 n = n + 1;
					 Contacto e=this.listaContactos.get(i);
					 return e;
				 }
				}
				if(n==0) {
					return null;
				}
	
			}
			
			
			else if(a==3) {
				Integer cont=0;
				
				//Imprime todos los intereses
				
				for(String s: claseintereses.getIntereses()) {
					System.out.println(cont.toString()+". "+s);
					cont++;
				}
				
				
				System.out.print("Indique que interes buscar: ");
			    int seleccion = sc.nextInt();
			    sc.nextLine();
			    String interesaux=new String();
				
				for(int i=0; i<claseintereses.getIntereses().size();i++) {
					
					if(seleccion==i) {
						interesaux=claseintereses.getIntereses().get(i);		
					}
					
				}
				
				//Busca los contactos que tengan el interes seleccionado arriba
				
				for(Contacto d: this.listaContactos) {
					
					for(int i=0;i<d.getIntereses().size();i++) {
						
						if(d.getIntereses().get(i).equals(interesaux)) {	
							aux.add(d);
							break;
						}
					}
					
				}
				
				if(aux.size()==0) {
					
					return buscado;
				}
				
				if(aux.size()==1) {
					
					return aux.get(0);
				}
				
				//Imprime todos los contactos con dicho interes.
				
				for(Integer i=0;i<aux.size();i++) {
					System.out.println(i.toString()+"Nombre: "+aux.get(i).getNombre()+" Email: "+ aux.get(i).getEmail());
					
				}
				
				System.out.println("Selecciona el contacto buscado");
					
				int seleccion2=sc.nextInt();
				sc.nextLine();
				
				for(int i=0;i<aux.size();i++) {
					
					if(i==seleccion2) {
						System.out.println("Contacto Seleccionado");
						buscado=aux.get(i);		
						break;
					}
				}
				return buscado;
	
			}
			
			
			else if(a==4) {
				String fechaaux=new String();
				int n = 0;
				System.out.print("Indique la fecha de nacimiento(dd/mm/yyyy) a buscar: ");
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date dnuevafecha = new Date(n);
				int cont=1;
				while(cont!=0) {
					cont=0;
					try {
						fechaaux = sc.nextLine();
						dnuevafecha = (Date) formatter.parse(fechaaux);
					} catch (ParseException e1) {
						cont++;
						System.out.print("Fecha mal introducida. Vuelva a introducirla(dd/MM/yyyy): ");
					}
				}
				//Busca los contactos que tengan el interes seleccionado arriba
				
				for(Contacto d: this.listaContactos) {
					if(d.getFechanacimiento().equals(dnuevafecha)) {
						aux.add(d);			
					}
				}
					
				
				if(aux.size()==0) {
					return null;
				
				}
				
				if(aux.size()==1) {
					return aux.get(0);
				}
				
				//Imprime todos los contactos con dicha fecha.
				
				for(Integer i=0;i<aux.size();i++) {
					System.out.println(i.toString()+"Nombre: "+aux.get(i).getNombre()+" Email: "+ aux.get(i).getEmail());
				}
				
				System.out.println("Selecciona el contacto buscado");
					
				
				int seleccion2=sc.nextInt();
				sc.nextLine();
				
				
				for(int i=0;i<aux.size();i++) {
					
					if(i==seleccion2) {
						System.out.println("Contacto Seleccionado");
						buscado=aux.get(i);
						
						break;
					}
				}
				
				return buscado;
	
			}*/
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}

		
		//sc.close();
		
		return null;
	}
	
	
	/*public String imprimirContacto(String a, String b, String string, String d) {
		//Mirar algo para formatear fecha sql.date.
		String cadena= "Nombre: "+a+ " Apellidos: "+b+" Fecha de Nacimiento: "+string+" Email: "+d;
		return cadena;
	}*/
	
	public void mostrarContactos() {
		for(Contacto e: listaContactos) {
			System.out.println(e.getEmail()+" "+e.getNombre());
		}
	}

	
	

}
