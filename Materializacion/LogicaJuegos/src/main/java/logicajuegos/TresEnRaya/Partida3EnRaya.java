package logicajuegos.TresEnRaya;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import logicajuegos.Juego;

/**
 *
 * @author jsanchez
 */
public class Partida3EnRaya extends Juego<Integer,Jugador3EnRaya> {
    
    private final ArrayList<Jugada3EnRaya> jugadas = new ArrayList<>();
    
    private int turno = 0;
    
    private static final Integer EMPATE = EstadoTablero.VACIO;
    private static final Integer GANA_J1 = EstadoTablero.X;
    private static final Integer GANA_J2 = EstadoTablero.O;

    public Partida3EnRaya(Jugador3EnRaya j1, Jugador3EnRaya j2) {
        super(j1, j2);
    }
    
    public static Partida3EnRaya crearPartida(Jugador3EnRaya unJ, Jugador3EnRaya otroJ) {
        Jugador3EnRaya J1 = elegirQuienComienza(unJ, otroJ);
        Jugador3EnRaya J2;
        if(J1.equals(unJ)){
            J2 = otroJ;
        } else {
            J2 = unJ;
        }
        return new Partida3EnRaya(J1,J2);
    }
    
    private static Jugador3EnRaya elegirQuienComienza(Jugador3EnRaya unJ, Jugador3EnRaya otroJ) {
        ArrayList<Jugador3EnRaya> liJ = new ArrayList<>();
        liJ.add(unJ);
        liJ.add(otroJ);
        Random r = new Random();
        return liJ.get(r.nextInt(2));
    }
    
    private Jugador3EnRaya getJugadorTurnoActual(){
        Jugador3EnRaya j = null;
        if(isPartidaEnCurso()){
            switch (turno%2){
                case 1 -> j = getJugador1();
                case 0 -> j = getJugador2();
                default -> j = null;
            }
        }
        return j;
    }
    
    public int getTurno() {
        return turno;
    }
    
    private int getTurnoJugada(int pos) {
        for(Jugada3EnRaya j : jugadas){
            if(j.getPosicion() == pos){
                return j.getTurno();
            }
        }
        return 0;
    }
    
    private boolean seHaJugado(int pos) {
        for(Jugada3EnRaya j: jugadas){
            if(j.getPosicion() == pos){
                return true;
            }
        }
        return false;
    }

    private EstadoTablero getEstadoTablero() {
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
    
    @Override
    public void iniciarJuego() {
        setPartidaEnCurso(true);
        nuevoTurno();
    }
    
    private void nuevoTurno(){
        turno++;
//        System.out.println("Turno de "+getJugadorTurnoActual().getNombre());
        enviarEstadoTablero(getJugadorTurnoActual());
        getJugadorTurnoActual().pedirJugada(this);
    }
    
    private void enviarEstadoTablero(Jugador3EnRaya j){
        j.recibirEstadoTablero(getEstadoTablero());
    }
    
    public void recibirJugada(Jugada3EnRaya jugada) throws IllegalArgumentException {
        if(seHaJugado(jugada.getPosicion())){
            throw new IllegalArgumentException("No se puede hacer otra jugada en esa casilla.");
        } else {
            this.jugadas.add(jugada);
        }
        continuarTurno();
    }
    
    private void continuarTurno() {
        if(existe3EnRaya()){
            setPartidaEnCurso(false);
            switch (getEstadoTablero().getGanador()){
                case 1 -> setResultado(GANA_J1);
                case 2 -> setResultado(GANA_J2);
            }
//            System.out.println("####\n\nGana "+getGanador().getNombre()+"!");
//            getEstadoTablero().mostrarEstadoTableroTerminal();
        } else if(tableroLleno()){
            setPartidaEnCurso(false);
            setResultado(EMPATE);
//            System.out.println("####\n\nEmpate!");
//            getEstadoTablero().mostrarEstadoTableroTerminal();
        } else {
            nuevoTurno();
        }
    }
    
    private boolean existe3EnRaya() {
        return getEstadoTablero().existe3EnRaya();
    }

    private boolean tableroLleno() {
        return getEstadoTablero().tableroLleno();
    }
    
    @Override
    public Optional<Jugador3EnRaya> devolverGanador() {
        Optional<Jugador3EnRaya> ganador = Optional.empty();
        if(devolverResultado().orElseThrow(new SupplierExcepcionesNoHayResultado()).equals(GANA_J1)){
            ganador = Optional.of(getJugador1());
        } else if(devolverResultado().orElseThrow(new SupplierExcepcionesNoHayResultado()).equals(GANA_J2)){
            ganador = Optional.of(getJugador2());
        }
        return ganador;
    }
    
    public void mostrarTableroTerminal(){
        String br = "+-+-+-+";
        String model = br+"\n";
        String ln = "|";
        
        for (int i = 1; i < 10; i++) {
            if(!seHaJugado(i)){
                ln += "-|";
            } else if(getTurnoJugada(i) % 2 == 1){
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
    
    public static void main(String[] args) {
//        Jugador3EnRaya unJ = new Jugador3EnRaya("Pepe");
        Jugador3EnRaya unJ = new Jugador3EnRaya("Persona");
//        Jugador3EnRaya otroJ = new Jugador3EnRaya("Juan");
        Bot3EnRaya otroJ = Bot3EnRaya.crearBot3EnRaya();
        
        Partida3EnRaya p = Partida3EnRaya.crearPartida(unJ, otroJ);
        try {
            System.out.println(p.devolverGanador().orElseThrow(new SupplierExcepcionesNoHayGanador()).getNombre());
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(p.devolverResultado().orElseThrow(new SupplierExcepcionesNoHayResultado()));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        p.iniciarJuego();
    }
    
}
