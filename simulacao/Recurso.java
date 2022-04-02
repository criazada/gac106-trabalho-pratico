package simulacao;

import java.awt.Image;
import javax.swing.ImageIcon;

public enum Recurso {
    CARRO("carro.png"),
    SEMAFORO_VERDE("semaforo-verde.png"),
    SEMAFORO_AMARELO("semaforo-amarelo.png"),
    SEMAFORO_VERMELHO("semaforo-vermelho.png"),
    RUA_NORTE("rua-norte.png"),
    RUA_SUL("rua-sul.png"),
    RUA_LESTE("rua-leste.png"),
    RUA_OESTE("rua-oeste.png");

    private Image imagem;
    private static final String base = "imagens";

    private Recurso(String nome) {
        imagem = new ImageIcon(getClass().getResource(base + "/" + nome)).getImage();
    }

    public Image getImagem() {
        return imagem;
    }
}
