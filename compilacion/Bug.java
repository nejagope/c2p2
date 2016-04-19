/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilacion;

/**
 *
 * @author NELSON
 */
public class Bug {
    public static final int ERROR_LEXICO = 0;
    public static final int ERROR_SINTACTICO = 1;
    public static final int ERROR_SEMANTICO = 2;
    public static final int ERROR_ARCHIVO = 3;
    public static final int ERROR_EJECUCION = 4;
    
    public String msj;
    public String lexema;
    public String archivo;
    public int linea, columna, tipo;
    
}
