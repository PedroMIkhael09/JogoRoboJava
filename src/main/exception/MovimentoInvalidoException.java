package main.exception;
public class MovimentoInvalidoException extends Exception {
	
	public MovimentoInvalidoException(String direcao) {
		super(direcao + ". O robô não pode ir para posições negativas.");
	}
}