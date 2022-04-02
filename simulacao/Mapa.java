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

    /**
     * Adiciona um objeto no mapa.
     *
     * @param o Objeto a ser adicionado
     */
    public void adicionarObjeto(ObjetoSimulacao o) {
        setObjeto(o.getCamada(), o.getLocalizacao(), o);
    }

    /**
     * Remove um objeto do mapa.
     * @param o Objeto a ser removido
     */
    public void removerObjeto(ObjetoSimulacao o) {
        setObjeto(o.getCamada(), o.getLocalizacao(), null);
    }

    /**
     * Atualiza a posição de um objeto no mapa.
     * @param o Objeto a ser atualizado
     */
    public void atualizarMapa(ObjetoSimulacao o) {
        Camada c = o.getCamada();
        Localizacao anterior = o.getLocalizacaoAnterior();
        if (getObjeto(c, anterior) != o) {
            System.out.printf("atualizarMapa: ObjetoSimulacao %s tentou atualizar posição que não é sua%n", o);
        }
        setObjeto(c, anterior, null);
        setObjeto(c, o.getLocalizacao(), o);
    }

    /**
     * Retorna a matriz de objetos de uma camada
     * @param c Camada
     * @return matriz de objetos da camada
     */
    private ObjetoSimulacao[][] getCamada(Camada c) {
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

    /**
     * Retorna o objeto em uma posição (x, y) em uma camada do mapa.
     * @param c Camada
     * @param x Posição X do objeto
     * @param y Posição Y do objeto
     * @return Objeto em (x, y) na camada
     */
    public ObjetoSimulacao getObjeto(Camada c, int x, int y) {
        return getCamada(c)[x][y];
    }

    /**
     * Retorna o objeto em uma localização em uma camada do mapa.
     * @param c Camada
     * @param l Localização do objeto
     * @return Objeto na localização dada na camada
     */
    public ObjetoSimulacao getObjeto(Camada c, Localizacao l) {
        return getObjeto(c, l.getX(), l.getY());
    }

    /**
     * Retorna o objeto em uma localização no foreground do mapa.
     * @param l Localização do objeto
     * @return Objeto na localização dada no foreground
     */
    public ObjetoSimulacao getObjetoForeground(Localizacao l) {
        return getObjeto(Camada.FOREGROUND, l);
    }

    /**
     * Retorna o objeto em uma localização no background do mapa.
     * @param l Localização do objeto
     * @return Objeto na localização dada no background
     */
    public ObjetoSimulacao getObjetoBackground(Localizacao l) {
        return getObjeto(Camada.BACKGROUND, l);
    }

    /**
     * Retorna os objetos em ambas camadas do mapa em uma localização.
     * @param l Localização dos objetos
     * @return Os objetos na localização
     */
    public List<ObjetoSimulacao> getObjetosEm(Localizacao l) {
        List<ObjetoSimulacao> objetos = new ArrayList<>();
        for (Camada c : Camada.TODAS) {
            ObjetoSimulacao o = getObjeto(c, l);
            if (o != null) {
                objetos.add(o);
            }
        }
        return objetos;
    }

    /**
     * Posiciona o objeto o em (x, y) na camada
     * @param c Camada
     * @param x Posição X
     * @param y Posição Y
     * @param o Objeto a ser posicionado
     */
    private void setObjeto(Camada c, int x, int y, ObjetoSimulacao o) {
        getCamada(c)[x][y] = o;
    }

    /**
     * Posiciona o objeto o na localização na camada
     * @param c Camada
     * @param l Localização
     * @param o Objeto a ser posicionado
     */
    private void setObjeto(Camada c, Localizacao l, ObjetoSimulacao o) {
        setObjeto(c, l.getX(), l.getY(), o);
    }

    /**
     * Captura a lista de objetos válidos em todas as camadas do mapa. Útil para
     * impedir que um mesmo objeto que se move seja atualizado mais de uma vez
     * em uma iteração da simulação.
     * @return Lista imutável de todos os objetos válidos da simulação.
     */
    public List<ObjetoSimulacao> getObjetos() {
        List<ObjetoSimulacao> objetosValidos = new ArrayList<>();

        for (Camada c : Camada.TODAS) {
            for (int y = getAltura() - 1; y >= 0; y--) {
                for (int x = getLargura() - 1; x >= 0; x--) {
                    ObjetoSimulacao objeto = getObjeto(c, x, y);
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
