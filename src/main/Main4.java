package main;

import main.exception.MovimentoInvalidoException;
import main.obstaculos.Bomba;
import main.obstaculos.Rocha;
import main.robos.RoboNormal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main4 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Criar robô
        System.out.println("Digite a cor do robo:");
        String cor = sc.nextLine();
        RoboNormal robo = new RoboNormal(cor);

        // Definir posição do alimento
        System.out.println("Digite a posição X do alimento:");
        int alimentoX = sc.nextInt();
        System.out.println("Digite a posição Y do alimento:");
        int alimentoY = sc.nextInt();

        // Listas de obstáculos
        List<Rocha> rochas = new ArrayList<>();
        List<Bomba> bombas = new ArrayList<>();

        // Adicionar Rochas
        System.out.println("Quantas rochas deseja adicionar?");
        int quantidadeRochas = sc.nextInt();

        for (int i = 0; i < quantidadeRochas; i++) {
            System.out.println("Posição X da rocha " + (i + 1) + ":");
            int x = sc.nextInt();
            System.out.println("Posição Y da rocha " + (i + 1) + ":");
            int y = sc.nextInt();
            rochas.add(new Rocha(x, y));
        }

        // Adicionar Bombas
        System.out.println("Quantas bombas deseja adicionar?");
        int quantidadeBombas = sc.nextInt();

        for (int i = 0; i < quantidadeBombas; i++) {
            System.out.println("Posição X da bomba " + (i + 1) + ":");
            int x = sc.nextInt();
            System.out.println("Posição Y da bomba " + (i + 1) + ":");
            int y = sc.nextInt();
            bombas.add(new Bomba(x, y));
        }

        sc.nextLine(); // Consumir quebra de linha pendente

        // Variáveis para guardar a posição anterior
        int posAnteriorX = robo.getPosicaoX();
        int posAnteriorY = robo.getPosicaoY();

        // Loop de movimentação
        while (true) {
            System.out.println("\nPosição atual do robo: (" + robo.getPosicaoX() + ", " + robo.getPosicaoY() + ")");
            System.out.println("Digite a direção para mover (up, down, left, right) ou 'sair' para encerrar:");
            String direcao = sc.nextLine();

            if (direcao.equalsIgnoreCase("sair")) {
                break;
            }

            try {
                // Guardar posição atual antes de mover
                posAnteriorX = robo.getPosicaoX();
                posAnteriorY = robo.getPosicaoY();

                boolean moveu = robo.moverRobo(direcao);

                if (moveu) {
                    // Verificar colisão com rochas
                    boolean bateuRocha = false;
                    for (Rocha rocha : rochas) {
                        if (robo.getPosicaoX() == rocha.getPosicaoX() && robo.getPosicaoY() == rocha.getPosicaoY()) {
                            System.out.println("⚠️ Robo bateu em uma ROCHA na posição (" + rocha.getPosicaoX() + ", " + rocha.getPosicaoY() + ")!");
                            // Voltar para a posição anterior
                            robo.setPosicaoX(posAnteriorX);
                            robo.setPosicaoY(posAnteriorY);
                            System.out.println("↩️ Robo voltou para a posição anterior: (" + posAnteriorX + ", " + posAnteriorY + ")");
                            bateuRocha = true;
                            break;
                        }
                    }

                    // Se bateu em rocha, pular as outras verificações
                    if (bateuRocha) {
                        continue;
                    }

                    // Verificar colisão com bombas
                    boolean bateuBomba = false;
                    for (Bomba bomba : bombas) {
                        if (robo.getPosicaoX() == bomba.getPosicaoX() && robo.getPosicaoY() == bomba.getPosicaoY()) {
                            System.out.println("💥 Robo EXPLODIU em uma BOMBA na posição (" + bomba.getPosicaoX() + ", " + bomba.getPosicaoY() + ")!");
                            bateuBomba = true;
                            break;
                        }
                    }

                    // Verificar se encontrou o alimento
                    if (robo.encontrarAlimento(alimentoX, alimentoY)) {
                        System.out.println("🍎 O robo encontrou o ALIMENTO na posição (" + alimentoX + ", " + alimentoY + ")!");
                        break;
                    }

                    if (bateuBomba) {
                        System.out.println("❌ Fim de jogo! O robo foi destruído.");
                        break;
                    }

                } else {
                    System.out.println("❌ Direção inválida.");
                }

            } catch (MovimentoInvalidoException e) {
                System.out.println("❌ Movimento inválido para a direção: " + e.getMessage());
            }
        }

        // Estatísticas finais
        System.out.println("\n📊 Estatísticas do Robo:");
        System.out.println("Movimentos válidos: " + robo.getMovimentosValidos());
        System.out.println("Movimentos inválidos: " + robo.getMovimentosInvalidos());
        System.out.println("Última direção usada: " + robo.getUltimaDirecaoUsada());
        System.out.println("Posição final do robo: (" + robo.getPosicaoX() + ", " + robo.getPosicaoY() + ")");
    }
}