package simulacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Representa os veiculos da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Onibus extends Veiculo {
    public static List<Localizacao> posicoesOnibus = new ArrayList<Localizacao>();
    private int ProximoPonto;
    private int esperar = 5;
    public static int contador;
    private int id ;

    public Onibus(int localizacao, int destino, Mapa mapa, Random rng) {
        super(Recurso.ONIBUS.getImagem(), PontoOnibus.posicoesRua.get(localizacao), PontoOnibus.posicoesRua.get(destino), mapa, rng);
        ProximoPonto =  localizacao;
        id = contador;
        contador++;
        posicoesOnibus.add(PontoOnibus.posicoesRua.get(localizacao));
    }

    @Override
    public void fimDeRota(){
        super.setLocalizacaoDestino(PontoOnibus.posicoesRua.get((ProximoPonto+1) % PontoOnibus.posicoesRua.size()));
        ProximoPonto++;
    }
    @Override
    public void executarAcao() {
        //System.out.println("Onibus " + id + ": " + getLocalizacao());
        posicoesOnibus.set(id, getLocalizacao());
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
