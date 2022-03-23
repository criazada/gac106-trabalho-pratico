package simulacao;

import java.util.*;

/**
 * Representa um mapa com todos os itens que participam da simulacao
 * 
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Mapa {
    public enum Camada {
        FOREGROUND, BACKGROUND;

        public static final Camada[] TODAS = { BACKGROUND, FOREGROUND };
    }

    private ObjetoSimulacao[][] foreground;
    private ObjetoSimulacao[][] background;

    private int largura;
    private int altura;

    private static final int LARGURA_PADRAO = 35;
    private static final int ALTURA_PADRAO = 35;

    /**
     * Cria mapa para alocar itens da simulacao.
     * 
     * @param largura: largura da área de simulacao.
     * @param altura:  altura da área de simulação.
     */
    public Mapa(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        foreground = new ObjetoSimulacao[altura][largura];
        background = new ObjetoSimulacao[altura][largura];
    }

    /**
     * Cria mapa com tamanho padrao.
     */
    public Mapa() {
        this(LARGURA_PADRAO, ALTURA_PADRAO);
    }

    public void adicionarItem(Camada c, ObjetoSimulacao v) {
        setItem(c, v.getLocalizacao(), v);
    }

    public void removerItem(Camada c, ObjetoSimulacao v) {
        setItem(c, v.getLocalizacao(), null);
    }

    public void atualizarMapa(Camada c, ObjetoSimulacao v, Localizacao anterior) {
        if (getItem(c, anterior) != v) {
            System.out.printf("Veículo %s tentou atualizar posição que não é sua%n", v);
        }
        setItem(c, anterior, null);
        setItem(c, v.getLocalizacao(), v);
    }

    public ObjetoSimulacao[][] getCamada(Camada c) {
        switch (c) {
            case FOREGROUND:
                return foreground;
            case BACKGROUND:
                return background;
            default:
                System.out.println("getCamada: camada inválida");
                return null;
        }
    }

    public ObjetoSimulacao getItem(Camada c, int x, int y) {
        return getCamada(c)[x][y];
    }

    public ObjetoSimulacao getItem(Camada c, Localizacao l) {
        return getItem(c, l.getX(), l.getY());
    }

    private void setItem(Camada c, int x, int y, ObjetoSimulacao v) {
        getCamada(c)[x][y] = v;
    }

    private void setItem(Camada c, Localizacao l, ObjetoSimulacao v) {
        setItem(c, l.getX(), l.getY(), v);
    }

    public List<ObjetoSimulacao> getItens() {
        List<ObjetoSimulacao> objetosValidos = new ArrayList<>();
        
        for (Camada c : Camada.TODAS) {
            for (int y = getAltura() - 1; y >= 0; y--) {
                for (int x = getLargura() - 1; x >= 0; x--) {
                    ObjetoSimulacao objeto = getItem(c, x, y);
                    if (objeto != null) {
                        objetosValidos.add(objeto);
                    }
                }
            }
        }

        return Collections.unmodifiableList(objetosValidos);
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

}
