package logicajuegos.PPT;

/**
 *
 * @author jsanchez
 */
public class JugadaPPTLS extends JugadaPPT{
    
    public static final int LAGARTO = 3;
    public static final int SPOCK = 4;
    
    private JugadaPPTLS(int elTurno, int elGesto, JugadorPPT elJugador) {
        super(elTurno, elGesto, elJugador);
    }
    
    public static JugadaPPTLS crearJugada(int elTurno, int elGesto, JugadorPPTLS elJugador){
        if(elGesto < 0 || elGesto > 4) {
            throw new IllegalArgumentException("Gesto no valido");
        }
        return new JugadaPPTLS(elTurno,elGesto,elJugador);
    }
    
    @Override
    public int compareTo(JugadaPPT otraJugada) {
        int result = 0;
        
        switch(this.getGesto()){
            case PIEDRA -> {
                switch(otraJugada.getGesto()){
                    case PIEDRA -> result = 0;
                    case PAPEL -> result = -1;
                    case TIJERA -> result = 1;
                    case LAGARTO -> result = 1;
                    case SPOCK -> result = -1;
                }
            }
            case PAPEL -> {
                switch(otraJugada.getGesto()){
                    case PIEDRA -> result = 1;
                    case PAPEL -> result = 0;
                    case TIJERA -> result = -1;
                    case LAGARTO -> result = -1;
                    case SPOCK -> result = 1;
                }
            }
            case TIJERA -> {
                switch(otraJugada.getGesto()){
                    case PIEDRA -> result = -1;
                    case PAPEL -> result = 1;
                    case TIJERA -> result = 0;
                    case LAGARTO -> result = 1;
                    case SPOCK -> result = -1;
                }
            }
            case LAGARTO -> {
                switch(otraJugada.getGesto()){
                    case PIEDRA -> result = -1;
                    case PAPEL -> result = 1;
                    case TIJERA -> result = -1;
                    case LAGARTO -> result = 0;
                    case SPOCK -> result = 1;
                }
            }
            case SPOCK -> {
                switch(otraJugada.getGesto()){
                    case PIEDRA -> result = 1;
                    case PAPEL -> result = -1;
                    case TIJERA -> result = 1;
                    case LAGARTO -> result = -1;
                    case SPOCK -> result = 0;
                }
            }
        }
        
        return result;
    }

    public static int getLAGARTO() {
        return LAGARTO;
    }

    public static int getSPOCK() {
        return SPOCK;
    }
    
    @Override
    public String getNombreGesto() {
        String nombre;
        
        switch(this.getGesto()){
            case LAGARTO -> nombre = "lagarto";
            case SPOCK -> nombre = "spock";
            default -> nombre = super.getNombreGesto();
        }
        
        return nombre;
    }
}
