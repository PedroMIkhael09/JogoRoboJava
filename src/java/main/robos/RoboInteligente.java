package robos;

import exception.MovimentoInvalidoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RoboInteligente extends Robo {
	
	private String ultimaDirecaoUsada;
	
	public RoboInteligente(String cor) {
		super(cor);
	}
	
	@Override
	public boolean moverRobo(String direcao) {
		ultimaDirecaoUsada = null;
		try {
			super.moverRobo(direcao);
			ultimaDirecaoUsada = direcao;
			return true;
		} catch (MovimentoInvalidoException e) {
			List<String> direcoes = new ArrayList<>(Arrays.asList("up", "down", "left", "right"));
			direcoes.remove(direcao);
			Collections.shuffle(direcoes);
			
			for (String novaDirecao : direcoes) {
				try {
					super.moverRobo(novaDirecao);
					ultimaDirecaoUsada = novaDirecao;
					return true;
				} catch (MovimentoInvalidoException ignored) {
				}
			}
			
			movimentosInvalidos++;
			return false;
		}
	}
	
	public String getUltimaDirecaoUsada() {
		return ultimaDirecaoUsada;
	}
}
