package logicajuegos;

/**
 *
 * @author jsanchez
 * @param <J> el tipo de jugador para el juego concreto,
 *              debe heredar de {@link Jugador}
 */
public abstract class Juego<J extends Jugador> {
    
    private final J jugador1;
    private final J jugador2;
    
    private boolean partidaEnCurso;
    
    protected Juego(J j1, J j2){
        this.jugador1 = j1;
        this.jugador2 = j2;
    }

    public J getJugador1() {
        return jugador1;
    }

    public J getJugador2() {
        return jugador2;
    }
    
    /**
     * @return the partidaEnCurso
     */
    public boolean isPartidaEnCurso() {
        return partidaEnCurso;
    }

    /**
     * @param partidaEnCurso the partidaEnCurso to set
     */
    protected void setPartidaEnCurso(boolean partidaEnCurso) {
        this.partidaEnCurso = partidaEnCurso;
    }
    
    public abstract void iniciarJuego();
    //enviar mensaje terminar juego, cambiar pantalla visible
    
    public abstract int devolverResultado();
    
}
