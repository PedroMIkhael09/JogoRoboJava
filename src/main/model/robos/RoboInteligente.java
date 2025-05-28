package main.model.robos;

import main.exception.MovimentoInvalidoException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RoboInteligente extends Robo {
	
	private final List<String> direcoesInvalidas = new ArrayList<>();
	
	public RoboInteligente(String cor) {
		super(cor);
	}
	
	@Override
	public boolean mover(String direcao) {
		String direcaoTentada = direcao.toLowerCase();
		
		// Se a direção já foi marcada como impossível (posição negativa), nem tenta
		if (direcoesInvalidas.contains(direcaoTentada)) {
			movimentosInvalidos++;
			return tentarOutrasDirecoes(direcaoTentada);
		}
		
		try {
			boolean sucesso = super.mover(direcaoTentada);
			
			if (sucesso) {
				// Movimento bem-sucedido
				return true;
			} else {
				// Movimento para fora dos limites (acima de 3), tenta outra direção, mas não marca como inválida
				return tentarOutrasDirecoes(direcaoTentada);
			}
			
		} catch (MovimentoInvalidoException e) {
			// Movimento inválido (posição negativa), registra como direção proibida
			direcoesInvalidas.add(direcaoTentada);
			movimentosInvalidos++;
			return tentarOutrasDirecoes(direcaoTentada);
		}
	}
	
	private boolean tentarOutrasDirecoes(String direcaoQueFalhou) {
		List<String> direcoesPossiveis = new ArrayList<>(Arrays.asList("up", "down", "left", "right"));
		direcoesPossiveis.removeAll(direcoesInvalidas);
		
		Collections.shuffle(direcoesPossiveis);
		
		for (String novaDirecao : direcoesPossiveis) {
			try {
				boolean sucesso = super.mover(novaDirecao);
				
				if (sucesso) {
					return true;
				}
				// Se não for sucesso (passou do limite superior), tenta outra
				// Mas não marca como inválida, porque não é erro crítico
				
			} catch (MovimentoInvalidoException e) {
				direcoesInvalidas.add(novaDirecao);
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
	
	public List<String> getDirecoesInvalidas() {
		return new ArrayList<>(direcoesInvalidas);
	}
	
	public void resetarDirecoesInvalidas() {
		direcoesInvalidas.clear();
	}
}
