package simulacao;

import simulacao.Mapa.Camada;

public class Calcada extends ObjetoSimulacao {
    public Calcada(Localizacao localizacao, Mapa mapa) {
        super(Recurso.CALCADA.getImagem(), localizacao, mapa, Camada.BACKGROUND, Mapa.PontoDeInteresse.ESTATICO, null);
    }

    @Override
    public void executarAcao() { }

    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        return !(o instanceof Veiculo) && !(o instanceof FantasmaVeiculo);
    }
}
