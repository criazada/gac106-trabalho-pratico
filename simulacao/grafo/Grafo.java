package simulacao.grafo;

import java.util.PriorityQueue;

public class Grafo {
    public static final int INF = 1000000000;
    private ListaAresta[] G;
    private int n;

    public Grafo(int n) {
        this.n = n;
        limpar();
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

    public int[] dijkstra(int s) {
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

            for (Aresta a : G[u].getArestas()) {
                int v = a.getV();
                int novo = a.getPeso() + d;
                if (novo < dist[v]) {
                    dist[v] = novo;
                    prev[v] = u;
                    Q.add(new EntradaVertice(novo, v));
                }
            }
        }

        return prev;
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
}