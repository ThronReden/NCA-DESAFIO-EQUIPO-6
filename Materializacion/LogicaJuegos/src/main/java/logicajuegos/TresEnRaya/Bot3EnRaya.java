package logicajuegos.TresEnRaya;

import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jsanchez
 */
public class Bot3EnRaya extends Jugador3EnRaya{
    
    private int marca = 0;
    
    private static final String[] nombresBot = {
        "Botijo",
        "Botardo",
        "Botifarra",
        "R2-D2",
        "Blitzcrank",
        "Enrayator-3000",
        "Bebop",
        "Glados"
    };
    
    private Bot3EnRaya(String nombre) {
        super(nombre);
    }
    
    public static Bot3EnRaya crearBot3EnRaya(){
        return new Bot3EnRaya(genNombreBot());
    }

    private static String genNombreBot() {
        Random r = new Random();
        return nombresBot[r.nextInt(nombresBot.length)]+" (BOT)";
    }
    
    @Override
    public void pedirJugada(Partida3EnRaya p){
        if(marca == 0){
            marca = (p.getTurno()%2 == 1)? EstadoTablero.X : EstadoTablero.O;
        }
        if(p.getClass() == Partida3EnRaya.class){
            getEstadoTablero().mostrarEstadoTableroTerminal();
        }
        new Thread(() -> {
            boolean bueno;
            do {       
                bueno = false;
                try {
                    Thread.sleep(600);
                    hacerJugada(calcularJugada(), p);
                    bueno = true;
                } catch (IllegalArgumentException IAE) {
//                System.out.println(IAE.getMessage());
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bot3EnRaya.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (!bueno);
        }).start();
    }

    private int calcularJugada() {
        int pos;
        Random r = new Random();
        if(getEstadoTablero().tableroVacio()){
            pos = r.nextInt(9);
//            if(pos == 4){
//                pos = r.nextInt(9);
//            }
        } else if(existeAmenaza()){
            if(r.nextInt(10)+1 > 2){
                pos = getTaparAmenaza();
                return pos;
            } else {
                pos = getJugadaAleatoria();
            }
        } else {
            pos = getJugadaAleatoria();
        }
//        System.out.println(getNombre()+" intenta jugar "+(pos+1));
        return pos;
    }

    private int getJugadaAleatoria() {
        Random r = new Random();
        int[] casillas = getEstadoTablero().getCasillasVacias();
        return casillas[r.nextInt(casillas.length)];
    }

    private boolean existeAmenaza() {
        int amenaza = getAmenaza();
        return comprobarFilas(amenaza)
            || comprobarColumnas(amenaza)
            || comprobarDiagonales(amenaza);
    }

    private boolean comprobarFilas(int amenaza) {
        EstadoTablero t = getEstadoTablero();
        int sum;
        int hayCasillaVacia;
        
        for(int fila = 0; fila < 3; fila++) {
            sum = 0;
            hayCasillaVacia = 0;
            for(int casilla = fila*3; casilla < fila*3+3; casilla++) {
                sum += t.getCasilla(casilla);
                if(t.getCasilla(casilla)==EstadoTablero.VACIO){
                    hayCasillaVacia++;
                }
            }
            if(hayCasillaVacia == 1 && sum == amenaza){
                return true;
            }
        }
        return false;
    }

    private boolean comprobarColumnas(int amenaza) {
        EstadoTablero t = getEstadoTablero();
        int sum;
        int hayCasillaVacia;
        
        for(int columna = 0; columna < 3; columna++) {
            sum = 0;
            hayCasillaVacia = 0;
            for(int casilla = columna; casilla < columna+7; casilla+=3) {
                sum += t.getCasilla(casilla);
                if(t.getCasilla(casilla)==EstadoTablero.VACIO){
                    hayCasillaVacia++;
                }
            }
            if(hayCasillaVacia == 1 && sum == amenaza){
                return true;
            }
        }
        return false;
    }

    private boolean comprobarDiagonales(int amenaza) {
        EstadoTablero t = getEstadoTablero();
        boolean existe;
        existe = ((t.getCasilla(0)+ t.getCasilla(4)+ t.getCasilla(8) == amenaza)
                && ((t.getCasilla(0) == EstadoTablero.VACIO
                        && t.getCasilla(4) != EstadoTablero.VACIO
                        && t.getCasilla(8) != EstadoTablero.VACIO)
                    || (t.getCasilla(4) == EstadoTablero.VACIO
                        && t.getCasilla(0) != EstadoTablero.VACIO
                        && t.getCasilla(8) != EstadoTablero.VACIO)
                    || (t.getCasilla(8) == EstadoTablero.VACIO
                        && t.getCasilla(0) != EstadoTablero.VACIO
                        && t.getCasilla(4) != EstadoTablero.VACIO)))
            || ((t.getCasilla(2) + t.getCasilla(4) + t.getCasilla(6) == amenaza)
                && ((t.getCasilla(2) == EstadoTablero.VACIO
                        && t.getCasilla(4) != EstadoTablero.VACIO
                        && t.getCasilla(6) != EstadoTablero.VACIO)
                || (t.getCasilla(4) == EstadoTablero.VACIO
                        && t.getCasilla(2) != EstadoTablero.VACIO
                        && t.getCasilla(6) != EstadoTablero.VACIO)
                || (t.getCasilla(6) == EstadoTablero.VACIO
                        && t.getCasilla(2) != EstadoTablero.VACIO
                        && t.getCasilla(4) != EstadoTablero.VACIO)));
        return existe;
    }
    
    private int getTaparAmenaza() {
        int amenaza = getAmenaza();
        Optional<Integer> posicion = buscarFilas(amenaza);
        if(posicion.isPresent()){
            return posicion.get();
        }
        posicion = buscarColumnas(amenaza);
        if(posicion.isPresent()){
            return posicion.get();
        }
        posicion = buscarDiagonales(amenaza);
        return posicion.get();
    }
    
    private Optional<Integer> buscarFilas(int amenaza) {
        EstadoTablero t = getEstadoTablero();
        int sum;
        Optional<Integer> pos = Optional.empty();
        int hayCasillaVacia;
        
        for(int fila = 0; fila < 3; fila++) {
            sum = 0;
            hayCasillaVacia = 0;
            for(int casilla = fila*3; casilla < fila*3+3; casilla++) {
                sum += t.getCasilla(casilla);
                if(t.getCasilla(casilla) == EstadoTablero.VACIO){
                    hayCasillaVacia++;
                    pos = Optional.of(casilla);
                }
            }
            if(hayCasillaVacia == 1 && sum == amenaza){
                return pos;
            }
            pos = Optional.empty();
        }
        return pos;
    }

    private Optional<Integer> buscarColumnas(int amenaza) {
        EstadoTablero t = getEstadoTablero();
        int sum;
        Optional<Integer> pos = Optional.empty();
        int hayCasillaVacia;
        
        for(int columna = 0; columna < 3; columna++) {
            sum = 0;
            hayCasillaVacia = 0;
            for(int casilla = columna; casilla < columna+7; casilla+=3) {
                sum += t.getCasilla(casilla);
                if(t.getCasilla(casilla) == EstadoTablero.VACIO){
                    hayCasillaVacia++;
                    pos = Optional.of(casilla);
                }
            }
            if(hayCasillaVacia == 1 && sum == amenaza){
                return pos;
            }
            pos = Optional.empty();
        }
        return pos;
    }

    private Optional<Integer> buscarDiagonales(int amenaza) {
        EstadoTablero t = getEstadoTablero();
        Optional<Integer> pos = Optional.empty();
        
        if(t.getCasilla(0) + t.getCasilla(4) + t.getCasilla(8) == amenaza){
            if(t.getCasilla(8) == EstadoTablero.VACIO){
                pos = Optional.of(8);
            } else if(t.getCasilla(4) == EstadoTablero.VACIO){
                pos = Optional.of(4);
            } else if (t.getCasilla(0) == EstadoTablero.VACIO) {
                pos = Optional.of(0);
            }
        }
        if(pos.isEmpty() && t.getCasilla(2)+ t.getCasilla(4)+ t.getCasilla(6)== amenaza){
            if(t.getCasilla(6) == EstadoTablero.VACIO){
                pos = Optional.of(6);
            } else if(t.getCasilla(4) == EstadoTablero.VACIO){
                pos = Optional.of(4);
            } else if (t.getCasilla(2) == EstadoTablero.VACIO) {
                pos = Optional.of(2);
            }
        }
        return pos;
    }

    private int getAmenaza() {
        int amenaza;
        if(this.marca == EstadoTablero.X){
            amenaza = 4;
        } else {
            amenaza = 2;
        }
        return amenaza;
    }
}
