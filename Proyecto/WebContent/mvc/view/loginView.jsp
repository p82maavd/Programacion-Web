<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="../../include/errorpage.jsp"%>
<jsp:useBean  id="contactoBean" scope="session" class="es.uco.pw.practica3.display.ContactoBean"></jsp:useBean>

<!DOCTYPE html>
<html>
 <!-- Inicio de sesión del usuario con su respectiva comprobación de credenciales -->
	<head>
  		<meta charset="UTF-8">
    	<title>Login</title>
    	<link href="${pageContext.request.contextPath}/css/views.css" rel="stylesheet" type="text/css" >
    	<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jsLib1.js"></script>
	</head>
	<body>
		<%
		/* Posibles flujos:
		1) customerBean está logado (!= null && != "") -> Se redirige al index.jsp (no debería estar aquí pero hay que comprobarlo)
		2) customerBean no está logado:
			a) Hay parámetros en el request  -> procede del controlador /con mensaje 
			b) No hay parámetros en el request -> procede del controlador /sin mensaje
		*/

		String nextPage = "../controller/loginController.jsp";
		String messageNextPage = request.getParameter("message");
		String cont=request.getParameter("contador");
		Integer contador;
		if(cont==null||cont.equals("null")){
			cont="1";
		}
		else{
			contador=Integer.parseInt(cont);
			cont=contador.toString();
		}
		if (messageNextPage == null) messageNextPage = "";
		
		if (contactoBean != null && !contactoBean.getEmailUsuario().equals("")) {
			//No debería estar aquí -> flujo salta a index.jsp
			nextPage = "../../index.jsp";
		} 
		
		else {
		%>

		<div class="formcontainer">
			<form method="post" action="../controller/loginController.jsp" onSubmit="return validar3(this)">
   				<%= messageNextPage %><br/><br/>
      			<h1>Login</h1>
      			<hr/>
		        <label for="email"><strong>Email</strong></label>
		        <input type="text" name="email" value=""><br/>
		        <label for="psw"><strong>Password</strong></label>
				<input type="password" name="password" value="">
				<input type="hidden" name="contador" value=<%=cont%>>
				<button type="submit">Login</button>
    		</form>
    		
		    <form method="post" action="../controller/registerController.jsp">
		    	<button type="submit">Registrarse</button>
		    </form>
		    
   			<form method="post" action="../../intereses.jsp">
    			<button type="submit">Gestor de Intereses</button>
    		</form>
    
    	</div>
   
		<%
		}
		%>
	</body>
</html>