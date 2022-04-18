package simulacao;

public class Estatisticas {
    private int passageiros;
    private int paradasOnibus;
    private int distanciaPercorridaOnibus;
    private int tempoPedestreNoPonto;
    private int tempoPedestreNoOnibus;
    private int tempoPedestreAndandoParaPonto;
    private int tempoOnibusNoPonto;

    public void entrouNoOnibus() {
        passageiros++;
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
        passageiros++;
    }

    public void onibusParado() {
        tempoOnibusNoPonto++;
    }

    public void pedestreNoOnibus() {
        tempoPedestreNoOnibus++;
    }
}
