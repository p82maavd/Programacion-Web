package ejercicio1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Date;
import java.util.Scanner;
import java.util.NoSuchElementException;


/**
 * 
 * @author Damian Martinez
 * @author Daniel Ortega
 * Declaracion de la clase GestorAnuncios
 *
 */

public class AnuncioDAO  {
	
	private Connection con=null;
	private static AnuncioDAO instance =null;
	private DAOFactory factory=DAOFactory.getInstance();
	private InteresDAO interesesDAO= factory.getInteresDAO();
	private ContactoDAO contactosDAO = factory.getContactoDAO();
	Configuracion config;
	

	/**
	 * Este método se encarga de crear una instancia en el caso de que no haya una ya creada. Patron de diseño Singleton
	 * @return Instancia única de GestorAnuncios.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	*/
	
	private AnuncioDAO(Connection e) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		this.con=e;
		config= Configuracion.getInstance(null);
	}
	public static AnuncioDAO getInstance(Connection e) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		
		if(instance==null) {
			instance=new AnuncioDAO(e);
		}
		return instance;
	}
	public ArrayList<Anuncio> getAnuncios() throws SQLException{
		Statement stmt=con.createStatement();
        ResultSet rs= stmt.executeQuery("select id, titulo, cuerpo, idautor, estado, fechapublicacion, fechainicio, fechafinal, tipo  from anuncios");
        ArrayList<Anuncio> anuncios =new ArrayList<Anuncio>();
        ArrayList<Interes> interesesaux = new ArrayList<Interes>();
        
        int id= 0;
        String titulo= new String();
        String cuerpo= new String();
        String idautor= new String();
        int estadoid = 0;
        Date fechapublicacion= new Date(0);
        Date fechainicio= new Date(0);
        Date fechafinal= new Date(0);
        String tipo= new String();
        
        AnuncioTematico t= null;
        AnuncioFlash f = null;
        AnuncioIndividualizado i = null;
        AnuncioGeneral g = null;
        Contacto a = null;

        try {

            while(rs.next()) {
            	tipo = rs.getString(9);
            	if(tipo.equals("class ejercicio1.AnuncioTematico")) {
            		
            		id = rs.getInt(1);
                    titulo = rs.getString(2);
                    cuerpo = rs.getString(3);
                    idautor = rs.getString(4);
                    estadoid = rs.getInt(5);
                    fechapublicacion = rs.getDate(6);
                    tipo = rs.getString(9);

                    Anuncio.Estados estado= null;
                    for (Anuncio.Estados p : Anuncio.Estados.values()) {
                    	if(p.getId()==estadoid) {
                    		estado = p;
                    	}
                    }
                    interesesaux = interesesDAO.getInteresesAnuncio(id);
                    a = contactosDAO.buscarContactoEmail(idautor); 
                    t = new AnuncioTematico(id,titulo,cuerpo,a,interesesaux, fechapublicacion, estado);
                    anuncios.add(t);
                    
    			}
    			else if(tipo.equals("class ejercicio1.AnuncioFlash")) {
    				
    				id = rs.getInt(1);
                    titulo = rs.getString(2);
                    cuerpo = rs.getString(3);
                    idautor = rs.getString(4);
                    estadoid = rs.getInt(5);
                    fechapublicacion = rs.getDate(6);
                    fechainicio = rs.getDate(7);
                    fechafinal = rs.getDate(8);
                    tipo = rs.getString(9);
                    

                    Anuncio.Estados estado= null;
                    for (Anuncio.Estados p : Anuncio.Estados.values()) {
                    	if(p.getId()==estadoid) {
                    		estado = p;
                    	}
                    }
                    a = contactosDAO.buscarContactoEmail(idautor); 
                    f = new AnuncioFlash(id, titulo,cuerpo,a,estado,fechapublicacion,fechainicio,fechafinal);
                    anuncios.add(f);
    			
    			}
    			else if(tipo.equals("class ejercicio1.AnuncioIndividualizado")) {
    				
    				id = rs.getInt(1);
                    titulo = rs.getString(2);
                    cuerpo = rs.getString(3);
                    idautor = rs.getString(4);
                    estadoid = rs.getInt(5);
                    fechapublicacion = rs.getDate(6);
                    fechainicio = rs.getDate(7);
                    fechafinal = rs.getDate(8);
                    tipo = rs.getString(9);
                    

                    Anuncio.Estados estado= null;
                    for (Anuncio.Estados p : Anuncio.Estados.values()) {
                    	if(p.getId()==estadoid) {
                    		estado = p;
                    	}
                    }
                    a = contactosDAO.buscarContactoEmail(idautor); 
                    i = new AnuncioIndividualizado(id,titulo,cuerpo,a,fechapublicacion,estado);
                    anuncios.add(i);
    			
    			}
    			else if(tipo.equals("class ejercicio1.AnuncioGeneral")) {
    				
    				id = rs.getInt(1);
                    titulo = rs.getString(2);
                    cuerpo = rs.getString(3);
                    idautor = rs.getString(4);
                    estadoid = rs.getInt(5);
                    fechapublicacion = rs.getDate(6);
                    fechainicio = rs.getDate(7);
                    fechafinal = rs.getDate(8);
                    tipo = rs.getString(9);
                    

                    Anuncio.Estados estado= null;
                    for (Anuncio.Estados p : Anuncio.Estados.values()) {
                    	if(p.getId()==estadoid) {
                    		estado = p;
                    	}
                    }
                    a = contactosDAO.buscarContactoEmail(idautor); 
                    g = new AnuncioGeneral(id,titulo,cuerpo,a,fechapublicacion,estado);
                    anuncios.add(g);
    			
    			}
            }
            return anuncios;
            //if(stmt!= null) stmt.close();

        }catch(Exception e) {
            System.out.println("Error al cargar los anuncios de la Base de Datos");
        }
        return anuncios;

    }
	
	/**
	 * Metodo que se encarga de guardar los Anuncios en el fichero de datos
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
	public void actualizarDestinatarios() throws SQLException {

        //Habria que hacerlo de forma que se compararan con los que hay. Si no esta en la base de datos 
        //se añade y si no esta en destinatarios se elimina. Con añadirlo creo que ya estaria. ELiminarlo seria que ya no existe el usuario, o le han quitado un interes.


        //Esto si es tematico
        //Hacer que cuando se modifique los intereses de un contacto se borre el usuario de la tabla destinatarios y de intereses_contacto.

        int cont=0, status=0;
        for(Anuncio a: getAnuncios()) {
            if(a.getClass().toString().equals("class ejercicio1.AnuncioTematico")) {

                //Aqui quizas si seria conveniente borrar antes los destinatarios para que este bien actualizado. 
                PreparedStatement ps0=con.prepareStatement("delete from destinatarios where idanuncio=?");
                ps0.setInt(1,a.getId());
                status=ps0.executeUpdate();
                for(Contacto e: contactosDAO.getContactos()){
                    cont=0;
                    for(Interes i: interesesDAO.getInteresesContacto(e.getEmail())) {
                    	
                    	for(Interes j : ((AnuncioTematico) a).getIntereses()) {
                    		if((i.getId()==j.getId()) && (cont==0)) {
                                
                                PreparedStatement ps=con.prepareStatement("insert into destinatarios(idanuncio,idcontacto) values(?,?)");
                                ps.setInt(1, a.getId());
                                ps.setString(2, e.getEmail());
                                status = ps.executeUpdate();
                                
                                if(status!=1) {
                                	System.out.println("Error al guardar los destinatarios");
                                }
                                cont++;

                            }
                    	}
                        
                    }


                }

            }

        }

    }

	public void insertarDestinatariosTematico(Anuncio a) throws SQLException {
		int cont=0, status=0;
	    for(Contacto e: contactosDAO.getContactos()){
	    cont=0;
	    	for(Interes i: e.getIntereses()) {
	    		for(Interes j: ((AnuncioTematico) a).getIntereses()) {
	    			if((i.getId()==j.getId()) && (cont==0)) {
	    				PreparedStatement ps=con.prepareStatement("insert into destinatarios(idanuncio,idcontacto) values(?,?)");
	    				ps.setInt(1, a.getId());
	    				ps.setString(2, e.getEmail());
	    				status = ps.executeUpdate();
	    				if(status!=1) {
	    					System.out.println("Error al añadir los destinatarios de anuncio tematico");
	    				}
	    				cont++;
	                            
	    			}
	        	}

	    	}

		}
	}
	public void insertarTodosDestinatarios(Anuncio a) throws SQLException {
		int status;
		
		for(int i=0; i<contactosDAO.getContactos().size() ;i++) {
			
			PreparedStatement ps3=con.prepareStatement(config.getProperty("INSERTAR_DESTINATARIOS"));//ACTUALIZAR SENTENCIAS.
			ps3.setString(1, contactosDAO.getContactos().get(i).getEmail());
			
			ps3.setInt(2, a.getId());
			status= ps3.executeUpdate();
			if(status!=1) {
      			System.out.println("Error al añadir los destinatarios de anuncio");
      		}
		}
	}
	
	public void insertarDestinatariosIndividualizado(Anuncio a) throws SQLException {
		ArrayList<Contacto> destinatarios=new ArrayList<Contacto>();
		boolean condicion=true;
		String linea=new String();
		Scanner sc= new Scanner(System.in);
		int as;
		int status;
		
		while(condicion) {
			
			try {
				
				System.out.print("Introduzca el Email del destinatario: ");
			
				linea=sc.nextLine();
				
				for(Contacto d: contactosDAO.getContactos()) {
					if(d.getEmail().equals(linea.toLowerCase())) {
						destinatarios.add(d);
						
						break;
					}
				}
				System.out.println("Quieres añadir otro destinatario: 1. Si 2.No");
				as=sc.nextInt();
				sc.nextLine();
				
				if(as!=1) {
					condicion=false;
				}
				
			} catch (NoSuchElementException w) {
				
                System.out.println("Debes insertar un numero");
	             
               as=sc.nextInt();
               sc.nextLine();

            }
        }
		for(Contacto d : destinatarios) {
			  PreparedStatement ps=con.prepareStatement("insert into destinatarios(idanuncio,idcontacto) values(?,?)");
              ps.setInt(1, a.getId());
              ps.setString(2, d.getEmail());
              status = ps.executeUpdate();	
              if(status!=1) {
      			System.out.println("Error al añadir los destinatarios de anuncio individualizado");
      		}
		}
 }
	
	public void guardarAnuncio(Anuncio a) throws FileNotFoundException, IOException, ClassNotFoundException {
			Class<? extends Anuncio> aux=a.getClass();
			String tipo= new String();
			tipo= aux.toString();
			System.out.println(tipo);
			int status=0;
			try {
			
			if(tipo.equals("class ejercicio1.AnuncioTematico")) {
				
				
				PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_ANUNCIOS"));
				ps.setString(1, a.getTitulo());
				ps.setString(2, a.getCuerpo());
				ps.setString(3, a.getUsuario().getEmail());
				ps.setInt(4, a.getEstado().getId());
				ps.setDate(5, a.getFecha());
				ps.setDate(6, null);
				ps.setDate(7, null);
				ps.setString(8, tipo);	
				
				status= ps.executeUpdate();
				if(status!=1) {
	      			System.out.println("Error al guardar un anuncio tematico");
	      		}
				
				PreparedStatement ps3 = con.prepareStatement("select id from anuncios where fechapublicacion = ? and titulo = ?");
				
				ps3.setDate(1, a.getFecha());
				ps3.setString(2, a.getTitulo());
				ResultSet rs= ps3.executeQuery();
				rs.next();
				int id = rs.getInt(1);
				a.setId(id);
				System.out.println("El id del anuncio es "+id);
				System.out.println("El numero de intereses del anuncio es " +  ((AnuncioTematico) a).getIntereses().size());
				for(Interes i: ((AnuncioTematico) a).getIntereses()) {
					System.out.println("Entro en el for al menos...");
					PreparedStatement ps2=con.prepareStatement(config.getProperty("INSERTAR_INTERES_ANUNCIO"));
					
					ps2.setInt(1, id);
					ps2.setInt(2, i.getId());
					status= ps2.executeUpdate();	
				
					if(status!=1) {
						System.out.println("Error al añadir los intereses de un anuncio tematico");
					}
				}
				insertarDestinatariosTematico(a);
			}
			else if(tipo.equals("class ejercicio1.AnuncioFlash")) {
				
				PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_ANUNCIOS"));
				ps.setString(1, a.getTitulo());
				ps.setString(2, a.getCuerpo());
				ps.setString(3, a.getUsuario().getEmail());
				ps.setInt(4, a.getEstado().getId());
				ps.setDate(5, a.getFecha());
				ps.setDate(6, ((AnuncioFlash) a).getFechaInicio());
				ps.setDate(7, ((AnuncioFlash) a).getFechaFinal());
				ps.setString(8, tipo);	
				
				status= ps.executeUpdate();
				if(status!=1) {
	      			System.out.println("Error al guardar un anuncio flash");
	      		}
				
				PreparedStatement ps3 = con.prepareStatement("select id from anuncios where fechapublicacion = ? and titulo = ?");
				//Si dos usuarios crean un anuncio en el mismo instante podria tomar un mal id.
				ps3.setDate(1, a.getFecha());
				ps3.setString(2, a.getTitulo());
				ResultSet rs= ps3.executeQuery();
				rs.next();
				int id = rs.getInt(1);
				a.setId(id);
				insertarTodosDestinatarios(a);
			}
			else if(tipo.equals("class ejercicio1.AnuncioIndividualizado")) {
				
				PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_ANUNCIOS"));
				ps.setString(1, a.getTitulo());
				ps.setString(2, a.getCuerpo());
				ps.setString(3, a.getUsuario().getEmail());
				ps.setInt(4, a.getEstado().getId());
				ps.setDate(5, a.getFecha());
				ps.setDate(6, null);
				ps.setDate(7, null);
				ps.setString(8, tipo);	
				
				status= ps.executeUpdate();
				if(status!=1) {
	      			System.out.println("Error al guardar un anuncio individualizado");
	      		}
				
				PreparedStatement ps3 = con.prepareStatement("select id from anuncios where fechapublicacion = ? and titulo = ?");
				//Si dos usuarios crean un anuncio en el mismo instante(por el momento dia) y con el mismo titulo podria tomar un mal id. Podria añadirse mas seguridad pero no creo que sea necesario.
				ps3.setDate(1, a.getFecha());
				ps3.setString(2, a.getTitulo());
				ResultSet rs= ps3.executeQuery();
				rs.next();
				int id = rs.getInt(1);
				a.setId(id);
				insertarDestinatariosIndividualizado(a);
			}
			else if(tipo.equals("class ejercicio1.AnuncioGeneral")) {
				
				
				PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_ANUNCIOS"));
				ps.setString(1, a.getTitulo());
				ps.setString(2, a.getCuerpo());
				ps.setString(3, a.getUsuario().getEmail());
				ps.setInt(4, a.getEstado().getId());
				ps.setDate(5, a.getFecha());
				ps.setDate(6, null);
				ps.setDate(7, null);
				ps.setString(8, tipo);
				
				status= ps.executeUpdate();
				if(status!=1) {
	      			System.out.println("Error al guardar un anuncio general");
	      		}
				
				PreparedStatement ps3 = con.prepareStatement("select id from anuncios where fechapublicacion = ? and titulo = ?");
				//Si dos usuarios crean un anuncio en el mismo instante podria tomar un mal id. Puedo comparar n parametros para arreglarlo. No lo voy a hacer.
				ps3.setDate(1, a.getFecha());
				ps3.setString(2, a.getTitulo());
				ResultSet rs= ps3.executeQuery();
				rs.next();
				int id = rs.getInt(1);
				a.setId(id);
				
				insertarTodosDestinatarios(a);
			}
	}catch(Exception e) {
		e.printStackTrace();
	}
}
	
	
	/**
	 * Metodo que se encarga de publicar un Anuncio
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException 
	 */
	public void publicarAnuncio(Anuncio e) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		int status=0;
		String string = new String();
		Class<? extends Anuncio> a=e.getClass();
		string=a.toString();
		String sqlUpdate=new String();
		sqlUpdate = "UPDATE anuncios " + "SET estado = ? " + "WHERE id = ?";
		PreparedStatement ps=con.prepareStatement(sqlUpdate);
		ps.setInt(1, 3);
		ps.setInt(2, e.getId());
		
		
		if(string.equals("class Ejercicio1.AnuncioFlash")) {
			Date fechaActual=new Date(System.currentTimeMillis());
			if( (fechaActual.compareTo(((AnuncioFlash) e).getFechaInicio())>0) & (fechaActual.compareTo(((AnuncioFlash) e).getFechaFinal())<0) ){
				ps.setInt(1, 3);
				ps.setInt(2, e.getId());
			}
			else {
				ps.setInt(1, 2);
				ps.setInt(2,  e.getId());
			}
		}
		
		status = ps.executeUpdate();
		
		if(status!=1) {
			System.out.println("Error al actualizar en la base de datos");
		}
		
		
	}
	
	/**
	 * Metodo que se encarga de modificar un Anuncio en edicion
	 * @param Anuncio
	 * @throws SQLException 
	 */
	public void modificarAnuncio(Anuncio e) throws SQLException {
		
		String string = new String();
		Class<? extends Anuncio> a=e.getClass();
		string=a.toString();
		
		if(e.getEstado().getId()>2) {
			System.out.println("No se puede modificar un anuncio ya publicado/archivado ");
		}
		
		
		if(string.equals("class ejercicio1.AnuncioTematico")) {
			modificarAnuncioTematico(e,interesesDAO.getIntereses());
		}
		else if(string.equals("class ejercicio1.AnuncioFlash")) {
			modificarAnuncioFlash(e);
		}
		else if(string.equals("class ejercicio1.AnuncioIndividualizado")) {
			modificarAnuncioIndividualizado(e);
		}
		else if(string.equals("class ejercicio1.AnuncioGeneral")) {
			modificarAnuncioGeneral(e);
		}
		
		
		
	}
	
	/**
	 * Metodo que se encargar de modificar un AnuncioTematico
	 * @param Anuncio
	 * @param intereses
	 */
	public void modificarAnuncioTematico(Anuncio e, ArrayList<Interes> intereses) throws SQLException{
		
		Scanner sc=new Scanner(System.in);
		Scanner sl=new Scanner(System.in);
		
		System.out.println("Que quieres modificar: ");
		System.out.println("1. Titulo ");
		System.out.println("2. Cuerpo ");
		System.out.println("3. Intereses");
		
		int a=sc.nextInt();
		String sqlUpdate=new String();
		int status=0;
		
		if(a==1) {
			
			sqlUpdate = "UPDATE anuncios " + "SET titulo = ? " + "WHERE id = ?";
			System.out.print("Introduce el nuevo titulo: ");
			String titulo=new String();
			titulo=sl.nextLine();
			PreparedStatement ps=con.prepareStatement(sqlUpdate);
			ps.setString(1, titulo);
			ps.setInt(2, e.getId());
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			
		}
		
		else if(a==2) {
			
			sqlUpdate = "UPDATE anuncios " + "SET cuerpo = ? " + "WHERE id = ?";
			System.out.print("Introduce el nuevo Cuerpo: ");
			String cuerpo=new String();
			cuerpo=sl.nextLine();
			PreparedStatement ps=con.prepareStatement(sqlUpdate);
			ps.setString(1, cuerpo);
			ps.setInt(2, e.getId());
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			
		}
		
		else if(a==3) {
			ArrayList<Interes> actuales=new ArrayList<Interes>();
			ArrayList<Integer> aux = new ArrayList<Integer>();
			ArrayList<Integer> contador = new ArrayList<Integer>();
			ResultSet rs;
			Statement stmt=con.createStatement();
			PreparedStatement ps= con.prepareStatement("select idinteres from intereses_anuncios where idanuncio= ?");
			ps.setInt(1, e.getId());
			rs=ps.executeQuery();
			actuales=((AnuncioTematico) e).getIntereses();
			System.out.print("Que desea eliminar(1) o añadir(2) un intereses del anuncio: "); 
			a=sc.nextInt();
			if(a==1) {
				System.out.println("Cual desea eliminar: ");
				Integer cont=0;
			 while(rs.next()) {
				 aux.add(rs.getInt(1));
			 }
				for(Interes var: actuales) {
					System.out.println(aux.get(cont).toString()+var.getInteres());
					cont++;
					
				}
			 
				a=sc.nextInt();
				sqlUpdate = "DELETE FROM intereses_anuncios " + "WHERE idanuncio= ? " + "AND idinteres= ? ";
				PreparedStatement ps1=con.prepareStatement(sqlUpdate);
				ps1.setInt(1, e.getId() );
				ps1.setInt(2, a );
				status=ps1.executeUpdate();
				 if(status!=1) {
		            	System.out.println("Error al insertar el interes al anuncio");
		         }
			}
			else if(a==2) {
				System.out.println("Intereses: ");
				for(Interes var: interesesDAO.getIntereses()) {
					System.out.println(var.getId()+". "+ var.getInteres());
				}
				System.out.print("Cual desea añadir: ");
				a=sc.nextInt();
				PreparedStatement ps2= con.prepareStatement("insert into intereses_anuncios(idanuncio,idinteres) values(?,?) ");	
	            ps2.setInt(1, e.getId());
	            ps2.setInt(2, a);
	            status=ps2.executeUpdate();
	            if(status!=1) {
	            	System.out.println("Error al insertar el interes al anuncio");
	            }
			}
			
			else {
				System.out.print("Opcion no valida");
			}
		}
		
		else {
			System.out.print("Opcion no valida");
		}
	}
	
	/**
	 * Metodo que se encarga de modificar un AnuncioFlash
	 * @param Anuncio
	 * @throws SQLException 
	 */
	public void modificarAnuncioFlash(Anuncio e) throws SQLException{
		
		Scanner sc=new Scanner(System.in);
		Scanner sl=new Scanner(System.in);
		ControlDeErrores control=new ControlDeErrores();
		
		System.out.println("Que quieres modificar: ");
		System.out.println("1. Titulo ");
		System.out.println("2. Cuerpo ");
		System.out.println("3. Fecha Inicio");
		System.out.println("4. Fecha Final");
		
		int a=sc.nextInt();
		String sqlUpdate=new String();
		int status=0;
		
		if(a==1) {
			
			System.out.print("Introduce el nuevo titulo: ");
			String titulo=new String();
			titulo=sl.nextLine();
			PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_TITULO_ANUNCIO"));
			ps.setString(1, titulo);
			ps.setInt(2, e.getId());
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			else {
				e.setTitulo(titulo);
			}
			
		}
		
		else if(a==2) {
			
			
			System.out.print("Introduce el nuevo Cuerpo: ");
			String cuerpo=new String();
			cuerpo=sl.nextLine();
			PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_CUERPO_ANUNCIO"));
			ps.setString(1, cuerpo);
			ps.setInt(2, e.getId());
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			else {
				e.setCuerpo(cuerpo);
			}
		}
		
		else if(a==3) {
			
			String fechainicio=new String();
			System.out.print("Introduzca la nueva fecha de inicio(dd/mm/yyyy): ");
			fechainicio = sc.nextLine();
			 
			Date fechaaux = new Date(0);
			int cont=1;
			while(cont!=0) {
				cont=0;
				try {
					fechainicio = sc.nextLine();
					while(!(control.esFecha(fechainicio))) {
						System.out.println("Formato de la fecha (dd/mm/yyyy)");
						System.out.print("Vuelva a introducir la fecha: ");
						fechainicio=sc.nextLine();
					}
					fechaaux = Date.valueOf(fechainicio);
				} catch (IllegalArgumentException e1) {
					System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy hh:mm:ss): ");
					cont++;
				}
			}
			PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_FECHAINICIO_ANUNCIO"));
			ps.setDate(1, fechaaux);
			ps.setInt(2, e.getId());
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			else {
				((AnuncioFlash) e).setFechaInicio(fechaaux);
			}
		}
		
		else if(a==4) {
			
			String fechafinal=new String();
			System.out.print("Introduzca la nueva fecha final(dd/mm/yyyy): ");
			fechafinal = sc.nextLine();
			 
			Date fechaaux = new Date(0);
			int cont=1;
			while(cont!=0) {
				cont=0;
				try {
					fechafinal = sc.nextLine();
					while(!(control.esFecha(fechafinal))) {
						System.out.println("Formato de la fecha (dd/mm/yyyy)");
						System.out.print("Vuelva a introducir la fecha: ");
						fechafinal=sc.nextLine();
					}
					fechaaux = Date.valueOf(fechafinal);
				} catch (IllegalArgumentException e1) {
					System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy hh:mm:ss): ");
					cont++;
				}
			}
			PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_FECHAFINAL_ANUNCIO"));
			ps.setDate(1, fechaaux);
			ps.setInt(2, e.getId());
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			else {
				((AnuncioFlash) e).setFechaFinal(fechaaux);
			}
		}
		
		else {
			System.out.print("Opcion no valida");
		}
	}
	
	/**
	 * Metodo que se encarga de modificar un AnuncioIndividualizado
	 * @param Anuncio
	 * @param destinatarios
	 * @throws SQLException 
	 */
	public void modificarAnuncioIndividualizado(Anuncio e) throws SQLException{
		
		if(e==null) {
			System.out.println("No existe anuncio con dichos atributos");
			return;
		}
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Que quieres modificar: ");
		System.out.println("1. Titulo ");
		System.out.println("2. Cuerpo ");
		System.out.println("3. Destinatarios");
		
		int a=sc.nextInt();
		sc.nextLine();
		
		
		int status=0;
		
		if(a==1) {
			
			
			System.out.print("Introduce el nuevo titulo: ");
			String titulo=new String();
			titulo=sc.nextLine();
			PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_TITULO_ANUNCIO"));
			ps.setString(1, titulo);
			ps.setInt(2, e.getId());
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			else {
				e.setTitulo(titulo);
			}
			
			
		}
		
		else if(a==2) {
			
			System.out.print("Introduce el nuevo Cuerpo: ");
			String cuerpo=new String();
			cuerpo=sc.nextLine();
			PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_CUERPO_ANUNCIO"));
			ps.setString(1, cuerpo);
			ps.setInt(2, e.getId());
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			else {
				e.setCuerpo(cuerpo);
			}
		}
		
		else if(a==3) {
			PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_DESTINATARIOS_ANUNCIO"));
			ps.setInt(1, e.getId() );
			ResultSet rs = ps.executeQuery();
			status =0;
			
			ArrayList<String> actuales=new ArrayList<String>();
			
			System.out.print("Que desea eliminar(1) o añadir(2) un destinatario del anuncio: "); 
			a=sc.nextInt();
			sc.nextLine();
			if(a==1) {
				while(rs.next()) {
					
					actuales.add(rs.getString(1));
				}
				System.out.println("Cual desea eliminar: ");
				Integer cont=0;
				for(String var: actuales) {
					System.out.println(cont.toString()+var);
					cont++;
				}
				a=sc.nextInt();
				sc.nextLine();
				String email = actuales.get(a);
				
				PreparedStatement ps1=con.prepareStatement(config.getProperty("BORRAR_DESTINATARIO_DOSPARAMETROS"));
				ps1.setString(1, email);
				ps1.setInt(2,e.getId());
				status = ps1.executeUpdate();
				
				if(status!=1) {
					System.out.println("Error al eliminar el contacto como destinatario");
				}
				
			}
			else if(a==2) {
				
				System.out.println("Cual desea añadir: ");
				Integer cont=0;
				for(Contacto var: contactosDAO.getContactos()) {
					System.out.println(cont.toString()+var.getEmail());
					cont++;
				}
				a=sc.nextInt();
				sc.nextLine();
				
				Contacto b = contactosDAO.getContactos().get(a);

				PreparedStatement ps1=con.prepareStatement(config.getProperty("INSERTAR_DESTINATARIOS"));
				ps1.setString(1, b.getEmail());
				ps1.setInt(2,e.getId());
				status=ps1.executeUpdate();
				
				if(status!=1) {
					System.out.println("Error al añadir el contacto como destinatario");
				}
				
			}
			
			else {
				System.out.print("Opcion no valida");
			}
		}
		
		else {
			System.out.print("Opcion no valida");
		}
	}
	
	
	/**
	 * Metodo que se encarga de modificar un AnuncioGeneral
	 * @param Anuncio
	 * @throws SQLException 
	 */
	public void modificarAnuncioGeneral(Anuncio e) throws SQLException{
		if(e==null) {
			System.out.println("No existe anuncio con dichos atributos");
			return;
		}
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Que quieres modificar: ");
		System.out.println("1. Titulo ");
		System.out.println("2. Cuerpo ");
		
		int a=sc.nextInt();
		sc.nextLine();
		String sqlUpdate=new String();
		
		
		int status=0;
		
		if(a==1) {
			
			sqlUpdate = "UPDATE anuncios " + "SET titulo = ? " + "WHERE id = ?";
			System.out.print("Introduce el nuevo titulo: ");
			String titulo=new String();
			titulo=sc.nextLine();
			PreparedStatement ps=con.prepareStatement(sqlUpdate);
			ps.setString(1, titulo);
			ps.setInt(2, e.getId());
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			else {
				e.setTitulo(titulo);
			}
			
		}
		
		else if(a==2) {
			sqlUpdate = "UPDATE anuncios " + "SET cuerpo = ? " + "WHERE id = ?";
			System.out.print("Introduce el nuevo Cuerpo: ");
			String cuerpo=new String();
			cuerpo=sc.nextLine();
			PreparedStatement ps=con.prepareStatement(sqlUpdate);
			ps.setString(1, cuerpo);
			ps.setInt(2, e.getId());
			status = ps.executeUpdate();
			if(status!=1) {
				System.out.println("Error al actualizar en la base de datos");
			}
			else {
				e.setCuerpo(cuerpo);
			}
		}
		
		else {
			System.out.print("Opcion no valida");
		}
		
		
	}
	
	/**
	 * Metodo que se encarga de archivar un Anuncio
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException 
	 */
	public void archivarAnuncio(Anuncio e) throws SQLException {
		int status=0;
		String sqlUpdate=new String();
		sqlUpdate = "UPDATE anuncios " + "SET estado = ? " + "WHERE id = ?";
		PreparedStatement ps=con.prepareStatement(sqlUpdate);
		ps.setInt(1, 4);
		ps.setInt(2, e.getId());
		status = ps.executeUpdate();
		if(status!=1) {
			System.out.println("Error al actualizar en la base de datos");
		}
	}
	
	public ArrayList<Anuncio> getAnunciosContacto(Contacto e) throws SQLException{
        ResultSet rs;
        ArrayList<Integer>anuncios=new ArrayList<Integer>();
        ArrayList<Anuncio> anunciosList=new ArrayList<Anuncio>();
        Scanner sc = new Scanner(System.in);
        String sql=new String();
        
        
        sql ="select idcontacto, idanuncio from destinatarios where idcontacto=? order by idanuncio";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, e.getEmail());
        rs=ps.executeQuery();
        while(rs.next()) {
            anuncios.add(rs.getInt(2));
        }

        for(Anuncio a: getAnuncios()) {
            for(Integer b: anuncios) {
                if(a.getId()==b) {
                    anunciosList.add(a);
                }
            }
        }
        System.out.println("Como quieres ordenados los anuncios: 1. Fecha 2. Propietario");
        int as = sc.nextInt();
        sc.nextLine();
        
        if(as==1) {
        	return anunciosList;
        }
        else if(as==2) {
        	return ordenarPropietario(anunciosList);
        }
        
        else {
        	System.out.println("Opcion no valida. Se mostraran ordenados por fecha.");
        }
		return anunciosList;
        

    }
	
	public ArrayList<Anuncio> ordenarPropietario(ArrayList<Anuncio> toOrder) {
		
		ArrayList<String> propietarios= new ArrayList<String>();
		ArrayList<Anuncio> ordenado= new ArrayList<Anuncio>();
		
		//Coge todos los nombres de los propietarios de todos los anuncios.
		
		for(Anuncio a: toOrder) {
			propietarios.add(a.getUsuario().getNombre());
		}
		
		//Ordena los nombres.
		Collections.sort(propietarios);
		
		//Va comparando los nombres ordenados extraidos anteriormente con los que coge del vector principal hasta que sean iguales. Si coinciden pues los añade a otro vector y los elimina de los otros dos.
	
		while(toOrder.size()!=0) {
			for(int j=0; j<toOrder.size();) {
				
				if(propietarios.get(0).equals(toOrder.get(j).getUsuario().getNombre())) {
					ordenado.add(toOrder.get(j));
					propietarios.remove(0);
					toOrder.remove(j);
					j=0;
					
				}
				else {
					j++;
				}
				
			}
		}
		
		return ordenado;
	}

	/**
	 * Metodo que se encarga que crear el menu de busqueda de un Anuncio
	 * @return Anuncio
	 * @throws SQLException 
	 */
	public Anuncio buscarAnuncio() throws SQLException {
		int opcion;
		Scanner sc= new Scanner(System.in);
		System.out.println("Buscar por: 1. Fecha 2.Interes 3. Propietario 4. Destinatario");
		Anuncio e=null;
        opcion = sc.nextInt();
        sc.nextLine();
                
        if (opcion==1) {
        	String fecha=new String();
			System.out.print("Introduzca la fecha: ");
			fecha = sc.nextLine();
        	e=buscarFecha(fecha);
        }
        
        else if (opcion==2) {
        	
        	Interes interes=null;
        	int cont=0;
        	for (Interes myVar : interesesDAO.getIntereses()) {
				System.out.println(cont+" "+myVar);
				cont++;
			}
			System.out.print("Seleccionar Interes: ");
			int newinteres= sc.nextInt();
			sc.nextLine();
			
			
        	for (int i=0; i<interesesDAO.getIntereses().size();i++) {
				if(newinteres==interesesDAO.getIntereses().get(i).getId()) {
					interes=interesesDAO.getIntereses().get(i);
				}
				
			}
			
        	e=buscarIntereses(interes,interesesDAO.getIntereses());
        }
        
        else if (opcion==3) {
        	String nombre=new String();
			System.out.print("Introduzca el email del propietario: ");
			nombre = sc.nextLine();
        	e=buscarPropietario(nombre);
        }
        
        else if (opcion==4) {
        	String destinatario=new String();
			System.out.print("Introduzca el email del destinatario: ");
			destinatario = sc.nextLine();
        	e=buscarDestinatario(destinatario);
        }
        
        
        else {
        	
        	System.out.println("Opcion no valida");
        	
        	return null;
        }
        
        return e;
 
	}
	
	/**
	 * Metodo que se encarga de buscar un Anuncio por la fecha de publicacion del mismo
	 * @param fecha
	 * @return Anuncio
	 * @throws SQLException 
	 */
	public Anuncio buscarFecha(String fecha) throws SQLException {
		ArrayList<Anuncio> aux=new ArrayList<Anuncio>();
		ArrayList<Anuncio> listaAnuncios= getAnuncios();
		String fechaaux=new String();
		int n = 0;
		System.out.print("Indique la fecha de publicacion(yyyy-mm-dd) a buscar: ");
		Integer cont=0;
		String cad=new String();
		Anuncio buscado=null;
		Scanner sc = new Scanner(System.in);
		
		Date dnuevafecha = new Date(n);
		cont=1;
		while(cont!=0) {
			cont=0;
			try {
				fechaaux = sc.nextLine();
				dnuevafecha=Date.valueOf(fechaaux);
			} catch (IllegalArgumentException e1) {
				cont++;
				System.out.print("Fecha mal introducida. Vuelva a introducirla(yyyy-mm-dd): ");
			}
		}
		
		for(Anuncio d: listaAnuncios) {
			if(d.getFecha().equals(dnuevafecha)) {
				aux.add(d);			
			}
		}
			
		if(aux.size()==0) {
			return null;
		
		}
		
		if(aux.size()==1) {
			return aux.get(0);
		}
		
		
		for(Integer i=0;i<aux.size();i++) {
			System.out.println(i.toString()+". Titulo: "+aux.get(i).getTitulo());
		}
		
		System.out.println("Selecciona el anuncio buscado");	
		
		int seleccion2=sc.nextInt();
		sc.nextLine();	
			
		buscado=aux.get(seleccion2);
			
		return buscado;

	}
	
	
	/**
	 * Metodo que se encarga de buscar un Anuncio por los intereses del mismo
	 * @param interes
	 * @param intereses
	 * @return Anuncio
	 * @throws SQLException 
	 */
	public Anuncio buscarIntereses(Interes interes, ArrayList<Interes> intereses) throws SQLException {
			
			
		PreparedStatement ps1= con.prepareStatement("select id, titulo, cuerpo, idautor, estado, fechapublicacion, fechainicio, fechafinal, tipo  from anuncios where tipo = ?");
		PreparedStatement ps=con.prepareStatement("select idanuncio from intereses_anuncios where idinteres = ?");
	        
		Anuncio buscado= null;
	    ArrayList<Interes> interesesaux= new ArrayList<Interes>();
	    ArrayList<AnuncioTematico> anuncios =new ArrayList<AnuncioTematico>();
	    ArrayList<Integer> aux = new ArrayList<Integer>();
	    ArrayList<AnuncioTematico> buscadolist = new ArrayList<AnuncioTematico>();
	    Scanner sc = new Scanner(System.in);
	    int id= 0;
	    Anuncio.Estados estado=null;
	       
	    Contacto a= null;
	    AnuncioTematico t= null;
	    try {
	    	ps1.setString(1, "class ejercicio1.AnuncioTematico");
	        ResultSet rs1 = ps1.executeQuery();
	        	
	        while(rs1.next()) {
	        		
	        	for (Anuncio.Estados p : Anuncio.Estados.values()) {
	        		if(p.getId()==rs1.getInt(5)) {
	        			estado = p;
	                }
	            }
	        		
	        	a = new Contacto(rs1.getString(4), null, null, null, null,null);
	        	t = new AnuncioTematico(rs1.getInt(1), rs1.getString(2), rs1.getString(3), a, interesesDAO.getInteresesAnuncio(rs1.getInt(1)),Date.valueOf(rs1.getString(6)), estado);
	        	anuncios.add(t);
	        }
	    }catch(Exception e) {
	        	
	    }
	        
	    try{
	            
	    	ps.setInt(1,interes.getId());
	        ResultSet rs= ps.executeQuery();
	            
	        while(rs.next()) {
	        	aux.add(rs.getInt(1));
	             
	        }
	        for(AnuncioTematico d : anuncios) {
	            for(Integer i : aux) {
	            	if(d.getId()==i) {
	            		buscadolist.add(d);
	           		}
	           	}
	        }
	        for(Integer i=0;i<buscadolist.size();i++) {
	   			System.out.println(i.toString()+". Titulo: "+buscadolist.get(i).getTitulo());
	   		}
	    		
	        System.out.println("Selecciona el anuncio buscado");	
	    		
	    	int seleccion2=sc.nextInt();
	   		sc.nextLine();	
	    			
	   		buscado=buscadolist.get(seleccion2);
	    			
	   		return buscado;
		
	    }catch(Exception e) {
		
		
	    }
		return null;
	}
	
	/**
	 * Metodo que busca un Anuncio por el nombre del propietario del mismo
	 * @param propietario
	 * @return Anuncio
	 * @throws SQLException 
	 */
	public Anuncio buscarPropietario(String email) throws SQLException {
		  ArrayList<Anuncio> aux= new ArrayList<Anuncio>();
		  ArrayList<Anuncio> listaAnuncios = getAnuncios();
		  Anuncio buscado= null;
		  Scanner sc = new Scanner(System.in);
            
          for(Anuncio d : listaAnuncios) {
            	
        	  if(d.getUsuario().getEmail().equals(email)) {
            			aux.add(d);
        	  }
          }
            
          for(Integer i=0;i<aux.size();i++) {
        	  System.out.println(i.toString()+". Titulo: "+aux.get(i).getTitulo());
          }
    		
          System.out.print("Selecciona el anuncio buscado: ");	
    		
          int seleccion2=sc.nextInt();
          sc.nextLine();	
    			
          buscado=aux.get(seleccion2);
    			
          return buscado;
	  }
	/**
	 * Metodo que se encarga de buscar un Anuncio por el email de uno de sus destinatarios
	 * @param email
	 * @return Anuncio
	 * @throws SQLException 
	 */
	public Anuncio buscarDestinatario(String email) throws SQLException {
		  PreparedStatement ps=con.prepareStatement("select idanuncio from destinatarios where idcontacto = ?");
	        Integer cont=1;
	        String emailaux=new String();
	        Anuncio buscado= null;
	        ArrayList<Anuncio> anuncios =new ArrayList<Anuncio>();
	        ArrayList<Integer> aux = new ArrayList<Integer>();
	        Scanner sc = new Scanner(System.in);
	        
	        try{
	            
	            ps.setString(1,email);
	            ResultSet rs= ps.executeQuery();
	            
	            while(rs.next()) {
	             aux.add(rs.getInt(1));
	             
	            }
	            for(Anuncio a : getAnuncios()) {
	            	for(Integer i : aux) {
	            		if(a.getId()==i) {
	            			anuncios.add(a);
	            		}
	            	}
	            }
	            for(Integer i=0;i<anuncios.size();i++) {
	    			System.out.println(i.toString()+". Titulo: "+anuncios.get(i).getTitulo());
	    		}
	    		
	    		System.out.println("Selecciona el anuncio buscado");	
	    		
	    		int seleccion2=sc.nextInt();
	    		sc.nextLine();	
	    			
	    		buscado=anuncios.get(seleccion2);
	    			
	    		return buscado;
	    }catch(Exception e) {
	    	
	    }
	    return buscado;
	}
	
	/**
	 * 
	 * Metodo que imprime todos los Anuncios
	 * @throws SQLException 
	 */
	public void mostrarAnuncios() throws SQLException {
		
		PreparedStatement ps=con.prepareStatement("select id, titulo, cuerpo, fechapublicacion from anuncios");
		
		
		try{
			
			ResultSet rs= ps.executeQuery();
			String cad = new String();
			while(rs.next()) {
				
				cad= "\nID: "+rs.getInt(1)+" Titulo: "+rs.getString(2)+"\nCuerpo: " + rs.getString(3) + "\nFecha de Publicacion: " + rs.getDate(4).toString();
				System.out.println(cad);
				
			}
			
			
		} catch(Exception e) { 
				e.printStackTrace();
				
		}
		
	}
	
	
	public void main() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		boolean condicion =true;
		int a=0;
		Scanner sc=new Scanner(System.in);
		while(condicion) {
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
					modificarAnuncio(buscarAnuncio());
					}catch(NullPointerException e) {
						System.out.println("No existen anuncios con dichos parametros");
					}
				}
			
				else if(a==2) {
					try {
					publicarAnuncio(buscarAnuncio());
					}catch(NullPointerException e) {
						System.out.println("Anuncio seleccionado no valido");
					}
				}
		
				else if(a==3) {
					try {
					archivarAnuncio(buscarAnuncio());
					}catch(NullPointerException e) {
						System.out.println("Anuncio seleccionado no valido");
					}
			
				}
				
				else if(a==4) {
					try {
					Anuncio an=buscarAnuncio();
					
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
					mostrarAnuncios();
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