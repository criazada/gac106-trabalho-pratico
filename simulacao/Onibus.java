package simulacao;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Representa os veiculos da simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Onibus extends Veiculo {
    private int pontoAtual;
    private int esperar;
    private RotaOnibus rota;
    private int capacidade;
    private Set<PedestreOnibus> passageiros;

    public Onibus(Localizacao localizacao, int capacidade, RotaOnibus rota, int inicio, Mapa mapa, Random rand) {
        super(Recurso.ONIBUS.getImagem(), localizacao, mapa, rand);
        esperar = 5;
        this.rota = rota;
        irParaPonto(inicio);
        this.capacidade = capacidade;
        passageiros = new HashSet<>();
    }

    private void irParaPonto(int ponto) {
        Localizacao loc = rota.get(ponto % rota.size()).getLocalizacao();
        setLocalizacaoDestino(loc);
    }

    private boolean cheio() {
        return passageiros.size() == capacidade;
    }

    public boolean receberPassageiro(PedestreOnibus pedestre) {
        if (!cheio()) {
            passageiros.add(pedestre);
            return true;
        } else {
            return false;
        }
    }

    public boolean removerPassageiro(PedestreOnibus pedestre) {
        if (estaNoPonto() && passageiros.contains(pedestre)) {
            passageiros.remove(pedestre);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void fimDeRota() {
        pontoAtual++;
        irParaPonto(pontoAtual);
    }

    private PontoOnibus getPontoMaisProximo() {
        return (PontoOnibus) getMapa().getPontoDeInteresseMaisProximo(this, Mapa.PontoDeInteresse.PONTO_ONIBUS);
    }

    @Override
    public void executarAcao() {
        for (PedestreOnibus po : passageiros.toArray(new PedestreOnibus[0])) {
            po.executarAcao();
        }
        if (estaNoPonto() && esperar > 0) {
            PontoOnibus ponto = getPontoMaisProximo();
            if (!ponto.temPedestre() || cheio()) {
                esperar--;
            }
            getEstatisticas().onibusParado();
        }
        else {
            esperar = 5;
            getEstatisticas().onibusAndou();
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
