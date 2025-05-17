package obstaculos;

import obstaculos.Obstaculo;

public class Bomba extends Obstaculo {
	@Override
	public boolean bater(int posicaoXrobo, int posicaoYrobo) {
		if (posicaoXrobo == posicaoX && posicaoYrobo == posicaoY) {
			return true;
		}
		return false;
	}
}