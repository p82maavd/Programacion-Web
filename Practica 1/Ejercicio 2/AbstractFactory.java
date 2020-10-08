package practica1;

public abstract class AbstractFactory {

	// Factory methods for each anuncio.
	
	public abstract AnuncioFlash createAnuncioFlash();
	
	public abstract AnuncioIndividualizado createAnuncioIndividualizado();
	
	public abstract AnuncioTematico createAnuncioTematico();
	
	public abstract AnuncioGeneral createAnuncioGeneral();
}
