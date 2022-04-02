package simulacao;

import java.util.Random;

/**
 * Responsavel pela simulacao.
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Simulacao {
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;

    public Simulacao() {
        Random rand = new Random(12346);
        mapa = new Mapa();
        int largura = mapa.getLargura();
        int altura = mapa.getAltura();

        for (int i = 10; i < 25; i++) {
            Semaforo semaforo = new Semaforo(new Localizacao(13, i), mapa);
            mapa.adicionarObjeto(semaforo);
            Direcao d = i % 2 == 0 ? Direcao.LESTE : Direcao.OESTE;
            for (int j = 10; j < 17; j++) {
                Rua rua = new Rua(d, j == 13, new Localizacao(j, i), mapa);
                mapa.adicionarObjeto(rua);
            }
        }
        // For para ver como fica com mais veiculos (debug)
        for (int i = 0; i < 5; i++) {
            // Cria um veiculo em uma posicao aleatoria
            Veiculo veiculo = new Veiculo(new Localizacao(rand.nextInt(largura), rand.nextInt(altura)), mapa, rand);
            // Define a posicao destino aleatoriamente
            veiculo.setLocalizacaoDestino(new Localizacao(rand.nextInt(largura), rand.nextInt(altura)));
            mapa.adicionarObjeto(veiculo);
        }
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
            int tempo = (int) (System.nanoTime() - inicio) / 1000000;
            System.out.println(tempo);
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
