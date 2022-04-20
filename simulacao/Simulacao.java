package simulacao;

import java.util.List;
import java.util.Random;

import simulacao.geracao.Gerador;
import simulacao.geracao.Segmento;

/**
 * Responsavel pela simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Simulacao {
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;

    public Simulacao() {
        Random rand = new Random(12345);
        int largura = 64;
        int altura = 64;
        Gerador g = new Gerador(largura, altura, rand);
        mapa = new Mapa(largura, altura);

        List<Segmento> S = g.gerar();
        Direcao[][][] ruas = g.gerarRuas(S);
        boolean[][] faixas = g.gerarFaixas(S);
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                if (ruas[i][j][0] != null) {
                    new Rua(ruas[i][j], faixas[i][j], new Localizacao(j, i), mapa);
                } else {
                    new Calcada(new Localizacao(j, i), mapa);
                }
            }
        }
        mapa.atualizarGrafos();

        RotaOnibus rota = new RotaOnibus();

        // gera pontos de onibus
        for (int i = 0; i < 15; i++) {
            PontoOnibus po = new PontoOnibus(mapa.getRuaAleatoria(rand), mapa);
            rota.add(po);
        }

        // gera onibus 
        for (int i = 0; i < 5; i++) {
            new Onibus(mapa.getRuaAleatoria(rand), 10, rota, rand.nextInt(rota.size()), mapa, rand);
        }

        // gera pedestres dos onibus
        for (int i = 0; i < 100; i++) {
            new PedestreOnibus(mapa.getCalcadaAleatoria(rand), mapa, rand);
        }
        //gera obras
        for(int i = 0; i < 10; i++){
            new Obra(mapa.getRuaAleatoria(rand), mapa, rand);
        }

        long inicio = System.nanoTime();
        for (int i = 0; i < 25; i++) {
            new Carro(mapa.getRuaAleatoria(rand), mapa, rand);
        }

        for (int i = 0; i < 100; i++) {
            new PedestreAmbulante(mapa.getCalcadaAleatoria(rand), mapa, rand);
        }

        int tempo = (int) ((System.nanoTime() - inicio) / 1000000);
        System.out.printf("T: %d%n", tempo);
        janelaSimulacao = new JanelaSimulacao(mapa);
    }

    /**
     * Executa n iterações da simulação
     * @param n número de iterações
     */
    public Estatisticas executarSimulacao(int n) {
        janelaSimulacao.atualizar();
        for (int i = 0; i < n; i++) {
            long inicio = System.nanoTime();
            iteracao();
            int tempo = (int) ((System.nanoTime() - inicio) / 1000000);
            System.out.printf("T: %d  \r", tempo);
            int t = 100 - tempo;
            esperar(t);
        }

        return mapa.getEstatisticas();
    }

    /**
     * Executa uma iteração da simulação
     */
    private void iteracao() {
        // Para cada objeto da simulação,
        for (ObjetoSimulacao v : mapa.getObjetos()) {
            // Sua ação é executada
            v.executarAcao();
        }

        janelaSimulacao.atualizar();
    }

    private void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos < 0 ? 0 : milisegundos);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
