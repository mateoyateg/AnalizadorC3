/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico;

import java.util.ArrayList;

/**
 *
 * @author Daniela Alexandra
 */
public class Nodo {
    public String Etiqueta;
    public ArrayList<Nodo> hijos = new ArrayList<>();
    public String valor;
    public int idNodo;
    public String Separador;

    public String getEtiqueta() {
        return Etiqueta;
    }

    public void setEtiqueta(String Etiqueta) {
        Etiqueta = Etiqueta;
    }

    public ArrayList<Nodo> getHijos() {
        return hijos;
    }

    public void agregarHijos(Nodo hijo) {
        hijos.add(hijo);
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getIdNodo() {
        return idNodo;
    }

    public void setIdNodo(int idNodo) {
        this.idNodo = idNodo;
    }

    public String getSeparador() {
        return Separador;
    }

    public void setSeparador(String Separador) {
        this.Separador = Separador;
    }
    
    
    
    
}
