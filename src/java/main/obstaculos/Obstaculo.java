package obstaculos;

public abstract class Obstaculo {
	protected int posicaoX;
	protected int posicaoY;
	
	public abstract boolean bater(int posicaoXrobo, int posicaoYrobo);
	
}