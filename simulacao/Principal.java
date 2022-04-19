package simulacao;

/**
 *
 * @author Luiz Merschmann
 */
public class Principal {

    public static void main(String[] args) {
        Simulacao sim = new Simulacao();
        Estatisticas es = sim.executarSimulacao(1000);
        es.exibir();
    }
}
