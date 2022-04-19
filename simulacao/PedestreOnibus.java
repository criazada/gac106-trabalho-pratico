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
    private PontoOnibus pontoFinal;
    private Onibus onibus;

    public PedestreOnibus(Localizacao localizacao, Mapa mapa, Random rng) {
        super(localizacao, mapa, rng);
        this.estado = Estado.AntesOnibus;
        setPontoDeDestino(getPontoMaisProximo());
        Object[] pontos = getMapa().getPontosDeInteresse(Mapa.PontoDeInteresse.PONTO_ONIBUS).toArray();
        pontoFinal = (PontoOnibus) pontos[rng.nextInt(pontos.length)];
    }

    private void setPontoDeDestino(PontoOnibusCalcada ponto) {
        pontoDestino = ponto;
        if (ponto != null) {
            setLocalizacaoDestino(ponto.getLocalizacao());
        }
    }

    @Override
    protected Localizacao gerarCandidatoLocalizacaoDestino() {
        PontoOnibusCalcada[] poc = getMapa().getPontosDeInteresse(Mapa.PontoDeInteresse.PONTO_ONIBUS_CALCADA).toArray(new PontoOnibusCalcada[0]);
        return poc[getRng().nextInt(poc.length)].getLocalizacao();
    }

    private PontoOnibusCalcada getPontoMaisProximo() {
        return (PontoOnibusCalcada) getMapa().getPontoDeInteresseMaisProximo(this, Mapa.PontoDeInteresse.PONTO_ONIBUS_CALCADA);
    }

    @Override
    public void fimDeRota() {
        if (estado == Estado.AntesOnibus) {
            if (pontoDestino != null && pontoDestino.temOnibus()) {
                if (getLocalizacao().equals(pontoDestino.getLocalizacao())) {
                    ObjetoSimulacao o = getMapa().getObjetoMiddle(pontoDestino.getPontoRua().getLocalizacao());
                    if (o instanceof Onibus && ((Onibus) o).receberPassageiro(this)) {
                        getEstatisticas().pedestreEntrouNoOnibus();
                        getMapa().removerObjeto(this);
                        estado = Estado.Onibus;
                        onibus = (Onibus) o;
                    }
                }
            }
        } else if (estado == Estado.Onibus) {
            //se esta no desitno e onibus ta no ponto 
            getEstatisticas().pedestreNoOnibus();
            if (onibus.getLocalizacao().equals(pontoFinal.getLocalizacao())) {
                if (pontoFinal.getPontoCalcada() == null) return;
                if (livre(pontoFinal.getPontoCalcada().getLocalizacao()) && onibus.removerPassageiro(this)) {
                    getEstatisticas().pedestreSaiuDoOnibus();
                }
            }
        }
    }
}
