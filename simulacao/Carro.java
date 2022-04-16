package simulacao;

import java.util.Random;

public class Carro extends Veiculo {

    public Carro(Localizacao localizacao, Localizacao destino, Mapa mapa, Random rng) {
        super(Recurso.CARRO.getImagem(), localizacao, destino, mapa, rng);
    }
}

