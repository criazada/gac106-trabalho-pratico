package simulacao.grafo;

public class Grafo {
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
}
