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
    indefinido, raiz, etiqueta,
    inc, dec,
    mas, menos, por, div, potencia, modulo, concat,
    menor, mayor, menorI, mayorI, igual, identico, noIgual, noIdentico,
    and, or, xor, not,
    litInt, litBool, litDouble, litStr,
    variable, array,
	asignacion,
	atributo,
	IF, FOR, WHILE, DO, RETURN, SWITCH, BREAK,  
	sentencias, funcion, ref, parametro,
	echo,
	decl_var_global, clase,
	llamada, NEW, include, CONTINUE, parent, comportamiento,
}
