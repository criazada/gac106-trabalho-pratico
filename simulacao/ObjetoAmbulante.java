package simulacao;

import java.awt.Image;
import java.util.List;
import java.util.Random;

public abstract class ObjetoAmbulante extends ObjetoSimulacao {
    private Localizacao localizacaoDestino;
    private List<Localizacao> caminho;
    private int passo;
    private int tipo;

    public ObjetoAmbulante(Image imagem, Localizacao localizacao, Mapa mapa, Random rng, int tipo) {
        super(imagem, localizacao, mapa, Mapa.Camada.MIDDLE, rng);
        localizacaoDestino = null;
        this.tipo = tipo;
    }

    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }

    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
        atualizarRota();
    }

    public void atualizarRota() {
        if (tipo == 0) {
            caminho = getMapa().getCaminhoParaDestino((Veiculo) this);
        } else {
            caminho = getMapa().getCaminhoParaDestino((PedestreAmbulante) this);
        }
        passo = 0;
    }

    public void incrementarPasso() {
        passo++;
    }

    /**
     * Gera a localizacao para se mover visando alcançar o destino
     *
     * @return Localizacao para onde se deve ir
     */
    public Localizacao proximaLocalizacao() {
        if (passo < caminho.size()) {
            return caminho.get(passo);
        }
        return getLocalizacao();
    }

    /**
     * Verifica se a localização em questão está livre para este objeto.
     * @param l Localização
     */
    public boolean livre(Localizacao l) {
        for (ObjetoSimulacao o : getMapa().getObjetosEm(l)) {
            if (!o.transparentePara(this)) return false;
        }
        return true;
    }

    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        return o instanceof Fantasma;
    }
}
