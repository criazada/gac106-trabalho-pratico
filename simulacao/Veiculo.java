package simulacao;

import java.util.Random;
import java.awt.Image;

/**
 * Representa os veiculos da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public abstract class Veiculo extends ObjetoAmbulante {
    /**
     * Constroi um veiculo.
     * @param imagem A imagem do veiculo.
     * @param localizacao A localizacao inicial do veiculo.
     * @param mapa Uma instancia do mapa.
     * @param rand Uma intancia do gerador de numeros aleatorios com a seed inicializada.
     */
    public Veiculo(Image imagem,Localizacao localizacao, Mapa mapa, Random rng) {
        super(imagem, localizacao, null, mapa, Mapa.TipoGrafo.VEICULO, rng);
    }
    /**
     * Template method para executar a ação do veiculo.
     */
    @Override
    public void fimDeRota() {
        getMapa().removerObjeto(this);
    }
}
