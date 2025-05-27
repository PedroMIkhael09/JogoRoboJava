package main.obstaculos;

import main.robos.Robo;

public class Bomba extends Obstaculo {

	public Bomba(int posicaoX, int posicaoY) {
		super(posicaoX, posicaoY);
	}
	

	public void bater(Robo robo){
	robo.explodir();
	}
	
}
