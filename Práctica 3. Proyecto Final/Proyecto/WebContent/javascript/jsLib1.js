//General
 
	//Funcion que comprueba que los campos a rellenar no esten vacios
	function campoVacio(inp) {
   		if(inp==""){
    		return "Se deben de rellenar todos los campos";
    	}
    	else{
    		return "";
    	}
	}

//Fin General

//anuncioView
	//Funcion para anunciosFlash
	function flash(){
					
		let x=document.getElementById("formulario");
				  		
		let label= document.createElement("label");
		label.setAttribute("for","fechainicio");
		label.innerHTML = "Fecha de Inicio: (yyyy-mm-dd hh:mm)";
				  		
		let label2= document.createElement("label");
		label2.setAttribute("for","fechafinal");
		label2.innerHTML = "Fecha de Finalizacion: (yyyy-mm-dd hh:mm)";
				  		
		let input= document.createElement("input");
		input.setAttribute("type","text");
		input.setAttribute("name","fechainicio");
		input.setAttribute("class","fechasFlash");
		if(document.getElementById("containerfechainicio").value!='null'){
			input.setAttribute("value",document.getElementById("containerfechainicio").value);
		}		  		
		let input2= document.createElement("input");
		input2.setAttribute("type","text");
		input2.setAttribute("name","fechafin");
		input2.setAttribute("class","fechasFlash");
		if(document.getElementById("containerfechafinal").value!='null'){
			input2.setAttribute("value",document.getElementById("containerfechafinal").value);
		}
				  		
		x.appendChild(label);
		x.appendChild(input);
		x.appendChild(label2);
		x.appendChild(input2);
			
	}
	//Funcion para anuncios individualizados
	function individualizado(){
		let x=document.getElementById("formulario");
	  		
		let label= document.createElement("label");
		label.setAttribute("for","destinatario");
		label.innerHTML = "Correo del Destinatario: ";
		x.appendChild(label);
			
		let input= document.createElement("input");
		input.setAttribute("type","text");
		input.setAttribute("name","destinatario");
		input.setAttribute("id","insertarEmail");
		x.appendChild(input);
			
		let button= document.createElement("button");
		button.setAttribute("name","boton");
		button.setAttribute("id","botonadd");
		button.setAttribute("type","button");
		button.innerHTML="Insertar";
		x.appendChild(button);
		document.getElementById("botonadd").addEventListener("click",add);

		let label2=document.createElement("label");
		label2.setAttribute("for","texto");
		label2.innerHTML="Lista de Destinatarios";
		let form = document.getElementsByTagName("form")[0];
		form.appendChild(label2);
			
	}
	//Funcion que añade destinatario
	function add(){
			
		let tabla= document.getElementsByTagName("table")[0];
		
		let identificador=document.getElementById("insertarEmail").value;
		document.getElementById("insertarEmail").value="";
		let fila = document.createElement("tr");
		let celda = document.createElement("td");
		let celda2= document.createElement("td");
		
		fila.setAttribute("id",identificador);
		let textoCelda = document.createElement("input");
		textoCelda.setAttribute("name","destinatarios");
		textoCelda.setAttribute("value",identificador);
		
		let button= document.createElement("button");
		
		button.setAttribute("name","boton");
		button.setAttribute("type","button");
		button.setAttribute("id","botondelete");
		button.innerHTML="Eliminar";
		
		celda2.appendChild(button);
		celda.appendChild(textoCelda);
		fila.appendChild(celda);
		fila.appendChild(celda2);
		tabla.appendChild(fila);
		
		document.getElementById("botondelete").addEventListener("click",function(evento){deletea(identificador);});
	}
	//Funcion que borra un destinatario
	function deletea(fila){
		let filatr=document.getElementById(fila);
		let tabla=document.getElementsByTagName("table")[0];
		tabla.removeChild(filatr);
	}
	
	//Funcion para validar la fecha en formatod yyyy-mm-dd hh:mm
	function validarFecha(fecha){
		years = fecha.substr(0,4);
		barra= fecha.substr(4,1);
		meses= fecha.substr(5,2);
		barra2 = fecha.substr(7,1);
		dias = fecha.substr(8,2);
		if ( parseInt(years,10)<1000 || parseInt(years,10)>9999 ){
			return "Formato de la fecha yyyy-mm-dd hh:mm";
		}
		else if (barra!="-"){
			return "Formato de la fecha yyyy-mm-dd hh:mm";
		}	
		else if (parseInt(meses,10)>12 || parseInt(meses,10)<01){
			return "Formato de la fecha yyyy-mm-dd hh:mm";
		}
		else if (barra2!="-"){
			return "Formato de la fecha yyyy-mm-dd hh:mm";
		}
		else if(parseInt(dias,10)>31 || parseInt(dias,10)<01){
			return "Formato de la fecha yyyy-mm-dd hh:mm";
		}
		else if(fecha.substr(10,1)!=" "){
		    return "Formato de la fecha yyyy-mm-dd hh:mm";
		}
		else if(parseInt(fecha.substr(11,2),10)>23 ){
		    return "Formato de la fecha yyyy-mm-dd hh:mm";
		}
	
		else if(fecha.substr(13,1)!=":"){
		    return "Formato de la fecha yyyy-mm-dd hh:mm";
		}
	
		else if(parseInt(fecha.substr(14,2),10)>59){
		    return "Formato de la fecha yyyy-mm-dd hh:mm";
		}
	
		else{
			return "";
		}
	}
		
	//Funcion que comprueba que la longitud del titulo no sea demasiado larga
	function longitudTitulo(inp){
		if(inp.length>59){
			return "Titulo demasiado largo";
		}
		else{
			return "";
		}
	}
	//Funcion que comprueba que la longitud del cuerpo no sea demasiado larga
 	function longitudCuerpo(inp){
		if(inp.length>1000){
			return "Cuerpo demasiado largo";
		}
		else{
			return "";
		}
	}
	
	//Funcion que valida las diferentes comprobaciones anteriormente creadas con los diferentes parametros de nuestro anuncio
	function validar2(form) {
    	if(form.tipo.value=="class es.uco.pw.practica3.business.AnuncioFlash" ){
	   		fallo = campoVacio(form.titulo.value);
	  		fallo += campoVacio(form.cuerpo.value);
	  		fallo += campoVacio2(form.containerfechainicio.value);
	  		fallo += campoVacio2(form.containerfechafinal.value);
	  		fallo2 = longitudTitulo(form.titulo.value);
	  		fallo3 = longitudCuerpo(form.cuerpo.value);
	  		fallo4 = validarFecha(form.fechainicio.value);
	  		fallo5 = validarFecha(form.fechafin.value);
	  		if (fallo == "" && fallo2=="" && fallo3=="" && fallo4=="" && fallo5==""){ 
				return true;
			}
			else {
				if(fallo!=""){
		           	fallo="Se deben de rellenar todos los campos";
		            alert(fallo);
				}
				if(fallo2!="")
					alert(fallo2);
				if(fallo3!="")
					alert(fallo3);
				if(fallo4!="")
					alert(fallo4);
				if(fallo5!="")
					alert(fallo5);
				return false;
			}
	    }
    	else{
    		fallo = campoVacio(form.titulo.value);
      		fallo += campoVacio(form.cuerpo.value);
      		fallo2 = longitudTitulo(form.titulo.value);
      		fallo3 = longitudCuerpo(form.cuerpo.value);
    		if (fallo == "" && fallo2=="" && fallo3==""){ 
    			return true;
    		}
    		else {
    			fallo="Se deben de rellenar todos los campos";
    			alert(fallo); 
    			if(fallo2!="")
    				alert(fallo2);
    			if(fallo3!="")
    				alert(fallo3);
    			return false;
    		}
    	}
    }

