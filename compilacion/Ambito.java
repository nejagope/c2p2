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
public class Ambito {
    public int inf, sup;
    
    public Ambito(NodoAST nodo){
        inf = nodo.getDescendienteMaxIzq().id;
        sup = nodo.id;
    }
    
    @Override
    public String toString(){
        return "[" + inf + "," + sup + "]";
    }
}
