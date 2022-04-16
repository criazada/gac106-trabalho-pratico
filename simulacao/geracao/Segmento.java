package simulacao.geracao;

import simulacao.Direcao;

public class Segmento {
    public static final int RUA = 0;
    public static final int AVENIDA = 1;

    private Direcao direcao;
    private int x;
    private int y;
    private int comprimento;
    private int tipo;
    private int[] clip;
    boolean segueReto;
    private boolean melhor;
    private Segmento ignorarColisao;

    public Segmento(int x, int y, Direcao direcao, int comprimento, int tipo, boolean segueReto, Segmento ignorarColisao) {
        this.x = x;
        this.y = y;
        this.direcao = direcao;
        this.comprimento = comprimento;
        this.tipo = tipo;
        this.clip = calcularClip();
        this.segueReto = segueReto;
        this.melhor = false;
        this.ignorarColisao = ignorarColisao;
    }

    public Segmento(int x, int y, Direcao direcao, int comprimento, int tipo, boolean segueReto) {
        this(x, y, direcao, comprimento, tipo, segueReto, null);
    }

    public int[] calcularClip() {
        int x1, y1;
        if (direcao == Direcao.LESTE || direcao == Direcao.OESTE) {
            x1 = x + comprimento * direcao.componenteX();
            y1 = y;
        } else {
            x1 = x;
            y1 = y + comprimento * direcao.componenteY();
        }

        int x = this.x;
        int y = this.y;

        if (x > x1) {
            x1 ^= x;
            x ^= x1;
            x1 ^= x;
        }

        if (y < y1) {
            y1 ^= y;
            y ^= y1;
            y1 ^= y;
        }

        return new int[]{x, y, x1, y1};
    }

    public boolean intersecta(Segmento s) {
        if (s == ignorarColisao || s.ignorarColisao == this) return false;
        if (comprimento == 0) {
            return clip[0] >= s.clip[0] && clip[0] <= s.clip[2] && clip[1] >= s.clip[3] && clip[1] <= s.clip[1];
        }
        if (direcao == s.direcao || direcao == s.direcao.oposta()) return false;
        if (clip[0] >= s.clip[2] || s.clip[0] >= clip[2]) return false;
        if (clip[3] >= s.clip[1] || s.clip[3] >= clip[1]) return false;
        return true;
    }

    public boolean muitoProximo(Segmento s) {
        if (s == ignorarColisao || s.ignorarColisao == this) return false;
        if (clip[0] >= s.clip[2] + 1 || s.clip[0] >= clip[2] + 1) return false;
        if (clip[3] >= s.clip[1] + 1 || s.clip[3] >= clip[1] + 1) return false;
        if (direcao == s.direcao || direcao == s.direcao.oposta()) {
            if (direcao.componenteY() != 0 && clip[0] == s.clip[0] && (clip[3] >= s.clip[1] || s.clip[3] >= clip[1])) return false;
            if (direcao.componenteX() != 0 && clip[1] == s.clip[1] && (clip[0] >= s.clip[2] || s.clip[0] >= clip[2])) return false;
        } else {
            return false;
        }
        return true;
    }

    public void setComprimento(int comprimento) {
        this.comprimento = comprimento;
        clip = calcularClip();
    }

    public Segmento getFinal() {
        int x = this.x + direcao.componenteX() * comprimento;
        int y = this.y + direcao.componenteY() * comprimento;
        return new Segmento(x, y, direcao, 0, tipo, segueReto, this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] getClip() {
        return clip;
    }

    public int getComprimento() {
        return comprimento;
    }

    public int getTipo() {
        return tipo;
    }

    public Direcao getDirecao() {
        return direcao;
    }

    public void setMelhor(boolean melhor) {
        this.melhor = melhor;
    }

    public boolean getMelhor() {
        return melhor;
    }
}