//Fin anuncioView
		
//contactoView

	//Función que desactiva el email si se esta modificando el contacto
	function email(){
		if(document.getElementById("email").value!=""){
			document.getElementById("email").disabled=true;
		}
	}
	//Funcion para mandar input para borrar un usuario
	function borrar(){
		alert("Entra en la funcion");
		var opcion = confirm("Clicka en Aceptar o Cancelar");
    	if (opcion == true) {
    		let accion = document.getElementById('accion');
        	accion.value = 'borrarUsuario';
        	alert(accion.value);
        	document.getElementById('formulario').submit();
    	}
	}
	
	//Funcion que comprueba que la longitud del email no sea demasiado larga
	function longitudEmail(inp){
		if(inp.length>40){
			return "Email demasiado largo";
		}
		else{
			return "";
		}
	}

	//Funcion que comprueba que la longitud del nombre no sea demasiado larga
	function longitudNombre(inp){
		if(inp.length>20){
			return "Nombre demasiado largo";
		}
		else{
			return "";
		}
	}
	
	//Funcion que comprueba que la longitud de los apellidos no sea demasiado larga
	function longitudApellidos(inp){
		if(inp.length>40){
			return "Apellidos demasiado largos";
		}
		else{
			return "";
		}
	}
	
    //Funcion que comprueba que la longitud de la contraseña no sea demasiado larga
	function longitudPwd(inp){
		if(inp.length>20){
			return "Contraseña demasiado larga";
		}
		else{
			return "";
		}
	}
	
	//Funcion que comprueba que el email tenga el formato correcto
	function comprobarEmail(inp){
    	//En esta expresion regex olo se aceptan valores desde "a" hasta "z" ya sea en mayuscula o minuscula, numeros y el caracter "_". /^\w+@\w+[.]+\w+$
    	//Para que detecte ñ's.
    	if (/^[a-zA-Z\u00f1\u00d1]*[a-zA-Z\u00f1\u00d1]+@+[a-zA-Z\u00f1\u00d1]*[a-zA-Z\u00f1\u00d1]+[.]+[a-zA-Z\u00f1\u00d1]*[a-zA-Z\u00f1\u00d1]+$/.test(inp)){
    	  return "";
    	} else {
    		return "Email incorrecto";
    	}
	}
	
    //Funcion que valida las diferentes comprobaciones anteriormente creadas con los diferentes parametros del contacto
	function validar1(form) {
		fallo = campoVacio(form.email.value);
	    fallo += campoVacio(form.password.value);
	    fallo += campoVacio(form.nombre.value);
	    fallo += campoVacio(form.apellidos.value);
	    fallo += campoVacio(form.fechanacimiento.value);
	    fallo1 = longitudEmail(form.email.value);
	    fallo2 = longitudNombre(form.nombre.value);
	    fallo3 = longitudApellidos(form.apellidos.value);
	    fallo4 = longitudPwd(form.password.value);
	    fallo5 = comprobarEmail(form.email.value);
	    if (fallo == "" && fallo1 == "" && fallo2 == "" && fallo3 == "" && fallo4 == "" && fallo5=="" ){ 
	      	return true;
	    }
	    else {
	    	if(fallo!=""){
	      		fallo="Se deben de rellenar todos los campos";
	      		alert(fallo);
	      	}
	      	if(fallo1!="")
	      		alert(fallo1);
	      	if(fallo2!="")
	      		alert(fallo2);
	   		if(fallo3!="")
	      		alert(fallo3);
	      	if(fallo4!="")
	      		alert(fallo4);
	      	if(fallo5!="")
	       		alert(fallo5);
	      	return false;
		}
	}
      	
 //Final contactoView
 
	
    	
 //loginView
 
	//Funcion que valida que tanto el email y la contraseña al iniciar sesion no este vacia
	function validar3(form) {
		fallo = campoVacio(form.email.value);
      	fallo += campoVacio(form.password.value);
      	if (fallo == ""){ 
      		return true;
      	}
      	else {
      		fallo="Se deben de rellenar todos los campos";
      		alert(fallo); 
      		return false;
      	}
	}