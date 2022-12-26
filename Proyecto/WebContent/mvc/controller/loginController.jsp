<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="../../include/errorpage.jsp"%>
<%@ page import ="es.uco.pw.practica3.business.Contacto,es.uco.pw.practica3.business.Configuracion,es.uco.pw.practica3.data.InteresDAO,es.uco.pw.practica3.display.InteresesBean,es.uco.pw.practica3.data.ContactoDAO,es.uco.pw.practica3.data.DAO,java.util.ArrayList,es.uco.pw.practica3.business.Interes" %>
<jsp:useBean  id="contactoBean" scope="session" class="es.uco.pw.practica3.display.ContactoBean"></jsp:useBean>
<%
/* Posibles flujos:
	
	1) customerBean no está logado:
		a) Hay parámetros en el request  -> procede de la vista 
		b) No hay parámetros en el request -> procede de otra funcionalidad o index.jsp
	2) customerBean está logado (!= null && != "") -> Se redirige al index.jsp
*/
response.setCharacterEncoding("UTF-8");
String nextPage="../../index.jsp";
String mensajeNextPage=new String();
String cont=request.getParameter("contador");
Integer contador=0;

//Caso 1
if (contactoBean == null  || contactoBean.getEmailUsuario().equals("")) {
	String emailUsuario = request.getParameter("email");
	String contraseñaUsuario = request.getParameter("password");

	//Caso 1.a: Hay parámetros -> procede de la VISTA
	if (emailUsuario != null) {
		//Se accede a bases de datos para obtener el usuario
		
		DAO dao= DAO.getInstance(application.getInitParameter("direcciondb"),application.getInitParameter("usuario"),application.getInitParameter("password"));
		
		ContactoDAO userDAO = dao.getContactoDAO();
		
		Contacto user = userDAO.buscarContactoEmail(emailUsuario);
		
		//Se realizan todas las comprobaciones necesarias del dominio
		//Comprobamos que el usuario este registrado
		if (user != null && contraseñaUsuario.equals(user.getPassword())) {
			// Usuario válido
			nextPage="../../index.jsp";
			
%>
<jsp:setProperty property="emailUsuario" value="<%=emailUsuario%>" name="contactoBean"/>
<jsp:setProperty property="contraseñaUsuario" value="<%=contraseñaUsuario%>" name="contactoBean"/>
<%
		} 
		
		else {
			// Usuario no válido
			
			if(cont==null||cont.equals("null")){
				contador=0;
			}
			else{
				contador=Integer.parseInt(cont);
			}
			nextPage = "../view/loginView.jsp";
			contador=contador+1;
			mensajeNextPage = "El usuario que ha indicado no existe o la password es incorrecta. Intentos Gastados: "+contador;
			if(contador>2){
				response.sendRedirect("http://www.uco.es");
			}
			
			
		}
	//Caso 1.b -> se debe ir a la vista por primera vez
	} else {
		
		//Cargamos lo necesario para que carge la pagina web.
		DAO dao=DAO.getInstance(application.getInitParameter("direcciondb"),application.getInitParameter("usuario"),application.getInitParameter("password"));

		java.io.InputStream myIO = application.getResourceAsStream(application.getInitParameter("sentencias"));

		Configuracion configu = Configuracion.getInstance(myIO);
		
		InteresDAO intereses=dao.getInteresDAO();
	    InteresesBean interesesBean=null;
	  
		interesesBean= new InteresesBean();
		try {
			interesesBean.setIntereses(intereses.getIntereses());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		request.getSession().setAttribute("interesesBean", interesesBean);
		
		nextPage = "../view/loginView.jsp";		
	}
}
else{	
	//Caso 2: Desconecta al usuario
	
	mensajeNextPage = "Usuario desconectado correctamente";
	nextPage="../../index.jsp";
	
	request.getSession().removeAttribute("contactoBean");
	request.getSession().removeAttribute("interesesBean");
}


%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=mensajeNextPage%>" name="message"/>
	<jsp:param value="<%=contador.toString()%>" name="contador"/>
</jsp:forward>