package simulacao;

import java.awt.Image;

public class Rua extends ObjetoSimulacao {
    private boolean cruzamento;
    
    public Rua(Direcao direcao, boolean cruzamento, Localizacao localizacao, Mapa mapa) {
        super(null, localizacao, mapa, Mapa.Camada.BACKGROUND, null);
        setDirecao(direcao);
        this.cruzamento = cruzamento;
        Recurso r = null;
        switch (direcao) {
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
    }

    @Override
    public Image getImagem() {
        return super.getImagem();
    }
    
    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        Direcao desejada = Direcao.calcular(o.getLocalizacao(), getLocalizacao());
        if (cruzamento) {
            return calcularDirecao().oposta() != desejada;
        } else {
            return calcularDirecao() == desejada;
        }
    }

    @Override
    public void executarAcao() { }
}
