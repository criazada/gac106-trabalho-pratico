package simulacao;

import java.awt.Image;

public abstract class ObjetoAmbulante extends ObjetoSimulacao {
    private Localizacao localizacaoDestino;

    public ObjetoAmbulante(Image imagem, Localizacao localizacao, Mapa mapa) {
        super(imagem, localizacao, mapa);
        localizacaoDestino = null;
    }

    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }

    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }
}

