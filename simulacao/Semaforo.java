package simulacao;

import java.util.Random;

public class Semaforo extends ObjetoSimulacao {
    private int contador;

    public Semaforo(Localizacao localizacao, Mapa mapa, Random rng) {
        super(Recurso.CARRO.getImagem(), localizacao, mapa, Mapa.Camada.MIDDLE, rng);
    }

    @Override
    public void executarAcao() {
        contador++;
    }

    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        return contador % 10 < 5;
    }
}
