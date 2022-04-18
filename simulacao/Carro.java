package simulacao;

import java.util.Random;
import java.awt.Image;

public class Carro extends Veiculo {
    public Carro(Localizacao localizacao, Localizacao destino, Mapa mapa, Random rng) {
        super(selecionarImagem(rng.nextInt(3)), localizacao, destino, mapa, rng);
    }

    private static Image selecionarImagem(int rng) {
        switch(rng) {
            case 0:
                return Recurso.CARRO_1.getImagem();   
            case 1:
                return Recurso.CARRO_2.getImagem();               
            case 2:
                return Recurso.CARRO_3.getImagem();
            default:
                return Recurso.CARRO_1.getImagem();  
        }
    }
}

