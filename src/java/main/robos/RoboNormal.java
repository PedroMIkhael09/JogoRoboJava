package robos;

import exception.MovimentoInvalidoException;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RoboNormal extends Robo {
	private Random random = new Random();
	
	public RoboNormal(String cor) {
		super(cor);
	}
	
	@Override
	public boolean moverRobo(String direcao) throws MovimentoInvalidoException {
		return moverParaDirecao(direcao);
	}
	
	@Override
	public boolean moverRobo(int numero) throws MovimentoInvalidoException {
		String direcao;
		
		switch (numero) {
			case 1:
				direcao = "up";
				break;
			case 2:
				direcao = "down";
				break;
			case 3:
				direcao = "right";
				break;
			case 4:
				direcao = "left";
				break;
			default:
				movimentosInvalidos++;
				return false;
		}
		
		return moverRobo(direcao);
	}
}
