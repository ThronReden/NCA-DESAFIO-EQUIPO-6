package logicajuegos.PPT;

/**
 *
 * @author jsanchez
 */
public class PartidaPPTLS extends PartidaPPT {
    
    private PartidaPPTLS(JugadorPPTLS j1, JugadorPPTLS j2, int nRondas) {
        super(j1, j2, nRondas);
    }
    
    public static PartidaPPTLS crearPartida(JugadorPPTLS unJ, JugadorPPTLS otroJ, int nRondas){
        JugadorPPTLS J1 = elegirQuienComienza(unJ, otroJ);
        JugadorPPTLS J2;
        if(J1.equals(unJ)){
            J2 = otroJ;
        } else {
            J2 = unJ;
        }
        return new PartidaPPTLS(J1,J2,nRondas);
    }
    public static PartidaPPTLS crearPartida(JugadorPPTLS unJ, int nRondas){
        JugadorPPTLS bot = BotPPTLS.crearBot();
        JugadorPPTLS J1 = elegirQuienComienza(unJ, bot);
        JugadorPPTLS J2;
        if(J1.equals(unJ)){
            J2 = bot;
        } else {
            J2 = unJ;
        }
        return new PartidaPPTLS(J1,J2,nRondas);
    }
    
    public static void main(String[] args){
        JugadorPPTLS J1 = new JugadorPPTLS("Persona");
        PartidaPPTLS p = PartidaPPTLS.crearPartida(J1, 3);
        p.iniciarJuego();
    }
}
