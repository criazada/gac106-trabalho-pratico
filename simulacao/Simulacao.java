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
        Localizacao lRua = null;
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                if (ruas[i][j][0] != null) {
                    Rua rua = new Rua(ruas[i][j], new Localizacao(j, i), mapa);
                    if (lRua == null) lRua = rua.getLocalizacao();
                    mapa.adicionarObjeto(rua);
                } else {
                    Calcada c = new Calcada(new Localizacao(j, i), mapa);
                    mapa.adicionarObjeto(c);
                }
            }
        }
        mapa.atualizarGrafos();

        Veiculo v = new Veiculo(lRua, mapa, rand);
        int x = 0;
        int y = 0;
        while (!(mapa.getObjeto(Mapa.Camada.BACKGROUND, x, y) instanceof Rua)) {
            x = rand.nextInt(largura);
            y = rand.nextInt(altura);
        }
        ((Rua) mapa.getObjeto(Mapa.Camada.BACKGROUND, x, y)).marcar(true);

        mapa.adicionarObjeto(v);
        v.setLocalizacaoDestino(new Localizacao(x, y));

        // Inicializando o mapa com o veículo
        janelaSimulacao = new JanelaSimulacao(mapa);
    }

    /**
     * Executa n iterações da simulação
     * @param n número de iterações
     */
    public void executarSimulacao(int n) {
        janelaSimulacao.atualizar();
        for (int i = 0; i < n; i++) {
            long inicio = System.nanoTime();
            iteracao();
            int tempo = (int) ((System.nanoTime() - inicio) / 1000000);
            System.out.printf("T: %d  \r", tempo);
            int t = 100 - tempo;
            esperar(t);
        }
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
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
