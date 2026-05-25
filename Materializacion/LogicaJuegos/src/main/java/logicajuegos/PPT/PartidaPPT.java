package logicajuegos.PPT;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import logicajuegos.Juego;

/**
 *
 * @author jsanchez
 */
public class PartidaPPT extends Juego<Integer,JugadorPPT> {

    private final ArrayList<JugadaPPT> jugadas = new ArrayList<>();
    
    private final int[] puntos;
    private int turno = 0;
    private final int rondas;
    
    private static final Integer EMPATE = 0;
    private static final Integer GANA_J1 = 1;
    private static final Integer GANA_J2 = 2;
    
    protected PartidaPPT(JugadorPPT j1, JugadorPPT j2, int nRondas) {
        super(j1, j2);
        rondas = nRondas;
        puntos =  new int[]{0,0};
    }
    
    public static PartidaPPT crearPartida(JugadorPPT unJ, JugadorPPT otroJ, int nRondas){
        JugadorPPT J1 = elegirQuienComienza(unJ, otroJ);
        JugadorPPT J2;
        if(J1.equals(unJ)){
            J2 = otroJ;
        } else {
            J2 = unJ;
        }
        return new PartidaPPT(J1,J2,nRondas);
    }
    public static PartidaPPT crearPartida(JugadorPPT unJ, int nRondas){
        JugadorPPT bot = BotPPT.crearBot();
        JugadorPPT J1 = elegirQuienComienza(unJ, bot);
        JugadorPPT J2;
        if(J1.equals(unJ)){
            J2 = bot;
        } else {
            J2 = unJ;
        }
        return new PartidaPPT(J1,J2,nRondas);
    }
    
    public static JugadorPPT elegirQuienComienza(JugadorPPT unJ, JugadorPPT otroJ) {
        ArrayList<JugadorPPT> liJ = new ArrayList<>();
        liJ.add(unJ);
        liJ.add(otroJ);
        Random r = new Random();
        return liJ.get(r.nextInt(2));
    }

    public int getTurno() {
        return turno;
    }
    
    @Override
    public void iniciarJuego() {
        setPartidaEnCurso(true);
        nuevoTurno();
    }

    private void nuevoTurno() {
        turno++;
                
        ExecutorService poolHilos = Executors.newFixedThreadPool(2);
        
        Future<JugadaPPT> pedirJugadaJ1 = poolHilos.submit(() -> getJugador1().pedirJugada(this));
        JugadaPPT jugadaJ1 = esperarJugada(pedirJugadaJ1, getJugador1());
        
        Future<JugadaPPT> pedirJugadaJ2 = poolHilos.submit(() -> getJugador2().pedirJugada(this));
        JugadaPPT jugadaJ2 = esperarJugada(pedirJugadaJ2, getJugador2());
        
        int resultadoComparar = jugadaJ1.compareTo(jugadaJ2);
        
        if(resultadoComparar > 0){
            addPuntoJ1();
            System.out.println("Punto de "+getJugador1().getNombre()+".");
        } else if(resultadoComparar < 0){
            addPuntoJ2();
            System.out.println("Punto de "+getJugador2().getNombre()+".");
        } else {
            System.out.println("Empate, no hay puntos.");
        }
        
        jugadas.add(jugadaJ1);
        jugadas.add(jugadaJ2);
        
        continuarTurno();
    }
    
    private JugadaPPT esperarJugada(Future<JugadaPPT> pedirJugada, JugadorPPT j){
        JugadaPPT jugada = JugadaPPT.crearJugada(getTurno(),JugadaPPT.PIEDRA,j);
        try{
            jugada = pedirJugada.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(PartidaPPT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            //Logger.getLogger(PartidaPPT.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Se acabó el tiempo!");
            jugada = JugadaPPT.crearJugada(getTurno(),JugadaPPT.PIEDRA,j);
        }
        return jugada;
    }

    private void continuarTurno() {
        if(getPuntosJ1() > rondas/2){
            setPartidaEnCurso(false);
            setResultado(GANA_J1);
        } else if (getPuntosJ2() > rondas/2){
            setPartidaEnCurso(false);
            setResultado(GANA_J2);
        } else if(turno >= rondas){
            setPartidaEnCurso(false);
            if(getPuntosJ1() == getPuntosJ2()){
                setResultado(EMPATE);
            } else if(getPuntosJ1() > getPuntosJ2()){
                setResultado(GANA_J1);
            } else {
                setResultado(GANA_J2);
            }
        } else {
            nuevoTurno();
        }
    }

    @Override
    public Optional<JugadorPPT> devolverGanador() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private void addPuntoJ1(){
        puntos[0]++;
    }
    
    public int getPuntosJ1(){
        return puntos[0];
    }
            
    private void addPuntoJ2(){
        puntos[1]++;
    }
    
    public int getPuntosJ2(){
        return puntos[1];
    }
    
    public static void main(String[] args){
        JugadorPPT J1 = new JugadorPPT("Pepe");
        JugadorPPT J2 = new JugadorPPT("Juan");
        PartidaPPT p = new PartidaPPT(J1, J2, 3);
        p.iniciarJuego();
    }
}
