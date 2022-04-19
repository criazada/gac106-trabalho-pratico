package simulacao;

import simulacao.Mapa.Camada;

public class PontoOnibus extends ObjetoSimulacao {
    private PontoOnibusCalcada calcada;
    /**
     * Cria um ponto de onibus.
     * @param localizacao localizacao do ponto.
     * @param mapa instancia do mapa da simulacao.
     */
    public PontoOnibus(Localizacao localizacao, Mapa mapa) {
        this(localizacao, mapa, Mapa.PontoDeInteresse.PONTO_ONIBUS, true);
    }
    /**
     * Cria um ponto de onibus.
     * @param localizacao localizacao do ponto.
     * @param mapa instancia do mapa da simulacao.
     * @param pontoDeInteresse tipo do ponto.
     * @param gerarCalcada se deve gerar um ponto na calcada para o ponto na rua.
     */
    private PontoOnibus(Localizacao localizacao, Mapa mapa, Mapa.PontoDeInteresse pdi, boolean gerarCalcada) {
        super(Recurso.PONTO_ONIBUS.getImagem(), localizacao, mapa, Mapa.Camada.FOREGROUND, pdi, null);

        if (gerarCalcada) {
            int x0 = localizacao.getX();
            int y0 = localizacao.getY();

            boolean encontrou = false;
            int[][] p = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {1, -1}};
            int l = getMapa().getLargura();
            int a = getMapa().getAltura();
            int i, x = 0, y = 0;
            for (i = 0; i < p.length; i++) {
                x = p[i][0] + x0;
                y = p[i][1] + y0;
                if (x < 0 || x >= l || y < 0 || y >= a) continue;
                ObjetoSimulacao o = mapa.getObjeto(Camada.BACKGROUND, x, y);
                if (o != null && o instanceof Calcada) {
                    encontrou = true;
                    break;
                }
            }

            if (encontrou) {
                calcada = new PontoOnibusCalcada(new Localizacao(x, y), mapa, this);
            }
        }
    }

    @Override
    public void executarAcao() { }

    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        return true;
    }
    /** 
     * @return posicao do ponto na calcada.
     */
    public PontoOnibusCalcada getPontoCalcada() {
        return calcada;
    }
    /**
     * set a posicao do ponto na calcada.
     * @param calcada
     */
    private void setCalcada(PontoOnibusCalcada calcada) {
        this.calcada = calcada;
    }
    /**
     * @return true se tem onibus no ponto
     */
    public boolean temOnibus() {
        return getMapa().getObjetoMiddle(getLocalizacao()) instanceof Onibus;
    }
    /**
     * 
     * @return true se tem pedestre no ponto da calcada
     */
    public boolean temPedestre() {
        if (calcada == null) return false;
        return getMapa().getObjetoMiddle(calcada.getLocalizacao()) instanceof PedestreOnibus;
    }

    public class PontoOnibusCalcada extends PontoOnibus {
        private PontoOnibus rua;
        /**
         * Cria um ponto de onibus na calcada.
         * @param localizacao localizacao do ponto.
         * @param mapa instancia do mapa da simulacao.
         * @param rua ponto de onibus na rua.
         */
        private PontoOnibusCalcada(Localizacao localizacao, Mapa mapa, PontoOnibus rua) {
            super(localizacao, mapa, Mapa.PontoDeInteresse.PONTO_ONIBUS_CALCADA, false);
            setCalcada(this);
            this.rua = rua;
        }
        /**
         * @return retonar o ponto da rua que o ponto de onibus na calcada esta mais proximo
         */
        public PontoOnibus getPontoRua() {
            return rua;
        }
        
        @Override
        public boolean temOnibus() {
            return rua.temOnibus();
        }
    }
}
