package logicajuegos;

import java.util.Optional;
import java.util.Random;

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
//        getEstadoTablero().mostrarEstadoTableroTerminal();
        boolean bueno;
        do{
            bueno = false;
            try{
                hacerJugada(calcularJugada(),p);
                bueno = true;
            } catch(IllegalArgumentException IAE){
//                System.out.println(IAE.getMessage());
            }
        } while(!bueno);
    }

    private int calcularJugada() {
        int pos;
        Random r = new Random();
        
        if(getEstadoTablero().tableroVacio()){
            pos = r.nextInt(9);
            if(pos == 4){
                pos = r.nextInt(9);
            }
        } else if(existeAmenaza()){
            if(r.nextInt(10)+1 > 6){
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
        boolean hayCasillaVacia;
        
        for(int fila = 0; fila < 3; fila++) {
            sum = 0;
            hayCasillaVacia = false;
            for(int casilla = fila*3; casilla < fila*3+3; casilla++) {
                sum += t.getCasilla(casilla);
                hayCasillaVacia |= t.getCasilla(casilla)==EstadoTablero.VACIO;
            }
            if(hayCasillaVacia && sum == amenaza){
                return true;
            }
        }
        return false;
    }

    private boolean comprobarColumnas(int amenaza) {
        EstadoTablero t = getEstadoTablero();
        int sum;
        boolean hayCasillaVacia;
        
        for(int columna = 0; columna < 3; columna++) {
            sum = 0;
            hayCasillaVacia = false;
            for(int casilla = columna; casilla < columna+7; casilla+=3) {
                sum += t.getCasilla(casilla);
                hayCasillaVacia |= t.getCasilla(casilla)==EstadoTablero.VACIO;
            }
            if(hayCasillaVacia && sum == amenaza){
                return true;
            }
        }
        return false;
    }

    private boolean comprobarDiagonales(int amenaza) {
        EstadoTablero t = getEstadoTablero();
        boolean existe;
        existe = (t.getCasilla(0)+ t.getCasilla(4)+ t.getCasilla(8) == amenaza)
            || (t.getCasilla(2) + t.getCasilla(4) + t.getCasilla(6) == amenaza);
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
        
        for(int fila = 0; fila < 3; fila++) {
            sum = 0;
            for(int casilla = fila*3; casilla < fila*3+3; casilla++) {
                sum += t.getCasilla(casilla);
                if(t.getCasilla(casilla) == EstadoTablero.VACIO){
                    pos = Optional.of(casilla);
                }
            }
            if(sum == amenaza){
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
        
        for(int columna = 0; columna < 3; columna++) {
            sum = 0;
            for(int casilla = columna; casilla < columna+7; casilla+=3) {
                sum += t.getCasilla(casilla);
                if(t.getCasilla(casilla) == EstadoTablero.VACIO){
                    pos = Optional.of(casilla);
                }
            }
            if(sum == amenaza){
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
            if(t.getCasilla(0) + t.getCasilla(4) == amenaza){
                pos = Optional.of(t.getCasilla(8));
            } else if(t.getCasilla(4) == EstadoTablero.VACIO){
                pos = Optional.of(t.getCasilla(4));
            } else {
                pos = Optional.of(t.getCasilla(0));
            }
        } else if(t.getCasilla(2)+ t.getCasilla(4)+ t.getCasilla(6)== amenaza){
            if(t.getCasilla(2) + t.getCasilla(4) == amenaza){
                pos = Optional.of(t.getCasilla(6));
            } else if(t.getCasilla(4) == EstadoTablero.VACIO){
                pos = Optional.of(t.getCasilla(4));
            } else {
                pos = Optional.of(t.getCasilla(2));
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
