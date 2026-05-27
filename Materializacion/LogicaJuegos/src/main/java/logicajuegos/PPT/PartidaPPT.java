package logicajuegos.PPT;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import logicajuegos.Juego;
import logicajuegos.SupplierExcepcionesNoHayGanador;
import logicajuegos.SupplierExcepcionesNoHayResultado;

/**
 *
 * @author jsanchez
 */
public class PartidaPPT extends Juego<Integer,JugadorPPT> {

    private final ArrayList<JugadaPPT> jugadas = new ArrayList<>();
    
    private final int[] puntos;
    private int turno = 0;
    private final int rondas;
    
    public static final int EMPATE = 0;
    public static final int GANA_J1 = 1;
    public static final int GANA_J2 = 2;
    
    private final ExecutorService poolHilos = Executors.newFixedThreadPool(2, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });
    
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

    public int getTurno() {
        return turno;
    }
    
    public void avanzarTurno(){
        turno++;
    }
    
    @Override
    public void iniciarJuego() {
        setPartidaEnCurso(true);
        nuevoTurno();
    }

    protected void nuevoTurno() {
        avanzarTurno();
        
        CompletableFuture<JugadaPPT> pedirJugadaJ1 = new CompletableFuture<>();
        CompletableFuture<JugadaPPT> pedirJugadaJ2 = new CompletableFuture<>();
        
        poolHilos.submit(() -> getJugador1().pedirJugada(this, pedirJugadaJ1));
        poolHilos.submit(() -> getJugador2().pedirJugada(this, pedirJugadaJ2));
        
        JugadaPPT jugadaJ1 = esperarJugada(pedirJugadaJ1, getJugador1());
        JugadaPPT jugadaJ2 = esperarJugada(pedirJugadaJ2, getJugador2());
        
        mostrarJugadas(jugadaJ1,jugadaJ2);
        
        calcularResultadoRonda(jugadaJ1.compareTo(jugadaJ2));
        
        jugadas.add(jugadaJ1);
        jugadas.add(jugadaJ2);
        
        continuarTurno();
    }

    protected void mostrarJugadas(JugadaPPT jugada1, JugadaPPT jugada2) {
        System.out.println(getJugador1().getNombre()+" juega "+jugada1.getNombreGesto()+"\n"+getJugador2().getNombre()+" juega "+jugada2.getNombreGesto());
    }
    
    private void calcularResultadoRonda(int resultadoComparar) {
        if(resultadoComparar > 0){
            addPuntoJ1();
            mostrarResultadoRonda(GANA_J1);
        } else if(resultadoComparar < 0){
            addPuntoJ2();
            mostrarResultadoRonda(GANA_J2);
        } else {
            mostrarResultadoRonda(EMPATE);
        }
    }
    
    public void mostrarResultadoRonda(int resultado){
        switch(resultado){
            case GANA_J1 -> System.out.println("Punto de "+getJugador1().getNombre()+".");
            case GANA_J2 -> System.out.println("Punto de "+getJugador2().getNombre()+".");
            case EMPATE -> System.out.println("Empate, no hay puntos.");
            default -> throw new IllegalArgumentException();
        }
    }
    
    private JugadaPPT esperarJugada(CompletableFuture<JugadaPPT> pedirJugada, JugadorPPT j){
        JugadaPPT jugada = JugadaPPT.crearJugada(getTurno(),JugadaPPT.PIEDRA,j);
        try{
            jugada = pedirJugada.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(PartidaPPT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            //Logger.getLogger(PartidaPPT.class.getName()).log(Level.SEVERE, null, ex);
            mostrarSeAcaboElTiempo();
            jugada = pedirJugada.getNow(JugadaPPT.crearJugada(getTurno(),JugadaPPT.PIEDRA,j));
        } finally {
            pedirJugada.cancel(true);
        }
        return jugada;
    }
    
    public void mostrarSeAcaboElTiempo(){
        System.out.println("Se acabó el tiempo!");
    }
    
    protected void continuarTurno() {
        if(getPuntosJ1() > rondas/2){
            setPartidaEnCurso(false);
            setResultado(GANA_J1);
            mostrarGanador();
        } else if (getPuntosJ2() > rondas/2){
            setPartidaEnCurso(false);
            setResultado(GANA_J2);
            mostrarGanador();
        } else if(turno >= rondas){
            setPartidaEnCurso(false);
            if(getPuntosJ1() == getPuntosJ2()){
                setResultado(EMPATE);
                mostrarEmpate();
            } else if(getPuntosJ1() > getPuntosJ2()){
                setResultado(GANA_J1);
                mostrarGanador();
            } else {
                setResultado(GANA_J2);
                mostrarGanador();
            }
        } else {
            esperarSiguienteTurno();
        }
    }
    
    protected void esperarSiguienteTurno(){
        nuevoTurno();
    }
    
    public void mostrarGanador(){
        System.out.println("####\n\nGana "+devolverGanador().orElseThrow(new SupplierExcepcionesNoHayGanador()).getNombre()+"!");
        System.out.println(getJugador1().getNombre()+" "+getPuntosJ1()+" - "+getPuntosJ2()+" "+getJugador2().getNombre());
    }
    
    public void mostrarEmpate(){
        System.out.println("####\n\nEmpate!");
        System.out.println(getJugador1().getNombre()+" "+getPuntosJ1()+" - "+getPuntosJ2()+" "+getJugador2().getNombre());
    }

    @Override
    public Optional<JugadorPPT> devolverGanador() {
        Optional<JugadorPPT> ganador = Optional.empty();
        if(devolverResultado().orElseThrow(new SupplierExcepcionesNoHayResultado()).equals(GANA_J1)){
            ganador = Optional.of(getJugador1());
        } else if(devolverResultado().orElseThrow(new SupplierExcepcionesNoHayResultado()).equals(GANA_J2)){
            ganador = Optional.of(getJugador2());
        }
        return ganador;
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
        // JugadorPPT J1 = new JugadorPPT("Pepe");
        // JugadorPPT J2 = new JugadorPPT("Juan");
        // PartidaPPT p = PartidaPPT.crearPartida(J1, J2, 3);
        JugadorPPT J1 = new JugadorPPT("Persona");
        PartidaPPT p = PartidaPPT.crearPartida(J1, 3);
        p.iniciarJuego();
    }
}
