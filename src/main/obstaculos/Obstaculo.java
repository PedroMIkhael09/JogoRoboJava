package main.obstaculos;

import main.robos.Robo;

public abstract class Obstaculo {
	protected int posicaoX;
	protected int posicaoY;
	

	public Obstaculo(int posicaoX, int posicaoY) {
		this.posicaoX = posicaoX;
		this.posicaoY = posicaoY;
	}
	
	public abstract void bater(Robo robo);

	public int getPosicaoX() {
		return posicaoX;
	}

	public int getPosicaoY() {
		return posicaoY;
	}

	public void setPosicaoX(int posicaoX) {
		this.posicaoX = posicaoX;
	}

	public void setPosicaoY(int posicaoY) {
		this.posicaoY = posicaoY;
	}
}
