package simulacao;

public class Fantasma extends ObjetoSimulacao {

    public Fantasma() {
        super(null, null, null, null, null);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void executarAcao() { }

    @Override
    public boolean transparentePara(ObjetoSimulacao o) { return true; }
    
}
