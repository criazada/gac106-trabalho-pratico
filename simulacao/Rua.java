package simulacao;

public class Rua extends ObjetoSimulacao {
    private boolean cruzamento;

    public Rua(Direcao direcao, boolean cruzamento, Localizacao localizacao, Mapa mapa) {
        super(Recurso.CARRO.getImagem(), localizacao, mapa, Mapa.Camada.BACKGROUND, null);
        setDirecao(direcao);
        this.cruzamento = cruzamento;
    }

    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        Direcao desejada = Direcao.calcular(o.getLocalizacao(), getLocalizacao());
        if (cruzamento) {
            return calcularDirecao().oposta() != desejada;
        } else {
            return calcularDirecao() == desejada;
        }
    }

    @Override
    public void executarAcao() { }
}
