package practica1;

import java.util.ArrayList;

public abstract class AbstractFactory {

	// Factory methods for each anuncio.
	
	public abstract AnuncioFlash createAnuncioFlash(Contacto e,ArrayList <Contacto> a);
	
	public abstract AnuncioIndividualizado createAnuncioIndividualizado(Contacto e);
	
	public abstract AnuncioTematico createAnuncioTematico(Contacto e);
	
	public abstract AnuncioGeneral createAnuncioGeneral(Contacto e,ArrayList <Contacto> a);
}
