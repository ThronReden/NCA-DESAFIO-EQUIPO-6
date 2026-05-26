package logicajuegos.PPT;

import java.util.concurrent.CompletableFuture;
import logicajuegos.Jugador;
import utilidades.Teclado;

/**
 *
 * @author jsanchez
 */
class JugadorPPT extends Jugador {
    
    public JugadorPPT(String nombre) {
        super(nombre);
    }
    
    public void pedirJugada(PartidaPPT p, CompletableFuture<JugadaPPT> jugadaPedida) {
        int gesto;
        do{
            gesto = Teclado.leerEntero(getNombre() + " elige un gesto:"+"\n"
                +"0 -> PIEDRA"+"\n"+"1 -> PAPEL"+"\n"+"2 -> TIJERA"+"\n"+":");
        } while(gesto < 0 || gesto > 2);
        
        jugadaPedida.complete(JugadaPPT.crearJugada(p.getTurno(),gesto,this));
    }
    
}
