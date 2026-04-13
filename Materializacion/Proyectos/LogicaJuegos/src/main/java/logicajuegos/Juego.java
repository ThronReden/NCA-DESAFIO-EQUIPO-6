package logicajuegos;

/**
 *
 * @author jsanchez
 */
abstract class Juego {
    
    private Jugador jugador1;
    private Jugador jugador2;
    
    protected Juego(Jugador j1, Jugador j2){
        this.jugador1 = j1;
        this.jugador2 = j2;
    }
    
    public abstract boolean iniciarJuego();
    //enviar mensaje terminar juego, cambiar pantalla visible
    
    public abstract int devolverResultado();

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }
}
