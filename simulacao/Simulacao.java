package simulacao;

import java.util.ArrayList;
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
                    Rua rua = new Rua(ruas[i][j], faixas[i][j], new Localizacao(j, i), mapa);
                    mapa.adicionarObjeto(rua);
                } else {
                    Calcada c = new Calcada(new Localizacao(j, i), mapa);
                    mapa.adicionarObjeto(c);
                }
            }
        }
        mapa.atualizarGrafos();
        
        // gera pontos de onibus
        for (int i = 0; i < 2; i++) {
            Localizacao loc = getRuaAleatoria(rand);
            PontoOnibus.posicoesRua.add(loc);
            PontoOnibus.posicoesCalcada.add(getCalcadaMaisProxima(loc));
            mapa.adicionarObjeto(new PontoOnibus(getCalcadaMaisProxima(loc), mapa, rand));
            mapa.adicionarObjeto(new PontoOnibus(loc, mapa, rand));
            System.out.println("ponto onibus Rua " + loc);
            System.out.println("ponto onibus Calada " + getCalcadaMaisProxima(loc));
        }

        for (int i = 0; i < 2; i++) {
            Veiculo v = new Onibus(i, (i+1) % PontoOnibus.posicoesRua.size(),  mapa, rand);
            mapa.adicionarObjeto(v);
        }
        
        for (int i = 0; i < 100; i++) {
            Localizacao s1 = getCalcadaAleatoria(rand);
            Localizacao d1 = getCalcadaAleatoria(rand);
            PedestreAmbulante p = new PedresteOnibus(s1,  d1, mapa, rand);
            mapa.adicionarObjeto(p);
        }
        
        long inicio = System.nanoTime();
        for (int i = 0; i < 150; i++) {
            // Localizacao s = getRuaAleatoria(rand);
            // Localizacao d = getRuaAleatoria(rand);
            // Localizacao s1 = getCalcadaAleatoria(rand);
            // Localizacao d1 = getCalcadaAleatoria(rand);
            // Veiculo v = new Carro(s, d, mapa, rand);
            // PedestreAmbulante p = new PedestreAmbulante(s1, d1, mapa, rand);
            // mapa.adicionarObjeto(v);
            // mapa.adicionarObjeto(p);
        }
        int tempo = (int) ((System.nanoTime() - inicio) / 1000000);
        System.out.printf("T: %d%n", tempo);
        // Inicializando o mapa com o veículo
        janelaSimulacao = new JanelaSimulacao(mapa);
    }

    private Localizacao getRuaAleatoria(Random r) {
        int x = r.nextInt(mapa.getLargura());
        int y = r.nextInt(mapa.getAltura());
        while (!(mapa.getObjeto(Mapa.Camada.BACKGROUND, x, y) instanceof Rua)) {
            x = r.nextInt(mapa.getLargura());
            y = r.nextInt(mapa.getAltura());
        } 
        return new Localizacao(x, y);
    }

    private Localizacao getCalcadaAleatoria(Random r) {
        int x = r.nextInt(mapa.getLargura());
        int y = r.nextInt(mapa.getAltura());
        while (!(mapa.getObjeto(Mapa.Camada.BACKGROUND, x, y) instanceof Calcada)) {
            x = r.nextInt(mapa.getLargura());
            y = r.nextInt(mapa.getAltura());
        } 
        return new Localizacao(x, y);
    }
    /**
     * Retorna a calçada mais proxima
     */
    public  Localizacao getCalcadaMaisProxima(Localizacao rua) {
        List<Localizacao> posMaisProximas = new ArrayList<Localizacao>();
        posMaisProximas.add(new Localizacao(rua.getX(), rua.getY() +1));
        posMaisProximas.add(new Localizacao(rua.getX() -1, rua.getY() ));
        posMaisProximas.add(new Localizacao(rua.getX() +1, rua.getY() ));
        posMaisProximas.add(new Localizacao(rua.getX(), rua.getY() -1));
        posMaisProximas.add(new Localizacao(rua.getX() +1, rua.getY() +1));
        posMaisProximas.add(new Localizacao(rua.getX() -1, rua.getY() +1));
        posMaisProximas.add(new Localizacao(rua.getX() +1, rua.getY() -1));
        posMaisProximas.add(new Localizacao(rua.getX() -1, rua.getY() -1));
        int index = 0;
        while (!(mapa.getObjeto(Mapa.Camada.BACKGROUND, posMaisProximas.get(index).getX(),
        posMaisProximas.get(index).getY()) instanceof Calcada)) {
            index++;
        } 
        return posMaisProximas.get(index);
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
            Thread.sleep(milisegundos < 0 ? 0 : milisegundos);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
