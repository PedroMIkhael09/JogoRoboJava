public class Main1 {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("Seja bem vindo ao jogo do robo! ");
		System.out.println("Digite a cor do seu robo: ");
		String cor = teclado.nextLine();
		Robo robo = new Robo(cor);
		
		System.out.println("Escolha uma posição no eixo X para o alimento: ");
		int posicaoXAlimento = teclado.nextInt();
		
		System.out.println("Escolha uma posição no eixo Y para o alimento: ");
		int posicaoYAlimento = teclado.nextInt();
		
		
		
	}
}