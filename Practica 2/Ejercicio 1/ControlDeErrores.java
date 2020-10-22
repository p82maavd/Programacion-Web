package ejercicio1;

/**
 * Declaracion de la clase ControlDeErrores.
 * @author Damian Martinez
 * @author Daniel Ortega
 * 
 * 
 */
public class ControlDeErrores {
	/**
	 * Metodo que se encarga de comprobar que el nombre de Contacto tenga el formato correcto
	 * @param cadena
	 * @return boolean
	 */
	public boolean esNombre(String cadena) {
		char[] cadenac=cadena.toCharArray();
		for(int i=0;i<cadena.length();i++) {
			if((Character.isDigit(cadenac[i]))) {
				return false;
			}
		}
		
		return true;
	}
	/**
	 * Metodo que comprueba que el email de Contacto tenga el formato correcto
	 * @param cadena
	 * @return boolean
	 */
	public boolean esEmail(String cadena) {
		
		if(cadena.contains("@")) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * Metodo que comprueba que una fecha tenga el formato correcto
	 * @param fecha
	 * @return boolean
	 */
	public boolean esFecha(String fecha) {
		
		if(fecha.charAt(4)!='-') {
			return false;
		}
		if(fecha.charAt(7)!='-') {
			return false;
		}
		//No es necesario poner para comprobar si el resto son numeros porque daria error al parsear la fecha
		
		return true;
	}

}
