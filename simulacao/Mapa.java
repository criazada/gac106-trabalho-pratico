package simulacao;

import java.util.*;

import simulacao.grafo.Aresta;
import simulacao.grafo.Grafo;

/**
 * Representa um mapa com todos os itens que participam da simulacao
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Mapa {
    public enum Camada {
        BACKGROUND, MIDDLE, FOREGROUND;
    }

    public enum PontoDeInteresse {
        PONTO_ONIBUS, PONTO_ONIBUS_CALCADA, ESTATICO, EXECUTAVEL, OBRA;
    }

    public enum TipoGrafo {
        PEDESTRE(new FantasmaPedestre()), VEICULO(new FantasmaVeiculo());

        private Fantasma f;
        private TipoGrafo(Fantasma f) {
            this.f = f;
        }
    }

    public class ConjuntoObjetoSimulacao extends HashSet<ObjetoSimulacao> {}

    private ObjetoSimulacao[][][] camadas;
    private ConjuntoObjetoSimulacao[] pontosDeInteresse;

    private Grafo[] grafos;

    private int largura;
    private int altura;

    private Estatisticas estatisticas;

    private static final int LARGURA_PADRAO = 35;
    private static final int ALTURA_PADRAO = 35;

    /**
     * Cria mapa para alocar itens da simulacao.
     *
     * @param largura: largura da área de simulacao.
     * @param altura:  altura da área de simulação.
     */
    public Mapa(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        camadas = new ObjetoSimulacao[Camada.values().length][][];
        for (int i = 0; i < camadas.length; i++) {
            camadas[i] = new ObjetoSimulacao[altura][largura];
        }
        grafos = new Grafo[TipoGrafo.values().length];
        for (int i = 0; i < grafos.length; i++) {
            grafos[i] = new Grafo(altura * largura);
        }

        pontosDeInteresse = new ConjuntoObjetoSimulacao[PontoDeInteresse.values().length];
        for (int i = 0; i < pontosDeInteresse.length; i++) {
            pontosDeInteresse[i] = new ConjuntoObjetoSimulacao();
        }

        estatisticas = new Estatisticas();
    }

    public Estatisticas getEstatisticas() {
        return estatisticas;
    }

    /**
     * Cria mapa com tamanho padrao.
     */
    public Mapa() {
        this(LARGURA_PADRAO, ALTURA_PADRAO);
    }

    /**
     * Adiciona um objeto no mapa.
     *
     * @param o Objeto a ser adicionado
     */
    public void adicionarObjeto(ObjetoSimulacao o) {
        setObjeto(o.getCamada(), o.getLocalizacao(), o);
    }

    /**
     * Remove um objeto do mapa.
     * @param o Objeto a ser removido
     */
    public void removerObjeto(ObjetoSimulacao o) {
        getPontosDeInteresseMutavel(o.getTipoPontoDeInteresse()).remove(o);
        setObjeto(o.getCamada(), o.getLocalizacao(), null);
    }

    /**
     * Atualiza a posição de um objeto no mapa.
     * @param o Objeto a ser atualizado
     */
    public void atualizarMapa(ObjetoSimulacao o) {
        Camada c = o.getCamada();
        Localizacao anterior = o.getLocalizacaoAnterior();
        if (getObjeto(c, anterior) != o) {
            System.out.printf("atualizarMapa: %s tentou atualizar posicao que nao e sua: %s %s %s%n", o, anterior, o.getLocalizacao(), getObjeto(c, anterior));
        }
        setObjeto(c, anterior, null);
        setObjeto(c, o.getLocalizacao(), o);
    }

    private void atualizarGrafo(Grafo G, Fantasma f) {
        G.limpar();
        boolean[][] visitado = new boolean[altura][largura];
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                dfs(G, f, visitado, x, y);
            }
        }
    }

    private int indiceVertice(int x, int y) {
        return y * largura + x;
    }

    private int traduzir(Localizacao l) {
        return indiceVertice(l.getX(), l.getY());
    }

    private int traduzirParaX(int v) {
        return v % largura;
    }

    private int traduzirParaY(int v) {
        return v / largura;
    }

    public void atualizarGrafos() {
        for (TipoGrafo tp : TipoGrafo.values()) {
            atualizarGrafo(getGrafoMutavel(tp), tp.f);
        }
    }

    private void dfs(Grafo G, Fantasma f, boolean[][] visitado, int x, int y) {
        if (visitado[y][x]) return;
        visitado[y][x] = true;

        int[][] p = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int u = indiceVertice(x, y);
        Localizacao l = new Localizacao(x, y);
        for (int[] prox : p) {
            f.setLocalizacao(l);
            int x1 = prox[0] + x;
            int y1 = prox[1] + y;

            if (x1 < 0 || x1 >= largura || y1 < 0 || y1 >= altura) continue;

            boolean passavel = true;
            for (ObjetoSimulacao s : getObjetosEm(x1, y1)) {
                if (!s.transparentePara(f)) {
                    passavel = false;
                    break;
                }
            }

            if (passavel) {
                int v = indiceVertice(x1, y1);
                G.addAresta(u, new Aresta(v, 1));
                dfs(G, f, visitado, x1, y1);
            }
        }
    }

    private Grafo getGrafoMutavel(TipoGrafo t) {
        return grafos[t.ordinal()];
    }

    public Grafo.GrafoImutavel getGrafo(TipoGrafo t) {
        return getGrafoMutavel(t).getGrafoImutavel();
    }

    public List<Localizacao> getCaminhoParaDestino(ObjetoAmbulante o) {
        Localizacao l = o.getLocalizacao();
        int s = traduzir(l);

        int[] prev = getGrafo(o.getTipoGrafo()).dijkstra(s).prev;
        List<Localizacao> passos = new ArrayList<>();

        Localizacao d = o.getLocalizacaoDestino();
        int v = traduzir(d);
        while (prev[v] != -1) {
            int x = traduzirParaX(v);
            int y = traduzirParaY(v);
            passos.add(new Localizacao(x, y));
            v = prev[v];
        }

        Collections.reverse(passos);
        return Collections.unmodifiableList(passos);
    }

    private ConjuntoObjetoSimulacao getPontosDeInteresseMutavel(PontoDeInteresse pdi) {
        return pontosDeInteresse[pdi.ordinal()];
    }

    public void addPontoDeInteresse(PontoDeInteresse pdi, ObjetoSimulacao o) {
        getPontosDeInteresseMutavel(pdi).add(o);
    }

    public Set<ObjetoSimulacao> getPontosDeInteresse(PontoDeInteresse pdi) {
        return Collections.unmodifiableSet(getPontosDeInteresseMutavel(pdi));
    }

    public ObjetoSimulacao getPontoDeInteresseMaisProximo(ObjetoAmbulante o, PontoDeInteresse pdi) {
        Set<ObjetoSimulacao> pontos = getPontosDeInteresse(pdi);
        ObjetoSimulacao maisProximo = null;
        int menorDistancia = Grafo.INF;

        int[] dist = getGrafo(o.getTipoGrafo()).dijkstra(traduzir(o.getLocalizacao())).dist;
        for (ObjetoSimulacao s : pontos) {
            int v = traduzir(s.getLocalizacao());
            if (dist[v] < menorDistancia) {
                menorDistancia = dist[v];
                maisProximo = s;
            }
        }

        return maisProximo;
    }

    public Localizacao getRuaAleatoria(Random r) {
        int x = r.nextInt(getLargura());
        int y = r.nextInt(getAltura());
        while (!(getObjeto(Camada.BACKGROUND, x, y) instanceof Rua)) {
            x = r.nextInt(getLargura());
            y = r.nextInt(getAltura());
        } 
        return new Localizacao(x, y);
    }

    public Localizacao getCalcadaAleatoria(Random r) {
        int x = r.nextInt(getLargura());
        int y = r.nextInt(getAltura());
        while (!(getObjeto(Camada.BACKGROUND, x, y) instanceof Calcada)) {
            x = r.nextInt(getLargura());
            y = r.nextInt(getAltura());
        } 
        return new Localizacao(x, y);
    }

    /**
     * Retorna a matriz de objetos de uma camada
     * @param c Camada
     * @return matriz de objetos da camada
     */
    private ObjetoSimulacao[][] getCamada(Camada c) {
        return camadas[c.ordinal()];
    }

    /**
     * Retorna o objeto em uma posição (x, y) em uma camada do mapa.
     * @param c Camada
     * @param x Posição X do objeto
     * @param y Posição Y do objeto
     * @return Objeto em (x, y) na camada
     */
    public ObjetoSimulacao getObjeto(Camada c, int x, int y) {
        return getCamada(c)[x][y];
    }

    /**
     * Retorna o objeto em uma localização em uma camada do mapa.
     * @param c Camada
     * @param l Localização do objeto
     * @return Objeto na localização dada na camada
     */
    public ObjetoSimulacao getObjeto(Camada c, Localizacao l) {
        return getObjeto(c, l.getX(), l.getY());
    }

    /**
     * Retorna o objeto em uma localização no foreground do mapa.
     * @param l Localização do objeto
     * @return Objeto na localização dada no foreground
     */
    public ObjetoSimulacao getObjetoForeground(Localizacao l) {
        return getObjeto(Camada.FOREGROUND, l);
    }

    /**
     * Retorna o objeto em uma localização na camada middle do mapa.
     * @param l Localização do objeto
     * @return Objeto na localização dada na camada middle
     */
    public ObjetoSimulacao getObjetoMiddle(Localizacao l) {
        return getObjeto(Camada.MIDDLE, l);
    }

    /**
     * Retorna o objeto em uma localização no background do mapa.
     * @param l Localização do objeto
     * @return Objeto na localização dada no background
     */
    public ObjetoSimulacao getObjetoBackground(Localizacao l) {
        return getObjeto(Camada.BACKGROUND, l);
    }

    /**
     * Retorna os objetos em ambas camadas do mapa em uma localização.
     * @param l Localização dos objetos
     * @return Os objetos na localização
     */
    public List<ObjetoSimulacao> getObjetosEm(Localizacao l) {
        return getObjetosEm(l.getX(), l.getY());
    }

    public List<ObjetoSimulacao> getObjetosEm(int x, int y) {
        List<ObjetoSimulacao> objetos = new ArrayList<>();
        for (Camada c : Camada.values()) {
            ObjetoSimulacao o = getObjeto(c, x, y);
            if (o != null) {
                objetos.add(o);
            }
        }
        return objetos;
    }

    /**
     * Posiciona o objeto o em (x, y) na camada
     * @param c Camada
     * @param x Posição X
     * @param y Posição Y
     * @param o Objeto a ser posicionado
     */
    private void setObjeto(Camada c, int x, int y, ObjetoSimulacao o) {
        getCamada(c)[x][y] = o;
    }

    /**
     * Posiciona o objeto o na localização na camada
     * @param c Camada
     * @param l Localização
     * @param o Objeto a ser posicionado
     */
    private void setObjeto(Camada c, Localizacao l, ObjetoSimulacao o) {
        setObjeto(c, l.getX(), l.getY(), o);
    }

    /**
     * Captura a lista de objetos válidos em todas as camadas do mapa. Útil para
     * impedir que um mesmo objeto que se move seja atualizado mais de uma vez
     * em uma iteração da simulação.
     * @return Lista imutável de todos os objetos válidos da simulação.
     */
    public List<ObjetoSimulacao> getObjetos() {
        List<ObjetoSimulacao> objetosValidos = new ArrayList<>();

        for (Camada c : Camada.values()) {
            for (int y = getAltura() - 1; y >= 0; y--) {
                for (int x = getLargura() - 1; x >= 0; x--) {
                    ObjetoSimulacao objeto = getObjeto(c, x, y);
                    if (objeto != null) {
                        objetosValidos.add(objeto);
                    }
                }
            }
        }

        return Collections.unmodifiableList(objetosValidos);
    }
    /**
     * @return Retorna a largura do mapa.
     */
    public int getLargura() {
        return largura;
    }
    /**
     * @return Retorna a altura do mapa.
     */
    public int getAltura() {
        return altura;
    }
}
