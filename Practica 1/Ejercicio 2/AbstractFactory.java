package practica1;

import java.util.ArrayList;

public abstract class AbstractFactory {

	// Factory methods for each anuncio.
	
	public abstract AnuncioFlash createAnuncioFlash(Contacto e,ArrayList <Contacto> a, int id);
	
	public abstract AnuncioIndividualizado createAnuncioIndividualizado(Contacto e, ArrayList<Contacto> destinatarios, int id);
	
	public abstract AnuncioTematico createAnuncioTematico(Contacto e, ArrayList <String> aux, ArrayList <Contacto> a, int id);
	
	public abstract AnuncioGeneral createAnuncioGeneral(Contacto e,ArrayList <Contacto> a, int id);
}
