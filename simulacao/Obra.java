package simulacao;

import java.util.Random;

public class Obra extends ObjetoSimulacao{
    
    private int contador;
    /**
     * Construtor para objetos da classe Obra
     * @param localizacao onde a obra ira ser colocada.
     * @param mapa uma instancia do mapa da simulação.
     * @param rng uma instancia de Random para gerar numeros aleatorios a partir da seed definida.
    */
    public Obra(Localizacao localizacao, Mapa mapa, Random rng){
        super(Recurso.OBRA.getImagem(), localizacao, mapa, Mapa.Camada.FOREGROUND, Mapa.PontoDeInteresse.OBRA, rng);
        this.contador = rng.nextInt(100);
    }

    /**
     * Decresce o contador de tempo de obra.
     * quando chega a zero a obra é destruida.
    */
    @Override
    public void executarAcao(){
        contador--;

        if(contador == 0){
            getMapa().removerObjeto(this);
        }
    }

    
    @Override
    public boolean transparentePara(ObjetoSimulacao o) {
        return o instanceof FantasmaPedestre;
    }



}