package main.model.robos;

import main.exception.MovimentoInvalidoException;

public class Robo {
	protected int posicaoX;
	protected int posicaoY;
	protected int posicaoAnteriorX;
	protected int posicaoAnteriorY;
	protected String cor;
	protected int movimentosInvalidos;
	protected int movimentosValidos;
	protected String ultimaDirecaoUsada;
	protected boolean ativo = true;
	
	public Robo(String cor) {
		this.cor = cor;
		this.posicaoX = 0;
		this.posicaoY = 0;
		this.movimentosInvalidos = 0;
		this.movimentosValidos = 0;
		this.ultimaDirecaoUsada = "";
	}
	
	public boolean mover(String direcao) throws MovimentoInvalidoException {
		
		posicaoAnteriorX = posicaoX;
		posicaoAnteriorY = posicaoY;
		
		switch (direcao.toLowerCase()) {
			case "up":
				posicaoY++;
				movimentosValidos++;
				ultimaDirecaoUsada = "up";
				return true;
			case "down":
				if (posicaoY - 1 < 0) {
					movimentosInvalidos++;
					throw new MovimentoInvalidoException("Movimento inválido: down");
				}
				posicaoY--;
				movimentosValidos++;
				ultimaDirecaoUsada = "down";
				return true;
			case "left":
				if (posicaoX - 1 < 0) {
					movimentosInvalidos++;
					throw new MovimentoInvalidoException("Movimento inválido: left");
				}
				posicaoX--;
				movimentosValidos++;
				ultimaDirecaoUsada = "left";
				return true;
			case "right":
				posicaoX++;
				movimentosValidos++;
				ultimaDirecaoUsada = "right";
				return true;
			default:
				movimentosInvalidos++;
				throw new MovimentoInvalidoException("Direção inválida: " + direcao);
		}
	}
	
	public boolean mover(int numero) throws MovimentoInvalidoException {
		switch (numero) {
			case 1: return mover("up");
			case 2: return mover("down");
			case 3: return mover("right");
			case 4: return mover("left");
			default:
				movimentosInvalidos++;
				throw new MovimentoInvalidoException("Número inválido: " + numero);
		}
	}
	public boolean tentarMover(String direcao) throws MovimentoInvalidoException {
		int novoX = posicaoX;
		int novoY = posicaoY;

		switch (direcao.toLowerCase()) {
			case "up":
				novoY++;
				break;
			case "down":
				novoY--;
				break;
			case "left":
				novoX--;
				break;
			case "right":
				novoX++;
				break;
		}

		// Verifica se a nova posição está dentro do tabuleiro 4x4 (0-3)
		if (novoX < 0 || novoX > 3 || novoY < 0 || novoY > 3) {
			movimentosInvalidos++;
			return false; // Movimento inválido, perde a vez
		}

		posicaoX = novoX;
		posicaoY = novoY;
		movimentosValidos++;
		return true;
	}
	
	public boolean encontrarAlimento(int posicaoXAlimento, int posicaoYAlimento) {
		return posicaoX == posicaoXAlimento && posicaoY == posicaoYAlimento;
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
	
	// Getters
	public String getUltimaDirecaoUsada() {
		return ultimaDirecaoUsada;
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
	
	public int getMovimentosInvalidos() {
		return movimentosInvalidos;
	}
	
	public int getMovimentosValidos() {
		return movimentosValidos;
	}
	
	// Setters
	public void setPosicaoX(int posicaoX) {
		this.posicaoX = posicaoX;
	}
	
	public void setPosicaoY(int posicaoY) {
		this.posicaoY = posicaoY;
	}
	
	public void setMovimentosInvalidos(int movimentosInvalidos) {
		this.movimentosInvalidos = movimentosInvalidos;
	}
	public void setMovimentosValidos(int movimentosValidos) {
		this.movimentosValidos = movimentosValidos;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}
}
