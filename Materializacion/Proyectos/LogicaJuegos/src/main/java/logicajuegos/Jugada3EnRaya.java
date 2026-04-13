package logicajuegos;

/**
 *
 * @author jsanchez
 */
class Jugada3EnRaya {
    
    private int turno;
    private int posicion;
    private Jugador jugador;
    
    public static final int ARRIBA_IZQUIERDA = 0;
    public static final int ARRIBA = 1;
    public static final int ARRIBA_DERECHA = 2;
    public static final int CENTRO_IZQUIERDA = 3;
    public static final int CENTRO = 4;
    public static final int CENTRO_DERECHA = 5;
    public static final int ABAJO_IZQUIERDA = 6;
    public static final int ABAJO = 7;
    public static final int ABAJO_DERECHA = 8;
    
    public Jugada3EnRaya(int turn, int pos, Jugador j){
        if(pos < 0 || pos >= 9){
            throw new IllegalArgumentException("Posicion no valida");
        } else {
            this.turno = turn;
            this.posicion = pos;
            this.jugador = j;
        }
    }

    public int getTurno() {
        return turno;
    }

    public int getPosicion() {
        return posicion;
    }

    public Jugador getJugador() {
        return jugador;
    }
    
}
