package simulacao;

import java.awt.Image;
import java.util.Random;

public abstract class ObjetoSimulacao {
    // Imagem que representa o objeto no mapa
    private Image imagem;
    // Localização atual do objeto no mapa
    private Localizacao locAtual;
    // Localização anterior do objeto no mapa
    private Localizacao locAnterior;
    // Visão do mapa
    private Mapa mapa;
    // Camada do objeto no mapa
    private Mapa.Camada camada;
    // RNG
    private Random rng;

    public ObjetoSimulacao(Image imagem, Localizacao localizacao, Mapa mapa, Mapa.Camada camada, Random rng) {
        this.imagem = imagem;
        this.locAtual = localizacao;
        this.locAnterior = localizacao;
        this.mapa = mapa;
        this.camada = camada;
        this.rng = rng;
    }

    public Mapa.Camada getCamada() {
        return camada;
    }

    public Image getImagem() {
        return imagem;
    }

    public void setImagem(Image imagem) {
        this.imagem = imagem;
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
