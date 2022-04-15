package simulacao.geracao;

import java.util.HashSet;
import java.util.Set;

public class Juncao {
    private Set<Segmento> S;

    public Juncao(Segmento r, Segmento s) {
        S = new HashSet<>();
        S.add(r);
        S.add(s);
    }

    public void add(Segmento s) {
        S.add(s);
    }

    public boolean contem(Segmento s) {
        return S.contains(s);
    }
}
