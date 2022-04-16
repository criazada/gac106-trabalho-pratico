package simulacao;

public class Semaforo extends ObjetoSimulacao {
    private int contador;
    private boolean transparente;

    public Semaforo(Localizacao localizacao, Mapa mapa) {
        super(Recurso.SEMAFORO_VERDE.getImagem(), localizacao, mapa, Mapa.Camada.FOREGROUND, null);
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
        return transparente || (o instanceof FantasmaVeiculo);
    }
}
