package logicajuegos;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * Supplier de excepciones para el retorno {@link java.util.Optional Optional}
 * del <i>getter</i> de {@link Juego#resultado resultado},
 * {@link Juego#devolverResultado() devolverResultado()}, en {@link Juego}.
 * 
 * @see logicajuegos.TresEnRaya.SupplierExcepcionesNoHayGanador
 * 
 * @author jsanchez
 */
public class SupplierExcepcionesNoHayResultado implements Supplier<NoSuchElementException> {

    @Override
    public NoSuchElementException get() {
        return new NoSuchElementException("No hay resultado, la partida no se ha terminado.");
    }
    
}
