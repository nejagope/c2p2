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
public class Parametro {
    String nombre;
    int tipo;
    
    public static final int POR_VALOR = 0;
    public static final int POR_REF = 1;
    
    public Parametro(String nombre){
        this.nombre = nombre;
        tipo = POR_VALOR;
    }
    
    public Parametro(String nombre, int tipo){
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    @Override
    public String toString(){
        String tipoStr = "";
        if (tipo == POR_REF)
            tipoStr = " POR REF";
        return nombre + tipoStr;
    }
}
