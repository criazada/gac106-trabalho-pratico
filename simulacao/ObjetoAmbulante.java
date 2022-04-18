package simulacao;

import java.awt.Image;
import java.util.List;
import java.util.Random;

import simulacao.Mapa.TipoGrafo;

public abstract class ObjetoAmbulante extends ObjetoSimulacao {
    private Localizacao localizacaoDestino;
    private List<Localizacao> caminho;
    private int passo;
    private TipoGrafo grafo;
    private int raiva;

    public ObjetoAmbulante(Image imagem, Localizacao localizacao, Localizacao localizacaoDestino, Mapa mapa, TipoGrafo grafo, Random rng) {
        super(imagem, localizacao, mapa, Mapa.Camada.MIDDLE, Mapa.PontoDeInteresse.EXECUTAVEL, rng);
        this.grafo = grafo;
        setLocalizacaoDestino(localizacaoDestino);
    }

    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }

    public TipoGrafo getTipoGrafo() {
        return grafo;
    }

    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
        if (localizacaoDestino != null) {
            atualizarRota();
        }
    }

    public void atualizarRota() {
        caminho = getMapa().getCaminhoParaDestino(this);
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
        if (caminho != null && passo < caminho.size()) {
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

    @Override
    public void executarAcao() {
        Localizacao destino = getLocalizacaoDestino();
        if (destino == null) return;
        Localizacao prox = proximaLocalizacao();

        if (prox == getLocalizacao()) {
            fimDeRota();
        }
        // objeto só anda se o espaço de destino está livre
        else if (livre(prox)) {
            setLocalizacao(prox);
            incrementarPasso();
            raiva = 0;
        } else {
            raiva++;
        }

        if (raiva >= 5) {
            Direcao d = Direcao.TODAS[getRng().nextInt(4)];
            prox = d.seguindo(getLocalizacao());
            boolean andar = true;
            for (ObjetoSimulacao o : getMapa().getObjetosEm(prox)) {
                if (o != null && !andarComRaivaPara(o, d)) {
                    andar = false;
                }
            }
            if (andar) {
                setLocalizacao(prox);
                atualizarRota();
                raiva = 0;
            }
        }
    }

    public void fimDeRota() {}

    public boolean andarComRaivaPara(ObjetoSimulacao o, Direcao d) {
        return o instanceof Rua && livre(o.getLocalizacao());
    }
}
