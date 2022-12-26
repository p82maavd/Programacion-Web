package es.uco.pw.practica3.business;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.uco.pw.practica3.business.Anuncio.Estados;
import es.uco.pw.practica3.data.AnuncioDAO;
import es.uco.pw.practica3.data.DAO;
import es.uco.pw.practica3.data.InteresDAO;
import es.uco.pw.practica3.display.ContactoBean;

/**
 * Servlet implementation class GetAnunciosController
 */
@WebServlet("/GetAnunciosController")
public class GetAnunciosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAnunciosController() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    //Desde Index, se llama a este metodo y dependiendo si el filtro de busqueda tiene valor o no muestra el tablon o busca un anuncio
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		DAO dao= DAO.getInstance(null, null, null);
		AnuncioDAO anuncios=null;
		
		try {
			anuncios = dao.getAnuncioDAO();
		} catch (ClassNotFoundException | IOException | SQLException e) {
			
			e.printStackTrace();
		}
		
		InteresDAO intereses=null;
		try {
			intereses = dao.getInteresDAO();
		} catch (ClassNotFoundException | IOException | SQLException e2) {
			
			e2.printStackTrace();
		}
		int orden = 0;
		if(request.getParameter("orden")!=null) {
			orden = Integer.parseInt(request.getParameter("orden"));
		}
		//1. Fecha(ID) 2.Propietario
		
		ContactoBean contactoBean = (ContactoBean) request.getSession().getAttribute("contactoBean");
		if(request.getParameter("accion").equals("tablon")) {
			
			try {
				for(Anuncio a: anuncios.getAnunciosContacto(contactoBean.getEmailUsuario(),orden)) {
					
					//Si el email del que llama al tablon esta en la lista de destinatarios y el anuncio esta publicado o en espera se imprime.
	
					if( (a.getEstado().getId()==2) || (a.getEstado().getId()==3) ){
							
						if(a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioGeneral")) {
								
								out.println("<div class="+"anuncio"+">");
					        	out.println("<h2>"+a.getTitulo()+"</h2>");
					        	out.println("<p>"+a.getCuerpo()+"</p>");
					        	out.println("<span class="+"clase"+">"+a.getClass().toString()+"</span>");
					        	out.println("<div>"+a.getFecha().toString()+"</div>");
					        	out.println("<div>"+a.getUsuario()+"</div>");
					        	out.println("<hr/>");
					        	out.println("</div>");
								
						}
							
						else if(a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioTematico")) {
							
							ArrayList<Interes> interesesAnuncio= intereses.getInteresesAnuncio(a.getId());
								
								out.println("<div class="+"anuncio"+">");
					        	out.println("<h2>"+a.getTitulo()+"</h2>");
					        	out.println("<p>"+a.getCuerpo()+"</p>");
					        	out.println("<div class="+"clase"+">"+a.getClass().toString()+"</div>");
					        	out.println("<div>"+a.getFecha().toString()+"</div>");
					        	out.println("<div class="+"intereses"+">"+"Intereses: ");
					        	for (int i = 0; i < interesesAnuncio.size(); i++) {
									out.println("<div style=\"display:none;\">"+interesesAnuncio.get(i).getId()+"</div>");
									out.println("<div>"+interesesAnuncio.get(i).getInteres()+"</div>");
					        	}
					        	out.println("</div>");
					        	out.println("<div>"+a.getUsuario()+"</div>");
					        	out.println("<hr/>");
					        	out.println("</div>");
						}
							
						else if(a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioIndividualizado")) {
								out.println("<div class="+"anuncio"+">");
					        	out.println("<h2>"+a.getTitulo()+"</h2>");
					        	out.println("<p>"+a.getCuerpo()+"</p>");
					        	out.println("<div class="+"clase"+">"+a.getClass().toString()+"</div>");
					        	out.println("<div>"+a.getFecha().toString()+"</div>");
					        	out.println("<div>"+a.getUsuario()+"</div>");
					        	out.println("<hr/>");
					        	out.println("</div>");
						}
							
						
						else {
							
							Timestamp fechaActual=new Timestamp(System.currentTimeMillis());
							
							//Si esta en una fecha entre inicio y fin te lo publica
							
							if( (fechaActual.compareTo(((AnuncioFlash) a).getFechaInicio())>0) & (fechaActual.compareTo(((AnuncioFlash) a).getFechaFinal())<0) ){
								
								try {
									anuncios.publicarAnuncio(a);
								} catch (ClassNotFoundException | IOException | SQLException e) {
									
									e.printStackTrace();
								}
								//Esto es para que el print muestre que esta publicado, aunque en la base de datos ya lo este en el dto no.
								Estados estado=Estados.Publicado;
								a.setEstado(estado);
								out.println("<div class="+"anuncio"+">");
					        	out.println("<h2>"+a.getTitulo()+"</h2>");
					        	out.println("<p>"+a.getCuerpo()+"</p>");
					        	out.println("<div class="+"clase"+">"+a.getClass().toString()+"</div>");
					        	out.println("<div>"+a.getFecha().toString()+"</div>");
					        	out.println("<div>"+a.getUsuario()+"</div>");
					        	out.println("<hr/>");
					        	out.println("</div>");
								
							}
							
							else {
								
								//Si se ha pasado la fecha final se archiva
								
								if(fechaActual.compareTo(((AnuncioFlash) a).getFechaFinal())>0) {
									try {
										anuncios.archivarAnuncio(a.getId());
									} catch (SQLException e) {
										
										e.printStackTrace();
									}
								}
								//Se pone en espera si no ha llegado la fecha inicio
								else if(fechaActual.compareTo(((AnuncioFlash) a).getFechaInicio())<0){
									try {
										anuncios.publicarAnuncio(a);
									} catch (ClassNotFoundException | IOException | SQLException e) {
										
										e.printStackTrace();
									}
								}
								
							}
							
						}
							
					}
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		
		else if(request.getParameter("accion").equals("busqueda")) {
			response.setContentType("text/html");
			
			if(request.getParameter("filtro").equals("autor")) {
				
				try {
							
					for(Anuncio a: anuncios.buscarPropietario(request.getParameter("valor"),orden)) {
						
						if(a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioTematico")) {
							
							ArrayList<Interes> interesesAnuncio= intereses.getInteresesAnuncio(a.getId());
							
							out.println("<div class="+"anuncio"+">");
				        	out.println("<h2>"+a.getTitulo()+"</h2>");
				        	out.println("<p>"+a.getCuerpo()+"</p>");
				        	out.println("<div class="+"clase"+">"+a.getClass().toString()+"</div>");
				        	out.println("<div>"+a.getFecha().toString()+"</div>");
				        	out.println("<div class="+"intereses"+">"+"Intereses: ");
				        	for (int i = 0; i < interesesAnuncio.size(); i++) {
								out.println("<div style=\"display:none;\">"+interesesAnuncio.get(i).getId()+"</div>");
								out.println("<div>"+interesesAnuncio.get(i).getInteres()+"</div>");
				        	}
				        	out.println("</div>");
				        	out.println("<div>"+"Estado: "+a.getEstado().getEstados()+"</div>");
				        	out.println("<div>"+"Autor: "+a.getUsuario()+"</div>");
				        	
				        	out.println("<hr/>");
				        	out.println("</div>");
						}
						
						else {
							out.println("<div class="+"anuncio"+">");
				        	out.println("<h2>"+a.getTitulo()+"</h2>");
				        	out.println("<p>"+a.getCuerpo()+"</p>");
				        	out.println("<div class="+"clase"+">"+a.getClass().toString()+"</div>");
				        	out.println("<div>"+a.getFecha().toString()+"</div>");
				        	out.println("<div>"+"Estado: "+a.getEstado().getEstados()+"</div>");
				        	out.println("<div>"+"Autor: "+a.getUsuario()+"</div>");
				        	out.println("<hr/>");
				        	out.println("</div>");
						}
						
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			
			else if(request.getParameter("filtro").equals("fecha")) {
				
				try {
					for(Anuncio a: anuncios.buscarFecha(request.getParameter("valor"),orden)) {
						
						if(a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioTematico")) {
							
							ArrayList<Interes> interesesAnuncio= intereses.getInteresesAnuncio(a.getId());
							
							out.println("<div class="+"anuncio"+">");
					    	out.println("<h2>"+a.getTitulo()+"</h2>");
					    	out.println("<p>"+a.getCuerpo()+"</p>");
					    	out.println("<div class="+"clase"+">"+a.getClass().toString()+"</div>");
					    	out.println("<div>"+a.getFecha().toString()+"</div>");
					    	out.println("<div class="+"intereses"+">"+"Intereses: ");
				        	for (int i = 0; i < interesesAnuncio.size(); i++) {
								out.println("<div style=\"display:none;\">"+interesesAnuncio.get(i).getId()+"</div>");
								out.println("<div>"+interesesAnuncio.get(i).getInteres()+"</div>");
				        	}
				        	out.println("</div>");
					    	out.println("<div>"+"Estado: "+a.getEstado().getEstados()+"</div>");
					    	out.println("<div>"+"Autor: "+a.getUsuario()+"</div>");
					    	
					    	out.println("<hr/>");
					    	out.println("</div>");
						}
						
						else {
							out.println("<div class="+"anuncio"+">");
					    	out.println("<h2>"+a.getTitulo()+"</h2>");
					    	out.println("<p>"+a.getCuerpo()+"</p>");
					    	out.println("<div class="+"clase"+">"+a.getClass().toString()+"</div>");
					    	out.println("<div>"+a.getFecha().toString()+"</div>");
					    	out.println("<div>"+"Estado: "+a.getEstado().getEstados()+"</div>");
					    	out.println("<div>"+"Autor: "+a.getUsuario()+"</div>");
					    	
					    	out.println("<hr/>");
					    	out.println("</div>");
						}
						
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			
			else if(request.getParameter("filtro").equals("titulo")) {
				
				try {
					for(Anuncio a: anuncios.buscarTitulo(request.getParameter("valor"),orden)) {
						
						if(a.getClass().toString().equals("class es.uco.pw.practica3.business.AnuncioTematico")) {
							
							out.println("<div class="+"anuncio"+">");
					    	out.println("<h2>"+a.getTitulo()+"</h2>");
					    	out.println("<p>"+a.getCuerpo()+"</p>");
					    	out.println("<div class="+"clase"+">"+a.getClass().toString()+"</div>");
					    	out.println("<div>"+a.getFecha().toString()+"</div>");
					    	ArrayList<Interes> interesesAnuncio= intereses.getInteresesAnuncio(a.getId());
					    	for (int i = 0; i < interesesAnuncio.size(); i++) {
								out.println("<div style=\"display:none;\">"+interesesAnuncio.get(i).getId()+"</div>");
								out.println("<div>"+interesesAnuncio.get(i).getInteres()+"</div>");
				        	}
					    	out.println("</div>");
					    	out.println("<div>"+"Estado: "+a.getEstado().getEstados()+"</div>");
					    	out.println("<div>"+"Autor: "+a.getUsuario()+"</div>");
					    	
					    	out.println("<hr/>");
					    	out.println("</div>");
						}
						
						else {
							out.println("<div class="+"anuncio"+">");
					    	out.println("<h2>"+a.getTitulo()+"</h2>");
					    	out.println("<p>"+a.getCuerpo()+"</p>");
					    	out.println("<div class="+"clase"+">"+a.getClass().toString()+"</div>");
					    	out.println("<div>"+a.getFecha().toString()+"</div>");
					    	out.println("<div>"+"Estado: "+a.getEstado().getEstados()+"</div>");
					    	out.println("<div>"+"Autor: "+a.getUsuario()+"</div>");
					    	out.println("<hr/>");
					    	out.println("</div>");
						}
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
			}
		}
		//Mis anuncios
		else if(request.getParameter("accion").equals("misanuncios")){
			
			try {
				out.println("<table>");
				
				out.println("<tr>");
				out.println("<th scope="+"col"+">Id"+"</th>");
				out.println("<th scope="+"col"+">Titulo del anuncio"+"</th>");
				out.println("<th scope="+"col"+">Editar"+"</th>");
				out.println("<th scope="+"col"+">Estado"+"</th>");
				out.println("<th scope="+"col"+">Acciones"+"</th>");
				out.println("</tr>");

				String direccion=request.getContextPath();
				for(Anuncio a: anuncios.getMisAnuncios(contactoBean.getEmailUsuario())) {
					  out.println("<tr>");
					  out.println("<td>"+a.getId()+"</td>");
					  out.println("<td>"+a.getTitulo()+"</td>");
					  
					  out.println("<td><a title="+"Editar Anuncios"+ " href=" +direccion+ "/anunciosController?id="+a.getId() + "><img src=" + "img/editar.jpg"+ " alt="+"Editar" + " width="+"36" +" height="+"36" + "></a></td>");
					  out.println("<td>"+a.getEstado()+"</td>");
					  out.println("<td>");
					  if(a.getEstado().getId()==1){
						  out.println("<a title="+"Publicar Anuncio"+ " href=" +direccion+ "/estadosController?accion=publicar&id="+a.getId()+"><img src=" + "img/publicar2.png"+ " alt="+"Publicar" + " width="+"36" +" height="+"36" + "></a>");
						  
					  }
					  if(a.getEstado().getId()==3){
						  out.println("<a title="+"Archivar Anuncio"+ " href=" +direccion+ "/estadosController?accion=archivar&id="+a.getId()+"><img src=" + "img/archivar.png"+ " alt="+"Archivar" + " width="+"36" +" height="+"36" + "></a>");
							  
					  }
					  if(a.getEstado().getId()==4){
						  out.println("<a title="+"Restaurar Anuncio"+ " href=" +direccion+ "/estadosController?accion=recuperar&id="+a.getId() + "><img src=" + "img/restaurar.png"+ " alt="+"Restaurar" + " width="+"36" +" height="+"36" + "></a>");
						  out.println("<a title="+"Eliminar Anuncio"+ " href=" +direccion+ "/estadosController?accion=eliminar&id="+a.getId()+"><img src=" + "img/eliminar.png"+ " alt="+"Eliminar" + " width="+"36" +" height="+"36" + "></a>");
						  
					  }
					  out.println("</td>");
					  out.println("</tr>");
				}
				out.println("</table>");
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	

}
