package main;

import main.exception.MovimentoInvalidoException;
import main.obstaculos.Bomba;
import main.obstaculos.Obstaculo;
import main.obstaculos.Rocha;
import main.robos.Robo;
import main.robos.RoboInteligente;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main4 {
    
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        
        System.out.println("Digite a cor do primeiro robô:");
        String cor1 = sc.nextLine().toLowerCase();
        Robo roboNormal = new Robo(cor1);
        
        System.out.println("Digite a cor do segundo robô (inteligente):");
        String cor2 = sc.nextLine().toLowerCase();
        while (cor2.equalsIgnoreCase(cor1)) {
            System.out.println("Essa cor já foi usada. Digite uma cor diferente:");
            cor2 = sc.nextLine().toLowerCase();
        }
        RoboInteligente roboInteligente = new RoboInteligente(cor2);
        
        System.out.println("Digite a posição X do alimento:");
        int alimentoX = sc.nextInt();
        System.out.println("Digite a posição Y do alimento:");
        int alimentoY = sc.nextInt();
        
        List<Obstaculo> obstaculos = new ArrayList<>();
        
        System.out.println("Quantas rochas deseja adicionar?");
        int qtdRochas = sc.nextInt();
        for (int i = 0; i < qtdRochas; i++) {
            System.out.println("Posição X da rocha " + (i + 1) + ":");
            int x = sc.nextInt();
            System.out.println("Posição Y da rocha " + (i + 1) + ":");
            int y = sc.nextInt();
            obstaculos.add(new Rocha(x, y));
        }
        
        System.out.println("Quantas bombas deseja adicionar?");
        int qtdBombas = sc.nextInt();
        for (int i = 0; i < qtdBombas; i++) {
            System.out.println("Posição X da bomba " + (i + 1) + ":");
            int x = sc.nextInt();
            System.out.println("Posição Y da bomba " + (i + 1) + ":");
            int y = sc.nextInt();
            obstaculos.add(new Bomba(x, y));
        }
        
        sc.nextLine();
        
        System.out.println();
        System.out.println("Jogo iniciado!");
        System.out.println("Robô " + roboNormal.getCor() + " começa na posição (0, 0)");
        System.out.println("Robô " + roboInteligente.getCor() + " começa na posição (0, 0)");
        
        boolean alimentoEncontrado = false;
        boolean roboNormalAtivo = true;
        boolean roboInteligenteAtivo = true;
        
        String[] direcoes = {"up", "down", "left", "right"};
        
        while (true) {
            if (roboNormalAtivo && !alimentoEncontrado) {
                System.out.println();
                System.out.println("Robô " + roboNormal.getCor() + " está na posição (" + roboNormal.getPosicaoX() + ", " + roboNormal.getPosicaoY() + ")");
                
                String direcao = direcoes[random.nextInt(4)];
                int posAnteriorX = roboNormal.getPosicaoX();
                int posAnteriorY = roboNormal.getPosicaoY();
                
                try {
                    boolean moveu = roboNormal.mover(direcao);
                    
                    if (moveu) {
                        
                        Obstaculo obstaculoParaRemover = null;
                        
                        for (Obstaculo o : obstaculos) {
                            if (roboNormal.getPosicaoX() == o.getPosicaoX() &&
                                    roboNormal.getPosicaoY() == o.getPosicaoY()) {
                                
                                o.bater(roboNormal);
                                
                                if (o instanceof Bomba) {
                                    System.out.println("💥 Robô " + roboNormal.getCor() + " explodiu ao bater na BOMBA em (" + o.getPosicaoX() + ", " + o.getPosicaoY() + ")");
                                    obstaculoParaRemover = o;
                                    roboNormalAtivo = false;
                                    Thread.sleep(2000);
                                } else if (o instanceof Rocha) {
                                    System.out.println("⚠️ Robô " + roboNormal.getCor() + " bateu em uma ROCHA em (" + o.getPosicaoX() + ", " + o.getPosicaoY() + ") e voltou para a posição anterior.");
                                    roboNormal.setPosicaoX(posAnteriorX);
                                    roboNormal.setPosicaoY(posAnteriorY);
                                    Thread.sleep(2000);
                                }
                                
                                break;
                            }
                        }
                        
                        if (obstaculoParaRemover != null) {
                            obstaculos.remove(obstaculoParaRemover);
                        }
                        
                        if (roboNormal.encontrarAlimento(alimentoX, alimentoY)) {
                            System.out.println("Robô " + roboNormal.getCor() + " encontrou o alimento em (" + alimentoX + ", " + alimentoY + ")");
                            alimentoEncontrado = true;
                            Thread.sleep(2000);
                        } else if (roboNormalAtivo) {
                            System.out.println("Robô " + roboNormal.getCor() + " moveu para (" + roboNormal.getPosicaoX() + ", " + roboNormal.getPosicaoY() + ") na direção " + direcao);
                        }
                    }
                } catch (MovimentoInvalidoException e) {
                    System.out.println("Robô " + roboNormal.getCor() + " tentou movimento inválido: " + e.getMessage());
                }
                
                Thread.sleep(2000);
            }
            
            if (alimentoEncontrado || (!roboNormalAtivo && !roboInteligenteAtivo)) {
                break;
            }
            
            if (roboInteligenteAtivo && !alimentoEncontrado) {
                System.out.println();
                System.out.println("Robô " + roboInteligente.getCor() + " está na posição (" + roboInteligente.getPosicaoX() + ", " + roboInteligente.getPosicaoY() + ")");
                
                String direcaoAleatoria = direcoes[random.nextInt(4)];
                int posAnteriorX = roboInteligente.getPosicaoX();
                int posAnteriorY = roboInteligente.getPosicaoY();
                
                boolean moveu = roboInteligente.mover(direcaoAleatoria);
                
                if (moveu) {
                    
                    Obstaculo obstaculoParaRemover = null;
                    
                    for (Obstaculo o : obstaculos) {
                        if (roboInteligente.getPosicaoX() == o.getPosicaoX() &&
                                roboInteligente.getPosicaoY() == o.getPosicaoY()) {
                            
                            o.bater(roboInteligente);
                            
                            if (o instanceof Bomba) {
                                System.out.println("💥 Robô " + roboInteligente.getCor() + " explodiu ao bater na BOMBA em (" + o.getPosicaoX() + ", " + o.getPosicaoY() + ")");
                                obstaculoParaRemover = o;
                                roboInteligenteAtivo = false;
                                Thread.sleep(2000);
                            } else if (o instanceof Rocha) {
                                System.out.println("⚠️ Robô " + roboInteligente.getCor() + " bateu em uma ROCHA em (" + o.getPosicaoX() + ", " + o.getPosicaoY() + ") e voltou para a posição anterior.");
                                roboInteligente.setPosicaoX(posAnteriorX);
                                roboInteligente.setPosicaoY(posAnteriorY);
                                Thread.sleep(2000);
                            }
                            
                            break;
                        }
                    }
                    
                    if (obstaculoParaRemover != null) {
                        obstaculos.remove(obstaculoParaRemover);
                    }
                    
                    if (roboInteligente.encontrarAlimento(alimentoX, alimentoY)) {
                        System.out.println("Robô " + roboInteligente.getCor() + " encontrou o alimento em (" + alimentoX + ", " + alimentoY + ")");
                        alimentoEncontrado = true;
                        Thread.sleep(2000);
                    } else if (roboInteligenteAtivo) {
                        System.out.println("Robô " + roboInteligente.getCor() + " moveu para (" + roboInteligente.getPosicaoX() + ", " + roboInteligente.getPosicaoY() + ") na direção " + direcaoAleatoria);
                    }
                } else {
                    System.out.println("Robô " + roboInteligente.getCor() + " não conseguiu se mover em nenhuma direção válida.");
                }
                
                Thread.sleep(2000);
            }
            
            if (alimentoEncontrado || (!roboNormalAtivo && !roboInteligenteAtivo)) {
                break;
            }
        }
        
        System.out.println();
        System.out.println("=== FIM DO JOGO ===");
        if (alimentoEncontrado) {
            System.out.println("Alimento foi encontrado!");
        } else {
            System.out.println("Nenhum robô conseguiu encontrar o alimento.");
        }
        
        System.out.println("Robô " + roboNormal.getCor() + " está " + (roboNormalAtivo ? "ativo" : "destruído") +
                ". Movimentos válidos: " + roboNormal.getMovimentosValidos() + ", inválidos: " + roboNormal.getMovimentosInvalidos());
        
        System.out.println("Robô " + roboInteligente.getCor() + " está " + (roboInteligenteAtivo ? "ativo" : "destruído") +
                ". Movimentos válidos: " + roboInteligente.getMovimentosValidos() + ", inválidos: " + roboInteligente.getMovimentosInvalidos());
    }
}
