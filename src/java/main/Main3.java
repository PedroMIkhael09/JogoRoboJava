import robos.Robo;
import exception.MovimentoInvalidoException;
import robos.RoboInteligente;

import java.util.Random;
import java.util.Scanner;

public class Main3 {
	public static void main(String[] args) throws MovimentoInvalidoException {
		Scanner teclado = new Scanner(System.in);
		Random random = new Random();
		String[] direcoes = {"up", "down", "left", "right"};
		
		System.out.println("Seja bem-vindo ao jogo dos dois robôs!");
		System.out.println("O segundo robo é inteligente, ele não repete um movimento " +
				"invalido!!");
		
		System.out.print("Digite a cor do primeiro robô: ");
		String cor1 = teclado.nextLine();
		Robo robo1 = new Robo(cor1);
		
		System.out.println("Digite a cor do segundo robô:");
		String cor2 = teclado.nextLine();
		
		while (cor2.equalsIgnoreCase(cor1)) {
			System.out.println("Essa cor já foi usada. Digite uma cor diferente:");
			cor2 = teclado.nextLine();
		}
		
		Robo robo2 = new RoboInteligente(cor2);
		
		System.out.print("Digite a posição X do alimento: ");
		int alimentoX = teclado.nextInt();
		System.out.print("Digite a posição Y do alimento: ");
		int alimentoY = teclado.nextInt();
		
		Robo vencedor = null;
		boolean robo1Achou = false;
		boolean robo2Achou = false;
		
		while (true) {
			
			if (!robo1Achou) {
				String direcao1 = direcoes[random.nextInt(4)];
				try {
					robo1.moverRobo(direcao1);
					System.out.println("Robô " + robo1.getCor() + " moveu para " + direcao1 +
							" Posição atual: (" + robo1.getPosicaoX() + "," + robo1.getPosicaoY() + ")");
				} catch (MovimentoInvalidoException e) {
					System.out.println("Robô " + robo1.getCor() + " tentou um movimento inválido: " + e.getMessage());
				}
				
				if (robo1.encontrarAlimento(alimentoX, alimentoY)) {
					System.out.println("O robo " + robo1.getCor() + " achou o alimento!");
					robo1Achou = true;
					if (vencedor == null) {
						vencedor = robo1;
					}
				}
			}
			
			try {
				Thread.sleep(3500);
			} catch (InterruptedException e) {
			}
			
			if (!robo2Achou) {
				if (!robo2Achou) {
					String direcao2 = direcoes[random.nextInt(4)];
					boolean moveu = robo2.moverRobo(direcao2);
					
					String direcaoUsada = ((RoboInteligente) robo2).getUltimaDirecaoUsada();
					
					if (moveu) {
						if (direcao2.equals(direcaoUsada)) {
							System.out.println("Robô " + robo2.getCor() + " moveu para " + direcaoUsada +
									" Posição atual: (" + robo2.getPosicaoX() + "," + robo2.getPosicaoY() + ")");
						} else {
							System.out.println("Robô " + robo2.getCor() + " tentou '" + direcao2 + "' (inválido), " +
									"mas usou '" + direcaoUsada + "' no lugar. Essa direção inválida não será repetida.");
							System.out.println("Nova posição: (" + robo2.getPosicaoX() + "," + robo2.getPosicaoY() + ")");
						}
					} else {
						System.out.println("Robô " + robo2.getCor() + " não conseguiu se mover em nenhuma direção válida.");
					}
					
					if (robo2.encontrarAlimento(alimentoX, alimentoY)) {
						System.out.println("O robô " + robo2.getCor() + " achou o alimento!");
						robo2Achou = true;
						if (vencedor == null) {
							vencedor = robo2;
						}
					}
				}
				
			}
			
			if (robo1Achou && robo2Achou) {
				break;
			}
			
			try {
				Thread.sleep(3500);
			} catch (InterruptedException e) {
			}
		}
		
		System.out.println("\nO robô vencedor foi o de cor: " + vencedor.getCor());
		System.out.println("Movimentos do robô " + robo1.getCor() + ": válidos = " + robo1.getMovimentosValidos() +
				", inválidos = " + robo1.getMovimentosInvalidos());
		System.out.println("Movimentos do robô " + robo2.getCor() + ": válidos = " + robo2.getMovimentosValidos() +
				", inválidos = " + robo2.getMovimentosInvalidos());
	}
}
