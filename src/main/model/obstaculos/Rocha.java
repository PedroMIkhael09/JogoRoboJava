package main.model.obstaculos;

import main.model.robos.Robo;

public class Rocha extends Obstaculo {

    public Rocha(int posicaoX, int posicaoY) {
        super(posicaoX, posicaoY);
    }
    
    @Override
    public void bater(Robo robo) {
        robo.voltarPosicaoAnterior();
    }
}


