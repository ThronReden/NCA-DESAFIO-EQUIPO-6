package logicajuegos.PPT;

//!!!!!!!!!!!!!!!!!!!!!!!!! ARREGLAR:
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import logicajuegos.TresEnRaya.Bot3EnRaya;

/**
 *
 * @author jsanchez
 */
public class BotPPT extends JugadorPPT{
    
    protected BotPPT(String nombre) {
        super(nombre);
    }
    
    public static BotPPT crearBot(){
        return new BotPPT(Bot3EnRaya.genNombreBot());
    }
    
    @Override
    public void pedirJugada(PartidaPPT p, CompletableFuture<JugadaPPT> jugadaPedida) {
        jugadaPedida.complete(JugadaPPT.crearJugada(p.getTurno(),gestoAleatorio(),this));
    }

    private int gestoAleatorio() {
        Random r = new Random();
        return r.nextInt(3);
    }
}
