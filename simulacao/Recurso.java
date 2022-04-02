package simulacao;

import java.awt.Image;
import javax.swing.ImageIcon;

public enum Recurso {
    CARRO("carro.png"),
    SEMAFORO_VERDE("semaforo-verde.png"),
    SEMAFORO_AMARELO("semaforo-amarelo.png"),
    SEMAFORO_VERMELHO("semaforo-vermelho.png");

    private Image imagem;
    private static final String base = "imagens";

    private Recurso(String nome) {
        imagem = new ImageIcon(getClass().getResource(base + "/" + nome)).getImage();
    }

    public Image getImagem() {
        return imagem;
    }
}
