/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilacion;

import java.util.ArrayList;
import java.io.FileWriter;
import java.util.Collections;

/**
 *
 * @author Nelson Jair
 */
public class NodoAST {

    public String etiqueta, archivoFuente, lexema;
    public int linea, columna, id;
    public TipoNodo tipo;
    public Object valor;
    public ArrayList<NodoAST> hijos;
    public NodoAST padre;

    public NodoAST(String etiqueta, NodoAST padre) {
        this.tipo = TipoNodo.indefinido;
        this.etiqueta = etiqueta;
        this.lexema = etiqueta;
        hijos = new ArrayList();
        this.padre = padre;
        if (padre != null) {
            padre.agregarHijo(this);
        }
    }

    public NodoAST(String etiqueta, TipoNodo tipo, NodoAST padre) {
        this.tipo = tipo;
        this.etiqueta = etiqueta;
        this.lexema = etiqueta;
        hijos = new ArrayList();
        this.padre = padre;
        if (padre != null) {
            padre.agregarHijo(this);
        }
    }

    public NodoAST(String etiqueta, int linea, int columna, TipoNodo tipo, NodoAST padre) {
        this.tipo = tipo;
        this.etiqueta = etiqueta;
        this.linea = linea;
        this.columna = columna;
        this.lexema = etiqueta;
        hijos = new ArrayList();
        this.padre = padre;
        if (padre != null) {
            padre.agregarHijo(this);
        }
    }

    public void agregarHijo(NodoAST nodo) {
        if (nodo != null) {
            this.hijos.add(nodo);
            nodo.padre = this;
        }
    }

    public NodoAST getHijo(int indice) {
        try {
            return hijos.get(indice);
        } catch (Exception ex) {
            return null;
        }
    }

    public NodoAST getUltimoHijo() {
        int i = hijos.size();
        if (i == 0) {
            return null;
        }
        return getHijo(i - 1);
    }

    public boolean tieneHijos() {
        if (hijos.size() > 0) {
            return true;
        }
        return false;
    }

    public int cantidadHijos() {
        if (hijos != null) {
            return hijos.size();
        }
        return 0;
    }

    public void dump(String tab) {
        System.out.println(tab + this.etiqueta);
        for (NodoAST hijo : this.hijos) {
            hijo.dump(tab + " ");
        }
    }

    private String getCuerpoDot() {
        String contenido = this.hashCode() + "[label=\"(" + this.id + ")";
        if (this.tipo == TipoNodo.litInt || this.tipo == TipoNodo.litStr || this.tipo == TipoNodo.variable
                || this.tipo == TipoNodo.litDouble || this.tipo == TipoNodo.litBool || this.tipo == TipoNodo.array) {
            contenido += this.etiqueta + "\\n(" + this.lexema + ")\\n"
                   + "[" + this.linea + "," + this.columna + "]\\n" 
                   + "Tipo: " + tipo + "\\n"
                   + "File: " + archivoFuente + "\\n"
                   + "\"];";
        } else {
            //contenido += this.etiqueta + "\"];";
            contenido += this.etiqueta + "\\n(" + this.lexema + ")\\n"
                   + "[" + this.linea + "," + this.columna + "]\\n" 
                   + "Tipo: " + tipo + "\\n"
                   + "File: " + archivoFuente + "\\n"
                   + "\"];";
        }
        for (NodoAST hijo : this.hijos) {
            contenido += this.hashCode() + "--" + hijo.hashCode() + ";";
            contenido += hijo.getCuerpoDot();
        }
        return contenido;
    }

    public String getScriptDot() {
        String script = "graph grafo{";
        script += "node [shape=rectangle];";
        script += getCuerpoDot();
        script += "}";
        return script;
    }

