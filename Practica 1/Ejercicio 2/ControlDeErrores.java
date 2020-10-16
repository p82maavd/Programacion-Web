package Ejercicio2;

public class ControlDeErrores {
	
	public boolean esNombre(String cadena) {
		char[] cadenac=cadena.toCharArray();
		for(int i=0;i<cadena.length();i++) {
			if((Character.isDigit(cadenac[i]))) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean esEmail(String cadena) {
		
		if(cadena.contains("@")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean esFecha(String fecha) {
		
		if(fecha.charAt(2)!='/') {
			return false;
		}
		if(fecha.charAt(5)!='/') {
			return false;
		}
		//No es necesario poner para comprobar si el resto son numeros porque daria error al parsear la fecha
		
		return true;
	}

}
