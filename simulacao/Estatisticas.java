package simulacao;

public class Estatisticas {
    private int passageiros;
    private int paradasOnibus;
    private int distanciaPercorridaOnibus;
    private int tempoPedestreNoPonto;
    private int tempoPedestreNoOnibus;
    private int tempoPedestreAndandoParaPonto;
    private int tempoOnibusNoPonto;
    private int totalPassageiros;

    /**
     * Incrementa a estatitica de quantas vezes o onibus parou.
     */
    public void onibusParou() {
        paradasOnibus++;
    }
    /**
     * Incrementa a estatitica de quantas unidades o onibus percorreu.
     */
    public void onibusAndou() {
        distanciaPercorridaOnibus++;
    }
    /**
     * Incrementa a estatitica de quantas unidades o pedestre espera no ponto.
     */
    public void pedestreEsperou() {
        tempoPedestreNoPonto++;
    }
    /**
     * Incrementa a estatitica de quantas unidades o pedestre andou para ir para o ponto.
     */
    public void pedestreAndou() {
        tempoPedestreAndandoParaPonto++;
    }

    /**
     * Incrementa a estatitica de passageiros.
     */
    public void pedestreEntrouNoOnibus() {
        System.out.println("Passageiro entrou no onibus");
        passageiros++;
    }
     /**
     * Incrementa a estatitica de passageiros que sairam do onibus
     */
    public void pedestreSaiuDoOnibus() {
        System.out.println("Passageiro saiu do onibus");
        passageiros--;
    }
    /**
     * Incrementa a estatitica de tempo que o onibus ficou parado no ponto
     */
    public void onibusParado() {
        tempoOnibusNoPonto++;
    }
    /**
     * Incrementa a estatitica de tempo que o pedestre ficou dentro do onibus
     */
    public void pedestreNoOnibus() {
        tempoPedestreNoOnibus++;
    }

    public void exibir() {
        System.out.println();
        System.out.printf("Passageiros ainda nos onibus: %d%n", passageiros);
        System.out.printf("Passageiros transportados no total: %d%n", totalPassageiros);
        System.out.printf("Vezes que onibus pararam: %d%n", paradasOnibus);
        System.out.printf("Tempo gasto por onibus no ponto: %d%n", tempoOnibusNoPonto);
        System.out.printf("Distancia que onibus percorreram: %d%n", distanciaPercorridaOnibus);
        System.out.printf("Tempo total de passageiros esperando no ponto: %d%n", tempoPedestreNoPonto);
        System.out.printf("Tempo total de passageiros andando para ponto: %d%n", tempoPedestreAndandoParaPonto);
        System.out.printf("Tempo total de passageiros dentro de onibus: %d%n", tempoPedestreNoOnibus);
    }
}
