package simulacao;

import java.util.Random;

public class PontoOnibus extends ObjetoSimulacao {
    public PontoOnibus( Localizacao localizacao, Mapa mapa, Random rng) {
        super(Recurso.PONTO_ONIBUS.getImagem(), localizacao, mapa, Mapa.Camada.FOREGROUND, rng);
    }
    @Override
    public void executarAcao(){
        // nao faz nada
    }
    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        return true;
    }
}
