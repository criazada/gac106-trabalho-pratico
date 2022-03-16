package simulacao;
import java.util.Random;
/**
 * Responsavel pela simulacao.
 * @author David J. Barnes and Michael Kolling and Luiz Merschmann
 */
public class Simulacao {
    private JanelaSimulacao janelaSimulacao;
    private Mapa mapa;
    
    public Simulacao() {
        Random rand = new Random(12346);
        mapa = new Mapa();
        int largura = mapa.getLargura();
        int altura = mapa.getAltura();

        // For para ver como fica com mais veiculos (debug)
        for (int i = 0; i < 5; i++) {
            // Cria um veiculo em uma posicao aleatoria
            Veiculo veiculo = new Veiculo(new Localizacao(rand.nextInt(largura),rand.nextInt(altura)), mapa);
            // Define a posicao destino aleatoriamente
            veiculo.setLocalizacaoDestino(new Localizacao(rand.nextInt(largura),rand.nextInt(altura)));
            mapa.adicionarItem(veiculo);
        }
        // Inicializando o mapa com o veículo
        janelaSimulacao = new JanelaSimulacao(mapa);
    }
    
    public void executarSimulacao(int numPassos){
        janelaSimulacao.executarAcao();
        for (int i = 0; i < numPassos; i++) {
            executarUmPasso();
            esperar(100);
        }        
    }

    private void executarUmPasso() {
        for (Veiculo v : mapa.getVeiculos()) {
            v.executarAcao();
        }

        janelaSimulacao.executarAcao();
    }
    
    private void esperar(int milisegundos){
        try{
            Thread.sleep(milisegundos);
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
    
}
