import robos.Robo;
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
		String cor1 = teclado.nextLine();
		Robo robo1 = new Robo(cor1);
		
		System.out.println("Digite a cor do segundo robô:");
		String cor2 = teclado.nextLine();
		
		while (cor2.equalsIgnoreCase(cor1)) {
			System.out.println("Essa cor já foi usada. Digite uma cor diferente:");
			cor2 = teclado.nextLine();
		}
		
		Robo robo2 = new Robo(cor2);
		
		System.out.print("Digite a posição X do alimento: ");
		int alimentoX = teclado.nextInt();
		System.out.print("Digite a posição Y do alimento: ");
		int alimentoY = teclado.nextInt();
		
		Robo vencedor = null;
		
		while (true) {
			
			String direcao1 = direcoes[random.nextInt(4)];
			try {
				robo1.moverRobo(direcao1);
				System.out.println("Robô " + robo1.getCor() + " moveu para " + direcao1 +
						" Posição atual: (" + robo1.getPosicaoX() + "," + robo1.getPosicaoY() + ")");
			} catch (MovimentoInvalidoException e) {
				System.out.println("Robô " + robo1.getCor() + " tentou um movimento inválido: " + e.getMessage());
			}
			
			if (robo1.encontrarAlimento(alimentoX, alimentoY)) {
				vencedor = robo1;
				break;
			}
			
			
			try {
				Thread.sleep(3500);
			} catch (InterruptedException e) {
			}
			
		
			String direcao2 = direcoes[random.nextInt(4)];
			try {
				robo2.moverRobo(direcao2);
				System.out.println("Robô " + robo2.getCor() + " moveu para " + direcao2 +
						" Posição atual: (" + robo2.getPosicaoX() + "," + robo2.getPosicaoY() + ")");
			} catch (MovimentoInvalidoException e) {
				System.out.println("Robô " + robo2.getCor() + " tentou um movimento inválido: " + e.getMessage());
			}
			
			if (robo2.encontrarAlimento(alimentoX, alimentoY)) {
				vencedor = robo2;
				break;
			}
			
			
			try {
				Thread.sleep(3500);
			} catch (InterruptedException e) {
			
			}
		}
		
		System.out.println("\n O robô vencedor foi o de cor: " + vencedor.getCor());
		System.out.println("Movimentos do robô " + robo1.getCor() + ": válidos = " + robo1.getMovimentosValidos() +
				", inválidos = " + robo1.getMovimentosInvalidos());
		System.out.println("Movimentos do robô " + robo2.getCor() + ": válidos = " + robo2.getMovimentosValidos() +
				", inválidos = " + robo2.getMovimentosInvalidos());
	}
}
