package simulacao;

import java.awt.Image;

public abstract class ObjetoAmbulante extends ObjetoSimulacao {
    private Localizacao localizacaoDestino;

    public ObjetoAmbulante(Image imagem, Localizacao localizacao, Mapa mapa) {
        super(imagem, localizacao, mapa, Mapa.Camada.FOREGROUND);
        localizacaoDestino = null;
    }

    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }

    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
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
}
