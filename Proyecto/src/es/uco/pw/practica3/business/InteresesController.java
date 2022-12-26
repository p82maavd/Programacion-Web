package es.uco.pw.practica3.business;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.uco.pw.practica3.data.DAO;
import es.uco.pw.practica3.data.InteresDAO;

/**
 * Servlet implementation class InteresesController
 */
@WebServlet("/InteresesController")
public class InteresesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InteresesController() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect("mvc/view/interesesView.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String interes = request.getParameter("interes");
		System.out.println(interes);
		DAO dao = DAO.getInstance(null, null, null);
		InteresDAO intereses= null;
		try {
			intereses = dao.getInteresDAO();
		} catch (ClassNotFoundException | IOException | SQLException e) {
			
			e.printStackTrace();
		}
		if(interes!= null && !(interes.equals("")) ) {
			intereses.crearInteres(interes);
		}
		response.sendRedirect("index.jsp");
		
	}

}
