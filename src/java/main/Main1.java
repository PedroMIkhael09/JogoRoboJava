import exception.MovimentoInvalidoException;
import robos.Robo;
import java.util.Scanner;

public class Main1 {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("Seja bem vindo ao jogo do robo!\n ");
		System.out.println("Regras do jogo:\n-Digite 1 ou 'up' para aumentar uma casa " +
				"no" +
				" " +
				"eixo Y\n-Digite 2 ou 'down' para diminuir uma casa no eixo Y\n-Digite " +
				"3 " +
				"ou" +
				" 'right' para ir aumentar uma casa no eixo X\n-Digite 4 ou 'left' para" +
				" diminuir uma casa no eixo X\nFaça isso ate chegar na posição do " +
				"alimento!!\n");
		
		System.out.println("Digite a cor do seu robo: ");
		String cor = teclado.nextLine();
		Robo robo = new Robo(cor);
		
		System.out.println("Escolha uma posição no eixo X para o alimento: ");
		int posicaoXAlimento = teclado.nextInt();
		
		System.out.println("Escolha uma posição no eixo Y para o alimento: ");
		int posicaoYAlimento = teclado.nextInt();
		teclado.nextLine();
		
		boolean encontrou = false;
		while (!encontrou) {
			System.out.println("Digite a direção para mover o robô: ");
			String direcao = teclado.nextLine();
			
			try {
				boolean mov = robo.moverRobo(direcao);
				if (!mov) {
					System.out.println("Direção inválida! Use: up, down, left, right.");
				} else {
					System.out.println("Robô na posição: (" + robo.getPosicaoX() + ", " + robo.getPosicaoY() + ")");
					encontrou = robo.encontrarAlimento(posicaoXAlimento, posicaoYAlimento);
					if (encontrou) {
						System.out.println("Parabéns! O robô encontrou o alimento!");
					}
				}
			} catch (MovimentoInvalidoException e) {
				System.out.println("Movimento inválido: " + e.getMessage());
				System.out.println("Posição atual do robô: (" + robo.getPosicaoX() + ", " + robo.getPosicaoY() + ")");
			}
		}
		
		teclado.close();
	}
}
