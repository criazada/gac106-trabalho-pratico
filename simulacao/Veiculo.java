package simulacao;

/**
 * Representa os veiculos da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Veiculo extends ObjetoAmbulante {
    public Veiculo(Localizacao localizacao, Mapa mapa) {
        super(Recurso.CARRO.getImagem(), localizacao, mapa);
    }

    @Override
    public void executarAcao() {
        Localizacao destino = getLocalizacaoDestino();
        if (destino != null) {
            Localizacao prox = getLocalizacao().proximaLocalizacao(destino);
            // carro só anda se o espaço de destino está livre
            if (livre(prox)) {
                setLocalizacao(prox);
            }
        }
    }

}
