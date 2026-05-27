package pantallas.juegos;

import java.util.concurrent.CompletableFuture;
import logicajuegos.PPT.JugadaPPT;
import logicajuegos.PPT.JugadorPPT;
import logicajuegos.PPT.PartidaPPT;

/**
 *
 * @author jsanchez
 */
class JugadorPPTUI extends JugadorPPT {
    
    public JugadorPPTUI(String nombre) {
        super(nombre);
    }
    
    @Override
    public void pedirJugada(PartidaPPT p, CompletableFuture<JugadaPPT> jugadaPedida) {
        ((PartidaPPTUI)p).Pantalla.pedirJugada(jugadaPedida);
    }
    
    protected void hacerJugada(int gesto, PartidaPPT p, CompletableFuture<JugadaPPT> jugadaPedida){
        JugadaPPT jugada = null;
        try{
            jugada = JugadaPPT.crearJugada(p.getTurno(),gesto,this);
        } catch (IllegalArgumentException IAEx){
            throw new IllegalArgumentException("¡Gesto no valido!");
        }
        if(jugada != null){
            jugadaPedida.complete(jugada);
        }
    }
}
