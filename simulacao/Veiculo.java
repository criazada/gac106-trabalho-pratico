package simulacao;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Representa os veiculos da simulacao.
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Veiculo {
    private Localizacao localizacaoAtual;
    private Localizacao localizacaoDestino;
    private Image imagem;
    private Mapa mapa;

    public Veiculo(Localizacao localizacao, Mapa mapa) {
        this.localizacaoAtual = localizacao;
        this.mapa = mapa;
        localizacaoDestino = null;
        imagem = new ImageIcon(getClass().getResource("Imagens/veiculo.jpg")).getImage();
    }

    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public Localizacao getLocalizacaoDestino() {
        return localizacaoDestino;
    }

    public Image getImagem(){
        return imagem;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        Localizacao anterior = this.localizacaoAtual;
        this.localizacaoAtual = localizacaoAtual;
        mapa.atualizarMapa(this, anterior);
    }

    public void setLocalizacaoDestino(Localizacao localizacaoDestino) {
        this.localizacaoDestino = localizacaoDestino;
    }

    public void executarAcao(){
        Localizacao destino = getLocalizacaoDestino();
        if(destino != null){
            Localizacao proximaLocalizacao = getLocalizacaoAtual().proximaLocalizacao(localizacaoDestino);
            // carro só anda se o espaço de destino está livre
            if (mapa.getItem(proximaLocalizacao) == null) {
                setLocalizacaoAtual(proximaLocalizacao);
            }
        }
    }
}
