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
    // Mapa de densidade de população do gerador
    private double[][] densidade;
    // Gerador de números aleatórios
    private Random rng;
    // Altura e largura do mapa do gerador
    private int largura, altura;
    // Número máximo de segmentos a serem gerados
    private int maxSegmentos;

    public Gerador(int largura, int altura, Random rng) {
        // Internamente, a geração é feita em 1/16 da resolução original.
        //
        // Isso ajuda muito para gerar o mapa final, visto que colisões entre
        // segmentos são tratados de forma muito mais simples (são linhas ao
        // invés de retângulos), e os resultados são muito mais simétricos.
        this.largura = largura / 4;
        this.altura = altura / 4;
        this.rng = rng;
        this.densidade = new double[altura][largura];
        // Uma boa estimativa para o número máximo de segmentos é a média
        // geométrica de largura e altura originais
        this.maxSegmentos = (int) Math.sqrt(largura * altura);

        // Seleciona a camada 2D do volume 3D de Perlin Noise
        double z = rng.nextDouble();

        // Multiplicadores de escala para fazer com que x e y caibam no volume
        // de Perlin noise (se forem apenas inteiros, o algoritmo de Perlin faz
        // com que os valores sempre caiam na posição (0,0) do volume de Perlin
        // Noise)
        double lm = 3.0 / largura;
        double am = 3.0 / altura;

        // Menor e maior valores vistos até agora, utilizados para normalizar o
        // ruído gerado para [0, 1].
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

        // Multiplicador para normalizar o ruído
        double norm = 1 / (maior - menor);
        // Normaliza o mapa de ruído
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                densidade[y][x] = (densidade[y][x] - menor) * norm;
            }
        }
    }

    // Retorna a distância ao quadrado entre (x0, y0) e (x1, y1)
    private static double distanceSquared(double x0, double y0, double x1, double y1) {
        double dx = x1 - x0;
        double dy = y1 - y0;
        return dx * dx + dy * dy;
    }

    // Retorna a direção que deve ser seguida a partir de (x, y) para ir para
    // uma área com densidade populacional grande
    private Direcao direcaoDensa(int x, int y) {
        // dx e dy armazenam a direção média ponderada para uma direção com
        // densidade populacional alta
        double dx = 0, dy = 0;

        for (int my = 0; my < altura; my++) {
            for (int mx = 0; mx < largura; mx++) {
                if (mx == x && my == y) continue;
                // O peso do vetor (mx-x, my-y) é dado pela densidade em (mx, my) 
                // divida pela distância à quarta (isso ajuda locais próximos a
                // terem um peso muito maior que locais distantes)
                double p = densidade[my][mx] / Math.pow(distanceSquared(x, y, mx, my), 2);
                dx += (mx - x) * p;
                dy += (my - y) * p;
            }
        }

        // Calcula hipotenusa de (dx, dy) e divide por sqrt(2) para poder
        // comparar com os componentes do vetor.
        //
        // É possível chegar nisso tentando normalizar o vetor (dx, dy) para
        // saber a direção que o vetor predominantemente aponta (na comparação
        // abaixo, substitua -m por -0.5 e m por 0.5)
        double m = Math.sqrt(0.5 * (dx * dx + dy * dy));

        // Retorna a direção que o vetor (dx, dy) predominantemente aponta para
        if (dx >= -m && dx <= m) {
            if (dy >= 0.0) return Direcao.SUL;
            else           return Direcao.NORTE;
        } else {
            if (dx >= 0.0) return Direcao.LESTE;
            else           return Direcao.OESTE;
        }
    }

    // Gera um segmento inicial
    private Segmento segmentoInicial() {
        double maior = 0;
        int x = 0;
        int y = 0;

        // Encontra o ponto com maior densidade
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
        // Escolhe uma direção aleatória para o segmento inicial
        if (rng.nextBoolean()) {
            // Norte-Sul
            y = 0;
            o = Direcao.SUL;
            l = altura;
        } else {
            // Leste-Oeste
            x = 0;
            o = Direcao.LESTE;
            l = largura;
        }

        // Cria um novo segmento partindo de (x, y) com comprimento l/4
        return new Segmento(x, y, o, l / 4, Segmento.RUA, true);
    }

    // Gera o mapa em si
    public List<Segmento> gerar() {
        // S é a lista de todos os segmentos. O mesmo nome é utilizado pelo
        // resto do código
        List<Segmento> S = new ArrayList<>();
        // J é a lista de junções entre segmentos
        List<Juncao> J = new ArrayList<>();

        // Q é a fila de prioridade de segmentos a serem processados
        PriorityQueue<EntradaSegmento> Q = new PriorityQueue<>();
        // Inicializa a fila com o segmento inicial
        Q.add(new EntradaSegmento(0, segmentoInicial()));

        // Enquanto ainda tiverem segmentos a serem processados e não foi
        // estourado o limite de segmentos
        while (!Q.isEmpty() && S.size() < maxSegmentos) {
            // Retira o próximo elemento a ser possivelmente colocado na lista
            // final
            EntradaSegmento e = Q.remove();
            // Verifica se o segmento é válido para ser colocado
            if (localConstraints(S, J, e)) {
                // Coloca o segmento na lista
                S.add(e.r);
                for (EntradaSegmento e1 : globalGoals(e)) {
                    // Cada segmento que pode ser gerado a partir deste é
                    // adicionado na fila de prioridade
                    e1.t += e.t + 1;
                    Q.add(e1);
                }
            }
        }

        // Realiza passo de pós-processamento
        posprocessar(S);

        imprimir(S);

        return S;
    }

    // Realiza um passo de pós-processamento para arrumar fins soltos de ruas
    public void posprocessar(List<Segmento> S) {
        boolean melhoravel = true;
        // Enquanto ainda houver possíveis fins soltos
        while (melhoravel) {
            melhoravel = false;

            // Tenta arrumar cada segmento r de S
            for (Segmento r : S) {
                // Se r já foi marcado como que já está em sua versão final,
                // então não o verifica novamente
                if (r.getMelhor()) continue;
                // Representa o ponto que está no final do segmento
                Segmento t = r.getFinal();
                int x = t.getX();
                int y = t.getY();
                // Se esse ponto passa dos limites do mapa, então o segmento
                // está correto
                if (x >= largura || y >= altura || y <= 0 || x <= 0) {
                    r.setMelhor(true);
                    continue;
                }
                // Verifica se o ponto realiza interseção com outro segmento
                boolean intersecta = false;
                for (Segmento s : S) {
                    if (t.intersecta(s)) {
                        intersecta = true;
                        break;
                    }
                }
                // Se o ponto não realiza interseção com nenhum outro segmento,
                // então aumenta o tamanho do segmento
                if (!intersecta) {
                    r.setComprimento(r.getComprimento() + 1);
                    melhoravel = true;
                } else {
                    r.setMelhor(true);
                }
            }
        }
    }

    // Escreve em um arquivo a imagem do mapa gerado
    public void imprimir(List<Segmento> S) {
        int largura = this.largura * 4;
        int altura = this.altura * 4;
        BufferedImage im = new BufferedImage(largura, altura, BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                im.setRGB(x, y, 0xFFFFFF);
            }
        }
        for (Segmento s : S) {
            int[] clip = s.getClip();
            // printClip(clip);
            Direcao d = s.getDirecao();
            int ex = Math.abs(d.componenteY()) + Math.abs(d.componenteX());
            for (int x = clip[0] * 4; x <= clip[2] * 4 + ex && x < largura; x++) {
                for (int y = clip[3] * 4; y <= clip[1] * 4 + ex && y < altura; y++) {
                    // int cor = (s.getX() == x / 4 && s.getY() == y / 2) ? 0xFFFF00 : (s.segueReto ? 0xFF00FF : 0x00FFFF);
                    int cor = 0;
                    im.setRGB(x, y, cor);
                }
            }
        }
        try {
            ImageIO.write(im, "bmp", new File("mapa.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Realiza a verificação da possibilidade de um segmento ser colocado, e o
    // ajusta em um limite de passos para que talvez possa ser válido
    private boolean localConstraints(List<Segmento> S, List<Juncao> J, EntradaSegmento e) {
        boolean ok = true;
        // São 5 tentativas de colocação no total
        for (int i = 0; i < 5; i++) {
            ok = true;
            int[] clip = e.r.getClip();
            
            // Se um segmento é muito curto, o abandona
            if (e.r.getComprimento() <= 1) {
                ok = false;
                break;
            }

            // Verifica se é longo demais (ultrapassa os limites do mapa)
            boolean longoDemais = false;
            for (int j = 0; j < 4; j += 2) {
                if (longoDemais) continue;
                if (clip[j] < 0 || clip[j] > largura) longoDemais = true;
            }
            for (int j = 1; j < 4; j += 2) {
                if (longoDemais) continue;
                if (clip[j] < 0 || clip[j] > altura) longoDemais = true;
            }
            // Se for longo demais, diminui seu tamanho e tenta novamente
            if (longoDemais) {
                ok = false;
                e.r.setComprimento(e.r.getComprimento() - 1);
                continue;
            }

            // Verifica se o segmento interage com outros
            for (Segmento s : S) {
                if (realizaJuncao(J, s, e.r)) {
                    // Se já realiza junção com este segmento s, então o ignora
                    continue;
                } if (s.muitoProximo(e.r)) {
                    // Se está muito próximo de outro segmento, não há muito o que
                    // fazer de forma segura, então o segmento é abandonado
                    ok = false;
                    break;
                } else if (s.intersecta(e.r)) {
                    // Se pode realizar interseção com outro segmento, então a cria
                    criarJuncao(J, s, e.r);
                    break;
                }
            }
            // Termina a verificação se o segmento deve ser abandonado
            if (!ok) {
                break;
            }
        }
        return ok;
    }

    // Verifica se existe uma junção de r e s
    private boolean realizaJuncao(List<Juncao> J, Segmento r, Segmento s) {
        for (Juncao j : J) {
            if (j.contem(r) && j.contem(s)) return true;
        }
        return false;
    }

    // Cria uma junção entre 
    private void criarJuncao(List<Juncao> J, Segmento r, Segmento s) {
        J.add(new Juncao(r, s));
    }

    // Gera candidatos de segmentos saindo de um segmento
    private List<EntradaSegmento> globalGoals(EntradaSegmento e) {
        List<EntradaSegmento> ES = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Segmento s = gerarCandidato(e);
            ES.add(new EntradaSegmento(i, s));
        }
        return ES;
    }

    // Gera um segmento candidato que sai de um segmento
    private Segmento gerarCandidato(EntradaSegmento e) {
        Segmento s = e.r;
        // 2/3 vezes o novo segmento seguirá reto a partir do original
        boolean segueReto = rng.nextInt(3) < 2;
        // Tamanho do segmento
        int l;
        // Posição (x, y) do segmento
        int x, y;
        // Direção
        Direcao d;
        // Tipo
        int t;
        if (segueReto) {
            t = s.getTipo();
            d = s.getDirecao();
            // O tamanho do segmento varia em [0, s.l + 5]
            l = selecionar(0, s.getComprimento() + 5);
            // O segmento começa a partir do final do anterior
            int delta = s.getComprimento();
            x = s.getX() + d.componenteX() * delta;
            y = s.getY() + d.componenteY() * delta;
        } else {
            t = Segmento.RUA;
            Direcao ed = s.getDirecao();
            // O segmento começa de um ponto aleatório em [0, s.l]
            int delta = selecionar(0, s.getComprimento());
            x = s.getX() + ed.componenteX() * delta;
            y = s.getY() + ed.componenteY() * delta;
            // O comprimento varia em [0, 10]
            l = selecionar(5, 10);
            // A direção principal a ser considerada é a direção que leva a um
            // lugar com maior densidade poopulacional
            d = direcaoDensa(x, y);
            // Se a direção for a mesma que se for seguir reto, tenta direção
            // aleatória que não seja seguir reto
            while (d == ed || d == ed.oposta()) {
                d = Direcao.TODAS[rng.nextInt(4)];
            }
        }

        return new Segmento(x, y, d, l, t, segueReto);
    }

    // Retorna um valor aleatório em [x, y] (ou [y, x] se x > y)
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
