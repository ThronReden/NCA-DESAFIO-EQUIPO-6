package logicajuegos.PPT;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import logicajuegos.TresEnRaya.Bot3EnRaya;

/**
 *
 * @author jsanchez
 */
public class BotPPTLS extends JugadorPPTLS {
    
    private BotPPTLS(String nombre) {
        super(nombre);
    }
    
    public static BotPPTLS crearBot(){
        return new BotPPTLS(Bot3EnRaya.genNombreBot());
    }
    
    @Override
    public void pedirJugada(PartidaPPT p, CompletableFuture<JugadaPPT> jugadaPedida) {
        jugadaPedida.complete(JugadaPPTLS.crearJugada(p.getTurno(),gestoAleatorio(),this));
    }
    
    private int gestoAleatorio() {
        Random r = new Random();
        return r.nextInt(5);
    }
}
