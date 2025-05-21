package robos;

import exception.MovimentoInvalidoException;

public abstract class Robo {
	protected String cor;
	protected int posicaoX;
	protected int posicaoY;
	protected int movimentosValidos;
	protected int movimentosInvalidos;
	protected String ultimaDirecaoUsada;
	
	public Robo(String cor) {
		this.cor = cor;
		this.posicaoX = 0;
		this.posicaoY = 0;
	}
	
	public abstract boolean moverRobo(String direcao) throws MovimentoInvalidoException;
	
	public abstract boolean moverRobo(int numero) throws MovimentoInvalidoException;
	
	public boolean encontrarAlimento(int posicaoXAlimento, int posicaoYAlimento){
		if (posicaoX == posicaoXAlimento && posicaoY == posicaoYAlimento) {
			return true;
		}
		return false;
	}
	
	protected boolean moverParaDirecao(String direcao) throws MovimentoInvalidoException {
		switch (direcao) {
			case "up":
				posicaoY++;
				movimentosValidos++;
				ultimaDirecaoUsada = "up";
				return true;
			case "down":
				if (posicaoY - 1 < 0) {
					movimentosInvalidos++;
					throw new MovimentoInvalidoException("down");
				}
				posicaoY--;
				movimentosValidos++;
				ultimaDirecaoUsada = "down";
				return true;
			case "right":
				posicaoX++;
				movimentosValidos++;
				ultimaDirecaoUsada = "right";
				return true;
			case "left":
				if (posicaoX - 1 < 0) {
					movimentosInvalidos++;
					throw new MovimentoInvalidoException("left");
				}
				posicaoX--;
				movimentosValidos++;
				ultimaDirecaoUsada = "left";
				return true;
			default:
				movimentosInvalidos++;
				return false;
		}
	}
	
	
	public String getCor() {
		return cor;
	}
	
	public int getPosicaoX() {
		return posicaoX;
	}
	
	public int getPosicaoY() {
		return posicaoY;
	}
	
	public int getMovimentosValidos() {
		return movimentosValidos;
	}
	
	public int getMovimentosInvalidos() {
		return movimentosInvalidos;
	}
	
	public String getUltimaDirecaoUsada() {
		return ultimaDirecaoUsada;
	}
	
	
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public void setPosicaoX(int posicaoX) {
		this.posicaoX = posicaoX;
	}
	
	public void setPosicaoY(int posicaoY) {
		this.posicaoY = posicaoY;
	}
	
	public void setMovimentosValidos(int movimentosValidos) {
		this.movimentosValidos = movimentosValidos;
	}
	
	public void setMovimentosInvalidos(int movimentosInvalidos) {
		this.movimentosInvalidos = movimentosInvalidos;
	}
	
	public void setUltimaDirecaoUsada(String ultimaDirecaoUsada) {
		this.ultimaDirecaoUsada = ultimaDirecaoUsada;
	}
	
	
}
