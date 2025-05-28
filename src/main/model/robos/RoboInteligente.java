package main.model.robos;

import main.exception.MovimentoInvalidoException;
import java.util.*;

public class RoboInteligente extends Robo {
	
	// Mapa de direções inválidas por posição
	private final Map<String, List<String>> direcoesInvalidasPorPosicao = new HashMap<>();
	
	public RoboInteligente(String cor) {
		super(cor);
	}
	
	@Override
	public boolean mover(String direcao) {
		String direcaoTentada = direcao.toLowerCase();
		String posicaoAtual = posicaoX + "," + posicaoY;
		
		List<String> direcoesInvalidas = direcoesInvalidasPorPosicao.getOrDefault(posicaoAtual, new ArrayList<>());
		
		// Se a direção já foi considerada inválida nessa posição, nem tenta
		if (direcoesInvalidas.contains(direcaoTentada)) {
			movimentosInvalidos++;
			return tentarOutrasDirecoes();
		}
		
		try {
			boolean sucesso = super.mover(direcaoTentada);
			
			if (sucesso) {
				return true;
			} else {
				// Se bateu na borda superior (não exceção, mas movimento inválido), registra como inválido nesta posição
				registrarDirecaoInvalida(posicaoAtual, direcaoTentada);
				return tentarOutrasDirecoes();
			}
			
		} catch (MovimentoInvalidoException e) {
			// Movimento inválido (para posição negativa), também registra
			registrarDirecaoInvalida(posicaoAtual, direcaoTentada);
			movimentosInvalidos++;
			return tentarOutrasDirecoes();
		}
	}
	
	private void registrarDirecaoInvalida(String posicao, String direcao) {
		direcoesInvalidasPorPosicao
				.computeIfAbsent(posicao, k -> new ArrayList<>())
				.add(direcao);
	}
	
	private boolean tentarOutrasDirecoes() {
		String posicaoAtual = posicaoX + "," + posicaoY;
		List<String> direcoesInvalidas = direcoesInvalidasPorPosicao.getOrDefault(posicaoAtual, new ArrayList<>());
		
		List<String> direcoesPossiveis = new ArrayList<>(Arrays.asList("up", "down", "left", "right"));
		direcoesPossiveis.removeAll(direcoesInvalidas);
		
		Collections.shuffle(direcoesPossiveis);
		
		for (String novaDirecao : direcoesPossiveis) {
			try {
				boolean sucesso = super.mover(novaDirecao);
				
				if (sucesso) {
					return true;
				} else {
					registrarDirecaoInvalida(posicaoAtual, novaDirecao);
				}
				
			} catch (MovimentoInvalidoException e) {
				registrarDirecaoInvalida(posicaoAtual, novaDirecao);
				movimentosInvalidos++;
			}
		}
		// Não conseguiu se mover
		return false;
	}
	
	@Override
	public boolean mover(int numero) {
		String direcao;
		switch (numero) {
			case 1: direcao = "up"; break;
			case 2: direcao = "down"; break;
			case 3: direcao = "right"; break;
			case 4: direcao = "left"; break;
			default:
				movimentosInvalidos++;
				return false;
		}
		return mover(direcao);
	}
	
	public Map<String, List<String>> getDirecoesInvalidasPorPosicao() {
		return new HashMap<>(direcoesInvalidasPorPosicao);
	}
	
	public void resetarDirecoesInvalidas() {
		direcoesInvalidasPorPosicao.clear();
	}
}
