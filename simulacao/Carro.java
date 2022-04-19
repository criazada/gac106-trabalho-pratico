package simulacao;

import java.util.Random;
import java.awt.Image;

public class Carro extends Veiculo {
    /**
     * Constroi um veiculo.
     * @param imagem A imagem do veiculo.
     * @param localizacao A localizacao inicial do veiculo.
     * @param mapa Uma instancia do mapa.
     * @param rand Uma intancia do gerador de numeros aleatorios com a seed inicializada.
     */
    public Carro(Localizacao localizacao, Mapa mapa, Random rng) {
        super(selecionarImagem(rng.nextInt(3)), localizacao, mapa, rng);
    }
    /**
     * Seleciona uma imagem aleatoria para o veiculo.
     * @param numero O numero aleatorio de 0 a 2 represetando a imagem.
     * @return A imagem gerada.
     */
    private static Image selecionarImagem(int rng) {
        switch(rng) {
            case 0:
                return Recurso.CARRO_1.getImagem();   
            case 1:
                return Recurso.CARRO_2.getImagem();               
            case 2:
                return Recurso.CARRO_3.getImagem();
            default:
                return Recurso.CARRO_1.getImagem();  
        }
    }
}
