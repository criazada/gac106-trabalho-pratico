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
    public static List<Localizacao> posPontosOnibus = new ArrayList<Localizacao>();
    public int localizacaoAtual;
    public int esperar = 5;

    public Onibus(int localizacao, int destino, Mapa mapa, Random rng) {
        super(Recurso.ONIBUS.getImagem(), Onibus.posPontosOnibus.get(localizacao), Onibus.posPontosOnibus.get(destino), mapa, rng);
        localizacaoAtual =  localizacao;
    }

    @Override
    public void fimDeRota(){
        super.setLocalizacaoDestino(Onibus.posPontosOnibus.get((localizacaoAtual+1) % Onibus.posPontosOnibus.size()));
        localizacaoAtual++;
    }
    @Override
    public void executarAcao() {
        if (estaNoPonto() && esperar > 0) {
            esperar--;
        }
        else{
            esperar = 5;
            super.executarAcao();
        }
    }

    /**
     * Retorna se o onibus esta no ponto
     * @return um boleando verdadeiro se o onibus esta no ponto e falso caso contrario
     */
    public boolean estaNoPonto(){
        if (proximaLocalizacao() == getLocalizacao()) {
            return true;
        }
        return false;
    }
}
