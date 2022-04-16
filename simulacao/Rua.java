package simulacao;

import java.awt.Image;

public class Rua extends ObjetoSimulacao {
    private Direcao[] direcoes;
    private boolean faixa;

    public Rua(Direcao[] direcoes, boolean faixa, Localizacao localizacao, Mapa mapa) {
        super((Image) null, localizacao, mapa, Mapa.Camada.BACKGROUND, null);
        Image[] imagens = new Image[direcoes.length + 3];
        imagens[0] = Recurso.FUNDO_RUA.getImagem();
        imagens[1] = faixa ? Recurso.FAIXA.getImagem() : null;
        for (int i = 0; i < direcoes.length; i++) {
            imagens[i+2] = getSetaParaDirecao(direcoes[i]);
        }
        setImagens(imagens);
        this.direcoes = direcoes;
        this.faixa = faixa;
    }

    public Rua(Direcao[] direcoes, Localizacao localizacao, Mapa mapa) {
        this(direcoes, false, localizacao, mapa);
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

    public void marcar(boolean m) {
        Image[] imagens = getImagens();
        int p = imagens.length - 1;
        if (m) {
            imagens[p] = Recurso.SEMAFORO_VERDE.getImagem();
        } else {
            imagens[p] = null;
        }
    }

    public Rua(Direcao direcao, Localizacao localizacao, Mapa mapa) {
        this(new Direcao[]{direcao}, localizacao, mapa);
    }

    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        if (o instanceof FantasmaPedestre || o instanceof PedestreAmbulante) return faixa;
        Direcao desejada = Direcao.calcular(o.getLocalizacao(), getLocalizacao());
        for (Direcao d : direcoes) {
            if (d == desejada) return true;
        }
        return false;
    }

    @Override
    public void executarAcao() { }
}
