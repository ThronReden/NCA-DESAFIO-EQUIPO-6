package pantallas.juegos;

import logicajuegos.TresEnRaya.Jugada3EnRaya;
import logicajuegos.TresEnRaya.Jugador3EnRaya;
import logicajuegos.TresEnRaya.Partida3EnRaya;

/**
 *
 * @author jsanchez
 */
public class Jugador3EnRayaUI extends Jugador3EnRaya {
    
    public Jugador3EnRayaUI(String nombre) {
        super(nombre);
    }
    
    
    @Override
    public void pedirJugada(Partida3EnRaya p){
        ((Partida3EnRayaUI)p).Pantalla.mostrarEstadoTablero();
    }
    
    @Override
    protected void hacerJugada(int pos, Partida3EnRaya p){
        Jugada3EnRaya jugada = new Jugada3EnRaya(p.getTurno(),pos,this);
        try{
            p.recibirJugada(jugada);
        } catch (IllegalArgumentException IAEx){
            ((Partida3EnRayaUI)p).Pantalla.mostrarError(IAEx.getMessage());
        }
    }
}
