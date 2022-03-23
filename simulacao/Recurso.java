package simulacao;

import java.awt.Image;
import javax.swing.ImageIcon;

public enum Recurso {
    CARRO("carro.jpg");

    private Image imagem;
    private static final String base = "Imagens";

    private Recurso(String nome) {
        imagem = new ImageIcon(getClass().getResource(base + "/" + nome)).getImage();
    }

    public Image getImagem() {
        return imagem;
    }
}
