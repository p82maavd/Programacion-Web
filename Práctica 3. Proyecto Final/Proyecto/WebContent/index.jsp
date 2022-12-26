<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import ="es.uco.pw.practica3.display.InteresesBean,es.uco.pw.practica3.business.Interes, es.uco.pw.practica3.business.Anuncio"%>
<jsp:useBean id="contactoBean" scope="session" class="es.uco.pw.practica3.display.ContactoBean"></jsp:useBean>


<!DOCTYPE html>
<html>
 <!-- Index de la pagina, muestra el tablon de anuncios (despu�s de iniciar sesion) con sus respectivos filtros y busqueda -->
	<head>
		<meta charset="UTF-8">
		<title>Gestor de Usuarios</title>
		<link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet" type="text/css" >
		<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jsLib2.js"></script>
	</head>
	<body>

		<%
		String direccion = request.getContextPath();
		
		if (contactoBean.getEmailUsuario().equals("") || contactoBean==null ) {
		
			response.sendRedirect(direccion+"/mvc/controller/loginController.jsp");
		} 
		
		else{
		
		%>
	
		<div id="menu">
			<ul>
				<li id="apartado"><a href="${pageContext.request.contextPath}/mvc/controller/registerController.jsp">Modificar Datos</a></li>
				<li id="apartado"><a href="${pageContext.request.contextPath}/anuncios.jsp">Mis Anuncios</a></li>
				<li id="apartado"><a href="${pageContext.request.contextPath}/mvc/controller/loginController.jsp">Desconectarse</a></li>
			</ul>
		</div>
		<div class = "bloque">
			<div class="espacioimagen">
				<img id="uco" src="${pageContext.request.contextPath}/img/LogoUCO.png">
			</div>
			<div class="zonabusqueda">
				
				<div id="barrabusqueda">
					
					<input type="search" id="busqueda" placeholder=" Busqueda">
					<select name="filtro" id="filtrobusqueda">
						<option value="titulo">Titulo</option>
					  	<option value="fecha">Fecha(dd/mm/yyyy)</option>
					  	<option value="autor">Autor</option>
				  	</select>
			  	</div>
			  	
			  	<h6>Bienvenido <jsp:getProperty property="emailUsuario" name="contactoBean"/></h6>	
			  	
			</div>
			<h6>Bienvenido <jsp:getProperty property="emailUsuario" name="contactoBean"/></h6>
		</div>

		<div class="izquierda" id="izquierda"></div>
		<script type="text/javascript">
			let actualizando = true;
			let context='${pageContext.request.contextPath}';
			
	  
	  		window.addEventListener('load',function(){
			  	document.getElementById("filtrarTipo").addEventListener("change",filtroClase);
		      	document.getElementById("interesesFiltro").addEventListener("change",filtroIntereses);
		      	document.getElementById("fechaFiltro").addEventListener("focusout",filtroFecha);
	      
			});
	
			let xhr = new XMLHttpRequest();
	
	  		let contador = 0;
	
	  		/* Cuando el documento cargue inicializamos todo */
	  		document.addEventListener("DOMContentLoaded", function(event) {
	  			
		    	/* Obtenemos esta valor �nicamente una vez y luego lo reutilizamos */
		    	let div = document.getElementById('izquierda');
		    	/* Preparamos la funci�n que ser� llamada cada vez que se actualice el estado del XHR */
		    	xhr.onreadystatechange = function () {
		      	/* readyState = 4 indica que ha terminado la carga del documento */
			      	if (xhr.readyState === 4) {
			        	/* Un c�digo HTTP 200 indica que todo fue bien */
			        	if (xhr.status === 200) {
			          	/* Actualizamos el contenido de la columna */
			          		contador=contador+1;
			          		div.innerHTML = xhr.responseText;
			          		
			       
			          		if(contador > 1){
			        	  
				          		if(document.getElementById("ultimoFiltro").value=="clase"){
				          	  		filtroClase();
				          		}
				          		else if(document.getElementById("ultimoFiltro").value=="fecha"){
				        	  	filtroFecha();
				          		}
				          		else if(document.getElementById("ultimoFiltro").value=="intereses"){
				        	  		filtroIntereses();
				          		}
			          		}
			        	} 
			        	else {
			          		alert("Tiempo de inactividad superado. Vuelva a logearse. En caso de que no pueda conectarse reinicie la aplicacion");
			          		window.location = context+"/mvc/controller/loginController.jsp";
			        	}
			      	}
	    		};
	    	/* Solicitamos los datos (si est� activado) y planificamos los intentos cada 2 segundos */
	    		solicitar_datos();
	    		setInterval('solicitar_datos()', 10000);
	  		});
	 		
	  		document.getElementById("busqueda").addEventListener("keyup", solicitar_datos);
	  
	  	</script>
		<div class="derecha">
    		<div >
    			<h2>Orden</h2>
    			<select name="orden" id="orden">
					<option selected="selected" value="1">Fecha</option>
			  		<option value="2">Autor</option>
		  		</select>
		  		<script type="text/javascript">document.getElementById("orden").addEventListener("change",solicitar_datos);</script>
      			<h2>Filtros</h2>
      
      			<h3>Tipo de Anuncio</h3>
      			<select name="filtrarTipo" id="filtrarTipo">
        			<option value=""></option>
	  				<option value="class es.uco.pw.practica3.business.AnuncioTematico">Tematico</option>
				  	<option value="class es.uco.pw.practica3.business.AnuncioGeneral">General</option>
				  	<option value="class es.uco.pw.practica3.business.AnuncioIndividualizado">Individualizado</option>
				  	<option value="class es.uco.pw.practica3.business.AnuncioFlash">Flash</option> 
				</select>
	 
	  			<h3>Intereses del Anuncio</h3>
       			<div>
      
      			<%
      
      			InteresesBean interesesBean=(InteresesBean)request.getSession().getAttribute("interesesBean");
		
			  	if(interesesBean.getIntereses()!=null){
			      	out.println("<select name="+"interesesFiltro"+ " id="+"interesesFiltro"+">");
			      	out.println("<option value="+""+">"+"</option>");
			      	for(Interes i: interesesBean.getIntereses()){
		            	out.println("<option value="+i.getId()+">"+i.getInteres()+"</option>");
				  	}
			      	out.println("</select>");
			  	}
		      
		      	%>
      
      			</div>
	  
	  			<h3>Fecha del Anuncio</h3>
      			<input type="date" id="fechaFiltro" name="fechaFiltro">
      
      			<input type="hidden" id="ultimoFiltro" name="ultimoFiltro">
      
    		</div>
    
  		</div>

		<% 
		}
		%>

	</body>
</html>