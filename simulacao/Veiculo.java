package simulacao;

import java.util.List;
import java.util.Random;

/**
 * Representa os veiculos da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Veiculo extends ObjetoAmbulante {
    private List<Localizacao> caminho;
    private int passo;

    public Veiculo(Localizacao localizacao, Localizacao destino, Mapa mapa, Random rng) {
        super(Recurso.CARRO.getImagem(), localizacao, mapa, rng);
        setLocalizacaoDestino(destino);
    }

    /**
     * Gera a localizacao para se mover visando alcançar o destino
     *
     * @return Localizacao para onde se deve ir
     */
    public Localizacao proximaLocalizacao() {
        if (passo < caminho.size()) {
            return caminho.get(passo);
        }
        return getLocalizacao();
    }

    @Override
    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        super.setLocalizacaoDestino(localizacaoDestino);
        caminho = getMapa().getCaminhoParaDestino(this);
        passo = 0;
    }

    @Override
    public void executarAcao() {
        Localizacao destino = getLocalizacaoDestino();
        if (destino != null) {
            Localizacao prox = proximaLocalizacao();
            if (prox == getLocalizacao()) {
                getMapa().removerObjeto(this);
            }
            // carro só anda se o espaço de destino está livre
            else if (livre(prox)) {
                setLocalizacao(prox);
                passo++;
            }
        }
    }
}
