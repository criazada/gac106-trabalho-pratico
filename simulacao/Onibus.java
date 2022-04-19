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

    /**
     * Cria um onibus.
     * @param localizacao inicial do onibus
     * @param capacidade capacidade maxima de pedestres no onibus
     * @param rota rota que o onibus ira seguir
     * @param inicio ponto inicial da rota.
     * @param mapa instancia do mapa da simulacao.
     * @param rand intancia do gerador de numeros aleatorios com a seed inicial
     */
    public Onibus(Localizacao localizacao, int capacidade, RotaOnibus rota, int inicio, Mapa mapa, Random rand) {
        super(Recurso.ONIBUS.getImagem(), localizacao, mapa, rand);
        esperar = 5;
        this.rota = rota;
        irParaPonto(inicio);
        this.capacidade = capacidade;
        passageiros = new HashSet<>();
    }
    /**
     * Seta a localizaçaõ do onibus para ir para o proximo ponto da rota.
     * @param ponto proximo ponto da rota.
     */
    private void irParaPonto(int ponto) {
        Localizacao loc = rota.get(ponto % rota.size()).getLocalizacao();
        setLocalizacaoDestino(loc);
    }
    /**
     * @return Retorna se o onibus esta cheio.
     */
    private boolean cheio() {
        return passageiros.size() == capacidade;
    }
    /**
     * Recebe um pedestre e o adiciona ao onibus.
     * @param p Pedestre que sera adicionado ao onibus.
     * @return true se o pedestre foi adicionado com sucesso e false caso onibus esteja cheio.
     */
    public boolean receberPassageiro(PedestreOnibus pedestre) {
        if (!cheio()) {
            passageiros.add(pedestre);
            return true;
        } else {
            return false;
        }
    }
    /**
     * Remove um pedestre do onibus.
     * @param p Pedestre que sera removido do onibus.
     * @return true se o pedestre foi removido com sucesso e false caso o pedestre nao esteja no onibus.
     */
    public boolean removerPassageiro(PedestreOnibus pedestre) {
        if (estaNoPonto() && passageiros.contains(pedestre)) {
            passageiros.remove(pedestre);
            return true;
        } else {
            return false;
        }
    }
    /**
     * Seta para onibus para proximo ponto da rota.
     */
    @Override
    public void fimDeRota() {
        pontoAtual++;
        irParaPonto(pontoAtual);
    }
    /**
     * @return Retorna o ponto de onibus mais proximo.
     */
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
