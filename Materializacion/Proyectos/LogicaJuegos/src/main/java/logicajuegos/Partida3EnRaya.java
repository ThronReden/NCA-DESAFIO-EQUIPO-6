package logicajuegos;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author jsanchez
 */
public class Partida3EnRaya extends Juego{
    
    private final ArrayList<Jugada3EnRaya> jugadas = new ArrayList<>();
    
    public static void main(String[] args) {
        Partida3EnRaya p = new Partida3EnRaya();
        p.mostrarTablero();
    }
    
    public void mostrarTablero(){
        String br = "+-+-+-+";
        String model = br+"\n";
        String ln = "|";
        
        for (int i = 1; i < 10; i++) {
            if(!seHaJugado(i)){
                ln += "-|";
            } else if(turnoJugada(i) % 2 == 1){
                ln += "X|";
            } else {
                ln += "O|";
            }
            if(i%3 == 0){
                model += ln+"\n"+br+"\n";
                ln = "|";
            }
        }
        
        System.out.println(model);
    }

    private boolean seHaJugado(int pos) {
        for(Jugada3EnRaya j: jugadas){
            if(j.getPosicion() == pos){
                return true;
            }
        }
        return false;
    }
    
    private int turnoJugada(int pos) {
        for(Jugada3EnRaya j: jugadas){
            if(j.getPosicion() == pos){
                return j.getTurno();
            }
        }
        return 0;
    }
}
