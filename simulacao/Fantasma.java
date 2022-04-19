package simulacao;

/**
 * Objeto usado para gerar o grafo de alcacabilidade 
 */
public class Fantasma extends ObjetoSimulacao {
    /**
     * Constroi o fantasma.
     * @param localizacao A localização do fantasma.
     */
    public Fantasma() {
        super();
    }
    /**
     * Como Fantasma nao tem movimento sua ação é nula.
     */
    @Override
    public void executarAcao() { }
    /**
     * Verifica se este objeto é transparente para outro objeto.
     * @param o O outro objeto
     * @return Transparência deste objeto na visão de outro objeto.
     */
    @Override
    public boolean transparentePara(ObjetoSimulacao o) { return true; }
}
