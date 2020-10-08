package practica1;

public class ConcreteFactory extends AbstractFactory {

	// Implementation of creation methods
	//Aqui falta rellenar Constructores.
	
	@Override
	public AnuncioFlash createAnuncioFlash() {
		AnuncioFlash product = new AnuncioFlash();
		return product;
	}

	@Override
	public AnuncioIndividualizado createAnuncioIndividualizado() {
		AnuncioIndividualizado product = new AnuncioIndividualizado();
		return product;
	}
	
	@Override
	public AnuncioTematico createAnuncioTematico() {
		AnuncioTematico product = new AnuncioTematico();
		return product;
	}

	@Override
	public AnuncioGeneral createAnuncioGeneral() {
		AnuncioGeneral product = new AnuncioGeneral();
		return product;
	}

}