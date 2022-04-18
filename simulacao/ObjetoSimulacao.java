package simulacao;

import java.awt.Image;
import java.util.Random;

public abstract class ObjetoSimulacao {
    // Imagem que representa o objeto no mapa
    private Image[] imagens;
    // Localização atual do objeto no mapa
    private Localizacao locAtual;
    // Localização anterior do objeto no mapa
    private Localizacao locAnterior;
    // Visão do mapa
    private Mapa mapa;
    // Camada do objeto no mapa
    private Mapa.Camada camada;
    private Mapa.PontoDeInteresse pdi;
    // RNG
    private Random rng;

    public ObjetoSimulacao(Image[] imagens, Localizacao localizacao, Mapa mapa, Mapa.Camada camada, Mapa.PontoDeInteresse tipo, Random rng) {
        this.imagens = imagens;
        this.locAtual = localizacao;
        this.locAnterior = localizacao;
        this.mapa = mapa;
        this.camada = camada;
        this.rng = rng;
        this.pdi = tipo;
        if (mapa != null) {
            mapa.adicionarObjeto(this);
            if (tipo != null) {
                mapa.addPontoDeInteresse(tipo, this);
            }
        }
    }

    public ObjetoSimulacao(Image imagem, Localizacao localizacao, Mapa mapa, Mapa.Camada camada, Mapa.PontoDeInteresse tipo, Random rng) {
        this(new Image[]{imagem}, localizacao, mapa, camada, tipo, rng);
    }

    public ObjetoSimulacao() {}

    public Mapa.Camada getCamada() {
        return camada;
    }

    public Image[] getImagens() {
        return imagens;
    }

    public void setImagem(Image imagem) {
        this.imagens = new Image[]{imagem};
    }

    public void setImagens(Image[] imagens) {
        this.imagens = imagens;
    }

    public Localizacao getLocalizacao() {
        return locAtual;
    }

    public Localizacao getLocalizacaoAnterior() {
        return locAnterior;
    }

    protected void setDirecao(Direcao direcao) {
        locAnterior = direcao.para(locAtual);
    }

    public Mapa getMapa() {
        return mapa;
    }

    public Random getRng() {
        return rng;
    }

    public Mapa.PontoDeInteresse getTipoPontoDeInteresse() {
        return pdi;
    }

    /**
     * Atualiza a localização do objeto, salvando a posição atual em sua posição
     * anterior.
     * @param localizacao Nova localização
     */
    public void setLocalizacao(Localizacao localizacao) {
        locAnterior = locAtual;
        locAtual = localizacao;
        if (mapa != null) {
            mapa.atualizarMapa(this);
        }
    }

    public Direcao calcularDirecao() {
        return Direcao.calcular(locAnterior, locAtual);
    }

    /**
     * Executa a ação do objeto em um passo da simulação
     */
    public abstract void executarAcao();
    
    /**
     * Verifica se este objeto é transparente para outro objeto.
     * Útil caso somente alguns tipos de objetos possam passar por este.
     * Mais relevante para objetos no background.
     * @param o O outro objeto
     * @return Transparência deste objeto na visão de outro objeto.
     */
    public abstract boolean transparentePara(ObjetoSimulacao o);
}
