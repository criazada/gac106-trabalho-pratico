package simulacao;

import java.awt.Image;

public class Rua extends ObjetoSimulacao {
    private Direcao[] direcoes;
    
    public Rua(Direcao[] direcoes, Localizacao localizacao, Mapa mapa) {
        super((Image) null, localizacao, mapa, Mapa.Camada.BACKGROUND, null);
        Recurso r = null;
        Image[] imagens = new Image[direcoes.length + 1];
        imagens[0] = Recurso.FUNDO_RUA.getImagem();
        for (int i = 0; i < direcoes.length; i++) {
            imagens[i+1] = getSetaParaDirecao(direcoes[i]);
        }
        setImagens(imagens);
        this.direcoes = direcoes;
    }

    private Image getSetaParaDirecao(Direcao d) {
        if (d == null) return null;

        Recurso r;
        switch (d) {
            case NORTE:
                r = Recurso.SETA_NORTE;
                break;
            case SUL:
                r = Recurso.SETA_SUL;
                break;
            case LESTE:
                r = Recurso.SETA_LESTE;
                break;
            case OESTE:
                r = Recurso.SETA_OESTE;
                break;
            default:
                r = null;
        }

        return r.getImagem();
    }

    public Rua(Direcao direcao, Localizacao localizacao, Mapa mapa) {
        this(new Direcao[]{direcao}, localizacao, mapa);
    }

    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        if (o instanceof Fantasma) return false;
        Direcao desejada = Direcao.calcular(o.getLocalizacao(), getLocalizacao());
        for (Direcao d : direcoes) {
            if (d == desejada) return true;
        }
        return false;
    }

    @Override
    public void executarAcao() { }
}
