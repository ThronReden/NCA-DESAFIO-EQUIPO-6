package pantallas.juegos;

import static logicajuegos.Juego.elegirQuienComienza;
import logicajuegos.PPT.BotPPT;
import logicajuegos.PPT.JugadaPPT;
import logicajuegos.PPT.JugadorPPT;
import logicajuegos.PPT.PartidaPPT;
import logicajuegos.SupplierExcepcionesNoHayGanador;

/**
 *
 * @author jsanchez
 */
public class PartidaPPTUI extends PartidaPPT {
    
    public PPT Pantalla;
    
    private PartidaPPTUI(JugadorPPT j1, JugadorPPT j2, int nRondas, PPT p){
        super(j1,j2,nRondas);
        Pantalla = p;
    }
    
    public static PartidaPPTUI crearPartida(JugadorPPTUI unJ, int nRondas, PPT p){
        JugadorPPT bot = BotPPT.crearBot();
        JugadorPPT J1 = elegirQuienComienza(unJ, bot);
        JugadorPPT J2;
        if(J1.equals(unJ)){
            J2 = bot;
        } else {
            J2 = unJ;
        }
        return new PartidaPPTUI(J1,J2,nRondas,p);
    }
    
    @Override
    protected void esperarSiguienteTurno() {
        Pantalla.mostrarBotonContinuar();
    }
    
    @Override
    protected void continuarTurno(){
        super.continuarTurno();
    }
    
    protected void siguienteTurno(){
        nuevoTurno();
    }
    
    @Override
    protected void mostrarJugadas(JugadaPPT jugada1, JugadaPPT jugada2) {
       JugadaPPT jugadaPersona = jugada1.getJugador() instanceof JugadorPPTUI? jugada1 : jugada2;
       JugadaPPT jugadaBot = jugadaPersona == jugada1? jugada2 : jugada1;
       Pantalla.mostrarJugadaPersona(jugadaPersona.getGesto());
       Pantalla.mostrarJugadaBot(jugadaBot.getGesto());
    }
    
    @Override
    public void mostrarResultadoRonda(int resultado){
        switch(resultado){
            case EMPATE -> Pantalla.resultadoRondaEMPATE();
            case GANA_J1 -> Pantalla.resultadoRondaGANA_J1();
            case GANA_J2 -> Pantalla.resultadoRondaGANA_J2();
            default -> throw new IllegalArgumentException();
        }
        Pantalla.mostrarPuntos();
    }
    
    @Override
    public void mostrarSeAcaboElTiempo(){
        Pantalla.pararCuentaAtras();
    }
    
    @Override
    public void mostrarGanador(){
        Pantalla.mostrarResultado(devolverGanador().orElseThrow(new SupplierExcepcionesNoHayGanador()));
    }
    
    @Override
    public void mostrarEmpate(){
        Pantalla.mostrarResultado(EMPATE);
    }
}
