package pantallas.juegos;

import static logicajuegos.Juego.elegirQuienComienza;
import logicajuegos.PPT.BotPPTLS;
import logicajuegos.PPT.JugadaPPT;
import logicajuegos.PPT.JugadorPPTLS;
import logicajuegos.PPT.PartidaPPTLS;
import logicajuegos.SupplierExcepcionesNoHayGanador;

/**
 *
 * @author jsanchez
 */
public class PartidaPPTLSUI extends PartidaPPTLS {
    
    public PPTLS Pantalla;
    
    private PartidaPPTLSUI(JugadorPPTLS j1, JugadorPPTLS j2, int nRondas, PPTLS p){
        super(j1,j2,nRondas);
        Pantalla = p;
    }
    
    public static PartidaPPTLSUI crearPartida(JugadorPPTLSUI unJ, int nRondas, PPTLS p){
        JugadorPPTLS bot = BotPPTLS.crearBot();
        JugadorPPTLS J1 = elegirQuienComienza(unJ, bot);
        JugadorPPTLS J2;
        if(J1.equals(unJ)){
            J2 = bot;
        } else {
            J2 = unJ;
        }
        return new PartidaPPTLSUI(J1,J2,nRondas,p);
    }
    
    @Override
    protected void esperarSiguienteTurno(){
        Pantalla.continuarTurno.setEnabled(true);
        Pantalla.continuarTurno.setVisible(true);
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
       JugadaPPT jugadaPersona = jugada1.getJugador() instanceof JugadorPPTLSUI? jugada1 : jugada2;
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
        //Pantalla.mostrarPuntos();
    }
    
    @Override
    public void mostrarSeAcaboElTiempo(){
        Pantalla.pararCuentaAtras();
    }
    
    @Override
    public void mostrarGanador(){
        Pantalla.mostrarResultado((JugadorPPTLS) devolverGanador().orElseThrow(new SupplierExcepcionesNoHayGanador()));
    }
    
    @Override
    public void mostrarEmpate(){
        Pantalla.mostrarResultado(EMPATE);
    }
}
