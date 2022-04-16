package simulacao;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
/**
 * Representa os veiculos da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Onibus extends Veiculo {
    public static List<Integer> posPontosOnibus = new ArrayList<Integer>();

    public Onibus(Localizacao localizacao, Localizacao destino, Mapa mapa, Random rng) {
        super(Recurso.ONIBUS.getImagem(), localizacao, destino, mapa, rng);
    }
    
}
