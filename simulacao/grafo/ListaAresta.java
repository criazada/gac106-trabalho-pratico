package simulacao.grafo;

import java.util.ArrayList;
import java.util.List;

public class ListaAresta {
    private List<Aresta> arestas;

    public ListaAresta() {
        arestas = new ArrayList<>();
    }

    public void add(Aresta a) {
        arestas.add(a);
    }
}
