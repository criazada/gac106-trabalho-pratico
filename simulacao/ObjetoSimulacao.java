package simulacao;

import java.awt.Image;

public abstract class ObjetoSimulacao {
    private Image imagem;
    private Localizacao localizacao;
    private Mapa mapa;
    private Mapa.Camada camada;

    public ObjetoSimulacao(Image imagem, Localizacao localizacao, Mapa mapa, Mapa.Camada camada) {
        this.imagem = imagem;
        this.localizacao = localizacao;
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
        return localizacao;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public void setLocalizacao(Localizacao localizacao) {
        Localizacao anterior = this.localizacao;
        this.localizacao = localizacao;
        mapa.atualizarMapa(getCamada(), this, anterior);
    }

    public abstract void executarAcao();
}
