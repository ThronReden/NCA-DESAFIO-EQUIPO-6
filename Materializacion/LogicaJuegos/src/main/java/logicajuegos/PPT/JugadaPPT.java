package logicajuegos.PPT;

/**
 *
 * @author jsanchez
 */
class JugadaPPT implements Comparable<JugadaPPT> {
    
    private final int turno;
    private final int gesto;
    private final JugadorPPT jugador;
    
    public static final int PIEDRA = 0;
    public static final int PAPEL = 1;
    public static final int TIJERA = 2;

    private JugadaPPT(int elTurno, int elGesto, JugadorPPT elJugador){
        this.turno = elTurno;
        this.gesto = elGesto;
        this.jugador = elJugador;
    }
    
    public static JugadaPPT crearJugada(int elTurno, int elGesto, JugadorPPT elJugador){
        if(elGesto < 0 || elGesto > 2) {
            throw new IllegalArgumentException("Gesto no valido");
        }
        return new JugadaPPT(elTurno,elGesto,elJugador);
    }

    public int getTurno() {
        return turno;
    }

    public int getGesto() {
        return gesto;
    }

    public JugadorPPT getJugador() {
        return jugador;
    }
    
    @Override
    public int compareTo(JugadaPPT otraJugada) {
        int result = 0;
        
        switch(this.getGesto()){
            case JugadaPPT.PIEDRA -> {
                switch(otraJugada.getGesto()){
                    case JugadaPPT.PIEDRA -> result = 0;
                    case JugadaPPT.PAPEL -> result = -1;
                    case JugadaPPT.TIJERA -> result = 1;
                }
            }
            case JugadaPPT.PAPEL -> {
                switch(otraJugada.getGesto()){
                    case JugadaPPT.PIEDRA -> result = 1;
                    case JugadaPPT.PAPEL -> result = 0;
                    case JugadaPPT.TIJERA -> result = -1;
                }
            }
            case JugadaPPT.TIJERA -> {
                switch(otraJugada.getGesto()){
                    case JugadaPPT.PIEDRA -> result = -1;
                    case JugadaPPT.PAPEL -> result = 1;
                    case JugadaPPT.TIJERA -> result = 0;
                }
            }
        }
        
        return result;
    }
    
    public int getPIEDRA() {
        return PIEDRA;
    }

    public int getPAPEL() {
        return PAPEL;
    }

    public int getTIJERA() {
        return TIJERA;
    }
    
}
