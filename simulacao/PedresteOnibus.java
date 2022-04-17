package simulacao;

import java.util.Random;

public class PedresteOnibus extends PedestreAmbulante {
    private Localizacao destino ;
    public PedresteOnibus(Localizacao localizacao, Localizacao destino, Mapa mapa, Random rng) {
        super( localizacao, PontoOnibus.pontoMaisProximo(localizacao), mapa, rng);
        this.destino = destino;
    }



}
