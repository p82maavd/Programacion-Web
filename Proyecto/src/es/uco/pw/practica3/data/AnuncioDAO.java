package es.uco.pw.practica3.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import es.uco.pw.practica3.business.Anuncio;
import es.uco.pw.practica3.business.AnuncioFlash;
import es.uco.pw.practica3.business.AnuncioGeneral;
import es.uco.pw.practica3.business.AnuncioIndividualizado;
import es.uco.pw.practica3.business.AnuncioTematico;
import es.uco.pw.practica3.business.Configuracion;
import es.uco.pw.practica3.business.Contacto;
import es.uco.pw.practica3.business.Interes;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;



/**
 * Declaracion de la clase AnuncioDAO
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 *
 */

public class AnuncioDAO  {
	
	private Connection con=null;
	private static AnuncioDAO instance =null;
	private DAO factory=DAO.getInstance(null,null,null);
	private InteresDAO interesesDAO= factory.getInteresDAO();
	private ContactoDAO contactosDAO = factory.getContactoDAO();
	Configuracion config;
	

	/**
	 * Este Metodo se encarga de crear una instancia en el caso de que no haya una ya creada. Patron de diseño Singleton
	 * @return Instancia única de GestorAnuncios.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	*/
	
	private AnuncioDAO(Connection e) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		this.con=e;
		config= Configuracion.getInstance(null);
	}
	/**
	 * Metodo Singleton para obtener una instancia de la clase AnuncioDAO
	 * @param e Objeto de la clase Connection para la base de datos.
	 * @return Devuelve un objeto de la clase AnuncioDAO
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public static AnuncioDAO getInstance(Connection e) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		
		if(instance==null) {
			instance=new AnuncioDAO(e);
		}
		return instance;
	}
	/**
	 * Metodo para obtener los anuncios de la base de datos
	 * @return Devuelve un arrayList de Anuncios
	 * @throws SQLException
	 */
	public ArrayList<Anuncio> getAnuncios() throws SQLException{
		Statement stmt=con.createStatement();
        ResultSet rs= stmt.executeQuery(config.getProperty("OBTENER_ANUNCIOS"));
        ArrayList<Anuncio> anuncios = new ArrayList<Anuncio>();
        anuncios=almacenarAnuncios(rs);
        return anuncios;

    }
	
	/**
	 * Metodo que devuelve los anuncios de un contacto.
	 * @param autor Correo del contacto del que se quieren obtener sus anuncios
	 * @return ArrayList con los anuncios del contacto
	 * @throws SQLException
	 */
	public ArrayList<Anuncio> getMisAnuncios(String autor) throws SQLException{
		PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_ANUNCIOS_AUTOR"));
		
		ps.setString(1, autor);
		
        ResultSet rs= ps.executeQuery();
        
        ArrayList<Anuncio> anuncios =new ArrayList<Anuncio>();
        anuncios=almacenarAnuncios(rs);
        return anuncios;

    }
	
	/**
	 * Metodo que devuelve los anuncios de la base de datos que sean tematicos
	 * @return ArrayList con los anuncios tematicos
	 * 
	 */
	public ArrayList<Anuncio> getAnunciosTematicos() throws SQLException{
		ArrayList<Anuncio> anuncios=new ArrayList<Anuncio>();
		
		PreparedStatement ps0=con.prepareStatement(config.getProperty("OBTENER_ANUNCIOS_TIPO"));
        ps0.setString(1,"class es.uco.pw.practica3.business.AnuncioTematico");
        ResultSet rs = ps0.executeQuery();
        

        while(rs.next()) {
        
        		int id = rs.getInt(1);
                String titulo = rs.getString(2);
                String cuerpo = rs.getString(3);
                String idautor = rs.getString(4);
                int estadoid = rs.getInt(5);
                Date fechapublicacion = rs.getDate(6);

                Anuncio.Estados estado= null;
                for (Anuncio.Estados p : Anuncio.Estados.values()) {
                	if(p.getId()==estadoid) {
                		estado = p;
                	}
                }
                ArrayList<Interes> interesesaux = interesesDAO.getInteresesAnuncio(id);
                 
                AnuncioTematico t = new AnuncioTematico(id,titulo,cuerpo,idautor,interesesaux, fechapublicacion, estado);
                anuncios.add(t);
                
			}
		return anuncios;
	}
	
	/**
	 * Metodo que devuelve los intereses de la base de datos que tengan un interes en concreto
	 * @param i Identificador del interes a buscar
	 * @return ArrayList con los anuncios buscados.
	 */
	
	public ArrayList<Integer> getAnunciosInteres(Integer i) throws SQLException {
		
		ArrayList<Integer> anuncios = new ArrayList<Integer>();
		
		PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_ANUNCIOS_INTERES"));
		
		ps.setInt(1, i);
		
        ResultSet rs= ps.executeQuery();
        
        while(rs.next()) {
         
            anuncios.add(rs.getInt(1));
                
		}
		
        return anuncios;
	}

	/**
	 * Metodo que inserta destinatarios de un anuncio tematico al crearlo
	 * @param a Anuncio Tematico
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public void insertarDestinatariosTematico(Anuncio a) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
		int cont=0, status=0;
	    for(Contacto e: contactosDAO.getContactos()){
	    cont=0;
	    	for(Interes i: e.getIntereses()) {
	    		for(Interes j: ((AnuncioTematico) a).getIntereses()) {
	    			if((i.getId()==j.getId()) && (cont==0)) {
	    				PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_DESTINATARIOS"));
	    				ps.setString(1, e.getEmail());
	    				ps.setInt(2, a.getId());
	    				
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
	/**
	 * Metodo que se encarga de añadir los destinatarios a un anuncio general o flash
	 * @param a Anuncio General o Flash
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public void insertarTodosDestinatarios(Anuncio a) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
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
	
	/**
	 * Metodo para insertar los destinatarios de un anuncio individualizado
	 * @param a Anuncio Individualizado
	 * @param destinatarios Emails de los destinatarios
	 * @throws SQLException
	 */
	public void insertarDestinatariosIndividualizado(Anuncio a, String[] destinatarios) throws SQLException {
		
		int status;
		
		for(String d : destinatarios) {
			
			PreparedStatement ps0=con.prepareStatement(config.getProperty("OBTENER_CONTACTOS_EMAIL"));
			ps0.setString(1,d);
			ResultSet rs= ps0.executeQuery();
			if(rs.next()) {
						
				PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_DESTINATARIOS"));
				ps.setInt(2, a.getId());
				ps.setString(1, d);
				status = ps.executeUpdate();	
		        if(status!=1) {
		        	System.out.println("Error al a�adir los destinatarios de anuncio individualizado");
			    }	
			}
		}
	}
	
	/**
	 * Metodo para guardar los anuncios en la base de datos
	 * @param a Anuncio que se quiere guardar
	 * @param destinatarios Destinatarios de los anuncios Individualizados
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void guardarAnuncio(Anuncio a, String[] destinatarios) throws FileNotFoundException, IOException, ClassNotFoundException {
			Class<? extends Anuncio> aux=a.getClass();
			String tipo= new String();
			tipo= aux.toString();
			
			int status=0;
			try {
			
			if(tipo.equals("class es.uco.pw.practica3.business.AnuncioTematico")) {
				
				
				PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_ANUNCIOS"));
				ps.setString(1, a.getTitulo());
				ps.setString(2, a.getCuerpo());
				ps.setString(3, a.getUsuario());
				ps.setInt(4, a.getEstado().getId());
				ps.setDate(5, a.getFecha());
				ps.setTimestamp(6, null);
				ps.setTimestamp(7, null);
				ps.setString(8, tipo);	
				
				status= ps.executeUpdate();
				if(status!=1) {
	      			System.out.println("Error al guardar un anuncio tematico");
	      		}
				
				PreparedStatement ps3 = con.prepareStatement(config.getProperty("OBTENER_ID_ANUNCIO"));
				
				ps3.setDate(1, a.getFecha());
				ps3.setString(2, a.getTitulo());
				ResultSet rs= ps3.executeQuery();
				rs.next();
				int id = rs.getInt(1);
				a.setId(id);
				
				
				for(Interes i: ((AnuncioTematico) a).getIntereses()) {
					
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
			else if(tipo.equals("class es.uco.pw.practica3.business.AnuncioFlash")) {
				
				PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_ANUNCIOS"));
				ps.setString(1, a.getTitulo());
				ps.setString(2, a.getCuerpo());
				ps.setString(3, a.getUsuario());
				ps.setInt(4, a.getEstado().getId());
				ps.setDate(5, a.getFecha());
				ps.setTimestamp(6, ((AnuncioFlash) a).getFechaInicio());
				ps.setTimestamp(7, ((AnuncioFlash) a).getFechaFinal());
				ps.setString(8, tipo);	
				
				status= ps.executeUpdate();
				if(status!=1) {
	      			System.out.println("Error al guardar un anuncio flash");
	      		}
				
				PreparedStatement ps3 = con.prepareStatement(config.getProperty("OBTENER_ID_ANUNCIO"));
				//Si dos usuarios crean un anuncio en el mismo instante podria tomar un mal id.
				ps3.setDate(1, a.getFecha());
				ps3.setString(2, a.getTitulo());
				ResultSet rs= ps3.executeQuery();
				rs.next();
				int id = rs.getInt(1);
				a.setId(id);
				insertarTodosDestinatarios(a);
			}
			else if(tipo.equals("class es.uco.pw.practica3.business.AnuncioIndividualizado")) {
				
				PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_ANUNCIOS"));
				ps.setString(1, a.getTitulo());
				ps.setString(2, a.getCuerpo());
				ps.setString(3, a.getUsuario());
				ps.setInt(4, a.getEstado().getId());
				ps.setDate(5, a.getFecha());
				ps.setTimestamp(6, null);
				ps.setTimestamp(7, null);
				ps.setString(8, tipo);	
				
				status= ps.executeUpdate();
				if(status!=1) {
				
	      			System.out.println("Error al guardar un anuncio individualizado");
	      		}
				
				PreparedStatement ps3 = con.prepareStatement(config.getProperty("OBTENER_ID_ANUNCIO"));
				ps3.setDate(1, a.getFecha());
				ps3.setString(2, a.getTitulo());
				ResultSet rs= ps3.executeQuery();
				rs.next();
				int id = rs.getInt(1);
				a.setId(id);
				insertarDestinatariosIndividualizado(a,destinatarios);
			}
			else if(tipo.equals("class es.uco.pw.practica3.business.AnuncioGeneral")) {
				
				
				PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_ANUNCIOS"));
				ps.setString(1, a.getTitulo());
				ps.setString(2, a.getCuerpo());
				ps.setString(3, a.getUsuario());
				ps.setInt(4, a.getEstado().getId());
				ps.setDate(5, a.getFecha());
				ps.setTimestamp(6, null);
				ps.setTimestamp(7, null);
				ps.setString(8, tipo);
				
				status= ps.executeUpdate();
				if(status!=1) {
	      			System.out.println("Error al guardar un anuncio general");
	      		}
				
				PreparedStatement ps3 = con.prepareStatement(config.getProperty("OBTENER_ID_ANUNCIO"));
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
	 * @param e Anuncio que se quiere publicar
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void publicarAnuncio(Anuncio e) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		int status=0;
		if(e==null) {
			System.out.println("Anuncio no valido para publicarse");
			return; 
		}
		String string = new String();
		Class<? extends Anuncio> a=e.getClass();
		string=a.toString();
		
		PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_ESTADO_ANUNCIO"));
		ps.setInt(1, 3);
		ps.setInt(2, e.getId());
		
		if(string.equals("class es.uco.pw.practica3.business.AnuncioFlash")) {
			Timestamp fechaActual=new Timestamp(System.currentTimeMillis());
			if( (fechaActual.compareTo(((AnuncioFlash) e).getFechaInicio())>0) & (fechaActual.compareTo(((AnuncioFlash) e).getFechaFinal())<0) ){
				
				
			}
			else if(fechaActual.compareTo(((AnuncioFlash) e).getFechaInicio())<0){
				ps.setInt(1, 2);
				
			}
			else {
				ps.setInt(1,4);
			}
		}
		
		status = ps.executeUpdate();
		
		if(status!=1) {
			System.out.println("Error al actualizar en la base de datos");
		}
		
		
	}
	
	
	/**
	 * Metodo que se encarga de modificar el titulo de un anuncio en la base de datos
	 * @param e Anuncio del que se quiere modificar el titulo
	 * @param titulo Nuevo titulo del anuncio
	 * @throws SQLException
	 */
	public void modificarTitulo(Anuncio e, String titulo) throws SQLException {
		
		int status;
		PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_TITULO_ANUNCIO"));
		ps.setString(1, titulo);
		ps.setInt(2, e.getId());
		status = ps.executeUpdate();
		if(status!=1) {
			System.out.println("Error al actualizar en la base de datos");
		}
		
	
	}
	/**
	 * Metodo que se encarga de modificar el cuerpo de un anuncio en la base de datos
	 * @param e Anuncio del que se quiere modificar el cuerpo
	 * @param cuerpo Nuevo cuerpo del anuncio.
	 * @throws SQLException
	 */
	public void modificarCuerpo(Anuncio e, String cuerpo) throws SQLException {
		int status;
		PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_CUERPO_ANUNCIO"));
		ps.setString(1, cuerpo);
		ps.setInt(2, e.getId());
		status = ps.executeUpdate();
		if(status!=1) {
			System.out.println("Error al actualizar en la base de datos");
		}
	}
	
	/**
	 * Metodo que se encarga de modificar los intereses de un anuncio en la base de datos
	 * @param e Anuncio del que se quiere modificar los intereses
	 * @param intereses Nuevos intereses del anuncio
	 * @throws SQLException
	 */
	public void modificarIntereses(Anuncio e, String[] intereses) throws SQLException {
		int status;
		PreparedStatement ps0= con.prepareStatement(config.getProperty("ELIMINAR_INTERESES_ANUNCIO"));
		ps0.setInt(1, e.getId());
		status=ps0.executeUpdate();
		for(Anuncio a: getAnunciosTematicos()) {  	
		        
		    PreparedStatement ps1=con.prepareStatement(config.getProperty("BORRAR_DESTINATARIO_ANUNCIO"));
		    ps1.setInt(1, a.getId());
	        status=ps1.executeUpdate();
	        
	    }
		if(intereses!=null) {
			for(String s: intereses) {
					
				PreparedStatement ps=con.prepareStatement(config.getProperty("INSERTAR_INTERES_ANUNCIO"));
				ps.setInt(1, e.getId());
				ps.setInt(2, Integer.parseInt(s));
				status=ps.executeUpdate();
				if(status<1) {
					System.out.println("Error al insertar los intereses");
				}
				
				for(String email: contactosDAO.getContactosInteres(Integer.parseInt(s))) {
					try {
						PreparedStatement ps3=con.prepareStatement(config.getProperty("INSERTAR_DESTINATARIOS"));
						ps3.setString(1,email);
						ps3.setInt(2,e.getId());
						
						status=ps3.executeUpdate();
					}catch(Exception ex) {
						System.out.println("Contacto repe");
					}
					
				}
				
			}
			
		}
	}
	
	/**
	 * Metodo que se encarga de modificar los destinatarios de un anuncio Individualizado en la base de datos
	 * @param e Anuncio del que se quiere modificar los destinatarios
	 * @param destinatarios Nuevos destinatarios del anuncio
	 * @throws SQLException
	 */
	public void modificarDestinatarios(Anuncio e, String[] destinatarios) throws SQLException {
		
		int status;
		PreparedStatement ps0= con.prepareStatement(config.getProperty("BORRAR_DESTINATARIO_ANUNCIO"));
		ps0.setInt(1, e.getId());
		status=ps0.executeUpdate();
		System.out.println("Borramos los destinatarios");
		if(destinatarios!=null) {
			
			for(String d : destinatarios) {
				//Busca si existe el destinatario
				PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_CONTACTOS_EMAIL"));
				ps.setString(1,d);
				ResultSet rs= ps.executeQuery();
				if(rs.next()) {
							
					PreparedStatement ps1=con.prepareStatement(config.getProperty("INSERTAR_DESTINATARIOS"));
					ps1.setString(1, d);
					ps1.setInt(2, e.getId());
					status = ps1.executeUpdate();	
			        if(status!=1) {
			        	System.out.println("Error al añadir los destinatarios de anuncio individualizado");
				    }	
					
				}
				  
			}
		}
		
	}
	
	/**
	 * Metodo que se encarga de modificar la fecha de inicio de un anuncio Flash en la base de datos
	 * @param e Anuncio del que se quiere modificar la fecha de inicio
	 * @param fechainicio Nueva fecha de inicio del anuncio
	 * @throws SQLException
	 */
	public void modificarFechaInicio(Anuncio e, String fechainicio) throws SQLException {
		int status;
		Timestamp fechaaux = new Timestamp(0);
		int cont=1;
		while(cont!=0) {
			cont=0;
			try {
				
				fechaaux = Timestamp.valueOf(fechainicio);
			} catch (IllegalArgumentException e1) {
				System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy hh:mm:ss): ");
				cont++;
			}
		}
		PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_FECHAINICIO_ANUNCIO"));
		ps.setTimestamp(1, fechaaux);
		ps.setInt(2, e.getId());
		status = ps.executeUpdate();
		if(status!=1) {
			System.out.println("Error al actualizar en la base de datos");
		}
		
	}
	
	/**
	 * Metodo que se encarga de modificar la fecha de finalizacion de un anuncio Flash en la base de datos
	 * @param e Anuncio del que se quiere modificar la fecha de finalizacion
	 * @param fechafin Nueva fecha de finalizacion del anuncio
	 * @throws SQLException
	 */
	
	public void modificarFechaFin(Anuncio e, String fechafin) throws SQLException {
		int status;
		Timestamp fechaaux = new Timestamp(0);
		int cont=1;
		while(cont!=0) {
			cont=0;
			try {
				
				fechaaux = Timestamp.valueOf(fechafin);
			} catch (IllegalArgumentException e1) {
				System.out.print("Error con la fecha. Vuelva a introducirla(dd/mm/yyyy hh:mm:ss): ");
				cont++;
			}
		}
		PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_FECHAFINAL_ANUNCIO"));
		ps.setTimestamp(1, fechaaux);
		ps.setInt(2, e.getId());
		status = ps.executeUpdate();
		if(status!=1) {
			System.out.println("Error al actualizar en la base de datos");
		}
		
	}
	

	/**
	 * Metodo que se encarga de archivar un Anuncio
	 * @param e Identificador del anuncio a archivar
	 * @throws SQLException 
	 */
	
	public void archivarAnuncio(int e) throws SQLException {
		int status=0;
		
		PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_ESTADO_ANUNCIO"));
		ps.setInt(1, 4);
		ps.setInt(2, e);
		status = ps.executeUpdate();
		if(status!=1) {
			System.out.println("Error al actualizar en la base de datos");
		}
	}
	
	/**
	 * Metodo que se encarga de borrar un Anuncio
	 * @param e Identificador del anuncio a borrar
	 * @throws SQLException
	 */
	public void borrarAnuncio(int e) throws SQLException {
		int status;
		
		PreparedStatement ps=con.prepareStatement(config.getProperty("BORRAR_ANUNCIO"));
		ps.setInt(1, e);
		status = ps.executeUpdate();
		if(status<1) {
			System.out.println("Error al actualizar en la base de datos");
		}
		
	}
	
	/**
	 * Metodo que se encarga de restaurar un anuncio
	 * @param e Identificador del anuncio a restaurar
	 * @throws SQLException
	 */
	public void restaurarAnuncio(int e) throws SQLException {
		int status;
		
		PreparedStatement ps=con.prepareStatement(config.getProperty("MODIFICAR_ESTADO_ANUNCIO"));
		ps.setInt(1, 1);
		ps.setInt(2, e);
		status = ps.executeUpdate();
		if(status<1) {
			System.out.println("Error al actualizar en la base de datos");
		}
	}
	
	
	
	/**
	 * Metodo que obtiene los anuncios que se le deben mostrar a un contacto
	 * @param email Email del contacto del que se quieren mostrar los anuncios que le corresponde
	 * @return ArrayList con los anuncios del contacto
	 * @throws SQLException
	 */
	public ArrayList<Anuncio> getAnunciosContacto(String email, int orden) throws SQLException{
        ResultSet rs;
        ArrayList<Integer>anuncios=new ArrayList<Integer>();
        ArrayList<Anuncio> anunciosList=new ArrayList<Anuncio>();
        	
        PreparedStatement ps = con.prepareStatement(config.getProperty("OBTENER_DESTINATARIOS_ORDENADOS"));
        ps.setString(1, email);
        rs=ps.executeQuery();
        while(rs.next()) {
        	anuncios.add(rs.getInt(2));
        }

        for(Integer b: anuncios) {
        	anunciosList.add(getAnuncioById(b));
        }
        if(orden==2) {
        	anunciosList= ordenarPropietario(anunciosList);
        }
        return anunciosList;
    }
	
	/**
	 * Metodo que se encarga de obtener un anuncio de la base de datos
	 * @param id Identificador del anuncio que se quiere obtener
	 * @return Anuncio buscado
	 * @throws SQLException
	 */
	public Anuncio getAnuncioById(int id) throws SQLException {
		PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_ANUNCIO_ID"));
		ResultSet rs;
		ArrayList<Anuncio> anuncios =new ArrayList<Anuncio>();
		ps.setInt(1, id);
		rs=ps.executeQuery();
		anuncios=almacenarAnuncios(rs);
		
		return anuncios.get(0);
	}
	
	
	/**
	 * Metodo que obtiene los destinatarios de un anuncio
	 * @param id Identificador del anuncio
	 * @return Destinatarios del anuncio
	 * @throws SQLException
	 */
	public ArrayList<String> getDestinatarios(Integer id) throws SQLException {
		PreparedStatement ps=con.prepareStatement(config.getProperty("OBTENER_DESTINATARIOS_ANUNCIO"));
		ResultSet rs;
		ArrayList<String> destinatarios= new ArrayList<String>();
		ps.setInt(1, id);
		rs=ps.executeQuery();
		
		while(rs.next()) {
			destinatarios.add(rs.getString(1));
			
		}
		return destinatarios;
		
	}
	
	/**
	 * Metodo que ordena una lista de anuncios segun el propietario
	 * @param toOrder Array List de Anuncios
	 * @return
	 */
	public ArrayList<Anuncio> ordenarPropietario(ArrayList<Anuncio> toOrder) {
		
		ArrayList<String> propietarios= new ArrayList<String>();
		ArrayList<Anuncio> ordenado= new ArrayList<Anuncio>();
		
		//Coge todos los nombres de los propietarios de todos los anuncios.
		
		for(Anuncio a: toOrder) {
			propietarios.add(a.getUsuario());
		}
		
		//Ordena los nombres.
		Collections.sort(propietarios);
		
		//Va comparando los nombres ordenados extraidos anteriormente con los que coge del vector principal hasta que sean iguales. Si coinciden pues los añade a otro vector y los elimina de los otros dos.
	
		while(toOrder.size()!=0) {
			for(int j=0; j<toOrder.size();) {
				
				if(propietarios.get(0).equals(toOrder.get(j).getUsuario())) {
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
	 * Metodo que se encarga de buscar un Anuncio por la fecha de publicacion del mismo
	 * @param fecha Fecha del anuncio a buscar
	 * @return Anuncio Anuncio Buscado
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	//Solo se puede con la fecha completa al tener que parsearse
	public ArrayList<Anuncio> buscarFecha(String fecha, int orden) throws SQLException, ParseException {
		
		Date dnuevafecha = new Date(0);
		
		try {
				
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsed = format.parse(fecha);
			dnuevafecha = new java.sql.Date(parsed.getTime());
		    	
		} catch (IllegalArgumentException e1) {
				
		}
		PreparedStatement ps = null;
		if(orden==1) {
			ps=con.prepareStatement(config.getProperty("OBTENER_ANUNCIOS_FECHA_ID"));
		}
		else {
			ps=con.prepareStatement(config.getProperty("OBTENER_ANUNCIOS_FECHA_AUTOR"));
		}
		ResultSet rs;
		ps.setDate(1, dnuevafecha);
		rs=ps.executeQuery();
		ArrayList<Anuncio> anuncios =new ArrayList<Anuncio>();
		anuncios=almacenarAnuncios(rs);
		
		return anuncios;
		
	}
	
	/**
	 * Metodo que busca un Anuncio por el nombre del propietario del mismo
	 * @param propietario
	 * @return Anuncio
	 * @throws SQLException 
	 */
	
	public 	ArrayList<Anuncio> buscarPropietario(String email, int orden) throws SQLException {
		PreparedStatement ps;
		if(orden==1) {
			ps=con.prepareStatement(config.getProperty("OBTENER_ANUNCIOS_AUTOR_ID"));
		}
		else {
			ps=con.prepareStatement(config.getProperty("OBTENER_ANUNCIOS_AUTOR_AUTOR"));
		}
		ResultSet rs;
		ps.setString(1, email+'%');
		rs=ps.executeQuery();
		ArrayList<Anuncio> anuncios =new ArrayList<Anuncio>();
		anuncios=almacenarAnuncios(rs);
        return anuncios;
     

	}
	
	/**
	 * Metodo que busca un Anuncio por el titulo
	 * @param titulo
	 * @return Anuncio
	 * @throws SQLException 
	 */
	
	public 	ArrayList<Anuncio> buscarTitulo(String titulos, int orden) throws SQLException {
		ArrayList<Anuncio> anuncios =new ArrayList<Anuncio>();
		PreparedStatement ps;
		if(orden==1) {
			ps=con.prepareStatement(config.getProperty("OBTENER_ANUNCIOS_TITULO_ID"));
		}
		else {
			ps=con.prepareStatement(config.getProperty("OBTENER_ANUNCIOS_TITULO_AUTOR"));
		}
		ResultSet rs;
		ps.setString(1, titulos+'%');
		rs=ps.executeQuery();
		anuncios=almacenarAnuncios(rs);
		return anuncios;
		
	  }
	
	/**
	 * Metodo que transforma los datos obtenidos de la base de datos a un ArrayList de anuncios.
	 * @param rs Datos obtenidos de la base de datos
	 * @return ArrayList de anuncios
	 * @throws SQLException 
	 */
	public ArrayList<Anuncio> almacenarAnuncios(ResultSet rs) throws SQLException{
		ArrayList<Anuncio> anuncios =new ArrayList<Anuncio>();
        ArrayList<Interes> interesesaux = new ArrayList<Interes>();
		int id= 0;
        String titulo= new String();
        String cuerpo= new String();
        String idautor= new String();
        int estadoid = 0;
        Date fechapublicacion= new Date(0);
       
        Timestamp fechainicio= new Timestamp(0);
        Timestamp fechafinal= new Timestamp(0);
        String tipo= new String();
        
        AnuncioTematico t= null;
        AnuncioFlash f = null;
        AnuncioIndividualizado i = null;
        AnuncioGeneral g = null;
			
		while(rs.next()) {
        	tipo = rs.getString(9);
        	if(tipo.equals("class es.uco.pw.practica3.business.AnuncioTematico")) {
        		
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
                 
                t = new AnuncioTematico(id,titulo,cuerpo,idautor,interesesaux, fechapublicacion, estado);
                anuncios.add(t);
                
			}
			else if(tipo.equals("class es.uco.pw.practica3.business.AnuncioFlash")) {
				
				id = rs.getInt(1);
                titulo = rs.getString(2);
                cuerpo = rs.getString(3);
                idautor = rs.getString(4);
                estadoid = rs.getInt(5);
                fechapublicacion = rs.getDate(6);
                fechainicio = rs.getTimestamp(7);
                fechafinal = rs.getTimestamp(8);
                tipo = rs.getString(9);
                

                Anuncio.Estados estado= null;
                for (Anuncio.Estados p : Anuncio.Estados.values()) {
                	if(p.getId()==estadoid) {
                		estado = p;
                	}
                }
               
                f = new AnuncioFlash(id, titulo,cuerpo,idautor,estado,fechapublicacion,fechainicio,fechafinal);
                anuncios.add(f);
			
			}
			else if(tipo.equals("class es.uco.pw.practica3.business.AnuncioIndividualizado")) {
				
				
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
               
                i = new AnuncioIndividualizado(id,titulo,cuerpo,idautor,fechapublicacion,estado);
                anuncios.add(i);
			
			}
			else if(tipo.equals("class es.uco.pw.practica3.business.AnuncioGeneral")) {
				
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
                
                g = new AnuncioGeneral(id,titulo,cuerpo,idautor,fechapublicacion,estado);
                anuncios.add(g);
			
			}
        }
        return anuncios;
	}
}