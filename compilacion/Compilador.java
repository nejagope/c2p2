/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilacion;

import analizador.ParseException;
import analizador.Parser;
import analizador.TokenMgrError;
import compi2_proyecto2.Global;
import java.io.FileNotFoundException;

/**
 *
 * @author NELSON
 */
public class Compilador {
    
    public Errores errores;
    public TablaSimbolos tablaSimbolos;
    public NodoAST ast;
    
    public Compilador(){
        errores = new Errores();
        tablaSimbolos = new TablaSimbolos();
    }
    
    public boolean compilar(String nombreArchivo){
        ast = getAST(Global.carpetaRaiz + nombreArchivo); 
        if (ast != null){
            Global.asignarIDSNodos(ast);
            return true;
        }else{
            return false;
        }
    }
    
    public void graficarAST(String nombreArchivo){
        if (ast != null)
            ast.graficar(nombreArchivo);
    }
    
    /**Transforma el código fuente del archivo a un AST 
     * se antepone al nombre del archivo el path Global.carpetaRaiz 
     * si hubiera archivos incluidos se generan sus respectivos AST y se agregan como
     * hijos al nodo INCLUDE que los invoca
     * @return null si se halla algún error
     */
    public NodoAST getAST(String nombreArchivo){
        try{
            Parser parser = new Parser(new java.io.FileInputStream(Global.carpetaRaiz + nombreArchivo));
            NodoAST ast = new NodoAST("DECLS", TipoNodo.raiz, null);
            parser.INICIO(ast);
            ast = ast.resumir(); 
            //se asignan a todos los nodos el nombre del archivo fuente en el que están definidos
            ast.asignarNombreArchivoFuente(Global.carpetaRaiz + nombreArchivo);
            for (NodoAST hijo: ast.hijos){
                if (hijo.tipo == TipoNodo.include){
                    NodoAST astIncluido = getAST(Global.carpetaRaiz + hijo.getHijo(0).lexema);
                    if (astIncluido != null){
                        hijo.agregarHijo(astIncluido);
                    }
                }
            }
            return ast;
        }catch(ParseException | FileNotFoundException | TokenMgrError ex){
            Bug bug = new Bug();
            bug.archivo = nombreArchivo;
            if (ex instanceof FileNotFoundException){                
                bug.lexema = nombreArchivo;
                bug.msj = "No se halló la ruta especificada para el archivo";                                
                bug.tipo = Bug.ERROR_ARCHIVO;
            } else if (ex instanceof ParseException){
                bug.lexema = ((ParseException)ex).currentToken.image;
                bug.linea = ((ParseException)ex).currentToken.beginLine;
                bug.columna = ((ParseException)ex).currentToken.beginColumn;
                bug.msj = ex.getMessage();                                
                bug.tipo = Bug.ERROR_SINTACTICO;
            }else if (ex instanceof TokenMgrError){
                bug.tipo = Bug.ERROR_LEXICO;
                bug.msj = ((TokenMgrError)ex).getMessage();
            }
            errores.add(bug);
        }
        return null;
    }
}
