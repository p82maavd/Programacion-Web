package es.uco.pw.practica3.business;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.uco.pw.practica3.data.AnuncioDAO;
import es.uco.pw.practica3.data.DAO;
import es.uco.pw.practica3.data.InteresDAO;
import es.uco.pw.practica3.display.ContactoBean;

/**
 * Servlet implementation class EstadosController
 */
@WebServlet("/EstadosController")
public class EstadosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EstadosController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DAO dao= DAO.getInstance(null, null, null);
		AnuncioDAO anuncios=null;
		InteresDAO intereses=null;
		Anuncio a=null;
		try {
			anuncios = dao.getAnuncioDAO();
			intereses = dao.getInteresDAO();
			a = anuncios.getAnuncioById(Integer.parseInt(request.getParameter("id")));
		} catch (ClassNotFoundException | IOException | SQLException e) {
			
			e.printStackTrace();
		}
		ContactoBean contactoBean=null;
		if( (contactoBean = (ContactoBean) request.getSession().getAttribute("contactoBean"))!=null) {
			if(contactoBean.getEmailUsuario().equals(a.getUsuario())) {
				if(request.getParameter("accion").equals("publicar")) {
					
					try {
						anuncios.publicarAnuncio(a);
						RequestDispatcher disp = request.getRequestDispatcher("anuncios.jsp");
						disp.forward(request, response); 
					} catch (ClassNotFoundException | IOException | SQLException e) {
						
						e.printStackTrace();
					}
				}
				else if(request.getParameter("accion").equals("archivar")) {
					
					try {
						anuncios.archivarAnuncio(Integer.parseInt(request.getParameter("id")));
						RequestDispatcher disp = request.getRequestDispatcher("anuncios.jsp");
						disp.forward(request, response); 
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				}
				else if(request.getParameter("accion").equals("recuperar")) {
					
					try {
						anuncios.restaurarAnuncio(Integer.parseInt(request.getParameter("id")));
						RequestDispatcher disp = request.getRequestDispatcher("anuncios.jsp");
						response.setCharacterEncoding("UTF-8");
						disp.forward(request, response); 
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				}
				else if(request.getParameter("accion").equals("eliminar")) {
					
					try {
						anuncios.borrarAnuncio(Integer.parseInt(request.getParameter("id")));
						RequestDispatcher disp = request.getRequestDispatcher("anuncios.jsp");
						disp.forward(request, response); 
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				}
			}
		}
		
		else if(request.getParameter("accion").equals("eliminarinteres")) {
			
			intereses.borrarInteres(Integer.parseInt(request.getParameter("id")));
			RequestDispatcher disp = request.getRequestDispatcher("intereses.jsp");
			disp.forward(request, response); 
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}
