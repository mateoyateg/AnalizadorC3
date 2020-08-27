/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sintactico;

import lexico.Nodo;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Daniela Alexandra
 */
public class ArbolAST {
    
        public  String recorrido(Nodo raiz) throws Exception {
            String cuerpo = "";
            for(Nodo hijos: raiz.hijos) {
                if(hijos.valor != "vacio"){
                    cuerpo += "\"" + raiz.idNodo + "\""+" {label=\""+raiz.Etiqueta+"\"}";
                    cuerpo += "\"" + hijos.idNodo + "\""+" {label=\""+hijos.Etiqueta+hijos.valor+"\"}";
                    cuerpo += "\"" + raiz.idNodo + "\""+" {label=\""+hijos.Etiqueta+"\"}";
                    cuerpo += recorrido(hijos);
                }
                
            }
        
            return cuerpo;
        }
        
        public  void Graficar(String cadena, String cad){
            FileWriter fichero = null;
            PrintWriter pw = null;
            String nombre = cad;
            String archivo = nombre+".dot";
            
            try{
                fichero = new FileWriter(archivo);
                pw = new PrintWriter(fichero);
                pw.println("digraph G {node [shape=oval, style=filled, color= coral1]; edge[color=chartreuseS]; rankdir=UD \n");
                pw.println("\n}");
                fichero.close();
            }catch(Exception e){                
                System.out.println(e);           
            }
            try{
                String cmd = "dot.exe -Tpng"+nombre+".dot -o "+cad+".png";
                Runtime.getRuntime().exec(cmd);
            }catch(IOException ioe){
                System.out.println(ioe);
            }
        }
    
}
