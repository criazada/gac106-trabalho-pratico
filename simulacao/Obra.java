package simulacao;

import java.util.Random;

public class Obra extends ObjetoSimulacao{
    
    private int contador;
    
    public Obra(Localizacao localizacao, Mapa mapa, Random rng){
        super(Recurso.OBRA.getImagem(), localizacao, mapa, Mapa.Camada.FOREGROUND, rng);
        this.contador = rng.nextInt(100);
    }

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