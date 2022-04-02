package simulacao;

import java.awt.Image;

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

    public ObjetoSimulacao(Image imagem, Localizacao localizacao, Mapa mapa, Mapa.Camada camada) {
        this.imagem = imagem;
        this.locAtual = localizacao;
        this.locAnterior = localizacao;
        this.mapa = mapa;
        this.camada = camada;
    }

    public Mapa.Camada getCamada() {
        return camada;
    }

    public Image getImagem() {
        return imagem;
    }

    public Localizacao getLocalizacao() {
        return locAtual;
    }

    public Localizacao getLocalizacaoAnterior() {
        return locAnterior;
    }

    public Mapa getMapa() {
        return mapa;
    }

    /**
     * Atualiza a localização do objeto, salvando a posição atual em sua posição
     * anterior.
     * @param localizacao Nova localização
     */
    public void setLocalizacao(Localizacao localizacao) {
        locAnterior = locAtual;
        locAtual = localizacao;
        mapa.atualizarMapa(this);
    }

    /**
     * Executa a ação do objeto em um passo da simulação
     */
    public abstract void executarAcao();
}
