package robos;

import exception.MovimentoInvalidoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RoboInteligente extends Robo {
	
	public RoboInteligente(String cor) {
		super(cor);
	}
	
	@Override
	public boolean moverRobo(String direcao) {
		try {
			return moverParaDirecao(direcao);
		} catch (MovimentoInvalidoException e) {
			List<String> direcoes = new ArrayList<>(Arrays.asList("up", "down", "left", "right"));
			direcoes.remove(direcao);
			Collections.shuffle(direcoes);
			
			for (String novaDirecao : direcoes) {
				try {
					return moverParaDirecao(novaDirecao);
				} catch (MovimentoInvalidoException ignored) {
				}
			}
			movimentosInvalidos++;
			return false;
		}
	}
	
	@Override
	public boolean moverRobo(int numero) throws MovimentoInvalidoException {
		switch (numero) {
			case 1: return moverParaDirecao("up");
			case 2: return moverParaDirecao("down");
			case 3: return moverParaDirecao("right");
			case 4: return moverParaDirecao("left");
			default:
				movimentosInvalidos++;
				return false;
		}
	}
	
}
