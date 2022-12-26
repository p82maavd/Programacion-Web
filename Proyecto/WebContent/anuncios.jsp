<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import ="es.uco.pw.practica3.business.Configuracion,es.uco.pw.practica3.business.GetAnunciosController,es.uco.pw.practica3.data.DAO,es.uco.pw.practica3.data.AnuncioDAO, es.uco.pw.practica3.business.Anuncio"%>
<jsp:useBean  id="contactoBean" scope="session" class="es.uco.pw.practica3.display.ContactoBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<!-- Lista de anuncios creados por el usuario que ha iniciado sesion, ademas de un menu con diferentes opciones como la de crear un anuncio, volver al tablon o desconectarse -->
	<head>
		<meta charset="UTF-8">
		<title>Tablon</title>
		<link href="${pageContext.request.contextPath}/css/pages.css" rel="stylesheet" type="text/css" >
	</head>
	<body>
		<div id="menu">
			<ul>
				<li id="apartado"><a href="#">Crear Anuncio</a>
					<ul id="submenu">
				    	<li><a href="${pageContext.request.contextPath}/anunciosController?tipo=class es.uco.pw.practica3.business.AnuncioTematico">Anuncio Tematico</a></li>
				    	<li><a href="${pageContext.request.contextPath}/anunciosController?tipo=class es.uco.pw.practica3.business.AnuncioGeneral">Anuncio General</a></li>
				    	<li><a href="${pageContext.request.contextPath}/anunciosController?tipo=class es.uco.pw.practica3.business.AnuncioFlash">Anuncio Flash</a></li>
				    	<li><a href="${pageContext.request.contextPath}/anunciosController?tipo=class es.uco.pw.practica3.business.AnuncioIndividualizado">Anuncio Individualizado</a></li>
				    </ul>
				<li id="apartado"><a href="${pageContext.request.contextPath}/index.jsp">Volver al Tablon</a></li>
				<li id="apartado"><a href="${pageContext.request.contextPath}/mvc/controller/loginController.jsp">Desconectarse</a></li>
			</ul>
			
		</div>

		<script>
			var xhr = new XMLHttpRequest();
			let contextPath='${pageContext.request.contextPath}';
			
			xhr.open('GET', contextPath.concat('/getAnunciosController?accion=misanuncios'));
			xhr.send(null);
			var table = document.createElement("table");
			xhr.onreadystatechange = function () {
			    /* readyState = 4 indica que ha terminado la carga del documento */
			    if (xhr.readyState === 4) {
			      /* Un c�digo HTTP 200 indica que todo fue bien */
			    	if (xhr.status === 200) {
			    		document.body.innerHTML += xhr.responseText;
			        }
			    	else{
			    		alert("Tiempo de inactividad superado. Vuelva a logearse. En caso de que no pueda conectarse reinicie la aplicacion");
			    		window.location = context+"/mvc/controller/loginController.jsp";
			    	}
			    }
			};
		</script>
	</body>
</html>