package ebooks.controle;

public abstract class AbstractCommand implements ICommand {

	IFachada fachada = new Fachada();

}
