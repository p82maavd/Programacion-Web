<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="../../include/errorpage.jsp"%>
<%@ page import ="java.util.*,es.uco.pw.practica3.business.Contacto,es.uco.pw.practica3.business.Interes,es.uco.pw.practica3.data.ContactoDAO,es.uco.pw.practica3.data.InteresDAO,java.util.ArrayList,es.uco.pw.practica3.data.DAO, java.text.SimpleDateFormat, java.sql.Date" %>
<jsp:useBean  id="contactoBean" scope="session" class="es.uco.pw.practica3.display.ContactoBean"></jsp:useBean>
<jsp:useBean  id="interesesBean" scope="session" class="es.uco.pw.practica3.display.InteresesBean"></jsp:useBean>
<jsp:useBean  id="contactoCompletoBean" scope="session" class="es.uco.pw.practica3.display.ContactoCompletoBean"></jsp:useBean>
<%
/* Posibles flujos:
	1) contactoBean está logado (!= null && != "") -> Se redirige al index.jsp
		a) Hay parámetros en el request  -> procede de la vista 
		b) El parametro que hay es accion -> Se borra al usuario.
		c) No hay parámetros en el request -> procede de otra funcionalidad o index.jsp
	2) customerBean no está logado:
		a) Hay parámetros en el request  -> procede de la vista 
		b) No hay parámetros en el request -> procede de otra funcionalidad o index.jsp
	*/
//Caso 1: HAY un usuario registrado. Por lo que se modifica sus atributos.
String nextPage=new String();
DAO factory= DAO.getInstance(application.getInitParameter("direcciondb"),application.getInitParameter("usuario"),application.getInitParameter("password"));
ContactoDAO userDAO = factory.getContactoDAO();
InteresDAO interesesDAO = factory.getInteresDAO();

%>
<jsp:setProperty property="intereses" value="<%=interesesDAO.getIntereses()%>" name="interesesBean"/>
<%


nextPage ="../../index.jsp";

String nombreUsuario = request.getParameter("nombre");
String apellidos = request.getParameter("apellidos");
String fechaUsuario = request.getParameter("fechanacimiento");
String emailUsuario = request.getParameter("email");
String contraseñaUsuario= request.getParameter("password");
String accion=request.getParameter("accion");
String[] intereses=request.getParameterValues("intereses");
System.out.println(accion);
if(accion==null){
	accion="";
}
ContactoDAO contactos= factory.getContactoDAO();


//Caso 1. El usuario esta logado en la bd. Por lo que podra modificar sus datos.

if(!contactoBean.getEmailUsuario().equals("") && contactoBean.getEmailUsuario()!=null){
	
	String email = contactoBean.getEmailUsuario();
	Contacto a = contactos.buscarContactoEmail(email);
	//Caso 1.a. Tiene parametros por lo que viene de la vista.
	if (nombreUsuario!=null && accion.equals("") ){
		
		contactos.actualizarContactoNombre(a, nombreUsuario);
		contactos.actualizarContactoApellido(a, apellidos);
		contactos.actualizarContactoFecha(a, fechaUsuario);
		contactos.actualizarContactoInteres(a, intereses);
		
	}
	//Caso 1.b Recibe parametro accion. Por lo que es para borrar el usuario.
	else if(accion.equals("borrarUsuario")){
		contactos.borrarContacto(a);
		nextPage = "../controller/loginController.jsp";
	}
	//Caso 1.c No tiene parametros. Por lo que es la primera iteracion y tiene que ir a la vista.
	else{
		%>
		
		<jsp:setProperty property="emailUsuario" value="<%=a.getEmail()%>" name="contactoCompletoBean"/>
		<jsp:setProperty property="contraseñaUsuario" value="<%=a.getPassword()%>" name="contactoCompletoBean"/>
		<jsp:setProperty property="nombreUsuario" value="<%=a.getNombre()%>" name="contactoCompletoBean"/>
		<jsp:setProperty property="apellidosUsuario" value="<%=a.getApellidos()%>" name="contactoCompletoBean"/>
		<jsp:setProperty property="fechanacimientoUsuario" value="<%=a.getFechanacimiento()%>" name="contactoCompletoBean"/>
		<jsp:setProperty property="interesesUsuario" value="<%=a.getIntereses()%>" name="contactoCompletoBean"/>
		
		<% nextPage = "../view/contactoView.jsp";
		
	}
}

//Caso 2 No esta registrado el usuario. Creamos uno nuevo

if (contactoBean == null || contactoBean.getEmailUsuario().equals("")) {
	
	//Caso 2.a: Hay parámetros -> procede de la VISTA
	
	if (nombreUsuario != null ) {
		
		//Se accede a bases de datos para obtener el usuario
		
		java.sql.Date parsed = new java.sql.Date(0);
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		
		java.util.Date date = formato.parse(fechaUsuario);
		
    	parsed = Date.valueOf(formato.format(date));
		
		ArrayList<Interes> inter=new ArrayList<Interes>();
		
		
		if(intereses!=null){
			
			for (String s: intereses){
				
				for(Interes i: interesesDAO.getIntereses()){
					
					if (s.equals(i.getId().toString())){
						
						inter.add(i);
					}
				}
			}
		}
		
		Contacto user = new Contacto(emailUsuario,nombreUsuario,apellidos,parsed, contraseñaUsuario,inter);
		
		userDAO.crearContacto(user);
		
		//Aquí comprobamos que se haya creado correctamente el usuario.
		if (user != null) {
			// Usuario válido		
			%>
			<jsp:setProperty property="emailUsuario" value="<%=emailUsuario%>" name="contactoBean"/>
			<%
		}
	//Caso 2.b -> se debe ir a la vista por primera vez
	} else {
		
		nextPage = "../view/contactoView.jsp";	
		
	}
}
%>
<jsp:forward page="<%=nextPage%>"></jsp:forward>