package exception;
public class MovimentoInvalidoException extends Exception {
	
	public MovimentoInvalidoException(String direcao) {
		super("Movimento inválido: " + direcao + ". O robô não pode ir para posições negativas.");
	}
}