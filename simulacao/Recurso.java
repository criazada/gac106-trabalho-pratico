package simulacao;

import java.awt.Image;
import javax.swing.ImageIcon;

public enum Recurso {
    CARRO_1("marquinhos2.png"),
    CARRO_2("mate.png"),
    CARRO_3("sally.png"),
    FUNDO_RUA("fundo-rua.png"),
    SEMAFORO_VERDE("semaforo-verde.png"),
    SEMAFORO_AMARELO("semaforo-amarelo.png"),
    SEMAFORO_VERMELHO("semaforo-vermelho.png"),
    SETA_NORTE("seta-norte.png"),
    SETA_SUL("seta-sul.png"),
    SETA_LESTE("seta-leste.png"),
    SETA_OESTE("seta-oeste.png"),
    CALCADA("ladrilho.png"),
    ONIBUS( "mamute.png"),
    PONTO_ONIBUS("ponto-onibus.png"),
    PEDESTRE_1("pedestre1.png"),
    PEDESTRE_2("pedestre2.png"),
    PEDESTRE_3("pedestre3.png"),
    FAIXA("faixa-horizontal.png"),
    OBRA("obra.png");

    private Image imagem;
    private static final String base = "imagens";

    private Recurso(String nome) {
        imagem = new ImageIcon(getClass().getResource(base + "/" + nome)).getImage();
    }

    public Image getImagem() {
        return imagem;
    }
}
