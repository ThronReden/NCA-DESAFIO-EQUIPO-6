package logicajuegos;

/**
 * Esta clase define nuestro arquetipo de jugador.
 * 
 * <p>Por ahora igual esta un poco vacia, pero nos basta...
 * 
 * <p><b>Constructor:</b> {@link #Jugador(String)}
 * 
 * @see Juego
 * 
 * @author jsanchez
 */
public class Jugador{
    
    /**
     * El nombre del jugador.
     * 
     * <p>Un texto que lo representa.
     * 
     * @see #getNombre()
     * @see #setNombre(String)
     */
    private String nombre;

    /**
     * Constructor para la clase {@link Jugador}.
     * 
     * @param nombre el {@link #nombre} del jugador a crear
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Getter para {@link #nombre}.
     * 
     * @return el {@link #nombre} de este {@link Jugador}
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter para {@link #nombre}.
     * 
     * @param nombre el nuevo {@link #nombre} para este  {@link Jugador}
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
