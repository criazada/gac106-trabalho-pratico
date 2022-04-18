package simulacao.grafo;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Grafo {
    public static final int INF = 1000000000;
    private class ListaAresta extends ArrayList<Aresta> {}
    private ListaAresta[] G;
    private int n;

    public Grafo(int n) {
        this.n = n;
        limpar();
    }

    protected Grafo(ListaAresta[] G, int n) {
        this.G = G;
        this.n = n;
    }

    public void limpar() {
        G = new ListaAresta[n];
        for (int v = 0; v < n; v++) {
            G[v] = new ListaAresta();
        }
    }

    public void addAresta(int v, Aresta a) {
        G[v].add(a);
    }

    public ResultadoDijkstra dijkstra(int s) {
        boolean[] visitado = new boolean[n];
        int[] dist = new int[n];
        int[] prev = new int[n];
        PriorityQueue<EntradaVertice> Q = new PriorityQueue<>();

        dist[s] = 0;
        for (int v = 0; v < n; v++) {
            if (v != s) {
                dist[v] = INF;
            }
            prev[v] = -1;
            Q.add(new EntradaVertice(dist[v], v));
        }

        while (!Q.isEmpty()) {
            EntradaVertice e = Q.remove();
            int u = e.v;
            int d = e.dist;

            if (visitado[u]) continue;
            visitado[u] = true;

            for (Aresta a : G[u]) {
                int v = a.getV();
                int novo = a.getPeso() + d;
                if (novo < dist[v]) {
                    dist[v] = novo;
                    prev[v] = u;
                    Q.add(new EntradaVertice(novo, v));
                }
            }
        }

        return new ResultadoDijkstra(dist, prev);
    }

    public GrafoImutavel getGrafoImutavel() {
        return new GrafoImutavel(this.G, this.n);
    }

    private class EntradaVertice implements Comparable<EntradaVertice> {
        int dist;
        int v;

        EntradaVertice(int dist, int v) {
            this.dist = dist;
            this.v = v;
        }

        @Override
        public int compareTo(EntradaVertice o) {
            return Integer.compare(dist, o.dist);
        }
    }

    public class ResultadoDijkstra {
        public int[] dist;
        public int[] prev;

        public ResultadoDijkstra(int[] dist, int[] prev) {
            this.dist = dist;
            this.prev = prev;
        }
    }

    public class GrafoImutavel extends Grafo {
        private GrafoImutavel(ListaAresta[] G, int n) {
            super(G, n);
        }

        @Override
        public void limpar() {}

        @Override
        public void addAresta(int v, Aresta a) {}
    }
}
