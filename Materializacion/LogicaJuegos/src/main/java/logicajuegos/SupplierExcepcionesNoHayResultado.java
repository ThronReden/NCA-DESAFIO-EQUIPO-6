package logicajuegos;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 *
 * @author jsanchez
 */
public class SupplierExcepcionesNoHayResultado implements Supplier<NoSuchElementException> {

    @Override
    public NoSuchElementException get() {
        return new NoSuchElementException("No hay resultado, la partida no se ha terminado.");
    }
    
}
