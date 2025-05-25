package main;

import main.exception.MovimentoInvalidoException;
import main.robos.Robo;
import main.robos.RoboNormal;
import main.robos.RoboInteligente;

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
		String cor1 = teclado.nextLine().toLowerCase();
		Robo roboNormal = new RoboNormal(cor1);
		
		System.out.println("Digite a cor do segundo robô:");
		String cor2 = teclado.nextLine().toLowerCase();
		
		while (cor2.equalsIgnoreCase(cor1)) {
			System.out.println("Essa cor já foi usada. Digite uma cor diferente:");
			cor2 = teclado.nextLine();
		}
		
		RoboInteligente roboInteligente = new RoboInteligente(cor2);
		
		System.out.print("Digite a posição X do alimento: ");
		int alimentoX = teclado.nextInt();
		System.out.print("Digite a posição Y do alimento: ");
		int alimentoY = teclado.nextInt();
		
		System.out.println("O robo Normal começa na posição: (" + roboNormal.getPosicaoX() + ", " + roboNormal.getPosicaoY() + "");
		
		System.out.println("O robo Inteligente começa na posição: (" + roboInteligente.getPosicaoX() +
				", " + roboInteligente.getPosicaoY() + "");
		Robo vencedor = null;
		boolean roboNormalAchou = false;
		boolean roboInteligenteAchou = false;
		
		while (true) {
			
			if (!roboNormalAchou) {
				String direcao1 = direcoes[random.nextInt(4)];
				try {
					roboNormal.moverRobo(direcao1);
					System.out.println("Robô " + roboNormal.getCor() + " moveu para " + direcao1 +
							" Posição atual: (" + roboNormal.getPosicaoX() + "," + roboNormal.getPosicaoY() + ")");
				} catch (MovimentoInvalidoException e) {
					System.out.println("Robô " + roboNormal.getCor() + " tentou um movimento inválido: " + e.getMessage());
				}
				
				if (roboNormal.encontrarAlimento(alimentoX, alimentoY)) {
					System.out.println("O robo " + roboNormal.getCor() + " achou o alimento!");
					roboNormalAchou = true;
					if (vencedor == null) {
						vencedor = roboNormal;
					}
				}
			}
			
			try {
				Thread.sleep(3500);
			} catch (InterruptedException e) {
			}
			
			if (!roboInteligenteAchou) {
                String direcao2 = direcoes[random.nextInt(4)];
                boolean moveu = roboInteligente.moverRobo(direcao2);

                String direcaoUsada = roboInteligente.getUltimaDirecaoUsada();

                if (moveu) {
                    if (direcao2.equals(direcaoUsada)) {
                        System.out.println("Robô " + roboInteligente.getCor() + " moveu para " + direcaoUsada +
                                " Posição atual: (" + roboInteligente.getPosicaoX() + "," + roboInteligente.getPosicaoY() + ")");
                    } else {
                        System.out.println("Robô " + roboInteligente.getCor() + " tentou '" + direcao2 + "' (inválido), " +
                                "mas usou '" + direcaoUsada + "' no lugar. Essa direção inválida não será repetida.");
                        System.out.println("Nova posição: (" + roboInteligente.getPosicaoX() + "," + roboInteligente.getPosicaoY() + ")");
                    }
                } else {
                    System.out.println("Robô " + roboInteligente.getCor() + " não conseguiu se mover em nenhuma direção válida.");
                }

                if (roboInteligente.encontrarAlimento(alimentoX, alimentoY)) {
                    System.out.println("O robô " + roboInteligente.getCor() + " achou o alimento!");
                    roboInteligenteAchou = true;
                    if (vencedor == null) {
                        vencedor = roboInteligente;
                    }
                }

            }
			
			if (roboNormalAchou && roboInteligenteAchou) {
				break;
			}
			
			try {
				Thread.sleep(3500);
			} catch (InterruptedException e) {
			}
		}
		
		System.out.println("\nO robô vencedor foi o de cor: " + vencedor.getCor());
		System.out.println("Movimentos do robô " + roboNormal.getCor() + ": válidos = " + roboNormal.getMovimentosValidos() +
				", inválidos = " + roboNormal.getMovimentosInvalidos());
		System.out.println("Movimentos do robô " + roboInteligente.getCor() + ": válidos = " + roboInteligente.getMovimentosValidos() +
				", inválidos = " + roboInteligente.getMovimentosInvalidos());
	}
}
