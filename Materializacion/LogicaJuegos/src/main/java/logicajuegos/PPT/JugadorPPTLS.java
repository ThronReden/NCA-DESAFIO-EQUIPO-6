package logicajuegos.PPT;

import java.util.concurrent.CompletableFuture;
import utilidades.Teclado;

/**
 *
 * @author jsanchez
 */
public class JugadorPPTLS extends JugadorPPT {
    
    public JugadorPPTLS(String nombre) {
        super(nombre);
    }
    
    @Override
    public void pedirJugada(PartidaPPT p, CompletableFuture<JugadaPPT> jugadaPedida) {
        int gesto;
        do{
            gesto = Teclado.leerEntero(getNombre() + " elige un gesto:"+"\n"
                +"0 -> PIEDRA"+"\n"+"1 -> PAPEL"+"\n"+"2 -> TIJERA"+"\n"
                    +"3 -> LAGARTO"+"\n"+"4 -> SPOCK"+"\n"+":");
        } while(gesto < 0 || gesto > 4);
        
        jugadaPedida.complete(JugadaPPTLS.crearJugada(p.getTurno(),gesto,this));
    }
}
