<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Nuevo Interes</title>
		<link href="${pageContext.request.contextPath}/css/views.css" rel="stylesheet" type="text/css" >
	</head>
	<body>
	
		<form id="formulario" method="post" action="../../interesesController">
			
			<label for="titulo">Interes: </label>
			<input type="text" name="interes" value="">	<br/>
			<button type="submit" form="formulario">Submit</button>
		</form>
	
	</body>
</html>