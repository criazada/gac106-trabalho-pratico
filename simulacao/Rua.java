package simulacao;

public class Rua extends ObjetoSimulacao {
    public Rua(Direcao direcao, Localizacao localizacao, Mapa mapa) {
        super(Recurso.CARRO.getImagem(), localizacao, mapa, Mapa.Camada.BACKGROUND, null);
        setDirecao(direcao);
    }

    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        Direcao desejada = Direcao.calcular(o.getLocalizacao(), getLocalizacao());
        return calcularDirecao().oposta() != desejada;
    }

    @Override
    public void executarAcao() { }
}