    public void crearArchivoDot(String nombre) {
        FileWriter fw;
        try {
            fw = new FileWriter(nombre + ".dot");
            fw.write(getScriptDot());
            fw.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void graficar(String nombre) {
        crearArchivoDot(nombre);
        try {
            String cmd = "dot " + nombre + ".dot -o " + nombre + ".png -Tpng";
            Runtime.getRuntime().exec(cmd);
        } catch (Exception ioe) {
            System.out.println(ioe);
        }
    }

    public NodoAST resumir() {
        if (etiqueta.equals("DECLS")
            || etiqueta.equals("ARRAY")
			|| etiqueta.equals("ATRIBUTO")
			|| etiqueta.equals("IF")
			|| etiqueta.equals("FOR")
			|| etiqueta.equals("SNTS")
			|| etiqueta.equals("++")
			|| etiqueta.equals("--")
			|| etiqueta.equals("WHILE")
			|| etiqueta.equals("DO")
			|| etiqueta.equals("RETURN")
			|| etiqueta.equals("SWITCH")			
			|| etiqueta.equals("ECHO")
			|| etiqueta.equals("FUNCION")
			|| etiqueta.equals("PARAM")
			|| etiqueta.equals("LLAMADA")
			|| etiqueta.equals("NEW")
			|| etiqueta.equals("INCLUDE")
			|| etiqueta.equals("DECL_VAR_GLOBAL")
			|| etiqueta.equals("CLASE")
			|| etiqueta.equals("PARENT")
			|| etiqueta.equals("COMPORTAMIENTO")
            || etiqueta.equals("=")) {
            NodoAST nuevo = this.clonar();
            for (NodoAST hijo : this.hijos) {
                nuevo.agregarHijo(hijo.resumir());
            }
            return nuevo;
        } 
        else if (etiqueta.equals("LIT_INT")
                || etiqueta.equals("LIT_STR")                  
                || etiqueta.equals("LIT_DOU") 
                || etiqueta.equals("LIT_BOOL")
				|| etiqueta.equals("VAR")
                                || etiqueta.equals("ETIQUETA")
				|| etiqueta.equals("BREAK")
				|| etiqueta.equals("CONTINUE")
				|| etiqueta.equals("REF")) {            
            return this.clonar();
        } 
        else if (etiqueta.equals("EXP_OR")
				|| etiqueta.equals("EXP_XOR")
				|| etiqueta.equals("EXP_AND")								
				//------------------------
				|| etiqueta.equals("EXP_SUM_RES_CONCAT") 
                || etiqueta.equals("EXP_MUL_DIV_MOD")                 
                || etiqueta.equals("EXP_POTENCIA")                 
                || etiqueta.equals("EXP_UNARIA") 
                || etiqueta.equals("EXP_SIMPLE")
                || etiqueta.equals("EXP_REL")
				|| etiqueta.equals("EXP_COMP")
				//------------------------
                ) {
            
            if (this.cantidadHijos() == 1) {
                return this.getHijo(0).resumir();
            } else if (this.cantidadHijos() == 2) {
                if (etiqueta.equals("INC_DEC")) {//dec o inc
                    NodoAST nuevo = this.hijos.get(1).clonar();
                    nuevo.agregarHijo(hijos.get(0).resumir());
                    return nuevo;
                } else if (etiqueta.equals("EXP_UNARIA")) { //negativos o not
                    NodoAST nuevo = this.hijos.get(0).clonar();
                    nuevo.agregarHijo(hijos.get(1).resumir());
                    return nuevo;
                }

            } else if (this.cantidadHijos() >= 3) {
                ArrayList<NodoAST> operandos = new ArrayList();
                ArrayList<NodoAST> operadores = new ArrayList();
                for (int i = 0; i < hijos.size(); i++) {
                    if (i % 2 == 0) {
                        operandos.add(hijos.get(i).resumir());
                    } else {
                        operadores.add(hijos.get(i).clonar());
                    }
                }
                Collections.reverse(operadores);
                Collections.reverse(operandos);

                NodoAST raiz = operadores.get(0);
                for (int i = 0; i < operadores.size(); i++) {
                    if (i == operadores.size() - 1) {
                        operadores.get(i).agregarHijo(operandos.get(i + 1));
                        operadores.get(i).agregarHijo(operandos.get(i));
                    } else {
                        operadores.get(i).agregarHijo(operadores.get(i + 1));
                        operadores.get(i).agregarHijo(operandos.get(i));
                    }
                }
                return raiz;
            }

        }
        return null;
    }

    public void removerHijo(NodoAST nodoHijo) {
        nodoHijo.padre = null;
        this.hijos.remove(nodoHijo);
    }

    private void sustituirPor(NodoAST sustituto) {
        this.padre.agregarHijo(sustituto);
        this.padre.removerHijo(this);
    }

    public NodoAST clonar() {
        NodoAST nodo = new NodoAST(this.etiqueta, null);
        nodo.tipo = this.tipo;
        nodo.linea = this.linea;
        nodo.lexema = this.lexema;
        nodo.columna = this.columna;
        nodo.hijos = new ArrayList<NodoAST>();
        return nodo;
    }
    
    public NodoAST getDescendienteMaxIzq(){
        if (cantidadHijos() == 0)
            return this;
        else
            return (getHijo(0));
    }
    
    public void asignarNombreArchivoFuente(String nombreArchivo){
        this.archivoFuente = nombreArchivo;
        for (NodoAST hijo : this.hijos) {
            hijo.asignarNombreArchivoFuente(nombreArchivo);
        }
    }
}
