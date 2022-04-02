package simulacao;

import java.util.Random;

/**
 * Representa os veiculos da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Veiculo extends ObjetoAmbulante {
    public Veiculo(Localizacao localizacao, Mapa mapa, Random rng) {
        super(Recurso.CARRO.getImagem(), localizacao, mapa, rng);
    }

    /**
     * Gera a localizacao para se mover visando alcançar o destino
     *
     * @param localizacaoDestino: localizacao que se deseja alcancar.
     * @return Localizacao para onde se deve ir
     */
    public Localizacao proximaLocalizacao() {
        Localizacao locDestino = getLocalizacaoDestino();
        Localizacao locAtual = getLocalizacao();
        if (locDestino.equals(locAtual)) {// Verifica se já alcancou o destino
            return locDestino;
        } else {
            int x = locAtual.getX();
            int y = locAtual.getY();
            int destX = locDestino.getX();
            int destY = locDestino.getY();
            int deslocX = x < destX ? 1 : x > destX ? -1 : 0;// Deslocamento de 1 ou 0 ou -1 posição em x
            int deslocY = y < destY ? 1 : y > destY ? -1 : 0;// Deslocamento de 1 ou 0 ou -1 posição em y
            Localizacao novaLocalizacao;
            if (deslocX != 0 && deslocY != 0) {// Se nenhuma coordenada coincide com a localizacao destino
                if (getRng().nextInt(2) == 0) {// Atualizar x
                    novaLocalizacao = new Localizacao(x + deslocX, y);
                } else {// Atualizar y
                    novaLocalizacao = new Localizacao(x, y + deslocY);
                }
            } else {
                if (deslocX != 0)
                    novaLocalizacao = new Localizacao(x + deslocX, y);
                else
                    novaLocalizacao = new Localizacao(x, y + deslocY);
            }
            return novaLocalizacao;
        }
    }

    @Override
    public void executarAcao() {
        Localizacao destino = getLocalizacaoDestino();
        if (destino != null) {
            Localizacao prox = proximaLocalizacao();
            // carro só anda se o espaço de destino está livre
            if (livre(prox)) {
                setLocalizacao(prox);
            }
        }
    }

}
