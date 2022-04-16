package simulacao;

import java.util.Random;

/**
 * Representa os pedestres da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class PedestreAmbulante extends ObjetoAmbulante {
    private int lentidao;
    private int raiva;

    public PedestreAmbulante(Localizacao localizacao, Localizacao destino, Mapa mapa, Random rng) {
        super(Recurso.PEDESTRE.getImagem(), localizacao, mapa, rng, 1);
        setLocalizacaoDestino(destino);
    }

    @Override
    public void executarAcao() {
        lentidao++;
        if (lentidao % 2 == 0) return;

        Localizacao destino = getLocalizacaoDestino();
        if (destino != null) {
            Localizacao prox = proximaLocalizacao();
            if (prox == getLocalizacao()) {
                getMapa().removerObjeto(this);
            }
            // pedestre só anda se o espaço de destino está livre
            else if (livre(prox)) {
                setLocalizacao(prox);
                incrementarPasso();
                raiva = 0;
            } else {
                raiva++;
            }

            if (raiva >= 5) {
                Direcao d = Direcao.TODAS[getRng().nextInt(4)];
                prox = d.seguindo(getLocalizacao());
                if (livre(prox)) {
                    setLocalizacao(prox);
                    atualizarRota();
                }
            }
        }
    }
}
