package simulacao;

import java.awt.Image;

public class Rua extends ObjetoSimulacao {
    private Direcao[] direcoes;
    
    private Rua(Direcao[] direcoes, Localizacao localizacao, Mapa mapa) {
        super(null, localizacao, mapa, Mapa.Camada.BACKGROUND, null);
        Recurso r = null;
        switch (direcoes[0]) {
            case NORTE:
                r = Recurso.RUA_NORTE;
                break;
    
            case SUL:
                r = Recurso.RUA_SUL;
                break;
    
            case LESTE:
                r = Recurso.RUA_LESTE;
                break;
    
            case OESTE:
                r = Recurso.RUA_OESTE;
                break;

            default:
                break;
        }
        setImagem(r.getImagem());
        this.direcoes = direcoes;
    }

    public Rua(Direcao direcao, Localizacao localizacao, Mapa mapa) {
        this(new Direcao[]{direcao}, localizacao, mapa);
    }

    public Rua(Direcao direcao, Direcao direcaoSecundaria, Localizacao localizacao, Mapa mapa) {
        this(new Direcao[]{direcao, direcaoSecundaria}, localizacao, mapa);
    }

    @Override
    public Image getImagem() {
        return super.getImagem();
    }
    
    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        Direcao desejada = Direcao.calcular(o.getLocalizacao(), getLocalizacao());
        for (Direcao d : direcoes) {
            if (d == desejada) return true;
        }
        return false;
    }

    @Override
    public void executarAcao() { }
}
