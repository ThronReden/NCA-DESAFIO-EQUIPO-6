package logicajuegos.TresEnRaya;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.lenient;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author jsanchez
 */
@ExtendWith(MockitoExtension.class)
public class Partida3EnRayaTest {
    
    @Mock
    Jugador3EnRaya jugador1;
    
    @Mock
    Jugador3EnRaya jugador2;
    
    static Partida3EnRaya partida;
    
    static List<Integer> jugadasPartida1J1 = List.of(
        Jugada3EnRaya.ARRIBA_IZQUIERDA,
        Jugada3EnRaya.CENTRO_IZQUIERDA,
        Jugada3EnRaya.ABAJO_DERECHA
    );
    static List<Integer> jugadasPartida1J2 = List.of(
        Jugada3EnRaya.CENTRO,
        Jugada3EnRaya.ABAJO_IZQUIERDA,
        Jugada3EnRaya.ARRIBA_DERECHA
    );
    
    static List<Integer> jugadasPartida2J1 = List.of(
        Jugada3EnRaya.CENTRO,
        Jugada3EnRaya.ABAJO_IZQUIERDA,
        Jugada3EnRaya.ARRIBA_DERECHA
    );
    static List<Integer> jugadasPartida2J2 = List.of(
        Jugada3EnRaya.ARRIBA_IZQUIERDA,
        Jugada3EnRaya.CENTRO_IZQUIERDA
    );
            
    public Partida3EnRayaTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        lenient().when(jugador1.getNombre()).thenReturn("Jugador1");
        lenient().when(jugador2.getNombre()).thenReturn("Jugador2");
        partida = new Partida3EnRaya(jugador1,jugador2);
    }
    
    @AfterEach
    public void tearDown() {
        partida = null;
    }
    
    void entrenarJugadorMock(Jugador3EnRaya jugadorMock, List<Integer> jugadas){                
        lenient().doAnswer(invocation -> {
            Partida3EnRaya p = invocation.getArgument(0);
            int turno = p.getTurno();
            if(turno-1 < jugadas.size()){
                jugadorMock.hacerJugada(jugadas.get(turno-1),p);
            }
            return null;
        }).when(jugadorMock).pedirJugada(any(Partida3EnRaya.class));
    }
    
    @Test
    void getJugadorTurnoActualPartidaNoEnCursoLanzaExcepcion(){
        assertThrows(IllegalArgumentException.class,() -> {
            partida.getJugadorTurnoActual();
        },"La partida no está en curso.");
    }
    
    @Test
    void getJugadorTurnoActualCorrecto(){
        partida.iniciarJuego();
        assertAll("comprobamos para varios turnos diferentes.",
            () -> {
                assertEquals(jugador1, partida.getJugadorTurnoActual());
            },
            () -> {
                partida.avanzarTurno();
                assertEquals(jugador2, partida.getJugadorTurnoActual());
            },
            () -> {
                partida.avanzarTurno();
                assertEquals(jugador1, partida.getJugadorTurnoActual());
            },
            () -> {
                partida.avanzarTurno();
                assertEquals(jugador2, partida.getJugadorTurnoActual());
            }
        );
    }
    
    @Test
    void recibirJugadaNoEsTurnoJugadorLanzaExcepcion(){
        partida.iniciarJuego();
        partida.avanzarTurno();
        Jugada3EnRaya jugada1 = new Jugada3EnRaya(1,5,jugador1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            partida.recibirJugada(jugada1);
        }, "No es el turno de este jugador.");
    }
    
    @Test
    void recibirJugadaPosicionOcupadaLanzaExcepcion(){
        partida.iniciarJuego();
        Jugada3EnRaya jugada1 = new Jugada3EnRaya(1,5,jugador1);
        partida.recibirJugada(jugada1);
        Jugada3EnRaya jugada2 = new Jugada3EnRaya(2,5,jugador2);
        
        assertThrows(IllegalArgumentException.class, () -> {
            partida.recibirJugada(jugada2);
        }, "No se puede hacer otra jugada en esa casilla.");
    }
    
    @Test
    void recibirJugadaPartidaNoEnCursoLanzaExcepcion(){
        Jugada3EnRaya jugada1 = new Jugada3EnRaya(1,5,jugador1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            partida.recibirJugada(jugada1);
        }, "No se pueden hacer jugadas, la partida no está en curso.");
    }
}
