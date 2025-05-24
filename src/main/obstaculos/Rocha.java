package main.obstaculos;


public class Rocha extends Obstaculo {
	@Override
	public boolean bater(int posicaoXrobo, int posicaoYrobo) {
        return posicaoXrobo == posicaoX && posicaoYrobo == posicaoY;
    }
}