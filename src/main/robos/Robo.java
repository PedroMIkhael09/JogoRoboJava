package main.robos;

import main.exception.MovimentoInvalidoException;

public abstract class Robo {
	protected String cor;
	protected int posicaoX;
	protected int posicaoY;
	protected int posicaoAnteriorX;
	protected int posicaoAnteriorY;
	protected int movimentosValidos;
	protected int movimentosInvalidos;
	protected String ultimaDirecaoUsada;
	protected boolean ativo = true;

	public Robo(String cor) {
		this.cor = cor;
		this.posicaoX = 0;
		this.posicaoY = 0;
		this.posicaoAnteriorX = 0;
		this.posicaoAnteriorY = 0;
	}

	public abstract boolean moverRobo(String direcao) throws MovimentoInvalidoException;

	public abstract boolean moverRobo(int numero) throws MovimentoInvalidoException;

	public boolean encontrarAlimento(int posicaoXAlimento, int posicaoYAlimento) {
		return posicaoX == posicaoXAlimento && posicaoY == posicaoYAlimento;
	}

	public boolean moverParaDirecao(String direcao) throws MovimentoInvalidoException {
		posicaoAnteriorX = posicaoX;
		posicaoAnteriorY = posicaoY;

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


	public void voltarPosicaoAnterior() {
		this.posicaoX = posicaoAnteriorX;
		this.posicaoY = posicaoAnteriorY;
		System.out.println("O robô " + cor + " voltou para a posição anterior devido a um obstáculo.");
	}

	public void explodir() {
		this.ativo = false;
		System.out.println("O robô " + cor + " explodiu!");
	}

	public boolean estaAtivo() {
		return ativo;
	}


	public String getCor() {
		return cor;
	}

	public void incrementarMovimentoInvalido(){
		this.movimentosInvalidos++;

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

	public boolean isAtivo() {
		return ativo;
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

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
