/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi2_proyecto2;

import compilacion.NodoAST;

/**
 *
 * @author NELSON
 */
public class Global {
 
    public static int tempIdNodo;
    public static String carpetaRaiz = "";
    
    public static void asignarIDSNodos(NodoAST raiz){
        tempIdNodo = 0;
        asignarIDNodo(raiz);
    }
    
    private static void asignarIDNodo(NodoAST nodo){
        if (nodo == null)
            return;                
        for (NodoAST hijo : nodo.hijos){            
            asignarIDNodo(hijo);            
        }          
        nodo.id = tempIdNodo;
        tempIdNodo ++;
    }
}
