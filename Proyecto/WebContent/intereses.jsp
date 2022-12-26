<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import ="es.uco.pw.practica3.business.Interes,es.uco.pw.practica3.display.InteresesBean"%>

<!DOCTYPE html>
<html>
<!-- CRUD de los intereses -->
	<head>
		<meta charset="UTF-8">
		<title>Intereses</title>
		<link href="${pageContext.request.contextPath}/css/pages.css" rel="stylesheet" type="text/css" >
	</head>
	<body>
		
		<div id="menu">
			<ul>
				<li id="apartado"><a href="${pageContext.request.contextPath}/interesesController">Crear Interes</a></li>
				<li id="apartado"><a href="${pageContext.request.contextPath}/mvc/controller/loginController.jsp">Volver al Login</a></li>
			</ul>
		</div>
		
		<table>
			<tr>
				<th scope="col">Id</th>
		    	<th scope="col">Interes</th>
		    	<th scope="col">Eliminar</th>
		  	</tr>
		
		  	<%
		   
		  	InteresesBean interesesBean = (InteresesBean)request.getSession().getAttribute("interesesBean");
		  	for(Interes a: interesesBean.getIntereses()){
				out.println("<tr>");
			    out.println("<td>"+a.getId()+"</td>");
			    out.println("<td>"+a.getInteres()+"</td>");
			    out.println("<td>");
			    out.println("<a title="+"Eliminar Anuncio"+ " href=" + request.getContextPath()+"/estadosController?accion=eliminarinteres&id="+a.getId()+"><img src=" + "img/eliminar.png"+ " alt="+"Eliminar" + " width="+"36" +" height="+"36" + "></a>");
			    out.println("</td>");
			    out.println("</tr>");
		    }
		  
		  	%>
		
		</table>
	
	</body>
</html>