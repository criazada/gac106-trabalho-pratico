package simulacao;

import java.util.Random;

public class Semaforo extends ObjetoSimulacao {
    private int contador;
    private boolean transparente;

    public Semaforo(Localizacao localizacao, Mapa mapa, Random rng) {
        super(Recurso.SEMAFORO_VERMELHO.getImagem(), localizacao, mapa, Mapa.Camada.MIDDLE, rng);
    }

    @Override
    public void executarAcao() {
        contador++;
        
        transparente = true;
        int t = contador % 20;
        Recurso r;
        if (t < 10) {
            r = Recurso.SEMAFORO_VERDE;
        } else if (t < 13) {
            r = Recurso.SEMAFORO_AMARELO;
        } else {
            r = Recurso.SEMAFORO_VERMELHO;
            transparente = false;
        }

        setImagem(r.getImagem());
    }

    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        return transparente;
    }
}
