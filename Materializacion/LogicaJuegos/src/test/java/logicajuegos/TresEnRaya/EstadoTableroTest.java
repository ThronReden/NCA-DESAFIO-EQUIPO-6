package logicajuegos.TresEnRaya;

import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author jsanchez
 */
public class EstadoTableroTest {
    
    Random R = new Random();
    
    public EstadoTableroTest() {
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

    static EstadoTablero getTableroVacio(){
        return new EstadoTablero();
    }
    
    static EstadoTablero geTableroEmpate(){
        EstadoTablero EMPATE = new EstadoTablero();
            EMPATE.setCasilla(Jugada3EnRaya.ARRIBA_IZQUIERDA,EstadoTablero.X);
            EMPATE.setCasilla(Jugada3EnRaya.ARRIBA,EstadoTablero.X);
            EMPATE.setCasilla(Jugada3EnRaya.ARRIBA_DERECHA,EstadoTablero.O);
            EMPATE.setCasilla(Jugada3EnRaya.CENTRO_IZQUIERDA,EstadoTablero.O);
            EMPATE.setCasilla(Jugada3EnRaya.CENTRO,EstadoTablero.X);
            EMPATE.setCasilla(Jugada3EnRaya.CENTRO_DERECHA,EstadoTablero.X);
            EMPATE.setCasilla(Jugada3EnRaya.ABAJO_IZQUIERDA,EstadoTablero.X);
            EMPATE.setCasilla(Jugada3EnRaya.ABAJO,EstadoTablero.O);
            EMPATE.setCasilla(Jugada3EnRaya.ABAJO_DERECHA,EstadoTablero.O);
        return EMPATE;
    }
    
    static EstadoTablero getTableroGanaX(){
        EstadoTablero GANA_X = new EstadoTablero();
            GANA_X.setCasilla(Jugada3EnRaya.ARRIBA_IZQUIERDA,EstadoTablero.X);
            GANA_X.setCasilla(Jugada3EnRaya.ARRIBA,EstadoTablero.X);
            GANA_X.setCasilla(Jugada3EnRaya.ARRIBA_DERECHA,EstadoTablero.O);
            GANA_X.setCasilla(Jugada3EnRaya.CENTRO_IZQUIERDA,EstadoTablero.O);
            GANA_X.setCasilla(Jugada3EnRaya.CENTRO,EstadoTablero.X);
            GANA_X.setCasilla(Jugada3EnRaya.CENTRO_DERECHA,EstadoTablero.VACIO);
            GANA_X.setCasilla(Jugada3EnRaya.ABAJO_IZQUIERDA,EstadoTablero.VACIO);
            GANA_X.setCasilla(Jugada3EnRaya.ABAJO,EstadoTablero.O);
            GANA_X.setCasilla(Jugada3EnRaya.ABAJO_DERECHA,EstadoTablero.X);
        return GANA_X;
    }
    
    static EstadoTablero getTableroGanaO(){
        EstadoTablero GANA_O = new EstadoTablero();
            GANA_O.setCasilla(Jugada3EnRaya.ARRIBA_IZQUIERDA,EstadoTablero.X);
            GANA_O.setCasilla(Jugada3EnRaya.ARRIBA,EstadoTablero.O);
            GANA_O.setCasilla(Jugada3EnRaya.ARRIBA_DERECHA,EstadoTablero.X);
            GANA_O.setCasilla(Jugada3EnRaya.CENTRO_IZQUIERDA,EstadoTablero.X);
            GANA_O.setCasilla(Jugada3EnRaya.CENTRO,EstadoTablero.O);
            GANA_O.setCasilla(Jugada3EnRaya.CENTRO_DERECHA,EstadoTablero.O);
            GANA_O.setCasilla(Jugada3EnRaya.ABAJO_IZQUIERDA,EstadoTablero.VACIO);
            GANA_O.setCasilla(Jugada3EnRaya.ABAJO,EstadoTablero.O);
            GANA_O.setCasilla(Jugada3EnRaya.ABAJO_DERECHA,EstadoTablero.X);
        return GANA_O;
    }
    
    static Stream<Arguments> casosComprobarGetCasillaCorrecto() {
        return Stream.of(
            Arguments.of(getTableroGanaX(), Jugada3EnRaya.ARRIBA_IZQUIERDA, EstadoTablero.X),
            Arguments.of(getTableroGanaO(), Jugada3EnRaya.ARRIBA, EstadoTablero.O),
            Arguments.of(getTableroGanaX(), Jugada3EnRaya.CENTRO_DERECHA, EstadoTablero.VACIO),
            Arguments.of(geTableroEmpate(), Jugada3EnRaya.ARRIBA_IZQUIERDA, EstadoTablero.X),
            Arguments.of(getTableroVacio(), Jugada3EnRaya.CENTRO, EstadoTablero.VACIO)
        );
    }
    
    @ParameterizedTest(name = "En {0} la posicion {1} debe ser {2}")
    @MethodSource("casosComprobarGetCasillaCorrecto")
    void getCasillaCorrecto(EstadoTablero tablero, int posicion, int marcaEsperada){
        assertEquals(marcaEsperada, tablero.getCasilla(posicion));
    }
    
    static Stream<Arguments> casosComprobarSetCasillaCorrecto() {
        return Stream.of(
            Arguments.of(getTableroVacio(), Jugada3EnRaya.CENTRO, EstadoTablero.X),
            Arguments.of(getTableroVacio(), Jugada3EnRaya.CENTRO_DERECHA, EstadoTablero.X),
            Arguments.of(getTableroVacio(), Jugada3EnRaya.ARRIBA_IZQUIERDA, EstadoTablero.O),
            Arguments.of(getTableroVacio(), Jugada3EnRaya.ARRIBA_DERECHA, EstadoTablero.O),
            Arguments.of(getTableroVacio(), Jugada3EnRaya.ABAJO, EstadoTablero.O),
            Arguments.of(getTableroVacio(), Jugada3EnRaya.ABAJO_DERECHA, EstadoTablero.VACIO)
        );
    }
    
    @ParameterizedTest(name = "La posicion {1} ahora debe ser {2}")
    @MethodSource("casosComprobarSetCasillaCorrecto")
    void setCasillaCorrecto(EstadoTablero tablero, int posicion, int nuevaMarca){
        tablero.setCasilla(posicion, nuevaMarca);
        var marcaEsperada = nuevaMarca;
        assertEquals(marcaEsperada, tablero.getCasilla(posicion));
    }
    
    @ParameterizedTest
    @ValueSource(ints = {-241,-1,3,135})
    void setCasillaMarcaInvalidaLanzaExcepcion(int marca){
        assertThrows(IllegalArgumentException.class,() -> {
            getTableroVacio().setCasilla(R.nextInt(9), marca);
        },marca+" no es una marca valida.");
    }
    
    @ParameterizedTest
    @ValueSource(ints = {-241,-1,9,351})
    void setCasillaPosicionInvalidaLanzaExcepcion(int posicion){
        assertThrows(IllegalArgumentException.class,() -> {
            getTableroVacio().setCasilla(posicion, R.nextInt(3));
        },posicion+" esta fuera de rango.");
    }
    
    //FALTAN
}
