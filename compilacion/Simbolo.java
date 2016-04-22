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
public class Simbolo {
    public String nombre;
    public TipoSimbolo tipo;
    public NodoAST nodo, nodoSentencias;    
    public boolean esArray;
    public int tamano; //tamaño de clases o funciones en base a sus simbolos asociados
    public int posicion; //posición de una variable en un metodo
    public String clase; //clase a la que pertenece
    public String clasePadre; //clase de la que hereda
    public ArrayList <Integer> dimensiones; //para arrays
    public ArrayList <String> variables; //atributos de una clase o var locales de una función
    public ArrayList <Parametro> parametros; //parametros de funciones
    public ArrayList <String> funciones; //funciones de una clase
    public int tipoParametro; //por ref o valor
    Ambito ambito; //ambito 
    public String funcionPadre; //para var locales y parametros
    
    public Simbolo(){
        dimensiones = new ArrayList();
        variables = new ArrayList();
        funciones = new ArrayList();
        parametros = new ArrayList();
        esArray = false;
    }
    
    @Override
    public String toString(){
        String msj = "--------------Símbolo-------------\n";
        msj += "Nombre: " + nombre + "\n";
        msj += "Tipo: " + tipo + "\n";
        msj += "Tamaño: " + tamano + "\n";
        msj += "Clase: " + clase + "\n";
        msj += "Clase Padre: " + clasePadre + "\n";            
        msj += "Array: " + esArray + "\n";
        msj += "Posición: " + posicion + "\n";                    
        msj += "Tipo Parametro: " + tipoParametro + "\n";
        msj += "Función Padre: " + funcionPadre + "\n";
        msj += "Ambito: " + ambito + "\n";
        msj += "Funciones: ";
        for (String func : funciones)
            msj += func + ", ";
        msj += "\n";
        msj += "Variables: ";
        for (String var : variables)
            msj += var + ", ";
        msj += "\n";
        msj += "Parámetros: ";
        for (Parametro p : parametros)
            msj += p + ", ";
        msj += "\n";
        msj += "Dimensiones: ";
        for (Integer dim : dimensiones)
            msj += dim + ", ";
        msj += "\n";
        return msj  + "-------------------------------------\n";
    }
    
}
