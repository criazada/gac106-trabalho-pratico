package simulacao;

import java.util.Random;
import java.awt.Image;
/**
 * Representa os veiculos da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Veiculo extends ObjetoAmbulante {
    private int raiva;

    public Veiculo(Image imagem,Localizacao localizacao, Localizacao destino, Mapa mapa, Random rng) {
        super(imagem, localizacao, mapa, rng, 0);
        setLocalizacaoDestino(destino);
    }

    @Override
    public void executarAcao() {
        Localizacao destino = getLocalizacaoDestino();
        if (destino != null) {
            Localizacao prox = proximaLocalizacao();
            if (prox == getLocalizacao()) {
                fimDeRota();
            }
            // carro só anda se o espaço de destino está livre
            else if (livre(prox)) {
                setLocalizacao(prox);
                incrementarPasso();
            } else {
                raiva++;
            }
            
            if (raiva >= 5) {
                Direcao d = Direcao.TODAS[getRng().nextInt(4)];
                prox = d.seguindo(getLocalizacao());
                boolean andar = true;
                for (ObjetoSimulacao o : getMapa().getObjetosEm(prox)) {
                    if (o != null && !(o instanceof Rua)) {
                        andar = false;
                    }
                }
                if (andar) {
                    setLocalizacao(prox);
                    atualizarRota();
                    raiva = 0;
                }
            }
        }
    }
    public void fimDeRota(){
        getMapa().removerObjeto(this);
    }
}
