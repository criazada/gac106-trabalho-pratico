package simulacao;

public enum Direcao {
    NORTE(0, -1, "^"),
    SUL(0, 1, "v"),
    LESTE(1, 0, "<"),
    OESTE(-1, 0, ">");

    public static final Direcao[] TODAS = { NORTE, SUL, LESTE, OESTE };

    private int x;
    private int y;
    private String c;

    private Direcao(int x, int y, String c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public Localizacao seguindo(Localizacao l) {
        return new Localizacao(l.getX() + x, l.getY() + y);
    }

    public Localizacao para(Localizacao l) {
        return new Localizacao(l.getX() - x, l.getY() - y);
    }

    public String caractere() {
        return c;
    }

    public int componenteX() {
        return x;
    }

    public int componenteY() {
        return y;
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
        } else if (dy < 0) {
            return SUL;
        } else {
            return NORTE;
        }
    }

    @Override
    public String toString() {
        return c;
    }
}
