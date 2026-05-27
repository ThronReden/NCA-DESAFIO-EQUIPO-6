package pantallas.juegos;

import java.util.concurrent.CompletableFuture;
import logicajuegos.PPT.JugadaPPT;
import logicajuegos.PPT.JugadaPPTLS;
import logicajuegos.PPT.JugadorPPTLS;
import logicajuegos.PPT.PartidaPPT;
import logicajuegos.PPT.PartidaPPTLS;

/**
 *
 * @author jsanchez
 */
class JugadorPPTLSUI extends JugadorPPTLS {
    
    public JugadorPPTLSUI(String nombre) {
        super(nombre);
    }
    
    @Override
    public void pedirJugada(PartidaPPT p, CompletableFuture<JugadaPPT> jugadaPedida) {
        ((PartidaPPTLSUI)p).Pantalla.pedirJugada(jugadaPedida);
    }
    
    protected void hacerJugada(int gesto, PartidaPPTLS p, CompletableFuture<JugadaPPT> jugadaPedida){
        JugadaPPTLS jugada = null;
        try{
            jugada = JugadaPPTLS.crearJugada(p.getTurno(),gesto,this);
        } catch (IllegalArgumentException IAEx){
            throw new IllegalArgumentException("¡Gesto no valido!");
        }
        if(jugada != null){
            jugadaPedida.complete(jugada);
        }
    }
}
