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

    protected Localizacao gerarCandidatoLocalizacaoDestino() {
        return getMapa().getRuaAleatoria(getRng());
    }

    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        if (localizacaoDestino != null) {
            this.localizacaoDestino = localizacaoDestino;
            atualizarRota();
        } else {
            int i;
            for (i = 0; caminho == null || (i < 10 && caminho.size() == passo); i++) {
                this.localizacaoDestino = gerarCandidatoLocalizacaoDestino();
                atualizarRota();
            }
            if (i == 10) {
                getMapa().removerObjeto(this);
            }
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
            int l = getMapa().getLargura();
            int a = getMapa().getAltura();
            int x = prox.getX();
            int y = prox.getY();
            if (x < 0 || x >= l || y < 0 || y >= a) return;

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

    public void fimDeRota() {
        setLocalizacaoDestino(null);
    }

    public boolean andarComRaivaPara(ObjetoSimulacao o, Direcao d) {
        return o instanceof Rua && livre(o.getLocalizacao());
    }
}
