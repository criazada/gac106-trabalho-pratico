package simulacao;

import java.util.*;

/**
 * Representa um mapa com todos os itens que participam da simulacao
 * 
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Mapa {
    private ObjetoSimulacao[][] itens;
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
        itens = new ObjetoSimulacao[altura][largura];
    }

    /**
     * Cria mapa com tamanho padrao.
     */
    public Mapa() {
        this(LARGURA_PADRAO, ALTURA_PADRAO);
    }

    public void adicionarItem(ObjetoSimulacao v) {
        setItem(v.getLocalizacao(), v);
    }

    public void removerItem(ObjetoSimulacao v) {
        setItem(v.getLocalizacao(), null);
    }

    public void atualizarMapa(ObjetoSimulacao v, Localizacao anterior) {
        if (getItem(anterior) != v) {
            System.out.printf("Veículo %s tentou atualizar posição que não é sua%n", v);
        }
        setItem(anterior, null);
        setItem(v.getLocalizacao(), v);
    }

    public ObjetoSimulacao getItem(int x, int y) {
        return itens[x][y];
    }

    public ObjetoSimulacao getItem(Localizacao l) {
        return getItem(l.getX(), l.getY());
    }

    private void setItem(int x, int y, ObjetoSimulacao v) {
        itens[x][y] = v;
    }

    private void setItem(Localizacao l, ObjetoSimulacao v) {
        setItem(l.getX(), l.getY(), v);
    }

    public List<ObjetoSimulacao> getItens() {
        List<ObjetoSimulacao> itensValidos = new ArrayList<>();

        for (int y = getAltura() - 1; y >= 0; y--) {
            for (int x = getLargura() - 1; x >= 0; x--) {
                ObjetoSimulacao item = getItem(x, y);
                if (item != null) {
                    itensValidos.add(item);
                }
            }
        }

        return itensValidos;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

}
