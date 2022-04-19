package simulacao;

import java.awt.Image;
import java.util.Random;

/**
 * Representa  qualquer objeto da simulacao.
 */
public abstract class ObjetoSimulacao {
    // Imagem que representa o objeto no mapa
    private Image[] imagens;
    // Localização atual do objeto no mapa
    private Localizacao locAtual;
    // Localização anterior do objeto no mapa
    private Localizacao locAnterior;
    // Visão do mapa
    private Mapa mapa;
    // Camada do objeto no mapa
    private Mapa.Camada camada;
    private Mapa.PontoDeInteresse pdi;
    // RNG
    private Random rng;

    /**
     * Classe abstrata que constrói um objeto a ser simulado,e o adicona no mapa.
     *
     * @param imagens Vetor de imagens que representam as camadas do objeto.
     * @param localização Localização inicial do objeto.
     * @param mapa A instancia do mapa da simução.
     * @param camada Camada do objeto no mapa.
     * @param tipo Ponto de interesse do objeto.
     * @param rng Uma intancia do gerador de numeros aleatorios com a seed inicializada.
     */
    public ObjetoSimulacao(Image[] imagens, Localizacao localizacao, Mapa mapa, Mapa.Camada camada, Mapa.PontoDeInteresse tipo, Random rng) {
        this.imagens = imagens;
        this.locAtual = localizacao;
        this.locAnterior = localizacao;
        this.mapa = mapa;
        this.camada = camada;
        this.rng = rng;
        this.pdi = tipo;
        if (mapa != null) {
            mapa.adicionarObjeto(this);
            if (tipo != null) {
                mapa.addPontoDeInteresse(tipo, this);
            }
        }
    }
    /**
     * Classe abstrata que constrói um objeto a ser simulado.
     *
     * @param imagens Imagens que representam as camadas do objeto.
     * @param localização Localização inicial do objeto.
     * @param mapa A instancia do mapa da simução.
     * @param camada Camada do objeto no mapa.
     * @param tipo Ponto de interesse do objeto.
     * @param rng Uma intancia do gerador de numeros aleatorios com a seed inicializada.
     */
    public ObjetoSimulacao(Image imagem, Localizacao localizacao, Mapa mapa, Mapa.Camada camada, Mapa.PontoDeInteresse tipo, Random rng) {
        this(new Image[]{imagem}, localizacao, mapa, camada, tipo, rng);
    }

    public ObjetoSimulacao() {}

    /**
     * Get na camada atual do objeto no mapa.
     * @return A camada que o objeto esta no mapa.
     */
    public Mapa.Camada getCamada() {
        return camada;
    }

    /**
     * Get nas imagens do objeto.
     * @return Vetor de imagens do objeto.
     */
    public Image[] getImagens() {
        return imagens;
    }

    /**
     * Get na instancia de Estatisticas da simulção.
     * @return A instancia de Estatisticas da simulção.
     */
    public Estatisticas getEstatisticas() {
        return mapa.getEstatisticas();
    }

    /**
     * Seta a nova imagem do objeto.
     * @param imagem A nova imagem do objeto.
     */
    public void setImagem(Image imagem) {
        this.imagens = new Image[]{imagem};
    }   
    /**
     * Seta as novas imagens do objeto.
     * @param imagem As novas imagem do objeto.
     */
    public void setImagens(Image[] imagens) {
        this.imagens = imagens;
    }
    /**
     * Get na localização atual do objeto no mapa.
     * @return A localização atual do objeto.
     */
    public Localizacao getLocalizacao() {
        return locAtual;
    }
    /**
     * Get na localização anterior do objeto no mapa.
     * @return A localização anterior do objeto.
     */
    public Localizacao getLocalizacaoAnterior() {
        return locAnterior;
    }
    /**
     * Get na instancia do mapa da simução.
     * @return A instancia do mapa da simução.
     */
    public Mapa getMapa() {
        return mapa;
    }
    /**
     * Get na instacia do objeto Ramdom com a seed inicial.
     * @return A instancia do objeto Ramdom.
     */
    public Random getRng() {
        return rng;
    }
    /**
     * Get o ponto de interesse do objeto.
     * @return O ponto de interesse do objeto.
     */
    public Mapa.PontoDeInteresse getTipoPontoDeInteresse() {
        return pdi;
    }

    /**
     * Atualiza a localização do objeto, salvando a posição atual em sua posição
     * anterior.
     * @param localizacao Nova localização
     */
    public void setLocalizacao(Localizacao localizacao) {
        locAnterior = locAtual;
        locAtual = localizacao;
        if (mapa != null) {
            mapa.atualizarMapa(this);
        }
    }

    /**
     * Executa a ação do objeto em um passo da simulação
     */
    public abstract void executarAcao();
    
    /**
     * Verifica se este objeto é transparente para outro objeto.
     * Útil caso somente alguns tipos de objetos possam passar por este.
     * Mais relevante para objetos no background.
     * @param o O outro objeto
     * @return Transparência deste objeto na visão de outro objeto.
     */
    public abstract boolean transparentePara(ObjetoSimulacao o);
}
