package simulacao;

import java.util.*;

/**
 * Representa um mapa com todos os itens que participam da simulacao
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Mapa {
    private Veiculo[][] ladrilhos;
    private int largura;
    private int altura;
    private Set<Veiculo> veiculos;

    private static final int LARGURA_PADRAO = 35;
    private static final int ALTURA_PADRAO = 35;
    
    /**
     * Cria mapa para alocar itens da simulacao.
     * @param largura: largura da área de simulacao.
     * @param altura: altura da área de simulação.
     */
    public Mapa(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        ladrilhos = new Veiculo[altura][largura];
        veiculos = new HashSet<>();
    }
    /**
     * Cria mapa com tamanho padrao.
     */
    public Mapa(){
        this(LARGURA_PADRAO,ALTURA_PADRAO);
    }
    
    public void adicionarItem(Veiculo v){
        veiculos.add(v);
    }
    
    public void removerItem(Veiculo v){
        veiculos.remove(v);
    }
    
    public void atualizarMapa(Veiculo v, Localizacao anterior){
        if (veiculos.contains(v)) {
            ladrilhos[anterior.getX()][anterior.getY()] = null;
        }
        Localizacao l = v.getLocalizacaoAtual();
        ladrilhos[l.getX()][l.getY()] = v;
    }

    public Veiculo getItem(int x, int y){
        return ladrilhos[x][y];
    }

    public Veiculo getItem(Localizacao l) {
        return ladrilhos[l.getX()][l.getY()];
    }

    public Iterable<Veiculo> getVeiculos() {
        return new Iterable<Veiculo>() {
            @Override
            public Iterator<Veiculo> iterator() {
                return veiculos.iterator();
            }
        };
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }
    
}
