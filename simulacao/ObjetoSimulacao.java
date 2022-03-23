package simulacao;

import java.awt.Image;

public abstract class ObjetoSimulacao {
    private Image imagem;
    private Localizacao localizacao;
    private Mapa mapa;

    public ObjetoSimulacao(Image imagem, Localizacao localizacao, Mapa mapa) {
        this.imagem = imagem;
        this.localizacao = localizacao;
        this.mapa = mapa;
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
        mapa.atualizarMapa(this, anterior);
    }

    public abstract void  executarAcao() ;
}
