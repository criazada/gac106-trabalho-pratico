package simulacao;

import simulacao.Mapa.Camada;

public class PontoOnibus extends ObjetoSimulacao {
    private PontoOnibusCalcada calcada;

    public PontoOnibus(Localizacao localizacao, Mapa mapa) {
        this(localizacao, mapa, Mapa.PontoDeInteresse.PONTO_ONIBUS, true);
    }

    private PontoOnibus(Localizacao localizacao, Mapa mapa, Mapa.PontoDeInteresse pdi, boolean gerarCalcada) {
        super(Recurso.PONTO_ONIBUS.getImagem(), localizacao, mapa, Mapa.Camada.FOREGROUND, pdi, null);

        if (gerarCalcada) {
            int x0 = localizacao.getX();
            int y0 = localizacao.getY();

            boolean encontrou = false;
            int[][] p = {{0, 1}, {1, 0}, {-1, 0}, {0 , -1}, {1, 1}, {1, -1}, {-1, 1}, {1, -1}};
            int i, x = 0, y = 0;
            for (i = 0; i < p.length; i++) {
                x = p[i][0] + x0;
                y = p[i][1] + y0;
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

    public PontoOnibusCalcada getPontoCalcada() {
        return calcada;
    }

    private void setCalcada(PontoOnibusCalcada calcada) {
        this.calcada = calcada;
    }

    public boolean temOnibus() {
        return getMapa().getObjetoMiddle(getLocalizacao()) instanceof Onibus;
    }

    public class PontoOnibusCalcada extends PontoOnibus {
        private PontoOnibus rua;

        private PontoOnibusCalcada(Localizacao localizacao, Mapa mapa, PontoOnibus rua) {
            super(localizacao, mapa, Mapa.PontoDeInteresse.PONTO_ONIBUS_CALCADA, false);
            setCalcada(this);
            this.rua = rua;
        }

        public PontoOnibus getPontoRua() {
            return rua;
        }

        @Override
        public boolean temOnibus() {
            return rua.temOnibus();
        }
    }
}
