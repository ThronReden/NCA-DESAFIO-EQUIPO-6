package pantallas.juegos;

import logicajuegos.TresEnRaya.Bot3EnRaya;
import logicajuegos.TresEnRaya.Jugador3EnRaya;
import logicajuegos.TresEnRaya.Partida3EnRaya;

/**
 *
 * @author jsanchez
 */
public class Partida3EnRayaUI extends Partida3EnRaya{
    
    public Pantalla_3EnRaya Pantalla;
    
    public Partida3EnRayaUI(Jugador3EnRaya j1, Jugador3EnRaya j2, Pantalla_3EnRaya p) {
        super(j1, j2);
        Pantalla = p;
    }
    
    public static Partida3EnRayaUI crearPartida(Jugador3EnRayaUI personaJ, Pantalla_3EnRaya p) {
        Jugador3EnRaya botJ = Bot3EnRaya.crearBot3EnRaya();
        Jugador3EnRaya J1 = Partida3EnRaya.elegirQuienComienza(personaJ, botJ);
        Jugador3EnRaya J2;
        if(J1.equals(personaJ)){
            J2 = botJ;
        } else {
            J2 = personaJ;
        }
        return new Partida3EnRayaUI(J1,J2,p);
    }
    
    @Override
    protected void nuevoTurno(){
        avanzarTurno();
//        System.out.println("Turno de "+getJugadorTurnoActual().getNombre());
        enviarEstadoTablero(getJugadorTurnoActual());
        getJugadorTurnoActual().pedirJugada(this);
    }
    
    @Override
    public void mostrarGanador(){
//        System.out.println("####\n\nGana "+devolverGanador().orElseThrow(new SupplierExcepcionesNoHayGanador()).getNombre()+"!");
    }
    
    @Override
    public void mostrarEmpate(){
//        System.out.println("####\n\nEmpate!");
    }
    
    @Override
    public void mostrarEstadoTablero() {
        Pantalla.mostrarEstadoTablero();
    }
}
