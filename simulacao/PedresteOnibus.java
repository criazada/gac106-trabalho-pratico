package simulacao;

import java.util.Random;

public class PedresteOnibus extends PedestreAmbulante {
    private Localizacao destino ;
    private enum Estado {
        AntesOnibus,
        Onibus,
        AposOnibus,
    } 
    Estado estado;
    private int idBus;
    public PedresteOnibus(Localizacao localizacao, Localizacao destino, Mapa mapa, Random rng) {
        super( localizacao, PontoOnibus.pontoMaisProximoCalcada(localizacao), mapa, rng);
        this.destino = destino;
        this.estado = Estado.AntesOnibus;
    }
    @Override
    public void estaNoDestino(){
        System.out.println("entrou" );
        if (estado == Estado.AntesOnibus) {
            int id = PontoOnibus.onibusEstaNoPonto(PontoOnibus.pontoMaisProximoRua(getLocalizacao()));
            System.out.println("id: " + id); 
            if (PontoOnibus.pedestreEstaNoPonto(getLocalizacao()) && 
             id != -1) {
                getMapa().removerObjeto(this);
                this.idBus = id;
                estado = Estado.Onibus; 
            }  
        }
        else if ( estado == Estado.Onibus) {
            //se esta no desitno e onibus ta no ponto 
           System.out.println("pos id" + Onibus.posicoesOnibus.get(idBus)); 
           System.out.println("pos a chegar" + PontoOnibus.pontoMaisProximoRua(destino)); 

        }
    }
    

}
