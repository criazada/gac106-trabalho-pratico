package simulacao;

/**
 * Representa uma localização no mapa
 *
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Localizacao {
    private int x;
    private int y;

    /**
     * Representa uma localização na cidade
     *
     * @param x Coordenada x: deve ser maior ou igual a 0.
     * @param y Coordenada y: deve ser maior ou igual a 0.
     */
    public Localizacao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return Coordenada x da localização.
     */
    public int getX() {
        return x;
    }
    /**
     * @return Coordenada y da localização.
     */
    public int getY() {
        return y;
    }
    
    /**
     * Calcula a distancia de Manhattan entre duas localizações
     * @param outra Localização a ser calculada a distancia
     * @return A distancia entre as duas localizações
     */
    public int distanciaManhattan(Localizacao outra) {
        int dx = Math.abs(outra.getX() - x);
        int dy = Math.abs(outra.getY() - y);
        return dx + dy;
    }

    /**
     * Verificacao de igualdade de conteudo de objetos do tipo Localizacao.
     *
     * @return true: se a localizacao é igual.
     *         false: caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Localizacao)) {
            return false;
        } else {
            Localizacao outro = (Localizacao) obj;
            return x == outro.x && y == outro.y;
        }
    }

    /**
     * @return A representacao da localizacao.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
