package logicajuegos;

import java.util.Optional;

/**
 * Esta clase define nuestro arquetipo de juego en terminos generales y será
 * padre de todos los tipos de juego implementados posteriormente.
 * 
 * <p>Es abstracta ya que no hay juegos que sean solo {@code Juego}, un juego debe ser
 * una implementacion de {@code Juego} para un tipo de resultado R y un tipo de
 * {@link Jugador} J.
 * 
 * @author jsanchez
 * @param <R> el tipo de dato del resultado de la partida
 * @param <J> el tipo de jugador para el juego concreto, 
 *              debe heredar de {@link Jugador}
 * @see Jugador
 */
public abstract class Juego<R,J extends Jugador> {
    
    private final J jugador1; //Nuestro Jugador 1
    private final J jugador2; //Nuestro Jugador 2
    //Un Juego según nuestra definición por defecto tiene al menos 2 Jugadores
    
    private boolean partidaEnCurso; //Flag para almacenar el estado del juego
    private R resultado; //El resultado de la partida (null si no ha terminado)
    
    /**
     * Constructor de la clase abstracta {@code Juego}.
     * 
     * <p>Recibe dos jugadores de clase {@code J} y los guarda como atributos.
     * 
     * @param j1, el jugador1
     * @param j2, el jugador2
     */
    protected Juego(J j1, J j2){
        this.jugador1 = j1;
        this.jugador2 = j2;
    }

    /**
     * Getter para {@code jugador1}.
     * 
     * <p>Cuidado porque devuelve el objeto como tal, no una copia.
     * <p>El tipo de jugador dependerá de {@code J}.
     * 
     * @return el jugador1 de esa instancia de {@code Juego}
     */
    public J getJugador1() {
        return jugador1;
    }

    /**
     * Getter para {@code jugador2}.
     * 
     * <p>Cuidado porque devuelve el objeto como tal, no una copia.
     * <p>El tipo de jugador dependerá de {@code J}.
     * 
     * @return el jugador2 de esa instancia de {@code Juego}
     */
    public J getJugador2() {
        return jugador2;
    }
    
    /**
     * Getter para {@code partidaEnCurso}.
     * 
     * <p>Nos sirve para no permitir acciones a los jugadores cuando ya haya
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
     * @param isPartidaEnCurso, el estado de la partida a almacenar
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
     * Setter para {@code resultado}.
     * 
     * <p>El tipo de dato del resultado dependerá de {@code R}
     * 
     * @param unResultado, el resultado de la partida
     */
    protected void setResultado(R unResultado){
        this.resultado = unResultado;
    }
    
    /**
     * Getter para {@code resultado} que devuelve un {@code Optional} para
     * gestionar la posibilidad de que no exista resultado todavía.
     * 
     * <p>En caso de no haber resultado (es {@code null}) se lanzará
     * una excepción personalizada.
     * @see SupplierExcepcionesNoHayResultado
     * 
     * @return el resultado de la partida
     */
    public Optional<R> devolverResultado() {
        return Optional.ofNullable(resultado);
    }
    
    /**
     * Getter para ganador que devuelve un {@code Optional} para gestionar la
     * posibilidad de que no exista ganador todavía.
     * 
     * <p>Es abstracto, de implementación obligada.
     * 
     * <p>En caso de no haber resultado (resultado es {@code null}) o no haber
     * ganador por otra razón se lanzarán excepciones personalizadas.
     * @see SupplierExcepcionesNoHayResultado
     * @see logicajuegos.SupplierExcepcionesNoHayResultado
     * 
     * @return el {@code Jugador} ganador de la partida
     */
    public abstract Optional<J> devolverGanador();
    
}
