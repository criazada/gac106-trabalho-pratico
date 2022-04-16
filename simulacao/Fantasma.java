package simulacao;

public class Fantasma extends ObjetoSimulacao {

    public Fantasma() {
        super();
    }

    @Override
    public void executarAcao() { }

    @Override
    public boolean transparentePara(ObjetoSimulacao o) { return true; }
    
}
