package es.uco.pw.practica3.business;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.uco.pw.practica3.data.AnuncioDAO;
import es.uco.pw.practica3.data.DAO;
import es.uco.pw.practica3.data.InteresDAO;
import es.uco.pw.practica3.display.AnuncioBean;
import es.uco.pw.practica3.display.ContactoBean;
import es.uco.pw.practica3.display.InteresesBean;

/**
 * Servlet implementation class Anuncios
 */
@WebServlet("/AnunciosController")
public class AnunciosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnunciosController() {
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //Si recibe id es para modificarlo
    //Si recibe todo es para crearlo
/* Posibles flujos:
	
	1) doGet (Accede desde una pagina web):
		a) Si recibe id va a modificar el anuncio en anuncioView
		b) Si no recibe id, va a crear anuncio a anuncioView
	2) doPost (Accede desde un formulario):
		a) Si recibe id, modifica el anuncio
		b) Si no recibe id crea el anuncio
*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAO dao= DAO.getInstance(null, null, null);
		AnuncioDAO anuncios=null;
		
		try {
			anuncios = dao.getAnuncioDAO();
		} catch (ClassNotFoundException | IOException | SQLException e1) {
			
			e1.printStackTrace();
		}
		InteresDAO intereses=null;
		try {
			intereses = dao.getInteresDAO();
			
		} catch (ClassNotFoundException | IOException | SQLException e1) {
			
			e1.printStackTrace();
		}
		
		InteresesBean interesesBean=null;
		if(request.getSession().getAttribute("interesesBean")==null) {
			interesesBean= new InteresesBean();
			try {
				interesesBean.setIntereses(intereses.getIntereses());
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			request.getSession().setAttribute("interesesBean", interesesBean);
		}
		
		String id=null;
		if(request.getParameter("id")==null) {
			id=null;
		
		}
		else {
			id=request.getParameter("id");
			
			if(id.equals("null")) {
				id=null;
			}
		}
		
		//Quiere crear un anuncio.
		if((id==null)){
				//Tiene que ir a la vista por primera vez
			response.sendRedirect("mvc/view/anuncioView.jsp"+"?tipo="+request.getParameter("tipo"));			
						
		}
					
		//1. Quiere editar un anuncio.
		
		else if(id!=null) {
			System.out.println("Entramos en 1");
			//1.2 Tiene los datos de la edicion
			
			System.out.println(request.getSession().getAttribute("anuncioBean"));
			
			System.out.println("Entramos en 1.1");
			Anuncio a=null;
			try {
				a = anuncios.getAnuncioById(Integer.parseInt(id));
			} catch (NumberFormatException | SQLException e) {
					
				e.printStackTrace();
			}
				
			AnuncioBean anuncioBean= new AnuncioBean();
			anuncioBean.setId(a.getId());
			anuncioBean.setAutor(a.getUsuario());
			anuncioBean.setTitulo(a.getTitulo());
			anuncioBean.setCuerpo(a.getCuerpo());
				
			if(a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioFlash")) {
				
				String fechainicio=((AnuncioFlash)a).getFechaInicio().toString().substring(0, 16);
				String fechafinal=((AnuncioFlash)a).getFechaFinal().toString().substring(0, 16);
				
				
				anuncioBean.setFechainicio(fechainicio);
				anuncioBean.setFechafinal(fechafinal);
			}
			else if(a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioTematico")){
				try {
					anuncioBean.setIntereses(intereses.getInteresesAnuncio(a.getId()));
				} catch (SQLException e) {
						
					e.printStackTrace();
				}
			}
			else if(a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioIndividualizado")){
				try {
					anuncioBean.setDestinatarios(anuncios.getDestinatarios(a.getId()));
				} catch (SQLException e) {
						
					e.printStackTrace();
				}
			}
				
				//Añadir fechas, intereses, destinatarios etc.
			request.getSession().setAttribute("anuncioBean", anuncioBean);
				
			
			response.sendRedirect("mvc/view/anuncioView.jsp"+"?tipo="+a.getClass().toString());
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAO dao= DAO.getInstance(null, null, null);
		AnuncioDAO anuncios=null;
		
		response.setCharacterEncoding("UTF-8");
		ConcreteFactory anuncioFactory = new ConcreteFactory();
		Date fecha= new Date(System.currentTimeMillis());
		
		try {
			anuncios = dao.getAnuncioDAO();
		} catch (ClassNotFoundException | IOException | SQLException e1) {
			
			e1.printStackTrace();
		}
		InteresDAO intereses=null;
		try {
			intereses = dao.getInteresDAO();
			
		} catch (ClassNotFoundException | IOException | SQLException e1) {
			
			e1.printStackTrace();
		}
		ContactoBean contactoBean = (ContactoBean) request.getSession().getAttribute("contactoBean");
		
		InteresesBean interesesBean=null;
		if(request.getSession().getAttribute("interesesBean")==null) {
			interesesBean= new InteresesBean();
			try {
				interesesBean.setIntereses(intereses.getIntereses());
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			request.getSession().setAttribute("interesesBean", interesesBean);
		}
		
		String id=null;
		if(request.getParameter("id")==null) {
			id=null;
			System.out.println("Es tipo null");
		
		}
		else {
			id=request.getParameter("id");
			
			if(id.equals("null")) {
				id=null;
			}
		}
		
		String titulo = request.getParameter("titulo");
		String cuerpo = request.getParameter("cuerpo");
		String fechainicio= request.getParameter("fechainicio");
		String fechafin= request.getParameter("fechafin");
		String[] interesess= request.getParameterValues("intereses");
		String[] destinatarios= request.getParameterValues("destinatarios");
		
		fechainicio=fechainicio+":00";
		fechafin=fechafin+":00";
		
		//Crear
		if (id==null) {
			System.out.println("Entramos en 2.1");
			
			ArrayList<Interes> interesesList = new ArrayList<Interes>();
			        
			try {
			  	if(interesess!=null) {
			  		for(Interes i: intereses.getIntereses()) {
			  			for(String s: interesess) {
							if(Integer.parseInt(s)==i.getId()) {
								interesesList.add(i);
							}
						}
					}
			    }
			} catch (NumberFormatException | SQLException e) {
						
				e.printStackTrace();
			}
			        
	        System.out.println((request.getParameter("tipo")));
			       
	        if(request.getParameter("tipo").equals("class es.uco.pw.practica3.business.AnuncioFlash")){
		       	
	        	try {
					anuncios.guardarAnuncio(anuncioFactory.createAnuncioFlash(contactoBean.getEmailUsuario(), titulo, cuerpo, fecha, Timestamp.valueOf(fechainicio), Timestamp.valueOf(fechafin)),null);
				} catch (ClassNotFoundException | IOException e) {
							
					e.printStackTrace();
				}
	        }
			else if(request.getParameter("tipo").equals("class es.uco.pw.practica3.business.AnuncioTematico")){
				try {
					anuncios.guardarAnuncio(anuncioFactory.createAnuncioTematico(contactoBean.getEmailUsuario(), titulo, cuerpo, interesesList, fecha),null);
				} catch (ClassNotFoundException | IOException e) {
							
					e.printStackTrace();
				}
	        }
			        
			else  if(request.getParameter("tipo").equals("class es.uco.pw.practica3.business.AnuncioGeneral")){
				
		    	try {
		    		anuncios.guardarAnuncio(anuncioFactory.createAnuncioGeneral(contactoBean.getEmailUsuario(), titulo, cuerpo,fecha),null);
				} catch (ClassNotFoundException | IOException e) {
			
					e.printStackTrace();
				}
			}
			        
			else {
				
			   	try {
			   		anuncios.guardarAnuncio(anuncioFactory.createAnuncioIndividualizado(contactoBean.getEmailUsuario(), titulo, cuerpo,fecha),destinatarios);
				} catch (ClassNotFoundException | IOException e) {
							
					e.printStackTrace();
				}
					        
			}
			RequestDispatcher disp = request.getRequestDispatcher("anuncios.jsp");
			disp.include(request, response); 
			        
			        
			        
		}
		//Editar
		else if(id!=null) {
			
			if(request.getParameter("titulo")!=null) {
				
				Anuncio a=null;
				try {
					a = anuncios.getAnuncioById(Integer.parseInt(id));
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				try {
					anuncios.modificarTitulo(a,request.getParameter("titulo"));
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				try {
					anuncios.modificarCuerpo(a,request.getParameter("cuerpo"));
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				if(a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioIndividualizado")) {
					try {
						anuncios.modificarDestinatarios(a,request.getParameterValues("destinatarios"));
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				}
				else if(a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioTematico")) {
					try {
						anuncios.modificarIntereses(a,request.getParameterValues("intereses"));
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				}
				
				else if(a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioFlash")) {
					try {
						anuncios.modificarFechaInicio(a,fechainicio);
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					try {
						anuncios.modificarFechaFin(a,fechafin);
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				}
				request.getSession().removeAttribute("anuncioBean");
				RequestDispatcher disp = request.getRequestDispatcher("anuncios.jsp");
				disp.include(request, response); 
				
				request.getSession().removeAttribute("anuncioBean");
			}
	}
				
	}
}
