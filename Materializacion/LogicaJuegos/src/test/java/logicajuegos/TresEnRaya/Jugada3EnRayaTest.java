package logicajuegos.TresEnRaya;

import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author jsanchez
 */
public class Jugada3EnRayaTest {
    
    Random R = new Random();
    
    public Jugada3EnRayaTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @ParameterizedTest(name = "Crear una jugada con el turno {0} lanza una IllegalArgumentException.")
    @ValueSource(ints = {-231, -1, 0})
    void constructorLanzaExcepcionTurnoNoValido(int turn){
        assertThrows(IllegalArgumentException.class,() -> {
            new Jugada3EnRaya(turn, R.nextInt(9), new Jugador3EnRaya("Jugador"));
        }, "Turno no valido.");
    }
    
    @ParameterizedTest(name = "Crear una jugada con la posicion {0} lanza una IllegalArgumentException.")
    @ValueSource(ints = {-231, -1, 9, 235})
    void constructorLanzaExcepcionPosicionNoValida(int pos){
        assertThrows(IllegalArgumentException.class, () -> {
            new Jugada3EnRaya(R.nextInt(9) + 1, pos, new Jugador3EnRaya("Jugador"));
        }, "Posicion no valida.");
    }
    
    @ParameterizedTest(name = "Crear una jugada con el turno {0} y la posicion {1} es correcto")
    @CsvSource({"1,0","10,8"})
    void constructorCorrecto(int turn, int pos){
        var jugador = new Jugador3EnRaya("Jugador");
        var j = new Jugada3EnRaya(turn, pos, jugador);
        assertAll(
            () -> assertEquals(turn, j.getTurno()),
            () -> assertEquals(pos, j.getPosicion()),
            () -> assertEquals(jugador, j.getJugador())
        );
    }
    
    @Test
    void constantesCorrectas(){
        assertAll(
            () -> assertEquals(0, Jugada3EnRaya.ARRIBA_IZQUIERDA),
            () -> assertEquals(1, Jugada3EnRaya.ARRIBA),
            () -> assertEquals(2, Jugada3EnRaya.ARRIBA_DERECHA),
            () -> assertEquals(3, Jugada3EnRaya.CENTRO_IZQUIERDA),
            () -> assertEquals(4, Jugada3EnRaya.CENTRO),
            () -> assertEquals(5, Jugada3EnRaya.CENTRO_DERECHA),
            () -> assertEquals(6, Jugada3EnRaya.ABAJO_IZQUIERDA),
            () -> assertEquals(7, Jugada3EnRaya.ABAJO),
            () -> assertEquals(8, Jugada3EnRaya.ABAJO_DERECHA)
        );
    }
}
