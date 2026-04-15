package utilidades;

/**
 * Write a description of class Teclado here.
 * 
 * @author (Alberto Ciriano) 
 * @version (SEP-22)
 */

import java.util.*;
import java.io.*;

public class Teclado {
    
    /**
     * Method leerEntero --> lee un entero int desde
     * teclado de forma robusta y obligada
     *
     * @param pregunta --> mensaje a ser mostrado
     * @return valor leido
     */
    public static int leerEntero(String pregunta){
        
        Scanner entrada = new Scanner(System.in);
        int dato = 0 ;
        boolean malo ;
        String ss;             
        do {
            malo = false;          
            try {
                System.out.print ( pregunta );
                dato = entrada.nextInt ();
            }
            catch (InputMismatchException e) {
                malo = true ;
                ss = entrada.nextLine();
                System.out.println("Dato introducido anómalo");
            }
        } while (malo);
        
        return dato ;
    }
    
   
    /**
     * Method leerEnteroLargo--> lee un entero largo longint 
     * desde teclado de forma robusta y obligada
     *
     * @param pregunta --> mensaje a ser mostrado
     * @return valor leido
     */
    public static long leerEnteroLargo(String pregunta){
        Scanner entrada = new Scanner(System.in );
        long dato = 0 ;
        boolean malo ;
        String ss;              
        do {
            malo = false;
            try {
                System.out.print ( pregunta );
                dato = entrada.nextLong ();
            }
            catch (InputMismatchException e) {
                malo = true ;
                ss = entrada.nextLine();
                System.out.println("Dato introducido anómalo");
            }
        } while (malo);
        return dato ;
    }
           
    
    /**
     * Method leerCaracter --> forma clásica de leer un
     * caracter desde teclado en java
     *
     * @param pregunta --> mensaje a ser mostrado
     * @return valor leido
     */
    public static char leerCaracter(String pregunta){
        //forma tradicional de leer un caracter de teclado
        InputStreamReader entrada = 
                           new InputStreamReader(System.in);
        //entrada para que traduzca caracteres a y de UTF
        char dato = '\n';
        System.out.print ( pregunta );
        try{
            dato = (char)entrada.read();
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
        return dato ;
    }
    
    
    /**
     * Method leerCaracter2 --> forma de leer un
     * caracter desde teclado en java usando la clase
     * Scanner
     *
     * @param pregunta --> mensaje a ser mostrado
     * @return valor leido
     */
    public static char leerCaracter2(String pregunta){
        Scanner entrada = new Scanner(System.in );
        char dato ;
        String leido;       
        System.out.print ( pregunta );
        leido = entrada.nextLine();
        
        if (leido.length() == 0)
            dato = '\n';
        else
            dato = leido.charAt(0); 
            
        return dato ;
    }
       
    
    /**
     * Method leerReal--> lee un real double desde
     * teclado de forma robusta y obligada. 
     *
     * @param pregunta --> mensaje a ser mostrado
     * @return valor leido
     */
    public static double leerReal ( String pregunta ) {
        Scanner entrada = new Scanner(System.in);
        entrada.useLocale(Locale.ENGLISH);
        // para formato habitual de números reales en 
        // computación, es decir, con punto decimal  
        double dato = 0.0 ;
        boolean malo ;
        String ss;
                
        do {
            malo = false;
            try {
                System.out.print ( pregunta );
                dato = entrada.nextDouble ();
            }
            catch (InputMismatchException e) {
                malo = true ;
                ss = entrada.nextLine();
                System.out.println("Dato introducido anómalo");
            }
        } while (malo);      
        return dato ;
    }
    
      
    /**
     * Method leerCadena --> lee una cadena de caracteres
     * String desde teclado
     *
     * @param pregunta --> mensaje a ser mostrado
     * @return valor leido
     */
    public static String leerCadena(String pregunta){
        
        Scanner entrada = new Scanner(System.in );
        String leido;
       
        System.out.print ( pregunta );
        leido = entrada.nextLine();
        
        return leido ;
    }
   
}