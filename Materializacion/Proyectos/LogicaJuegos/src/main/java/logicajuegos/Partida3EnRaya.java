package logicajuegos;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jsanchez
 */
public class Partida3EnRaya extends Juego{
    
    private final ArrayList<Jugada3EnRaya> jugadas = new ArrayList<>();
    
    private int turno = 0;
    
    private Partida3EnRaya(Jugador J1, Jugador J2){
        super(J1,J2);
    }
    
    public static void main(String[] args) {
        Jugador j1 = new Jugador();
        Jugador j2 = new Jugador();
        Partida3EnRaya p = new Partida3EnRaya(j1, j2);
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
    
    private void enviarEstadoTablero(Jugador3EnRaya j){
        j.recibirEstadoTablero(estadoTablero());
    }
    
    public void recibirJugada(Jugada3EnRaya jugada){
        this.jugadas.add(jugada);
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
        for(Jugada3EnRaya j : jugadas){
            if(j.getPosicion() == pos){
                return j.getTurno();
            }
        }
        return 0;
    }

    private EstadoTablero estadoTablero() {
        EstadoTablero t = new EstadoTablero();
        for(Jugada3EnRaya j : jugadas){
            if(j.getJugador().equals(this.getJugador1())){
                t.setCasilla(j.getPosicion(), EstadoTablero.X);
            } else {
                t.setCasilla(j.getPosicion(), EstadoTablero.O);
            }
        }
        return t;
    }

    public static Partida3EnRaya crear3EnRaya(Jugador unJ, Jugador otroJ){
        Jugador J1 = elegirQuienComienza(unJ, otroJ);
        Jugador J2;
        if(J1.equals(unJ)){
            J2 = otroJ;
        } else {
            J2 = unJ;
        }
        return new Partida3EnRaya(J1,J2);
    }
    
    @Override
    public boolean iniciarJuego() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int devolverResultado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static Jugador elegirQuienComienza(Jugador unJ, Jugador otroJ) {
        ArrayList<Jugador> liJ = new ArrayList<>();
        liJ.add(unJ);
        liJ.add(otroJ);
        Random r = new Random();
        return liJ.get(r.nextInt(1));
    }
}
