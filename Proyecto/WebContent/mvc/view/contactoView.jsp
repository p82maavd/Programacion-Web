<%@page import="es.uco.pw.practica3.business.Interes"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="../../include/errorpage.jsp"%>
<%@ page import ="java.text.SimpleDateFormat,es.uco.pw.practica3.business.Contacto, java.util.ArrayList, java.util.Date" %>
<jsp:useBean  id="contactoBean" scope="session" class="es.uco.pw.practica3.display.ContactoBean"></jsp:useBean>
<jsp:useBean  id="interesesBean" scope="session" class="es.uco.pw.practica3.display.InteresesBean"></jsp:useBean>
<jsp:useBean  id="contactoCompletoBean" scope="session" class="es.uco.pw.practica3.display.ContactoCompletoBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<!-- Registro de los datos de un nuevo usuario o de un usuario ya creado en el caso de que queramos modificarlo-->
	<head>
		<title>Registro</title>
		<link href="${pageContext.request.contextPath}/css/views.css" rel="stylesheet" type="text/css" >
		<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jsLib1.js"></script>
	</head>
	<body>
  
  	<%

	String nextPage = "../controller/registerController.jsp";

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
	java.util.Date date;
	if(contactoCompletoBean.getFechanacimientoUsuario()!=null){
		date = new Date(contactoCompletoBean.getFechanacimientoUsuario().getTime());
	}
	else{
		date= new Date();
	}
	 
	%>
	    <div class="main-block">  
			<form id= "formulario" method="post" action="../controller/registerController.jsp"  onSubmit="return validar1(this)">
				<%if (!contactoBean.getEmailUsuario().equals("")){
				      out.println("<h1>Actualizar Datos</h1>");
				  }
				  else{
				      out.println("<h1>Registro</h1>");
				  }
				%>
		    
		    	<fieldset>
		        	<hr/>
		        	<h3>Cuenta</h3>
		        
		        	<div  class="detallescuenta">
		          		<div><label>Email </label></div>
		          		<div><input type="text" name="email" id="email" value=<%=contactoCompletoBean.getEmailUsuario() %>></div>
		          		<script type="text/javascript">
		          			email();
		          		</script>
		          		<div><label>Contrase�a </label></div><div><input type="password" name="password" value=<%=contactoCompletoBean.getContrase�aUsuario() %>></div>
		        	</div>
		      	</fieldset>
		      	<fieldset>
		        
		        	<hr/>
		        	<h3>Datos Personales</h3>
		        
		        	<div  class="detallespersonales">
			            <div><label>Nombre </label><input type="text" name="nombre" value=<%=contactoCompletoBean.getNombreUsuario() %>></div>
			            <div><label>Apellidos </label><input type="text" name="apellidos" value=<%=contactoCompletoBean.getApellidosUsuario() %>></div>
			            <div><label>Fecha de nacimiento</label><input type="date" name="fechanacimiento" value=<%=contactoCompletoBean.getFechanacimientoUsuario() %>></div>
		        	</div>
		      	</fieldset>
		      	<fieldset>
		        	<hr/>
		        	<h3>Intereses</h3>
		        
		        	<%
		        	int cont=0;
			
					for(Interes i: interesesBean.getIntereses()){
						cont=0;
						if((contactoCompletoBean.getInteresesUsuario())!=null){
							for(Interes j:contactoCompletoBean.getInteresesUsuario()){
								if(i.getId()==j.getId()){
									out.println("<input type="+"checkbox" + " name="+"intereses"+" value="+i.getId()+" checked="+"checked"+">"+i.getInteres());
									cont++;
								}
							}
					  		if(cont==0){
								out.println("<input type="+"checkbox" + " name="+"intereses"+" value="+i.getId()+">"+i.getInteres());
							}
						}
						else{
							out.println("<input type="+"checkbox" + " name="+"intereses"+" value="+i.getId()+">"+i.getInteres());
						}
					}
					%>
		      	</fieldset>
		      	<input type = "hidden" name="accion" id="accion" value="">
		      	
		      	<button type="submit" >Submit</button>
		      	
		      	<%
		      	if (!contactoBean.getEmailUsuario().equals("")){
			   		out.println(" <button id="+"eliminarboton" +" type="+"button>Eliminar Usuario</button>");
			    }
			  	%>
			  	<script type="text/javascript">
			  		
	    			document.getElementById("eliminarboton").addEventListener("click", borrar);
	    			
				</script>
		      	
	    	</form>
	      
	    </div> 
	    
	
		<%
		session.removeAttribute("contactoCompletoBean");
		%>
	</body>
</html>