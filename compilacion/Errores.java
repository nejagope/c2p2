/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilacion;

import java.util.ArrayList;

/**
 *
 * @author NELSON
 */
public class Errores extends ArrayList<Bug>{
    
    @Override
    public String toString(){
        String msj = "";
        for (Bug bug : this){
            msj += bug.tipo + "," + bug.archivo + "," + bug.lexema + "," + bug.msj + "," + bug.linea + "," + bug.columna + "\n";
        }
        return msj;
    }
}
