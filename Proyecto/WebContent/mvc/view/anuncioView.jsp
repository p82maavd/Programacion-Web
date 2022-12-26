<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import ="java.lang.Integer,java.text.SimpleDateFormat,es.uco.pw.practica3.business.Interes, java.util.ArrayList, java.util.Date" %>
    
    <jsp:useBean  id="interesesBean" scope="session" class="es.uco.pw.practica3.display.InteresesBean"></jsp:useBean>
    <jsp:useBean  id="anuncioBean" scope="session" class="es.uco.pw.practica3.display.AnuncioBean"></jsp:useBean>
    <jsp:useBean  id="contactoBean" scope="session" class="es.uco.pw.practica3.display.ContactoBean"></jsp:useBean>
<!DOCTYPE html>
<html>
<!-- Formulario para crear un anuncio de los diferentes tipos que hay disponibles -->
	<head>
		<meta charset="ISO-8859-1">
		<title>Anuncio</title>
		<link href="${pageContext.request.contextPath}/css/views.css" rel="stylesheet" type="text/css" >
		<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jsLib1.js"></script>
	</head>
	<body>


		<% 
		String id;
		id=null;
		if(anuncioBean.getId()==0){
			id=null;
		}
		else{
			Integer ids= Integer.valueOf(anuncioBean.getId());
			id=ids.toString();
		}
		//Comprobacion seguridad
		if(!(anuncioBean.getAutor().equals(contactoBean.getEmailUsuario())) & !anuncioBean.getTitulo().equals("")){
			//Si no eres el autor no puedes modificar un anuncio.
			response.sendRedirect(request.getContextPath()+"/mvc/controller/loginController.jsp");
		}
		%>
		<form id="formulario" method="post" action="../../anunciosController" onSubmit="return validar2(this)">
	
			<label for="titulo">Titulo: </label>
			<input type="text" name="titulo" value="<%=anuncioBean.getTitulo() %>">	<br/>
			<label for="cuerpo">Cuerpo: </label><br/>
			<textarea name="cuerpo" rows="8" cols="100"><%=anuncioBean.getCuerpo() %></textarea>
			
			
			<input type="hidden" name="tipo" id="tipo" value="<%=request.getParameter("tipo") %>">
			<input type="hidden" name="nintereses" id="nintereses" value="<%=interesesBean.getIntereses().size() %>">
			<input type="hidden" name="id" id="id" value="<%=id%>">
			<input type="hidden" name="containerfechainicio" id="containerfechainicio" value="<%=anuncioBean.getFechainicio() %>">
			<input type="hidden" name="containerfechafinal" id="containerfechafinal" value="<%=anuncioBean.getFechafinal() %>">
		 	
			<br/>
	
			<script>
				if(document.getElementById('tipo').value=='class es.uco.pw.practica3.business.AnuncioTematico'){
					let x=document.getElementById("formulario");
					let label= document.createElement("label");
	  				label.setAttribute("for","titulo");
	  				label.innerHTML = "Seleccione los intereses: ";
	  				x.appendChild(label);
	  				//Como los javabeans no son parte del cliente, se hace mediante un snippet en java
				}
			
				else if(document.getElementById('tipo').value=='class es.uco.pw.practica3.business.AnuncioFlash'){
				
					flash();
				}
			
				else if(document.getElementById('tipo').value=='class es.uco.pw.practica3.business.AnuncioIndividualizado'){
				
					individualizado();
					
				}
			</script>
	
	
			<%
		
			if(request.getParameter("tipo").equals("class es.uco.pw.practica3.business.AnuncioIndividualizado") ){
				out.println("<table>");
				if(anuncioBean.getDestinatarios()!=null){
					for(String s:anuncioBean.getDestinatarios()){
					
						out.println("<tr id="+s+">");
						out.println("<td><input name="+"destinatarios"+" value="+s+"></td>");
						out.println("<td><button type="+"button"+" name="+"boton"+ "onclick=deletea("+"'"+s+"'"+")>Eliminar</td>");
					
					}
				}
				out.println("</table>");
			}
			else if(request.getParameter("tipo").equals("class es.uco.pw.practica3.business.AnuncioTematico") ){
				int cont=0; Integer cont2=0;
				
				for(Interes i: interesesBean.getIntereses()){
					cont=0;
					
					if((anuncioBean.getIntereses())!=null){
						for(Interes j:anuncioBean.getIntereses()){
							if(i.getId()==j.getId()){
								out.println("<input type="+"checkbox" + " id= "+cont2.toString()+" name="+"intereses"+" value="+i.getId()+" checked="+"checked"+">"+i.getInteres());
								cont++;
								cont2++;
							}
						}
						if(cont==0){
							out.println("<input type="+"checkbox" + " id= "+cont2.toString()+ " name="+"intereses"+" value="+i.getId()+">"+i.getInteres());
							cont2++;
						}
					}
					else{
						out.println("<input type="+"checkbox"+" id= "+cont2.toString() + " name="+"intereses"+" value="+i.getId()+">"+"<span>"+i.getInteres()+"</span>");
						cont2++;
					}
				}
			}
			
			request.getSession().removeAttribute("anuncioBean");
			%>
	
			<button type="submit" form="formulario">Submit</button>
		</form>

	</body>
</html>