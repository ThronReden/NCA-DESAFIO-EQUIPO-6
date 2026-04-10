package logicajuegos;

/**
 *
 * @author jsanchez
 */
class Jugada3EnRaya {
    
    private int turno;
    private int posicion;
    private String jugador;
    
    public static final int ARRIBA_IZQUIERDA = 1;
    public static final int ARRIBA = 2;
    public static final int ARRIBA_DERECHA = 3;
    public static final int CENTRO_IZQUIERDA = 4;
    public static final int CENTRO = 5;
    public static final int CENTRO_DERECHA = 6;
    public static final int ABAJO_IZQUIERDA = 7;
    public static final int ABAJO = 8;
    public static final int ABAJO_DERECHA = 9;
    
    public Jugada3EnRaya(int turn, int pos, String j){
        if(pos < 1 || pos > 9){
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

    public String getJugador() {
        return jugador;
    }
    
}
