package simulacao;

import simulacao.Mapa.Camada;

public class Calcada extends ObjetoSimulacao {
    public Calcada(Localizacao localizacao, Mapa mapa) {
        super(Recurso.SEMAFORO_VERMELHO.getImagem(), localizacao, mapa, Camada.BACKGROUND, null);
    }

    @Override
    public void executarAcao() { }

    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        return !(o instanceof Veiculo) && !(o instanceof FantasmaVeiculo);
    }
}
