package robos;
import exception.MovimentoInvalidoException;

public class Robo {
	protected String cor;
	protected int posicaoX;
	protected int posicaoY;
	protected int movimentosValidos;
	protected int movimentosInvalidos;
	
	public Robo(String cor){
		this.cor = cor;
		this.posicaoX = 0;
		this.posicaoY = 0;
	}
	
	public boolean moverRobo(String direcao) throws MovimentoInvalidoException {
		if (direcao.equals("up")) {
			posicaoY++;
			movimentosValidos++;
			return true;
		} else if (direcao.equals("down")) {
			if (posicaoY - 1 < 0) {
				movimentosInvalidos++;
				throw new MovimentoInvalidoException("down");
			}
			posicaoY--;
			movimentosValidos++;
			return true;
		} else if (direcao.equals("right")) {
			posicaoX++;
			movimentosValidos++;
			return true;
		} else if (direcao.equals("left")) {
			if (posicaoX - 1 < 0) {
				movimentosInvalidos++;
				throw new MovimentoInvalidoException("left");
			}
			posicaoX--;
			movimentosValidos++;
			return true;
		} else {
			movimentosInvalidos++;
			return false;
		}
	}
	
	public boolean moverRobo(int numero) throws MovimentoInvalidoException {
		if (numero == 1) {
			posicaoY++;
			movimentosValidos++;
			return true;
		} else if (numero == 2) {
			if (posicaoY - 1 < 0) {
				movimentosInvalidos++;
				throw new MovimentoInvalidoException("2 (down)");
			}
			posicaoY--;
			movimentosValidos++;
			return true;
		} else if (numero == 3) {
			posicaoX++;
			movimentosValidos++;
			return true;
		} else if (numero == 4) {
			if (posicaoX - 1 < 0) {
				movimentosInvalidos++;
				throw new MovimentoInvalidoException("4 (left)");
			}
			posicaoX--;
			movimentosValidos++;
			return true;
		} else {
			movimentosInvalidos++;
			return false;
		}
	}
	
	
	public boolean encontrarAlimento(int posicaoXAlimento, int posicaoYAlimento){
		if (posicaoXAlimento == posicaoX && posicaoYAlimento == posicaoY){
			return true;
		}
		return false;
	}
	
	public int getPosicaoX() {
		return posicaoX;
	}
	public void setPosicaoX(int posicaoX) {
		this.posicaoX = posicaoX;
	}
	
	public int getPosicaoY() {
		return posicaoY;
	}
	
	public void setPosicaoY(int posicaoY) {
		this.posicaoY = posicaoY;
	}
	
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public int getMovimentosValidos() {
		return movimentosValidos;
	}
	
	public void setMovimentosValidos(int movimentosValidos) {
		this.movimentosValidos = movimentosValidos;
	}
	
	public int getMovimentosInvalidos() {
		return movimentosInvalidos;
	}
	
	public void setMovimentosInvalidos(int movimentosInvalidos) {
		this.movimentosInvalidos = movimentosInvalidos;
	}
	
}