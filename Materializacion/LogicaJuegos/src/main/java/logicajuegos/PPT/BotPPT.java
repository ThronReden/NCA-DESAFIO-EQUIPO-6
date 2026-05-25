package logicajuegos.PPT;

//!!!!!!!!!!!!!!!!!!!!!!!!! ARREGLAR:
import java.util.Random;
import logicajuegos.TresEnRaya.Bot3EnRaya;

/**
 *
 * @author jsanchez
 */
class BotPPT extends JugadorPPT{
    
    private BotPPT(String nombre) {
        super(nombre);
    }
    
    public static BotPPT crearBot(){
        return new BotPPT(Bot3EnRaya.genNombreBot());
    }
    
    @Override
    public JugadaPPT pedirJugada(PartidaPPT p) {
        return JugadaPPT.crearJugada(p.getTurno(),gestoAleatorio(),this);
    }

    private int gestoAleatorio() {
        Random r = new Random();
        return r.nextInt(3);
    }
}
