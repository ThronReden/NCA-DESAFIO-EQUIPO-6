package logicajuegos;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 *
 * @author jsanchez
 * @param <R> el tipo de dato del resultado de la partida
 * @param <J> el tipo de jugador para el juego concreto,
 *              debe heredar de {@link Jugador}
 */
public abstract class Juego<R,J extends Jugador> {
    
    private final J jugador1;
    private final J jugador2;
    
    private boolean partidaEnCurso;
    private Optional<R> resultado;
    
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
    
    protected void setResultado(R unResultado){
        this.resultado = Optional.of(unResultado);
    }
    
    protected abstract void setResultadoGanaJ1();
    
    protected abstract void setResultadoGanaJ2();
    
    public R devolverResultado() throws NoSuchElementException {
        return resultado.orElseThrow(new SupplierExcepcionesNoHayResultado());
    }
    
    public abstract J devolverGanador();
    
}
