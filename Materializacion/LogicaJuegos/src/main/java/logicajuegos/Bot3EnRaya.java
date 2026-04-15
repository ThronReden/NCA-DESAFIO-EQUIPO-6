package logicajuegos;

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
            
        }
        hacerJugada(calcularJugada(),p);
    }

    private int calcularJugada() {
        int pos;
        Random r = new Random();
        
        if(getEstadoTablero().tableroVacio()){
            pos = r.nextInt(9);
            if(pos == 4){
                pos = r.nextInt(9);
                return pos;
            }
        } else if(existeAmenaza()){
            pos = getTaparAmenaza();
            return pos;
        } else {
            pos = r.nextInt(9);
        }
        
        return pos;
    }

    private boolean existeAmenaza() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private int getTaparAmenaza() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
