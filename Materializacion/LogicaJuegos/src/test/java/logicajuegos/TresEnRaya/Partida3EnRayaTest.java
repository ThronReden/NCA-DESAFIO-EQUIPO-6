package logicajuegos.TresEnRaya;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author jsanchez
 */
public class Partida3EnRayaTest {
    
    static Jugador3EnRaya jugador1 = new Jugador3EnRaya("Jugador1");
    static Jugador3EnRaya jugador2 = new Jugador3EnRaya("Jugador2");
    static Jugador3EnRaya bot = Bot3EnRaya.crearBot();
    
    static Partida3EnRaya partida;
    
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
    }
    
    @AfterEach
    public void tearDown() {
        partida = null;
    }
    
}
