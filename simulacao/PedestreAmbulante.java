package simulacao;

import java.util.Random;
import java.awt.Image;

/**
 * Representa os pedestres da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */

public class PedestreAmbulante extends ObjetoAmbulante {
    private int lentidao;

    public PedestreAmbulante(Localizacao localizacao, Mapa mapa, Random rng) {
        super(selecionarImagem(rng.nextInt(3)), localizacao, null, mapa, Mapa.TipoGrafo.PEDESTRE, rng);
    }

    private static Image selecionarImagem(int rng) {
        switch(rng) {
            case 0:
                return Recurso.PEDESTRE_1.getImagem();   
            case 1:
                return Recurso.PEDESTRE_2.getImagem();               
            case 2:
                return Recurso.PEDESTRE_3.getImagem();
            default:
                return Recurso.PEDESTRE_1.getImagem();  
        }
    }

    @Override
    protected Localizacao gerarCandidatoLocalizacaoDestino() {
        return getMapa().getCalcadaAleatoria(getRng());
    }

    @Override
    public void executarAcao() {
        lentidao++;
        if (lentidao % 2 == 0) return;
        super.executarAcao();
    }

    @Override
    public boolean andarComRaivaPara(ObjetoSimulacao o, Direcao d) {
        return livre(d.seguindo(getLocalizacao()));
    }
}
