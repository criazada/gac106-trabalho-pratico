package simulacao;

/**
 * Representa os veiculos da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Onibus extends Veiculo {
    private int pontoAtual;
    private int esperar;
    private RotaOnibus rota;

    public Onibus(Localizacao localizacao, RotaOnibus rota, int inicio, Mapa mapa) {
        super(Recurso.ONIBUS.getImagem(), localizacao, null, mapa, null);
        esperar = 5;
        this.rota = rota;
        irParaPonto(inicio);
    }

    private void irParaPonto(int ponto) {
        Localizacao loc = rota.get(ponto % rota.size()).getLocalizacao();
        setLocalizacaoDestino(loc);
    }

    @Override
    public void fimDeRota() {
        pontoAtual++;
        irParaPonto(pontoAtual);
    }

    @Override
    public void executarAcao() {
        //System.out.println("Onibus " + id + ": " + getLocalizacao());
        if (estaNoPonto() && esperar > 0) {
            esperar--;
        }
        else {
            esperar = 5;
            super.executarAcao();
        }
    }

    /**
     * Retorna se o onibus esta no ponto
     * @return um boleando verdadeiro se o onibus esta no ponto e falso caso contrario
     */
    public boolean estaNoPonto() {
        return proximaLocalizacao() == getLocalizacao();
    }    
}
