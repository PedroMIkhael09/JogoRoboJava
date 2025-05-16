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
			return super.moverRobo(direcao);
		} catch (MovimentoInvalidoException e) {
			
			List<String> direcoes = new ArrayList<>(Arrays.asList("up", "down", "left", "right"));
			direcoes.remove(direcao);
			Collections.shuffle(direcoes);
			
			for (String novaDirecao : direcoes) {
				try {
					return super.moverRobo(novaDirecao);
				} catch (MovimentoInvalidoException ignored) {
				
				}
			}
			
			movimentosInvalidos++;
			return false;
		}
	}
}
