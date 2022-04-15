package simulacao.geracao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import javax.imageio.ImageIO;

import simulacao.Direcao;

public class Gerador {
    private static final int MAX_SEGMENTOS = 200;

    private double[][] densidade;
    private Random rng;
    private int largura, altura;

    public Gerador(int largura, int altura, Random rng) {
        this.largura = largura;
        this.altura = altura;
        this.rng = rng;
        this.densidade = new double[altura][largura];

        int z = rng.nextInt();

        double lm = 3.0 / largura;
        double am = 3.0 / altura;

        double maior = -1;
        double menor = 1;
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                double d = ImprovedNoise.noise(x * lm, y * am, z);
                if (d < menor) menor = d;
                if (d > maior) maior = d;
                densidade[y][x] = d;
            }
        }

        double norm = 1 / (maior - menor);
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                densidade[y][x] = (densidade[y][x] - menor) * norm;
            }
        }
    }

    private static double distanceSquared(double x0, double y0, double x1, double y1) {
        double dx = x1 - x0;
        double dy = y1 - y0;
        return dx * dx + dy * dy;
    }

    private Direcao direcaoDensa(int x, int y) {
        double dx = 0, dy = 0;

        for (int my = 0; my < altura; my++) {
            for (int mx = 0; mx < largura; mx++) {
                if (mx == x && my == y) continue;
                double p = densidade[my][mx] / Math.pow(distanceSquared(x, y, mx, my), 2);
                dx += (mx - x) * p;
                dy += (my - y) * p;
            }
        }

        double m = Math.sqrt(0.5 * (dx * dx + dy * dy));

        if (dx >= -m && dx <= m) {
            if (dy >= 0.0) return Direcao.SUL;
            else           return Direcao.NORTE;
        } else {
            if (dx >= 0.0) return Direcao.LESTE;
            else           return Direcao.OESTE;
        }
    }

    private Segmento segmentoInicial() {
        double maior = 0;
        int x = 0;
        int y = 0;

        for (int my = 0; my < altura; my++) {
            for (int mx = 0; mx < largura; mx++) {
                double d = densidade[my][mx];
                if (d > maior) {
                    maior = d;
                    x = mx;
                    y = my;
                }
            }
        }

        Direcao o;
        int l;
        if (rng.nextBoolean()) {
            // Avenida Norte-Sul
            y = 0;
            o = Direcao.SUL;
            l = altura;
        } else {
            // Avenida Leste-Oeste
            x = 0;
            o = Direcao.LESTE;
            l = largura;
        }

        return new Segmento(x, y, o, l, Segmento.AVENIDA, true);
    }

    public List<Segmento> gerar() {
        List<Segmento> S = new ArrayList<>();
        List<Juncao> J = new ArrayList<>();

        PriorityQueue<EntradaSegmento> Q = new PriorityQueue<>();
        Q.add(new EntradaSegmento(0, segmentoInicial()));

        while (!Q.isEmpty() && S.size() < MAX_SEGMENTOS) {
            EntradaSegmento e = Q.remove();
            if (localConstraints(S, J, e)) {
                S.add(e.r);
                for (EntradaSegmento e1 : globalGoals(e)) {
                    e1.t += e.t + 1;
                    Q.add(e1);
                }
            }
        }

        BufferedImage im = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                im.setRGB(x, y, 0xFFFFFF);
            }
        }
        for (Segmento s : S) {
            int[] clip = s.getClip();
            // printClip(clip);
            for (int x = clip[0]; x <= clip[2] && x < largura; x++) {
                for (int y = clip[3]; y <= clip[1] && y < altura; y++) {
                    im.setRGB(x, y, (s.getX() == x && s.getY() == y) ? 0xFFFF00 : (s.segueReto ? 0xFF00FF : 0x00FFFF));
                }
            }
        }
        try {
            ImageIO.write(im, "bmp", new File("mapa.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return S;
    }

    private void printClip(int[] clip) {
        System.out.print('{');
        for (int i = 0; i < clip.length; i++) {
            System.out.print(clip[i]);
            if (i < clip.length - 1) System.out.print(", ");
        }
        System.out.println('}');
    }

    private boolean localConstraints(List<Segmento> S, List<Juncao> J, EntradaSegmento e) {
        boolean ok = true;
        for (int i = 0; i < 5; i++) {
            ok = true;
            int[] clip = e.r.getClip();
            boolean longoDemais = false;
            for (int j = 0; j < 4; j += 2) {
                if (longoDemais) continue;
                if (clip[j] < 0 || clip[j] > largura) longoDemais = true;
            }
            for (int j = 1; j < 4; j += 2) {
                if (longoDemais) continue;
                if (clip[j] < 0 || clip[j] > altura) longoDemais = true;
            }
            if (longoDemais) {
                ok = false;
                e.r.setComprimento(e.r.getComprimento() - 1);
                continue;
            }
            for (Segmento s : S) {
                if (realizaJuncao(J, s, e.r)) {
                    continue;
                } else if (s.muitoProximo(e.r)) {
                    ok = false;
                    break;
                } else if (s.intersecta(e.r)) {
                    criarJuncao(J, s, e.r);
                    break;
                }
            }
            if (!ok) {
                break;
            }
        }
        return ok;
    }

    private boolean realizaJuncao(List<Juncao> J, Segmento r, Segmento s) {
        for (Juncao j : J) {
            if (j.contem(r) && j.contem(s)) return true;
        }
        return false;
    }

    private void criarJuncao(List<Juncao> J, Segmento r, Segmento s) {
        J.add(new Juncao(r, s));
    }

    private List<EntradaSegmento> globalGoals(EntradaSegmento e) {
        List<EntradaSegmento> ES = new ArrayList<>();
        int t = e.r.getTipo() == Segmento.AVENIDA ? 20 : 5;
        for (int i = 0; i < t; i++) {
            Segmento s = gerarCandidato(e);
            ES.add(new EntradaSegmento(i, s));
        }
        return ES;
    }

    private Segmento gerarCandidato(EntradaSegmento e) {
        boolean segueReto = rng.nextInt(3) < 2;
        int l;
        int x, y, xd, yd;
        Direcao d;
        int t;
        if (segueReto) {
            t = e.r.getTipo();
            d = e.r.getDirecao();
            l = escalar(e.r.getComprimento(), 2);
            xd = e.r.getComprimento();
            x = e.r.getX() + d.componenteX() * xd;
            y = e.r.getY() + d.componenteY() * xd;
        } else {
            d = direcaoDensa(e.r.getX(), e.r.getY());
            while (d == e.r.getDirecao() || d == e.r.getDirecao().oposta()) {
                d = Direcao.TODAS[rng.nextInt(4)];
            }
            l = ((int) Math.floor(rng.nextDouble() * (largura / 7))) + 7;
            xd = selecionar(0, e.r.getComprimento() / 2);
            x = e.r.getX() + e.r.getDirecao().componenteX() * xd;
            y = e.r.getY() + e.r.getDirecao().componenteY() * xd;
            t = Segmento.RUA;
        }

        return new Segmento(x, y, d, l, t, segueReto);
    }

    private int escalar(int x, double variacao) {
        double v = x * rng.nextDouble() * variacao;
        if (variacao < 0) {
            return (int) Math.floor(v);
        } else {
            return (int) Math.ceil(v);
        }
    }

    private int selecionar(int x, int y) {
        if (x > y) {
            x ^= y;
            y ^= x;
            x ^= y;
        }
        return rng.nextInt(y - x + 1) + x;
    }

    private class EntradaSegmento implements Comparable<EntradaSegmento> {
        int t;
        Segmento r;

        EntradaSegmento(int t, Segmento r) {
            this.t = t;
            this.r = r;
        }

        @Override
        public int compareTo(EntradaSegmento o) {
            return Integer.compare(t, o.t);
        }
    }
}
