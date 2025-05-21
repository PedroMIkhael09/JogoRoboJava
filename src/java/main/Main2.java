import robos.Robo;
import robos.RoboNormal;
import exception.MovimentoInvalidoException;
import java.util.Random;
import java.util.Scanner;

public class Main2 {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		Random random = new Random();
		String[] direcoes = {"up", "down", "left", "right"};
		
		System.out.println("Seja bem-vindo ao jogo dos dois robôs!");
		
		System.out.print("Digite a cor do primeiro robô: ");
		String cor1 = teclado.nextLine().toLowerCase();
		Robo roboNormal1 = new RoboNormal(cor1);
		
		System.out.println("Digite a cor do segundo robô:");
		String cor2 = teclado.nextLine().toLowerCase();
		
		while (cor2.equals(cor1)) {
			System.out.println("Essa cor já foi usada. Digite uma cor diferente:");
			cor2 = teclado.nextLine();
		}
		
		Robo roboNormal2 = new RoboNormal(cor2);
		
		System.out.print("Digite a posição X do alimento: ");
		int alimentoX = teclado.nextInt();
		System.out.print("Digite a posição Y do alimento: ");
		int alimentoY = teclado.nextInt();
		
		System.out.println("O primeiro Robo Normal começa na posição: (" + roboNormal1.getPosicaoX() + ", " + roboNormal1.getPosicaoY() + "");
		
		System.out.println("O segundo Robo Normal começa na posição: (" + roboNormal2.getPosicaoX() + ", " + roboNormal2.getPosicaoY() + "");
		
		Robo vencedor = null;
		
		while (true) {
			
			String direcao1 = direcoes[random.nextInt(4)];
			try {
				roboNormal1.moverRobo(direcao1);
				System.out.println("Robô " + roboNormal1.getCor() + " moveu para " + direcao1 +
						" Posição atual: (" + roboNormal1.getPosicaoX() + "," + roboNormal1.getPosicaoY() + ")");
			} catch (MovimentoInvalidoException e) {
				System.out.println("Robô " + roboNormal1.getCor() + " tentou um movimento inválido: " + e.getMessage());
			}
			
			if (roboNormal1.encontrarAlimento(alimentoX, alimentoY)) {
				vencedor = roboNormal1;
				break;
			}
			
			
			try {
				Thread.sleep(3500);
			} catch (InterruptedException e) {
			}
			
		
			String direcao2 = direcoes[random.nextInt(4)];
			try {
				roboNormal2.moverRobo(direcao2);
				System.out.println("Robô " + roboNormal2.getCor() + " moveu para " + direcao2 +
						" Posição atual: (" + roboNormal2.getPosicaoX() + "," + roboNormal2.getPosicaoY() + ")");
			} catch (MovimentoInvalidoException e) {
				System.out.println("Robô " + roboNormal2.getCor() + " tentou um movimento inválido: " + e.getMessage());
			}
			
			if (roboNormal2.encontrarAlimento(alimentoX, alimentoY)) {
				vencedor = roboNormal2;
				break;
			}
			
			
			try {
				Thread.sleep(3500);
			} catch (InterruptedException e) {
			
			}
		}
		
		System.out.println("\n O robô vencedor foi o de cor: " + vencedor.getCor());
		System.out.println("Movimentos do robô " + roboNormal1.getCor() + ": válidos = " + roboNormal1.getMovimentosValidos() +
				", inválidos = " + roboNormal1.getMovimentosInvalidos());
		System.out.println("Movimentos do robô " + roboNormal2.getCor() + ": válidos = " + roboNormal2.getMovimentosValidos() +
				", inválidos = " + roboNormal2.getMovimentosInvalidos());
	}
}
