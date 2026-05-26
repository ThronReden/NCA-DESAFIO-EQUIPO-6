package pantallas.juegos;

import java.util.concurrent.CompletableFuture;
import logicajuegos.PPT.JugadaPPT;
import logicajuegos.PPT.JugadorPPT;
import logicajuegos.PPT.PartidaPPT;
import utilidades.Teclado;

/**
 *
 * @author jsanchez
 */
class JugadorPPTUI extends JugadorPPT {
    
    public JugadorPPTUI(String nombre) {
        super(nombre);
    }
    
    public void pedirJugada(PartidaPPT p, CompletableFuture<JugadaPPT> jugadaPedida) {
        ((PartidaPPTUI)p).Pantalla.pedirJugada(jugadaPedida);
        int gesto;
        do{
            gesto = Teclado.leerEntero(getNombre() + " elige un gesto:"+"\n"
                +"0 -> PIEDRA"+"\n"+"1 -> PAPEL"+"\n"+"2 -> TIJERA"+"\n"+":");
        } while(gesto < 0 || gesto > 2);
        
        jugadaPedida.complete(JugadaPPT.crearJugada(p.getTurno(),gesto,this));
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
