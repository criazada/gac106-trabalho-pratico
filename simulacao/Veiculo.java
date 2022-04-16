package simulacao;

import java.util.Random;

/**
 * Representa os veiculos da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Veiculo extends ObjetoAmbulante {
    public Veiculo(Localizacao localizacao, Localizacao destino, Mapa mapa, Random rng) {
        super(Recurso.CARRO.getImagem(), localizacao, mapa, rng, 0);
        setLocalizacaoDestino(destino);
    }

    @Override
    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        super.setLocalizacaoDestino(localizacaoDestino);
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
                incrementarPasso();
            }
        }
    }
}
