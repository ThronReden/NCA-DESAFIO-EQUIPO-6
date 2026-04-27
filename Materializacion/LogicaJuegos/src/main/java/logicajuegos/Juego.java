package logicajuegos;

import java.util.Optional;

/**
 * Esta clase define nuestro arquetipo de juego en terminos generales y será
 * padre de todos los tipos de juego implementados posteriormente.
 * 
 * <p>Es abstracta ya que no hay juegos que sean solo {@code Juego}, un juego debe ser
 * una implementacion de {@code Juego} para un tipo de resultado {@code R} y un tipo de
 * {@link Jugador} {@code J} propios.
 * 
 * <p><b>Constructor:</b> {@link #Juego(J,J)}
 * 
 * @param <R> el tipo de dato del {@link #resultado} de la partida
 * @param <J> el tipo de jugador para el juego concreto, debe heredar de {@link Jugador}
 * 
 * @see Jugador
 * 
 * @author jsanchez
 */
public abstract class Juego<R,J extends Jugador> {
    /**
     * Nuestro Jugador 1.
     * 
     * <p>Un {@code Juego} según nuestra definición tiene por defecto al menos
     * 2 Jugadores: {@link #jugador1} y {@link #jugador2}
     * 
     * @see Jugador
     * @see #getJugador1()
     */
    private final J jugador1;
    /**
     * Nuestro Jugador 2.
     * 
     * <p>Un {@code Juego} según nuestra definición tiene por defecto al menos
     * 2 Jugadores: {@link #jugador1} y {@link #jugador2}
     * 
     * @see Jugador
     * @see #getJugador2()
     */
    private final J jugador2;
    /**
     * Flag para almacenar el estado del juego, por defecto es {@code false}.
     * 
     * <p>Será {@code true} cuando la partida esté siendo jugada y {@code false}
     * en cualquier otro caso. (Partida sin iniciar, partida finalizada...)
     * <p>Nos sirve para no permitir acciones a los jugadores cuando ya haya
     * terminado el juego y cosas similares.
     * 
     * @see #isPartidaEnCurso()
     * @see #setPartidaEnCurso(boolean)
     */
    private boolean partidaEnCurso = false;
    /**
     * El resultado de la partida.
     * 
     * <p>Seá {@code null} si la partida no ha comenzado o no ha terminado
     * todavía.
     * 
     * @see #devolverResultado()
     * @see #setResultado(R)
     */
    private R resultado;
    
    /**
     * Constructor para la clase abstracta {@link Juego}.
     * 
     * <p>Recibe dos jugadores de clase {@code J} y los guarda como atributos.
     * 
     * @param j1 el {@link #jugador1} del juego a crear
     * @param j2 el {@link #jugador2} del juego a crear
     */
    protected Juego(J j1, J j2){
        this.jugador1 = j1;
        this.jugador2 = j2;
    }

    /**
     * Getter para {@link #jugador1}.
     * 
     * <p>Cuidado porque <b>devuelve el objeto como tal</b>, no una copia.
     * <p>El tipo de jugador dependerá de {@code J}.
     * 
     * @return el {@link #jugador1} de esta instancia de {@link Juego}
     */
    public J getJugador1() {
        return jugador1;
    }

    /**
     * Getter para {@link #jugador2}.
     * 
     * <p>Cuidado porque <b>devuelve el objeto como tal</b>, no una copia.
     * <p>El tipo de jugador dependerá de {@code J}.
     * 
     * @return el {@link #jugador2} de esta instancia de {@code Juego}
     */
    public J getJugador2() {
        return jugador2;
    }
    
    /**
     * Getter para {@link #partidaEnCurso}.
     * 
     * <p>Nos sirve para no permitir acciones a los {@link #jugador1 jugadores} cuando ya haya
     * terminado el juego y cosas similares.
     * 
     * @return {@code true} si la partida está en curso,
     * {@code false} en cualquier otro caso
     */
    public boolean isPartidaEnCurso() {
        return partidaEnCurso;
    }

    /**
     * Setter para {@code partidaEnCurso}.
     * 
     * @param isPartidaEnCurso el estado de la partida
     */
    protected void setPartidaEnCurso(boolean isPartidaEnCurso) {
        this.partidaEnCurso = isPartidaEnCurso;
    }
    
    /**
     * Metodo que inicia el bucle principal del juego.
     * 
     * <p>Es de implementacion obligada.
     */
    public abstract void iniciarJuego();
    
    /**
     * Setter para {@link #resultado}.
     * 
     * <p>El tipo de dato del resultado dependerá de {@code R}
     * 
     * @param unResultado el resultado de la partida
     */
    protected void setResultado(R unResultado){
        this.resultado = unResultado;
    }
    
    /**
     * Getter para {@link #resultado} que devuelve un {@link Optional} para
     * gestionar la posibilidad de que no exista resultado todavía.
     * 
     * <p>En caso de no haber resultado (es {@code null}) se lanzará
     * una excepción personalizada: {@link SupplierExcepcionesNoHayResultado}
     * 
     * @return el {@code resultado} de la partida
     */
    public Optional<R> devolverResultado() {
        return Optional.ofNullable(resultado);
    }
    
    /**
     * Getter para ganador que devuelve un {@link Optional} para gestionar la
     * posibilidad de que no exista ganador todavía.
     * 
     * <p>Metodo abstracto, de implementación obligada.
     * 
     * <p>En caso de no haber {@link #resultado} (resultado es {@code null}) o no haber
     * ganador por cualquier otra razón se lanzarán excepciones personalizadas.
     * Ver {@link SupplierExcepcionesNoHayResultado} y
     * {@link logicajuegos.TresEnRaya.SupplierExcepcionesNoHayGanador SupplierExcepcionesNoHayGanador}
     * 
     * @return el {@link Jugador} ganador de la partida en caso de haberlo ({@link Optional})
     */
    public abstract Optional<J> devolverGanador();
    
}
