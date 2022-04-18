package simulacao;

import java.util.Random;
import java.awt.Image;
/**
 * Representa os veiculos da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Veiculo extends ObjetoAmbulante {
    public Veiculo(Image imagem,Localizacao localizacao, Localizacao destino, Mapa mapa, Random rng) {
        super(imagem, localizacao, destino, mapa, Mapa.TipoGrafo.VEICULO, rng);
    }

    @Override
    public void fimDeRota() {
        getMapa().removerObjeto(this);
    }
}
