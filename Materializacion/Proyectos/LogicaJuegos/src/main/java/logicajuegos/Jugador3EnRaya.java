package logicajuegos;

/**
 *
 * @author jsanchez
 */
public class Jugador3EnRaya extends Jugador{
    
    EstadoTablero estadoTablero;
    
    public void recibirEstadoTablero(EstadoTablero estadoTablero){
        this.estadoTablero = estadoTablero;
    }
    
    public void hacerJugada(int turno, Partida3EnRaya p){
        Jugada3EnRaya jugada = new Jugada3EnRaya(turno,3,this);
        p.recibirJugada(jugada);
    }
}
