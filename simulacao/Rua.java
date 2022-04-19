package simulacao;

import java.awt.Image;

public class Rua extends ObjetoSimulacao {
    private Direcao[] direcoes;
    private boolean faixa;
    /**
     * Constroi uma rua.
     * @param Direção A direção da rua, Norte, Sul, Leste ou Oeste.
     * @param faixa se essa estancia de rua é uma faixa de pedestre.
     * @param localizacao A localizacao da rua.
     * @param mapa A instancia do mapa.
     */
    public Rua(Direcao[] direcoes, boolean faixa, Localizacao localizacao, Mapa mapa) {
        super((Image) null, localizacao, mapa, Mapa.Camada.BACKGROUND, Mapa.PontoDeInteresse.ESTATICO, null);
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
    /**
     * Constroi uma rua.
     * @param direções vetor direçoes da rua, Norte, Sul, Leste ou Oeste.
     * @param localizacao A localizacao da rua.
     * @param mapa A instancia do mapa.
     */
    public Rua(Direcao[] direcoes, Localizacao localizacao, Mapa mapa) {
        this(direcoes, false, localizacao, mapa);
    }

     /**
     * Constroi uma rua.
     * @param direção direção da rua, Norte, Sul, Leste ou Oeste.
     * @param localizacao A localizacao da rua.
     * @param mapa A instancia do mapa.
     */
    public Rua(Direcao direcao, Localizacao localizacao, Mapa mapa) {
        this(new Direcao[]{direcao}, localizacao, mapa);
    }

    /**
     * Retorna a imagem da seta para a direção dada.
     * @param direcao A direção da rua.
     * @return A imagem da seta para a direção dada.
     */
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

    /**
     * Verifica se este objeto é transparente para outro objeto.
     * Útil caso somente alguns tipos de objetos possam passar por este.
     * Muito relevante para objetos no background.
     * @param o O outro objeto
     * @return Transparência deste objeto na visão de outro objeto.
     */
    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        if (o instanceof FantasmaPedestre || o instanceof PedestreAmbulante) return faixa;
        Direcao desejada = Direcao.calcular(o.getLocalizacao(), getLocalizacao());
        for (Direcao d : direcoes) {
            if (d == desejada) return true;
        }
        return false;
    }
    /**
     * Como Rua nao tem movimento sua ação é nula.
     */
    @Override
    public void executarAcao() { }
}
