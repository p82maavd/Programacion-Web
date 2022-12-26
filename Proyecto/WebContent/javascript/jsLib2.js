	//Funcion que filtra los anuncios por su clase
	function filtroClase(){
	    let x = document.getElementsByClassName("anuncio");
	    		
	 	for (let i=0; i<x.length; i=i+1) {
	 		//Cambiar children[2] = document.getElementsByClassName("clase");
	 		if(x[i].children[2].innerHTML==document.getElementById("filtrarTipo").value || document.getElementById("filtrarTipo").value == ""){
	 			x[i].style.display='block';
			}
	 		else{	
				x[i].style.display='none';
			}
	 	}
	 		
	 	document.getElementById("ultimoFiltro").value = "clase";
	}
	//Funcion que filtra los anuncios por su fecha
	function filtroFecha(){
		let x = document.getElementsByClassName("anuncio");
	  			
		for (let i=0; i<x.length; i=i+1) {
			//Cambiar children[2] = document.getElementsByClassName("clase");
			//alert("Children: "+x[i].children[3].innerHTML);
			//alert("Element: "+document.getElementById("fechaFiltro").value);
			if(x[i].children[3].innerHTML==document.getElementById("fechaFiltro").value || document.getElementById("fechaFiltro").value == ""){
				x[i].style.display='block';
	 	    }
			else{	
	 	    	x[i].style.display='none';
		    }
		}
			
		document.getElementById("ultimoFiltro").value = "fecha";
	}
  	//Funcion que filtra los anuncios por sus intereses
	function filtroIntereses(){
		let x= document.getElementsByClassName("anuncio");
	 
		//x[i] = cada anuncio
		//x[i].children[4]= div de los intereses
	  	//x[i].children[4].children[par]= id de cada interes
	  	//x[i].children[4] == x[i].getElementsByClassName("intereses") se supone
	  	let cont=0;
		
		for (let i=0; i<x.length; i=i+1) {
			cont=0;
			//alert("Anuncio: "+x[i].children[0].innerHTML);
			if(document.getElementById("interesesFiltro").value != ""){
				if(x[i].children[4].children[0]!=undefined){
					//alert("Es un anuncio Tematico");
					
					for(let j=0 ; j<x[i].children[4].childElementCount ; j=j+2){
						//alert("Tiene el interes: "+x[i].children[4].children[j+1].innerHTML);
						
						if(x[i].children[4].children[j]!=null && cont==0){
							if(x[i].children[4].children[j].innerHTML==document.getElementById("interesesFiltro").value){
								x[i].style.display='block';	
								cont=cont+1;
			  	   	 		}
							else{	
			  	    			x[i].style.display='none';
					    	}
						}
					}
				}
				
				else{
					x[i].style.display='none';
				}
			}
			
			else{
				x[i].style.display='block';
			}
		}
		
		document.getElementById("ultimoFiltro").value = "intereses";
	}
	//Funcion para obtener los anuncios de la base de datos
	function solicitar_datos() {
		let buscar= document.getElementById('busqueda');
		let orden= document.getElementById('orden').value;
	    /* Actualizamos el globo s�lo si est� siendo mostrado */
		if (actualizando == true && buscar.value=="") {
	    	/* Abrimos una nueva conexi�n y enviamos la petici�n para obtener el tablon */
	    	xhr.open('GET', context.concat("",'/getAnunciosController?accion=tablon&orden='+orden));
	    	xhr.send(null);
		}
			    
		//Busca los anuncios en la bd con el criterio que se especifique
		//Autor, fecha, titulo
		else if(actualizando==true){
			let filtro= document.getElementById('filtrobusqueda').value;
			xhr.open('GET',context.concat("",'/getAnunciosController?accion=busqueda&filtro='+filtro+'&valor='+buscar.value+'&orden='+orden));
			xhr.send(null);	
		}
			    
	}