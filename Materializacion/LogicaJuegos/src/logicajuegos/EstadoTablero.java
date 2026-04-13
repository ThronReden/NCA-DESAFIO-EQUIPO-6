package logicajuegos;

/**
 *
 * @author jsanchez
 */
class EstadoTablero {
    
    private int[] tablero = new int[9];
    
    public static final int VACIO = 0;
    public static final int X = 1;
    public static final int O = 2;
    
    public EstadoTablero(){
        for(int casilla : tablero){
            casilla = 0;
        }
    }
    
    public int getCasilla(int i){
        return this.tablero[i];
    }
    
    public void setCasilla(int i, int marca){
        if(marca < 0 || marca > 2){
            throw new IllegalArgumentException(marca+" no es una marca valida.");
        } else if (i < 0 || i >= this.tablero.length){
            throw new IllegalArgumentException(i+" esta fuera de rango.");    
        } else {
            this.tablero[i] = marca;
        }
    }
}
