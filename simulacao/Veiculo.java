package simulacao;

import java.util.Random;
import java.awt.Image;
/**
 * Representa os veiculos da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Veiculo extends ObjetoAmbulante {
    public Veiculo(Image imagem, Localizacao localizacao, Mapa mapa, Random rng) {
        super(imagem, localizacao, null, mapa, Mapa.TipoGrafo.VEICULO, rng);
    }
}
