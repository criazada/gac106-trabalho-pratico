package simulacao;

import simulacao.Mapa.Camada;
/**
 * Representa a calçada da simulação.
 */
public class Calcada extends ObjetoSimulacao {
    /**
     * Constroi a calçada.
     * @param localizacao A localização da calçada.
     * @param mapa A instancia do mapa.
    */
    public Calcada(Localizacao localizacao, Mapa mapa) {
        super(Recurso.CALCADA.getImagem(), localizacao, mapa, Camada.BACKGROUND, Mapa.PontoDeInteresse.ESTATICO, null);
    }
    /**
     * Como Calçada nao tem movimento sua ação é nula.
     */
    @Override
    public void executarAcao() { }

    /**
     * Verifica se este objeto é transparente para outro objeto.
     * Útil caso somente alguns tipos de objetos possam passar por este.
     * @param o O outro objeto
     * @return Transparência deste objeto na visão de outro objeto.
     */
    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        return !(o instanceof Veiculo) && !(o instanceof FantasmaVeiculo);
    }
}
