package simulacao;

import java.awt.Image;
import javax.swing.ImageIcon;

public enum Recurso {
    CARRO("carro.png"),
    FUNDO_RUA("fundo-rua.png"),
    SEMAFORO_VERDE("semaforo-verde.png"),
    SEMAFORO_AMARELO("semaforo-amarelo.png"),
    SEMAFORO_VERMELHO("semaforo-vermelho.png"),
    SETA_NORTE("seta-norte.png"),
    SETA_SUL("seta-sul.png"),
    SETA_LESTE("seta-leste.png"),
    SETA_OESTE("seta-oeste.png"),
    CALCADA("ladrilho.png"),
    PEDESTRE("pedestre.png"),
    FAIXA("faixa-horizontal.png");

    private Image imagem;
    private static final String base = "imagens";

    private Recurso(String nome) {
        imagem = new ImageIcon(getClass().getResource(base + "/" + nome)).getImage();
    }

    public Image getImagem() {
        return imagem;
    }
}
