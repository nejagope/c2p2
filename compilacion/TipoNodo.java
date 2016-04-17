/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilacion;
/**
 *
 * @author Nelson Jair
 */
public enum TipoNodo {
    indefinido,
    inc, dec,
    mas, menos, por, div, potencia, modulo,
    menor, mayor, menorI, mayorI, igual, identico, noIgual, noIdentico,
    and, or, xor, not,
    litInt, litBool, litDouble, litStr,
    variable, array,
	asignacion,
	atributo,
	IF, FOR, WHILE, DO, RETURN, 
	sentencias, funcion, ref, parametro,
}
