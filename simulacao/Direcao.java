package simulacao;

public enum Direcao {
    NORTE(0, -1),
    SUL(0, 1),
    LESTE(1, 0),
    OESTE(-1, 0);

    private int x;
    private int y;

    private Direcao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Localizacao seguindo(Localizacao l) {
        return new Localizacao(l.getX() + x, l.getY() + y);
    }

    public Localizacao para(Localizacao l) {
        return new Localizacao(l.getX() - x, l.getY() - y);
    }

    public Direcao oposta() {
        switch (this) {
            case NORTE:
                return SUL;

            case SUL:
                return NORTE;

            case LESTE:
                return OESTE;

            case OESTE:
                return LESTE;

            default:
                return null;
        }
    }

    public static Direcao calcular(Localizacao anterior, Localizacao atual) {
        int dx = anterior.getX() - atual.getX();
        int dy = anterior.getY() - atual.getY();
        if (dx > 0) {
            return OESTE;
        } else if (dx < 0) {
            return LESTE;
        } else if (dy > 0) {
            return NORTE;
        } else {
            return SUL;
        }
    }
}
