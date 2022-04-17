package simulacao;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class PontoOnibus extends ObjetoSimulacao {
    public static List<Localizacao> posicoesRua = new ArrayList<Localizacao>();
    public static List<Localizacao> posicoesCalcada = new ArrayList<Localizacao>();

    public PontoOnibus( Localizacao localizacao, Mapa mapa, Random rng) {
        super(Recurso.PONTO_ONIBUS.getImagem(), localizacao, mapa, Mapa.Camada.FOREGROUND, rng);
    }
    @Override
    public void executarAcao(){
        // nao faz nada
    }
    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        return true;
    }
    
    public static Localizacao pontoMaisProximoCalcada( Localizacao inicial){
        int menorDistancia = Integer.MAX_VALUE;
        int indice = 0;
        for (int i = 0; i < posicoesCalcada.size(); i++) {
            int distancia = posicoesCalcada.get(i).distancia(inicial);
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                indice = i;
            }
        }
        return posicoesCalcada.get(indice);
    }
    /**
     * Retorna o ponto mais proximo da rua
     * @param inicial a localizacao do pedreste
     * @return o ponto mais proximo da rua
     */
    public static Localizacao pontoMaisProximoRua( Localizacao inicial){
        int menorDistancia = Integer.MAX_VALUE;
        int indice = 0;
        for (int i = 0; i < posicoesRua.size(); i++) {
            int distancia = posicoesRua.get(i).distancia(inicial);
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                indice = i;
            }
        }
        return posicoesRua.get(indice);
    }
    
    public static boolean pedestreEstaNoPonto(Localizacao loc){
        for (Localizacao localizacao : posicoesCalcada) {
            if (loc.distancia(localizacao) == 0) {
                return true;
            }
        }
        return false;
    }

    public static int onibusEstaNoPonto(Localizacao loc){
        for (int i = 0; i < Onibus.posicoesOnibus.size(); i++) {
            Localizacao localizacao = Onibus.posicoesOnibus.get(i);
            if (loc.distancia(localizacao) == 0) {
                return i;
            }
        }
        return -1;
    }
}
