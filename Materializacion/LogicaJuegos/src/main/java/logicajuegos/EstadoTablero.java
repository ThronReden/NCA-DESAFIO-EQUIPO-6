package logicajuegos;

/**
 *
 * @author jsanchez
 */
class EstadoTablero {
    
    private int[] tablero = new int[9];
    
    private int ganador = 0;
    
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
        existe3EnRaya();
    }

    public int getGanador() {
        return ganador;
    }
    
    public void mostrarEstadoTableroTerminal(){
        String br = "+-+-+-+";
        String model = br+"\n";
        String ln = "|";
        
        for (int i = 1; i < 10; i++) {
            switch (this.getCasilla(i-1)) {
                case EstadoTablero.X -> ln += "X|";
                case EstadoTablero.O -> ln += "O|";
                case EstadoTablero.VACIO -> ln += "-|";
                default -> ln += "-|";
            }
            if(i%3 == 0){
                model += ln+"\n"+br+"\n";
                ln = "|";
            }
        }
        
        System.out.println(model);
    }

    public boolean existe3EnRaya() {
        return comprobarFilas()
            || comprobarColumnas()
            || comprobarDiagonales();
    }

    private boolean comprobarFilas() {
        boolean existe = false;
        for(int fila = 0; fila < 3; fila++) {
            existe = true;
            for(int casilla = fila*3; casilla < fila*3+2; casilla++) {
                if(tablero[casilla] == EstadoTablero.VACIO){
                    existe = false;
                    break;
                } else {
                    existe &= tablero[casilla] == tablero[casilla+1];
                }
            }
            if(existe){
                ganador = tablero[fila];
                break;
            }
        }
        return existe;
    }

    private boolean comprobarColumnas() {
        boolean existe = false;
        for(int columna = 0; columna < 3; columna++) {
            existe = true;
            for(int casilla = columna; casilla < columna+6; casilla+=3) {
                if(tablero[casilla] == EstadoTablero.VACIO){
                    existe = false;
                    break;
                } else {
                    existe &= tablero[casilla] == tablero[casilla+3];
                }
            }
            if(existe){
                ganador = tablero[columna];
                break;
            }
        }
        return existe;
    }

    private boolean comprobarDiagonales() {
        boolean existe;
        existe = ((tablero[0] != EstadoTablero.VACIO)
                && (tablero[0] == tablero[4] && tablero[4] == tablero[8]))
            || ((tablero[2] != EstadoTablero.VACIO)
                && (tablero[2] == tablero[4] && tablero[4] == tablero[6]));
        if(existe){
            ganador = tablero[4];
        }
        return existe;
    }

    public boolean tableroVacio() {
        boolean vacio = true;
        for(int casilla : tablero){
            if(casilla != EstadoTablero.VACIO){
                vacio = false;
                break;
            }
        }
        return vacio;
    }
    
    public boolean tableroLleno() {
        boolean lleno = true;
        for(int casilla : tablero){
            if(casilla == EstadoTablero.VACIO){
                lleno = false;
                break;
            }
        }
        return lleno;
    }
}
