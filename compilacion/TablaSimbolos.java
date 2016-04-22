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
public class TablaSimbolos extends ArrayList<Simbolo>{
    
    public ArrayList <Bug> errores;
    
    public TablaSimbolos(){
        super();
        errores = new ArrayList<>();
    }
    
    @Override
    public boolean add(Simbolo simbolo){
        return super.add(simbolo);
    }
    
    public void llenar(NodoAST nodo, Simbolo owner){
        Simbolo simbolo;
        switch(nodo.tipo){
            case raiz:
            case include:
                for (NodoAST hijo: nodo.hijos)
                    llenar(hijo, null);
                break;
            case clase:
                simbolo = new Simbolo();
                simbolo.nombre = nodo.getHijo(0).lexema;
                simbolo.tipo = TipoSimbolo.clase;
                simbolo.nodo = nodo;
                if (nodo.getHijo(1).tipo == TipoNodo.etiqueta){
                    simbolo.clasePadre = nodo.getHijo(1).lexema;
                }
                for (NodoAST hijo: nodo.hijos){
                    if (hijo.tipo == TipoNodo.decl_var_global
                            || hijo.tipo == TipoNodo.funcion){
                      llenar(hijo, simbolo);
                    }
                }
                add(simbolo);
                break;
            case funcion:
                simbolo = new Simbolo();
                simbolo.nombre = nodo.getHijo(0).lexema;
                simbolo.tipo = TipoSimbolo.funcion;
                simbolo.nodo = nodo;
                simbolo.nodoSentencias = nodo.getUltimoHijo();
                simbolo.clase = owner.nombre;
                add(simbolo);                
                if (nodo.cantidadHijos() > 2){
                    //tiene parámetros
                    for (int i= 1; i< nodo.cantidadHijos()-1; i++){
                        NodoAST nodoParam = nodo.getHijo(i);                        
                        Simbolo simParam = new Simbolo();
                        simParam.tipo = TipoSimbolo.parametro;
                        simParam.ambito = new Ambito(nodo);
                        simParam.funcionPadre = nodo.getHijo(0).lexema;
                        simParam.posicion = i - 1;
                        simParam.nodo = nodoParam;
                        if (nodoParam.cantidadHijos() == 1){
                            //parametro por valor
                            simbolo.parametros.add(new Parametro(nodoParam.getHijo(0).lexema));
                            simParam.nombre = nodoParam.getHijo(0).lexema;
                            simParam.tipoParametro = Parametro.POR_VALOR;
                        }else{
                            //parametro por referencia
                            simbolo.parametros.add(new Parametro(nodoParam.getHijo(1).lexema, Parametro.POR_REF));
                            simParam.tipoParametro = Parametro.POR_REF;
                            simParam.nombre = nodoParam.getHijo(1).lexema;
                        }
                        add(simParam);
                    }
                }
                NodoAST nodoSents = nodo.getUltimoHijo();
                for (NodoAST sent: nodoSents.hijos){
                    if (sent.tipo == TipoNodo.variable){
                        //declaración de var local
                        Simbolo simVar = new Simbolo();
                        simVar.nombre = sent.lexema;
                        simVar.tipo = TipoSimbolo.varLocal;
                        simVar.funcionPadre = nodo.getHijo(0).lexema;
                        simVar.posicion = simbolo.parametros.size() + simbolo.variables.size();
                        simVar.ambito = new Ambito(nodo);
                        simVar.nodo = sent;
                        
                        add(simVar);
                    }else if (sent.tipo == TipoNodo.asignacion){ 
                        //se puede declarar y asignar a la vez
                    }
                }
                break;                
        }
    }
    
    
    @Override
    public String toString(){
        String msj = "--------------Símbolos-------------\n";
        for (Simbolo s: this){
            msj += s + "\n";            
        }
        return msj  + "-------------------------------------\n";
    }
}
