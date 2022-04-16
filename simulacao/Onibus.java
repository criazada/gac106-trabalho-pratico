package simulacao;

import java.util.Random;

/**
 * Representa os veiculos da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Onibus extends Veiculo {

    public Onibus(Localizacao localizacao, Localizacao destino, Mapa mapa, Random rng) {
        super(Recurso.ONIBUS.getImagem(), localizacao, destino, mapa, rng);
    }
}
