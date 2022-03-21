package simulacao;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;

/**
 * Representa os veiculos da simulacao.
 * 
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Veiculo {
    private Localizacao localizacaoAtual;
    private Localizacao localizacaoDestino;
    private Image imagem;
    private Mapa mapa;

    private static Random rand = new Random();

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

    public Image getImagem() {
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

    public void executarAcao() {
        Localizacao destino = getLocalizacaoDestino();
        
        if(destino != null){
            Localizacao localizacaoAtual = getLocalizacaoAtual();
                
            if(destino.equals(localizacaoAtual)){
                setLocalizacaoAtual(destino);
         
            }else{
         
                int x = localizacaoAtual.getX();
                int y = localizacaoAtual.getY();
                
                int destX = destino.getX();
                int destY = destino.getY();
                int deslocX = x < destX ? 1 : x > destX ? -1 : 0;//Deslocamento de 1 ou 0 ou -1 posição em x
                int deslocY = y < destY ? 1 : y > destY ? -1 : 0;//Deslocamento de 1 ou 0 ou -1 posição em y

                Localizacao proximaLocalizacao;
                if(deslocX != 0 && deslocY != 0){//Se nenhuma coordenada coincide com a localizacao destino
                    if(rand.nextInt(2) == 0){//Atualizar x
                        proximaLocalizacao = new Localizacao(x + deslocX, y);
                    }else{//Atualizar y
                        proximaLocalizacao = new Localizacao(x, y + deslocY);
                    }
                }else{
                    if(deslocX != 0) proximaLocalizacao = new Localizacao(x + deslocX, y);
                    else proximaLocalizacao = new Localizacao(x, y + deslocY);
                }
                
                if (mapa.getItem(proximaLocalizacao) == null) {
                    setLocalizacaoAtual(proximaLocalizacao);
                }
            }        
        }           
    }

    
}
