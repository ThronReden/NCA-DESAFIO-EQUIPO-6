package logicajuegos.TresEnRaya;

import logicajuegos.Jugador;
import utilidades.Teclado;

/**
 *
 * @author jsanchez
 */
public class Jugador3EnRaya extends Jugador{
    
    private EstadoTablero estadoTablero;

    public Jugador3EnRaya(String nombre) {
        super(nombre);
    }

    public Jugador3EnRaya(Jugador j) {
        super(j.getNombre());
    }

    protected EstadoTablero getEstadoTablero() {
        return this.estadoTablero;
    }
    
    public void recibirEstadoTablero(EstadoTablero estadoTablero){
        this.estadoTablero = estadoTablero;
    }
    
    //TERMINAL
    public void pedirJugada(Partida3EnRaya p){
        boolean bueno;
        do {
            bueno = false;
            getEstadoTablero().mostrarEstadoTableroTerminal();
            try{
                hacerJugada(Teclado.leerEntero(getNombre()+" introduce una posicion (1-9): ")-1,p);
    
                bueno = true;
            } catch (IllegalArgumentException IAE){
                System.out.println(IAE.getMessage());
            }
        } while(!bueno);
    }
    
    protected void hacerJugada(int pos, Partida3EnRaya p){
        Jugada3EnRaya jugada = new Jugada3EnRaya(p.getTurno(),pos,this);
        p.recibirJugada(jugada);
    }
}
