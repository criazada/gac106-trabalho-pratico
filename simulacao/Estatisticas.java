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

    public void entrouNoOnibus() {
        passageiros++;
        totalPassageiros++;
    }

    public void onibusParou() {
        paradasOnibus++;
    }

    public void onibusAndou() {
        distanciaPercorridaOnibus++;
    }

    public void pedestreEsperou() {
        tempoPedestreNoPonto++;
    }

    public void pedestreAndou() {
        tempoPedestreAndandoParaPonto++;
    }

    public void pedestreEntrouNoOnibus() {
        System.out.println("Passageiro entrou no onibus");
        passageiros++;
    }

    public void pedestreSaiuDoOnibus() {
        System.out.println("Passageiro saiu do onibus");
        passageiros--;
    }

    public void onibusParado() {
        tempoOnibusNoPonto++;
    }

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
