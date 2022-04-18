package simulacao;

import java.util.Random;

import simulacao.PontoOnibus.PontoOnibusCalcada;

public class PedestreOnibus extends PedestreAmbulante {
    private enum Estado {
        AntesOnibus,
        Onibus,
        AposOnibus,
    } 
    Estado estado;
    private PontoOnibusCalcada pontoDestino;

    public PedestreOnibus(Localizacao localizacao, Localizacao destino, Mapa mapa, Random rng) {
        super(localizacao, null, mapa, rng);
        this.estado = Estado.AntesOnibus;
        setPontoDeDestino(getPontoMaisProximo());
    }

    private void setPontoDeDestino(PontoOnibusCalcada ponto) {
        pontoDestino = ponto;
        if (ponto != null) {
            setLocalizacaoDestino(ponto.getLocalizacao());
        }
    }

    private PontoOnibusCalcada getPontoMaisProximo() {
        return (PontoOnibusCalcada) getMapa().getPontoDeInteresseMaisProximo(this, Mapa.PontoDeInteresse.PONTO_ONIBUS_CALCADA);
    }

    @Override
    public void fimDeRota() {
        if (estado == Estado.AntesOnibus) {
            if (pontoDestino.temOnibus()) {
                estado = Estado.Onibus;
                if (getLocalizacao().equals(pontoDestino.getLocalizacao())) {
                    ObjetoSimulacao o = getMapa().getObjetoMiddle(pontoDestino.getPontoRua().getLocalizacao());
                    if (o != null && o instanceof Onibus) {
                        if (((Onibus) o).receberPassageiro(this)) {
                            getEstatisticas().pedestreEntrouNoOnibus();
                            getMapa().removerObjeto(this);
                        }
                    }
                }
            }
        }
        else if (estado == Estado.Onibus) {
            //se esta no desitno e onibus ta no ponto 
        }
    }
}
