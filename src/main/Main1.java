package main;

import main.exception.MovimentoInvalidoException;
import main.robos.Robo;

import java.util.Scanner;

public class Main1 {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("Seja bem-vindo ao jogo do robô!\n");
		System.out.println("Regras do jogo:");
		System.out.println("- Digite 1 ou 'up' para subir (Y + 1)");
		System.out.println("- Digite 2 ou 'down' para descer (Y - 1)");
		System.out.println("- Digite 3 ou 'right' para direita (X + 1)");
		System.out.println("- Digite 4 ou 'left' para esquerda (X - 1)");
		System.out.println("Faça isso até o robô encontrar o alimento!\n");
		
		System.out.print("Digite a cor do seu robô: ");
		String cor = teclado.nextLine();
		Robo roboNormal = new Robo(cor);
		
		System.out.print("Escolha a posição X do alimento: ");
		int posicaoXAlimento = teclado.nextInt();
		
		System.out.print("Escolha a posição Y do alimento: ");
		int posicaoYAlimento = teclado.nextInt();
		teclado.nextLine();
		
		System.out.println("------JOGO INICIADO------");
		System.out.println("O robô começa na posição: (" + roboNormal.getPosicaoX() + ", " + roboNormal.getPosicaoY() + ")");
		
		boolean encontrou = false;
		while (!encontrou) {
			System.out.print("Digite a direção (número ou texto): ");
			String input = teclado.nextLine();
			
			try {
				boolean mov;
				
				try {
					int numero = Integer.parseInt(input);
					mov = roboNormal.mover(numero);
				} catch (NumberFormatException e) {
					mov = roboNormal.mover(input.toLowerCase());
				}
				
				if (!mov) {
					System.out.println("Direção inválida! Use: 1-4 ou up/down/left/right.");
				} else {
					System.out.println("Robô na posição: (" + roboNormal.getPosicaoX() + ", " + roboNormal.getPosicaoY() + ")");
					encontrou = roboNormal.encontrarAlimento(posicaoXAlimento, posicaoYAlimento);
					if (encontrou) {
						System.out.println("=== FIM DO JOGO ===");
						System.out.println("Parabéns! O robô encontrou o alimento!");
					}
				}
			} catch (MovimentoInvalidoException e) {
				System.out.println(e.getMessage());
				System.out.println("Posição atual do robô: (" + roboNormal.getPosicaoX() + ", " + roboNormal.getPosicaoY() + ")");
			}
		}
		
		teclado.close();
	}
}
