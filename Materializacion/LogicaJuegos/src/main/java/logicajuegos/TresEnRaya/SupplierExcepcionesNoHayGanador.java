package logicajuegos.TresEnRaya;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 *
 * @author jsanchez
 */
public class SupplierExcepcionesNoHayGanador implements Supplier<NoSuchElementException> {

    @Override
    public NoSuchElementException get() {
        return new NoSuchElementException("No hay ganador, ha sido empate.");
    }
    
}
